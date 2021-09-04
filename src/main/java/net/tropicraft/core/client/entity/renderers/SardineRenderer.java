package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.tropicraft.core.client.entity.models.SardineModel;
import net.tropicraft.core.common.entity.underdasea.SardineEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.tropicraft.core.common.registry.TropicraftEntityModelLayers;

public class SardineRenderer extends TropicraftFishRenderer<SardineEntity, SardineModel> {
    public SardineRenderer(EntityRendererFactory.Context context) {
        super(context, new SardineModel(context.getPart(TropicraftEntityModelLayers.RIVER_SARDINE_LAYER)), 0.2f);
    }
}
