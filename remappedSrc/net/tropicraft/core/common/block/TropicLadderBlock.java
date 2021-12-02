package net.tropicraft.core.common.block;


import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;

public class TropicLadderBlock extends LadderBlock {
    public TropicLadderBlock() {
        super(FabricBlockSettings.copyOf(Blocks.BAMBOO));
    }
}
