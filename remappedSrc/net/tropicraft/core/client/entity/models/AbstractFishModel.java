package net.tropicraft.core.client.entity.models;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.animal.AbstractFish;

public abstract class AbstractFishModel<T extends AbstractFish> extends ListModel<T> {
    public ModelPart body;
    public ModelPart tail;

    public AbstractFishModel() {
        body = new ModelPart(this);
        body.setPos(0F, 16F, 0F);
        body.addBox(0, 0, 0, 0, 1, 1);
        tail = new ModelPart(this);
        tail.setPos(0, 0, -1);
        tail.addBox(0, 0, 0, 0, 1, 1);
        body.addChild(tail);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(body);
    }



    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        tail.yRot = (float) (Math.sin(ageInTicks * .25F)) * .25F;
    }
}