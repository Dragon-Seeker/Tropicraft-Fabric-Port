package net.tropicraft.core.common.entity;

import net.tropicraft.Constants;
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.tropicraft.core.common.registry.TropicraftItems;
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
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BambooItemFrameEntity extends ItemFrame {
	public static ResourceLocation SPAWN_PACKET = new ResourceLocation(Constants.MODID, "bamboo_item_frame");

	public BambooItemFrameEntity(EntityType<? extends ItemFrame> bambooItemFrameEntityEntityType, Level world) {
		super(bambooItemFrameEntityEntityType, world);
	}

	public BambooItemFrameEntity(Level worldIn, BlockPos pos, Direction direction) {
		this(TropicraftEntities.BAMBOO_ITEM_FRAME, worldIn, pos, direction);
	}

	protected BambooItemFrameEntity(final EntityType<? extends ItemFrame> type, final Level world, final BlockPos pos, final Direction direction) {
		super(type, world, pos, direction);

	}

	@Environment(EnvType.CLIENT)
	public BambooItemFrameEntity(EntityType<? extends ItemFrame> entityType, Level world, double x, double y, double z, Direction facing, int id, UUID uuid) {
		super(entityType, world);
		absMoveTo(x, y, z);
		setPacketCoordinates(x, y, z);
		setId(id);
		setUUID(uuid);
		setDirection(facing);
	}

	@Override
	protected void dropItem(@Nullable Entity entity, boolean alwaysDrop) {
		super.dropItem(entity, false);
		if (alwaysDrop) {
			this.spawnAtLocation(TropicraftItems.BAMBOO_ITEM_FRAME);
		}
	}

	//@Override
	//protected ItemStack getAsItemStack() {
	//	return new ItemStack(TropicraftItems.BAMBOO_ITEM_FRAME);
	//}


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
		packet.writeInt(getDirection().get3DDataValue());

		return ServerSidePacketRegistry.INSTANCE.toPacket(SPAWN_PACKET, packet);

    }



}
