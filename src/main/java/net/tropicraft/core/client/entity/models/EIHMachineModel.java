package net.tropicraft.core.client.entity.models;

/*
import com.google.common.collect.ImmutableList;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.tropicraft.core.common.block.tileentity.IMachineTile;

import java.util.function.Function;
 */

import com.google.common.collect.ImmutableList;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.tropicraft.core.common.block.blockentity.IMachineTile;

import java.util.function.Function;

public class EIHMachineModel <T extends BlockEntity & IMachineTile> extends MachineModel<T> {

    final ModelPart base;
    final ModelPart back;
    final ModelPart nose;
    final ModelPart forehead;
    final ModelPart leftEye;
    final ModelPart rightEye;
    final ModelPart basinNearBack;
    final ModelPart basinSide1;
    final ModelPart basinSide2;
    final ModelPart basinNearFront;
    final ModelPart basinCorner1;
    final ModelPart basinCorner2;
    final ModelPart basinCorner3;
    final ModelPart basinCorner4;
    final ModelPart lidBase;
    final ModelPart lidTop;
    final ModelPart mouth;

    public EIHMachineModel(ModelPart root) {
        super(RenderLayer::getEntitySolid);

        this.base =  root.getChild("base");
        this.back =  root.getChild("back");
        this.nose =  root.getChild("nose");
        this.forehead =  root.getChild("forehead");
        this.leftEye =  root.getChild("leftEye");
        this.rightEye =  root.getChild("rightEye");
        this.basinNearBack =  root.getChild("basinNearBack");
        this.basinSide1 =  root.getChild("basinSide1");
        this.basinSide2 =  root.getChild("basinSide2");
        this.basinNearFront =  root.getChild("basinNearFront");
        this.basinCorner1 =  root.getChild("basinCorner1");
        this.basinCorner2 =  root.getChild("basinCorner2");
        this.basinCorner3 =  root.getChild("basinCorner3");
        this.basinCorner4 =  root.getChild("basinCorner4");
        this.lidBase =  root.getChild("lidBase");
        this.lidTop =  root.getChild("lidTop");
        this.mouth =  root.getChild("mouth");

        /*
        textureWidth = 64;
        textureHeight = 64;

        base = new ModelPart(this, 0, 44);
        base.addCuboid(-8F, -1F, -8F, 16, 3, 16);
        base.setPivot(0F, 22F, 0F);
        base.mirror = true;
        base.setTextureSize(64, 64);
        back = new ModelPart(this, 0, 0);
        back.addCuboid(-3F, -15F, -8F, 6, 25, 16);
        back.setPivot(5F, 11F, 0F);
        back.mirror = true;
        back.setTextureSize(64, 64);
        nose = new ModelPart(this, 0, 0);
        nose.addCuboid(-1F, -7F, -2F, 2, 14, 4);
        nose.setPivot(1F, 8F, 0F);
        nose.mirror = true;
        nose.setTextureSize(64, 64);
        forehead = new ModelPart(this, 0, 0);
        forehead.addCuboid(-1F, -1F, -8F, 3, 5, 16);
        forehead.setPivot(0F, -3F, 0F);
        forehead.mirror = true;
        forehead.setTextureSize(64, 64);
        leftEye = new ModelPart(this, 1, 35);
        leftEye.addCuboid(0F, -1F, -3F, 1, 4, 6);
        leftEye.setPivot(1F, 2F, 5F);
        leftEye.mirror = true;
        leftEye.setTextureSize(64, 64);
        rightEye = new ModelPart(this, 1, 35);
        rightEye.addCuboid(0F, -1F, -3F, 1, 4, 6);
        rightEye.setPivot(1F, 2F, -5F);
        rightEye.mirror = true;
        rightEye.setTextureSize(64, 64);
        basinNearBack = new ModelPart(this, 0, 0);
        basinNearBack.addCuboid(-1F, 0F, -4F, 1, 1, 8);
        basinNearBack.setPivot(2F, 20F, 0F);
        basinNearBack.mirror = true;
        basinNearBack.setTextureSize(64, 64);
        basinSide = new ModelPart(this, 0, 0);
        basinSide.addCuboid(-5F, 0F, -2F, 10, 1, 4);
        basinSide.setPivot(-3F, 20F, 6F);
        basinSide.mirror = true;
        basinSide.setTextureSize(64, 64);
        basinSide2 = new ModelPart(this, 0, 0);
        basinSide2.addCuboid(-5F, 0F, -2F, 10, 1, 4);
        basinSide2.setPivot(-3F, 20F, -6F);
        basinSide2.mirror = true;
        basinSide2.setTextureSize(64, 64);
        basinNearFront = new ModelPart(this, 0, 0);
        basinNearFront.addCuboid(-1F, 0F, -4F, 2, 1, 8);
        basinNearFront.setPivot(-7F, 20F, 0F);
        basinNearFront.mirror = true;
        basinNearFront.setTextureSize(64, 64);
        basinCorner1 = new ModelPart(this, 0, 0);
        basinCorner1.addCuboid(0F, 0F, 0F, 1, 1, 1);
        basinCorner1.setPivot(0F, 20F, 3F);
        basinCorner1.mirror = true;
        basinCorner1.setTextureSize(64, 64);
        basinCorner2 = new ModelPart(this, 0, 0);
        basinCorner2.addCuboid(0F, 0F, 0F, 1, 1, 1);
        basinCorner2.setPivot(0F, 20F, -4F);
        basinCorner2.mirror = true;
        basinCorner2.setTextureSize(64, 64);
        basinCorner3 = new ModelPart(this, 0, 0);
        basinCorner3.addCuboid(0F, 0F, 0F, 1, 1, 1);
        basinCorner3.setPivot(-6F, 20F, 3F);
        basinCorner3.mirror = true;
        basinCorner3.setTextureSize(64, 64);
        basinCorner4 = new ModelPart(this, 0, 0);
        basinCorner4.addCuboid(0F, 0F, 0F, 1, 1, 1);
        basinCorner4.setPivot(-6F, 20F, -4F);
        basinCorner4.mirror = true;
        basinCorner4.setTextureSize(64, 64);
        lidBase = new ModelPart(this, 0, 0);
        lidBase.addCuboid(-4F, 0F, -8F, 9, 1, 16);
        lidBase.setPivot(3F, -5F, 0F);
        lidBase.mirror = true;
        lidBase.setTextureSize(64, 64);
        lidTop = new ModelPart(this, 0, 0);
        lidTop.addCuboid(-2F, 0F, -2F, 4, 1, 4);
        lidTop.setPivot(3F, -6F, 0F);
        lidTop.mirror = true;
        lidTop.setTextureSize(64, 64);
        mouth = new ModelPart(this, 54, 0);
        mouth.addCuboid(0F, -1F, -2F, 1, 3, 4);
        mouth.setPivot(1F, 16F, 0F);
        mouth.mirror = true;
        mouth.setTextureSize(64, 64);
         */
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("base",
                ModelPartBuilder.create()
                        .uv(0,44)
                        .mirrored()
                        .cuboid(-8F, -1F, -8F, 16, 3, 16),
                ModelTransform.pivot(0F, 22F, 0F));

        modelPartData.addChild("back",
                ModelPartBuilder.create()
                        .uv(0,0)
                        .mirrored()
                        .cuboid(-3F, -15F, -8F, 6, 25, 16),
                ModelTransform.pivot(5F, 11F, 0F));

        modelPartData.addChild("nose",
                ModelPartBuilder.create()
                        .uv(0,0)
                        .mirrored()
                        .cuboid(-1F, -7F, -2F, 2, 14, 4),
                ModelTransform.pivot(1F, 8F, 0F));

        modelPartData.addChild("forehead",
                ModelPartBuilder.create()
                        .uv(0,0)
                        .mirrored()
                        .cuboid(-1F, -1F, -8F, 3, 5, 16),
                ModelTransform.pivot(0F, -3F, 0F));

        modelPartData.addChild("leftEye",
                ModelPartBuilder.create()
                        .uv(1, 35)
                        .mirrored()
                        .cuboid(0F, -1F, -3F, 1, 4, 6),
                ModelTransform.pivot(1F, 2F, 5F));

        modelPartData.addChild("rightEye",
                ModelPartBuilder.create()
                        .uv(1, 35)
                        .mirrored()
                        .cuboid(0F, -1F, -3F, 1, 4, 6),
                ModelTransform.pivot(1F, 2F, -5F));

        modelPartData.addChild("basinNearBack",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .mirrored()
                        .cuboid(-1F, 0F, -4F, 1, 1, 8),
                ModelTransform.pivot(2F, 20F, 0F));

        modelPartData.addChild("basinSide1",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .mirrored()
                        .cuboid(-5F, 0F, -2F, 10, 1, 4),
                ModelTransform.pivot(-3F, 20F, 6F));

        modelPartData.addChild("basinSide2",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .mirrored()
                        .cuboid(-5F, 0F, -2F, 10, 1, 4),
                ModelTransform.pivot(-3F, 20F, -6F));

        modelPartData.addChild("basinNearFront",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .mirrored()
                        .cuboid(-1F, 0F, -4F, 2, 1, 8),
                ModelTransform.pivot(-7F, 20F, 0F));

        modelPartData.addChild("basinCorner1",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .mirrored()
                        .cuboid(0F, 0F, 0F, 1, 1, 1),
                ModelTransform.pivot(0F, 20F, 3F));

        modelPartData.addChild("basinCorner2",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .mirrored()
                        .cuboid(0F, 0F, 0F, 1, 1, 1),
                ModelTransform.pivot(0F, 20F, -4F));

        modelPartData.addChild("basinCorner3",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .mirrored()
                        .cuboid(0F, 0F, 0F, 1, 1, 1),
                ModelTransform.pivot(-6F, 20F, 3F));

        modelPartData.addChild("basinCorner4",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .mirrored()
                        .cuboid(0F, 0F, 0F, 1, 1, 1),
                ModelTransform.pivot(-6F, 20F, -4F));

        modelPartData.addChild("lidBase",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .mirrored()
                        .cuboid(-4F, 0F, -8F, 9, 1, 16),
                ModelTransform.pivot(3F, -5F, 0F));

        modelPartData.addChild("lidTop",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .mirrored()
                        .cuboid(-2F, 0F, -2F, 4, 1, 4),
                ModelTransform.pivot(3F, -6F, 0F));

        modelPartData.addChild("mouth",
                ModelPartBuilder.create()
                        .uv(54, 0)
                        .mirrored()
                        .cuboid(0F, -1F, -2F, 1, 3, 4),
                ModelTransform.pivot(1F, 16F, 0F));


        return TexturedModelData.of(modelData,64,64);
    }

    @Override
    public float getScale(T te) {
        return 0.0625F;
    }

    @Override
    public String getTexture(T te) {
        return "drink_mixer";
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(
            base, back, nose, forehead, leftEye, rightEye, basinNearBack,
            basinSide1, basinSide2, basinNearFront, basinCorner1, basinCorner2,
            basinCorner3, basinCorner4, lidBase, lidTop, mouth
        );
    }


}
