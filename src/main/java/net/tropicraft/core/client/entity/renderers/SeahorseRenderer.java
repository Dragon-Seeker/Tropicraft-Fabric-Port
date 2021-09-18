package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.tropicraft.core.client.entity.models.SeahorseModel;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.underdasea.SeahorseEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class SeahorseRenderer extends MobEntityRenderer<SeahorseEntity, SeahorseModel> {
	public SeahorseRenderer(EntityRendererFactory.Context context) {
		super(context, new SeahorseModel(context.getPart(TropicraftEntityRendering.SEAHORSE_LAYER)), 0.5F);
		shadowOpacity = 0.5f;
	}

	@Override
	public void render(SeahorseEntity seahorse, float entityYaw, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn) {
		matrixStackIn.push();

		matrixStackIn.translate(0, -1f, 0);
		matrixStackIn.scale(0.5f, 0.5f, 0.5f);

		super.render(seahorse, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		matrixStackIn.pop();
	}

	@Nullable
	@Override
	public Identifier getTexture(SeahorseEntity seahorseEntity) {
		return TropicraftRenderUtils.getTextureEntity(String.format("seahorse/%s", seahorseEntity.getTexture()));
	}
}

