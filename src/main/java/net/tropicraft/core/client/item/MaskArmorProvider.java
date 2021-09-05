package net.tropicraft.core.client.item;

import net.tropicraft.core.client.entity.models.PlayerHeadpieceModel;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.item.AshenMasks;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import shadow.fabric.api.client.rendering.v1.ArmorRenderingRegistry;

public class MaskArmorProvider implements ArmorRenderingRegistry.ModelProvider, ArmorRenderingRegistry.TextureProvider {

    AshenMasks maskType;

    public MaskArmorProvider(AshenMasks maskType){
        this.maskType = maskType;
    }


    @Override
    public @NotNull BipedEntityModel<LivingEntity> getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, BipedEntityModel<LivingEntity> defaultModel) {
        return slot == EquipmentSlot.HEAD ? new PlayerHeadpieceModel(maskType.ordinal(), maskType.getXOffset(), maskType.getYOffset()) : null;
    }

    @Override
    public @NotNull Identifier getArmorTexture(LivingEntity entity, ItemStack stack, EquipmentSlot slot, boolean secondLayer, @Nullable String suffix, Identifier defaultTexture) {
        return TropicraftRenderUtils.getTextureEntity("ashen/mask");
    }
}


