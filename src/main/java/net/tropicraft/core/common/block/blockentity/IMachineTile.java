package net.tropicraft.core.common.block.blockentity;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public interface IMachineTile {
    
    boolean isActive();
    
    float getProgress(float partialTicks);
    
    Direction getDirection(BlockState state);

}
