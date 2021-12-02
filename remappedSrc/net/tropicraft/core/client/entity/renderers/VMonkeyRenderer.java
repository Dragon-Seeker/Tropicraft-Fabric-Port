package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tropicraft.Constants;
import net.tropicraft.core.client.entity.models.VMonkeyModel;
import net.tropicraft.core.client.entity.renderlayer.VMonkeyHeldItemLayer;
import net.tropicraft.core.common.entity.neutral.VMonkeyEntity;

public class VMonkeyRenderer extends MobRenderer<VMonkeyEntity, VMonkeyModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Constants.MODID + ":textures/entity/monkeytext.png");
    private static final ResourceLocation ANGRY_TEXTURE = new ResourceLocation(Constants.MODID + ":textures/entity/monkey_angrytext.png");

    public VMonkeyRenderer(EntityRenderDispatcher rendererManager) {
        super(rendererManager, new VMonkeyModel(), 0.5F);
        shadowRadius = 0.3f;
        shadowStrength = 0.5f;
        addLayer(new VMonkeyHeldItemLayer<>(this));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    public ResourceLocation getTexture(VMonkeyEntity entity) {
        return entity.isAggressive() ? ANGRY_TEXTURE : TEXTURE;
    }
}
