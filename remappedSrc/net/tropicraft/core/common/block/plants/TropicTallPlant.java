package net.tropicraft.core.common.block.plants;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.material.Material;

public class TropicTallPlant extends TallFlowerBlock {
    public TropicTallPlant() {
        super(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollission().instabreak().sound(SoundType.GRASS));
    }
}
