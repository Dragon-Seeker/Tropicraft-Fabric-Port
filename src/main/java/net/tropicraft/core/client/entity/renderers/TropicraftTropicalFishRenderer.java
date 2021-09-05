package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.tropicraft.core.client.entity.models.TropicraftTropicalFishModel;
import net.tropicraft.core.common.entity.underdasea.TropicraftTropicalFishEntity;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;

public class TropicraftTropicalFishRenderer extends TropicraftFishRenderer<TropicraftTropicalFishEntity, TropicraftTropicalFishModel> {
    public TropicraftTropicalFishRenderer(EntityRendererFactory.Context context) {
        super(context, new TropicraftTropicalFishModel(context.getPart(TropicraftEntityRendering.TROPICAL_FISH_LAYER)), 0.2f);
    }
}


