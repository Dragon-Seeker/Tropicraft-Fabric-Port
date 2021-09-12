package net.tropicraft.core.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.tropicraft.core.common.block.blockentity.SifterTileEntity;
import org.jetbrains.annotations.Nullable;

public class SifterBlock extends Block implements BlockEntityProvider {

    public SifterBlock(final Settings properties) {
        super(properties);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult result) {
        final ItemStack stack = player.getStackInHand(hand);

        // TODO use item tag
        final boolean isSandInHand = Block.getBlockFromItem(stack.getItem()).getDefaultState().getMaterial() == Material.AGGREGATE;
        if (!isSandInHand) {
            return ActionResult.PASS;
        }

        if (!world.isClient) {
            final SifterTileEntity sifter = (SifterTileEntity) world.getBlockEntity(pos);
            if (sifter != null && !stack.isEmpty() && !sifter.isSifting()) {
                final ItemStack addItem;
                if (!player.isCreative()) {
                    addItem = stack.split(1);
                } else {
                    addItem = stack.copy().split(1);
                }
                sifter.addItemToSifter(addItem);

                sifter.startSifting();
                return ActionResult.CONSUME;
            }
        }

        return ActionResult.SUCCESS;
    } // /o/ \o\ /o\ \o\ /o\ \o/ /o/ /o/ \o\ \o\ /o/ /o/ \o/ /o\ \o/ \o/ /o\ /o\ \o/ \o/ /o/ \o\o\o\o\o\o\o\o\o\ :D

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SifterTileEntity(pos, state);
    }
}
