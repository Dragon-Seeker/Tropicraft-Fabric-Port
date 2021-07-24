package com.dragonseeker.tropicfabricport.client.entity.renderers;

import com.dragonseeker.tropicfabricport.client.util.TropicraftRenderUtils;
import com.dragonseeker.tropicfabricport.common.entity.WallItemEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;

public class WallItemRenderer extends EntityRenderer<WallItemEntity> {

    public WallItemRenderer(final EntityRenderDispatcher entityRendererManager) {
        super(entityRendererManager);
    }
    
    @Override
    public void render(final WallItemEntity entity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
        stack.push();
        stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(entity.pitch));
        stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F - entity.yaw));
        stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(entity.getRotation() * 360 / 8F));        
        TropicraftRenderUtils.renderItem(entity.getHeldItemStack(), 1, false, stack, bufferIn, packedLightIn, OverlayTexture.DEFAULT_UV, null);
        stack.pop();
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    @Nullable
    @Override
    public Identifier getTexture(WallItemEntity wallItemEntity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
