package com.dragonseeker.tropicfabricport.client.models;

import com.dragonseeker.tropicfabricport.client.TropicraftRenderUtils;
import com.dragonseeker.tropicfabricport.item.AshenMaskItem;
import com.dragonseeker.tropicfabricport.item.AshenMasks;
import com.dragonseeker.tropicfabricport.registry.TropicItems;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderingRegistry;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
