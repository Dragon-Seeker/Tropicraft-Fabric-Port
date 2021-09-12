package net.tropicraft.core.common.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.tropicraft.core.common.block.blockentity.SifterTileEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SifterBlock extends Block implements BlockEntityProvider {

    public SifterBlock(final Settings properties) {
        super(properties);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView worldIn, List<Text> tooltip, TooltipContext flagIn) {
        super.appendTooltip(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableText("!! Work In Progress !!").formatted(Formatting.RED, Formatting.BOLD));

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
