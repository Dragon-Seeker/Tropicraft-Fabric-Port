package net.tropicraftFabric.client.entity.renderlayer;

import net.tropicraftFabric.client.entity.models.TropiBeeModel;
import net.tropicraftFabric.client.entity.renderers.TropiBeeRenderer;
import net.tropicraftFabric.client.util.TropicraftRenderUtils;
import net.tropicraftFabric.client.util.TropicraftSpecialRenderHelper;
import net.tropicraftFabric.common.entity.TropiBeeEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;


public class SunglassesLayer extends FeatureRenderer<TropiBeeEntity, TropiBeeModel> {

    private TropicraftSpecialRenderHelper mask;
    private TropiBeeModel beeModel;

    public SunglassesLayer(TropiBeeRenderer renderer) {
        super(renderer);
        beeModel = new TropiBeeModel();
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
