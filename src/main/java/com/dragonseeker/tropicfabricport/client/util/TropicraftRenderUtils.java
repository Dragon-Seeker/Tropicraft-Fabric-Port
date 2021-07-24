package com.dragonseeker.tropicfabricport.client.util;

import com.dragonseeker.tropicfabricport.Tropicfabricport;
import com.google.common.collect.Maps;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SkullItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

import java.util.Map;

public class TropicraftRenderUtils {

    /**
     * Cache of ResourceLocations for texture binding
     */
    private static Map<String, Identifier> resLocMap = Maps.newHashMap();
    private static Map<String, SpriteIdentifier> materialMap = Maps.newHashMap();

    public static VertexConsumer getEntityCutoutBuilder(final VertexConsumerProvider buffer, final Identifier resourceLocation) {
        return buffer.getBuffer(RenderLayer.getEntityCutout(resourceLocation));
    }

    public static BakedModel getBakedModel(final ItemRenderer itemRenderer, final ItemStack itemStack) {
        return itemRenderer.getModels().getModel(itemStack);
    }

    public static void renderModel(final SpriteIdentifier material, final Model model, MatrixStack stack, VertexConsumerProvider buffer, int combinedLightIn, int combinedOverlayIn) {
        model.render(stack, buffer.getBuffer(model.getLayer(material.getTextureId())), combinedLightIn, combinedOverlayIn, 1, 1, 1, 1);
    }

    public static SpriteIdentifier getBlockMaterial(final String path) {
        return materialMap.computeIfAbsent(path, m -> createBlockMaterial(path));
    }

    private static SpriteIdentifier createBlockMaterial(final String path) {
        return new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, getTextureBlock(path));
    }

    public static SpriteIdentifier getTEMaterial(final String path) {
        return materialMap.computeIfAbsent(path, m -> createTEMaterial(path));
    }

    private static SpriteIdentifier createTEMaterial(final String path) {
        return new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, getTextureTE(path));
    }

    public static Identifier getTexture(String path) {
        return resLocMap.computeIfAbsent(path, k -> getResLoc(path));
    }

    private static Identifier getResLoc(String path) {
        return new Identifier(Tropicfabricport.MOD_ID, path);
    }

    public static Identifier getTextureArmor(String path) {
        return getTexture(String.format("textures/models/armor/%s.png", path));
    }

    public static Identifier getTextureBlock(String path) {
        return getTexture(String.format("textures/block/%s.png", path));
    }

    public static Identifier getTextureEntity(String path) {
        return getTexture(String.format("textures/entity/%s.png", path));
    }

    public static Identifier getTextureGui(String path) {
        return getTexture(String.format("textures/gui/%s.png", path));
    }

    public static Identifier getTextureTE(String path) {
        return getTexture(String.format("textures/block/te/%s.png", path));
    }

    public static Identifier bindTextureArmor(String path) {
        return bindTexture(getTextureArmor(path));
    }

    public static Identifier bindTextureEntity(String path) {
        return bindTexture(getTextureEntity(path));
    }

    public static Identifier bindTextureGui(String path) {
        return bindTexture(getTextureGui(path));
    }

    public static Identifier bindTextureTE(String path) {
        return bindTexture(getTextureTE(path));
    }

    public static Identifier bindTextureBlock(String path) {
        return bindTexture(getTextureBlock(path));
    }

    public static Identifier bindTexture(Identifier resource) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(resource);
        return resource;
    }

    public static void renderItem(ItemStack itemStack, final float scale, boolean leftHand, MatrixStack stack, VertexConsumerProvider buffer, int combinedLightIn, int combinedOverlayIn, BakedModel modelIn) {
        if (!itemStack.isEmpty()) {
            stack.push();
            stack.scale(scale, scale, scale);

            // TODO what is this now?
            if (/*!Minecraft.getInstance().getItemRenderer().shouldRenderItemIn3D(stack) || */itemStack.getItem() instanceof SkullItem) {
                stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
            }
            MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformation.Mode.FIXED, combinedLightIn, combinedOverlayIn, stack, buffer);
            stack.pop();
        }
    }
}