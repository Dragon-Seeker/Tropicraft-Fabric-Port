package net.tropicraft.core.common.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.tropicraft.core.common.block.blockentity.DrinkMixerTileEntity;
import net.tropicraft.core.common.block.blockentity.SifterTileEntity;
import net.tropicraft.core.common.drinks.Drink;
import net.tropicraft.core.common.drinks.MixerRecipes;
import net.tropicraft.core.common.registry.TropicBlockEntities;
import net.tropicraft.core.common.registry.TropicraftItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DrinkMixerBlock extends Block implements BlockEntityProvider {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public DrinkMixerBlock(final Settings properties) {
        super(properties);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(final StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext flag) {
        super.appendTooltip(stack, world, tooltip, flag);
        tooltip.add(new TranslatableText(getTranslationKey() + ".desc").formatted(Formatting.GRAY));
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return null;
        //return checkType(type, TropicBlockEntities.SIFTER, (world1, pos, state1, be) -> SifterTileEntity.tick(world1, pos, state1, be));
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        ItemStack stack = player.getMainHandStack();

        DrinkMixerTileEntity mixer = (DrinkMixerTileEntity) world.getBlockEntity(pos);
        if (mixer == null) {
            return ActionResult.FAIL;
        }

        if (mixer.isDoneMixing()) {
            mixer.retrieveResult(player);
            return ActionResult.CONSUME;
        }

        if (stack.isEmpty()) {
            mixer.emptyMixer(player);
            return ActionResult.CONSUME;
        }

        ItemStack ingredientStack = stack.copy();
        ingredientStack.setCount(1);

        if (mixer.addToMixer(ingredientStack)) {
            if (!player.isCreative()) {
                player.getInventory().removeStack(player.getInventory().selectedSlot, 1);
            }
        }

        if (ingredientStack.getItem() == TropicraftItems.BAMBOO_MUG && mixer.canMix()) {
            mixer.startMixing();
            if (!player.isCreative()) {
                player.getInventory().removeStack(player.getInventory().selectedSlot, 1);
            }

            Drink craftedDrink = MixerRecipes.getDrink(mixer.ingredients);
            Drink pinaColada = Drink.PINA_COLADA;

            if (craftedDrink != null && craftedDrink.drinkId == pinaColada.drinkId) {
                // TODO advancements entityPlayer.addStat(AchievementRegistry.craftPinaColada);
            }
        }

        return ActionResult.CONSUME;
    }
    
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockState ret = super.getPlacementState(context);
        return ret.with(FACING, context.getPlayer().getHorizontalFacing());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DrinkMixerTileEntity(pos, state);
    }
}
