package net.tropicraft.core.common.entity.placeable;

import com.google.common.collect.ImmutableList;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
//import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.tropicraft.Constants;
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.tropicraft.core.common.registry.TropicraftItems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class BeachFloatEntity extends FurnitureEntity {//implements IEntityAdditionalSpawnData {

    public static ResourceLocation SPAWN_PACKET = new ResourceLocation(Constants.MODID, "furniture_entity_float");

    @NotNull
    private static final Random rand = new Random(298457L);
    @NotNull
    private static final PerlinSimplexNoise windNoise = new PerlinSimplexNoise(new WorldgenRandom(298457L), ImmutableList.of(0));

    /* Wind */
    private double windModifier = 0;

    /* Is any entity laying on the float? */
    public boolean isEmpty;

    /* Acceleration */
    public float rotationSpeed;

    /* Water checks */
    private double prevMotionY;

    public BeachFloatEntity(EntityType<BeachFloatEntity> type, Level worldIn) {
        super(type, worldIn, TropicraftItems.BEACH_FLOATS);
        this.noCulling = true;
        this.isEmpty = true;
        this.blocksBuilding = true;
        //TODO: Welp, figure out how to readd this later
        //this.pushSpeedReduction = .95F;
        setId(this.getId());
    }

    @Environment(EnvType.CLIENT)
    public BeachFloatEntity(Level world, double x, double y, double z, int id, UUID uuid) {
        super(TropicraftEntities.BEACH_FLOAT, world, TropicraftItems.BEACH_FLOATS);
        absMoveTo(x, y, z);
        setPacketCoordinates(x, y, z);
        setId(id);
        setUUID(uuid);
    }
    
    @Override
    public void setId(int id) {
        super.setId(id);
        random.setSeed(id);
        this.windModifier = (1 + (random.nextGaussian() * 0.1)) - 0.05;
    }

    @Override
    public void tick() {
        Entity rider = getControllingPassenger();
        if (level.isClientSide && rider instanceof Player) {
            Player controller = (Player) rider;
            float move = controller.zza;
            float rot = -controller.xxa;
            rotationSpeed += rot * 0.25f;

            float ang = this.getYRot();
            float moveX = Mth.sin(-ang * 0.017453292F) * move * 0.0035f;
            float moveZ = Mth.cos(ang * 0.017453292F) * move * 0.0035f;
            setDeltaMovement(getDeltaMovement().add(moveX, 0, moveZ));
        }

        if (this.wasTouchingWater) {
            double windAng = (windNoise.getValue(getX() / 1000, getZ() / 1000, false) + 1) * Math.PI;
            double windX = Math.sin(windAng) * 0.0005 * windModifier;
            double windZ = Math.cos(windAng) * 0.0005 * windModifier;
            setDeltaMovement(getDeltaMovement().add(windX, 0, windZ));
            // Rotate towards a target yaw with some random perturbance
            double targetYaw = Math.toDegrees(windAng) + ((windModifier - 1) * 45);
            double yaw = (Mth.wrapDegrees(this.getYRot()) + 180 - 35) % 360;
            double angleDiff = targetYaw - yaw;
            if (angleDiff > 0) {
                this.rotationSpeed += Math.min(0.005 * windModifier, angleDiff);
            } else {
                this.rotationSpeed += Math.max(-0.005 * windModifier, angleDiff);
            }
        }

        double water = getWaterLevel();
        double center = getCenterY();
        double eps = 1 / 16D;
        if (water < center - eps) { // Gravity
            setDeltaMovement(getDeltaMovement().add(0, -Mth.clamp(center - water, 0, 0.04), 0));
        } else if (water > center + eps) {
            double floatpush = Mth.clamp(water - center, 0, 0.02);
            setDeltaMovement(getDeltaMovement().add(0, floatpush, 0));
        } else if (Math.abs(getDeltaMovement().y) < 0.02) { // Close enough, just force to the correct spot
            if (getDeltaMovement().y != 0) {
                lerpY = water - 0.011;
            }
            setDeltaMovement(getDeltaMovement().multiply(1, 0, 1));
            prevMotionY = 0;
        }
        
        super.tick();

        this.setYRot(this.getYRot() + rotationSpeed);
        //yaw += rotationSpeed;

        move(MoverType.PLAYER, getDeltaMovement());

        setDeltaMovement(getDeltaMovement().multiply(0.9, 0.9, 0.9));
        rotationSpeed *= 0.9f;

        if (!this.level.isClientSide) {
            List<Entity> list = this.level.getEntities(this, this.getBoundingBox().inflate(0.20000000298023224D, 0.0D, 0.20000000298023224D));
            for (Entity entity : list) {
                if (entity != this.getControllingPassenger() && entity.isPushable()) {
                    entity.push(this);
                }
            }

            if (this.getControllingPassenger() != null && !this.getControllingPassenger().isAlive()) {
                this.ejectPassengers();
            }
        }
    }
    
    @Override
    protected boolean preventMotion() {
        return false;
    }
    
    private double getCenterY() {
        AABB bb = getBoundingBox();
        return bb.minY + (bb.maxY - bb.minY) * 0.5D;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        this.prevMotionY = this.getDeltaMovement().y;
        super.checkFallDamage(y, onGroundIn, state, pos);
    }

    @Override
    protected boolean updateInWaterStateAndDoFluidPushing() {
        this.fluidHeight.clear();
        this.updateInWaterStateAndDoWaterCurrentPushing();
        boolean lava = this.updateFluidHeightAndDoFluidPushing(FluidTags.LAVA, this.level.dimensionType().ultraWarm() ? 0.007 : 0.0023333333333333335D);
        return this.isInWater() || lava;
    }

    void updateInWaterStateAndDoWaterCurrentPushing() {
        AABB temp = getBoundingBox();
        setBoundingBox(temp.contract(1, 0, 1).contract(-1, 0.125, -1));

        try {
            if (this.updateFluidHeightAndDoFluidPushing(FluidTags.WATER, 0.014D)) {
                if (!this.wasTouchingWater && !this.firstTick) {
                    this.doWaterSplashEffect();
                }

                this.fallDistance = 0.0F;
                this.wasTouchingWater = true;
                this.clearFire();
            } else {
                this.wasTouchingWater = false;
            }
        } finally {
            setBoundingBox(temp);
        }
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (!this.level.isClientSide && !player.isShiftKeyDown()) {
            player.startRiding(this);
            return InteractionResult.SUCCESS;
        }

        return !player.isPassengerOfSameVehicle(this) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    /* Following three methods copied from EntityBoat for passenger updates */

    @Override
    public void positionRider(@NotNull Entity passenger) {
        if (this.hasPassenger(passenger)) {
            // float yaw = this.rotationYaw;

            // passenger.setPosition(x, this.posY + this.getMountedYOffset() + passenger.getYOffset(), z);

            float f = 0.0F;
            float f1 = (float) ((!isAlive() ? 0.001 : this.getPassengersRidingOffset()) + passenger.getMyRidingOffset());

            if (this.getPassengers().size() > 1) {
                int i = this.getPassengers().indexOf(passenger);

                if (i == 0) {
                    f = 0.2F;
                } else {
                    f = -0.6F;
                }

                if (passenger instanceof LivingEntity) {
                    f = (float) ((double) f + 0.2D);
                }
            }

            float len = 0.6f;
            double x = this.getX() + (-Mth.sin(-this.getYRot() * 0.017453292F) * len);
            double z = this.getZ() + (-Mth.cos(this.getYRot() * 0.017453292F) * len);
            passenger.setPos(x, this.getY() + (double) f1, z);
            passenger.setYRot(this.getYRot() + this.rotationSpeed);
            //passenger.yaw += this.rotationSpeed;
            passenger.setYHeadRot(passenger.getYHeadRot() + this.rotationSpeed);
            this.applyYawToEntity(passenger);

            if (passenger instanceof LivingEntity && this.getPassengers().size() > 1) {
                int j = passenger.getId() % 2 == 0 ? 90 : 270;
                passenger.setYBodyRot(((LivingEntity) passenger).yBodyRot + (float) j);
                passenger.setYHeadRot(passenger.getYHeadRot() + (float) j);
            }

            if (passenger instanceof Player) {
                ((Player) passenger).setBoundingBox(getBoundingBox().expandTowards(0, 0.3, 0).contract(0, -0.1875, 0));
            }
        }
    }
    
    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        if (passenger instanceof Player) {
            passenger.refreshDimensions();
        }
    }

    protected void applyYawToEntity(Entity entityToUpdate) {
        if (!entityToUpdate.level.isClientSide || isClientFirstPerson()) {
            entityToUpdate.setYBodyRot(this.getYRot());
            float yaw = Mth.wrapDegrees(entityToUpdate.getYRot() - this.getYRot());
            float pitch = Mth.wrapDegrees(entityToUpdate.getXRot() - this.getXRot());
            float clampedYaw = Mth.clamp(yaw, -105.0F, 105.0F);
            float clampedPitch = Mth.clamp(pitch, -100F, -10F);
            entityToUpdate.yRotO += clampedYaw - yaw;
            entityToUpdate.setYRot(this.getYRot() + (clampedYaw - yaw));
            //entityToUpdate.yaw += clampedYaw - yaw;

            entityToUpdate.xRotO += clampedPitch - pitch;
            entityToUpdate.setXRot(this.getXRot() + (clampedPitch - pitch));
            //entityToUpdate.pitch += clampedPitch - pitch;

            entityToUpdate.setYHeadRot(entityToUpdate.getYRot());
        }
    }

    @Override
    public void onPassengerTurned(@NotNull Entity entityToUpdate) {
        this.applyYawToEntity(entityToUpdate);
    }

    private boolean isClientFirstPerson() {
        return Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON;
    }

    /* Again, from entity boat, for water checks */

    private float getWaterLevel() {
        AABB axisalignedbb = this.getBoundingBox();
        int minX = Mth.floor(axisalignedbb.minX);
        int maxX = Mth.ceil(axisalignedbb.maxX);
        int minY = Mth.floor(axisalignedbb.minY - prevMotionY);
        int maxY = minY + 1;
        int minZ = Mth.floor(axisalignedbb.minZ);
        int maxZ = Mth.ceil(axisalignedbb.maxZ);

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        float waterHeight = minY - 1;

        for (int y = maxY; y >= minY; --y) {
            for (int x = minX; x < maxX; x++) {
                for (int z = minZ; z < maxZ; ++z) {
                    pos.set(x, y, z);
                    FluidState fluidstate = this.level.getFluidState(pos);

                    if (fluidstate.getType().isSame(Fluids.WATER)) {
                        waterHeight = Math.max(waterHeight, pos.getY() + fluidstate.getHeight(this.level, pos));
                    }
                    if (waterHeight >= maxY) {
                        return waterHeight;
                    }
                }
            }
        }

        return waterHeight;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public double getMyRidingOffset() {
        return 0;
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    @Override
    public double getPassengersRidingOffset() {
        return getBbHeight() - 1.1;
    }

    /**
     * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example, Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
     */
    @Override
    @Nullable
    public Entity getControllingPassenger() {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * Gets the horizontal facing direction of this Entity, adjusted to take specially-treated entity types into account.
     */
    @Override
    public Direction getMotionDirection() {
        return this.getDirection().getClockWise();
    }


    //@Override
    public boolean canBeControlledByRider() {
        return false;
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
        packet.writeDouble(this.lerpYaw);

        return ServerSidePacketRegistry.INSTANCE.toPacket(SPAWN_PACKET, packet);
    }
    /*
    @Override
    public void writeSpawnData(PacketByteBuf buffer) {
        buffer.writeDouble(this.lerpYaw);
    }

    @Override
    public void readSpawnData(PacketByteBuf additionalData) {
        this.lerpYaw = MathHelper.wrapDegrees(additionalData.readDouble());
    }
     */

    /*
    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(TropicraftItems.BEACH_FLOATS.get(DyeColor.byId(getColor().getId())));
    }
     */
}
