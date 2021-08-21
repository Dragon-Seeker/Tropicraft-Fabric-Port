package net.tropicraft.core.common.entity.placeable;

import com.google.common.collect.ImmutableList;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkRandom;
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

    public static Identifier SPAWN_PACKET = new Identifier(Constants.MODID, "furniture_entity_float");

    @NotNull
    private static final Random rand = new Random(298457L);
    @NotNull
    private static final OctaveSimplexNoiseSampler windNoise = new OctaveSimplexNoiseSampler(new ChunkRandom(298457L), ImmutableList.of(0));

    /* Wind */
    private double windModifier = 0;

    /* Is any entity laying on the float? */
    public boolean isEmpty;

    /* Acceleration */
    public float rotationSpeed;

    /* Water checks */
    private double prevMotionY;

    public BeachFloatEntity(EntityType<BeachFloatEntity> type, World worldIn) {
        super(type, worldIn, TropicraftItems.BEACH_FLOATS);
        this.ignoreCameraFrustum = true;
        this.isEmpty = true;
        this.inanimate = true;
        this.pushSpeedReduction = .95F;
        setEntityId(this.getEntityId());
    }

    @Environment(EnvType.CLIENT)
    public BeachFloatEntity(World world, double x, double y, double z, int id, UUID uuid) {
        super(TropicraftEntities.BEACH_FLOAT, world, TropicraftItems.BEACH_FLOATS);
        updatePosition(x, y, z);
        updateTrackedPosition(x, y, z);
        setEntityId(id);
        setUuid(uuid);
    }
    
    @Override
    public void setEntityId(int id) {
        super.setEntityId(id);
        random.setSeed(id);
        this.windModifier = (1 + (random.nextGaussian() * 0.1)) - 0.05;
    }

    @Override
    public void tick() {
        Entity rider = getPrimaryPassenger();
        if (world.isClient && rider instanceof PlayerEntity) {
            PlayerEntity controller = (PlayerEntity) rider;
            float move = controller.forwardSpeed;
            float rot = -controller.sidewaysSpeed;
            rotationSpeed += rot * 0.25f;

            float ang = yaw;
            float moveX = MathHelper.sin(-ang * 0.017453292F) * move * 0.0035f;
            float moveZ = MathHelper.cos(ang * 0.017453292F) * move * 0.0035f;
            setVelocity(getVelocity().add(moveX, 0, moveZ));
        }

        if (this.touchingWater) {
            double windAng = (windNoise.sample(getX() / 1000, getZ() / 1000, false) + 1) * Math.PI;
            double windX = Math.sin(windAng) * 0.0005 * windModifier;
            double windZ = Math.cos(windAng) * 0.0005 * windModifier;
            setVelocity(getVelocity().add(windX, 0, windZ));
            // Rotate towards a target yaw with some random perturbance
            double targetYaw = Math.toDegrees(windAng) + ((windModifier - 1) * 45);
            double yaw = (MathHelper.wrapDegrees(this.yaw) + 180 - 35) % 360;
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
            setVelocity(getVelocity().add(0, -MathHelper.clamp(center - water, 0, 0.04), 0));
        } else if (water > center + eps) {
            double floatpush = MathHelper.clamp(water - center, 0, 0.02);
            setVelocity(getVelocity().add(0, floatpush, 0));
        } else if (Math.abs(getVelocity().y) < 0.02) { // Close enough, just force to the correct spot
            if (getVelocity().y != 0) {
                lerpY = water - 0.011;
            }
            setVelocity(getVelocity().multiply(1, 0, 1));
            prevMotionY = 0;
        }
        
        super.tick();

        yaw += rotationSpeed;
        move(MovementType.PLAYER, getVelocity());

        setVelocity(getVelocity().multiply(0.9, 0.9, 0.9));
        rotationSpeed *= 0.9f;

        if (!this.world.isClient) {
            List<Entity> list = this.world.getOtherEntities(this, this.getBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
            for (Entity entity : list) {
                if (entity != this.getPrimaryPassenger() && entity.isPushable()) {
                    entity.pushAwayFrom(this);
                }
            }

            if (this.getPrimaryPassenger() != null && !this.getPrimaryPassenger().isAlive()) {
                this.removeAllPassengers();
            }
        }
    }
    
    @Override
    protected boolean preventMotion() {
        return false;
    }
    
    private double getCenterY() {
        Box bb = getBoundingBox();
        return bb.minY + (bb.maxY - bb.minY) * 0.5D;
    }

    @Override
    protected void fall(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        this.prevMotionY = this.getVelocity().y;
        super.fall(y, onGroundIn, state, pos);
    }

    @Override
    protected boolean updateWaterState() {
        this.fluidHeight.clear();
        this.checkWaterState();
        boolean lava = this.updateMovementInFluid(FluidTags.LAVA, this.world.getDimension().isUltrawarm() ? 0.007 : 0.0023333333333333335D);
        return this.isTouchingWater() || lava;
    }

    void checkWaterState() {
        Box temp = getBoundingBox();
        setBoundingBox(temp.shrink(1, 0, 1).shrink(-1, 0.125, -1));

        try {
            if (this.updateMovementInFluid(FluidTags.WATER, 0.014D)) {
                if (!this.touchingWater && !this.firstUpdate) {
                    this.onSwimmingStart();
                }

                this.fallDistance = 0.0F;
                this.touchingWater = true;
                this.extinguish();
            } else {
                this.touchingWater = false;
            }
        } finally {
            setBoundingBox(temp);
        }
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (!this.world.isClient && !player.isSneaking()) {
            player.startRiding(this);
            return ActionResult.SUCCESS;
        }

        return !player.isConnectedThroughVehicle(this) ? ActionResult.SUCCESS : ActionResult.PASS;
    }

    /* Following three methods copied from EntityBoat for passenger updates */

    @Override
    public void updatePassengerPosition(@NotNull Entity passenger) {
        if (this.hasPassenger(passenger)) {
            // float yaw = this.rotationYaw;

            // passenger.setPosition(x, this.posY + this.getMountedYOffset() + passenger.getYOffset(), z);

            float f = 0.0F;
            float f1 = (float) ((!isAlive() ? 0.001 : this.getMountedHeightOffset()) + passenger.getHeightOffset());

            if (this.getPassengerList().size() > 1) {
                int i = this.getPassengerList().indexOf(passenger);

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
            double x = this.getX() + (-MathHelper.sin(-this.yaw * 0.017453292F) * len);
            double z = this.getZ() + (-MathHelper.cos(this.yaw * 0.017453292F) * len);
            passenger.setPosition(x, this.getY() + (double) f1, z);
            passenger.yaw += this.rotationSpeed;
            passenger.setHeadYaw(passenger.getHeadYaw() + this.rotationSpeed);
            this.applyYawToEntity(passenger);

            if (passenger instanceof LivingEntity && this.getPassengerList().size() > 1) {
                int j = passenger.getEntityId() % 2 == 0 ? 90 : 270;
                passenger.setBodyYaw(((LivingEntity) passenger).bodyYaw + (float) j);
                passenger.setHeadYaw(passenger.getHeadYaw() + (float) j);
            }

            if (passenger instanceof PlayerEntity) {
                ((PlayerEntity) passenger).setBoundingBox(getBoundingBox().stretch(0, 0.3, 0).shrink(0, -0.1875, 0));
            }
        }
    }
    
    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        if (passenger instanceof PlayerEntity) {
            passenger.calculateDimensions();
        }
    }

    protected void applyYawToEntity(Entity entityToUpdate) {
        if (!entityToUpdate.world.isClient || isClientFirstPerson()) {
            entityToUpdate.setBodyYaw(this.yaw);
            float yaw = MathHelper.wrapDegrees(entityToUpdate.yaw - this.yaw);
            float pitch = MathHelper.wrapDegrees(entityToUpdate.pitch - this.pitch);
            float clampedYaw = MathHelper.clamp(yaw, -105.0F, 105.0F);
            float clampedPitch = MathHelper.clamp(pitch, -100F, -10F);
            entityToUpdate.prevYaw += clampedYaw - yaw;
            entityToUpdate.yaw += clampedYaw - yaw;
            entityToUpdate.prevPitch += clampedPitch - pitch;
            entityToUpdate.pitch += clampedPitch - pitch;
            entityToUpdate.setHeadYaw(entityToUpdate.yaw);
        }
    }

    @Override
    public void onPassengerLookAround(@NotNull Entity entityToUpdate) {
        this.applyYawToEntity(entityToUpdate);
    }

    private boolean isClientFirstPerson() {
        return MinecraftClient.getInstance().options.getPerspective() == Perspective.FIRST_PERSON;
    }

    /* Again, from entity boat, for water checks */

    private float getWaterLevel() {
        Box axisalignedbb = this.getBoundingBox();
        int minX = MathHelper.floor(axisalignedbb.minX);
        int maxX = MathHelper.ceil(axisalignedbb.maxX);
        int minY = MathHelper.floor(axisalignedbb.minY - prevMotionY);
        int maxY = minY + 1;
        int minZ = MathHelper.floor(axisalignedbb.minZ);
        int maxZ = MathHelper.ceil(axisalignedbb.maxZ);

        BlockPos.Mutable pos = new BlockPos.Mutable();
        float waterHeight = minY - 1;

        for (int y = maxY; y >= minY; --y) {
            for (int x = minX; x < maxX; x++) {
                for (int z = minZ; z < maxZ; ++z) {
                    pos.set(x, y, z);
                    FluidState fluidstate = this.world.getFluidState(pos);

                    if (fluidstate.getFluid().matchesType(Fluids.WATER)) {
                        waterHeight = Math.max(waterHeight, pos.getY() + fluidstate.getHeight(this.world, pos));
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
    public double getHeightOffset() {
        return 0;
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    @Override
    public double getMountedHeightOffset() {
        return getHeight() - 1.1;
    }

    /**
     * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example, Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
     */
    @Override
    @Nullable
    public Entity getPrimaryPassenger() {
        List<Entity> list = this.getPassengerList();
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * Gets the horizontal facing direction of this Entity, adjusted to take specially-treated entity types into account.
     */
    @Override
    public Direction getMovementDirection() {
        return this.getHorizontalFacing().rotateYClockwise();
    }


    //@Override
    public boolean canBeControlledByRider() {
        return false;
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
