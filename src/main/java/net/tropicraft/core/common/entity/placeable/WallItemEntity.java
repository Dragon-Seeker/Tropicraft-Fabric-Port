package net.tropicraft.core.common.entity.placeable;

import net.tropicraft.Constants;
import net.tropicraft.core.common.entity.BambooItemFrameEntity;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.tropicraft.core.common.registry.TropicraftItems;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class WallItemEntity extends BambooItemFrameEntity {

	public static ResourceLocation SPAWN_PACKET = new ResourceLocation(Constants.MODID, "wall_item");

    public WallItemEntity(EntityType<? extends BambooItemFrameEntity> entityType, Level world) {
        super(entityType, world);
    }

	public WallItemEntity(Level worldIn, BlockPos pos, Direction on) {
		super(TropicraftEntities.WALL_ITEM, worldIn, pos, on);

	}

	@Environment(EnvType.CLIENT)
	public WallItemEntity(EntityType<? extends BambooItemFrameEntity> entityType, Level world, double x, double y, double z, Direction facing, int id, UUID uuid) {
		super(entityType, world, x, y, z, facing, id, uuid);
	}

	@Override
	public int getWidth() {
		return 16;
	}

	@Override
	public int getHeight() {
		return 16;
	}

	//@Override
	//protected ItemStack getAsItemStack() {
	//	return ItemStack.EMPTY;
	//}


	@Override
	protected void dropItem(@Nullable Entity entity, boolean alwaysDrop) {
		super.dropItem(entity, false);
		this.remove(RemovalReason.KILLED);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		//return new EntitySpawnS2CPacket(this, this.getType(), this.facing.getId(), this.getDecorationBlockPos());

		FriendlyByteBuf packet = new FriendlyByteBuf(Unpooled.buffer());

		// entity position
		packet.writeDouble(getX());
		packet.writeDouble(getY());
		packet.writeDouble(getZ());

		// entity id & uuid
		packet.writeInt(getId());
		packet.writeUUID(getUUID());

		//Entity Facing direction

		//packet.writeIdentifier(Registry.ITEM.getId(getAsItemStack().getItem()));

		packet.writeInt(getDirection().get3DDataValue());
		return ServerSidePacketRegistry.INSTANCE.toPacket(SPAWN_PACKET, packet);

	}

	/*
	@Override
	public ItemStack getPickedResult(HitResult target) {
		return getHeldItemStack();
	}

	 */
}
