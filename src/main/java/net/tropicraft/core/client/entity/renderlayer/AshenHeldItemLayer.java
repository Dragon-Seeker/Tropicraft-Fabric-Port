package net.tropicraft.core.client.entity.renderlayer;

import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.client.entity.models.AshenModel;
import net.tropicraft.core.common.entity.hostile.AshenEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;


@Environment(EnvType.CLIENT)
public class AshenHeldItemLayer<T extends AshenEntity, M extends EntityModel<T> & ArmedModel> extends ItemInHandLayer<T, M> {
    private AshenModel ashenModel;

    public AshenHeldItemLayer(RenderLayerParent<T, M> featureRendererContext) {
        super(featureRendererContext);
        ashenModel = (AshenModel) this.getParentModel();
    }

    @Override
    protected ResourceLocation getTextureLocation(AshenEntity entityIn) {
        return TropicraftRenderUtils.getTextureEntity("ashen/ashen");
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int packedLightIn, T ashen, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        final ItemStack blowGunHand = ashen.getMainHandItem();
        final ItemStack daggerHand = ashen.getMainHandItem();

        if (!blowGunHand.isEmpty() || !daggerHand.isEmpty()) {
            stack.pushPose();

            if (ashenModel.young) {
                stack.translate(0.0F, 0.625F, 0.0F);
                stack.mulPose(Vector3f.XN.rotationDegrees(-20));
                stack.scale(0.5f, 0.5f, 0.5f);
            }

            HumanoidArm arm = ashen.getMainArm();
            renderHeldItem(ashen, blowGunHand, arm, stack, buffer, packedLightIn);
            arm = arm.getOpposite();
            renderHeldItem(ashen, daggerHand, arm, stack, buffer, packedLightIn);

            stack.popPose();
        }
    }

    private void renderHeldItem(AshenEntity entity, ItemStack itemstack, HumanoidArm arm, PoseStack stack, MultiBufferSource buffer, int combinedLightIn) {
        if (itemstack.isEmpty()) {
            return;
        }

        if (entity.getActionState() == AshenEntity.AshenState.HOSTILE) {
            float scale = 0.5F;
            if (arm == HumanoidArm.LEFT) {
                stack.pushPose();
                ashenModel.leftArm.translateAndRotate(stack);

                stack.translate(0.3F, -0.30F, -0.045F);
                stack.mulPose(Vector3f.XP.rotationDegrees(180F));
                stack.mulPose(Vector3f.YP.rotationDegrees(180F));
                stack.mulPose(Vector3f.ZP.rotationDegrees(10F));

                stack.scale(scale, scale, scale);
                //TODO: WHAT THE HELL IS SEED AND WHAT VAULE DOSE IT NEED TO BE
                //renderItem(LivingEntity entity, ItemStack stack, Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light)
                //MinecraftClient.getInstance().getItemRenderer().renderItem(entity, itemstack, ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, false, stack, buffer, entity.world, combinedLightIn, OverlayTexture.DEFAULT_UV, entity.getId() + renderMode.ordinal());

                //MinecraftClient.getInstance().renderItem
                Minecraft.getInstance().getItemInHandRenderer().renderItem(entity, itemstack, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, false, stack, buffer, combinedLightIn);
                stack.popPose();
            } else {
                stack.pushPose();
                ashenModel.rightArm.translateAndRotate(stack);

                stack.translate(-0.375F, -0.35F, -0.125F);
                stack.mulPose(Vector3f.YP.rotationDegrees(90F));
                stack.scale(scale, scale, scale);

                Minecraft.getInstance().getItemRenderer().renderStatic(entity, itemstack, ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND, false, stack, buffer, entity.level, combinedLightIn, OverlayTexture.NO_OVERLAY, 0);
                stack.popPose();
            }
        }
    }
}


