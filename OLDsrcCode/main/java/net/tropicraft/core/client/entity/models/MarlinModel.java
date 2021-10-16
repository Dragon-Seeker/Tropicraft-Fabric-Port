package net.tropicraft.core.client.entity.models;

import net.minecraft.client.model.*;
import net.tropicraft.core.common.entity.underdasea.MarlinEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.render.entity.model.CompositeEntityModel;

public class MarlinModel extends CompositeEntityModel<MarlinEntity> {
    ModelPart head;
    ModelPart body;
    ModelPart sword;
    ModelPart tail1;
    ModelPart tail2;
    ModelPart tail3;
    ModelPart tailEndB;
    ModelPart tailEndT;
    ModelPart dorsalFin1;
    ModelPart leftFin;
    ModelPart rightFin;
    ModelPart bottomFin;
    public boolean inWater;

    public MarlinModel(ModelPart root) {
        head = root.getChild("head");
        body = root.getChild("body");
        sword = head.getChild("sword");
        tail1 = root.getChild("tail1");
        tail2 = tail1.getChild("tail2");
        tail3 = tail2.getChild("tail3");
        tailEndB = tail3.getChild("tailEndB");
        tailEndT = tail3.getChild("tailEndT");
        dorsalFin1 = root.getChild("dorsalFin1");
        leftFin = root.getChild("leftFin");
        rightFin = root.getChild("rightFin");
        bottomFin = root.getChild("bottomFin");


        /*
        textureWidth = 64;
        textureHeight = 32;

        body = new ModelPart(this, 0, 22);
        body.addCuboid(-5F, -3F, -2F, 7, 6, 4);
        body.setPivot(0F, 19F, 0F);
        body.mirror = true;
        setRotation(body, 0F, -1.570796F, 0F);

        dorsalFin1 = new ModelPart(this, 24, 20);
        dorsalFin1.addCuboid(-0.5F, -0.5F, -0.5F, 1, 2, 10);
        dorsalFin1.setPivot(0F, 15.5F, -5F);
        dorsalFin1.mirror = true;

        leftFin = new ModelPart(this, 12, 10);
        leftFin.addCuboid(0F, -0.5F, -2F, 4, 1, 2);
        leftFin.setPivot(2F, 21F, -3F);
        leftFin.mirror = true;

        rightFin = new ModelPart(this, 12, 7);
        rightFin.addCuboid(-4F, -0.5F, -2F, 4, 1, 2);
        rightFin.setPivot(-2F, 21F, -3F);
        rightFin.mirror = true;

        bottomFin = new ModelPart(this, 52, 0);
        bottomFin.addCuboid(-0.5F, 2F, -2.5F, 1, 3, 2);
        bottomFin.setPivot(0F, 19F, 0F);
        bottomFin.mirror = true;
        setRotation(bottomFin, 0.6981317F, 0F, 0F);

        head = new ModelPart(this, 46, 24);
        head.setPivot(0F, 20F, -5F);
        head.mirror = true;
        head.addCuboid(-1.5F, -3F, -3F, 3, 5, 3);
        head.setTextureOffset(28, 0).addCuboid(-1F, -1.5F, -4F, 2, 3, 1);
        head.setTextureOffset(22, 0).addCuboid(-0.5F, -0.5F, -6F, 1, 2, 2);
        head.setTextureOffset(23, 24).addCuboid(-0.5F, -6F, -2.5F, 1, 3, 2);

        sword = new ModelPart(this);
        sword.setPivot(0F, 0F, 0F);
        setRotation(sword, 0F, 1.5707F, 0F);
        sword.mirror = true;
        sword.setTextureOffset(0, 0).addCuboid(4F, -1.5F, -0.5F, 10, 1, 1);
        head.addChild(sword);

        tail1 = new ModelPart(this);
        tail1.setPivot(0F, 19F, 2F);
        tail1.mirror = true;
        tail1.setTextureOffset(0, 13).addCuboid(-1.5F, -2F, 0F, 3, 5, 4);

        tail2 = new ModelPart(this);
        tail2.setPivot(0F, 0F, 4F);
        tail2.mirror = true;
        tail2.setTextureOffset(0, 5).addCuboid(-1F, -1.5F, 0F, 2, 4, 4);
        tail1.addChild(tail2);

        tail3 = new ModelPart(this);
        tail3.setPivot(0F, 1F, 4F);
        tail3.mirror = true;
        tail3.setTextureOffset(46, 0).addCuboid(-0.5F, -1.5F, 0F, 1, 3, 2);
        tail2.addChild(tail3);

        tailEndB = new ModelPart(this);
        tailEndB.setPivot(0F, 0F, 0F);
        setRotation(tailEndB, 0.593411F, 0F, 0F);
        tailEndB.mirror = true;
        tailEndB.setTextureOffset(40, 0).addCuboid(-0.5F, 1F, -1F, 1, 5, 2);
        tail3.addChild(tailEndB);

        tailEndT = new ModelPart(this);
        tailEndT.setPivot(0F, 0F, 0F);
        setRotation(tailEndT, 2.548179F, 0F, 0F);
        tailEndT.mirror = true;
        tailEndT.setTextureOffset(34, 0).addCuboid(-0.5F, 1F, -1F, 1, 5, 2);
        tail3.addChild(tailEndT);

         */
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("body",
                ModelPartBuilder.create().uv(0,22).mirrored()
                        .cuboid(-5F, -3F, -2F, 7, 6, 4),
                ModelTransform.of(0F, 19F, 0F, 0F, -1.570796F, 0F));

        modelPartData.addChild("dorsalFin1",
                ModelPartBuilder.create().uv(24,20).mirrored()
                        .cuboid(-0.5F, -0.5F, -0.5F, 1, 2, 10),
                ModelTransform.pivot(0F, 15.5F, -5F));

        modelPartData.addChild("leftFin",
                ModelPartBuilder.create().uv(12, 10).mirrored()
                        .cuboid(0F, -0.5F, -2F, 4, 1, 2),
                ModelTransform.pivot(2F, 21F, -3F));

        modelPartData.addChild("rightFin",
                ModelPartBuilder.create().uv(12,7).mirrored()
                        .cuboid(-4F, -0.5F, -2F, 4, 1, 2),
                ModelTransform.pivot(-2F, 21F, -3F));

        modelPartData.addChild("bottomFin",
                ModelPartBuilder.create().uv(52, 0).mirrored()
                        .cuboid(-0.5F, 2F, -2.5F, 1, 3, 2),
                ModelTransform.of(0F, 19F, 0F, 0.6981317F, 0F, 0F));

        ModelPartData modelPartHead = modelPartData.addChild("head",
                ModelPartBuilder.create().mirrored()
                        .uv(46, 24)
                            .cuboid(-1.5F, -3F, -3F, 3, 5, 3)
                        .uv(28, 0)
                            .cuboid(-1F, -1.5F, -4F, 2, 3, 1)
                        .uv(22, 0)
                            .cuboid(-0.5F, -0.5F, -6F, 1, 2, 2)
                        .uv(23, 24)
                            .cuboid(-0.5F, -6F, -2.5F, 1, 3, 2),
                ModelTransform.pivot(0F, 20F, -5F));

        modelPartHead.addChild("sword",
                ModelPartBuilder.create().uv(0, 0).mirrored()
                        .cuboid(4F, -1.5F, -0.5F, 10, 1, 1),
                ModelTransform.of(0F, 0F, 0F, 0F, 1.5707F, 0F));

        ModelPartData modelPartTail1 = modelPartData.addChild("tail1",
                ModelPartBuilder.create().uv(0, 13).mirrored()
                        .cuboid(-1.5F, -2F, 0F, 3, 5, 4),
                ModelTransform.pivot(0F, 19F, 2F));

        ModelPartData modelPartTail2 = modelPartTail1.addChild("tail2",
                ModelPartBuilder.create().uv(0, 5).mirrored()
                        .cuboid(-1F, -1.5F, 0F, 2, 4, 4),
                ModelTransform.pivot(0F, 0F, 4F));

        ModelPartData modelPartTail3 = modelPartTail2.addChild("tail3",
                ModelPartBuilder.create().uv(46, 0).mirrored()
                        .cuboid(-0.5F, -1.5F, 0F, 1, 3, 2),
                ModelTransform.pivot(0F, 1F, 4F));

        modelPartTail3.addChild("tailEndB",
                ModelPartBuilder.create().uv(40, 0).mirrored()
                        .cuboid(-0.5F, 1F, -1F, 1, 5, 2),
                ModelTransform.of(0F, 0F, 0F, 0.593411F, 0F, 0F));

        modelPartTail3.addChild("tailEndT",
                ModelPartBuilder.create().uv(34, 0).mirrored()
                        .cuboid(-0.5F, 1F, -1F, 1, 5, 2),
                ModelTransform.of(0F, 0F, 0F, 2.548179F, 0F, 0F));


        return TexturedModelData.of(modelData,64,32);
    }

