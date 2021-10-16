package net.tropicraft.core.client.entity.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.tropicraft.core.client.entity.models.AshenModel;
import net.tropicraft.core.client.entity.models.BeachFloatModel;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.common.entity.placeable.BeachFloatEntity;

public class BeachFloatRenderer extends FurnitureRenderer<BeachFloatEntity> {

    public BeachFloatRenderer(EntityRendererProvider.Context context) {
        super(context, "beach_float", new BeachFloatModel(context.bakeLayer(TropicraftEntityRendering.BEACH_FLOAT_LAYER)));
        shadowRadius = .5F;

    }
    
    @Override
    protected double getYOffset() {
        return super.getYOffset() + 1.2;
    }
    
    @Override
    protected void setupTransforms(PoseStack stack) {
        stack.mulPose(Vector3f.YP.rotationDegrees(-180));
    }

    @Override
    protected Vector3f getRockingAxis() {
        return Vector3f.XP;
    }

    @Override
    protected float getRockAmount() {
        return 25;
    }
}


