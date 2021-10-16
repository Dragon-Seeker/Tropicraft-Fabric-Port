package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.tropicraft.Constants;
import net.tropicraft.core.client.entity.models.TropiSkellyModel;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.common.entity.hostile.TropiSkellyEntity;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.util.Identifier;

public class TropiSkellyRenderer extends BipedEntityRenderer<TropiSkellyEntity, TropiSkellyModel> {

    private static final Identifier TEXTURE = new Identifier(Constants.MODID, "textures/entity/tropiskeleton.png");

    public TropiSkellyRenderer(EntityRendererFactory.Context context) {
        super(context, new TropiSkellyModel(context.getPart(TropicraftEntityRendering.TROPI_SKELLY_LAYER)), 0.5F);

        features.clear();

        addFeature(new HeadFeatureRenderer<>(this, context.getModelLoader()));
        addFeature(new ElytraFeatureRenderer<>(this, context.getModelLoader()));
        addFeature(new HeldItemFeatureRenderer<>(this));
    }

    @Override
    public Identifier getTexture(TropiSkellyEntity entity) {
        return TEXTURE;
    }
}


