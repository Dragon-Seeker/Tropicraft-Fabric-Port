package net.tropicraftFabric.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.tropicraftFabric.common.entity.placeable.WallItemEntity;

public class ShellItem extends Item {

    public ShellItem(final Settings properties) {
        super(properties);
    }

    @Override
    public ActionResult useOnBlock(final ItemUsageContext context) {
        final Direction facing = context.getSide();
        final ItemStack stack = context.getPlayer().getStackInHand(context.getHand());
        final BlockPos pos = context.getBlockPos().offset(facing);

        // Must set the world coordinates here, or onValidSurface will be false.
        final World world = context.getWorld();
        final WallItemEntity hangingEntity = new WallItemEntity(world, pos, facing);
        hangingEntity.setHeldItemStack(stack);

        if (!context.getPlayer().canPlaceOn(pos, facing, stack)) {
            return ActionResult.FAIL;
        } else {
            if (hangingEntity.canStayAttached()) {
                if (!world.isClient) {
                    world.spawnEntity(hangingEntity);
                }

                stack.decrement(1);
            }

            return ActionResult.SUCCESS;
        }
    }
}
