package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.client.entity.models.AshenModel;
import net.tropicraft.core.client.entity.renderlayer.AshenHeldItemLayer;
import net.tropicraft.core.client.entity.renderlayer.AshenMaskFeatureRenderer;
import net.tropicraft.core.common.entity.hostile.AshenEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import org.jetbrains.annotations.Nullable;


public class AshenRenderer extends MobEntityRenderer<AshenEntity, AshenModel<AshenEntity>> {

    private static final Identifier ASHEN_TEXTURE_LOCATION = TropicraftRenderUtils.bindTextureEntity("ashen/ashen");

    public AshenRenderer(EntityRendererFactory.Context context) {
        super(context, new AshenModel(context.getPart(TropicraftEntityRendering.ASHEN_LAYER)), 0.5f);

        this.addFeature(new AshenMaskFeatureRenderer(this));
        this.addFeature(new AshenHeldItemLayer(this));

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


