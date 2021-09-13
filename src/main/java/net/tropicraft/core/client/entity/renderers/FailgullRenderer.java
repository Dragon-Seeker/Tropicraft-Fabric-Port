package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.tropicraft.Constants;
import net.tropicraft.core.client.entity.models.FailgullModel;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.common.entity.passive.FailgullEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FailgullRenderer extends MobEntityRenderer<FailgullEntity, FailgullModel> {
    private static final Identifier FAILGULL_TEXTURE = new Identifier(Constants.MODID, "textures/entity/failgull.png");

    public FailgullRenderer(EntityRendererFactory.Context context) {
        super(context, new FailgullModel(context.getPart(TropicraftEntityRendering.FAILGULL_LAYER)), 0.25F);
    }

    @Override
    public Identifier getTexture(FailgullEntity e) {
        return FAILGULL_TEXTURE;
    }
}


