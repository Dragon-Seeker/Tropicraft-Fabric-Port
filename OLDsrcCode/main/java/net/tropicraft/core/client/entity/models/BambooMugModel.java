package net.tropicraft.core.client.entity.models;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class BambooMugModel extends Model {
    public ModelPart base;
    public ModelPart wall1;
    public ModelPart wall2;
    public ModelPart wall3;
    public ModelPart wall4;
    public ModelPart liquid;
    public ModelPart handletop;
    public ModelPart handlebottom;
    public ModelPart handle;

    public boolean renderLiquid;
    public int liquidColor;

    //Function<Identifier, RenderLayer> renderTypeIn

    public BambooMugModel(ModelPart root) {
        super(RenderLayer::getEntityCutout);
        this.base = root.getChild("base");
        this.wall1 = root.getChild("wall1");
        this.wall2 = root.getChild("wall2");
        this.wall3 = root.getChild("wall3");
        this.wall4 = root.getChild("wall4");
        this.liquid = root.getChild("liquid");
        this.handletop = root.getChild("handletop");
        this.handlebottom = root.getChild("handlebottom");
        this.handle = root.getChild("handle");

    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("base", ModelPartBuilder.create().uv(10,0).mirrored().cuboid(-2F, 23F, -2F, 4, 1, 4),ModelTransform.pivot(0F, 0F, 0F));
        modelPartData.addChild("wall1", ModelPartBuilder.create().uv(0,10).mirrored().cuboid(-2F, 17F, -3F, 4, 6, 1),ModelTransform.pivot(0F, 0F, 0F));
        modelPartData.addChild("wall2", ModelPartBuilder.create().uv(0,10).mirrored().cuboid(-2F, 17F, 2F, 4, 6, 1),ModelTransform.pivot(0F, 0F, 0F));
        modelPartData.addChild("wall3", ModelPartBuilder.create().uv(0,0).mirrored().cuboid(2F, 17F, -2F, 1, 6, 4),ModelTransform.pivot(0F, 0F, 0F));
        modelPartData.addChild("wall4", ModelPartBuilder.create().uv(0,0).mirrored().cuboid(-3F, 17F, -2F, 1, 6, 4),ModelTransform.pivot(0F, 0F, 0F));
        modelPartData.addChild("liquid", ModelPartBuilder.create().uv(10,5).mirrored().cuboid(-2F, 18F, -2F, 4, 1, 4),ModelTransform.pivot(0F, 0F, 0F));
        modelPartData.addChild("handletop", ModelPartBuilder.create().uv(26,0).mirrored().cuboid(-1F, 18F, -4F, 2, 1, 1),ModelTransform.pivot(0F, 0F, 0F));
        modelPartData.addChild("handlebottom", ModelPartBuilder.create().uv(26,2).mirrored().cuboid(-1F, 21F, -4F, 2, 1, 1),ModelTransform.pivot(0F, 0F, 0F));
        modelPartData.addChild("handle", ModelPartBuilder.create().uv(32,0).mirrored().cuboid(-1F, 19F, -5F, 2, 2, 1),ModelTransform.pivot(0F, 0F, 0F));

        return TexturedModelData.of(modelData,64,32);
    }

    public Iterable<ModelPart> getMugParts() {
        return ImmutableList.of(
            base, wall1, wall2, wall3, wall4, handletop, handlebottom, handle
        );
    }

    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        getMugParts().forEach((part) -> {
            part.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });

        if (renderLiquid) {
            float r = (float)(liquidColor >> 16 & 255) / 255.0F;
            float g = (float)(liquidColor >> 8 & 255) / 255.0F;
            float b = (float)(liquidColor & 255) / 255.0F;

            liquid.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red * r, green * g, blue * b, alpha);
        }
    }
}


