package net.tropicraft.core.client.entity.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tropicraft.core.client.entity.models.SeaUrchinModel;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.underdasea.SeaUrchinEntity;

public class SeaUrchinRenderer extends MobRenderer<SeaUrchinEntity, SeaUrchinModel> {
	/**
	 * Amount freshly hatched sea urchins are scaled down while rendering.
	 */
	public static final float BABY_RENDER_SCALE = 0.5f;

	/**
	 * Amount mature sea urchins are scaled down while rendering.
	 */
	public static final float ADULT_RENDER_SCALE = 1f;
	public static final ResourceLocation SEA_URCHIN_TEXTURE = TropicraftRenderUtils.bindTextureEntity("seaurchin");

	public SeaUrchinRenderer(EntityRenderDispatcher renderManager) {
		super(renderManager, new SeaUrchinModel(), 0.5f);
	}

	@Override
	protected void scale(final SeaUrchinEntity urchin, final PoseStack stack, final float partialTickTime) {
		shadowRadius = 0.15f;
		shadowStrength = 0.5f;
		float growthProgress = urchin.getGrowthProgress();
		final float scale = BABY_RENDER_SCALE + growthProgress * (ADULT_RENDER_SCALE - BABY_RENDER_SCALE);
		final float scaleAmt = 0.5f * scale;

		stack.scale(scaleAmt, scaleAmt, scaleAmt);
	}

	@Override
	public ResourceLocation getTexture(final SeaUrchinEntity entity) {
		return SEA_URCHIN_TEXTURE;
	}
}