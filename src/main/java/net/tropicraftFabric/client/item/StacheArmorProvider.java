package net.tropicraftFabric.client.item;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderingRegistry;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.tropicraftFabric.client.entity.models.PlayerHeadpieceRenderer;
import net.tropicraftFabric.client.util.TropicraftRenderUtils;
import net.tropicraftFabric.common.item.AshenMasks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StacheArmorProvider implements ArmorRenderingRegistry.ModelProvider, ArmorRenderingRegistry.TextureProvider {

    public StacheArmorProvider(){
    }

    @Override
    public @NotNull BipedEntityModel<LivingEntity> getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot slot, BipedEntityModel<LivingEntity> defaultModel) {
        return slot == EquipmentSlot.HEAD ? new PlayerHeadpieceRenderer(0) : null;
    }

    @Override
    public @NotNull Identifier getArmorTexture(LivingEntity entity, ItemStack stack, EquipmentSlot slot, boolean secondLayer, @Nullable String suffix, Identifier defaultTexture) {
        return TropicraftRenderUtils.getTextureArmor("nigel_layer_1");
    }
}
