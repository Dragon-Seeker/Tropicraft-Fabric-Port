package net.tropicraft.core.common.entity.projectile;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.tropicraft.Constants;
import net.tropicraft.core.common.registry.TropicraftEntities;

import java.util.UUID;

public class LavaBallEntity extends Entity {

    public static Identifier SPAWN_PACKET = new Identifier(Constants.MODID, "lava_ball");

    public boolean setFire;
    public float size;
    public boolean held;
    public int lifeTimer;

    public double accelerationX;
    public double accelerationY;
    public double accelerationZ;

    public LavaBallEntity(EntityType<? extends LavaBallEntity> type, World world) {
        super(type, world);
        setFire = false;
        held = false;
        size = 1;
        lifeTimer = 0;
    }

    public LavaBallEntity(EntityType<? extends LavaBallEntity> type,World world, double i, double j, double k, double motX, double motY, double motZ) {
        super(type, world);
        setFire = false;
        refreshPositionAndAngles(i, j, k, 0, 0);
        accelerationX = motX;
        accelerationY = motY;
        accelerationZ = motZ;
        size = 1;
        held = false;
        lifeTimer = 0;
    }

    public LavaBallEntity(EntityType<? extends LavaBallEntity> type, World world, float startSize) {
        super(type, world);
        size = startSize;
        setFire = false;
        held = true;
        lifeTimer = 0;
    }

    @Environment(EnvType.CLIENT)
    public LavaBallEntity(World world, double x, double y, double z, int id, UUID uuid) {
        super(TropicraftEntities.LAVA_BALL, world);
        updatePosition(x, y, z);
        updateTrackedPosition(x, y, z);
        setId(id);
        setUuid(uuid);
    }




    @Override
    public boolean collides() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Environment(EnvType.CLIENT)
    public void supahDrip() {
        float x = (float) getX();
        float y = (float) getY();
        float z = (float) getZ();

        if (world.isClient) {
            world.addParticle(ParticleTypes.LAVA, x, y, z, this.getVelocity().x, -1.5F, this.getVelocity().z);
        }
    }

    @Override
    protected void initDataTracker()
    {

    }

    @Override
    public void tick() {
        super.tick();
        // System.out.println("laba ball: " + posX + " " + posY + " " + posZ);

        if (lifeTimer < 500)
        {
            lifeTimer++;
        }
        else
        {
            this.remove(RemovalReason.KILLED);
        }

        double motionX = this.getVelocity().x;
        double motionY = this.getVelocity().y;
        double motionZ = this.getVelocity().z;

        if (size < 1) {
            size += .025;
        }

        if (onGround) {
            motionZ *= .95;
            motionX *= .95;
        }

        motionY *= .99;

        if (!onGround) {
            motionY -=.05F;
            if (world.isClient) {
                for (int i = 0; i < 5 + random.nextInt(3); i++){
                    supahDrip();
                }
            }
        }

        if (horizontalCollision) {
            motionZ = 0;
            motionX = 0;
        }

        //TODO: Note below, these used to be tempLavaMoving - maybe they still need to be?
        int thisX = (int)Math.floor(getX());
        int thisY = (int)Math.floor(getY());
        int thisZ = (int)Math.floor(getZ());

        BlockPos posCurrent = new BlockPos(thisX, thisY, thisZ);
        BlockPos posBelow = posCurrent.down();
        BlockState stateBelow = world.getBlockState(posBelow);

        if (!world.isAir(posBelow) && stateBelow.getMaterial() != Material.LAVA && !held) {
            if (setFire) {
                world.setBlockState(posCurrent, Blocks.LAVA.getDefaultState(), 3);
                this.remove(RemovalReason.DISCARDED);
            }

            if (!setFire) {
                if (world.isAir(posCurrent.west())) {
                    world.setBlockState(posCurrent.west(), Blocks.LAVA.getDefaultState(), 2);
                }

                if (world.isAir(posCurrent.east())) {
                    world.setBlockState(posCurrent.east(), Blocks.LAVA.getDefaultState(), 2);
                }

                if (world.isAir(posCurrent.south())) {
                    world.setBlockState(posCurrent.south(), Blocks.LAVA.getDefaultState(), 2);
                }

                if (world.isAir(posCurrent.north())) {
                    world.setBlockState(posCurrent.north(), Blocks.LAVA.getDefaultState(), 2);
                }

                world.setBlockState(posCurrent, Blocks.LAVA.getDefaultState(), 3);
                setFire = true;
            }
        }

        Vec3d motion = new Vec3d(motionX + this.accelerationX, motionY + this.accelerationY, motionZ + this.accelerationZ);
        this.setVelocity(motion);

        this.move(MovementType.SELF, motion);
    }

    // TODO: Need this again? 1.14
    /*@Override
    protected void entityInit() {

    }*/

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.lifeTimer = nbt.getInt("lifeTimer");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("lifeTimer", this.lifeTimer);
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
        packet.writeInt(getId());
        packet.writeUuid(getUuid());

        return ServerSidePacketRegistry.INSTANCE.toPacket(SPAWN_PACKET, packet);
    }

}
