package net.tropicraft.core.client.entity.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.tropicraft.core.client.entity.models.ChairModel;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.common.entity.placeable.ChairEntity;

public class ChairRenderer extends FurnitureRenderer<ChairEntity> {

    public ChairRenderer(EntityRendererProvider.Context context) {
        super(context, "chair", new ChairModel(context.bakeLayer(TropicraftEntityRendering.CHAIR_LAYER)));
        shadowRadius = 0.65f;
    }
    
    @Override
    protected void setupTransforms(PoseStack stack) {
        stack.translate(0, 0, -0.15);
    }
}