    @Override
    public void setAngles(MarlinEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        double yAngleRot = Math.sin(ageInTicks * .25F);
        float zWaveFloat = (float) yAngleRot * .165F;
        if (!inWater) {
            float yWaveRot = (float) (Math.sin(ageInTicks * .55F) * .260F);
            head.yaw = yWaveRot;
            tail1.yaw = yWaveRot;
            tail2.yaw = yWaveRot;
            tail3.yaw = yWaveRot;
            leftFin.roll = zWaveFloat + 0.523598F;
            rightFin.roll = -(float) yAngleRot * .165F - 0.523598F;
            leftFin.yaw = -1.5F;
            rightFin.yaw = 1.5F - zWaveFloat - 0.523598F;
        } else {
            head.yaw = (float) yAngleRot * .135F;
            tail1.yaw = (float) yAngleRot * .135F;
            tail2.yaw = (float) Math.sin(ageInTicks * .35F) * .150F;
            tail3.yaw = (float) Math.sin(ageInTicks * .45F) * .160F;
            leftFin.roll = zWaveFloat + 0.523598F;
            rightFin.roll = -(float) yAngleRot * .165F - 0.523598F;
            leftFin.yaw = -0.392699F;
            rightFin.yaw = 0.392699F;
        }
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(body, dorsalFin1, leftFin, rightFin, bottomFin, head, tail1);
    }

    private void setRotation(ModelPart model, float x, float y, float z) {
        model.pitch = x;
        model.yaw = y;
        model.roll = z;
    }
}


