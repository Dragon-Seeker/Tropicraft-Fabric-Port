package net.tropicraft.core.client.entity.models;

import net.tropicraft.core.common.entity.underdasea.ManOWarEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;

public class ManOWarModel extends CompositeEntityModel<ManOWarEntity> {
    ModelPart Body;
    ModelPart CenterTent;
    ModelPart CenterTent2;
    ModelPart CenterTent3;
    ModelPart Tent1;
    ModelPart Tent2;
    ModelPart Tent3;
    ModelPart Tent4;
    public boolean isOnGround;

    public ManOWarModel(int i, int j, boolean derp) {
        isOnGround = false;
        textureWidth = 64;
        textureHeight = 32;

        final float delta = 0.001f;
        Body = new ModelPart(this);
        Body.setPivot(0F, 18F, 0F);
        setRotation(Body, 0F, 0F, 0F);
        Body.mirror = true;
        Body.addCuboid("float", -2F, -4F, -2F, 4, 4, 8, delta, i, j);
        Body.addCuboid("Shape1", 0F, -6F, -2F, 0, 6, 10, delta, derp ? 32 : 15, derp ? 20 : -10);
        int tbOX = derp ? 0 : 32;
        int tbOY = derp ? 14 : 20;
        Body.addCuboid("tentbase", -2F, 0F, -2F, 4, 2, 4, delta, tbOX, tbOY);
        CenterTent = new ModelPart(this);
        CenterTent.setPivot(0F, 2F, 0F);
        setRotation(CenterTent, 0F, 0F, 0F);
        CenterTent.mirror = true;
        CenterTent.addCuboid("tent51", -0.5F, 0F, -0.5F, 1, 10, 1, delta, derp ? 7 : 32, derp ? 0 : 20);
        CenterTent2 = new ModelPart(this);
        CenterTent2.setPivot(0F, 10F, 0F);
        setRotation(CenterTent2, 0F, 0F, 0F);
        CenterTent2.mirror = true;
        CenterTent2.addCuboid("tent52", -0.5F, 0F, -0.5F, 1, 4, 1, delta, derp ? 11 : 32, derp ? 0 : 20);
        CenterTent3 = new ModelPart(this);
        CenterTent3.setPivot(0F, 4F, 0F);
        setRotation(CenterTent3, 0F, 0F, 0F);
        CenterTent3.mirror = true;
        CenterTent3.addCuboid("tent53", -0.5F, 0F, -0.5F, 1, 5, 1, delta, derp ? 11 : 32, derp ? 5 : 20);
        CenterTent2.addChild(CenterTent3);
        CenterTent.addChild(CenterTent2);
        Body.addChild(CenterTent);
        Tent1 = new ModelPart(this);
        Tent1.setPivot(-1.5F, 2F, -1.5F);
        setRotation(Tent1, 0F, 0F, 0F);
        Tent1.mirror = true;
        Tent1.addCuboid("tent1", -0.5F, 0F, -0.5F, 1, 11, 1, delta, derp ? 0 : 32, derp ? 0 : 20);
        Body.addChild(Tent1);
        Tent2 = new ModelPart(this);
        Tent2.setPivot(1.5F, 2F, 1.5F);
        setRotation(Tent2, 0F, 0F, 0F);
        Tent2.mirror = true;
        Tent2.addCuboid("tent2", -0.5F, 0F, -0.5F, 1, 11, 1, delta, derp ? 0 : 32, derp ? 0 : 20);
        Body.addChild(Tent2);
        Tent3 = new ModelPart(this);
        Tent3.setPivot(-1.5F, 2F, 1.5F);
        setRotation(Tent3, 0F, 0F, 0F);
        Tent3.mirror = true;
        Tent3.addCuboid("tent3", -0.5F, 0F, -0.5F, 1, 11, 1, delta, derp ? 0 : 32, derp ? 0 : 20);
        Body.addChild(Tent3);
        Tent4 = new ModelPart(this);
        Tent4.setPivot(1.5F, 2F, -1.5F);
        setRotation(Tent4, 0F, 0F, 0F);
        Tent4.mirror = true;
        Tent4.addCuboid("tent4", -0.5F, 0F, -0.5F, 1, 11, 1, delta, derp ? 0 : 32, derp ? 0 : 20);
        Body.addChild(Tent4);
    }

    private void setRotation(ModelPart model, float x, float y, float z) {
        model.pitch = x;
        model.yaw = y;
        model.roll = z;
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(Body);
    }

    @Override
    public void setAngles(ManOWarEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isOnGround()) {
            Tent3.roll = 0F;
            Tent3.pitch = 0F;
            Tent1.roll = 0F;
            Tent1.pitch = 0F;
            Tent4.roll = 0F;
            Tent4.pitch = 0F;
            Tent2.roll = 0f;
            Tent2.pitch = 0F;
            CenterTent.pitch = 0F;
            CenterTent2.pitch = 0F;
            CenterTent3.pitch = 0F;
        } else {
            Tent3.roll = (float) (Math.sin(ageInTicks * .1F)) * .07F + .4F;
            Tent3.pitch = (float) (Math.sin(ageInTicks * .1F)) * .05F + .4F;
            Tent1.roll = -(float) (Math.sin(ageInTicks * .1F)) * .06F + .4F;
            Tent1.pitch = -(float) (Math.sin(ageInTicks * .1F)) * .05F + .4F;
            Tent4.roll = -(float) (Math.sin(ageInTicks * .1F)) * .06F - .4F;
            Tent4.pitch = -(float) (Math.sin(ageInTicks * .1F)) * .04F + .4F;
            Tent2.roll = (float) (Math.sin(ageInTicks * .025F)) * .05F - .4f;
            Tent2.pitch = (float) (Math.sin(ageInTicks * .025F)) * .05F + .4F;
            CenterTent.pitch = (float) (Math.sin(ageInTicks * .0125F)) * .05F + .2F;
            CenterTent2.pitch = (float) (Math.sin(ageInTicks * .0125F)) * .65F + 1.507F;
            CenterTent3.pitch = Math.abs((float) (Math.sin(ageInTicks * .0125F)) * .35F) + -1.25F;
        }
    }
}
