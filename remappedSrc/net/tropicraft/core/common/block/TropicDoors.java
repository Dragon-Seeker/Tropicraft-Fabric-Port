package net.tropicraft.core.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;

public class TropicDoors extends DoorBlock {
    public TropicDoors(Block block) {
        super(FabricBlockSettings.copyOf(block).noOcclusion());
    }

    public TropicDoors(Block block, float baseHardness) {
        super(FabricBlockSettings.copyOf(block).noOcclusion().hardness(baseHardness));
    }
}
