package net.tropicraft.core.client.entity.models;

import net.minecraft.client.model.*;
import net.tropicraft.core.common.entity.placeable.ChairEntity;
import com.google.common.collect.ImmutableList;
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

    public ChairModel(ModelPart root) {
        this.seat = root.getChild("seat");
        this.back = root.getChild("back");
        this.backRightLeg = root.getChild("backRightLeg");
        this.backLeftLeg = root.getChild("backLeftLeg");
        this.frontLeftLeg = root.getChild("frontLeftLeg");
        this.frontRightLeg = root.getChild("frontRightLeg");
        this.rightArm = root.getChild("rightArm");
        this.leftArm = root.getChild("leftArm");

        //seat = new ModelPart(this, 0, 0);
        //seat.addCuboid(-7F, 0F, -8F, 16, 1, 16, 0F);
        //seat.setPivot(-1F, 0F, 0F);

        //back = new ModelPart(this, 0, 0);
        //back.addCuboid(-7F, 0F, 0F, 16, 1, 16, 0F);
        //back.setPivot(-1F, 0F, 8F);
        //back.pitch = 1.169371F;

        //backRightLeg = new ModelPart(this, 0, 0);
        //backRightLeg.addCuboid(-1F, -1F, 0F, 1, 10, 1, 0F);
        //backRightLeg.setPivot(-8F, -3F, 6F);
        //backRightLeg.pitch = 0.4537856F;

        //backLeftLeg = new ModelPart(this, 0, 0);
        //backLeftLeg.addCuboid(0F, 0F, 0F, 1, 10, 1, 0F);
        //backLeftLeg.setPivot(8F, -4F, 5F);
        //backLeftLeg.pitch = 0.4537856F;

        //frontLeftLeg = new ModelPart(this, 0, 0);
        //frontLeftLeg.addCuboid(0F, 0F, -1F, 1, 10, 1, 0F);
        //frontLeftLeg.setPivot(8F, -4F, 0F);
        //frontLeftLeg.pitch = -0.4537856F;

        //frontRightLeg = new ModelPart(this, 0, 0);
        //frontRightLeg.addCuboid(-1F, 0F, -1F, 1, 10, 1, 0F);
        //frontRightLeg.setPivot(-8F, -4F, 0F);
        //frontRightLeg.pitch = -0.4537856F;

        //rightArm = new ModelPart(this, 0, 29);
        //rightArm.addCuboid(0F, -1F, 0F, 14, 1, 2, 0F);
        //rightArm.setPivot(-10F, -4F, 11F);
        //rightArm.yaw = 1.570796F;

        //leftArm = new ModelPart(this, 0, 29);
        //leftArm.addCuboid(0F, 0F, 0F, 14, 1, 2, 0F);
        //leftArm.setPivot(8F, -5F, 11F);
        //leftArm.yaw = 1.570796F;
    }

    public static TexturedModelData getTexturedModelData(){
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("seat", ModelPartBuilder.create().uv(0, 0).cuboid(-7F, 0F, -8F, 16, 1, 16), ModelTransform.pivot(-1F, 0F, 0F));
        modelPartData.addChild("back", ModelPartBuilder.create().uv(0, 0).cuboid(-7F, 0F, 0F, 16, 1, 16), ModelTransform.of(-1F, 0F, 8F, 1.169371F, 0F, 0F));
        modelPartData.addChild("backRightLeg", ModelPartBuilder.create().uv(0, 0).cuboid(-1F, -1F, 0F, 1, 10, 1), ModelTransform.of(-8F, -3F, 6F, 0.4537856F, 0F, 0F));
        modelPartData.addChild("backLeftLeg", ModelPartBuilder.create().uv(0, 0).cuboid(0F, 0F, 0F, 1, 10, 1), ModelTransform.of(8F, -4F, 5F, 0.4537856F, 0F, 0F));
        modelPartData.addChild("frontLeftLeg", ModelPartBuilder.create().uv(0, 0).cuboid(0F, 0F, -1F, 1, 10, 1), ModelTransform.of(8F, -4F, 0F, -0.4537856F, 0F, 0F));
        modelPartData.addChild("frontRightLeg", ModelPartBuilder.create().uv(0, 0).cuboid(-1F, 0F, -1F, 1, 10, 1), ModelTransform.of(-8F, -4F, 0F, -0.4537856F, 0F, 0F));
        modelPartData.addChild("rightArm", ModelPartBuilder.create().uv(0, 29).cuboid(0F, -1F, 0F, 14, 1, 2), ModelTransform.of(-10F, -4F, 11F, 0F, 1.570796F, 0F));
        modelPartData.addChild("leftArm", ModelPartBuilder.create().uv(0, 29).cuboid(0F, 0F, 0F, 14, 1, 2), ModelTransform.of(8F, -5F, 11F, 0F, 1.570796F, 0F));

        return TexturedModelData.of(modelData, 64, 32);
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


