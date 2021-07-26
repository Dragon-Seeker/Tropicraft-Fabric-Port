package net.tropicraftFabric.client.entity.renderers;

import net.tropicraftFabric.client.entity.models.BeachFloatModel;
import net.tropicraftFabric.common.entity.placeable.BeachFloatEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;

public class BeachFloatRenderer extends FurnitureRenderer<BeachFloatEntity> {

    public BeachFloatRenderer(EntityRenderDispatcher rendererManager) {
        super(rendererManager, "beach_float", new BeachFloatModel());
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
