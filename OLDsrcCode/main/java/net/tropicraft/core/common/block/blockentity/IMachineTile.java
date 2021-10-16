package net.tropicraft.core.common.block.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public interface IMachineTile {
    
    boolean isActive();
    
    float getProgress(float partialTicks);
    
    Direction getDirection(BlockState state);

}
