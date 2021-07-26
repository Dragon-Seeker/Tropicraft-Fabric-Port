package net.tropicraftFabric.client.entity.renderers;

import net.tropicraftFabric.client.util.TropicraftRenderUtils;
import net.tropicraftFabric.client.util.TropicraftSpecialRenderHelper;
import net.tropicraftFabric.common.entity.placeable.AshenMaskEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class AshenMaskRenderer extends EntityRenderer<AshenMaskEntity> {
	protected TropicraftSpecialRenderHelper mask;

	public AshenMaskRenderer(final EntityRenderDispatcher dispatcher) {
		super(dispatcher);
		shadowRadius = 0.5F;
		shadowOpacity  = 0.5f;
		mask = new TropicraftSpecialRenderHelper();
	}

	@Override
	public Identifier getTexture(AshenMaskEntity entity) {
		return TropicraftRenderUtils.getTextureEntity("ashen/mask");
	}

	@Override
	public void render(AshenMaskEntity entity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider buffer, int packedLightIn) {
		stack.push();

		VertexConsumer ivertexbuilder = buffer.getBuffer(RenderLayer.getEntityCutout(getTexture(entity)));
	//	mask.render(stack, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
//		GlStateManager.pushMatrix();
	//	bindEntityTexture(entity);

		stack.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(90));

		mask.renderMask(stack, ivertexbuilder, entity.getMaskType(), packedLightIn, OverlayTexture.DEFAULT_UV);

		stack.pop();
	}


}
