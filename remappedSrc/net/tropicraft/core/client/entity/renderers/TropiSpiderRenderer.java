package net.tropicraft.core.client.entity.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.hostile.TropiSpiderEntity;

public class TropiSpiderRenderer extends SpiderRenderer<TropiSpiderEntity> {
	public TropiSpiderRenderer(final EntityRenderDispatcher manager) {
		super(manager);
		shadowStrength = 0.5f;
	}

	@Override
	public void render(TropiSpiderEntity spider, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
		stack.pushPose();
		final float scale = getScale(spider);
		shadowRadius = scale;
		stack.scale(scale, scale, scale);
		super.render(spider, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
		stack.popPose();
	}

	@Override
	public ResourceLocation getTexture(TropiSpiderEntity entity) {
		if (entity.getSpiderType() == TropiSpiderEntity.Type.CHILD) {
			return TropicraftRenderUtils.bindTextureEntity("spiderchild");
		}
		if (entity.getSpiderType() == TropiSpiderEntity.Type.MOTHER) {
			return TropicraftRenderUtils.bindTextureEntity("spidermother");
		}
		return TropicraftRenderUtils.bindTextureEntity("spideradult");
	}

	private float getScale(final TropiSpiderEntity spider) {
		float scale = 1.0f;
		if (spider.getSpiderType() == TropiSpiderEntity.Type.CHILD) {
			scale = 0.5f;
		}
		if (spider.getSpiderType() == TropiSpiderEntity.Type.MOTHER) {
			scale = 1.2f;
		}
		return scale;
	}
}