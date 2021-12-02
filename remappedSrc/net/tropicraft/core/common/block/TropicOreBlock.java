package net.tropicraft.core.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import net.tropicraft.core.common.registry.TropicraftBlocks;

import java.util.Random;

public class TropicOreBlock extends OreBlock {

    @Override
    protected int xpOnDrop(Random random) {
        if (this == TropicraftBlocks.AZURITE_ORE) { //Coal
            return Mth.nextInt(random, 0, 2);
        } else if (this == Blocks.DIAMOND_ORE) {
            return Mth.nextInt(random, 3, 7);
        } else if (this == Blocks.EMERALD_ORE) {
            return Mth.nextInt(random, 3, 7);
        } else if (this == Blocks.LAPIS_ORE) {
            return Mth.nextInt(random, 2, 5);
        } else if (this == Blocks.NETHER_QUARTZ_ORE) {
            return Mth.nextInt(random, 2, 5);
        } else {
            return this == Blocks.NETHER_GOLD_ORE ? Mth.nextInt(random, 0, 1) : 0;
        }
    }

    public TropicOreBlock(FabricBlockSettings settings) {
        super(settings);
    }

}
