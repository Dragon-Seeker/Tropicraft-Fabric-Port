package net.tropicraft.core.common.entity.projectile;

import net.tropicraft.Constants;
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.tropicraft.core.common.registry.TropicraftItems;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import java.util.UUID;

public class ExplodingCoconutEntity extends ThrowableItemProjectile {

    public static ResourceLocation SPAWN_PACKET = new ResourceLocation(Constants.MODID, "exploding_coconut");
    
    public ExplodingCoconutEntity(EntityType<? extends ExplodingCoconutEntity> type, Level world) {
        super(type, world);
    }
    
    public ExplodingCoconutEntity(Level world, LivingEntity thrower) {
        super(TropicraftEntities.EXPLODING_COCONUT, thrower, world);
    }

    @Environment(EnvType.CLIENT)
    public ExplodingCoconutEntity(Level world, double x, double y, double z, int id, UUID uuid) {
        super(TropicraftEntities.EXPLODING_COCONUT, world);
        absMoveTo(x, y, z);
        setPacketCoordinates(x, y, z);
        setId(id);
        setUUID(uuid);
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

	@Override
	protected void onHit(HitResult hitResult) {
		// TODO - why isn't this being called?
		if (!level.isClientSide) {
			level.explode(this, getX(), getY(), getZ(), 2.4F, Explosion.BlockInteraction.DESTROY);
			remove();
		}
	}

    @Override
    protected Item getDefaultItem() {
        return TropicraftItems.EXPLODING_COCONUT;
    }
}
