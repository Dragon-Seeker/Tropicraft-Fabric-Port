package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.tropicraft.core.client.entity.models.EggModel;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.client.util.TropicraftSpecialRenderHelper;
import net.tropicraft.core.common.entity.egg.EggEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import net.tropicraft.core.common.registry.TropicraftEntityModelLayers;
/*
public class EggRenderer extends LivingEntityRenderer<EggEntity, EggModel> {

	public EggRenderer(final EntityRendererFactory.Context context) {
		super(context, new EggModel(context.getPart()), 1f);
		shadowOpacity = 0.5f;
	}

	@Override
	public void render(EggEntity egg, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
		stack.push();
		if (egg.shouldEggRenderFlat()) {
			shadowRadius = 0.0f;
			stack.translate(0, 0.05, 0);
			drawFlatEgg(egg, partialTicks, stack, bufferIn, packedLightIn);
		} else {
			shadowRadius = 0.2f;
			stack.scale(0.5f, 0.5f, 0.5f);
			super.render(egg, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
		}
		stack.pop();
	}

	public void drawFlatEgg(EggEntity ent, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
		stack.push();

		stack.multiply(this.dispatcher.getRotation());
		stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));

		stack.scale(0.25f, 0.25f, 0.25f);

		final VertexConsumer buffer = TropicraftRenderUtils.getEntityCutoutBuilder(bufferIn, getTexture(ent));
		int overlay = getOverlay(ent, getAnimationCounter(ent, partialTicks));
		
		Matrix4f mat = stack.peek().getModel();
		Matrix3f normal = new Matrix3f();
		normal.loadIdentity();
		TropicraftSpecialRenderHelper.vertex(buffer, mat, normal, -.5, -.25, 0, 1, 1, 1, 1, 0, 1, Direction.UP, packedLightIn, overlay);
		TropicraftSpecialRenderHelper.vertex(buffer, mat, normal,  .5, -.25, 0, 1, 1, 1, 1, 1, 1, Direction.UP, packedLightIn, overlay);
		TropicraftSpecialRenderHelper.vertex(buffer, mat, normal,  .5,  .75, 0, 1, 1, 1, 1, 1, 0, Direction.UP, packedLightIn, overlay);
		TropicraftSpecialRenderHelper.vertex(buffer, mat, normal, -.5,  .75, 0, 1, 1, 1, 1, 0, 0, Direction.UP, packedLightIn, overlay);

		stack.pop();
	}


	@Override
	public Identifier getTexture(EggEntity entity) {
		return TropicraftRenderUtils.bindTextureEntity(entity.getEggTexture());
	}
}

 */
