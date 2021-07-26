package net.tropicraftFabric.common.block.plants;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.sound.BlockSoundGroup;

public class TropicTallPlant extends TallFlowerBlock {
    public TropicTallPlant() {
        super(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS));
    }
}
