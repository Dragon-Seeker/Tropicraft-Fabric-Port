package net.tropicraft.core.client.entity.models;

import net.minecraft.client.model.*;
import net.tropicraft.core.common.entity.hostile.AshenEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

public class AshenModel extends CompositeEntityModel<AshenEntity> implements ModelWithArms {
    public static ModelPart root;

    public static ModelPart rightLeg;
    public static ModelPart leftLeg;
    public static ModelPart body;
    public static ModelPart head;
    public static ModelPart mask;
    public static ModelPart rightArm;
    public static ModelPart leftArm;
    public static ModelPart rightArmSub;
    public static ModelPart leftArmSub;
    public float headAngle;
    public boolean swinging;
    public AshenEntity.AshenState actionState;
    public static int textureWidth;
    public static int textureHeight;

    public AshenModel(ModelPart root) {
        this.root = root;
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.mask = root.getChild("mask");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightArmSub = root.getChild("right_arm_sub");
        this.leftArmSub = root.getChild("left_arm_sub");

        textureWidth = 64;
        textureHeight = 32;

        /*
        swinging = false;
        actionState = AshenEntity.AshenState.PEACEFUL;
        headAngle = 0;
        textureWidth = 64;
        textureHeight = 32;

        rightLeg = new ModelPart(this, 25, 0);
        rightLeg.addCuboid(0F, 0F, 0F, 1, 7, 1);
        rightLeg.setPivot(1F, 17F, 0F);
        rightLeg.setTextureSize(64, 32);
        rightLeg.mirror = true;
        setRotation(rightLeg, 0F, 0F, 0F);

        leftLeg = new ModelPart(this, 25, 0);
        leftLeg.addCuboid(-1F, 0F, 0F, 1, 7, 1);
        leftLeg.setPivot(-1F, 17F, 0F);
        leftLeg.setTextureSize(64, 32);
        leftLeg.mirror = true;
        setRotation(leftLeg, 0F, 0F, 0F);

        body = new ModelPart(this, 24, 8);
        body.addCuboid(-2F, -3F, 0F, 4, 7, 3);
        body.setPivot(0F, 13F, 2F);
        body.setTextureSize(64, 32);
        body.mirror = true;
        setRotation(body, 0F, 3.141593F, 0F);

        head = new ModelPart(this, 24, 18);
        head.addCuboid(-2F, -3F, -1F, 4, 3, 4);
        head.setPivot(0F, 10F, 1F);
        head.setTextureSize(64, 32);
        head.mirror = true;
        setRotation(head, 0F, 3.141593F, 0F);
        
        //mask = new ModelRenderer(this, 0, 0);
        //mask.addBox(-5.5F, -10F, 3F, 11, 22, 1);
        //mask.setRotationPoint(0F, 10F, 1F);
        //mask.setTextureSize(64, 32);
        //mask.mirror = true;
        //setRotation(mask, 0F, 3.141593F, 0F);

        rightArm = new ModelPart(this);
        rightArm.setPivot(-2F, 10.5F, 0.5F);
        setRotation(rightArm, 0F, 0F, 0F);
        rightArm.mirror = true;
        rightArm.setTextureOffset(0, 24).addCuboid(-6F, -0.5F, -0.5F, 6, 1, 1);

        rightArmSub = new ModelPart(this);
        rightArmSub.setPivot(-5.5F, 0F, 0F);
        setRotation(rightArmSub, 0F, 0F, 0F);
        rightArmSub.mirror = true;
        rightArmSub.setTextureOffset(31, 0).addCuboid(-0.5F, -6F, -0.5F, 1, 6, 1);
        rightArm.addChild(rightArmSub);

        leftArm = new ModelPart(this);
        leftArm.setPivot(2F, 10.46667F, 0.5F);
        setRotation(leftArm, 0F, 0F, 0F);
        leftArm.mirror = true;
        leftArm.setTextureOffset(0, 24).addCuboid(0F, -0.5F, -0.5F, 6, 1, 1);

        leftArmSub = new ModelPart(this);
        leftArmSub.setPivot(5.5F, 0F, 0F);
        setRotation(leftArmSub, 0F, 0F, 0F);
        leftArmSub.mirror = true;
        leftArmSub.setTextureOffset(31, 0).addCuboid(-0.5F, -6F, -0.5F, 1, 6, 1);
        leftArm.addChild(leftArmSub);
         */
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        boolean swinging = false;
        AshenEntity.AshenState actionState = AshenEntity.AshenState.PEACEFUL;
        float headAngle = 0;

        modelPartData.addChild("right_leg",
                ModelPartBuilder.create()
                    .mirrored(true)
                    .uv(64, 32)
                        .cuboid(0F, 0F, 0F, 1, 7, 1),
                ModelTransform.rotation(0F, 0F, 0F).pivot(1F, 17F, 0F));
        /*
        rightLeg = new ModelPart(this, 25, 0);
        rightLeg.addCuboid(0F, 0F, 0F, 1, 7, 1);
        rightLeg.setPivot(1F, 17F, 0F);
        rightLeg.setTextureSize(64, 32);
        rightLeg.mirror = true;
        setRotation(rightLeg, 0F, 0F, 0F);
         */

        modelPartData.addChild("left_leg",
                ModelPartBuilder.create()
                        .mirrored(true)
                        .uv(64, 32)
                            .cuboid(-1F, 0F, 0F, 1, 7, 1),
                ModelTransform.rotation(0F, 0F, 0F).pivot(-1F, 17F, 0F));
        /*
        leftLeg = new ModelPart(this, 25, 0);
        leftLeg.addCuboid(-1F, 0F, 0F, 1, 7, 1);
        leftLeg.setPivot(-1F, 17F, 0F);
        leftLeg.setTextureSize(64, 32);
        leftLeg.mirror = true;
        setRotation(leftLeg, 0F, 0F, 0F);
         */

        modelPartData.addChild("body",
                ModelPartBuilder.create()
                        .mirrored(true)
                        .uv(64, 32)
                            .cuboid(-2F, -3F, 0F, 4, 7, 3),
                ModelTransform.rotation(0F, 3.141593F, 0F).pivot(0F, 13F, 2F));
        /*
        body = new ModelPart(this, 24, 8);
        body.addCuboid(-2F, -3F, 0F, 4, 7, 3);
        body.setPivot(0F, 13F, 2F);
        body.setTextureSize(64, 32);
        body.mirror = true;
        setRotation(body, 0F, 3.141593F, 0F);
         */

        modelPartData.addChild("head",
                ModelPartBuilder.create()
                        .mirrored(true)
                        .uv(64, 32)
                            .cuboid(-2F, -3F, -1F, 4, 3, 4),
                ModelTransform.rotation(0F, 3.141593F, 0F).pivot(0F, 10F, 1F));
        /*
        head = new ModelPart(this, 24, 18);
        head.addCuboid(-2F, -3F, -1F, 4, 3, 4);
        head.setPivot(0F, 10F, 1F);
        head.setTextureSize(64, 32);
        head.mirror = true;
        setRotation(head, 0F, 3.141593F, 0F);
         */

        //mask = new ModelRenderer(this, 0, 0);
        //mask.addBox(-5.5F, -10F, 3F, 11, 22, 1);
        //mask.setRotationPoint(0F, 10F, 1F);
        //mask.setTextureSize(64, 32);
        //mask.mirror = true;
        //setRotation(mask, 0F, 3.141593F, 0F);

        modelPartData.addChild("head",
                ModelPartBuilder.create()
                        .mirrored(true)
                        .uv(64, 32)
                            .cuboid(-2F, -3F, -1F, 4, 3, 4),
                ModelTransform.rotation(0F, 3.141593F, 0F).pivot(0F, 10F, 1F));
        /*
        rightArm = new ModelPart(this);
        rightArm.setPivot(-2F, 10.5F, 0.5F);
        setRotation(rightArm, 0F, 0F, 0F);
        rightArm.mirror = true;
        rightArm.setTextureOffset(0, 24).addCuboid(-6F, -0.5F, -0.5F, 6, 1, 1);
         */

        rightArmSub = new ModelPart(this);
        rightArmSub.setPivot(-5.5F, 0F, 0F);
        setRotation(rightArmSub, 0F, 0F, 0F);
        rightArmSub.mirror = true;
        rightArmSub.setTextureOffset(31, 0).addCuboid(-0.5F, -6F, -0.5F, 1, 6, 1);
        rightArm.addChild(rightArmSub);

        leftArm = new ModelPart(this);
        leftArm.setPivot(2F, 10.46667F, 0.5F);
        setRotation(leftArm, 0F, 0F, 0F);
        leftArm.mirror = true;
        leftArm.setTextureOffset(0, 24).addCuboid(0F, -0.5F, -0.5F, 6, 1, 1);

        leftArmSub = new ModelPart(this);
        leftArmSub.setPivot(5.5F, 0F, 0F);
        setRotation(leftArmSub, 0F, 0F, 0F);
        leftArmSub.mirror = true;
        leftArmSub.setTextureOffset(31, 0).addCuboid(-0.5F, -6F, -0.5F, 1, 6, 1);
        leftArm.addChild(leftArmSub);





        modelPartData.addChild("body", ModelPartBuilder.create()
                .cuboid(0,0,0,0,1,1), ModelTransform.pivot(0F,16F,0F));
        modelPartData.addChild("tail", ModelPartBuilder.create().cuboid(0,0,0,0,1,1), ModelTransform.pivot(0F,0F,1F));
        return TexturedModelData.of(modelData,0,0);
    }

