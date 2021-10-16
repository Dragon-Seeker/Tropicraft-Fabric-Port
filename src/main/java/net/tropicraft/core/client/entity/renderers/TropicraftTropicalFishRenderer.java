package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.tropicraft.core.client.entity.models.TropicraftTropicalFishModel;
import net.tropicraft.core.common.entity.underdasea.TropicraftTropicalFishEntity;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;

public class TropicraftTropicalFishRenderer extends TropicraftFishRenderer<TropicraftTropicalFishEntity, TropicraftTropicalFishModel> {
    public TropicraftTropicalFishRenderer(EntityRendererProvider.Context context) {
        super(context, new TropicraftTropicalFishModel(context.bakeLayer(TropicraftEntityRendering.TROPICAL_FISH_LAYER)), 0.2f);
    }
}


