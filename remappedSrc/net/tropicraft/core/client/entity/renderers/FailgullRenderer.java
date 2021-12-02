package net.tropicraft.core.client.entity.renderers;

import net.tropicraft.Constants;
import net.tropicraft.core.client.entity.models.FailgullModel;
import net.tropicraft.core.common.entity.passive.FailgullEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class FailgullRenderer extends MobRenderer<FailgullEntity, FailgullModel> {
    private static final ResourceLocation FAILGULL_TEXTURE = new ResourceLocation(Constants.MODID, "textures/entity/failgull.png");

    public FailgullRenderer(EntityRenderDispatcher rendererManager) {
        super(rendererManager, new FailgullModel(), 0.25F);
    }

    @Override
    public ResourceLocation getTexture(FailgullEntity e) {
        return FAILGULL_TEXTURE;
    }
}
