package net.tropicraft.core.client.entity.models;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.tropicraft.core.common.block.blockentity.IMachineTile;

import java.util.function.Function;

public abstract class MachineModel <T extends BlockEntity & IMachineTile> extends Model {
    public MachineModel(Function<Identifier, RenderLayer> renderTypeIn) {
        super(renderTypeIn);
    }

    public abstract float getScale(T te);

    public abstract String getTexture(T te);

    public abstract Iterable<ModelPart> getParts();

    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        getParts().forEach((part) -> {
            part.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

}
