package net.tropicraft.core.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.OreBlock;
import net.tropicraft.core.common.registry.TropicraftBlocks;

import java.util.Random;

public class TropicOreBlock extends OreBlock {
    public TropicOreBlock(Properties settings) {
        super(settings); //UniformIntProvider.create(0, 2)
    }

    public TropicOreBlock(Properties settings, UniformInt provider) {
        super(settings, provider); //UniformIntProvider.create(0, 2)
    }

}
