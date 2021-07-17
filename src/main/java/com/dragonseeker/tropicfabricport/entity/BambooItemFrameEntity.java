package com.dragonseeker.tropicfabricport.entity;

import com.dragonseeker.tropicfabricport.mixins.ItemFrameEntityInvoker;
import com.dragonseeker.tropicfabricport.registry.TropicEntities;
import com.dragonseeker.tropicfabricport.registry.TropicItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BambooItemFrameEntity extends ItemFrameEntity implements ItemFrameEntityInvoker {

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

	@Override
	public Packet<?> createSpawnPacket() {
		return new EntitySpawnS2CPacket(this, this.getType(), this.facing.getId(), this.getDecorationBlockPos());
    }

	/*
    @Override
    public void writeSpawnData(PacketByteBuf buffer) {
        buffer.writeBlockPos(this.attachmentPos);
        buffer.writeByte(facingDirection.getIndex());
    }

    @Override
    public void readSpawnData(PacketByteBuf additionalData) {
        this.attachmentPos = additionalData.readBlockPos();
        updateFacingWithBoundingBox(Direction.byIndex(additionalData.readByte()));
    }

	 */

	@Override
	public ItemStack getHeldItemStack() {
		return new ItemStack(TropicItems.BAMBOO_ITEM_FRAME);
	}
}
