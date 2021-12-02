package net.tropicraft.core.client.entity.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tropicraft.core.client.entity.models.SeahorseModel;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.underdasea.SeahorseEntity;
import org.jetbrains.annotations.Nullable;

public class SeahorseRenderer extends MobRenderer<SeahorseEntity, SeahorseModel> {
	public SeahorseRenderer(EntityRenderDispatcher renderManager) {
		super(renderManager, new SeahorseModel(), 0.5F);
		shadowStrength = 0.5f;
	}

	@Override
	public void render(SeahorseEntity seahorse, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
		matrixStackIn.pushPose();

		matrixStackIn.translate(0, -1f, 0);
		matrixStackIn.scale(0.5f, 0.5f, 0.5f);

		super.render(seahorse, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		matrixStackIn.popPose();
	}

	@Nullable
	@Override
	public ResourceLocation getTexture(SeahorseEntity seahorseEntity) {
		return TropicraftRenderUtils.getTextureEntity(String.format("seahorse/%s", seahorseEntity.getTexture()));
	}
}