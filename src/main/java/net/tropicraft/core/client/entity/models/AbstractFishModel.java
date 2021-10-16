package net.tropicraft.core.client.entity.models;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.animal.AbstractFish;
public abstract class AbstractFishModel<T extends AbstractFish> extends ListModel<T> {
    public static ModelPart root;
    public static ModelPart body;
    public static ModelPart tail;
    public AbstractFishModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.tail = this.body.getChild("tail");
    }

    public static LayerDefinition getTexturedModelData() {
        /*
        body = new ModelPart(this);
        body.setPivot(0F, 16F, 0F);
        body.addCuboid(0, 0, 0, 0, 1, 1);
        tail = new ModelPart(this);
        tail.setPivot(0, 0, -1);
        tail.addCuboid(0, 0, 0, 0, 1, 1);
        body.addChild(tail);
         */

        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        //body.addCuboid(0, 0, 0, 0, 1, 1);
        //tail.addCuboid(0, 0, 0, 0, 1, 1);
        //body.addChild(tail);
        PartDefinition modelPartDataBody = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().addBox(0,0,0,0,1,1), PartPose.offset(0F,16F,0F));
        modelPartDataBody.addOrReplaceChild("tail", CubeListBuilder.create().addBox(0,0,0,0,1,1), PartPose.offset(0F,0F,-1F));
        return LayerDefinition.create(modelData,0,0);
    }

    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(body);
    }

    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        tail.yRot = (float) (Math.sin(ageInTicks * .25F)) * .25F;
    }
}