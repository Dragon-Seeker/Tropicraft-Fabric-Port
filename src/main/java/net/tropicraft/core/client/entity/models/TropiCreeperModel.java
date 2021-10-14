package net.tropicraft.core.client.entity.models;

import net.minecraft.client.model.*;
import net.tropicraft.core.common.entity.passive.TropiCreeperEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;


public class TropiCreeperModel extends CompositeEntityModel<TropiCreeperEntity> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leg3;
    private final ModelPart leg4;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart hat1;
    private final ModelPart hat2;
    private final ModelPart hat3;

    public TropiCreeperModel(ModelPart root) {

        head = root.getChild("head");
        body = root.getChild("body");
        leg3 = root.getChild("leg3");
        leg4 = root.getChild("leg4");
        leg1 = root.getChild("leg1");
        leg2 = root.getChild("leg2");
        hat1 = head.getChild("hat1");
        hat2 = head.getChild("hat2");
        hat3 = head.getChild("hat3");

        /*
        int i = 4;
        head = new ModelPart(this, 0, 0);
        head.addCuboid(-4F, -8F, -4F, 8, 8, 8);
        head.setPivot(0F, 6F, 0F);
        head.pitch = 0F;
        head.yaw = 0F;
        head.roll = 0F;
        head.mirror = false;

        body = new ModelPart(this, 16, 16);
        body.addCuboid(-4F, 0F, -2F, 8, 12, 4);
        body.setPivot(0F, 6F, 0F);

        leg3 = new ModelPart(this, 0, 16);
        leg3.addCuboid(-2F, 0.0F, -2F, 4, 6, 4, 0);
        leg3.setPivot(-2F, 12 + i, -4F);

        leg4 = new ModelPart(this, 0, 16);
        leg4.addCuboid(-2F, 0.0F, -2F, 4, 6, 4, 0);
        leg4.setPivot(2.0F, 12 + i, -4F);

        leg1 = new ModelPart(this, 0, 16);
        leg1.addCuboid(-2F, 0.0F, -2F, 4, 6, 4, 0);
        leg1.setPivot(-2F, 12 + i, 4F);

        leg2 = new ModelPart(this, 0, 16);
        leg2.addCuboid(-2F, 0.0F, -2F, 4, 6, 4, 0);
        leg2.setPivot(2.0F, 12 + i, 4F);

        hat1 = new ModelPart(this, 24, 0);
        hat1.addCuboid(-5F, -6F, -5F, 12, 1, 6);
        hat1.setPivot(-1F, -3F, -1F);
        hat1.mirror = true;
        head.addChild(hat1);

        hat2 = new ModelPart(this, 40, 24);
        hat2.addCuboid(0F, -6F, 0F, 6, 2, 6);
        hat2.setPivot(-3F, -5F, -3F);
        hat2.mirror = false;
        head.addChild(hat2);

        hat3 = new ModelPart(this, 24, 0);
        hat3.addCuboid(-5F, -6F, 0F, 12, 1, 6);
        hat3.setPivot(-1F, -3F, 0F);
        hat3.mirror = false;
        head.addChild(hat3);
         */
    }

    public static TexturedModelData getTexturedModelData() {
        int i = 4;

        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData modelPartHead = modelPartData.addChild("head",
                ModelPartBuilder.create().uv(0, 0)
                        .cuboid(-4F, -8F, -4F, 8, 8, 8),
                ModelTransform.of(0F, 6F, 0F, 0F, 0F, 0F));

        modelPartData.addChild("body",
                ModelPartBuilder.create().uv(16, 16)
                        .cuboid(-4F, 0F, -2F, 8, 12, 4),
                ModelTransform.pivot(0F, 6F, 0F));

        modelPartData.addChild("leg3",
                ModelPartBuilder.create().uv(0, 16)
                        .cuboid(-2F, 0.0F, -2F, 4, 6, 4),
                ModelTransform.pivot(-2F, 12 + i, -4F));

        modelPartData.addChild("leg4",
                ModelPartBuilder.create().uv(0, 16)
                        .cuboid(-2F, 0.0F, -2F, 4, 6, 4),
                ModelTransform.pivot(2.0F, 12 + i, -4F));

        modelPartData.addChild("leg1",
                ModelPartBuilder.create().uv(0, 16)
                        .cuboid(-2F, 0.0F, -2F, 4, 6, 4),
                ModelTransform.pivot(-2F, 12 + i, 4F));

        modelPartData.addChild("leg2",
                ModelPartBuilder.create().uv(0, 16)
                        .cuboid(-2F, 0.0F, -2F, 4, 6, 4),
                ModelTransform.pivot(2.0F, 12 + i, 4F));

        modelPartHead.addChild("hat1",
                ModelPartBuilder.create().uv(24, 0).mirrored()
                        .cuboid(-5F, -6F, -5F, 12, 1, 6),
                ModelTransform.pivot(-1F, -3F, -1F));

        modelPartHead.addChild("hat2",
                ModelPartBuilder.create().uv(40, 24)
                        .cuboid(0F, -6F, 0F, 6, 2, 6),
                ModelTransform.pivot(-3F, -5F, -3F));

        modelPartHead.addChild("hat3",
                ModelPartBuilder.create().uv(24, 0)
                        .cuboid(-5F, -6F, 0F, 12, 1, 6),
                ModelTransform.pivot(-1F, -3F, 0F));

        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public void setAngles(TropiCreeperEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        head.yaw = netHeadYaw * ((float) Math.PI / 180F);
        head.pitch = headPitch * ((float) Math.PI / 180F);
        leg1.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        leg2.pitch = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        leg3.pitch = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        leg4.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(
            head, body, leg1, leg2, leg3, leg4
        );
    }
}

