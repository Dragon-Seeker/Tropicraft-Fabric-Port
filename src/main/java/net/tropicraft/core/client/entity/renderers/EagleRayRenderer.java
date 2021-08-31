package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.tropicraft.core.client.entity.models.EagleRayModel;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.underdasea.EagleRayEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;



public class EagleRayRenderer extends MobEntityRenderer<EagleRayEntity, EagleRayModel> {

    public static final Identifier RAY_TEXTURE_LOC = TropicraftRenderUtils.bindTextureEntity("ray/eagleray");

    public EagleRayRenderer(EntityRendererFactory.Context context) {
        super(context, new EagleRayModel(context.getPart()), 0.8f);
    }

    @Override
    public void render(EagleRayEntity eagleRay, float entityYaw, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn) {
        matrixStackIn.push();
        // TODO still needed?
        matrixStackIn.translate(0, -1.25, 0);
        super.render(eagleRay, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.pop();
    }

    @Nullable
    @Override
    public Identifier getTexture(EagleRayEntity eagleRayEntity) {
        return RAY_TEXTURE_LOC;
    }
}
