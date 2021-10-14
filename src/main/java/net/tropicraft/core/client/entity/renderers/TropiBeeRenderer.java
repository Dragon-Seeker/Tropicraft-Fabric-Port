package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.tropicraft.core.client.entity.models.TropiBeeModel;
import net.tropicraft.core.client.entity.renderlayer.SunglassesLayer;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.TropiBeeEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

//TODO: SUNGLASSES BE FLOATING ABOVE THE BEE FOR SOME REASON
public class TropiBeeRenderer extends MobEntityRenderer<TropiBeeEntity, TropiBeeModel> {

    public TropiBeeRenderer(EntityRendererFactory.Context context) {
        super(context, new TropiBeeModel(context.getPart(TropicraftEntityRendering.TROPI_BEE_LAYER)), 0.4F);

        addFeature(new SunglassesLayer(this));
    }

    @Override
    public Identifier getTexture(TropiBeeEntity bee) {
        if (bee.hasNectar()) {
            return TropicraftRenderUtils.getTextureEntity("tropibee_nectar");
        }
        return TropicraftRenderUtils.getTextureEntity("tropibee");
    }
}


