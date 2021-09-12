package net.tropicraft.core.client.blockEntity;
/*
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.tropicraft.core.client.TropicraftRenderUtils;
import net.tropicraft.core.client.entity.model.EIHMachineModel;
import net.tropicraft.core.client.scuba.ModelScubaGear;
import net.tropicraft.core.common.block.TropicraftBlocks;
import net.tropicraft.core.common.block.tileentity.AirCompressorTileEntity;
import net.tropicraft.core.common.item.scuba.ScubaArmorItem;

public class AirCompressorRenderer extends MachineRenderer<AirCompressorTileEntity> {
    
    private final ModelScubaGear tankModel = new ModelScubaGear(0, EquipmentSlot.CHEST); // Can't reuse the main one with a different scale

    public AirCompressorRenderer(final BlockEntityRendererFactory.Context ctx) {
        super(ctx, TropicraftBlocks.AIR_COMPRESSOR.get(), new EIHMachineModel<>(RenderLayer::getEntitySolid));
    }

    @Override
    protected SpriteIdentifier getMaterial() {
        return TropicraftRenderUtils.getTEMaterial("drink_mixer");
    }
    @Override
    protected void animationTransform(AirCompressorTileEntity te, final MatrixStack stack, float partialTicks) {
        float progress = te.getBreatheProgress(partialTicks);
        float sin = 1 + MathHelper.cos(progress);
        float sc = 1 + 0.05f * sin;
        stack.translate(0, 1.5f, 0);
        stack.scale(sc, sc, sc);
        stack.translate(0, -1.5f, 0);
        if (progress < Math.PI) {
            float shake = MathHelper.sin(te.getBreatheProgress(partialTicks) * 10) * 8f;
            stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(shake));
        }
    }

    @Override
    protected void renderIngredients(AirCompressorTileEntity te, MatrixStack stack, VertexConsumerProvider buffer, int combinedLightIn, int combinedOverlayIn) {
        if (te.isActive()) {
            stack.push();
            stack.translate(-0.5f, 0.5f, 0);
            stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
            // TODO this is likely wrong
            VertexConsumer builder = ItemRenderer.getItemGlintConsumer(buffer, RenderLayer.getEntityCutoutNoCull(ScubaArmorItem.getArmorTexture(te.getTank().getType())), true, false);
            tankModel.showChest = true;
            tankModel.renderScubaGear(stack, builder, combinedLightIn, combinedOverlayIn, false);
            stack.pop();
        }
    }
}
 */
