package net.tropicraft.core.client.entity.models;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.tropicraft.core.common.block.blockentity.IMachineTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.function.Function;

public abstract class MachineModel <T extends BlockEntity & IMachineTile> extends Model {
    public MachineModel(Function<ResourceLocation, RenderType> renderTypeIn) {
        super(renderTypeIn);
    }

    public abstract float getScale(T te);

    public abstract String getTexture(T te);

    public abstract Iterable<ModelPart> getParts();

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        getParts().forEach((part) -> {
            part.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

}
