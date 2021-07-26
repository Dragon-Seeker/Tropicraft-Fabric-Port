package net.tropicraftFabric.client.entity.renderers;

import net.tropicraftFabric.client.entity.models.ManOWarModel;
import net.tropicraftFabric.client.entity.renderlayer.ManOWarGelLayer;
import net.tropicraftFabric.client.util.TropicraftRenderUtils;
import net.tropicraftFabric.common.entity.underdasea.ManOWarEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class ManOWarRenderer extends MobEntityRenderer<ManOWarEntity, ManOWarModel> {

    public ManOWarRenderer(EntityRenderDispatcher rendererManager) {
        super(rendererManager, new ManOWarModel(32, 20, true), 0.35f);
        addFeature(new ManOWarGelLayer(this));
    }

    @Override
    public Identifier getTexture(ManOWarEntity entity) {
        return TropicraftRenderUtils.getTextureEntity("manowar");
    }
}
