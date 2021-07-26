package net.tropicraftFabric.client.entity.models;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.passive.FishEntity;

public abstract class AbstractFishModel<T extends FishEntity> extends CompositeEntityModel<T> {
    public ModelPart body;
    public ModelPart tail;

    public AbstractFishModel() {
        body = new ModelPart(this);
        body.setPivot(0F, 16F, 0F);
        body.addCuboid(0, 0, 0, 0, 1, 1);
        tail = new ModelPart(this);
        tail.setPivot(0, 0, -1);
        tail.addCuboid(0, 0, 0, 0, 1, 1);
        body.addChild(tail);
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