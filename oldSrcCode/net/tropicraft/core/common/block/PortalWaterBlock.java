package net.tropicraft.core.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.tropicraft.core.common.dimension.TropicraftDimension;

import java.util.Random;

public class PortalWaterBlock extends FluidBlock {

    public PortalWaterBlock(Settings builder) {
        super(Fluids.WATER, builder);
    }



    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityCollision(state, worldIn, pos, entityIn);

        if (!worldIn.isClient && entityIn instanceof ServerPlayerEntity && entityIn.getMaxNetherPortalTime() <= 0 && !entityIn.hasVehicle() && !entityIn.hasPassengers() && entityIn.canUsePortals()) {
            TropicraftDimension.teleportPlayer((ServerPlayerEntity) entityIn, TropicraftDimension.WORLD);
        }
    }

    @Override
    public void randomDisplayTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(2) == 0) {
            double d0 = pos.getX();
            double d1 = pos.getY();
            double d2 = pos.getZ();
            
            worldIn.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, d0 + 0.5D, d1, d2 + 0.5D, 0.0D, 0.04D, 0.0D);
            worldIn.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, d0 + (double)rand.nextFloat(), d1 + (double)rand.nextFloat(), d2 + (double)rand.nextFloat(), 0.0D, 0.04D, 0.0D);
        }
    }
}
