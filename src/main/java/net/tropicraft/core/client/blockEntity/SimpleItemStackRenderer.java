package net.tropicraft.core.client.blockEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class SimpleItemStackRenderer<T extends BlockEntity> extends BlockEntityWithoutLevelRenderer {

    private final LazyLoadedValue<T> te;
    
    public SimpleItemStackRenderer(BlockEntityRendererProvider.Context ctx, Supplier<T> te) {
        super(ctx.getBlockEntityRenderDispatcher(), ctx.getModelSet());
        this.te = new LazyLoadedValue<>(te);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transform, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        T te = this.te.get();
        Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(te).render(te, 0, matrixStack, buffer, combinedLight, combinedOverlay);
        //BlockEntityRenderDispatcher.INSTANCE.get(te).render(te, 0, matrixStack, buffer, combinedLight, combinedOverlay);
    }
}
