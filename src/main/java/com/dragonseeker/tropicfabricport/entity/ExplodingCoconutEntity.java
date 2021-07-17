package com.dragonseeker.tropicfabricport.entity;

import com.dragonseeker.tropicfabricport.Tropicfabricport;
import com.dragonseeker.tropicfabricport.registry.TropicEntities;
import com.dragonseeker.tropicfabricport.registry.TropicItems;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class ExplodingCoconutEntity extends ThrownItemEntity {
    
    public ExplodingCoconutEntity(EntityType<? extends ExplodingCoconutEntity> type, World world) {
        super(type, world);
    }
    
    public ExplodingCoconutEntity(World world, LivingEntity thrower) {
        super(TropicEntities.EXPLODING_COCONUT, thrower, world);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        //return new EntitySpawnS2CPacket(this);

        Identifier SPAWN_PACKET = new Identifier(Tropicfabricport.MOD_ID, "exploding_coconut");

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
	protected void onCollision(HitResult hitResult) {
		// TODO - why isn't this being called?
		if (!world.isClient) {
			world.createExplosion(this, getX(), getY(), getZ(), 2.4F, Explosion.DestructionType.DESTROY);
			remove();
		}
	}

    @Override
    protected Item getDefaultItem() {
        return TropicItems.EXPLODING_COCONUT;
    }
}
