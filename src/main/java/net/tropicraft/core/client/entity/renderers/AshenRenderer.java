package net.tropicraft.core.client.entity.renderers;

import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.client.entity.models.AshenModel;
import net.tropicraft.core.client.entity.renderlayer.AshenHeldItemLayer;
import net.tropicraft.core.client.entity.renderlayer.AshenMaskLayer;
import net.tropicraft.core.common.entity.hostile.AshenEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;


public class AshenRenderer extends MobEntityRenderer<AshenEntity, AshenModel> {

    private static final Identifier ASHEN_TEXTURE_LOCATION = TropicraftRenderUtils.bindTextureEntity("ashen/ashen");

    public AshenRenderer(EntityRenderDispatcher manager) {
        super(manager, new AshenModel(), 0.5f);

        addFeature(new AshenMaskLayer(this));
        AshenHeldItemLayer<AshenEntity, AshenModel> layer = new AshenHeldItemLayer<>(this);
        layer.setAshenModel(model);

        addFeature(layer);
        //addLayer(layer);

        shadowOpacity = 0.5f;
        shadowRadius = 0.3f;
    }

    @Override
    public void render(AshenEntity entityAshen, float entityYaw, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn) {
        model.actionState = entityAshen.getActionState();
        if (entityAshen.getTarget() != null && entityAshen.distanceTo(entityAshen.getTarget()) < 5.0F && !entityAshen.handSwinging) {
            model.swinging = true;
        } else {
            if (entityAshen.handSwinging && entityAshen.handSwingTicks > 6) {
                model.swinging = false;
            }
        }
        super.render(entityAshen, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Nullable
    @Override
    public Identifier getTexture(AshenEntity entity) {
        return ASHEN_TEXTURE_LOCATION;
    }
}


