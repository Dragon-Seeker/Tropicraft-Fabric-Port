package net.tropicraft.core.client.entity.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.placeable.WallItemEntity;
import org.jetbrains.annotations.Nullable;

public class WallItemRenderer extends EntityRenderer<WallItemEntity> {

    public WallItemRenderer(final EntityRenderDispatcher entityRendererManager) {
        super(entityRendererManager);
    }
    
    @Override
    public void render(final WallItemEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        stack.pushPose();
        stack.mulPose(Vector3f.XP.rotationDegrees(entity.xRot));
        stack.mulPose(Vector3f.YP.rotationDegrees(180.0F - entity.yRot));
        stack.mulPose(Vector3f.ZP.rotationDegrees(entity.getRotation() * 360 / 8F));        
        TropicraftRenderUtils.renderItem(entity.getItem(), 1, false, stack, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, null);
        stack.popPose();
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    @Nullable
    @Override
    public ResourceLocation getTexture(WallItemEntity wallItemEntity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
