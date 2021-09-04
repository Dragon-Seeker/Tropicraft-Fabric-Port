package net.tropicraft.core.client.entity.models;

import net.tropicraft.core.client.util.TropicraftSpecialRenderHelper;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3f;
/*
public class PlayerHeadpieceModel extends BipedEntityModel<LivingEntity> {
	private int textureIndex;
	private final double xOffset, yOffset;
	protected TropicraftSpecialRenderHelper renderer;

	public PlayerHeadpieceModel(final int textureIndex) {
		this(textureIndex, 0, 0);
	}

	public PlayerHeadpieceModel(final int textureIndex, final double xOffset, final double yOffset) {
		super(0);
		this.textureIndex = textureIndex;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		renderer = new TropicraftSpecialRenderHelper();
	}

	@Override
	public void render(MatrixStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		stack.push();

		if (sneaking) {
			stack.translate(0, 0.25f, 0);
		}

		// Set head rotation to mask
		stack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(head.yaw));
		stack.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(head.pitch));

		// Flip mask to face away from the player
		stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));

		// put it in the middle in front of the face
		stack.translate(0.0F - xOffset, 0.112f + 0.0625f - yOffset, 0.2501F);

   		// renderMask handles the rendering of the mask model, but it doesn't set the texture.
		// Setting the texture is handled in the item class.
		renderer.renderMask(stack, bufferIn, this.textureIndex, packedLightIn, packedOverlayIn);
		
		stack.pop();
	}
	
	
}


 */