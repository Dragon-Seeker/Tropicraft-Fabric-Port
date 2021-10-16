package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.tropicraft.core.client.entity.models.SharkModel;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.underdasea.SharkEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SharkRenderer extends MobEntityRenderer<SharkEntity, SharkModel> {

    public static final Identifier BASIC_SHARK_TEXTURE = TropicraftRenderUtils.getTextureEntity("shark/hammerhead1");
    public static final Identifier BOSS_SHARK_TEXTURE = TropicraftRenderUtils.getTextureEntity("shark/hammerhead4");

    public SharkRenderer(EntityRendererFactory.Context context) {
        super(context, new SharkModel(context.getPart(TropicraftEntityRendering.HAMMERHEAD_LAYER)), 1);
    }

    @Override
    public Identifier getTexture(SharkEntity sharkEntity) {
        if (sharkEntity.isBoss()) {
            return BOSS_SHARK_TEXTURE;
        }
        return BASIC_SHARK_TEXTURE;
    }

    @Override
    public void render(SharkEntity shark, float yaw, float partialTicks, MatrixStack stack, VertexConsumerProvider buffer, int light) {
        stack.push();
        stack.translate(0, -1, 0);
        super.render(shark, yaw, partialTicks, stack, buffer, light);
        stack.pop();
    }

    @Override
    protected void scale(SharkEntity shark, final MatrixStack stack, float partialTickTime) {
        float scale = 1f;

        if (shark.isBoss()) {
            scale = 1.5f;
            stack.translate(0, 0.3f, 0);
        }

        stack.scale(scale, scale, scale);
    }
}


