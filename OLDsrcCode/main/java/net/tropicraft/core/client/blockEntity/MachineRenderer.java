package net.tropicraft.core.client.blockEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.tropicraft.core.client.entity.models.MachineModel;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.block.blockentity.IMachineTile;

public abstract class MachineRenderer<T extends BlockEntity & IMachineTile> implements BlockEntityRenderer<T> {
    private final Block block;
    protected final MachineModel<T> model;

    public MachineRenderer(final BlockEntityRendererFactory.Context ctx, final Block block, final MachineModel<T> model) {
        //super(ctx);
        this.block = block;
        this.model = model;
    }

    @Override
    public void render(T te, float partialTicks, MatrixStack stack, VertexConsumerProvider buffer, int combinedLightIn, int combinedOverlayIn) {
        stack.push();
        stack.translate(0.5f, 1.5f, 0.5f);
        stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
        //stack.rotate(Vector3f.ZP.rotationDegrees(180));

        if (te == null || te.getWorld() == null) {
            stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90));
        } else {
            BlockState state = te.getWorld().getBlockState(te.getPos());
            Direction facing;
            if (state.getBlock() != this.block) {
                facing = Direction.NORTH;
            } else {
                facing = te.getDirection(state);
            }
            stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(facing.asRotation() + 90));
        }

        if (te != null && te.isActive()) {
            animationTransform(te, stack, partialTicks);
        }

//        TropicraftRenderUtils.bindTextureTE(model.getTexture(te));
//        model.renderAsBlock(te);

        // Get light above, since block is solid
        TropicraftRenderUtils.renderModel(getMaterial(), model, stack, buffer, combinedLightIn, combinedOverlayIn);

        if (te != null) {
            renderIngredients(te, stack, buffer, combinedLightIn, combinedOverlayIn);
        }

        //GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        stack.pop();
    }

    protected abstract SpriteIdentifier getMaterial();
    
    protected void animationTransform(T te, MatrixStack stack, float partialTicks) {
        float angle = MathHelper.sin((float) (25f * 2f * Math.PI * te.getProgress(partialTicks))) * 15f;
        stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(angle));
    }
    
    protected abstract void renderIngredients(final T te, final MatrixStack stack, final VertexConsumerProvider buffer, int packedLightIn, int combinedOverlayIn);
}
