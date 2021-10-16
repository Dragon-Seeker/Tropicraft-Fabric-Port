package net.tropicraft.core.common.entity;

import net.minecraft.item.Items;
import net.tropicraft.Constants;
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.tropicraft.core.common.registry.TropicraftItems;
import net.tropicraft.core.mixins.ItemFrameEntityInvoker;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BambooItemFrameEntity extends ItemFrameEntity implements ItemFrameEntityInvoker {
	public static Identifier SPAWN_PACKET = new Identifier(Constants.MODID, "bamboo_item_frame");

	public BambooItemFrameEntity(EntityType<? extends ItemFrameEntity> bambooItemFrameEntityEntityType, World world) {
		super(bambooItemFrameEntityEntityType, world);
	}

	public BambooItemFrameEntity(World worldIn, BlockPos pos, Direction direction) {
		this(TropicraftEntities.BAMBOO_ITEM_FRAME, worldIn, pos, direction);
	}

	protected BambooItemFrameEntity(final EntityType<? extends ItemFrameEntity> type, final World world, final BlockPos pos, final Direction direction) {
		super(type, world, pos, direction);

	}

	@Environment(EnvType.CLIENT)
	public BambooItemFrameEntity(EntityType<? extends ItemFrameEntity> entityType, World world, double x, double y, double z, Direction facing, int id, UUID uuid) {
		super(entityType, world);
		updatePosition(x, y, z);
		updateTrackedPosition(x, y, z);
		setId(id);
		setUuid(uuid);
		setFacing(facing);
	}

	@Override
	protected void dropHeldStack(@Nullable Entity entity, boolean alwaysDrop) {
		super.dropHeldStack(entity, false);
		if (alwaysDrop) {
			this.dropItem(TropicraftItems.BAMBOO_ITEM_FRAME);
		}
	}

	//@Override
	//protected ItemStack getAsItemStack() {
	//	return new ItemStack(TropicraftItems.BAMBOO_ITEM_FRAME);
	//}


	@Override
	public Packet<?> createSpawnPacket() {
		//return new EntitySpawnS2CPacket(this, this.getType(), this.facing.getId(), this.getDecorationBlockPos());

		PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());

		// entity position
		packet.writeDouble(getX());
		packet.writeDouble(getY());
		packet.writeDouble(getZ());

		// entity id & uuid
		packet.writeInt(getId());
		packet.writeUuid(getUuid());

		//Entity Facing direction
		packet.writeInt(getHorizontalFacing().getId());

		return ServerSidePacketRegistry.INSTANCE.toPacket(SPAWN_PACKET, packet);

    }



}
