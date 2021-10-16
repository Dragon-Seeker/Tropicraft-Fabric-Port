package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.tropicraft.core.client.entity.models.SardineModel;
import net.tropicraft.core.common.entity.underdasea.SardineEntity;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;

public class SardineRenderer extends TropicraftFishRenderer<SardineEntity, SardineModel> {
    public SardineRenderer(EntityRendererProvider.Context context) {
        super(context, new SardineModel(context.bakeLayer(TropicraftEntityRendering.RIVER_SARDINE_LAYER)), 0.2f);
    }
}
