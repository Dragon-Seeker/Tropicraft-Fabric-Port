package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.tropicraft.core.client.entity.models.AshenModel;
import net.tropicraft.core.client.entity.models.BeachFloatModel;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.common.entity.placeable.BeachFloatEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;

public class BeachFloatRenderer extends FurnitureRenderer<BeachFloatEntity> {

    public BeachFloatRenderer(EntityRendererFactory.Context context) {
        super(context, "beach_float", new BeachFloatModel(context.getPart(TropicraftEntityRendering.BEACH_FLOAT_LAYER)));
        shadowRadius = .5F;

    }
    
    @Override
    protected double getYOffset() {
        return super.getYOffset() + 1.2;
    }
    
    @Override
    protected void setupTransforms(MatrixStack stack) {
        stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-180));
    }

    @Override
    protected Vec3f getRockingAxis() {
        return Vec3f.POSITIVE_X;
    }

    @Override
    protected float getRockAmount() {
        return 25;
    }
}


