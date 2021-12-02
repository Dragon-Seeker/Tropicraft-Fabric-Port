package net.tropicraft.core.client.entity.models;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
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

    public BambooMugModel(Function<ResourceLocation, RenderType> renderTypeIn) {
        super(renderTypeIn);
        texWidth = 64;
        texHeight = 32;

        base = new ModelPart(this, 10, 0);
        base.addBox(-2F, 23F, -2F, 4, 1, 4);
        base.setPos(0F, 0F, 0F);
        base.setTexSize(64, 32);
        base.mirror = true;
        wall1 = new ModelPart(this, 0, 10);
        wall1.mirror = true;
        wall1.addBox(-2F, 17F, -3F, 4, 6, 1);
        wall1.setPos(0F, 0F, 0F);
        wall1.setTexSize(64, 32);
        wall2 = new ModelPart(this, 0, 10);
        wall2.addBox(-2F, 17F, 2F, 4, 6, 1);
        wall2.setPos(0F, 0F, 0F);
        wall2.setTexSize(64, 32);
        wall2.mirror = true;
        wall3 = new ModelPart(this, 0, 0);
        wall3.addBox(2F, 17F, -2F, 1, 6, 4);
        wall3.setPos(0F, 0F, 0F);
        wall3.setTexSize(64, 32);
        wall3.mirror = true;
        wall4 = new ModelPart(this, 0, 0);
        wall4.addBox(-3F, 17F, -2F, 1, 6, 4);
        wall4.setPos(0F, 0F, 0F);
        wall4.setTexSize(64, 32);
        wall4.mirror = true;
        liquid = new ModelPart(this, 10, 5);
        liquid.addBox(-2F, 18F, -2F, 4, 1, 4);
        liquid.setPos(0F, 0F, 0F);
        liquid.setTexSize(64, 32);
        liquid.mirror = true;
        handletop = new ModelPart(this, 26, 0);
        handletop.addBox(-1F, 18F, -4F, 2, 1, 1);
        handletop.setPos(0F, 0F, 0F);
        handletop.setTexSize(64, 32);
        handletop.mirror = true;
        handlebottom = new ModelPart(this, 26, 2);
        handlebottom.addBox(-1F, 21F, -4F, 2, 1, 1);
        handlebottom.setPos(0F, 0F, 0F);
        handlebottom.setTexSize(64, 32);
        handlebottom.mirror = true;
        handle = new ModelPart(this, 32, 0);
        handle.addBox(-1F, 19F, -5F, 2, 2, 1);
        handle.setPos(0F, 0F, 0F);
        handle.setTexSize(64, 32);
        handle.mirror = true;
    }

    public Iterable<ModelPart> getMugParts() {
        return ImmutableList.of(
            base, wall1, wall2, wall3, wall4, handletop, handlebottom, handle
        );
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
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
