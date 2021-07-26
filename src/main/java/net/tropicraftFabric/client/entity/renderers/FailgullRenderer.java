package net.tropicraftFabric.client.entity.renderers;

import net.tropicraftFabric.Constants;
import net.tropicraftFabric.client.entity.models.FailgullModel;
import net.tropicraftFabric.common.entity.passive.FailgullEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FailgullRenderer extends MobEntityRenderer<FailgullEntity, FailgullModel> {
    private static final Identifier FAILGULL_TEXTURE = new Identifier(Constants.MODID, "textures/entity/failgull.png");

    public FailgullRenderer(EntityRenderDispatcher rendererManager) {
        super(rendererManager, new FailgullModel(), 0.25F);
    }

    @Override
    public Identifier getTexture(FailgullEntity e) {
        return FAILGULL_TEXTURE;
    }
}
