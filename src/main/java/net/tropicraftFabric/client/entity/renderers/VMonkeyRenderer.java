package net.tropicraftFabric.client.entity.renderers;

import net.tropicraftFabric.Constants;
import net.tropicraftFabric.client.entity.models.VMonkeyModel;
import net.tropicraftFabric.client.entity.renderlayer.VMonkeyHeldItemLayer;
import net.tropicraftFabric.common.entity.neutral.VMonkeyEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class VMonkeyRenderer extends MobEntityRenderer<VMonkeyEntity, VMonkeyModel> {
    private static final Identifier TEXTURE = new Identifier(Constants.MODID + ":textures/entity/monkeytext.png");
    private static final Identifier ANGRY_TEXTURE = new Identifier(Constants.MODID + ":textures/entity/monkey_angrytext.png");

    public VMonkeyRenderer(EntityRenderDispatcher rendererManager) {
        super(rendererManager, new VMonkeyModel(), 0.5F);
        shadowRadius = 0.3f;
        shadowOpacity = 0.5f;
        addFeature(new VMonkeyHeldItemLayer<>(this));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    public Identifier getTexture(VMonkeyEntity entity) {
        return entity.isAttacking() ? ANGRY_TEXTURE : TEXTURE;
    }
}
