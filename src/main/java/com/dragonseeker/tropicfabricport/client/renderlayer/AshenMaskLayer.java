package com.dragonseeker.tropicfabricport.client.renderlayer;

import com.dragonseeker.tropicfabricport.client.TropicraftRenderUtils;
import com.dragonseeker.tropicfabricport.client.TropicraftSpecialRenderHelper;
import com.dragonseeker.tropicfabricport.client.models.AshenModel;
import com.dragonseeker.tropicfabricport.client.renderers.AshenRenderer;
import com.dragonseeker.tropicfabricport.entity.AshenEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class AshenMaskLayer extends FeatureRenderer<AshenEntity, AshenModel> {

    private TropicraftSpecialRenderHelper mask;
    private AshenModel modelAshen;

    public AshenMaskLayer(AshenRenderer renderer) {
        super(renderer);
        modelAshen = new AshenModel();
        mask = new TropicraftSpecialRenderHelper();
    }

    @Override
    public void render(MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn, AshenEntity ashen, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (ashen.hasMask()) {
            stack.push();
            modelAshen.setAngles(ashen, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            modelAshen.head.rotate(stack);

            stack.translate(-0.03125F, 0.0625f * 3, .18F);
            stack.scale(0.75f, 0.75f, 0.75f);
            VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderLayer.getEntityCutoutNoCull(TropicraftRenderUtils.getTextureEntity("ashen/mask")));
            mask.renderMask(stack, ivertexbuilder, ashen.getMaskType(), packedLightIn, OverlayTexture.DEFAULT_UV);
            stack.pop();
        }
    }
}
