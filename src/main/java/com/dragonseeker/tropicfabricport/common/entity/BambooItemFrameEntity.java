package com.dragonseeker.tropicfabricport.common.entity;

import com.dragonseeker.tropicfabricport.Tropicfabricport;
import com.dragonseeker.tropicfabricport.mixins.ItemFrameEntityInvoker;
import com.dragonseeker.tropicfabricport.common.registry.TropicEntities;
import com.dragonseeker.tropicfabricport.common.registry.TropicItems;
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

import java.util.UUID;

public class BambooItemFrameEntity extends ItemFrameEntity implements ItemFrameEntityInvoker {

	public static Identifier SPAWN_PACKET = new Identifier(Tropicfabricport.MOD_ID, "bamboo_item_frame");

	public BambooItemFrameEntity(World worldIn, BlockPos pos, Direction direction) {
		this(TropicEntities.BAMBOO_ITEM_FRAME, worldIn, pos, direction);
	}

	protected BambooItemFrameEntity(final EntityType<? extends ItemFrameEntity> type, final World world, final BlockPos pos,
									final Direction direction) {
		super(type, world);
		this.attachmentPos = pos;
		this.setFacing(direction);
	}

	public BambooItemFrameEntity(EntityType<BambooItemFrameEntity> bambooItemFrameEntityEntityType, World world) {
		super(bambooItemFrameEntityEntityType, world);
	}

	@Environment(EnvType.CLIENT)
	public BambooItemFrameEntity(World world, double x, double y, double z, Direction facing, int id, UUID uuid) {
		super(TropicEntities.BAMBOO_ITEM_FRAME, world);
		updatePosition(x, y, z);
		updateTrackedPosition(x, y, z);
		setEntityId(id);
		setUuid(uuid);
		setFacing(facing);
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
			this.dropItem(TropicItems.BAMBOO_ITEM_FRAME);
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
	public Packet<?> createSpawnPacket() {
		//return new EntitySpawnS2CPacket(this, this.getType(), this.facing.getId(), this.getDecorationBlockPos());

		PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());

		// entity position
		packet.writeDouble(getX());
		packet.writeDouble(getY());
		packet.writeDouble(getZ());

		// entity id & uuid
		packet.writeInt(getEntityId());
		packet.writeUuid(getUuid());

		//Entity Facing direction
		packet.writeInt(getHorizontalFacing().getId());

		return ServerSidePacketRegistry.INSTANCE.toPacket(SPAWN_PACKET, packet);

    }

	@Override
	public ItemStack getHeldItemStack() {
		return new ItemStack(TropicItems.BAMBOO_ITEM_FRAME);
	}
}
