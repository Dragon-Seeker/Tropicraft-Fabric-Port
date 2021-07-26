package net.tropicraftFabric.common.entity.projectile;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.tropicraftFabric.Constants;
import net.tropicraftFabric.common.registry.TropicraftEntities;

import java.util.UUID;

public class PoisonBlotEntity extends ThrownEntity {

    public static Identifier SPAWN_PACKET = new Identifier(Constants.MODID, "poison_blot");

    public PoisonBlotEntity(EntityType<? extends ThrownEntity> type, World world) {
        super(type, world);
    }

    public PoisonBlotEntity(EntityType<? extends ThrownEntity> type, LivingEntity thrower, World world) {
        super(type, thrower, world);
    }

    @Environment(EnvType.CLIENT)
    public PoisonBlotEntity(World world, double x, double y, double z, int id, UUID uuid) {
        super(TropicraftEntities.POISON_BLOT, world);
        updatePosition(x, y, z);
        updateTrackedPosition(x, y, z);
        setEntityId(id);
        setUuid(uuid);
    }

    @Override
    protected void onCollision(HitResult result) {
        if (result.getType() == HitResult.Type.ENTITY) {
            final Entity entity = ((EntityHitResult) result).getEntity();

            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 12 * 20, 0));
                remove();
            }
        }
    }

    @Override
    protected void initDataTracker() {

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
}
