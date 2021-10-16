package net.tropicraft.core.common.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.tropicraft.core.common.registry.TropicraftBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import java.util.Arrays;

public class Builder {


    private static MaterialColor SAND = MaterialColor.SAND;


    public static Block tropicBlock(Material baseMaterial, MaterialColor baseColor, float hardness, float resistance){
        return tropicBlock(FabricBlockSettings
                .of(baseMaterial)
                .materialColor(baseColor)
                .destroyTime(hardness)
                .explosionResistance(resistance));
    }

    public static Block tropicBlock(Material baseMaterial, MaterialColor baseColor, float hardness, float resistance, SoundType soundbase){
        return tropicBlock(FabricBlockSettings
                .of(baseMaterial)
                .materialColor(baseColor)
                .destroyTime(hardness)
                .explosionResistance(resistance)
                .sound(soundbase));
    }

    public static Block tropicBlock(BlockBehaviour.Properties settings){
        return new Block(FabricBlockSettings
                .copyOf(settings));
    }

    public static Block Fence(Block block){
        return new FenceBlock(FabricBlockSettings
                .copyOf(block).noOcclusion());
    }

    public static Block Stairs(Block block){
        return new TropicStairsBlock(block);
    }

    public static Block Slab(Block block){
        return new SlabBlock(FabricBlockSettings
                .copyOf(block));
    }

    public static Block FenceGate(Block block){
        return new FenceGateBlock(FabricBlockSettings
                .copyOf(block).noOcclusion());
    }

    public static Block MetalBlock(MaterialColor baseColor){
        return new Block(FabricBlockSettings
                .of(Material.METAL)
                .breakByTool(FabricToolTags.PICKAXES, 2)
                .materialColor(baseColor)
                .sound(SoundType.METAL)
                .destroyTime(5.0F)
                .explosionResistance(6.0F));
    }

    public static TropicOreBlock OreBlock(MaterialColor baseColor, UniformInt provider){
        return OreBlock(baseColor, 2, provider);
    }

    public static TropicOreBlock OreBlock(MaterialColor baseColor, int miningLevel){
        return OreBlock(baseColor, miningLevel, UniformInt.of(0,0));
    }

    public static TropicOreBlock OreBlock(MaterialColor baseColor, int miningLevel, UniformInt provider){
        return new TropicOreBlock(FabricBlockSettings
                .of(Material.STONE, baseColor)
                .breakByTool(FabricToolTags.PICKAXES, miningLevel)
                .destroyTime(3.0F)
                .explosionResistance(3.0F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE), provider);
    }

    public static Block Sand() {
        return Sand(SAND);
    }

    public static Block Sand(float hardness, float resistance) {
        return Sand(hardness, resistance, SAND);
    }

    public static Block Sand(MaterialColor baseColor){
        return Sand(0.5f, 0.5f, baseColor);
    }

    public static Block Sand(float hardness, float resistance, MaterialColor baseColor){
        return new TropicSand(FabricBlockSettings
                .of(Material.SAND)
                .materialColor(baseColor)
                .destroyTime(hardness)
                .explosionResistance(resistance)
                .sound(SoundType.SAND));
    }



    //LogBlock
    public static Block Log(Material baseMaterial, MaterialColor color1, MaterialColor color2, SoundType baseSound) {
        return new RotatedPillarBlock(FabricBlockSettings
                .of(baseMaterial, blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? color1 : color2)
                .strength(2.0F)
                .sound(baseSound));
    }

    //Plank Block
    public static Block Planks(Material baseMaterial, SoundType baseSound, float baseHardness, float baseResistance) {
        return new RotatedPillarBlock(FabricBlockSettings
                .of(baseMaterial)
                .destroyTime(baseHardness)
                .explosionResistance(baseResistance)
                .sound(baseSound));
    }

    //WoodBlock
    public static Block Bark(Material baseMaterial, SoundType baseSound) {
        return new RotatedPillarBlock(FabricBlockSettings
                .of(baseMaterial)
                .destroyTime(2.0F)
                .sound(baseSound));
    }

    public static Block Wall(Block block) {
        return new WallBlock(FabricBlockSettings.copyOf(block).noOcclusion());
    }

    //TropicBambooPot(Blocks.AIR, );
    public static TropicBambooPot BambooPot(Block content) {
        return new TropicBambooPot(content, FabricBlockSettings.copyOf(TropicraftBlocks.FlowerPot).noOcclusion());
    }


    public static TropicFlowerBlock flower(TropicFlowers type) {
        return new TropicFlowerBlock(type.getEffect(), type.getEffectDuration(), type.getShape(), FabricBlockSettings.copyOf(Blocks.POPPY));
    }


    public static TropicBambooPot tropicraftPot(final Block block) {
        return new TropicBambooPot(block, FabricBlockSettings.of(Material.DECORATION).strength(0.2F, 5.0F).sound(SoundType.BAMBOO));
    }

    public static FlowerPotBlock vanillaPot(final Block block) {
        return new FlowerPotBlock(block, FabricBlockSettings.copyOf(Blocks.FLOWER_POT));
    }

    @SafeVarargs
    public static SaplingBlock sapling(final AbstractTreeGrower tree, FabricBlockSettings settings, final Block... validPlantBlocks) {
        return new SaplingBlock(tree, settings) {
            protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
                final Block block = floor.getBlock();
                if (validPlantBlocks == null || validPlantBlocks.length == 0) {
                    return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.FARMLAND;
                } else {
                    return Arrays.stream(validPlantBlocks).anyMatch(b -> b == block);
                }
            }
        };
    }



}