    @Override
    public void setAngles(AshenEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        head.pitch = headPitch / 125F + headAngle;
        head.yaw = netHeadYaw / 125F + 3.14159F;

        final float armRotater = 1.247196F;
        final float subStraight = 1.570795F;

        switch (actionState) {
            case LOST_MASK:                                             //Mask off
                headAngle = -0.4F;
                rightArm.roll = -armRotater;
                rightArmSub.roll = -5.1F;
                leftArm.roll = armRotater;
                leftArmSub.roll = 5.1F;
                leftArm.pitch = subStraight;
                rightArm.pitch = subStraight;
                rightArm.yaw = -.5F;
                leftArm.yaw = .5F;
                break;
            case HOSTILE:
                headAngle = 0.0F;
                leftArm.pitch = 1.65F + limbSwing / 125F;
                leftArm.yaw = .9F + limbSwingAmount / 125F;
                leftArm.roll = armRotater;
                leftArmSub.roll = 6.2F;
                rightArm.roll = 0.0F - MathHelper.sin(limbSwingAmount * 0.75F) * 0.0220F;
                rightArm.yaw = 0.0F;
                rightArmSub.roll = 0.0F;

                if (swinging) {
                    rightArm.pitch += MathHelper.sin(limbSwingAmount * 0.75F) * 0.0520F;
                } else {
                    rightArm.pitch = 0.0F;
                }
                break;
            default:
                headAngle = 0;
                rightArm.roll = -armRotater;
                rightArmSub.roll = -subStraight;
                leftArm.roll = armRotater;
                leftArmSub.roll = subStraight;
                rightArm.yaw = 0F;
                leftArm.yaw = 0F;
                break;
        }

        leftArm.roll += MathHelper.sin(ageInTicks * 0.25F) * 0.020F;
        rightArm.roll -= MathHelper.sin(ageInTicks * 0.25F) * 0.020F;
    }

    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(body, head, rightArm, leftArm, leftLeg, rightLeg);
    }

    private void setRotation(ModelPart model, float x, float y, float z) {
        model.pitch = x;
        model.yaw = y;
        model.roll = z;
    }

    @Override
    public void animateModel(final AshenEntity entity, float f, float f1, float f2) {
        rightLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.25F * f1;
        leftLeg.pitch = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.25F * f1;
    }

    @Override
    public void setArmAngle(Arm arm, MatrixStack stack) {
        stack.translate(0.09375F, 0.1875F, 0.0F);
    }
}
