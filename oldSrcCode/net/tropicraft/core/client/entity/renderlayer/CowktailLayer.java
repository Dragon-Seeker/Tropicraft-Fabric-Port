package net.tropicraft.core.client.entity.renderlayer;

import net.tropicraft.core.common.entity.passive.CowktailEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class CowktailLayer<T extends CowktailEntity> extends FeatureRenderer<T, CowEntityModel<T>>
{
   public CowktailLayer(FeatureRendererContext<T, CowEntityModel<T>> rendererIn) {
      super(rendererIn);
   }

   public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (!entitylivingbaseIn.isBaby() && !entitylivingbaseIn.isInvisible()) {
         BlockRenderManager blockrendererdispatcher = MinecraftClient.getInstance().getBlockRenderManager();
         BlockState blockstate = entitylivingbaseIn.getCowktailType().getRenderState();
         int i = LivingEntityRenderer.getOverlay(entitylivingbaseIn, 0.0F);
         matrixStackIn.push();
         matrixStackIn.translate((double)0.2F, (double)-0.35F, 0.5D);
         matrixStackIn.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-48.0F));
         matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
         matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
         blockrendererdispatcher.renderBlockAsEntity(blockstate, matrixStackIn, bufferIn, packedLightIn, i);
         matrixStackIn.pop();
         matrixStackIn.push();
         matrixStackIn.translate((double)0.2F, (double)-0.35F, 0.5D);
         matrixStackIn.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(42.0F));
         matrixStackIn.translate((double)0.1F, 0.0D, (double)-0.6F);
         matrixStackIn.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-48.0F));
         matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
         matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
         blockrendererdispatcher.renderBlockAsEntity(blockstate, matrixStackIn, bufferIn, packedLightIn, i);
         matrixStackIn.pop();
         matrixStackIn.push();
         this.getContextModel().getHead().rotate(matrixStackIn);
         matrixStackIn.translate(0.0D, (double)-0.7F, (double)-0.2F);
         matrixStackIn.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-78.0F));
         matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
         matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
         blockrendererdispatcher.renderBlockAsEntity(blockstate, matrixStackIn, bufferIn, packedLightIn, i);
         matrixStackIn.pop();
      }
   }
}
