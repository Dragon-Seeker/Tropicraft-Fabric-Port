package net.tropicraft.core.common.entity.projectile;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.tropicraft.Constants;
import net.tropicraft.core.common.registry.TropicraftEntities;

import java.util.UUID;

public class PoisonBlotEntity extends ThrowableProjectile {

    public static ResourceLocation SPAWN_PACKET = new ResourceLocation(Constants.MODID, "poison_blot");

    public PoisonBlotEntity(EntityType<? extends ThrowableProjectile> type, Level world) {
        super(type, world);
    }

    public PoisonBlotEntity(EntityType<? extends ThrowableProjectile> type, LivingEntity thrower, Level world) {
        super(type, thrower, world);
    }

    @Environment(EnvType.CLIENT)
    public PoisonBlotEntity(Level world, double x, double y, double z, int id, UUID uuid) {
        super(TropicraftEntities.POISON_BLOT, world);
        absMoveTo(x, y, z);
        setPacketCoordinates(x, y, z);
        setId(id);
        setUUID(uuid);
    }

    @Override
    protected void onHit(HitResult result) {
        if (result.getType() == HitResult.Type.ENTITY) {
            final Entity entity = ((EntityHitResult) result).getEntity();

            if (entity instanceof Player) {
                Player player = (Player) entity;
                player.addEffect(new MobEffectInstance(MobEffects.POISON, 12 * 20, 0));
                remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    protected void defineSynchedData() {

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
}
