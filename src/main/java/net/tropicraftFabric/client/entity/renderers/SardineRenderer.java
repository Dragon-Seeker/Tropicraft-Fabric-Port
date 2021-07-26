package net.tropicraftFabric.client.entity.renderers;

import net.tropicraftFabric.client.entity.models.SardineModel;
import net.tropicraftFabric.common.entity.underdasea.SardineEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;

public class SardineRenderer extends TropicraftFishRenderer<SardineEntity, SardineModel> {
    public SardineRenderer(EntityRenderDispatcher manager) {
        super(manager, new SardineModel(), 0.2f);
    }
}
