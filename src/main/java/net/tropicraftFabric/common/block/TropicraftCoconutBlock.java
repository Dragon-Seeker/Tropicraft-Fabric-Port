package net.tropicraftFabric.common.block;


import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class TropicraftCoconutBlock extends Block {
    private static final VoxelShape COCONUT_AABB = Block.createCuboidShape(4, 0.0D, 4, 12, 10, 12);

    public TropicraftCoconutBlock() {
        super(FabricBlockSettings.of(Material.GOURD).hardness(2.0f).breakByTool(FabricToolTags.AXES).sounds(BlockSoundGroup.STONE));

    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COCONUT_AABB;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, final BlockPos pos, ShapeContext context) {
        return COCONUT_AABB;
    }

}
