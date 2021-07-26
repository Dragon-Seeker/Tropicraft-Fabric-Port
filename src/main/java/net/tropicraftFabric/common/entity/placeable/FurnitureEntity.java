package net.tropicraftFabric.common.entity.placeable;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.tropicraftFabric.Constants;
import net.tropicraftFabric.common.registry.TropicraftEntities;
import net.tropicraftFabric.common.registry.TropicraftItems;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public abstract class FurnitureEntity extends Entity {

    private static final TrackedData<Integer> COLOR = DataTracker.registerData(FurnitureEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Float> DAMAGE = DataTracker.registerData(FurnitureEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> FORWARD_DIRECTION = DataTracker.registerData(FurnitureEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> TIME_SINCE_HIT = DataTracker.registerData(FurnitureEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public static Identifier SPAWN_PACKET = new Identifier(Constants.MODID, "furniture_entity_default");
    
    private static final int DAMAGE_THRESHOLD = 40;
    
    private final Function<DyeColor, Item> itemLookup;
    
    protected int lerpSteps;
    protected double lerpX;
    protected double lerpY;
    protected double lerpZ;
    protected double lerpYaw = Double.NaN; // Force first-time sync even if packet is incomplete
    protected double lerpPitch;
    
    protected FurnitureEntity(EntityType<?> entityTypeIn, World worldIn, Map<DyeColor, ? extends Item> items) {
        this(entityTypeIn, worldIn, c -> items.get(c));
    }

    protected FurnitureEntity(EntityType<?> entityTypeIn, World worldIn, Function<DyeColor, Item> itemLookup) {
        super(entityTypeIn, worldIn);
        this.itemLookup = itemLookup;
        this.ignoreCameraFrustum = true;
        this.inanimate = true;
        this.pushSpeedReduction = .95F;
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
        this.lerpYaw = this.yaw = MathHelper.wrapDegrees(yaw);
    }



    @Override
    public Packet<?> createSpawnPacket() {
        //return new EntitySpawnS2CPacket(this);

        //NetworkHooks.getEntitySpawningPacket(this);

        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());

        // entity position
        packet.writeDouble(getX());
        packet.writeDouble(getY());
        packet.writeDouble(getZ());

        // entity id & uuid
        packet.writeInt(getEntityId());
        packet.writeUuid(getUuid());

        return ServerSidePacketRegistry.INSTANCE.toPacket(SPAWN_PACKET, packet);
    }

    @Override
    protected void initDataTracker() {
        this.getDataTracker().startTracking(COLOR, 0);
        this.getDataTracker().startTracking(DAMAGE, (float) 0);
        this.getDataTracker().startTracking(FORWARD_DIRECTION, 1);
        this.getDataTracker().startTracking(TIME_SINCE_HIT, 0);
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

        final Vec3d currentPos = getPos();
        prevX = currentPos.x;
        prevY = currentPos.y;
        prevZ = currentPos.z;
    
        super.tick();
    
        tickLerp();
    
        if (preventMotion()) {
            setVelocity(Vec3d.ZERO);
        }
    
        //updateRocking();
    
        this.checkBlockCollision();
        List<Entity> list = this.world.getOtherEntities(this, this.getBoundingBox().expand((double)0.2F, (double)-0.01F, (double)0.2F), EntityPredicates.canBePushedBy(this));
        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (!entity.hasPassenger(this)) {
                    this.pushAwayFrom(entity);
                }
            }
        }
    }
    
    protected boolean preventMotion() {
        return true;
    }
    
    /* Following two methods mostly copied from EntityBoat interpolation code */
    @Override
    public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        if (teleport) {
            super.updateTrackedPositionAndAngles(x, y, z, yaw, pitch, posRotationIncrements, teleport);
        } else {
            this.lerpX = x;
            this.lerpY = y;
            this.lerpZ = z;
            // Avoid "jumping" back to the client's rotation due to vanilla's dumb incomplete packets
            if (yaw != yaw || Double.isNaN(lerpYaw)) {
                this.lerpYaw = MathHelper.wrapDegrees((double) yaw);
            }
            this.lerpSteps = 10;
            this.pitch = pitch;
        }
    }

    private void tickLerp() {
        if (this.lerpSteps > 0) {
            double d0 = this.getX() + (this.lerpX - this.getX()) / (double)this.lerpSteps;
            double d1 = this.getY() + (this.lerpY - this.getY()) / (double)this.lerpSteps;
            double d2 = this.getZ() + (this.lerpZ - this.getZ()) / (double)this.lerpSteps;
            double d3 = MathHelper.wrapDegrees(this.lerpYaw - (double)this.yaw);
            this.yaw = (float)((double)this.yaw + d3 / (double)this.lerpSteps);
            this.pitch = (float)((double)this.pitch + (this.lerpPitch - (double)this.pitch) / (double)this.lerpSteps);
            --this.lerpSteps;
            this.setPosition(d0, d1, d2);
            this.setRotation(this.yaw, this.pitch);
        }
    }

    @Override
    public boolean damage(DamageSource damageSource, float amount) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        }
        else if (!this.world.isClient && isAlive()) {
            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setDamage(this.getDamage() + amount * 10.0F);
            this.scheduleVelocityUpdate();
            boolean flag = damageSource.getAttacker() instanceof PlayerEntity && ((PlayerEntity)damageSource.getAttacker()).abilities.creativeMode;
    
            if (flag || this.getDamage() > DAMAGE_THRESHOLD) {
                Entity rider = this.getPrimaryPassenger();
                if (rider != null) {
                    rider.startRiding(this);
                }
    
                if (!flag) {
                    this.dropStack(getItemStack(), 0.0F);
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
    public double getMountedHeightOffset() {
        return 0.0D;
    }

    @Override
    public void animateDamage() {
        this.setForwardDirection(-1 * this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamage(this.getDamage() * 10.0F);
    }

    @Override
    protected boolean canClimb() {
        return false;
    }

    @Override
    public boolean collides() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        setColor(DyeColor.byId(nbt.getInt("Color")));
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("Color", getColor().ordinal());
    }

    public void setColor(DyeColor color) {
        dataTracker.set(COLOR, color.ordinal());
    }

    public DyeColor getColor() {
        return DyeColor.byId(dataTracker.get(COLOR));
    }

    /**
     * Sets the forward direction of the entity.
     */
    public void setForwardDirection(int dir) {
        dataTracker.set(FORWARD_DIRECTION, dir);
    }

    /**
     * Gets the forward direction of the entity.
     */
    public int getForwardDirection() {
        return dataTracker.get(FORWARD_DIRECTION);
    }

    /**
     * Sets the damage taken from the last hit.
     */
    public void setDamage(float damageTaken) {
        dataTracker.set(DAMAGE, damageTaken);
    }

    /**
     * Gets the damage taken from the last hit.
     */
    public float getDamage() {
        return dataTracker.get(DAMAGE);
    }

    /**
     * Sets the time to count down from since the last time entity was hit.
     */
    public void setTimeSinceHit(int timeSinceHit) {
        dataTracker.set(TIME_SINCE_HIT, timeSinceHit);
    }

    /**
     * Gets the time since the last hit.
     */
    public int getTimeSinceHit() {
        return dataTracker.get(TIME_SINCE_HIT);
    }
}
