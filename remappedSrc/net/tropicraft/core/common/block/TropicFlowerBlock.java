package net.tropicraft.core.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TropicFlowerBlock extends FlowerBlock {
    protected static VoxelShape SHAPE;
    protected static Flower flower;
    public static MobEffect baseEffect;
    public int effectDuration;

    public TropicFlowerBlock(MobEffect baseEffect, int effectDuration, FabricBlockSettings Settings) {
        //super(suspiciousStewEffect, effectDuration, settings);
        this(baseEffect, effectDuration, 7, Settings);
    }

    public TropicFlowerBlock(MobEffect baseEffect, int effectDuration, int width, FabricBlockSettings Settings) {
        this(baseEffect, effectDuration, width, 15, Settings);
    }

    public TropicFlowerBlock(MobEffect baseEffect, int effectDuration, int width, int height, FabricBlockSettings Settings) {
        super(baseEffect, effectDuration, Settings);
        this.baseEffect = baseEffect;
        this.effectDuration = effectDuration;
        float halfW = width / 2f;
        this.SHAPE = Block.box(8 - halfW, 0, 8 - halfW, 8 + halfW, height, 8 + halfW);
    }

    public TropicFlowerBlock(MobEffect baseEffect, int effectDuration, VoxelShape shape, FabricBlockSettings Settings) {
        super(baseEffect, effectDuration, Settings);
        this.baseEffect = baseEffect;
        this.effectDuration = effectDuration;
        this.SHAPE = shape;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
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
