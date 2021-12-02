package net.tropicraft.core.common.entity.placeable;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.tropicraft.Constants;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class FurnitureEntity extends Entity {

    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(FurnitureEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(FurnitureEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> FORWARD_DIRECTION = SynchedEntityData.defineId(FurnitureEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TIME_SINCE_HIT = SynchedEntityData.defineId(FurnitureEntity.class, EntityDataSerializers.INT);

    public static ResourceLocation SPAWN_PACKET = new ResourceLocation(Constants.MODID, "furniture_entity_default");
    
    private static final int DAMAGE_THRESHOLD = 40;
    
    private final Function<DyeColor, Item> itemLookup;
    
    protected int lerpSteps;
    protected double lerpX;
    protected double lerpY;
    protected double lerpZ;
    protected double lerpYaw = Double.NaN; // Force first-time sync even if packet is incomplete
    protected double lerpPitch;
    
    protected FurnitureEntity(EntityType<?> entityTypeIn, Level worldIn, Map<DyeColor, ? extends Item> items) {
        this(entityTypeIn, worldIn, c -> items.get(c));
    }

    protected FurnitureEntity(EntityType<?> entityTypeIn, Level worldIn, Function<DyeColor, Item> itemLookup) {
        super(entityTypeIn, worldIn);
        this.itemLookup = itemLookup;
        this.noCulling = true;
        this.blocksBuilding = true;
        this.pushthrough = .95F;
    }
    /*
    @Environment(EnvType.CLIENT)
    public FurnitureEntity(World world, double x, double y, double z, int id, UUID uuid) {
        super(TropicraftEntities.BEACH_FLOAT, world);
        updatePosition(x, y, z);
        updateTrackedPosition(x, y, z);
        setEntityId(id);
        setUuid(uuid);
    }
     */
    
    public void setRotation(float yaw) {
        this.lerpYaw = this.yRot = Mth.wrapDegrees(yaw);
    }



    @Override
    public Packet<?> getAddEntityPacket() {
        //return new EntitySpawnS2CPacket(this);

        //NetworkHooks.getEntitySpawningPacket(this);

        FriendlyByteBuf packet = new FriendlyByteBuf(Unpooled.buffer());

        // entity position
        packet.writeDouble(getX());
        packet.writeDouble(getY());
        packet.writeDouble(getZ());

        // entity id & uuid
        packet.writeInt(getId());
        packet.writeUUID(getUUID());

        return ServerSidePacketRegistry.INSTANCE.toPacket(SPAWN_PACKET, packet);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(COLOR, 0);
        this.getEntityData().define(DAMAGE, (float) 0);
        this.getEntityData().define(FORWARD_DIRECTION, 1);
        this.getEntityData().define(TIME_SINCE_HIT, 0);
    }

    @Override
    public void tick() {
        final int timeSinceHit = getTimeSinceHit();
        if (timeSinceHit > 0) {
            setTimeSinceHit(timeSinceHit - 1);
        }
    
        final float damage = getDamage();
        if (damage > 0) {
            setDamage(damage - 1);
        }

        final Vec3 currentPos = position();
        xo = currentPos.x;
        yo = currentPos.y;
        zo = currentPos.z;
    
        super.tick();
    
        tickLerp();
    
        if (preventMotion()) {
            setDeltaMovement(Vec3.ZERO);
        }
    
        //updateRocking();
    
        this.checkInsideBlocks();
        List<Entity> list = this.level.getEntities(this, this.getBoundingBox().inflate((double)0.2F, (double)-0.01F, (double)0.2F), EntitySelector.pushableBy(this));
        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (!entity.hasPassenger(this)) {
                    this.push(entity);
                }
            }
        }
    }
    
    protected boolean preventMotion() {
        return true;
    }
    
    /* Following two methods mostly copied from EntityBoat interpolation code */
    @Override
    public void lerpTo(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        if (teleport) {
            super.lerpTo(x, y, z, yaw, pitch, posRotationIncrements, teleport);
        } else {
            this.lerpX = x;
            this.lerpY = y;
            this.lerpZ = z;
            // Avoid "jumping" back to the client's rotation due to vanilla's dumb incomplete packets
            if (yaw != yaw || Double.isNaN(lerpYaw)) {
                this.lerpYaw = Mth.wrapDegrees((double) yaw);
            }
            this.lerpSteps = 10;
            this.xRot = pitch;
        }
    }

    private void tickLerp() {
        if (this.lerpSteps > 0) {
            double d0 = this.getX() + (this.lerpX - this.getX()) / (double)this.lerpSteps;
            double d1 = this.getY() + (this.lerpY - this.getY()) / (double)this.lerpSteps;
            double d2 = this.getZ() + (this.lerpZ - this.getZ()) / (double)this.lerpSteps;
            double d3 = Mth.wrapDegrees(this.lerpYaw - (double)this.yRot);
            this.yRot = (float)((double)this.yRot + d3 / (double)this.lerpSteps);
            this.xRot = (float)((double)this.xRot + (this.lerpPitch - (double)this.xRot) / (double)this.lerpSteps);
            --this.lerpSteps;
            this.setPos(d0, d1, d2);
            this.setRot(this.yRot, this.xRot);
        }
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        }
        else if (!this.level.isClientSide && isAlive()) {
            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setDamage(this.getDamage() + amount * 10.0F);
            this.markHurt();
            boolean flag = damageSource.getEntity() instanceof Player && ((Player)damageSource.getEntity()).abilities.instabuild;
    
            if (flag || this.getDamage() > DAMAGE_THRESHOLD) {
                Entity rider = this.getControllingPassenger();
                if (rider != null) {
                    rider.startRiding(this);
                }
    
                if (!flag) {
                    this.spawnAtLocation(getItemStack(), 0.0F);
                }
    
                this.remove();
            }
        }
    
        return true;
    }

    private ItemStack getItemStack() {
        return new ItemStack(itemLookup.apply(getColor()));
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0.0D;
    }

    @Override
    public void animateHurt() {
        this.setForwardDirection(-1 * this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamage(this.getDamage() * 10.0F);
    }

    @Override
    protected boolean isMovementNoisy() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        setColor(DyeColor.byId(nbt.getInt("Color")));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        nbt.putInt("Color", getColor().ordinal());
    }

    public void setColor(DyeColor color) {
        entityData.set(COLOR, color.ordinal());
    }

    public DyeColor getColor() {
        return DyeColor.byId(entityData.get(COLOR));
    }

    /**
     * Sets the forward direction of the entity.
     */
    public void setForwardDirection(int dir) {
        entityData.set(FORWARD_DIRECTION, dir);
    }

    /**
     * Gets the forward direction of the entity.
     */
    public int getForwardDirection() {
        return entityData.get(FORWARD_DIRECTION);
    }

    /**
     * Sets the damage taken from the last hit.
     */
    public void setDamage(float damageTaken) {
        entityData.set(DAMAGE, damageTaken);
    }

    /**
     * Gets the damage taken from the last hit.
     */
    public float getDamage() {
        return entityData.get(DAMAGE);
    }

    /**
     * Sets the time to count down from since the last time entity was hit.
     */
    public void setTimeSinceHit(int timeSinceHit) {
        entityData.set(TIME_SINCE_HIT, timeSinceHit);
    }

    /**
     * Gets the time since the last hit.
     */
    public int getTimeSinceHit() {
        return entityData.get(TIME_SINCE_HIT);
    }
}