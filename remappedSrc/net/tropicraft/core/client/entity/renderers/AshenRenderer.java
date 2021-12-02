package net.tropicraft.core.client.entity.renderers;

import net.tropicraft.core.client.util.TropicraftRenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tropicraft.core.client.entity.models.AshenModel;
import net.tropicraft.core.client.entity.renderlayer.AshenHeldItemLayer;
import net.tropicraft.core.client.entity.renderlayer.AshenMaskLayer;
import net.tropicraft.core.common.entity.hostile.AshenEntity;
import org.jetbrains.annotations.Nullable;


public class AshenRenderer extends MobRenderer<AshenEntity, AshenModel> {

    private static final ResourceLocation ASHEN_TEXTURE_LOCATION = TropicraftRenderUtils.bindTextureEntity("ashen/ashen");

    public AshenRenderer(EntityRenderDispatcher manager) {
        super(manager, new AshenModel(), 0.5f);

        addLayer(new AshenMaskLayer(this));
        AshenHeldItemLayer<AshenEntity, AshenModel> layer = new AshenHeldItemLayer<>(this);
        layer.setAshenModel(model);

        addLayer(layer);
        //addLayer(layer);

        shadowStrength = 0.5f;
        shadowRadius = 0.3f;
    }

    @Override
    public void render(AshenEntity entityAshen, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        model.actionState = entityAshen.getActionState();
        if (entityAshen.getTarget() != null && entityAshen.distanceTo(entityAshen.getTarget()) < 5.0F && !entityAshen.swinging) {
            model.swinging = true;
        } else {
            if (entityAshen.swinging && entityAshen.swingTime > 6) {
                model.swinging = false;
            }
        }
        super.render(entityAshen, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Nullable
    @Override
    public ResourceLocation getTexture(AshenEntity entity) {
        return ASHEN_TEXTURE_LOCATION;
    }
}


