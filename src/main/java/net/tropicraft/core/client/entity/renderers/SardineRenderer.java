package net.tropicraft.core.client.entity.renderers;

import net.tropicraft.core.client.entity.models.SardineModel;
import net.tropicraft.core.common.entity.underdasea.SardineEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;

public class SardineRenderer extends TropicraftFishRenderer<SardineEntity, SardineModel> {
    public SardineRenderer(EntityRenderDispatcher manager) {
        super(manager, new SardineModel(), 0.2f);
    }
}
