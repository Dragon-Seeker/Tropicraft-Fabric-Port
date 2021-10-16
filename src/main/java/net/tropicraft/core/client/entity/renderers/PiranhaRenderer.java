package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.tropicraft.core.client.entity.models.PiranhaModel;
import net.tropicraft.core.common.entity.underdasea.PiranhaEntity;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;

public class PiranhaRenderer extends TropicraftFishRenderer<PiranhaEntity, PiranhaModel> {

    public PiranhaRenderer(EntityRendererProvider.Context context) {
        super(context, new PiranhaModel(context.bakeLayer(TropicraftEntityRendering.PIRANHA_LAYER)), 0.2f);
    }
}

