package net.tropicraft.core.client.entity.renderers;

import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.client.util.TropicraftSpecialRenderHelper;
import net.tropicraft.core.common.entity.underdasea.StarfishEntity;
import net.tropicraft.core.common.entity.underdasea.StarfishType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;

public class StarfishRenderer extends EntityRenderer<StarfishEntity> {

	/**
	 * Amount freshly hatched starfish are scaled down while rendering.
	 */
	public static final float BABY_RENDER_SCALE = 0.25f;

	/**
	 * Amount mature starfish are scaled down while rendering.
	 */
	public static final float ADULT_RENDER_SCALE = 1f;

	public StarfishRenderer(EntityRenderDispatcher renderManager) {
		super(renderManager);
	}

	@Override
	public void render(StarfishEntity starfish, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider buffer, int packedLightIn) {
		StarfishType type = starfish.getStarfishType();

		float f = 0f;
		float f1 = 1f;
		float f2 = 0f;
		float f3 = 1f;
		float f1shifted = 1;
		float f3shifted = 1;

		stack.push();
		stack.translate(-0.5, 0, -0.5);
		stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));

		final float scale = BABY_RENDER_SCALE + starfish.getGrowthProgress() * (ADULT_RENDER_SCALE - BABY_RENDER_SCALE);
		stack.scale(scale, scale, scale);

		for (int i = 0; i < type.getLayerCount(); i++) {
			final VertexConsumer ivertexbuilder = buffer.getBuffer(RenderLayer.getEntityCutout(TropicraftRenderUtils.getTextureEntity(type.getTexturePaths().get(i))));
			final float red = 1;
			final float green = starfish.hurtTime > 0 ? 0 : 1;
			final float blue = starfish.hurtTime > 0 ? 0 : 1;
			final float alpha = 1;
			final float layerHeight = type.getLayerHeights()[i];
			TropicraftSpecialRenderHelper.popper(f1, f2, f, f3, f1shifted, f3shifted, layerHeight, stack, ivertexbuilder, packedLightIn, LivingEntityRenderer.getOverlay(starfish, 0), red, green, blue, alpha);
			stack.translate(0f, 0f, -layerHeight);
		}

		stack.pop();
	}

	@Nullable
	@Override
	public Identifier getTexture(StarfishEntity starfishEntity) {
		return null; // Custom setting this in the render loop
	}
}
