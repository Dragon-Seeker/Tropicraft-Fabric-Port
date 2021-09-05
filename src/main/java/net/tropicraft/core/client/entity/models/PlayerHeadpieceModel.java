package net.tropicraft.core.client.entity.models;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.client.util.TropicraftSpecialRenderHelper;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3f;
import org.w3c.dom.Entity;

public class PlayerHeadpieceModel extends BipedEntityModel<LivingEntity> {
	private int textureIndex;
	private double xOffset;
	private double yOffset;
	protected TropicraftSpecialRenderHelper renderer;
	private static float scale;
	final ModelPart headpiece;

	public PlayerHeadpieceModel(ModelPart modelPart, final int textureIndex) {
		this(modelPart,0, textureIndex, 0, 0);
	}

	public PlayerHeadpieceModel(ModelPart modelPart, final int textureIndex, final double xOffset, final double yOffset) {
		this(modelPart,0, textureIndex, xOffset, yOffset);
	}

	public PlayerHeadpieceModel(ModelPart modelPart, float scale, final int textureIndex, final double xOffset, final double yOffset) {
		super(modelPart, RenderLayer::getEntityCutoutNoCull);
		this.textureIndex = textureIndex;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.scale = scale;
		renderer = new TropicraftSpecialRenderHelper();

		headpiece = modelPart.getChild(EntityModelPartNames.HAT);
	}

	/*

	public PlayerHeadpieceModel(ModelPart modelPart) {
		super(modelPart, RenderLayer::getEntityCutoutNoCull);
	}

	 */

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.NONE);
		//modelPartData.addChild("hat", ModelPartBuilder.create(), ModelTransform.NONE);
		modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.NONE);
		modelPartData.addChild("right_arm", ModelPartBuilder.create(), ModelTransform.NONE);
		modelPartData.addChild("left_arm", ModelPartBuilder.create(), ModelTransform.NONE);
		modelPartData.addChild("right_leg", ModelPartBuilder.create(), ModelTransform.NONE);
		modelPartData.addChild("left_leg", ModelPartBuilder.create(), ModelTransform.NONE);

		Dilation dilation_hat = new Dilation(scale + 0.5f);
		ModelPartData hat = modelPartData.addChild(EntityModelPartNames.HAT,
						ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, dilation_hat),
						ModelTransform.NONE);

		return TexturedModelData.of(modelData, 64, 32);
	}

	public static PlayerHeadpieceModel createModel(EntityModelLayer entityModelLayer, EntityModelLoader entityModelLoader, final int textureIndex, final double xOffset, final double yOffset) {
		return new PlayerHeadpieceModel(entityModelLoader == null ?
				getTexturedModelData().createModel() :
				entityModelLoader.getModelPart(entityModelLayer), textureIndex, xOffset, yOffset);
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


