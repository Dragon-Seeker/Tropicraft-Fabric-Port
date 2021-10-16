package net.tropicraft.core.client.entity.models;

import net.minecraft.client.model.*;
import net.tropicraft.core.common.entity.SeaTurtleEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

// TODO - extend QuadrupedModel?
public class SeaTurtleModel extends CompositeEntityModel<SeaTurtleEntity> {
    public ModelPart body;
    public ModelPart frFlipper;
    public ModelPart flFlipper;
    public ModelPart head;
    public ModelPart rlFlipper;
    public ModelPart rrFlipper;
    public boolean inWater;

    public SeaTurtleModel(ModelPart root) {
        inWater = false;
        //textureWidth = 64;
        //textureHeight = 64;

        body = root.getChild("body");
        frFlipper = body.getChild("frFlipper");
        flFlipper = body.getChild("flFlipper");
        head = body.getChild("head");
        rlFlipper = body.getChild("rlFlipper");
        rrFlipper = body.getChild("rrFlipper");

        /*
        body = new ModelPart(this);
        body.setPivot(0F, 19F, 0F);
        setRotation(body, 0F, 0F, 0F);
        body.mirror = true;

        frFlipper = new ModelPart(this);
        frFlipper.setPivot(-7F, 2F, -6F);
        setRotation(frFlipper, 0F, 0F, 0F);
        frFlipper.mirror = true;
        frFlipper.setTextureOffset(0, 20).addCuboid(-10F, 0F, -3F, 10, 1, 4);
        body.addChild(frFlipper);

        flFlipper = new ModelPart(this);
        flFlipper.setPivot(7F, 2F, -6F);
        setRotation(flFlipper, 0F, 0F, 0F);
        flFlipper.mirror = true;
        flFlipper.setTextureOffset(0, 20).addCuboid(0F, 0F, -3F, 10, 1, 4);
        body.addChild(flFlipper);

        body.setTextureOffset(0, 29).addCuboid(-4.5F, -1F, -9F, 9, 2, 1);
        body.setTextureOffset(43, 40).addCuboid(-3F, -2F, 1F, 6, 1, 4);
        body.setTextureOffset(0, 52).addCuboid(-7F, -2F, -8F, 14, 4, 8);
        body.setTextureOffset(0, 41).addCuboid(-5F, -1F, 0F, 10, 3, 8);
        body.setTextureOffset(0, 32).addCuboid(-4F, -2.5F, -6F, 8, 2, 7);
        body.setTextureOffset(44, 55).addCuboid(-6F, -0.5F, 0F, 1, 2, 7);
        body.setTextureOffset(44, 55).addCuboid(5F, -0.5F, 0F, 1, 2, 7);
        body.setTextureOffset(0, 25).addCuboid(-4F, -0.5F, 8F, 8, 2, 2);

        head = new ModelPart(this);
        head.setPivot(0F, 1F, -8F);
        setRotation(head, 0F, 0F, 0F);
        head.mirror = true;
        head.setTextureOffset(0, 0).addCuboid(-1.5F, -1.5F, -6F, 3, 3, 6);
        body.addChild(head);

        rlFlipper = new ModelPart(this);
        rlFlipper.setPivot(-4F, 2F, 7F);
        setRotation(rlFlipper, 0F, 0F, 0F);
        rlFlipper.mirror = true;
        rlFlipper.setTextureOffset(0, 16).addCuboid(-7F, 0F, -1F, 7, 1, 3);
        body.addChild(rlFlipper);

        rrFlipper = new ModelPart(this);
        rrFlipper.setPivot(4F, 2F, 7F);
        setRotation(rrFlipper, 0F, 0F, 0F);
        rrFlipper.mirror = true;
        rrFlipper.setTextureOffset(0, 16).addCuboid(-1F, 0F, -1F, 7, 1, 3);
        body.addChild(rrFlipper);

         */
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData modelPartBody = modelPartData.addChild("body",
                ModelPartBuilder.create().mirrored()
                        .cuboid("bodypart1", -4.5F, -1F, -9F, 9, 2, 1, 0,29)
                        .cuboid("bodypart2", -3F, -2F, 1F, 6, 1, 4, 43,40)
                        .cuboid("bodypart3", -7F, -2F, -8F, 14, 4, 8, 0,52)
                        .cuboid("bodypart4", -5F, -1F, 0F, 10, 3, 8, 0,41)
                        .cuboid("bodypart5", -4F, -2.5F, -6F, 8, 2, 7, 0,32)
                        .cuboid("bodypart6", -6F, -0.5F, 0F, 1, 2, 7, 44,55)
                        .cuboid("bodypart7", 5F, -0.5F, 0F, 1, 2, 7, 44,55)
                        .cuboid("bodypart8", -4F, -0.5F, 8F, 8, 2, 2, 0,25)
                ,ModelTransform.pivot(0F, 19F, 0F));

        modelPartBody.addChild("frFlipper",
                ModelPartBuilder.create().mirrored()
                        .uv(0,20).cuboid(-10F, 0F, -3F, 10, 1, 4),
                ModelTransform.pivot(-7F, 2F, -6F));

        modelPartBody.addChild("flFlipper",
                ModelPartBuilder.create().mirrored()
                        .uv(0,20).cuboid(0F, 0F, -3F, 10, 1, 4),
                ModelTransform.pivot(7F, 2F, -6F));

        modelPartBody.addChild("head",
                ModelPartBuilder.create().mirrored()
                        .uv(0,0).cuboid(-1.5F, -1.5F, -6F, 3, 3, 6),
                ModelTransform.pivot(0F, 1F, -8F));

        modelPartBody.addChild("rlFlipper",
                ModelPartBuilder.create().mirrored()
                        .uv(0,16).cuboid(-7F, 0F, -1F, 7, 1, 3),
                ModelTransform.pivot(-4F, 2F, 7F));

        modelPartBody.addChild("rrFlipper",
                ModelPartBuilder.create().mirrored()
                        .uv(0,16).cuboid(-1F, 0F, -1F, 7, 1, 3),
                ModelTransform.pivot(4F, 2F, 7F));



        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(SeaTurtleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float defFront = 0.3927F;
        float defFront2 = 0.3F;
        float defRear = .5F;

        if (!entity.isTouchingWater() && !entity.hasPassengers()) {

            limbSwingAmount *= 3f;
            limbSwing *= 2f;

            body.pitch = -Math.abs(MathHelper.sin(limbSwing * 0.25F) * 1.25F * limbSwingAmount) - .10F;
            frFlipper.pitch = defFront2;
            frFlipper.yaw = swimRotate(limbSwing, limbSwingAmount, 0.5F, 5F, 0, defFront);
            frFlipper.roll = swimRotate(limbSwing, limbSwingAmount, 0.5f, 1.25f, 0, -defFront2);
            flFlipper.pitch = defFront2;
            flFlipper.yaw = swimRotate(limbSwing, limbSwingAmount, 0.5f, 5f, (float) Math.PI, -defFront2);
            flFlipper.roll = -swimRotate(limbSwing, limbSwingAmount, 0.5f, 1.25f, 0, -defFront2);
            rrFlipper.pitch = 0F;
            rrFlipper.yaw = -swimRotate(limbSwing, limbSwingAmount, 3f, 2f, 0, defRear);
            rrFlipper.roll = 0F;
            rlFlipper.pitch = 0F;
            rlFlipper.yaw = -swimRotate(limbSwing, limbSwingAmount, 3f, 2f, 0, -defRear);
            rlFlipper.roll = 0F;
        } else {
            limbSwingAmount *= 0.75f;
            limbSwing *= 0.1f;
            body.pitch = (float) Math.toRadians(headPitch);
            frFlipper.yaw = swimRotate(limbSwing, limbSwingAmount, 1.25f, 1.5f, 0, defFront);
            frFlipper.pitch = swimRotate(limbSwing, limbSwingAmount, 1.25f, 1.5f, (float) Math.PI / 4, defFront2 + 0.25f);
            frFlipper.roll = 0;
            flFlipper.yaw = -swimRotate(limbSwing, limbSwingAmount, 1.25f, 1.5f, 0, defFront);
            flFlipper.roll = 0;
            flFlipper.pitch = swimRotate(limbSwing, limbSwingAmount, 1.25f, 1.5f, (float) Math.PI / 4, defFront2 + 0.25f);
            rlFlipper.pitch = swimRotate(limbSwing, limbSwingAmount, 5f, 0.5f, (float) Math.PI / 4, 0);
            rrFlipper.pitch = swimRotate(limbSwing, limbSwingAmount, 5f, 0.5f, (float) Math.PI / 4, 0);
            rrFlipper.yaw = -0.5f;
            rlFlipper.yaw = 0.5f;
            rrFlipper.roll = swimRotate(limbSwing, limbSwingAmount, 5f, 0.5f, 0, 0.5f);
            rlFlipper.roll = swimRotate(limbSwing, limbSwingAmount, 5f, 0.5f, (float) Math.PI, -0.5f);;
        }
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(body);
    }
    
    private float swimRotate(float swing, float amount, float rot, float intensity, float rotOffset, float offset) {
        return MathHelper.cos(swing * rot + rotOffset) * amount * intensity + offset;
    }

    private void setRotation(ModelPart model, float x, float y, float z) {
        model.pitch = x;
        model.yaw = y;
        model.roll = z;
    }
}


