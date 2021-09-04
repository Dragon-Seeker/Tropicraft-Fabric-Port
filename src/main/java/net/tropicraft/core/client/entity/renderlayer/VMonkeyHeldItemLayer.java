package net.tropicraft.core.client.entity.renderlayer;
/*
import net.tropicraft.core.common.entity.neutral.VMonkeyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class VMonkeyHeldItemLayer<T extends VMonkeyEntity, M extends EntityModel<T> & ModelWithArms> extends HeldItemFeatureRenderer<T, M> {
    public VMonkeyHeldItemLayer(FeatureRendererContext<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(MatrixStack stack, VertexConsumerProvider buffer, int packedLightIn, T monkey, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (monkey.isSitting() && !monkey.getMainHandStack().isEmpty()) {
            stack.push();
            stack.translate(0.0F, 1.30F, -0.425F);
            stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180));
            stack.scale(0.5F, 0.5F, 0.5F);
            MinecraftClient.getInstance().getItemRenderer().renderItem(monkey.getMainHandStack(), ModelTransformation.Mode.NONE, packedLightIn, LivingEntityRenderer.getOverlay(monkey, 0.0F), stack, buffer);
            stack.pop();
        }
    }
}

 */
