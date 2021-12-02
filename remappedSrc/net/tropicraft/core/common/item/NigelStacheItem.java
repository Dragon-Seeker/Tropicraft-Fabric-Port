package net.tropicraft.core.common.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;

public class NigelStacheItem extends ArmorItem {

    public NigelStacheItem(final FabricItemSettings properties) {
        super(ArmorMaterials.NIGEL_STACHE, EquipmentSlot.HEAD, properties);
    }
    /*
    @Environment("Client")
    @Nullable
    @Override
    public BipedModel getArmorModel(final LivingEntity entityLiving, final ItemStack itemStack, final EquipmentSlot armorSlot, final BipedModel model) {
        return armorSlot == EquipmentSlot.HEAD ? new PlayerHeadpieceRenderer(0) : null;
    }
    
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
    	return TropicraftRenderUtils.getTextureArmor("nigel_layer_1").toString();
    }

     */


}
