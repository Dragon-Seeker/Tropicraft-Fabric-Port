package net.tropicraft.core.client.entity.renderers;

import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.projectile.PoisonBlotEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class PoisonBlotRenderer extends EntityRenderer<PoisonBlotEntity> {

    public PoisonBlotRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager);
    }

    public void render(final PoisonBlotEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn) {
        stack.pushPose();
        stack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        stack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        final VertexConsumer buffer = TropicraftRenderUtils.getEntityCutoutBuilder(bufferIn, getTexture(entity));
        buffer.vertex(-.5, -.5, 0).uv(0, 1).endVertex();
        buffer.vertex( .5, -.5, 0).uv(1, 1).endVertex();
        buffer.vertex( .5,  .5, 0).uv(1, 0).endVertex();
        buffer.vertex(-.5,  .5, 0).uv(0, 0).endVertex();
        stack.popPose();
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    @Nullable
    @Override
    public ResourceLocation getTexture(PoisonBlotEntity entity) {
        return TropicraftRenderUtils.getTextureEntity("treefrog/blot");
    }
}
