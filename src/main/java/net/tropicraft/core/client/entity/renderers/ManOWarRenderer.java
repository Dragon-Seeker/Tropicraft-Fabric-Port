package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tropicraft.core.client.entity.models.ManOWarModel;
import net.tropicraft.core.client.entity.renderlayer.ManOWarGelLayer;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.underdasea.ManOWarEntity;

public class ManOWarRenderer extends MobRenderer<ManOWarEntity, ManOWarModel> {

    public ManOWarRenderer(EntityRendererProvider.Context context) {
        super(context, new ManOWarModel(context.bakeLayer(TropicraftEntityRendering.MAN_O_WAR_LAYER)), 0.35f);
        //ManOWarModel model = new ManOWarModel(context.getPart(TropicraftEntityRendering.MAN_O_WAR_LAYER));
        addLayer(new ManOWarGelLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ManOWarEntity entity) {
        return TropicraftRenderUtils.getTextureEntity("manowar");
    }
}


