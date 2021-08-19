package net.tropicraftFabric.common.block;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.tropicraftFabric.common.registry.TropicraftBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;

import java.util.Arrays;
import java.util.function.Supplier;

public class Builder {


    private static MapColor SAND = MapColor.PALE_YELLOW;


    public static Block tropicBlock(Material baseMaterial, MapColor baseColor, float hardness, float resistance){
        return tropicBlock(FabricBlockSettings
                .of(baseMaterial)
                .materialColor(baseColor)
                .hardness(hardness)
                .resistance(resistance));
    }

    public static Block tropicBlock(Material baseMaterial, MapColor baseColor, float hardness, float resistance, BlockSoundGroup soundbase){
        return tropicBlock(FabricBlockSettings
                .of(baseMaterial)
                .materialColor(baseColor)
                .hardness(hardness)
                .resistance(resistance)
                .sounds(soundbase));
    }

    public static Block tropicBlock(FabricBlockSettings settings){
        return new Block(FabricBlockSettings
                .copyOf(settings));
    }

    public static Block Fence(Block block){
        return new FenceBlock(FabricBlockSettings
                .copyOf(block).nonOpaque());
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
                .copyOf(block).nonOpaque());
    }

    public static Block MetalBlock(MapColor baseColor){
        return new Block(FabricBlockSettings
                .of(Material.METAL)
                .materialColor(baseColor)
                .sounds(BlockSoundGroup.METAL)
                .hardness(5.0F)
                .resistance(6.0F)
                .breakByTool(FabricToolTags.PICKAXES, 2));
    }

    public static TropicOreBlock OreBlock(MapColor baseColor, int miningLevel){
        return new TropicOreBlock(FabricBlockSettings
                .of(Material.STONE, baseColor)
                .hardness(3.0F)
                .resistance(3.0F)
                .requiresTool().breakByTool(FabricToolTags.PICKAXES, miningLevel)
                .sounds(BlockSoundGroup.STONE));
    }

    public static TropicOreBlock OreBlock(MapColor baseColor){
        return OreBlock(baseColor, 2);
    }



    public static Block Sand() {
        return Sand(SAND);
    }

    public static Block Sand(float hardness, float resistance) {
        return Sand(hardness, resistance, SAND);
    }

    public static Block Sand(MapColor baseColor){
        return Sand(0.5f, 0.5f, baseColor);
    }

    public static Block Sand(float hardness, float resistance, MapColor baseColor){
        return new TropicSand(FabricBlockSettings
                .of(Material.AGGREGATE)
                .materialColor(baseColor)
                .hardness(hardness)
                .resistance(resistance)
                .sounds(BlockSoundGroup.SAND));
    }



    //LogBlock
    public static Block Log(Material baseMaterial, MapColor color1, MapColor color2, BlockSoundGroup baseSound) {
        return new PillarBlock(FabricBlockSettings
                .of(baseMaterial, blockState -> blockState.get(PillarBlock.AXIS) == Direction.Axis.Y ? color1 : color2)
                .strength(2.0F)
                .sounds(baseSound));
    }

    //Plank Block
    public static Block Planks(Material baseMaterial, BlockSoundGroup baseSound, float baseHardness, float baseResistance) {
        return new PillarBlock(FabricBlockSettings
                .of(baseMaterial)
                .hardness(baseHardness)
                .resistance(baseResistance)
                .sounds(baseSound));
    }

    //WoodBlock
    public static Block Bark(Material baseMaterial, BlockSoundGroup baseSound) {
        return new PillarBlock(FabricBlockSettings
                .of(baseMaterial)
                .hardness(2.0F)
                .sounds(baseSound));
    }

    public static Block Wall(Block block) {
        return new WallBlock(FabricBlockSettings.copyOf(block).nonOpaque());
    }

    //TropicBambooPot(Blocks.AIR, );
    public static TropicBambooPot BambooPot(Block content) {
        return new TropicBambooPot(content, FabricBlockSettings.copyOf(TropicraftBlocks.FlowerPot).nonOpaque());
    }


    public static TropicFlowerBlock flower(TropicFlowers type) {
        return new TropicFlowerBlock(type.getEffect(), type.getEffectDuration(), type.getShape(), FabricBlockSettings.copyOf(Blocks.POPPY));
    }


    public static TropicBambooPot tropicraftPot(final Block block) {
        return new TropicBambooPot(block, FabricBlockSettings.of(Material.DECORATION).strength(0.2F, 5.0F).sounds(BlockSoundGroup.BAMBOO));
    }

    public static FlowerPotBlock vanillaPot(final Block block) {
        return new FlowerPotBlock(block, FabricBlockSettings.copyOf(Blocks.FLOWER_POT));
    }

    @SafeVarargs
    public static SaplingBlock sapling(final SaplingGenerator tree, FabricBlockSettings settings, final Block... validPlantBlocks) {
        return new SaplingBlock(tree, settings) {
            protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
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
