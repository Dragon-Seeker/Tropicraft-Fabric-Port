package net.tropicraft.core.client.entity.models;

import net.minecraft.client.model.*;
import net.tropicraft.core.common.entity.placeable.BeachFloatEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;

public class BeachFloatModel extends CompositeEntityModel<BeachFloatEntity> {
    public ModelPart floatCross4;
    public ModelPart floatCross3;
    public ModelPart floatCross2;
    public ModelPart floatCross1;
    public ModelPart topFloatCross4;
    public ModelPart topFloatCross3;
    public ModelPart topFloatCross2;
    public ModelPart topFloatCross1;
    public ModelPart floatFoot;
    public ModelPart floatTop;
    public ModelPart headPillow;
    public ModelPart topBed;
    public ModelPart bottomBed;

    public BeachFloatModel(ModelPart root) {
        this.floatCross4 = root.getChild("floatCross4");
        this.floatCross3 = root.getChild("floatCross3");
        this.floatCross2 = root.getChild("floatCross2");
        this.floatCross1 = root.getChild("floatCross1");
        this.topFloatCross4 = root.getChild("topFloatCross4");
        this.topFloatCross3 = root.getChild("topFloatCross3");
        this.topFloatCross2 = root.getChild("topFloatCross2");
        this.topFloatCross1 = root.getChild("topFloatCross1");
        this.floatFoot = root.getChild("floatFoot");
        this.floatTop = root.getChild("floatTop");
        this.headPillow = root.getChild("headPillow");
        this.topBed = root.getChild("topBed");
        this.bottomBed = root.getChild("bottomBed");

        //floatCross4 = new ModelPart(this, 0, 0);
        //floatCross4.addCuboid(0F, -1F, -1F, 16, 2, 2);
        //floatCross4.setPivot(0F, 23F, -6F);

        //floatCross3 = new ModelPart(this, 0, 0);
        //floatCross3.addCuboid(0F, -1F, -1F, 16, 2, 2);
        //floatCross3.setPivot(0F, 23F, -2F);

        //floatCross2 = new ModelPart(this, 0, 0);
        //floatCross2.addCuboid(0F, -1F, -1F, 16, 2, 2);
        //floatCross2.setPivot(0F, 23F, 2F);

        //floatCross1 = new ModelPart(this, 0, 0);
        //floatCross1.addCuboid(0F, -1F, -1F, 16, 2, 2);
        //floatCross1.setPivot(0F, 23F, 6F);

        //topFloatCross4 = new ModelPart(this, 0, 0);
        //topFloatCross4.addCuboid(0F, -1F, -1F, 16, 2, 2);
        //topFloatCross4.setPivot(0F, 23F, -6F);
        //topFloatCross4.roll = 3.141593F;

        //topFloatCross3 = new ModelPart(this, 0, 0);
        //topFloatCross3.addCuboid(0F, -1F, -1F, 16, 2, 2);
        //topFloatCross3.setPivot(0F, 23F, -2F);
        //topFloatCross3.roll = 3.141593F;

        //topFloatCross2 = new ModelPart(this, 0, 0);
        //topFloatCross2.addCuboid(0F, 0F, 1F, 16, 2, 2);
        //topFloatCross2.setPivot(0F, 24F, 0F);
        //topFloatCross2.roll = 3.141593F;

        //topFloatCross1 = new ModelPart(this, 0, 0);
        //topFloatCross1.addCuboid(0F, -1F, -1F, 16, 2, 2);
        //topFloatCross1.setPivot(0F, 23F, 6F);
        //topFloatCross1.roll = 3.141593F;

        //floatFoot = new ModelPart(this, 0, 4);
        //floatFoot.addCuboid(-7F, -1F, 0F, 14, 2, 2);
        //floatFoot.setPivot(16F, 23F, 0F);
        //floatFoot.yaw = 1.570796F;

        //floatTop = new ModelPart(this, 0, 4);
        //floatTop.addCuboid(-7F, -1F, 0F, 14, 2, 2);
        //floatTop.setPivot(-17F, 24F, 0F);
        //floatTop.pitch = 1.570796F;
        //floatTop.yaw = -1.570796F;

        //headPillow = new ModelPart(this, 0, 13);
        //headPillow.addCuboid(-6F, -1.5F, -4F, 12, 2, 4);
        //headPillow.setPivot(-12F, 22F, 0F);
        //headPillow.yaw = 1.570796F;

        //topBed = new ModelPart(this, 0, 19);
        //topBed.addCuboid(-6F, -0.5F, -6F, 14, 1, 12);
        //topBed.setPivot(-6F, 22F, 0F);

        //bottomBed = new ModelPart(this, 0, 19);
        //bottomBed.addCuboid(-6F, -0.5F, -6F, 14, 1, 12);
        //bottomBed.setPivot(8F, 22F, 0F);
    }

