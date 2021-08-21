package net.tropicraft.core.client.entity.renderlayer;

import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.client.entity.models.AshenModel;
import net.tropicraft.core.common.entity.hostile.AshenEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;


@Environment(EnvType.CLIENT)
public class AshenHeldItemLayer<T extends AshenEntity, M extends EntityModel<T> & ModelWithArms> extends HeldItemFeatureRenderer<T, M> {

    private AshenModel model;

    public AshenHeldItemLayer(FeatureRendererContext<T, M> renderer) {
        super(renderer);
    }

    public void setAshenModel(final AshenModel model) {
        this.model = model;
    }

    @Override
    protected Identifier getTexture(AshenEntity entityIn) {
        return TropicraftRenderUtils.getTextureEntity("ashen/ashen");
    }

    @Override
    public void render(MatrixStack stack, VertexConsumerProvider buffer, int packedLightIn, T ashen, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        final ItemStack blowGunHand = ashen.getMainHandStack();
        final ItemStack daggerHand = ashen.getMainHandStack();

        if (!blowGunHand.isEmpty() || !daggerHand.isEmpty()) {
            stack.push();

            if (model.child) {
                stack.translate(0.0F, 0.625F, 0.0F);
                stack.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(-20));
                stack.scale(0.5f, 0.5f, 0.5f);
            }

            Arm arm = ashen.getMainArm();
            renderHeldItem(ashen, blowGunHand, arm, stack, buffer, packedLightIn);
            arm = arm.getOpposite();
            renderHeldItem(ashen, daggerHand, arm, stack, buffer, packedLightIn);

            stack.pop();
        }
    }

    private void renderHeldItem(AshenEntity entity, ItemStack itemstack, Arm arm, MatrixStack stack, VertexConsumerProvider buffer, int combinedLightIn) {
        if (itemstack.isEmpty()) {
            return;
        }

        if (entity.getActionState() == AshenEntity.AshenState.HOSTILE) {
            float scale = 0.5F;
            if (arm == Arm.LEFT) {
                stack.push();
                model.leftArm.rotate(stack);

                stack.translate(0.3F, -0.30F, -0.045F);
                stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180F));
                stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180F));
                stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(10F));

                stack.scale(scale, scale, scale);
                MinecraftClient.getInstance().getItemRenderer().renderItem(entity, itemstack, ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND, false, stack, buffer, entity.world, combinedLightIn, OverlayTexture.DEFAULT_UV);
                stack.pop();
            } else {
                stack.push();
                model.rightArm.rotate(stack);

                stack.translate(-0.375F, -0.35F, -0.125F);
                stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90F));
                stack.scale(scale, scale, scale);

                MinecraftClient.getInstance().getItemRenderer().renderItem(entity, itemstack, ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND, false, stack, buffer, entity.world, combinedLightIn, OverlayTexture.DEFAULT_UV);
                stack.pop();
            }
        }
    }
}
