package net.tropicraftFabric.client.entity.renderers;

import net.tropicraftFabric.client.entity.models.TropiBeeModel;
import net.tropicraftFabric.client.entity.renderlayer.SunglassesLayer;
import net.tropicraftFabric.client.util.TropicraftRenderUtils;
import net.tropicraftFabric.common.entity.TropiBeeEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class TropiBeeRenderer extends MobEntityRenderer<TropiBeeEntity, TropiBeeModel> {

    public TropiBeeRenderer(EntityRenderDispatcher p_i226033_1_) {
        super(p_i226033_1_, new TropiBeeModel(), 0.4F);

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
