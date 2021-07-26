package net.tropicraftFabric.common.item;

import net.tropicraftFabric.common.entity.placeable.WallItemEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

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
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        Direction direction = context.getPlayerFacing();
        BlockPos offsetPos = pos.offset(direction);
        PlayerEntity player = context.getPlayer();
        ItemStack itemStack = context.getStack();
        if (player != null && !this.canPlace(player, direction, itemStack, offsetPos)) {
            return ActionResult.FAIL;
        } else {
            World world = context.getWorld();
            WallItemEntity wallItem = new WallItemEntity(world, offsetPos, direction);
            wallItem.setHeldItemStack(itemStack);

            if (wallItem.canStayAttached()) {
                if (!world.isClient) {
                    wallItem.onPlace();
                    world.spawnEntity(wallItem);
                }

                itemStack.decrement(1);
            }

            return ActionResult.SUCCESS;
        }
    }

    private boolean canPlace(PlayerEntity player, Direction direction, ItemStack heldStack, BlockPos pos) {
        return player.canPlaceOn(pos, direction, heldStack); //canPlayerEdit()
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
