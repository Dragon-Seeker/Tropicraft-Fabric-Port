package net.tropicraft.core.client.entity.renderlayer;

import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.tropicraft.core.client.entity.models.AshenModel;
import net.tropicraft.core.client.entity.models.TropiBeeModel;
import net.tropicraft.core.client.entity.renderers.TropiBeeRenderer;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.client.util.TropicraftSpecialRenderHelper;
import net.tropicraft.core.common.entity.TropiBeeEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;
import net.tropicraft.core.common.entity.hostile.AshenEntity;

public class SunglassesLayer extends FeatureRenderer<TropiBeeEntity, TropiBeeModel> {

    private TropicraftSpecialRenderHelper mask;
    private TropiBeeModel beeModel;

    public SunglassesLayer(FeatureRendererContext<TropiBeeEntity, TropiBeeModel> featureRendererContext) {
        super(featureRendererContext);
        beeModel = featureRendererContext.getModel();
        mask = new TropicraftSpecialRenderHelper();
    }

    @Override
    public void render(MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn, TropiBeeEntity bee, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        stack.push();
        beeModel.setAngles(bee, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        beeModel.getBody().rotate(stack);

        if (!bee.isBaby()) {
            stack.translate(0.03125F, 0.175, -.313F);
        } else {
            stack.translate(0.03125F, 0.295, -.163F);
        }
        stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderLayer.getEntityCutoutNoCull(TropicraftRenderUtils.getTextureEntity("sunglasses")));
        mask.renderMask(stack, ivertexbuilder, 0, packedLightIn, OverlayTexture.DEFAULT_UV);
        stack.pop();
    }
}


