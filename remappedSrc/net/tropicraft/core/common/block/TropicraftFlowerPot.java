package net.tropicraft.core.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class TropicraftFlowerPot extends FlowerPotBlock {
    public TropicraftFlowerPot() {
        super(Blocks.AIR, FabricBlockSettings.of(Material.STONE).hardness(0.2F).resistance(5.0F).sound(SoundType.BAMBOO));
    }
}
