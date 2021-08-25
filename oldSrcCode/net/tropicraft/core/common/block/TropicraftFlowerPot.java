package net.tropicraft.core.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class TropicraftFlowerPot extends FlowerPotBlock {
    public TropicraftFlowerPot() {
        super(Blocks.AIR, FabricBlockSettings.of(Material.STONE).hardness(0.2F).resistance(5.0F).sounds(BlockSoundGroup.BAMBOO));
    }
}
