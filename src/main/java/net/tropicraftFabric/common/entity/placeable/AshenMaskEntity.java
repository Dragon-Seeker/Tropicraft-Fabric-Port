package net.tropicraftFabric.common.entity.placeable;

import net.tropicraftFabric.Constants;
import net.tropicraftFabric.common.entity.hostile.AshenEntity;
import net.tropicraftFabric.common.item.AshenMasks;
import net.tropicraftFabric.common.registry.TropicraftEntities;
import net.tropicraftFabric.common.registry.TropicraftItems;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.UUID;

public class AshenMaskEntity extends Entity {

    public static Identifier SPAWN_PACKET = new Identifier(Constants.MODID, "ashen_mask");

    private static final TrackedData<Byte> MASK_TYPE = DataTracker.registerData(AshenEntity.class, TrackedDataHandlerRegistry.BYTE);
    public static final int MAX_TICKS_ALIVE = 24000;

    public AshenMaskEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Environment(EnvType.CLIENT)
    public AshenMaskEntity(World world, double x, double y, double z, int id, UUID uuid) {
        super(TropicraftEntities.ASHEN_MASK, world);
        updatePosition(x, y, z);
        updateTrackedPosition(x, y, z);
        setEntityId(id);
        setUuid(uuid);
    }

    public void dropItemStack() {
        dropStack(new ItemStack(TropicraftItems.ASHEN_MASKS.get(AshenMasks.VALUES[getMaskType()])), 1.0F);
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
    protected void initDataTracker() {
        dataTracker.startTracking(MASK_TYPE, (byte) 0);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        setMaskType(nbt.getByte("MaskType"));
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putByte("MaskType", getMaskType());
    }

    @Override
    public Packet<?> createSpawnPacket() {

        //return NetworkHooks.getEntitySpawningPacket(this);
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

    public void setMaskType(byte type) {
        dataTracker.set(MASK_TYPE, type);
    }

    public byte getMaskType() {
        return dataTracker.get(MASK_TYPE);
    }

    @Override
    public void tick() {
        if (!world.isClient) {
            // Remove masks that have been on the ground abandoned for over a day
            if (age >= MAX_TICKS_ALIVE) {
                remove();
            }
        }

        final Vec3d motion = getVelocity();

        if (onGround) {
            setVelocity(motion.multiply(0.5, 0, 0.5));
        }

        if (isTouchingWater()) {
            setVelocity(motion.x * 0.95f, 0.02f, motion.z * 0.95f);
        } else {
            setVelocity(motion.subtract(0, 0.05f, 0));
        }

        move(MovementType.SELF, motion);
    }

    @Override
    public boolean damage(DamageSource damageSource, float par2) {
        if (isInvulnerableTo(damageSource)) {
            return false;
        } else {
            if (isAlive() && !world.isClient) {
                remove();
                scheduleVelocityUpdate();
                dropItemStack();
            }

            return true;
        }
    }

    /*
    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(TropicraftItems.ASHEN_MASKS.get(AshenMasks.VALUES[getMaskType()]).get());
    }
     */
}
