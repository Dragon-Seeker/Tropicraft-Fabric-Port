package com.dragonseeker.tropicfabricport.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class TropicraftCreatureEntity extends PathAwareEntity {

    public TropicraftCreatureEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos.down()).getBlock() == Blocks.GRASS_BLOCK ? 10.0F : world.getBrightness(pos) - 0.5F;
    }



}
