package net.tropicraft.core.client.entity.renderers;

import net.tropicraft.core.client.entity.models.TropicraftTropicalFishModel;
import net.tropicraft.core.common.entity.underdasea.TropicraftTropicalFishEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;

public class TropicraftTropicalFishRenderer extends TropicraftFishRenderer<TropicraftTropicalFishEntity, TropicraftTropicalFishModel> {
    public TropicraftTropicalFishRenderer(EntityRenderDispatcher manager) {
        super(manager, new TropicraftTropicalFishModel(), 0.2f);
    }
}
