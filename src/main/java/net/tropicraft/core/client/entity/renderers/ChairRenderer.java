package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.tropicraft.core.client.entity.models.ChairModel;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.common.entity.placeable.ChairEntity;

public class ChairRenderer extends FurnitureRenderer<ChairEntity> {

    public ChairRenderer(EntityRendererFactory.Context context) {
        super(context, "chair", new ChairModel(context.getPart(TropicraftEntityRendering.CHAIR_LAYER)));
        shadowRadius = 0.65f;
    }
    
    @Override
    protected void setupTransforms(MatrixStack stack) {
        stack.translate(0, 0, -0.15);
    }
}
