package net.tropicraftFabric.common.entity.hostile;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class TropicraftCreatureEntity extends PathAwareEntity {

    public TropicraftCreatureEntity(EntityType<? extends PathAwareEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView worldIn) {
        return worldIn.getBlockState(pos.down()).getBlock() == Blocks.GRASS_BLOCK ? 10.0F : worldIn.getBrightness(pos) - 0.5F;
    }
}
