package net.tropicraft.core.common.entity.placeable;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.tropicraft.Constants;
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.tropicraft.core.common.registry.TropicraftItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ChairEntity extends FurnitureEntity {
    public static Identifier SPAWN_PACKET = new Identifier(Constants.MODID, "furniture_entity_chair");

    // TODO add drips after being wet
    // TODO make it so monkies can sit in the chair ouo
    private static final TrackedData<Byte> COMESAILAWAY = DataTracker.registerData(ChairEntity.class, TrackedDataHandlerRegistry.BYTE);
    
    /** Is any entity sitting in the chair? */
    public boolean isChairEmpty = true;

    /** Acceleration */
    private double speedMultiplier = 0.1;

    public ChairEntity(EntityType<ChairEntity> type, World world) {
        super(type, world, TropicraftItems.CHAIRS);
    }

    @Environment(EnvType.CLIENT)
    public ChairEntity(World world, double x, double y, double z, int id, UUID uuid) {
        super(TropicraftEntities.CHAIR, world, TropicraftItems.CHAIRS);
        updatePosition(x, y, z);
        updateTrackedPosition(x, y, z);
        setEntityId(id);
        setUuid(uuid);
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        super.tick();
        byte b0 = 5;
        double d0 = 0.0D;

        if (this.getComeSailAway()) {
            for (int i = 0; i < b0; ++i) {
                double d1 = this.getBoundingBox().minY + (this.getBoundingBox().maxY - this.getBoundingBox().minY) * (double)(i + 0) / (double)b0 - 0.125D;
                double d3 = this.getBoundingBox().minY + (this.getBoundingBox().maxY - this.getBoundingBox().minY) * (double)(i + 1) / (double)b0 - 0.125D;
                Box axisalignedbb = new Box(this.getBoundingBox().minX, d1, this.getBoundingBox().minZ, this.getBoundingBox().maxX, d3, this.getBoundingBox().maxZ);

                if (this.world.containsFluid(axisalignedbb)) {
                    d0 += 1.0D / (double)b0;
                }
            }
        }

        double d10 = Math.sqrt(this.getVelocity().x * this.getVelocity().x + this.getVelocity().z * this.getVelocity().z);
        double d2;
        double d4;
        int j;

        if (/*this.getComeSailAway() && */d10 > 0.26249999999999996D) {
            d2 = Math.cos((double) this.yaw * Math.PI / 180.0D);
            d4 = Math.sin((double) this.yaw * Math.PI / 180.0D);

            if (this.getComeSailAway())
                for (j = 0; (double)j < 1.0D + d10 * 60.0D; ++j) {
                    double d5 = random.nextFloat() * 2.0F - 1.0F;
                    double d6 = (double)(random.nextInt(2) * 2 - 1) * 0.7D;
                    double particleX;
                    double particleZ;

                    if (random.nextBoolean()) {
                        particleX = getX() - d2 * d5 * 0.8D + d4 * d6;
                        particleZ = getZ() - d4 * d5 * 0.8D - d2 * d6;
                    } else {
                        particleX = getX() + d2 + d4 * d5 * 0.7D;
                        particleZ = getZ() + d4 - d2 * d5 * 0.7D;
                    }
                    world.addParticle(ParticleTypes.SPLASH, particleX, getY() - 0.125D, particleZ, getVelocity().x, getVelocity().y, getVelocity().z);
                }
        }

        double d11;
        double d12;

        if (!this.world.isClient || this.getComeSailAway()) {
            if (d0 < 1.0D) {
                d2 = d0 * 2.0D - 1.0D;
                setVelocity(getVelocity().add(0, 0.03999999910593033D * d2, 0));
            } else {
                if (this.getVelocity().y < 0.0D) {
                    setVelocity(getVelocity().multiply(1, 0.5, 1));
                }

                setVelocity(getVelocity().add(0, 0.007000000216066837D, 0));
            }

            if (this.getComeSailAway() && this.getPrimaryPassenger() != null && this.getPrimaryPassenger() instanceof LivingEntity) {
                LivingEntity entitylivingbase = (LivingEntity)this.getPrimaryPassenger();
                float f = this.getPrimaryPassenger().yaw + -entitylivingbase.sidewaysSpeed * 90.0F;
                double moveX = -Math.sin((double)(f * (float)Math.PI / 180.0F)) * this.speedMultiplier * (double)entitylivingbase.forwardSpeed * 0.05000000074505806D;
                double moveZ = Math.cos((double)(f * (float)Math.PI / 180.0F)) * this.speedMultiplier * (double)entitylivingbase.forwardSpeed * 0.05000000074505806D;
                setVelocity(getVelocity().add(moveX, 0, moveZ));
            }

            d2 = Math.sqrt(this.getVelocity().x * this.getVelocity().x + this.getVelocity().z * this.getVelocity().z);

            if (d2 > 0.45D) {
                d4 = 0.45D / d2;
                setVelocity(getVelocity().multiply(d4, 1, d4));
                d2 = 0.45D;
            }

            if (d2 > d10 && this.speedMultiplier < 0.45D) {
                this.speedMultiplier += (0.45D - this.speedMultiplier) / 45.0D;

                if (this.speedMultiplier > 0.45D) {
                    this.speedMultiplier = 0.45D;
                }
            } else {
                this.speedMultiplier -= (this.speedMultiplier - 0.10D) / 45.0D;

                if (this.speedMultiplier < 0.10D) {
                    this.speedMultiplier = 0.10D;
                }
            }

            int l;

            if (this.getComeSailAway())
                for (l = 0; l < 4; ++l) {
                    int i1 = MathHelper.floor(this.getX() + ((double)(l % 2) - 0.5D) * 0.8D);
                    j = MathHelper.floor(this.getZ() + ((double)(l / 2) - 0.5D) * 0.8D);

                    for (int j1 = 0; j1 < 2; ++j1) {
                        int k = MathHelper.floor(this.getY()) + j1;
                        BlockPos pos = new BlockPos(i1, k, j);
                        Block block = this.world.getBlockState(pos).getBlock();
                        
                        if (block == Blocks.SNOW) {
                            this.world.breakBlock(pos, true);
                            this.horizontalCollision = false;
                        } else if (block == Blocks.LILY_PAD) {
                            this.world.breakBlock(pos, true);
                            this.horizontalCollision = false;
                        }
                    }
                }

            if (this.getComeSailAway() && this.onGround) {
                setVelocity(getVelocity().multiply(0.5, 0.5, 0.5));
            } else if (this.onGround) {
                setVelocity(Vec3d.ZERO);
            }

            this.move(MovementType.SELF, getVelocity());

            // This will never trigger since d10 will only ever get up to 0.45 >:D *evil laugh*
            // In other words, when come sail away, there is no stopping this sucker
            if (this.getComeSailAway()) {
                setVelocity(getVelocity().multiply(0.9900000095367432D, 0.949999988079071D, 0.9900000095367432D));
            }

            this.pitch = 0.0F;
            d4 = (double)this.yaw;
            d11 = this.prevX - this.getX();
            d12 = this.prevZ - this.getZ();

            if (d11 * d11 + d12 * d12 > 0.001D) {
                d4 = (double)((float)(Math.atan2(d12, d11) * 180.0D / Math.PI));
            }

            double d7 = MathHelper.wrapDegrees(d4 - (double)this.yaw);

            if (d7 > 20.0D) {
                d7 = 20.0D;
            }

            if (d7 < -20.0D) {
                d7 = -20.0D;
            }

            this.yaw = (float)((double)this.yaw + d7);
            this.setRotation(this.yaw, this.pitch);

            if (!this.world.isClient) {
                List<?> list = this.world.getOtherEntities(this, this.getBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));

                if (list != null && !list.isEmpty()) {
                    for (int k1 = 0; k1 < list.size(); ++k1) {
                        Entity entity = (Entity)list.get(k1);

                        if (entity != this.getPrimaryPassenger() && entity.isPushable() && entity instanceof ChairEntity) {
                            entity.pushAwayFrom(this);
                        }
                    }
                }

                if (this.getPrimaryPassenger() != null && !this.getPrimaryPassenger().isAlive()) {
                    this.removeAllPassengers();
                }
            }
        } else {
            this.move(MovementType.SELF, getVelocity());
        }
    }
    
    @Override
    protected boolean preventMotion() {
        return !getComeSailAway();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(COMESAILAWAY, Byte.valueOf((byte)0));
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setComeSailAway(Boolean.valueOf(nbt.getBoolean("true")));
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("COME_SAIL_AWAY", getComeSailAway());
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (!world.isClient && !player.isSneaking()) {
            player.startRiding(this);
            return ActionResult.SUCCESS;
        }

        return !player.isConnectedThroughVehicle(this) ? ActionResult.SUCCESS : ActionResult.PASS;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    @Override
    public boolean collides() {
        return isAlive();
    }
    
    @Override
    @Nullable
    public Entity getPrimaryPassenger() {
        List<Entity> list = this.getPassengerList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public double getMountedHeightOffset() {
        return 0.11;
    }
    
    @Override
    public void updatePassengerPosition(Entity passenger) {
        if (this.hasPassenger(passenger)) {
            Vec3d xzOffset = new Vec3d(0, 0, -0.125).rotateY((float) Math.toRadians(-yaw));
            passenger.setPosition(getX() + xzOffset.x, getY() + getMountedHeightOffset() + passenger.getHeightOffset(), getZ() + xzOffset.z);
        }
    }

    public void setComeSailAway(boolean sail) {
        dataTracker.set(COMESAILAWAY, sail ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0));
    }

    public boolean getComeSailAway() {
        return dataTracker.get(COMESAILAWAY) == (byte)1;
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

    /*
    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(TropicraftItems.CHAIRS.get(DyeColor.byId(getColor().getId())));
    }

     */
}
