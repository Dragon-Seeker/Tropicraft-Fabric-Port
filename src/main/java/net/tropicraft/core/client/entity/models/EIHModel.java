package net.tropicraft.core.client.entity.models;

import net.minecraft.client.model.*;
import net.tropicraft.core.common.entity.neutral.EIHEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.render.entity.model.CompositeEntityModel;

public class EIHModel extends CompositeEntityModel<EIHEntity> {
    private ModelPart body;
    private ModelPart base;
    private ModelPart nose;
    private ModelPart mouth;
    private ModelPart top;
    private ModelPart leye;
    private ModelPart reye;

    public EIHModel(ModelPart root) {
        this.body = root.getChild("body");
        this.base = root.getChild("base");
        this.nose = root.getChild("nose");
        this.mouth = root.getChild("mouth");
        this.top = root.getChild("top");
        this.leye = root.getChild("leye");
        this.reye = root.getChild("reye");

        /*
        body = new ModelPart(this, 34, 8);
        body.addCuboid(-4F, 1.0F, -1F, 8, 17, 7);
        body.setPivot(0.0F, -2F, 0.0F);
        base = new ModelPart(this, 0, 0);
        base.addCuboid(-4F, 11F, -3F, 8, 8, 11);
        base.setPivot(0.0F, 5F, -2F);
        nose = new ModelPart(this, 27, 2);
        nose.addCuboid(13.5F, -1F, -3F, 13, 2, 3);
        nose.setPivot(0.0F, -14.8F, -1F);
        nose.roll = 1.570796F;
        mouth = new ModelPart(this, 56, 11);
        mouth.addCuboid(-1.5F, 4F, -1F, 3, 3, 1);
        mouth.setPivot(0.0F, 7.5F, -0.5F);
        top = new ModelPart(this, 0, 17);
        top.addCuboid(-4F, -1F, -10F, 8, 5, 10);
        top.setPivot(0.0F, -5F, 6F);
        leye = new ModelPart(this, 56, 7);
        leye.addCuboid(0.0F, 0.0F, 0.0F, 3, 3, 1);
        leye.setPivot(1.0F, -1F, -2F);
        leye.mirror = true;
        reye = new ModelPart(this, 56, 7);
        reye.addCuboid(-1.5F, -1F, -1F, 3, 3, 1);
        reye.setPivot(-2.5F, 0.0F, -1F);
         */
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("body", ModelPartBuilder.create().uv(32,8).cuboid(-4F, 1.0F, -1F, 8, 17, 7), ModelTransform.pivot(0.0F,2F,0.0F));
        modelPartData.addChild("base", ModelPartBuilder.create().uv(0,0).cuboid(-4F, 11F, -3F, 8, 8, 11), ModelTransform.pivot(0.0F,5F,2F));
        modelPartData.addChild("nose", ModelPartBuilder.create().uv(27,2).cuboid(13.5F, -1F, -3F, 13, 2, 3), ModelTransform.of(0.0F,-14.8F,1F,0,0, 1.570796F));
        modelPartData.addChild("mouth", ModelPartBuilder.create().uv(56,11).cuboid(-1.5F, 4F, -1F, 3, 3, 1), ModelTransform.pivot(0.0F,7.5F,-0.5F));
        modelPartData.addChild("top", ModelPartBuilder.create().uv(0,17).cuboid(-4F, -1F, -10F, 8, 5, 10), ModelTransform.pivot(0.0F, -5F, 6F));
        modelPartData.addChild("leye", ModelPartBuilder.create().uv(56,7).mirrored().cuboid(0.0F, 0.0F, 0.0F, 3, 3, 1), ModelTransform.pivot(1.0F, -1F, -2F));
        modelPartData.addChild("reye", ModelPartBuilder.create().uv(56,7).cuboid(-1.5F, -1F, -1F, 3, 3, 1), ModelTransform.pivot(-2.5F, 0.0F, -1F));

        return TexturedModelData.of(modelData,64,32);
    }

    @Override
    public void setAngles(EIHEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // it's a statue, what do you want from me
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(body, base, nose, mouth, top, leye, reye);
    }


}

