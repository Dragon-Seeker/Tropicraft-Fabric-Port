package net.tropicraft.core.client.blockEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class SimpleItemStackRenderer<T extends BlockEntity> extends BuiltinModelItemRenderer {

    private final Lazy<T> te;
    
    public SimpleItemStackRenderer(BlockEntityRendererFactory.Context ctx, Supplier<T> te) {
        super(ctx.getRenderDispatcher(), ctx.getLayerRenderDispatcher());
        this.te = new Lazy<>(te);
    }

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode transform, MatrixStack matrixStack, VertexConsumerProvider buffer, int combinedLight, int combinedOverlay) {
        T te = this.te.get();
        MinecraftClient.getInstance().getBlockEntityRenderDispatcher().get(te).render(te, 0, matrixStack, buffer, combinedLight, combinedOverlay);
        //BlockEntityRenderDispatcher.INSTANCE.get(te).render(te, 0, matrixStack, buffer, combinedLight, combinedOverlay);
    }
}
