package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.tropicraft.core.client.entity.models.MarlinModel;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.underdasea.MarlinEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class MarlinRenderer extends MobEntityRenderer<MarlinEntity, MarlinModel> {
    public MarlinRenderer(EntityRendererFactory.Context context) {
        super(context, new MarlinModel(context.getPart(TropicraftEntityRendering.MARLIN_LAYER)), 0.5F);
        shadowOpacity = 0.5f;
    }

    @Override
    public void render(MarlinEntity marlin, float entityYaw, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn) {
        getModel().inWater = marlin.isTouchingWater();
        super.render(marlin, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Nullable
    @Override
    public Identifier getTexture(MarlinEntity marlin) {
        return TropicraftRenderUtils.getTextureEntity(marlin.getTexture());
    }
}


