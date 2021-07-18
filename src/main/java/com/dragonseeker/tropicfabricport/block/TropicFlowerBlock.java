package com.dragonseeker.tropicfabricport.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class TropicFlowerBlock extends FlowerBlock {
    protected static VoxelShape SHAPE;
    protected static Flower flower;
    public static StatusEffect baseEffect;
    public int effectDuration;

    public TropicFlowerBlock(StatusEffect baseEffect, int effectDuration, FabricBlockSettings Settings) {
        //super(suspiciousStewEffect, effectDuration, settings);
        this(baseEffect, effectDuration, 7, Settings);
    }

    public TropicFlowerBlock(StatusEffect baseEffect, int effectDuration, int width, FabricBlockSettings Settings) {
        this(baseEffect, effectDuration, width, 15, Settings);
    }

    public TropicFlowerBlock(StatusEffect baseEffect, int effectDuration, int width, int height, FabricBlockSettings Settings) {
        super(baseEffect, effectDuration, Settings);
        this.baseEffect = baseEffect;
        this.effectDuration = effectDuration;
        float halfW = width / 2f;
        this.SHAPE = Block.createCuboidShape(8 - halfW, 0, 8 - halfW, 8 + halfW, height, 8 + halfW);
    }

    public TropicFlowerBlock(StatusEffect baseEffect, int effectDuration, VoxelShape shape, FabricBlockSettings Settings) {
        super(baseEffect, effectDuration, Settings);
        this.baseEffect = baseEffect;
        this.effectDuration = effectDuration;
        this.SHAPE = shape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    /*
    @Override
    public MutableText getName() {
        if(flower.getId() != null){
            return (MutableText) Text.of(flower.getId());
        }
        else{
            return super.getName();
        }

    }
     */
}
