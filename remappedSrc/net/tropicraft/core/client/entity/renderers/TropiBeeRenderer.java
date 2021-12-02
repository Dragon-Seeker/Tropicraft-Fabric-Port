package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tropicraft.core.client.entity.models.TropiBeeModel;
import net.tropicraft.core.client.entity.renderlayer.SunglassesLayer;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.TropiBeeEntity;

public class TropiBeeRenderer extends MobRenderer<TropiBeeEntity, TropiBeeModel> {

    public TropiBeeRenderer(EntityRenderDispatcher p_i226033_1_) {
        super(p_i226033_1_, new TropiBeeModel(), 0.4F);

        addLayer(new SunglassesLayer(this));
    }

    @Override
    public ResourceLocation getTexture(TropiBeeEntity bee) {
        if (bee.hasNectar()) {
            return TropicraftRenderUtils.getTextureEntity("tropibee_nectar");
        }
        return TropicraftRenderUtils.getTextureEntity("tropibee");
    }
}
