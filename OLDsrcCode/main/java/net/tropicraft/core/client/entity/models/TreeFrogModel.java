package net.tropicraft.core.client.entity.models;

import net.minecraft.client.model.*;
import net.tropicraft.core.common.entity.neutral.TreeFrogEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

public class TreeFrogModel extends CompositeEntityModel<TreeFrogEntity> {
    public ModelPart frontLeftLeg;
    public ModelPart frontRightLeg;
    public ModelPart body;
    public ModelPart rearRightLeg;
    public ModelPart rearLeftLeg;
    public ModelPart rightEye;
    public ModelPart leftEye;

    public TreeFrogModel(ModelPart root) {

        frontLeftLeg = root.getChild("frontLeftLeg");
        frontRightLeg = root.getChild("frontRightLeg");
        body = root.getChild("body");
        rearRightLeg = root.getChild("rearRightLeg");
        rearLeftLeg = root.getChild("rearLeftLeg");
        rightEye = root.getChild("rightEye");
        leftEye = root.getChild("leftEye");


        /*
        frontLeftLeg = new ModelPart(this, 12, 19);
        frontLeftLeg.addCuboid(-2F, 0.0F, -2F, 4, 1, 4, 0.0F);
        frontLeftLeg.setPivot(2.0F, 23F, -3F);
        frontLeftLeg.mirror = false;

        frontRightLeg = new ModelPart(this, 12, 14);
        frontRightLeg.addCuboid(-2F, 0.0F, -2F, 4, 1, 4, 0.0F);
        frontRightLeg.setPivot(-2F, 23F, -3F);
        frontRightLeg.mirror = false;

        body = new ModelPart(this, 28, 8);
        body.addCuboid(-2F, -5F, -2F, 4, 9, 4, 0.0F);
        body.setPivot(0.0F, 21F, 1.0F);
        body.pitch = 1.570796F;
        body.mirror = false;

        rearRightLeg = new ModelPart(this, 0, 16);
        rearRightLeg.addCuboid(-3F, 0.0F, -2F, 3, 5, 3, 0.0F);
        rearRightLeg.setPivot(-2F, 19F, 4F);
        rearRightLeg.mirror = false;

        rearLeftLeg = new ModelPart(this, 0, 8);
        rearLeftLeg.addCuboid(0.0F, 0.0F, -2F, 3, 5, 3, 0.0F);
        rearLeftLeg.setPivot(2.0F, 19F, 4F);
        rearLeftLeg.mirror = false;

        rightEye = new ModelPart(this, 0, 0);
        rightEye.addCuboid(-2F, -1F, -1F, 2, 2, 2, 0.0F);
        rightEye.setPivot(-1F, 19F, -1F);
        rightEye.mirror = false;

        leftEye = new ModelPart(this, 0, 4);
        leftEye.addCuboid(0.0F, -1F, -1F, 2, 2, 2, 0.0F);
        leftEye.setPivot(1.0F, 19F, -1F);
        leftEye.mirror = false;
        */
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("frontLeftLeg",
                ModelPartBuilder.create().uv(12, 19)
                        .cuboid(-2F, 0.0F, -2F, 4, 1, 4),
                ModelTransform.pivot(2.0F, 23F, -3F));

        modelPartData.addChild("frontRightLeg",
                ModelPartBuilder.create().uv(12, 14)
                        .cuboid(-2F, 0.0F, -2F, 4, 1, 4),
                ModelTransform.pivot(-2F, 23F, -3F));

        modelPartData.addChild("body",
                ModelPartBuilder.create().uv(28, 8)
                        .cuboid(-2F, -5F, -2F, 4, 9, 4),
                ModelTransform.of(0.0F, 21F, 1.0F, 1.570796F, 0F ,0F));

        modelPartData.addChild("rearRightLeg",
                ModelPartBuilder.create().uv(0, 16)
                        .cuboid(-3F, 0.0F, -2F, 3, 5, 3),
                ModelTransform.pivot(-2F, 19F, 4F));

        modelPartData.addChild("rearLeftLeg",
                ModelPartBuilder.create().uv(0, 8)
                        .cuboid(0.0F, 0.0F, -2F, 3, 5, 3),
                ModelTransform.pivot(2.0F, 19F, 4F));

        modelPartData.addChild("rightEye",
                ModelPartBuilder.create().uv(0, 0)
                        .cuboid(-2F, -1F, -1F, 2, 2, 2),
                ModelTransform.pivot(-1F, 19F, -1F));

        modelPartData.addChild("leftEye",
                ModelPartBuilder.create().uv(0, 4)
                        .cuboid(0.0F, -1F, -1F, 2, 2, 2),
                ModelTransform.pivot(1.0F, 19F, -1F));

        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public void setAngles(TreeFrogEntity froog, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        frontLeftLeg.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        rearLeftLeg.pitch = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        rearRightLeg.pitch = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        frontRightLeg.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(
            frontLeftLeg, frontRightLeg, body, rearRightLeg, rearLeftLeg, rightEye, leftEye
        );
    }
}