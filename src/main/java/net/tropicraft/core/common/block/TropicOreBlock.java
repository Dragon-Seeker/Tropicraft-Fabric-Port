package net.tropicraft.core.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.util.math.MathHelper;
import net.tropicraft.core.common.registry.TropicraftBlocks;

import java.util.Random;

public class TropicOreBlock extends OreBlock {

    @Override
    protected int getExperienceWhenMined(Random random) {
        if (this == TropicraftBlocks.AZURITE_ORE) { //Coal
            return MathHelper.nextInt(random, 0, 2);
        } else if (this == Blocks.DIAMOND_ORE) {
            return MathHelper.nextInt(random, 3, 7);
        } else if (this == Blocks.EMERALD_ORE) {
            return MathHelper.nextInt(random, 3, 7);
        } else if (this == Blocks.LAPIS_ORE) {
            return MathHelper.nextInt(random, 2, 5);
        } else if (this == Blocks.NETHER_QUARTZ_ORE) {
            return MathHelper.nextInt(random, 2, 5);
        } else {
            return this == Blocks.NETHER_GOLD_ORE ? MathHelper.nextInt(random, 0, 1) : 0;
        }
    }

    public TropicOreBlock(FabricBlockSettings settings) {
        super(settings);
    }

}
