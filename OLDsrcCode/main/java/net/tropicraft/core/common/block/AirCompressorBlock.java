package net.tropicraft.core.common.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.EnumProperty;
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
import net.tropicraft.core.common.block.blockentity.AirCompressorTileEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
import java.util.List;

public class AirCompressorBlock extends Block implements BlockEntityProvider {

    @NotNull
    public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;

    public AirCompressorBlock(FabricBlockSettings properties) {
        super(properties);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView worldIn, List<Text> tooltip, TooltipContext flagIn) {
        super.appendTooltip(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableText("!! Work In Progress !!").formatted(Formatting.RED, Formatting.BOLD));
        tooltip.add(new TranslatableText(getTranslationKey() + ".desc").formatted(Formatting.GRAY));

    }

    @Override
    protected void appendProperties(Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    /*
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        ItemStack stack = player.getMainHandStack();

        AirCompressorTileEntity mixer = (AirCompressorTileEntity)world.getBlockEntity(pos);

        if (mixer.isDoneCompressing()) {
            mixer.ejectTank();
            return ActionResult.CONSUME;
        }

        if (stack.isEmpty()) {
            mixer.ejectTank();
            return ActionResult.CONSUME;
        }

        ItemStack ingredientStack = stack.copy();
        ingredientStack.setCount(1);

        if (mixer.addTank(ingredientStack)) {
            player.getInventory().removeStack(player.getInventory().selectedSlot, 1);
        }

        return ActionResult.CONSUME;
    }
     */

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!world.isClient) {
            AirCompressorTileEntity te = (AirCompressorTileEntity) world.getBlockEntity(pos);
            //te.ejectTank();
        }

        super.onStateReplaced(state, world, pos, newState, isMoving);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockState ret = super.getPlacementState(context);
        return ret.with(FACING, context.getPlayerFacing());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AirCompressorTileEntity(pos, state);
    }
}
