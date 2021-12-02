package net.tropicraft.core.client.entity.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tropicraft.core.client.entity.models.EagleRayModel;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.underdasea.EagleRayEntity;
import org.jetbrains.annotations.Nullable;



public class EagleRayRenderer extends MobRenderer<EagleRayEntity, EagleRayModel> {

    public static final ResourceLocation RAY_TEXTURE_LOC = TropicraftRenderUtils.bindTextureEntity("ray/eagleray");

    public EagleRayRenderer(EntityRenderDispatcher manager) {
        super(manager, new EagleRayModel(), 0.8f);
    }

    @Override
    public void render(EagleRayEntity eagleRay, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        // TODO still needed?
        matrixStackIn.translate(0, -1.25, 0);
        super.render(eagleRay, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.popPose();
    }

    @Nullable
    @Override
    public ResourceLocation getTexture(EagleRayEntity eagleRayEntity) {
        return RAY_TEXTURE_LOC;
    }
}
