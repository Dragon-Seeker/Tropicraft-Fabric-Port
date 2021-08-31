package net.tropicraft.core.client.entity.models;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.passive.FishEntity;
public abstract class AbstractFishModel<T extends FishEntity> extends CompositeEntityModel<T> {
    public static ModelPart root;
    public static ModelPart body;
    public static ModelPart tail;
    public AbstractFishModel(ModelPart root) {
        this.root = root;
        this.tail = root.getChild("tail");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        //body.addCuboid(0, 0, 0, 0, 1, 1);
        //tail.addCuboid(0, 0, 0, 0, 1, 1);
        //body.addChild(tail);
        modelPartData.addChild("body", ModelPartBuilder.create().cuboid(0,0,0,0,1,1), ModelTransform.pivot(0F,16F,0F));
        modelPartData.addChild("tail", ModelPartBuilder.create().cuboid(0,0,0,0,1,1), ModelTransform.pivot(0F,0F,1F));
        return TexturedModelData.of(modelData,0,0);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(body);
    }

    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        tail.yaw = (float) (Math.sin(ageInTicks * .25F)) * .25F;
    }
}