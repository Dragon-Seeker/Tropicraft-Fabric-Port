package net.tropicraft.core.client.entity.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.client.util.TropicraftSpecialRenderHelper;
import net.tropicraft.core.common.entity.placeable.AshenMaskEntity;

public class AshenMaskRenderer extends EntityRenderer<AshenMaskEntity> {
	protected TropicraftSpecialRenderHelper mask;

	public AshenMaskRenderer(final EntityRendererProvider.Context context) {
		super(context);
		shadowRadius = 0.5F;
		shadowStrength  = 0.5f;
		mask = new TropicraftSpecialRenderHelper();
	}

	@Override
	public ResourceLocation getTextureLocation(AshenMaskEntity entity) {
		return TropicraftRenderUtils.getTextureEntity("ashen/mask");
	}

	@Override
	public void render(AshenMaskEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int packedLightIn) {
		stack.pushPose();

		VertexConsumer ivertexbuilder = buffer.getBuffer(RenderType.entityCutout(getTextureLocation(entity)));
	//	mask.render(stack, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
//		GlStateManager.pushMatrix();
	//	bindEntityTexture(entity);

		stack.mulPose(Vector3f.XN.rotationDegrees(90));

		mask.renderMask(stack, ivertexbuilder, entity.getMaskType(), packedLightIn, OverlayTexture.NO_OVERLAY);

		stack.popPose();
	}


}
