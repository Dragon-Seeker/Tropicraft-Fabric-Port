package net.tropicraft.core.client.entity.models;

import net.minecraft.client.model.*;
import net.tropicraft.core.common.entity.TropiBeeEntity;
import net.minecraft.client.render.entity.model.BeeEntityModel;

//TODO: MAY NEED SOME EDITS DUE TO THE NEW 1.17 SYSTEM
public class TropiBeeModel extends BeeEntityModel<TropiBeeEntity> {
    private final ModelPart hat1;
    private final ModelPart hat2;
    private final ModelPart hat3;

    public TropiBeeModel(ModelPart root) {
        super(root);

        ModelPart body = getBody();

        hat1 = body.getChild("hat1");
        hat2 = body.getChild("hat2");
        hat3 = body.getChild("hat3");
        /*
        hat1 = new ModelPart(this, 0, 32);
        hat1.addCuboid(-5F, -6F, -5F, 12, 1, 6);
        hat1.setPivot(-1F, 1F, -1F);
        hat1.mirror = true;
        ModelPart body = getBody();
        body.addChild(hat1);
        hat2 = new ModelPart(this, 0, 48);
        hat2.addCuboid(0F, -6F, 0F, 6, 2, 6);
        hat2.setPivot(-3F, -1F, -3F);
        hat2.mirror = false;
        body.addChild(hat2);
        hat3 = new ModelPart(this, 0, 32);
        hat3.addCuboid(-5F, -6F, 0F, 12, 1, 6);
        hat3.setPivot(-1F, 1F, 0F);
        hat3.mirror = false;
        body.addChild(hat3);
         */
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();

        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData modelPartData2 = modelPartData.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 19.0F, 0.0F));
        ModelPartData modelPartData3 = modelPartData2.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -4.0F, -5.0F, 7.0F, 7.0F, 10.0F), ModelTransform.NONE);
        modelPartData3.addChild("stinger", ModelPartBuilder.create().uv(26, 7).cuboid(0.0F, -1.0F, 5.0F, 0.0F, 1.0F, 2.0F), ModelTransform.NONE);
        modelPartData3.addChild("left_antenna", ModelPartBuilder.create().uv(2, 0).cuboid(1.5F, -2.0F, -3.0F, 1.0F, 2.0F, 3.0F), ModelTransform.pivot(0.0F, -2.0F, -5.0F));
        modelPartData3.addChild("right_antenna", ModelPartBuilder.create().uv(2, 3).cuboid(-2.5F, -2.0F, -3.0F, 1.0F, 2.0F, 3.0F), ModelTransform.pivot(0.0F, -2.0F, -5.0F));
        Dilation dilation = new Dilation(0.001F);
        modelPartData2.addChild("right_wing", ModelPartBuilder.create().uv(0, 18).cuboid(-9.0F, 0.0F, 0.0F, 9.0F, 0.0F, 6.0F, dilation), ModelTransform.of(-1.5F, -4.0F, -3.0F, 0.0F, -0.2618F, 0.0F));
        modelPartData2.addChild("left_wing", ModelPartBuilder.create().uv(0, 18).mirrored().cuboid(0.0F, 0.0F, 0.0F, 9.0F, 0.0F, 6.0F, dilation), ModelTransform.of(1.5F, -4.0F, -3.0F, 0.0F, 0.2618F, 0.0F));
        modelPartData2.addChild("front_legs", ModelPartBuilder.create().cuboid("front_legs", -5.0F, 0.0F, 0.0F, 7, 2, 0, 26, 1), ModelTransform.pivot(1.5F, 3.0F, -2.0F));
        modelPartData2.addChild("middle_legs", ModelPartBuilder.create().cuboid("middle_legs", -5.0F, 0.0F, 0.0F, 7, 2, 0, 26, 3), ModelTransform.pivot(1.5F, 3.0F, 0.0F));
        modelPartData2.addChild("back_legs", ModelPartBuilder.create().cuboid("back_legs", -5.0F, 0.0F, 0.0F, 7, 2, 0, 26, 5), ModelTransform.pivot(1.5F, 3.0F, 2.0F));

        modelPartData3.addChild("hat1",
                ModelPartBuilder.create().uv(0, 32).mirrored()
                        .cuboid(-5F, -6F, -5F, 12, 1, 6),
                ModelTransform.pivot(-1F, 1F, -1F));

        modelPartData3.addChild("hat2",
                ModelPartBuilder.create().uv(0, 48)
                        .cuboid(0F, -6F, 0F, 6, 2, 6),
                ModelTransform.pivot(-3F, -1F, -3F));

        modelPartData3.addChild("hat3",
                ModelPartBuilder.create().uv(0, 32)
                        .cuboid(-5F, -6F, 0F, 12, 1, 6),
                ModelTransform.pivot(-1F, 1F, 0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    //TODO: CHECK IF THIS IS WORKING PROPERLY AS BEFORE IT MAY HAVE BEEN CORRECT
    public ModelPart getBody() {
        ModelPart bone = null;
        for (ModelPart b : getBodyParts()) {
            bone = b;
            break;
        }
        return bone.getChild("body");
    }
}

