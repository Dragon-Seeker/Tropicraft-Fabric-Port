package net.tropicraft.core.common.item;

import net.tropicraft.core.common.entity.BambooItemFrameEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HangingEntityItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.tropicraft.core.common.registry.TropicraftEntities;


public class BambooItemFrameItem extends HangingEntityItem {
    public BambooItemFrameItem(Properties settings) {
        // Game crashes if we pass to super, we don't need it anyway
        super(TropicraftEntities.BAMBOO_ITEM_FRAME, settings);
    }
    
    @Override
    public InteractionResult useOn(UseOnContext context) {
        final Direction direction = context.getClickedFace();
        final BlockPos blockpos = context.getClickedPos().relative(direction);
        final Player playerentity = context.getPlayer();
        final ItemStack itemstack = context.getItemInHand();

        if (playerentity != null && !this.mayPlace(playerentity, direction, itemstack, blockpos)) {
           return InteractionResult.FAIL;
        } else {
           Level world = context.getLevel();
           BambooItemFrameEntity hangingentity = new BambooItemFrameEntity(world, blockpos, direction);

            CompoundTag compoundtag = itemstack.getTag();
           if (compoundtag != null) {
              EntityType.updateCustomEntityTag(world, playerentity, hangingentity, compoundtag);
           }

           if (hangingentity.survives()) {
              if (!world.isClientSide()) {
                 hangingentity.playPlacementSound();//playPlaceSound
                 world.addFreshEntity(hangingentity);
              }

              itemstack.shrink(1);
           }

           return InteractionResult.SUCCESS;
        }
    }

    @Override
    protected boolean mayPlace(Player player, Direction side, ItemStack stack, BlockPos pos) {
        return player.mayUseItemAt(pos, side, stack) && !player.level.isOutsideBuildHeight(pos);
    }

}