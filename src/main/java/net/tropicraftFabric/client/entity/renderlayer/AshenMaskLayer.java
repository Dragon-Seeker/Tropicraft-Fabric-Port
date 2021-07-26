package net.tropicraftFabric.client.entity.renderlayer;

import net.tropicraftFabric.client.util.TropicraftRenderUtils;
import net.tropicraftFabric.client.util.TropicraftSpecialRenderHelper;
import net.tropicraftFabric.client.entity.models.AshenModel;
import net.tropicraftFabric.client.entity.renderers.AshenRenderer;
import net.tropicraftFabric.common.entity.hostile.AshenEntity;
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
