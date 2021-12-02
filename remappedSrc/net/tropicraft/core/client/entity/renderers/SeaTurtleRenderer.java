package net.tropicraft.core.client.entity.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tropicraft.core.client.entity.models.SeaTurtleModel;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.SeaTurtleEntity;

public class SeaTurtleRenderer extends MobRenderer<SeaTurtleEntity, SeaTurtleModel> {

    public SeaTurtleRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new SeaTurtleModel(), 0.7F);
        shadowRadius = 0.5f;
        shadowStrength = 0.5f;
    }

    public void render(SeaTurtleEntity turtle, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        float scale = 0.3f;
        final float existingTime = (float) turtle.tickCount / 4000;
        if (turtle.tickCount < 30) {
            shadowStrength = 0.5f;
            shadowRadius = 0.2f + existingTime;
            if (shadowRadius > 0.5f) {
                shadowRadius = 0.5f;
            }
        } else {
            scale = 0.3f + existingTime;
            if (scale > 1f) {
                scale = 1f;
            }
        }
        if (turtle.isMature()) {
            scale = 1f;
        }
        stack.pushPose();
        stack.scale(scale, scale, scale);

        super.render(turtle, entityYaw, partialTicks, stack, bufferIn, packedLightIn);

        stack.popPose();
    }

    @Override
    public ResourceLocation getTexture(SeaTurtleEntity seaTurtleEntity) {
        return TropicraftRenderUtils.getTextureEntity(String.format("turtle/sea_turtle%s", seaTurtleEntity.getTurtleType()));
    }
}