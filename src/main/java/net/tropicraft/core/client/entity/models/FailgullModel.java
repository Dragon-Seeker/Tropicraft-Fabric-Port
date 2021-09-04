package net.tropicraft.core.client.entity.models;

import net.tropicraft.core.common.entity.passive.FailgullEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;
/*
public class FailgullModel extends CompositeEntityModel<FailgullEntity> {
	final ModelPart baseFootLeft;
	final ModelPart baseFootRight;
	final ModelPart lowerLeg1;
	final ModelPart lowestBody;
	final ModelPart lowerLeg11;
	final ModelPart lowerBody1;
	final ModelPart lowerBody2;
	final ModelPart rightWing;
	final ModelPart leftWing;
	final ModelPart neck;
	final ModelPart newShape1;

	public FailgullModel() {
		baseFootLeft = new ModelPart(this, 0, 0);
		baseFootLeft.addCuboid(0F, 0F, 0F, 1, 0, 1);
		baseFootLeft.setPivot(-1F, 23F, 0F);

		baseFootRight = new ModelPart(this, 0, 0);
		baseFootRight.addCuboid(0F, 0F, 0F, 1, 0, 1);
		baseFootRight.setPivot(1F, 23F, 0F);

		lowerLeg1 = new ModelPart(this, 0, 0);
		lowerLeg1.addCuboid(0F, -1F, 0F, 1, 2, 0);
		lowerLeg1.setPivot(1F, 22F, 1F);

		lowestBody = new ModelPart(this, 0, 0);
		lowestBody.addCuboid(0F, 0F, 0F, 3, 1, 4);
		lowestBody.setPivot(-1F, 20F, 0F);

		lowerLeg11 = new ModelPart(this, 0, 0);
		lowerLeg11.addCuboid(0F, 0F, 0F, 1, 2, 0);
		lowerLeg11.setPivot(-1F, 21F, 1F);

		lowerBody1 = new ModelPart(this, 0, 0);
		lowerBody1.addCuboid(0F, 0F, 0F, 3, 1, 8);
		lowerBody1.setPivot(-1F, 19F, -1F);

		lowerBody2 = new ModelPart(this, 0, 0);
		lowerBody2.addCuboid(0F, 0F, 0F, 3, 1, 7);
		lowerBody2.setPivot(-1F, 18F, -2F);

		rightWing = new ModelPart(this, 0, 0);
		rightWing.addCuboid(0F, 0F, 0F, 0, 2, 5);
		rightWing.setPivot(-1F, 18F, 0F);
		rightWing.pitch = -0.06981F;
		rightWing.yaw = -0.06981F;

		leftWing = new ModelPart(this, 0, 0);
		leftWing.addCuboid(0F, 0F, 0F, 0, 2, 5);
		leftWing.setPivot(2F, 18F, 0F);
		leftWing.pitch = -0.06981F;
		leftWing.yaw = 0.06981F;

		neck = new ModelPart(this, 0, 0);
		neck.addCuboid(0F, 0F, 0F, 3, 2, 2);
		neck.setPivot(-1F, 16F, -3F);

		newShape1 = new ModelPart(this, 14, 0);
		newShape1.addCuboid(0F, 0F, 0F, 1, 1, 2);
		newShape1.setPivot(0F, 17F, -5F);
	}

	@Override
	public void setAngles(FailgullEntity failgull, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		lowerLeg1.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		lowerLeg11.pitch = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
		rightWing.roll = ageInTicks;
		leftWing.roll = -ageInTicks;		//left wing
	}

	@Override
	public Iterable<ModelPart> getParts() {
		return ImmutableList.of(baseFootLeft, baseFootRight, lowerLeg1, lowerLeg11, rightWing, leftWing, neck, newShape1, lowestBody, lowerBody1, lowerBody2);
	}
}
 */