    public static TexturedModelData getTexturedModelData(){
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("floatCross4", ModelPartBuilder.create().uv(0, 0).cuboid(0F, -1F, -1F, 16, 2, 2), ModelTransform.pivot(0F, 23F, -6F));
        modelPartData.addChild("floatCross3", ModelPartBuilder.create().uv(0, 0).cuboid(0F, -1F, -1F, 16, 2, 2), ModelTransform.pivot(0F, 23F, -2F));
        modelPartData.addChild("floatCross2", ModelPartBuilder.create().uv(0, 0).cuboid(0F, -1F, -1F, 16, 2, 2), ModelTransform.pivot(0F, 23F, 2F));
        modelPartData.addChild("floatCross1", ModelPartBuilder.create().uv(0, 0).cuboid(0F, -1F, -1F, 16, 2, 2), ModelTransform.pivot(0F, 23F, 6F));
        modelPartData.addChild("topFloatCross4", ModelPartBuilder.create().uv(0, 0).cuboid(0F, -1F, -1F, 16, 2, 2), ModelTransform.of(0F, 23F, -6F, 0F,0F, 3.141593F));
        modelPartData.addChild("topFloatCross3", ModelPartBuilder.create().uv(0, 0).cuboid(0F, -1F, -1F, 16, 2, 2), ModelTransform.of(0F, 23F, -2F, 0F,0F, 3.141593F));
        modelPartData.addChild("topFloatCross2", ModelPartBuilder.create().uv(0, 0).cuboid(0F, 0F, 1F, 16, 2, 2), ModelTransform.of(0F, 24F, 0F, 0F,0F, 3.141593F));
        modelPartData.addChild("topFloatCross1", ModelPartBuilder.create().uv(0, 0).cuboid(0F, -1F, -1F, 16, 2, 2), ModelTransform.of(0F, 23F, 6F, 0F,0F, 3.141593F));
        modelPartData.addChild("floatFoot", ModelPartBuilder.create().uv(0, 4).cuboid(-7F, -1F, 0F, 14, 2, 2), ModelTransform.of(16F, 23F, 0F, 0F,1.570796F, 0F));
        modelPartData.addChild("floatTop", ModelPartBuilder.create().uv(0, 4).cuboid(-7F, -1F, 0F, 14, 2, 2), ModelTransform.of(-17F, 24F, 0F, 1.570796F,-1.570796F, 0F));
        modelPartData.addChild("headPillow", ModelPartBuilder.create().uv(0, 13).cuboid(-6F, -1.5F, -4F, 12, 2, 4), ModelTransform.of(-12F, 22F, 0F, 0F,1.570796F, 0F));
        modelPartData.addChild("topBed", ModelPartBuilder.create().uv(0, 19).cuboid(-6F, -0.5F, -6F, 14, 1, 12), ModelTransform.pivot(-6F, 22F, 0F));
        modelPartData.addChild("bottomBed", ModelPartBuilder.create().uv(0, 19).cuboid(-6F, -0.5F, -6F, 14, 1, 12), ModelTransform.pivot(8F, 22F, 0F));

        return TexturedModelData.of(modelData,64,32);
    }

    @Override
    public void setAngles(BeachFloatEntity beachFloat, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(
            floatCross4, floatCross3, floatCross2, floatCross1,
            topFloatCross4, topFloatCross3, topFloatCross2, topFloatCross1,
            floatFoot, floatTop, headPillow, topBed, bottomBed
        );
    }

    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrixStackIn.push();
        matrixStackIn.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90));
        super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
    }
}

