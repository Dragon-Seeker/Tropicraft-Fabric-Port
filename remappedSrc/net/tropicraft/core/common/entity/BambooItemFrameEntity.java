package net.tropicraft.core.common.entity;

import net.tropicraft.Constants;
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.tropicraft.core.common.registry.TropicraftItems;
import net.tropicraft.core.mixins.ItemFrameEntityInvoker;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import java.util.UUID;

public class BambooItemFrameEntity extends ItemFrame implements ItemFrameEntityInvoker {

	public static ResourceLocation SPAWN_PACKET = new ResourceLocation(Constants.MODID, "bamboo_item_frame");

	public BambooItemFrameEntity(Level worldIn, BlockPos pos, Direction direction) {
		this(TropicraftEntities.BAMBOO_ITEM_FRAME, worldIn, pos, direction);
	}

	protected BambooItemFrameEntity(final EntityType<? extends ItemFrame> type, final Level world, final BlockPos pos,
									final Direction direction) {
		super(type, world);
		this.pos = pos;
		this.setDirection(direction);
	}

	public BambooItemFrameEntity(EntityType<BambooItemFrameEntity> bambooItemFrameEntityEntityType, Level world) {
		super(bambooItemFrameEntityEntityType, world);
	}

	@Environment(EnvType.CLIENT)
	public BambooItemFrameEntity(Level world, double x, double y, double z, Direction facing, int id, UUID uuid) {
		super(TropicraftEntities.BAMBOO_ITEM_FRAME, world);
		absMoveTo(x, y, z);
		setPacketCoordinates(x, y, z);
		setId(id);
		setUUID(uuid);
		setDirection(facing);
	}

	/*
	@Override
	public void dropHeldStack(Entity entityIn, boolean alwaysDrop) {
		alwaysDrop = false;
		if (alwaysDrop) {
			this.dropItem(TropicItems.BAMBOO_ITEM_FRAME);
		} else {
			BambooItemFrameAccessor.dropHeldStackInvoker(entityIn, false);//dropItemOrSelf
		}
	}

	 */

	@Override
	public void invokedropHeldStack(Entity entityIn, boolean dropSelf) {
		((ItemFrameEntityInvoker) this).invokedropHeldStack(entityIn, false); //??? TODO: Check if this is working
		if (dropSelf) {
			this.spawnAtLocation(TropicraftItems.BAMBOO_ITEM_FRAME);
		}
	}

	/*
	@Override
	@Nullable
	public ItemEntity dropItem(ItemConvertible item) {
		if(item == Items.ITEM_FRAME){
			return this.dropItem(TropicItems.BAMBOO_ITEM_FRAME, 0);
		}
		else{
			return this.dropItem(item, 0);
		}
	}
	 */

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

	@Override
	public ItemStack getItem() {
		return new ItemStack(TropicraftItems.BAMBOO_ITEM_FRAME);
	}
}
