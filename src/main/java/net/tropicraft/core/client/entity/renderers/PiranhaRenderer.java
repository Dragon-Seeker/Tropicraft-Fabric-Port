package net.tropicraft.core.client.entity.renderers;

import net.tropicraft.core.client.entity.models.PiranhaModel;
import net.tropicraft.core.common.entity.underdasea.PiranhaEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;

public class PiranhaRenderer extends TropicraftFishRenderer<PiranhaEntity, PiranhaModel> {

    public PiranhaRenderer(EntityRenderDispatcher manager) {
        super(manager, new PiranhaModel(), 0.2f);
    }
}
