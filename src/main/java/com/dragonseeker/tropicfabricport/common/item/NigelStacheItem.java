package com.dragonseeker.tropicfabricport.common.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
//import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
//import net.tropicraft.core.client.TropicraftRenderUtils;
//import net.tropicraft.core.client.entity.model.PlayerHeadpieceRenderer;

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
