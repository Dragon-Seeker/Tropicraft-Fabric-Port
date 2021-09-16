package net.tropicraft.core.client.entity.renderlayer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.tropicraft.core.client.entity.models.AshenModel;
import net.tropicraft.core.client.entity.models.ManOWarModel;
import net.tropicraft.core.client.entity.renderers.ManOWarRenderer;
import net.tropicraft.core.common.entity.hostile.AshenEntity;
import net.tropicraft.core.common.entity.underdasea.ManOWarEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class ManOWarGelLayer extends FeatureRenderer<ManOWarEntity, ManOWarModel> {
    private final ManOWarModel mowModel;

    public ManOWarGelLayer(FeatureRendererContext<ManOWarEntity, ManOWarModel> featureRendererContext) {
        super(featureRendererContext);
        mowModel = featureRendererContext.getModel();
    }

    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, ManOWarEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isInvisible()) {
            getContextModel().copyStateTo(mowModel);
            mowModel.animateModel(entity, limbSwing, limbSwingAmount, partialTicks);
            mowModel.setAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderLayer.getEntityTranslucent(getTexture(entity)));
            mowModel.render(matrixStackIn, ivertexbuilder, packedLightIn, LivingEntityRenderer.getOverlay(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}

