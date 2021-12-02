package net.tropicraft.core.client.entity.renderlayer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.tropicraft.core.client.entity.models.TropiBeeModel;
import net.tropicraft.core.client.entity.renderers.TropiBeeRenderer;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.client.util.TropicraftSpecialRenderHelper;
import net.tropicraft.core.common.entity.TropiBeeEntity;


public class SunglassesLayer extends RenderLayer<TropiBeeEntity, TropiBeeModel> {

    private TropicraftSpecialRenderHelper mask;
    private TropiBeeModel beeModel;

    public SunglassesLayer(TropiBeeRenderer renderer) {
        super(renderer);
        beeModel = new TropiBeeModel();
        mask = new TropicraftSpecialRenderHelper();
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource bufferIn, int packedLightIn, TropiBeeEntity bee, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        stack.pushPose();
        beeModel.setupAnim(bee, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        beeModel.getBody().translateAndRotate(stack);

        if (!bee.isBaby()) {
            stack.translate(0.03125F, 0.175, -.313F);
        } else {
            stack.translate(0.03125F, 0.295, -.163F);
        }
        stack.mulPose(Vector3f.YP.rotationDegrees(180));
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(TropicraftRenderUtils.getTextureEntity("sunglasses")));
        mask.renderMask(stack, ivertexbuilder, 0, packedLightIn, OverlayTexture.NO_OVERLAY);
        stack.popPose();
    }
}
