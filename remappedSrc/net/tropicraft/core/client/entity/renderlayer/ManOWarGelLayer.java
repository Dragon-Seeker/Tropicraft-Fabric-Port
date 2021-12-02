package net.tropicraft.core.client.entity.renderlayer;

import net.tropicraft.core.client.entity.models.ManOWarModel;
import net.tropicraft.core.client.entity.renderers.ManOWarRenderer;
import net.tropicraft.core.common.entity.underdasea.ManOWarEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

@Environment(EnvType.CLIENT)
public class ManOWarGelLayer extends RenderLayer<ManOWarEntity, ManOWarModel> {
    private final ManOWarRenderer mowRenderer;
    private final ManOWarModel mowModel = new ManOWarModel(0, 20, false);

    public ManOWarGelLayer(ManOWarRenderer manOWarRenderer) {
        super(manOWarRenderer);
        mowRenderer = manOWarRenderer;
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, ManOWarEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isInvisible()) {
            getParentModel().copyPropertiesTo(mowModel);
            mowModel.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
            mowModel.setAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityTranslucent(getTextureLocation(entity)));
            mowModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}