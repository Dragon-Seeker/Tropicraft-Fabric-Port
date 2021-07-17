package com.dragonseeker.tropicfabricport.entity;

import com.dragonseeker.tropicfabricport.registry.TropicEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WallItemEntity extends BambooItemFrameEntity {

    public WallItemEntity(EntityType<? extends BambooItemFrameEntity> entityType, World world) {
        super((EntityType<BambooItemFrameEntity>) entityType, world);
    }

	public WallItemEntity(World worldIn, BlockPos pos, Direction on) {
		super(TropicEntities.WALL_ITEM, worldIn, pos, on);
	}

	@Override
	public int getWidthPixels() {
		return 16;
	}

	@Override
	public int getHeightPixels() {
		return 16;
	}

    @Override
    public void invokedropHeldStack(@Nullable Entity entityIn, boolean p_146065_2_) {
    	super.invokedropHeldStack(entityIn, false);
    	this.remove();
    }

    @Override
    public void onPlace() {
    }

	/*
	@Override
	public ItemStack getPickedResult(HitResult target) {
		return getHeldItemStack();
	}

	 */
}
