package net.tropicraft.core.client.entity.renderers;

import net.tropicraft.core.client.entity.models.TropicraftDolphinModel;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.underdasea.TropicraftDolphinEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class TropicraftDolphinRenderer extends MobRenderer<TropicraftDolphinEntity, TropicraftDolphinModel> {

    public TropicraftDolphinRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new TropicraftDolphinModel(), 0.5F);
        shadowStrength = 0.5f;
    }

    @Nullable
    @Override
    public ResourceLocation getTexture(TropicraftDolphinEntity dolphin) {
        return TropicraftRenderUtils.getTextureEntity(dolphin.getTexture());
    }
}
