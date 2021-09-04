package net.tropicraft.core.client.entity.models;

import net.tropicraft.core.common.entity.underdasea.MarlinEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
/*
public class MarlinModel extends CompositeEntityModel<MarlinEntity> {
    ModelPart body;
    ModelPart dorsalFin1;
    ModelPart leftFin;
    ModelPart rightFin;
    ModelPart bottomFin;
    ModelPart head;
    ModelPart tail;
    ModelPart tail1;
    ModelPart sword;
    ModelPart tail3;
    ModelPart tailEndB;
    ModelPart tailEndT;
    public boolean inWater;

    public MarlinModel() {
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
        tail = new ModelPart(this);
        tail.setPivot(0F, 19F, 2F);
        tail.mirror = true;
        tail.setTextureOffset(0, 13).addCuboid(-1.5F, -2F, 0F, 3, 5, 4);
        tail1 = new ModelPart(this);
        tail1.setPivot(0F, 0F, 4F);
        tail1.mirror = true;
        tail1.setTextureOffset(0, 5).addCuboid(-1F, -1.5F, 0F, 2, 4, 4);
        tail3 = new ModelPart(this);
        tail3.setPivot(0F, 1F, 4F);
        tail3.mirror = true;
        tail3.setTextureOffset(46, 0).addCuboid(-0.5F, -1.5F, 0F, 1, 3, 2);
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
        tail1.addChild(tail3);
        tail.addChild(tail1);
    }

    @Override
    public void setAngles(MarlinEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        double yAngleRot = Math.sin(ageInTicks * .25F);
        float zWaveFloat = (float) yAngleRot * .165F;
        if (!inWater) {
            float yWaveRot = (float) (Math.sin(ageInTicks * .55F) * .260F);
            head.yaw = yWaveRot;
            tail.yaw = yWaveRot;
            tail1.yaw = yWaveRot;
            tail3.yaw = yWaveRot;
            leftFin.roll = zWaveFloat + 0.523598F;
            rightFin.roll = -(float) yAngleRot * .165F - 0.523598F;
            leftFin.yaw = -1.5F;
            rightFin.yaw = 1.5F - zWaveFloat - 0.523598F;
        } else {
            head.yaw = (float) yAngleRot * .135F;
            tail.yaw = (float) yAngleRot * .135F;
            tail1.yaw = (float) Math.sin(ageInTicks * .35F) * .150F;
            tail3.yaw = (float) Math.sin(ageInTicks * .45F) * .160F;
            leftFin.roll = zWaveFloat + 0.523598F;
            rightFin.roll = -(float) yAngleRot * .165F - 0.523598F;
            leftFin.yaw = -0.392699F;
            rightFin.yaw = 0.392699F;
        }
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(body, dorsalFin1, leftFin, rightFin, bottomFin, head, tail);
    }

    private void setRotation(ModelPart model, float x, float y, float z) {
        model.pitch = x;
        model.yaw = y;
        model.roll = z;
    }
}


 */