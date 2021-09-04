package net.tropicraft.core.client.entity.models;

import net.tropicraft.core.common.entity.hostile.TropiSkellyEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AbstractZombieModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
/*
public class TropiSkellyModel extends AbstractZombieModel<TropiSkellyEntity> implements ModelWithArms {
    
    private final ModelPart skirt;
    
    public TropiSkellyModel() {
        super(0.0F, 0.0F, 64, 64);
        float g = 0.0F;
        textureWidth = 64;
        textureHeight = 64;
        rightArm = new ModelPart(this, 40, 16);
        rightArm.addCuboid(-1.0F, -2.0F, -1.0F, 2, 12, 2, g);
        rightArm.setPivot(-5.0F, 2.0F, 0.0F);
        leftArm = new ModelPart(this, 40, 16);
        leftArm.mirror = true;
        leftArm.addCuboid(-1.0F, -2.0F, -1.0F, 2, 12, 2, g);
        leftArm.setPivot(5.0F, 2.0F, 0.0F);
        rightLeg = new ModelPart(this, 0, 16);
        rightLeg.addCuboid(-1.0F, 0.0F, -1.0F, 2, 12, 2, g);
        rightLeg.setPivot(-2.0F, 12.0F, 0.0F);
        leftLeg = new ModelPart(this, 0, 16);
        leftLeg.mirror = true;
        leftLeg.addCuboid(-1.0F, 0.0F, -1.0F, 2, 12, 2, g);
        leftLeg.setPivot(2.0F, 12.0F, 0.0F);

        // Hula Skirt
        skirt = new ModelPart(this, 40, 0);
        skirt.addCuboid(-4.0F, 12.0F, -2.0F, 8, 3, 4, 0.0F);
        body.addChild(skirt);;
    }

    @Override
    public void setArmAngle(Arm side, final MatrixStack stack) {
        super.setArmAngle(side, stack);
        stack.translate((side == Arm.LEFT ? -1 : 1) * 0.1f, 0, 0.0F);
    }

    @Override
    public boolean isAttacking(TropiSkellyEntity entityIn) {
        return entityIn.isAttacking();
    }
}

 */