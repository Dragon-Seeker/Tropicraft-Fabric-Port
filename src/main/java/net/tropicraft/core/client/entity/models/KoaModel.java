package net.tropicraft.core.client.entity.models;

import net.minecraft.client.model.*;
import net.tropicraft.core.common.entity.passive.EntityKoaBase;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class KoaModel extends BipedEntityModel<EntityKoaBase> {
    //TODO: 1.17 has completly broken this so IDk if its needed as it had the renderSystem cull call commented
//    private static class ModelRendererCull extends ModelPart {
//
//        public ModelRendererCull(Model model, int texOffX, int texOffY) {
//            super(model, texOffX, texOffY);
//        }
//
//        @Override
//        public void render(MatrixStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn) {
//            RenderSystem.enableCull();
//            super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
//            RenderSystem.disableCull();
//        }
//    }

    public ModelPart headband;
    public ModelPart armbandR;
    public ModelPart leaf1;
    public ModelPart leaf3;
    public ModelPart leaf2;
    public ModelPart leaf4;
    public ModelPart leaf5;
    public ModelPart leaf6;
    public ModelPart leaf7;
    public ModelPart leaf8;
    public ModelPart leaf9;
    public ModelPart leaf10;
    public ModelPart armbandL;

    public KoaModel(ModelPart root){
        super(root);
        headband = this.head.getChild("headband");
        armbandR = this.rightArm.getChild("armbandR");
        armbandL = this.leftArm.getChild("armbandL");
        leaf1 = this.headband.getChild("leaf1");
        leaf3 = this.headband.getChild("leaf3");
        leaf2 = this.headband.getChild("leaf2");
        leaf4 = this.headband.getChild("leaf4");
        leaf5 = this.headband.getChild("leaf5");
        leaf6 = this.headband.getChild("leaf6");
        leaf7 = this.headband.getChild("leaf7");
        leaf8 = this.headband.getChild("leaf8");
        leaf9 = this.headband.getChild("leaf9");
        leaf10 = this.headband.getChild("leaf10");

        hat.visible = false;

        /*
        super(modelSize);
        head = new ModelPart(this, 0, 2);
        head.addCuboid(-4F, -8F, -4F, 8, 8, 8);
        head.setPivot(0F, 0F, 0F);
        head.setTextureSize(64, 32);
        head.mirror = true;
        setRotation(head, 0F, 0F, 0F);

        headband = new ModelPart(this, 24, 1);
        headband.addCuboid(-5F, 0F, -5F, 10, 2, 10);
        headband.setPivot(0F, -7F, 0F);        //0,-7,0 before
        headband.setTextureSize(64, 32);
        headband.mirror = true;
        head.addChild(headband);
        setRotation(headband, 0F, 0F, 0F);

        armbandR = new ModelPart(this, 35, 6);
        armbandR.addCuboid(2.5F, -2F, -2.5F, 5, 1, 5);    //offset, dimensions
        armbandR.setPivot(-6F, 3F, 0F);    //position
        armbandR.setTextureSize(64, 32);
        rightArm.addChild(armbandR);
        setRotation(armbandR, 0F, 0F, 0F);

        armbandL = new ModelPart(this, 34, 1);
        armbandL.addCuboid(-7.5F, -2F, -2.5F, 5, 1, 5);        //offset, dimensions
        armbandL.setPivot(6F, 3F, 0F);        //position
        armbandL.setTextureSize(64, 32);
        armbandL.mirror = true;
        leftArm.addChild(armbandL);
        setRotation(armbandL, 0F, 0F, 0F);

        leaf = new ModelRendererCull(this, 0, 0);
        leaf.addCuboid(0F, 7F, 0F, 1, 0, 1);
        leaf.setPivot(2F, -6F, -6F);
        leaf.setTextureSize(64, 32);
        leaf.mirror = true;
        headband.addChild(leaf);
        setRotation(leaf, 0F, 0F, 0F);

        leaf3 = new ModelRendererCull(this, 0, 0);
        leaf3.addCuboid(0F, 7F, 0F, 1, 0, 1);
        leaf3.setPivot(-1F, -6F, -6F);
        leaf3.setTextureSize(64, 32);
        leaf3.mirror = true;
        headband.addChild(leaf3);
        setRotation(leaf3, 0F, 0F, 0F);

        leaf2 = new ModelRendererCull(this, 0, 0);
        leaf2.addCuboid(0F, 7F, 0F, 1, 0, 1);
        leaf2.setPivot(-4F, -6F, -6F);
        leaf2.setTextureSize(64, 32);
        leaf2.mirror = true;
        headband.addChild(leaf2);
        setRotation(leaf2, 0F, 0F, 0F);

        leaf4 = new ModelRendererCull(this, 0, 0);
        leaf4.addCuboid(0F, 7F, 0F, 1, 0, 1);
        leaf4.setPivot(0F, -7F, -6F);
        leaf4.setTextureSize(64, 32);
        leaf4.mirror = true;
        headband.addChild(leaf4);
        setRotation(leaf4, 0F, 0F, 0F);

        leaf5 = new ModelRendererCull(this, 0, 0);
        leaf5.addCuboid(0F, 7F, 0F, 1, 0, 1);
        leaf5.setPivot(5F, -6F, -1F);
        leaf5.setTextureSize(64, 32);
        leaf5.mirror = true;
        headband.addChild(leaf5);
        setRotation(leaf5, 0F, 0F, 0F);

        leaf6 = new ModelRendererCull(this, 0, 0);
        leaf6.addCuboid(0F, 7F, 0F, 1, 0, 1);
        leaf6.setPivot(5F, -6F, 3F);
        leaf6.setTextureSize(64, 32);
        leaf6.mirror = true;
        headband.addChild(leaf6);
        setRotation(leaf6, 0F, 0F, 0F);

        leaf7 = new ModelRendererCull(this, 0, 0);
        leaf7.addCuboid(0F, 7F, 0F, 1, 0, 1);
        leaf7.setPivot(-6F, -6F, 0F);
        leaf7.setTextureSize(64, 32);
        leaf7.mirror = true;
        headband.addChild(leaf7);
        setRotation(leaf7, 0F, 0F, 0F);

        leaf8 = new ModelRendererCull(this, 0, 0);
        leaf8.addCuboid(0F, 7F, 0F, 1, 0, 1);
        leaf8.setPivot(-6F, -6F, -4F);
        leaf8.setTextureSize(64, 32);
        leaf8.mirror = true;
        headband.addChild(leaf8);
        setRotation(leaf8, 0F, 0F, 0F);

        leaf9 = new ModelRendererCull(this, 0, 0);
        leaf9.addCuboid(0F, 7F, 0F, 1, 0, 1);
        leaf9.setPivot(-2F, -6F, 5F);
        leaf9.setTextureSize(64, 32);
        leaf9.mirror = true;
        headband.addChild(leaf9);
        setRotation(leaf9, 0F, 0F, 0F);

        leaf10 = new ModelRendererCull(this, 0, 0);
        leaf10.addCuboid(0F, 7F, 0F, 1, 0, 1);
        leaf10.setPivot(2F, -6F, 5F);
        leaf10.setTextureSize(64, 32);
        leaf10.mirror = true;
        headband.addChild(leaf10);
        setRotation(leaf10, 0F, 0F, 0F);
        
        hat.visible = false;

         */
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData(Dilation.NONE, 0F);
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData modelPartHead = modelPartData.addChild("head",
                ModelPartBuilder.create()
                        .uv(0, 2)
                        .mirrored()
                        .cuboid(-4F, -8F, -4F, 8, 8, 8),
                ModelTransform.pivot(0F, 0F, 0F));

        ModelPartData modelPartHeadBand = modelPartHead.addChild("headband",
                ModelPartBuilder.create()
                        .uv(24, 1)
                        .mirrored()
                        .cuboid(-5F, 0F, -5F, 10, 2, 10),
                ModelTransform.pivot(0F, -7F, 0F));

        ModelPartData modelPartRightArm = modelPartData.getChild("right_arm");
        modelPartRightArm.addChild("armbandR",
                ModelPartBuilder.create()
                        .uv(35, 6)
                        .mirrored()
                        .cuboid(2.5F, -2F, -2.5F, 5, 1, 5),
                ModelTransform.pivot(-6F, 3F, 0F));

        ModelPartData modelPartLeftArm = modelPartData.getChild("left_arm");
        modelPartLeftArm.addChild("armbandL",
                ModelPartBuilder.create()
                        .uv(34, 1)
                        .mirrored()
                        .cuboid(-7.5F, -2F, -2.5F, 5, 1, 5),
                ModelTransform.pivot(6F, 3F, 0F));


        modelPartHeadBand.addChild("leaf1",
                leafModelBuilder(),
                ModelTransform.pivot(2F, -6F, -6F));
        modelPartHeadBand.addChild("leaf3",
                leafModelBuilder(),
                ModelTransform.pivot(-1F, -6F, -6F));
        modelPartHeadBand.addChild("leaf2",
                leafModelBuilder(),
                ModelTransform.pivot(-4F, -6F, -6F));
        modelPartHeadBand.addChild("leaf4",
                leafModelBuilder(),
                ModelTransform.pivot(0F, -7F, -6F));
        modelPartHeadBand.addChild("leaf5",
                leafModelBuilder(),
                ModelTransform.pivot(5F, -6F, -1F));
        modelPartHeadBand.addChild("leaf6",
                leafModelBuilder(),
                ModelTransform.pivot(5F, -6F, 3F));
        modelPartHeadBand.addChild("leaf7",
                leafModelBuilder(),
                ModelTransform.pivot(-6F, -6F, 0F));
        modelPartHeadBand.addChild("leaf8",
                leafModelBuilder(),
                ModelTransform.pivot(-6F, -6F, -4F));
        modelPartHeadBand.addChild("leaf9",
                leafModelBuilder(),
                ModelTransform.pivot(-2F, -6F, 5F));
        modelPartHeadBand.addChild("leaf10",
                leafModelBuilder(),
                ModelTransform.pivot(2F, -6F, 5F));

        return TexturedModelData.of(modelData, 64,32);
    }

    public static ModelPartBuilder leafModelBuilder(){
        return ModelPartBuilder.create()
                .uv(0, 0)
                .mirrored()
                .cuboid(0F, 7F, 0F, 1, 0, 1);
    }

    @Override
    public void render(MatrixStack ms, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn,
            float red, float green, float blue, float alpha) {

        ms.push();
        if (riding) {
            if (child) {
                ms.translate(0, 0.3, 0);
            } else {
                ms.translate(0, 0.6, 0);
            }
        }
        
        if (this.child) {
            ms.push();
            ms.scale(0.75F, 0.75F, 0.75F);
            ms.translate(0.0F, 1.0F, 0.0F);
            this.head.render(ms, bufferIn, packedLightIn, packedOverlayIn);
            ms.pop();
            ms.push();
            ms.scale(0.5F, 0.5F, 0.5F);
            ms.translate(0.0F, 1.5F, 0.0F);
            this.body.render(ms, bufferIn, packedLightIn, packedOverlayIn);
            this.rightArm.render(ms, bufferIn, packedLightIn, packedOverlayIn);
            this.leftArm.render(ms, bufferIn, packedLightIn, packedOverlayIn);
            this.rightLeg.render(ms, bufferIn, packedLightIn, packedOverlayIn);
            this.leftLeg.render(ms, bufferIn, packedLightIn, packedOverlayIn);
            ms.pop();
        } else {
            head.render(ms, bufferIn, packedLightIn, packedOverlayIn);
            body.render(ms, bufferIn, packedLightIn, packedOverlayIn);
            rightArm.render(ms, bufferIn, packedLightIn, packedOverlayIn);
            leftArm.render(ms, bufferIn, packedLightIn, packedOverlayIn);
            rightLeg.render(ms, bufferIn, packedLightIn, packedOverlayIn);
            leftLeg.render(ms, bufferIn, packedLightIn, packedOverlayIn);
        }
        ms.pop();
    }

    @Override
    public void setAngles(EntityKoaBase entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        hat.visible = false;

        riding = entityIn.isSitting() || entityIn.hasVehicle();
        final boolean isDancing = entityIn.isDancing();


        float ticks = (entityIn.age + MinecraftClient.getInstance().getTickDelta()) % 360;

        final double headRot = Math.cos(Math.toRadians(ticks * 35F));
        if (isDancing) {
            // TODO remove translate calls in this method
       //     RenderSystem.translated(0, 0.01F + (float)Math.sin(Math.toRadians(ticks * 35F)) * 0.02F, 0);
      //      RenderSystem.translated((float) headRot * 0.02F, 0, 0);
//            bipedHead.offsetY = 0.01F + (float)Math.sin(Math.toRadians(ticks * 35F)) * 0.02F;
//            bipedHead.offsetX = (float)Math.cos(Math.toRadians(ticks * 35F)) * 0.02F;
//            bipedHead.offsetZ = 0;
            head.roll = (float) headRot * 0.05F;
        } else {
           // RenderSystem.translated(0, 0, 0);
//            bipedHead.offsetY = 0;
//            bipedHead.offsetX = 0;
//            bipedHead.offsetZ = 0;
            head.roll = 0;
        }

        super.setAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        if (isDancing) {
            this.head.pitch += (float) Math.sin(Math.toRadians((entityIn.world.getTime() % 360) * 35F)) * 0.05F;

            float amp = 0.5F;

            final double armRot = Math.sin(Math.toRadians(ticks * 35F));
            double x = Math.PI + Math.PI / 4 + (float) armRot * amp;
            double y = armRot * amp;
            double z = (float) headRot * amp;

            rightArm.pitch += x;
            rightArm.yaw += y;
            rightArm.roll += z;

            leftArm.pitch += x;
            leftArm.yaw += y;
            leftArm.roll += z;
        }
    }

    //private void setRotation(ModelPart model, float x, float y, float z) {
    //    model.pitch = x;
    //    model.yaw = y;
    //    model.roll = z;
    //}
}


