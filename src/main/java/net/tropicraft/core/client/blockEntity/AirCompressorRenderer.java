package net.tropicraft.core.client.blockEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.util.Mth;
//import net.tropicraft.core.client.TropicraftRenderUtils;
//import net.tropicraft.core.client.entity.model.EIHMachineModel;
import net.tropicraft.core.client.entity.models.EIHMachineModel;
//import net.tropicraft.core.client.scuba.ModelScubaGear;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
//import net.tropicraft.core.common.block.TropicraftBlocks;
import net.tropicraft.core.common.block.blockentity.AirCompressorTileEntity;
//import net.tropicraft.core.common.block.tileentity.AirCompressorTileEntity;
//import net.tropicraft.core.common.item.scuba.ScubaArmorItem;
import net.tropicraft.core.common.registry.TropicraftBlocks;

public class AirCompressorRenderer extends MachineRenderer<AirCompressorTileEntity> {
    
    //private final ModelScubaGear tankModel = new ModelScubaGear(0, EquipmentSlot.CHEST); // Can't reuse the main one with a different scale

    public AirCompressorRenderer(final BlockEntityRendererProvider.Context ctx) {
        super(ctx, TropicraftBlocks.AIR_COMPRESSOR, new EIHMachineModel(ctx.bakeLayer(TropicraftEntityRendering.AIRCOMPRESSOR_LAYER)));
    }

    @Override
    protected Material getMaterial() {
        return TropicraftRenderUtils.getTEMaterial("drink_mixer");
    }
    @Override
    protected void animationTransform(AirCompressorTileEntity te, final PoseStack stack, float partialTicks) {
        float progress = 0F;//te.getBreatheProgress(partialTicks);
        float sin = 1 + Mth.cos(progress);
        float sc = 1 + 0.05f * sin;
        stack.translate(0, 1.5f, 0);
        stack.scale(sc, sc, sc);
        stack.translate(0, -1.5f, 0);
        //if (progress < Math.PI) {
        //    float shake = MathHelper.sin(te.getBreatheProgress(partialTicks) * 10) * 8f;
        //    stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(shake));
        //}
    }

    @Override
    protected void renderIngredients(AirCompressorTileEntity te, PoseStack stack, MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
        /*
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
         */
    }
}

