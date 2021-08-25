package net.tropicraft.core.client.entity.renderers;

import net.tropicraft.core.client.entity.models.ManOWarModel;
import net.tropicraft.core.client.entity.renderlayer.ManOWarGelLayer;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.underdasea.ManOWarEntity;
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
