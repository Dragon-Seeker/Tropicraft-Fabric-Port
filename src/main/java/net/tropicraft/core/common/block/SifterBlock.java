package net.tropicraft.core.common.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.tropicraft.core.common.block.blockentity.SifterTileEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SifterBlock extends Block implements EntityBlock {

    public SifterBlock(final Properties properties) {
        super(properties);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent("!! Work In Progress !!").withStyle(ChatFormatting.RED, ChatFormatting.BOLD));

    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        final ItemStack stack = player.getItemInHand(hand);

        // TODO use item tag
        final boolean isSandInHand = Block.byItem(stack.getItem()).defaultBlockState().getMaterial() == Material.SAND;
        if (!isSandInHand) {
            return InteractionResult.PASS;
        }

        if (!world.isClientSide) {
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
                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.SUCCESS;
    } // /o/ \o\ /o\ \o\ /o\ \o/ /o/ /o/ \o\ \o\ /o/ /o/ \o/ /o\ \o/ \o/ /o\ /o\ \o/ \o/ /o/ \o\o\o\o\o\o\o\o\o\ :D

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SifterTileEntity(pos, state);
    }
}
