package net.tropicraft.core.common.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.tropicraft.core.common.block.blockentity.DrinkMixerTileEntity;
import net.tropicraft.core.common.block.blockentity.SifterTileEntity;
import net.tropicraft.core.common.drinks.Drink;
import net.tropicraft.core.common.drinks.MixerRecipes;
import net.tropicraft.core.common.registry.TropicBlockEntities;
import net.tropicraft.core.common.registry.TropicraftItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DrinkMixerBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public DrinkMixerBlock(final Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @Environment(EnvType.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(new TranslatableComponent("!! Work In Progress !!").withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
        tooltip.add(new TranslatableComponent(getDescriptionId() + ".desc").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return null;
        //return checkType(type, TropicBlockEntities.SIFTER, (world1, pos, state1, be) -> SifterTileEntity.tick(world1, pos, state1, be));
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        ItemStack stack = player.getMainHandItem();

        DrinkMixerTileEntity mixer = (DrinkMixerTileEntity) world.getBlockEntity(pos);
        if (mixer == null) {
            return InteractionResult.FAIL;
        }

        if (mixer.isDoneMixing()) {
            mixer.retrieveResult(player);
            return InteractionResult.CONSUME;
        }

        if (stack.isEmpty()) {
            mixer.emptyMixer(player);
            return InteractionResult.CONSUME;
        }

        ItemStack ingredientStack = stack.copy();
        ingredientStack.setCount(1);

        if (mixer.addToMixer(ingredientStack)) {
            if (!player.isCreative()) {
                player.getInventory().removeItem(player.getInventory().selected, 1);
            }
        }

        if (ingredientStack.getItem() == TropicraftItems.BAMBOO_MUG && mixer.canMix()) {
            mixer.startMixing();
            if (!player.isCreative()) {
                player.getInventory().removeItem(player.getInventory().selected, 1);
            }

            Drink craftedDrink = MixerRecipes.getDrink(mixer.ingredients);
            Drink pinaColada = Drink.PINA_COLADA;

            if (craftedDrink != null && craftedDrink.drinkId == pinaColada.drinkId) {
                // TODO advancements entityPlayer.addStat(AchievementRegistry.craftPinaColada);
            }
        }

        return InteractionResult.CONSUME;
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState ret = super.getStateForPlacement(context);
        return ret.setValue(FACING, context.getPlayer().getDirection());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DrinkMixerTileEntity(pos, state);
    }
}
