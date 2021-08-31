package net.tropicraft.core.client.entity.models;

import net.minecraft.client.model.*;
import net.tropicraft.core.common.entity.underdasea.EagleRayEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

public class EagleRayModel extends CompositeEntityModel<EagleRayEntity> {
	/**
	 * Wing joint amplitudes, linearly interpolated between previous tick and this tick using partialTicks.
	 */
	private float[] interpolatedWingAmplitudes = new float[EagleRayEntity.WING_JOINTS];

	private ModelPart body;
	private ModelPart root;

	public EagleRayModel(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");

		//textureWidth = 128;
		//textureHeight = 64;

		//body = new ModelPart(this, 32, 0);
		//body.addCuboid(-2F, 0F, 0F, 5, 3, 32);
		//body.setPivot(0F, 0F, -8F);
		//body.setTextureSize(128, 64);
		//body.mirror = true;
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		//body.addCuboid(-2F, 0F, 0F, 5, 3, 32);
		//body.setTextureSize(128, 64);
		//body.mirror = true;
		modelPartData.addChild("body", ModelPartBuilder.create().uv(128, 64).cuboid(-2F, 0F, 0F, 5, 3, 32).mirrored(true), ModelTransform.pivot(0F,0F,8F));
		return TexturedModelData.of(modelData,128,128);
	}

	@Override
	public void setAngles(EagleRayEntity eagleRay, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public Iterable<ModelPart> getParts() {
		return ImmutableList.of(body);
	}

	@Override
	public void render(MatrixStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		renderWings(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		renderTailSimple(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	private void renderTailSimple(MatrixStack stack, VertexConsumer buffer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		float minU = 0.75f;
		float maxU = 1.0f;
		float minV = 0.0f;
		float maxV = 0.5f;

		stack.push();
		stack.translate(0.55f, 0f, 1.5f);
		stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90f));
		stack.scale(1.5f, 1f, 1f);
		vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), 0, 0, 0, red, green, blue, alpha, minU, minV, packedLightIn, packedOverlayIn);
		vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), 0, 0, 1, red, green, blue, alpha, minU, maxV, packedLightIn, packedOverlayIn);
		vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), 1, 0, 1, red, green, blue, alpha, maxU, maxV, packedLightIn, packedOverlayIn);
		vertex(buffer, stack.peek().getModel(), stack.peek().getNormal(), 1, 0, 0, red, green, blue, alpha, maxU, minV, packedLightIn, packedOverlayIn);
		stack.pop();
	}

	private static void vertex(VertexConsumer bufferIn, Matrix4f matrixIn, Matrix3f matrixNormalIn, float x, float y, float z, float red, float green, float blue, float alpha, float texU, float texV, int packedLight, int packedOverlay) {
		bufferIn.vertex(matrixIn, x, y, z).color(red, green, blue, alpha).texture(texU, texV).overlay(packedOverlay).light(packedLight).normal(matrixNormalIn, 0.0F, -1.0F, 0.0F).next();
	}

	private void renderWings(MatrixStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		matrixStackIn.push();
		matrixStackIn.translate(0.5f / 16f, 0, -0.5f); // Center on body
		matrixStackIn.scale(2f, 0.5f, 2f); // Scale to correct size
		
		renderWing(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha, false);
		
		// Rotate around center
		matrixStackIn.translate(0, 0, 0.5f);
		matrixStackIn.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180f));
		matrixStackIn.translate(0, 0, -0.5f);
		
		renderWing(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha, true);
		
		matrixStackIn.pop();
	}

	private void renderWing(MatrixStack stack, VertexConsumer buffer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, boolean reverse) {
		float minUFront = 0f;
		float maxUFront = 0.25f;
		float minVFront = 0f;
		float maxVFront = 0.5f;

		float minUBack = 0f;
		float maxUBack = 0.25f;
		float minVBack = 0.5f;
		float maxVBack = 1f;
		
		stack.push();
		stack.translate(1.25f / 16f, 0, 0); // Translate out to body edge

		for (int i = 1; i < EagleRayEntity.WING_JOINTS; i++) {
			float prevAmplitude = interpolatedWingAmplitudes[i - 1];
			float amplitude = interpolatedWingAmplitudes[i];

			float prevX = (i-1)/(EagleRayEntity.WING_JOINTS - 1f);
			float x = i/(EagleRayEntity.WING_JOINTS - 1f);

			float prevUFront = minUFront + (maxUFront-minUFront) * prevX;
			float uFront = minUFront + (maxUFront-minUFront) * x;
			float prevUBack = minUBack + (maxUBack-minUBack) * prevX;
			float uBack = minUBack + (maxUBack-minUBack) * x;

			float offset = -0.001f;
			// Bottom
			final Matrix4f matrix = stack.peek().getModel();
			final Matrix3f normal = stack.peek().getNormal();

			vertex(buffer, matrix, normal, x, amplitude-offset, 0, red, green, blue, alpha, uBack, reverse ? maxVBack : minVBack, packedLightIn, packedOverlayIn);
			vertex(buffer, matrix, normal, x, amplitude-offset, 1, red, green, blue, alpha, uBack, reverse ? minVBack : maxVBack, packedLightIn, packedOverlayIn);
			vertex(buffer, matrix, normal, prevX, prevAmplitude-offset, 1, red, green, blue, alpha, prevUBack, reverse ? minVBack : maxVBack, packedLightIn, packedOverlayIn);
			vertex(buffer, matrix, normal, prevX, prevAmplitude-offset, 0, red, green, blue, alpha, prevUBack, reverse ? maxVBack : minVBack, packedLightIn, packedOverlayIn);

			// Top
			vertex(buffer, matrix, normal, prevX, prevAmplitude, 0, red, green, blue, alpha, prevUFront, reverse ? maxVFront : minVFront, packedLightIn, packedOverlayIn);
			vertex(buffer, matrix, normal, prevX, prevAmplitude, 1, red, green, blue, alpha, prevUFront, reverse ? minVFront : maxVFront, packedLightIn, packedOverlayIn);
			vertex(buffer, matrix, normal, x, amplitude, 1, red, green, blue, alpha, uFront, reverse ? minVFront : maxVFront, packedLightIn, packedOverlayIn);
			vertex(buffer, matrix, normal, x, amplitude, 0, red, green, blue, alpha, uFront, reverse ? maxVFront : minVFront, packedLightIn, packedOverlayIn);
		}
		
		stack.pop();
	}

	@Override
	public void animateModel(EagleRayEntity ray, float par2, float par3, float partialTicks) {
		final float[] prevWingAmplitudes = ray.getPrevWingAmplitudes();
		final float[] wingAmplitudes = ray.getWingAmplitudes();

		for (int i = 1; i < EagleRayEntity.WING_JOINTS; i++) {
			final float prevWingAmplitude = prevWingAmplitudes[i];
			final float wingAmplitude = wingAmplitudes[i];
			interpolatedWingAmplitudes[i] = prevWingAmplitude + partialTicks * (wingAmplitude - prevWingAmplitude);
		}
	}
}
