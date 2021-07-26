package net.tropicraftFabric.client.entity.models;

import net.tropicraftFabric.common.entity.neutral.IguanaEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

public class IguanaModel extends CompositeEntityModel<IguanaEntity> {
    public ModelPart head;
    public ModelPart headTop1;
    public ModelPart headTop2;
    public ModelPart body;
    public ModelPart frontLeftLeg;
    public ModelPart rearLeftLeg;
    public ModelPart frontRightLeg;
    public ModelPart rearRightLeg;
    public ModelPart back1;
    public ModelPart back2;
    public ModelPart back3;
    public ModelPart jaw;
    public ModelPart dewLap;
    public ModelPart tailBase;
    public ModelPart tailMid;
    public ModelPart miscPart;

    public IguanaModel() {
        head = new ModelPart(this, 36, 23);
        head.addCuboid(-2.5F, -2F, -6F, 5, 3, 6);
        head.setPivot(0F, 20F, -6F);

        body = new ModelPart(this, 0, 16);
        body.addCuboid(-2.5F, -1.5F, -7.5F, 5, 3, 13);
        body.setPivot(0F, 21.5F, 1F);

        frontLeftLeg = new ModelPart(this, 24, 21);
        frontLeftLeg.addCuboid(0F, 0F, -1.5F, 2, 3, 3);
        frontLeftLeg.setPivot(2.5F, 21F, -4F);

        rearLeftLeg = new ModelPart(this, 24, 21);
        rearLeftLeg.addCuboid(0F, 0F, -1.5F, 2, 3, 3);
        rearLeftLeg.setPivot(2.5F, 21F, 4F);

        frontRightLeg = new ModelPart(this, 0, 21);
        frontRightLeg.addCuboid(-2F, 0F, -1.5F, 2, 3, 3);
        frontRightLeg.setPivot(-2.5F, 21F, -4F);

        rearRightLeg = new ModelPart(this, 0, 21);
        rearRightLeg.addCuboid(-2F, 0F, -1.5F, 2, 3, 3);
        rearRightLeg.setPivot(-2.5F, 21F, 4F);

        back1 = new ModelPart(this, 0, 0);
        back1.addCuboid(-1.5F, -1F, 0F, 3, 1, 10);
        back1.setPivot(0F, 20F, -5F);

        back2 = new ModelPart(this, 32, 0);
        back2.addCuboid(-0.5F, -1F, -3F, 1, 1, 6);
        back2.setPivot(0F, 19F, 0F);

        headTop2 = new ModelPart(this, 0, 0);
        headTop2.addCuboid(-0.5F, -4F, -4F, 1, 1, 2);
        headTop2.setPivot(0F, 20F, -6F);

        headTop1 = new ModelPart(this, 32, 7);
        headTop1.addCuboid(-0.5F, -3F, -5F, 1, 1, 4);
        headTop1.setPivot(0F, 20F, -6F);

        jaw = new ModelPart(this, 0, 11);
        jaw.addCuboid(-1F, 1F, -4F, 2, 1, 4);
        jaw.setPivot(0F, 20F, -6F);

        back3 = new ModelPart(this, 32, 7);
        back3.addCuboid(-0.5F, 0F, -2F, 1, 1, 4);
        back3.setPivot(0F, 17F, 0F);

        dewLap = new ModelPart(this, 0, 4);
        dewLap.addCuboid(-0.5F, 2F, -3F, 1, 1, 3);
        dewLap.setPivot(0F, 20F, -6F);

        tailBase = new ModelPart(this, 46, 0);
        tailBase.addCuboid(-1.5F, -0.5F, 0F, 3, 1, 6);
        tailBase.setPivot(0F, 21.5F, 6F);

        tailMid = new ModelPart(this, 48, 7);
        tailMid.addCuboid(-1F, -0.5F, 0F, 2, 1, 6);
        miscPart = new ModelPart(this, 52, 14);
        miscPart.addCuboid(-0.5F, -0.5F, 0F, 1, 1, 5);
    }


    @Override
    public void setAngles(IguanaEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        final float magicHeadRotationAmt = 57.29578F;
        head.pitch = headPitch / magicHeadRotationAmt;
        head.yaw = headYaw / magicHeadRotationAmt;
        jaw.pitch = head.pitch;
        jaw.yaw = head.yaw;
        headTop2.pitch = head.pitch;
        headTop2.yaw = head.yaw;
        headTop1.pitch = head.pitch;
        headTop1.yaw = head.yaw;
        dewLap.pitch = head.pitch;
        dewLap.yaw = head.yaw;
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(head, body, frontLeftLeg, rearLeftLeg, frontRightLeg, rearRightLeg, back1, back2, headTop2, headTop1, jaw,
                back3, dewLap, tailBase, tailMid, miscPart);
    }

    @Override
    public void animateModel(final IguanaEntity iggy, float f, float f1, float f2) {
        frontRightLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.75F * f1;
        frontLeftLeg.pitch = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.75F * f1;
        rearRightLeg.pitch = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.75F * f1;
        rearLeftLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.75F * f1;
        tailBase.yaw = MathHelper.cos(f * 0.6662F) * .25F * f1;
        tailMid.setPivot(0F - (MathHelper.cos(tailBase.yaw + 1.570796F) * 6), 21.5F, 12F + MathHelper.sin(tailBase.pitch + 3.14159F) * 6);
        tailMid.yaw = tailBase.yaw + MathHelper.cos(f * 0.6662F) * .50F * f1;
        miscPart.setPivot(0F - (MathHelper.cos(tailMid.yaw + 1.570796F) * 6), 21.5F, 18F + MathHelper.sin(tailMid.pitch + 3.14159F) * 6);
        miscPart.yaw = tailMid.yaw + MathHelper.cos(f * 0.6662F) * .75F * f1;;
    }
}
