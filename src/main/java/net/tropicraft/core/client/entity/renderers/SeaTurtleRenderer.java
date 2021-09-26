package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.tropicraft.core.client.entity.models.SeaTurtleModel;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.SeaTurtleEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SeaTurtleRenderer extends MobEntityRenderer<SeaTurtleEntity, SeaTurtleModel> {

    public SeaTurtleRenderer(EntityRendererFactory.Context context) {
        super(context, new SeaTurtleModel(context.getPart(TropicraftEntityRendering.SEA_TURTLE_LAYER)), 0.7F);
        shadowRadius = 0.5f;
        shadowOpacity = 0.5f;
    }

    public void render(SeaTurtleEntity turtle, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
        float scale = 0.3f;
        final float existingTime = (float) turtle.age / 4000;
        if (turtle.age < 30) {
            shadowOpacity = 0.5f;
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
        stack.push();
        stack.scale(scale, scale, scale);

        super.render(turtle, entityYaw, partialTicks, stack, bufferIn, packedLightIn);

        stack.pop();
    }

    @Override
    public Identifier getTexture(SeaTurtleEntity seaTurtleEntity) {
        return TropicraftRenderUtils.getTextureEntity(String.format("turtle/sea_turtle%s", seaTurtleEntity.getTurtleType()));
    }
}


