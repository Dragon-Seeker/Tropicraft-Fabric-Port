package net.tropicraftFabric.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.sound.BlockSoundGroup;

public class TropicOreBlock extends OreBlock {


    public TropicOreBlock() {
        super(FabricBlockSettings
                .of(Material.STONE, MapColor.PALE_YELLOW)//Sand Color
                .hardness(3F)
                .resistance(9F)
                .requiresTool().breakByTool(FabricToolTags.PICKAXES, 2)
                .sounds(BlockSoundGroup.STONE));
    }

    public TropicOreBlock(MapColor baseColor) {
        super(FabricBlockSettings
                .of(Material.STONE, baseColor)
                .hardness(3.0F)
                .resistance(3.0F)
                .requiresTool().breakByTool(FabricToolTags.PICKAXES, 2)
                .sounds(BlockSoundGroup.STONE));
    }

    public TropicOreBlock(MapColor baseColor, float hardness, float resistance) {
        super(FabricBlockSettings
                .of(Material.STONE, baseColor)
                .hardness(hardness)
                .resistance(resistance)
                .requiresTool().breakByTool(FabricToolTags.PICKAXES, 2)
                .sounds(BlockSoundGroup.STONE));
    }




}
