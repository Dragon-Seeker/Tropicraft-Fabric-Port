package net.tropicraftFabric.client.entity.renderers;

import net.tropicraftFabric.client.entity.models.TropicraftTropicalFishModel;
import net.tropicraftFabric.common.entity.underdasea.TropicraftTropicalFishEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;

public class TropicraftTropicalFishRenderer extends TropicraftFishRenderer<TropicraftTropicalFishEntity, TropicraftTropicalFishModel> {
    public TropicraftTropicalFishRenderer(EntityRenderDispatcher manager) {
        super(manager, new TropicraftTropicalFishModel(), 0.2f);
    }
}
