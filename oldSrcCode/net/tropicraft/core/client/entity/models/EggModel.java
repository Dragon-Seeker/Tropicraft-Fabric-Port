package net.tropicraft.core.client.entity.models;

import net.tropicraft.core.common.entity.egg.EggEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class EggModel extends CompositeEntityModel<EggEntity> {
    public ModelPart body;

    public EggModel() {
        textureWidth = 64;
        textureHeight = 32;

        body = new ModelPart(this);
        body.setPivot(0F, 24F, 0F);
        body.mirror = true;
        body.setTextureOffset(0, 16).addCuboid(-3F, -10F, -3F, 6, 10, 6);
        body.setTextureOffset(0, 0).addCuboid(-1.5F, -11F, -1.5F, 3, 1, 3);
        body.setTextureOffset(0, 7).addCuboid(3F, -7F, -1.5F, 1, 6, 3);
        body.setTextureOffset(24, 9).addCuboid(-1.5F, -7F, 3F, 3, 6, 1);
        body.setTextureOffset(16, 7).addCuboid(-4F, -7F, -1.5F, 1, 6, 3);
        body.setTextureOffset(8, 9).addCuboid(-1.5F, -7F, -4F, 3, 6, 1);
    }

    @Override
    public void setAngles(EggEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(body);
    }

    @Override
    public void animateModel(EggEntity entityliving, float limbSwing, float limbSwingAmount, float partialTick) {
        boolean hatching = entityliving.isNearHatching();
        double randRotator = entityliving.rotationRand;
        body.yaw = 0F;
        if (hatching) {
            body.yaw = (float) (Math.sin((entityliving.age + partialTick) * .6)) * .6f;
            body.pitch = (float) ((Math.sin(randRotator * 4))) * .6f;
            body.roll = (float) ((Math.cos(randRotator * 4))) * .6f;
        } else {
            body.pitch = 0F;
            body.roll = 0F;
        }
    }
    
    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn,
            float red, float green, float blue, float alpha) {
        super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}