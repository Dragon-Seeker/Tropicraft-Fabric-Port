package com.dragonseeker.tropicfabricport.client.entity.models;

import com.dragonseeker.tropicfabricport.client.util.TropicraftRenderUtils;
import com.dragonseeker.tropicfabricport.common.item.AshenMasks;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderingRegistry;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MaskArmorProvider implements ArmorRenderingRegistry.ModelProvider, ArmorRenderingRegistry.TextureProvider {

    AshenMasks maskType;

    public MaskArmorProvider(AshenMasks maskType){
        this.maskType = maskType;
    }


    @Override
    public @NotNull BipedEntityModel<LivingEntity> getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, BipedEntityModel<LivingEntity> defaultModel) {
        return slot == EquipmentSlot.HEAD ? new PlayerHeadpieceRenderer(maskType.ordinal(), maskType.getXOffset(), maskType.getYOffset()) : null;
    }

    @Override
    public @NotNull Identifier getArmorTexture(LivingEntity entity, ItemStack stack, EquipmentSlot slot, boolean secondLayer, @Nullable String suffix, Identifier defaultTexture) {
        return TropicraftRenderUtils.getTextureEntity("ashen/mask");
    }
}
