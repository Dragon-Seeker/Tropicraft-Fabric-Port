package net.tropicraft.core.common.item;

import net.tropicraft.core.common.entity.BambooItemFrameEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DecorationItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.tropicraft.core.common.registry.TropicraftEntities;


public class BambooItemFrameItem extends DecorationItem {
    public BambooItemFrameItem(FabricItemSettings settings) {
        // Game crashes if we pass to super, we don't need it anyway
        super(TropicraftEntities.BAMBOO_ITEM_FRAME, settings);
    }
    
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos blockpos = context.getBlockPos();
        Direction direction = context.getPlayerFacing();
        BlockPos blockpos1 = blockpos.offset(direction);
        PlayerEntity playerentity = context.getPlayer();
        ItemStack itemstack = context.getStack();
        if (playerentity != null && !this.canPlaceOn(playerentity, direction, itemstack, blockpos1)) {
           return ActionResult.FAIL;
        } else {
           World world = context.getWorld();
           BambooItemFrameEntity hangingentity = new BambooItemFrameEntity(world, blockpos1, direction);

            NbtCompound compoundtag = itemstack.getNbt();
           if (compoundtag != null) {
              EntityType.loadFromEntityNbt(world, playerentity, hangingentity, compoundtag);
           }

           if (hangingentity.canStayAttached()) {
              if (!world.isClient()) {
                 hangingentity.onPlace();//playPlaceSound
                 world.spawnEntity(hangingentity);
              }

              itemstack.decrement(1);
           }

           return ActionResult.SUCCESS;
        }
    }

    @Override
    protected boolean canPlaceOn(PlayerEntity player, Direction side, ItemStack stack, BlockPos pos) {
        return player.canPlaceOn(pos, side, stack) && !World.isOutOfBuildLimitVertically(pos);
    }

}