package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.tropicraft.core.client.entity.models.ManOWarModel;
import net.tropicraft.core.client.entity.renderlayer.ManOWarGelLayer;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.underdasea.ManOWarEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class ManOWarRenderer extends MobEntityRenderer<ManOWarEntity, ManOWarModel> {

    public ManOWarRenderer(EntityRendererFactory.Context context) {
        super(context, new ManOWarModel(context.getPart(TropicraftEntityRendering.MAN_O_WAR_LAYER)), 0.35f);
        //ManOWarModel model = new ManOWarModel(context.getPart(TropicraftEntityRendering.MAN_O_WAR_LAYER));
        addFeature(new ManOWarGelLayer(this));
    }

    @Override
    public Identifier getTexture(ManOWarEntity entity) {
        return TropicraftRenderUtils.getTextureEntity("manowar");
    }
}


