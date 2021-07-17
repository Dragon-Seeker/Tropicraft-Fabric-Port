package com.dragonseeker.tropicfabricport.entity;

import com.dragonseeker.tropicfabricport.Tropicfabricport;
import com.dragonseeker.tropicfabricport.item.AshenMasks;
import com.dragonseeker.tropicfabricport.registry.TropicItems;
import io.netty.buffer.Unpooled;
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

public class AshenMaskEntity extends Entity {

    private static final TrackedData<Byte> MASK_TYPE = DataTracker.registerData(AshenEntity.class, TrackedDataHandlerRegistry.BYTE);
    public static final int MAX_TICKS_ALIVE = 24000;

    public AshenMaskEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public void dropItemStack() {
        dropStack(new ItemStack(TropicItems.ASHEN_MASKS.get(AshenMasks.VALUES[getMaskType()])), 1.0F);
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

        Identifier SPAWN_PACKET = new Identifier(Tropicfabricport.MOD_ID, "ashen_mask");

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
