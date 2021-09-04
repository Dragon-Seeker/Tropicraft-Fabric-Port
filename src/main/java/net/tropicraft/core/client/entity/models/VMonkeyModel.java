package net.tropicraft.core.client.entity.models;

import net.tropicraft.core.common.entity.neutral.VMonkeyEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

import java.util.Random;
/*
public class VMonkeyModel extends CompositeEntityModel<VMonkeyEntity> implements ModelWithArms {
    public ModelPart body;
    public ModelPart lLegUpper;
    public ModelPart rLegUpper;
    public ModelPart rArmUpper;
    public ModelPart lArmUpper;
    public ModelPart tailBase;
    public ModelPart tailMid;
    public ModelPart tailTop;
    public ModelPart rArmLower;
    public ModelPart lArmLower;
    public ModelPart lLegLower;
    public ModelPart rLegLower;
    public ModelPart face;
    public ModelPart head;
    protected Random rand;
    public float herps;

    public VMonkeyModel() {
        body = new ModelPart(this, 0, 8);
        body.addCuboid(-1F, -2F, -4F, 2, 4, 9, 0F);
        body.setPivot(0F, 16F, 0F);
        body.pitch = 0F;
        body.yaw = 3.141593F;
        body.roll = 0F;
        body.mirror = false;
        lLegUpper = new ModelPart(this, 7, 0);
        lLegUpper.addCuboid(-1F, 0F, -0.5F, 1, 5, 1, 0F);
        lLegUpper.setPivot(-1F, 14F, -3.5F);
        lLegUpper.pitch = 0F;
        lLegUpper.yaw = 0F;
        lLegUpper.roll = 0F;
        lLegUpper.mirror = false;
        rLegUpper = new ModelPart(this, 0, 0);
        rLegUpper.addCuboid(0F, 0F, -0.5F, 1, 5, 1, 0F);
        rLegUpper.setPivot(1F, 14F, -3.5F);
        rLegUpper.pitch = 0F;
        rLegUpper.yaw = 0F;
        rLegUpper.roll = 0F;
        rLegUpper.mirror = false;
        rArmUpper = new ModelPart(this, 0, 0);
        rArmUpper.addCuboid(0F, 0F, -0.5F, 1, 5, 1, 0F);
        rArmUpper.setPivot(1F, 14F, 3.5F);
        rArmUpper.pitch = 0F;
        rArmUpper.yaw = 0F;
        rArmUpper.roll = 0F;
        rArmUpper.mirror = false;
        lArmUpper = new ModelPart(this, 7, 0);
        lArmUpper.addCuboid(-1F, 0F, -0.5F, 1, 5, 1, 0F);
        lArmUpper.setPivot(-1F, 14F, 3.5F);
        lArmUpper.pitch = 0F;
        lArmUpper.yaw = 0F;
        lArmUpper.roll = 0F;
        lArmUpper.mirror = false;
        tailBase = new ModelPart(this, 20, 27);
        tailBase.addCuboid(-0.5F, -4F, -0.5F, 1, 3, 1, 0F);
        tailBase.setPivot(0F, 15F, 3.5F);
        tailBase.pitch = 0F;
        tailBase.yaw = 3.141593F;
        tailBase.roll = 0F;
        tailBase.mirror = false;
        tailMid = new ModelPart(this, 20, 24);
        tailMid.addCuboid(-0.5F, -2F, -0.5F, 1, 2, 1, 0F);
        tailMid.setPivot(0F, 11F, 3.5F);
        tailMid.pitch = 0F;
        tailMid.yaw = 3.141593F;
        tailMid.roll = 0F;
        tailMid.mirror = false;
        tailTop = new ModelPart(this, 20, 21);
        tailTop.addCuboid(-0.5F, -2F, -0.5F, 1, 2, 1, 0F);
        tailTop.setPivot(0F, 9F, 3.5F);
        tailTop.pitch = 0F;
        tailTop.yaw = 3.141593F;
        tailTop.roll = 0F;
        tailTop.mirror = false;
        rArmLower = new ModelPart(this, 0, 7);
        rArmLower.addCuboid(0F, 0F, -0.5F, 1, 5, 1, 0F);
        rArmLower.setPivot(1F, 19F, 3.5F);
        rArmLower.pitch = 0F;
        rArmLower.yaw = 0F;
        rArmLower.roll = 0F;
        rArmLower.mirror = false;
        lArmLower = new ModelPart(this, 12, 0);
        lArmLower.addCuboid(-1F, 0F, -0.5F, 1, 5, 1, 0F);
        lArmLower.setPivot(-1F, 19F, 3.5F);
        lArmLower.pitch = 0F;
        lArmLower.yaw = 0F;
        lArmLower.roll = 0F;
        lArmLower.mirror = false;
        lLegLower = new ModelPart(this, 12, 0);
        lLegLower.addCuboid(-1F, 0F, -0.5F, 1, 5, 1, 0F);
        lLegLower.setPivot(-1F, 19F, -3.5F);
        lLegLower.pitch = 0F;
        lLegLower.yaw = 0F;
        lLegLower.roll = 0F;
        lLegLower.mirror = false;
        rLegLower = new ModelPart(this, 0, 7);
        rLegLower.addCuboid(0F, 0F, -0.5F, 1, 5, 1, 0F);
        rLegLower.setPivot(1F, 19F, -3.5F);
        rLegLower.pitch = 0F;
        rLegLower.yaw = 0F;
        rLegLower.roll = 0F;
        rLegLower.mirror = false;
        face = new ModelPart(this, 0, 25);
        face.addCuboid(-2F, -1F, 0F, 4, 4, 3, 0F);
        face.setPivot(0F, 15F, -5F);
        face.pitch = 0F;
        face.yaw = 3.141593F;
        face.roll = 0F;
        face.mirror = false;
        head = new ModelPart(this, 25, 25);
        head.addCuboid(-3F, -2F, 0F, 6, 5, 2, 0F);
        head.setPivot(0F, 15F, -5F);
        head.pitch = 0F;
        head.yaw = 3.141593F;
        head.roll = 0F;
        head.mirror = false;
    }

    @Override
    public void setAngles(VMonkeyEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        face.pitch = headPitch / 57.29578F + herps;
        face.yaw = netHeadYaw / 57.29578F + 3.141593F;
        head.pitch = face.pitch;
        head.yaw = face.yaw;
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(
            body, lLegUpper, rLegUpper, rArmUpper, lArmUpper, tailBase, tailMid,
            tailTop, rArmLower, lArmLower, lLegLower, rLegLower, face, head
        );
    }

    @Override
    public void animateModel(VMonkeyEntity entityvmonkey, float f, float f1, float f2) {
        if (entityvmonkey.isSitting()) {
            body.setPivot(0F, 20F, 0F);
            body.pitch = 0.9320058F;
            body.yaw = 3.141593F;
            lLegUpper.setPivot(-1F, 16F, -1.5F);
            lLegUpper.pitch = -0.2792527F;
            rLegUpper.setPivot(1F, 16F, -1.5F);
            rLegUpper.pitch = -0.2792527F;
            rLegUpper.yaw = 0.005817764F;
            rArmUpper.setPivot(1F, 22F, 3.5F);
            rArmUpper.pitch = -2.142101F;
            lArmUpper.setPivot(-1F, 22F, 3.5F);
            lArmUpper.pitch = -2.142043F;
            tailBase.setPivot(0F, 22F, 2.466667F);
            tailBase.pitch = 1.902409F;
            tailBase.yaw = 3.141593F;
            tailMid.setPivot(0F, 23.3F, 5.966667F);
            tailMid.pitch = 1.570796F;
            tailMid.yaw = 2.111848F;
            tailMid.roll = -0.2617994F;
            tailTop.setPivot(-1F, 23.2F, 7F);
            tailTop.pitch = 1.570796F;
            tailTop.yaw = 0.8377581F;
            tailTop.roll = 0.01745329F;
            rArmLower.setPivot(1F, 19F, -0.5F);
            rArmLower.pitch = -0.1489348F;
            lArmLower.setPivot(-1F, 19F, -0.3F);
            lArmLower.pitch = -0.1492257F;
            lLegLower.setPivot(-1F, 21F, -2.8F);
            lLegLower.pitch = -0.9599311F;
            rLegLower.setPivot(1F, 21F, -2.833333F);
            rLegLower.pitch = -0.9599311F;
            face.setPivot(0F, 15F, -3F);
            head.setPivot(0F, 15F, -3F);
            herps = 0;
        } else if (entityvmonkey.isClimbing()) {
            body.pitch = 1.570796F;
            body.setPivot(0F, 16F, 0F);
            lLegUpper.setPivot(-1F, 12F, 2F);
            //lLegUpper.rotateAngleX = -1.570796F;    
            rLegUpper.setPivot(1F, 12F, 2F);
            //rLegUpper.rotateAngleX = -1.570796F;        
            rArmUpper.setPivot(1F, 19.5F, 2F);
            //rArmUpper.rotateAngleX = -1.570796F;        
            lArmUpper.setPivot(-1F, 19.5F, 2F);
            //lArmUpper.rotateAngleX = -1.570796F;        
            tailBase.setPivot(0F, 19.5F, 0.5F);
            tailBase.pitch = 1.570796F;
            tailBase.yaw = 3.141593F;
            tailMid.setPivot(0F, 19.5F, 4.5F);

            tailMid.pitch = 1.570796F;
            tailMid.yaw = 3.141593F;
            tailTop.setPivot(0F, 19.5F, 6.5F);
            tailTop.pitch = 1.570796F;
            tailTop.yaw = 3.141593F;
            rArmLower.setPivot(1F, 19.5F, -3F);
            //rArmLower.rotateAngleX = -0.6981317F;
            lArmLower.setPivot(-1F, 19.5F, -3F);
            //lArmLower.rotateAngleX = -0.6981317F;        
            lLegLower.setPivot(-1F, 12F, -3F);
            //lLegLower.rotateAngleX = -2.443461F;    
            rLegLower.setPivot(1F, 12F, -3F);
            //rLegLower.rotateAngleX = -2.443461F;        
            face.setPivot(0F, 11F, 1F);
            herps = 1.570796F;
            head.setPivot(0F, 11F, 1F);
            head.pitch = 1.570796F;

            rLegUpper.pitch = MathHelper.cos(f * .5F) * .75F * f2 - 1.570796F;
            rArmUpper.pitch = MathHelper.cos(f * .5F) * .75F * f2 - 1.570796F;
            lArmUpper.pitch = MathHelper.cos(f * .5F) * .75F * f2 - 1.570796F;
            lLegUpper.pitch = MathHelper.cos(f * .5F) * .75F * f2 - 1.570796F;
            rLegLower.setPivot(1F, 12F + (MathHelper.cos(rLegUpper.pitch) * 5), -3F - (5 + MathHelper.sin(rLegUpper.pitch) * 5));
            rArmLower.setPivot(1F, 19.5F + (MathHelper.cos(rArmUpper.pitch) * 5), -3F - (5 + MathHelper.sin(rArmUpper.pitch) * 5));
            lArmLower.setPivot(-1F, 19.5F + (MathHelper.cos(lArmUpper.pitch) * 5), -3F - (5 + MathHelper.sin(lArmUpper.pitch) * 5));
            lLegLower.setPivot(-1F, 12F + (MathHelper.cos(lLegUpper.pitch) * 5), -3F - (5 + MathHelper.sin(lLegUpper.pitch) * 5));
            rLegLower.pitch = rLegUpper.pitch - 0.6981317F;
            rArmLower.pitch = rArmUpper.pitch + 0.6981317F;
            lLegLower.pitch = lLegUpper.pitch - 0.6981317F;
            lArmLower.pitch = lArmUpper.pitch + 0.6981317F;
        } else {
            body.setPivot(0F, 16F, 0F);
            body.yaw = 3.141593F;
            body.pitch = 0F;
            lLegUpper.setPivot(-1F, 14F, -3.5F);
            rLegUpper.setPivot(1F, 14F, -3.5F);
            rArmUpper.setPivot(1F, 14F, 3.5F);
            lArmUpper.setPivot(-1F, 14F, 3.5F);
            tailBase.setPivot(0F, 15F, 3.5F);
            tailBase.pitch = 0F;
            tailBase.yaw = 3.141593F;
            tailBase.roll = 0F;
            tailMid.setPivot(0F, 11F, 3.5F);
            tailMid.pitch = 0F;
            tailMid.yaw = 3.141593F;
            tailMid.roll = 0F;
            tailTop.setPivot(0F, 9F, 3.5F);
            tailTop.pitch = 0F;
            tailTop.yaw = 3.141593F;
            tailTop.roll = 0F;
            face.setPivot(0F, 15F, -5F);
            head.setPivot(0F, 15F, -5F);

            rLegUpper.pitch = MathHelper.cos(f * 0.6662F) * .75F * f1;
            rArmUpper.pitch = MathHelper.cos(f * 0.6662F + 3.141593F) * .75F * f1;
            lLegUpper.pitch = MathHelper.cos(f * 0.6662F + 3.141593F) * .75F * f1;
            lArmUpper.pitch = MathHelper.cos(f * 0.6662F) * .75F * f1;
            rLegLower.setPivot(1F, 19F - (5 - MathHelper.sin(rLegUpper.pitch + 1.570796327F) * 5), -3.5F - (MathHelper.cos(rLegUpper.pitch + 1.570796327F) * 5));
            rArmLower.setPivot(1F, 19F - (5 - MathHelper.sin(rArmUpper.pitch + 1.570796327F) * 5), 3.5F - (MathHelper.cos(rArmUpper.pitch + 1.570796327F) * 5));
            lArmLower.setPivot(-1F, 19F - (5 - MathHelper.sin(lArmUpper.pitch + 1.570796327F) * 5), 3.5F - (MathHelper.cos(lArmUpper.pitch + 1.570796327F) * 5));
            lLegLower.setPivot(-1F, 19F - (5 - MathHelper.sin(lLegUpper.pitch + 1.570796327F) * 5), -3.5F - (MathHelper.cos(lLegUpper.pitch + 1.570796327F) * 5));
            rLegLower.pitch = rLegUpper.pitch;
            rArmLower.pitch = rArmUpper.pitch;
            lLegLower.pitch = lLegUpper.pitch;
            lArmLower.pitch = lArmUpper.pitch;

            tailBase.pitch = MathHelper.cos(f * 0.6662F) * .50F * f1;
            tailBase.roll = MathHelper.cos(f * 0.6662F) * .50F * f1;
            tailMid.setPivot(0F - (MathHelper.cos(tailBase.roll + ((float) Math.PI) / 2F) * 3), 11F + (3 - MathHelper.sin(tailBase.pitch + ((float) Math.PI) / 2F) * 3), 3.5F - (MathHelper.cos(tailBase.pitch + ((float) Math.PI) / 2F) * 3));
            tailMid.pitch = tailBase.pitch + MathHelper.cos(f * 0.6662F) * .75F * f1;
            tailMid.roll = tailBase.roll + MathHelper.cos(f * 0.6662F) * .75F * f1;
            tailTop.setPivot(0F - (MathHelper.cos(tailMid.roll + ((float) Math.PI) / 2F) * 2), 9F + (2 - MathHelper.sin(tailMid.pitch + ((float) Math.PI) / 2F) * 2), 3.5F - (MathHelper.cos(tailMid.pitch + ((float) Math.PI) / 2F) * 2));
            tailTop.pitch = tailMid.pitch + MathHelper.cos(f * 0.6662F) * 1.75F * f1;
            tailTop.roll = tailMid.pitch + MathHelper.cos(f * 0.6662F) * 1.75F * f1;
            herps = 0;
        }
    }

    @Override
    public void setArmAngle(Arm side, MatrixStack stack) {
        stack.translate(0.09375F, 0.1875F, 0.0F);
    }
}
 */
