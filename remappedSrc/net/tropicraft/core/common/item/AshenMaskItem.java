package net.tropicraft.core.common.item;

import net.tropicraft.core.common.entity.placeable.WallItemEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class AshenMaskItem extends ArmorItem {
    public final AshenMasks maskType;

    public AshenMaskItem(ArmorMaterial armorMaterial, AshenMasks maskType, FabricItemSettings settings) {
        super(armorMaterial, EquipmentSlot.HEAD, settings);
        this.maskType = maskType;
    }

    public AshenMasks getMaskType() {
        return maskType;
    }

    /**
     * Called when this item is used when targetting a Block
     */
    @Override //Dont know if It needs to be overridden
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Direction direction = context.getHorizontalDirection();
        BlockPos offsetPos = pos.relative(direction);
        Player player = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();
        if (player != null && !this.canPlace(player, direction, itemStack, offsetPos)) {
            return InteractionResult.FAIL;
        } else {
            Level world = context.getLevel();
            WallItemEntity wallItem = new WallItemEntity(world, offsetPos, direction);
            wallItem.setItem(itemStack);

            if (wallItem.survives()) {
                if (!world.isClientSide) {
                    wallItem.playPlacementSound();
                    world.addFreshEntity(wallItem);
                }

                itemStack.shrink(1);
            }

            return InteractionResult.SUCCESS;
        }
    }

    private boolean canPlace(Player player, Direction direction, ItemStack heldStack, BlockPos pos) {
        return player.mayUseItemAt(pos, direction, heldStack); //canPlayerEdit()
	}



	/*
	@OnlyIn(Dist.CLIENT)
	@Nullable
	@Override
	public BipedEntityModel getArmorModel(final LivingEntity entityLiving, final ItemStack itemStack, final EquipmentSlot armorSlot, final BipedEntityModel model) {
		return armorSlot == EquipmentSlot.HEAD ? new PlayerHeadpieceRenderer(maskType.ordinal(), maskType.getXOffset(), maskType.getYOffset()) : null;
	}

	@Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
    	return TropicraftRenderUtils.getTextureEntity("ashen/mask").toString();
    }
	 */
}
