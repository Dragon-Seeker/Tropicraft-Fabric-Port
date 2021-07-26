package net.tropicraftFabric.client.entity.models;

import net.tropicraftFabric.common.entity.placeable.BeachFloatEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
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

    public BeachFloatModel() {
        floatCross4 = new ModelPart(this, 0, 0);
        floatCross4.addCuboid(0F, -1F, -1F, 16, 2, 2, 0F);
        floatCross4.setPivot(0F, 23F, -6F);

        floatCross3 = new ModelPart(this, 0, 0);
        floatCross3.addCuboid(0F, -1F, -1F, 16, 2, 2, 0F);
        floatCross3.setPivot(0F, 23F, -2F);

        floatCross2 = new ModelPart(this, 0, 0);
        floatCross2.addCuboid(0F, -1F, -1F, 16, 2, 2, 0F);
        floatCross2.setPivot(0F, 23F, 2F);

        floatCross1 = new ModelPart(this, 0, 0);
        floatCross1.addCuboid(0F, -1F, -1F, 16, 2, 2, 0F);
        floatCross1.setPivot(0F, 23F, 6F);

        topFloatCross4 = new ModelPart(this, 0, 0);
        topFloatCross4.addCuboid(0F, -1F, -1F, 16, 2, 2, 0F);
        topFloatCross4.setPivot(0F, 23F, -6F);
        topFloatCross4.roll = 3.141593F;

        topFloatCross3 = new ModelPart(this, 0, 0);
        topFloatCross3.addCuboid(0F, -1F, -1F, 16, 2, 2, 0F);
        topFloatCross3.setPivot(0F, 23F, -2F);
        topFloatCross3.roll = 3.141593F;

        topFloatCross2 = new ModelPart(this, 0, 0);
        topFloatCross2.addCuboid(0F, 0F, 1F, 16, 2, 2, 0F);
        topFloatCross2.setPivot(0F, 24F, 0F);
        topFloatCross2.roll = 3.141593F;

        topFloatCross1 = new ModelPart(this, 0, 0);
        topFloatCross1.addCuboid(0F, -1F, -1F, 16, 2, 2, 0F);
        topFloatCross1.setPivot(0F, 23F, 6F);
        topFloatCross1.roll = 3.141593F;

        floatFoot = new ModelPart(this, 0, 4);
        floatFoot.addCuboid(-7F, -1F, 0F, 14, 2, 2, 0F);
        floatFoot.setPivot(16F, 23F, 0F);
        floatFoot.yaw = 1.570796F;

        floatTop = new ModelPart(this, 0, 4);
        floatTop.addCuboid(-7F, -1F, 0F, 14, 2, 2, 0F);
        floatTop.setPivot(-17F, 24F, 0F);
        floatTop.pitch = 1.570796F;
        floatTop.yaw = -1.570796F;

        headPillow = new ModelPart(this, 0, 13);
        headPillow.addCuboid(-6F, -1.5F, -4F, 12, 2, 4, 0F);
        headPillow.setPivot(-12F, 22F, 0F);
        headPillow.yaw = 1.570796F;

        topBed = new ModelPart(this, 0, 19);
        topBed.addCuboid(-6F, -0.5F, -6F, 14, 1, 12, 0F);
        topBed.setPivot(-6F, 22F, 0F);

        bottomBed = new ModelPart(this, 0, 19);
        bottomBed.addCuboid(-6F, -0.5F, -6F, 14, 1, 12, 0F);
        bottomBed.setPivot(8F, 22F, 0F);
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
