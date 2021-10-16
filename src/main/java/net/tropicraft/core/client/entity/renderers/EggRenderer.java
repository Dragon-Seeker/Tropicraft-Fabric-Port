package net.tropicraft.core.client.entity.renderers;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.tropicraft.core.client.entity.models.EggModel;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.client.util.TropicraftSpecialRenderHelper;
import net.tropicraft.core.common.entity.egg.EggEntity;

public class EggRenderer extends LivingEntityRenderer<EggEntity, EggModel> {

	public EggRenderer(final EntityRendererProvider.Context context, ModelLayerLocation layer) {
		super(context, new EggModel(context.bakeLayer(layer)), 1f);
		shadowStrength = 0.5f;
	}

	@Override
	public void render(EggEntity egg, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
		stack.pushPose();
		if (egg.shouldEggRenderFlat()) {
			shadowRadius = 0.0f;
			stack.translate(0, 0.05, 0);
			drawFlatEgg(egg, partialTicks, stack, bufferIn, packedLightIn);
		} else {
			shadowRadius = 0.2f;
			stack.scale(0.5f, 0.5f, 0.5f);
			super.render(egg, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
		}
		stack.popPose();
	}

	public void drawFlatEgg(EggEntity ent, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
		stack.pushPose();

		stack.mulPose(this.entityRenderDispatcher.cameraOrientation());
		stack.mulPose(Vector3f.YP.rotationDegrees(180.0F));

		stack.scale(0.25f, 0.25f, 0.25f);

		final VertexConsumer buffer = TropicraftRenderUtils.getEntityCutoutBuilder(bufferIn, getTextureLocation(ent));
		int overlay = getOverlayCoords(ent, getWhiteOverlayProgress(ent, partialTicks));
		
		Matrix4f mat = stack.last().pose();
		Matrix3f normal = new Matrix3f();
		normal.setIdentity();
		TropicraftSpecialRenderHelper.vertex(buffer, mat, normal, -.5, -.25, 0, 1, 1, 1, 1, 0, 1, Direction.UP, packedLightIn, overlay);
		TropicraftSpecialRenderHelper.vertex(buffer, mat, normal,  .5, -.25, 0, 1, 1, 1, 1, 1, 1, Direction.UP, packedLightIn, overlay);
		TropicraftSpecialRenderHelper.vertex(buffer, mat, normal,  .5,  .75, 0, 1, 1, 1, 1, 1, 0, Direction.UP, packedLightIn, overlay);
		TropicraftSpecialRenderHelper.vertex(buffer, mat, normal, -.5,  .75, 0, 1, 1, 1, 1, 0, 0, Direction.UP, packedLightIn, overlay);

		stack.popPose();
	}


	@Override
	public ResourceLocation getTextureLocation(EggEntity entity) {
		return TropicraftRenderUtils.bindTextureEntity(entity.getEggTexture());
	}
}

