package net.tropicraft.core.client.entity.models;

import net.tropicraft.core.common.entity.placeable.ChairEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;

public class ChairModel extends CompositeEntityModel<ChairEntity> {
    public ModelPart seat;
    public ModelPart back;
    public ModelPart backRightLeg;
    public ModelPart backLeftLeg;
    public ModelPart frontLeftLeg;
    public ModelPart frontRightLeg;
    public ModelPart rightArm;
    public ModelPart leftArm;

    public ChairModel() {
        seat = new ModelPart(this, 0, 0);
        seat.addCuboid(-7F, 0F, -8F, 16, 1, 16, 0F);
        seat.setPivot(-1F, 0F, 0F);

        back = new ModelPart(this, 0, 0);
        back.addCuboid(-7F, 0F, 0F, 16, 1, 16, 0F);
        back.setPivot(-1F, 0F, 8F);
        back.pitch = 1.169371F;

        backRightLeg = new ModelPart(this, 0, 0);
        backRightLeg.addCuboid(-1F, -1F, 0F, 1, 10, 1, 0F);
        backRightLeg.setPivot(-8F, -3F, 6F);
        backRightLeg.pitch = 0.4537856F;

        backLeftLeg = new ModelPart(this, 0, 0);
        backLeftLeg.addCuboid(0F, 0F, 0F, 1, 10, 1, 0F);
        backLeftLeg.setPivot(8F, -4F, 5F);
        backLeftLeg.pitch = 0.4537856F;

        frontLeftLeg = new ModelPart(this, 0, 0);
        frontLeftLeg.addCuboid(0F, 0F, -1F, 1, 10, 1, 0F);
        frontLeftLeg.setPivot(8F, -4F, 0F);
        frontLeftLeg.pitch = -0.4537856F;

        frontRightLeg = new ModelPart(this, 0, 0);
        frontRightLeg.addCuboid(-1F, 0F, -1F, 1, 10, 1, 0F);
        frontRightLeg.setPivot(-8F, -4F, 0F);
        frontRightLeg.pitch = -0.4537856F;

        rightArm = new ModelPart(this, 0, 29);
        rightArm.addCuboid(0F, -1F, 0F, 14, 1, 2, 0F);
        rightArm.setPivot(-10F, -4F, 11F);
        rightArm.yaw = 1.570796F;

        leftArm = new ModelPart(this, 0, 29);
        leftArm.addCuboid(0F, 0F, 0F, 14, 1, 2, 0F);
        leftArm.setPivot(8F, -5F, 11F);
        leftArm.yaw = 1.570796F;
    }

    @Override
    public void setAngles(ChairEntity chair, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(
            seat, back, backRightLeg, backLeftLeg, frontLeftLeg, frontRightLeg, rightArm, leftArm
        );
    }
}
