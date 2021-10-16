package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.tropicraft.core.client.entity.models.SeaUrchinModel;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.underdasea.SeaUrchinEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SeaUrchinRenderer extends MobEntityRenderer<SeaUrchinEntity, SeaUrchinModel> {
	/**
	 * Amount freshly hatched sea urchins are scaled down while rendering.
	 */
	public static final float BABY_RENDER_SCALE = 0.5f;

	/**
	 * Amount mature sea urchins are scaled down while rendering.
	 */

	public static final float ADULT_RENDER_SCALE = 1f;
	public static final Identifier SEA_URCHIN_TEXTURE = TropicraftRenderUtils.bindTextureEntity("seaurchin");

	public SeaUrchinRenderer(EntityRendererFactory.Context context) {
		super(context, new SeaUrchinModel(context.getPart(TropicraftEntityRendering.SEA_URCHIN_LAYER)), 0.5f);
	}

	@Override
	protected void scale(final SeaUrchinEntity urchin, final MatrixStack stack, final float partialTickTime) {
		shadowRadius = 0.15f;
		shadowOpacity = 0.5f;
		float growthProgress = urchin.getGrowthProgress();
		final float scale = BABY_RENDER_SCALE + growthProgress * (ADULT_RENDER_SCALE - BABY_RENDER_SCALE);
		final float scaleAmt = 0.5f * scale;

		stack.scale(scaleAmt, scaleAmt, scaleAmt);
	}

	@Override
	public Identifier getTexture(final SeaUrchinEntity entity) {
		return SEA_URCHIN_TEXTURE;
	}
}

