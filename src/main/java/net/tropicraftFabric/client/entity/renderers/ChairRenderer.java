package net.tropicraftFabric.client.entity.renderers;

import net.tropicraftFabric.client.entity.models.ChairModel;
import net.tropicraftFabric.common.entity.placeable.ChairEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;

public class ChairRenderer extends FurnitureRenderer<ChairEntity> {

    public ChairRenderer(EntityRenderDispatcher rendererManager) {
        super(rendererManager, "chair", new ChairModel());
        shadowRadius = 0.65f;
    }
    
    @Override
    protected void setupTransforms(MatrixStack stack) {
        stack.translate(0, 0, -0.15);
    }
}
