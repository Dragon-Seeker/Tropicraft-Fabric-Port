package net.tropicraftFabric.client.entity.renderers;

import net.tropicraftFabric.client.entity.models.PiranhaModel;
import net.tropicraftFabric.common.entity.underdasea.PiranhaEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;

public class PiranhaRenderer extends TropicraftFishRenderer<PiranhaEntity, PiranhaModel> {

    public PiranhaRenderer(EntityRenderDispatcher manager) {
        super(manager, new PiranhaModel(), 0.2f);
    }
}
