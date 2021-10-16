package net.tropicraft.core.common.entity.placeable;

import net.tropicraft.Constants;
import net.tropicraft.core.common.entity.hostile.AshenEntity;
import net.tropicraft.core.common.item.AshenMasks;
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.tropicraft.core.common.registry.TropicraftItems;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import java.util.UUID;

public class AshenMaskEntity extends Entity {

    public static ResourceLocation SPAWN_PACKET = new ResourceLocation(Constants.MODID, "ashen_mask");

    private static final EntityDataAccessor<Byte> MASK_TYPE = SynchedEntityData.defineId(AshenEntity.class, EntityDataSerializers.BYTE);
    public static final int MAX_TICKS_ALIVE = 24000;

    public AshenMaskEntity(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Environment(EnvType.CLIENT)
    public AshenMaskEntity(Level world, double x, double y, double z, int id, UUID uuid) {
        super(TropicraftEntities.ASHEN_MASK, world);
        absMoveTo(x, y, z);
        setPacketCoordinates(x, y, z);
        setId(id);
        setUUID(uuid);
    }

    public void dropItemStack() {
        spawnAtLocation(new ItemStack(TropicraftItems.ASHEN_MASKS.get(AshenMasks.VALUES[getMaskType()])), 1.0F);
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
    protected void defineSynchedData() {
        entityData.define(MASK_TYPE, (byte) 0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        setMaskType(nbt.getByte("MaskType"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        nbt.putByte("MaskType", getMaskType());
    }

    @Override
    public Packet<?> getAddEntityPacket() {

        //return NetworkHooks.getEntitySpawningPacket(this);
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

    public void setMaskType(byte type) {
        entityData.set(MASK_TYPE, type);
    }

    public byte getMaskType() {
        return entityData.get(MASK_TYPE);
    }

    @Override
    public void tick() {
        if (!level.isClientSide) {
            // Remove masks that have been on the ground abandoned for over a day
            if (tickCount >= MAX_TICKS_ALIVE) {
                remove(RemovalReason.DISCARDED);
            }
        }

        final Vec3 motion = getDeltaMovement();

        if (onGround) {
            setDeltaMovement(motion.multiply(0.5, 0, 0.5));
        }

        if (isInWater()) {
            setDeltaMovement(motion.x * 0.95f, 0.02f, motion.z * 0.95f);
        } else {
            setDeltaMovement(motion.subtract(0, 0.05f, 0));
        }

        move(MoverType.SELF, motion);
    }

    @Override
    public boolean hurt(DamageSource damageSource, float par2) {
        if (isInvulnerableTo(damageSource)) {
            return false;
        } else {
            if (isAlive() && !level.isClientSide) {
                remove(RemovalReason.KILLED);
                markHurt();
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
