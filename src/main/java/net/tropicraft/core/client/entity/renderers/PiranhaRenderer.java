package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.tropicraft.core.client.entity.models.PiranhaModel;
import net.tropicraft.core.common.entity.underdasea.PiranhaEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.tropicraft.core.common.registry.TropicraftEntityModelLayers;

public class PiranhaRenderer extends TropicraftFishRenderer<PiranhaEntity, PiranhaModel> {

    public PiranhaRenderer(EntityRendererFactory.Context context) {
        super(context, new PiranhaModel(context.getPart(TropicraftEntityModelLayers.PIRANHA_LAYER)), 0.2f);
    }
}

