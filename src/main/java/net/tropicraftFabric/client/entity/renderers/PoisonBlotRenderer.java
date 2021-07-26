package net.tropicraftFabric.client.entity.renderers;

import net.tropicraftFabric.client.util.TropicraftRenderUtils;
import net.tropicraftFabric.common.entity.projectile.PoisonBlotEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class PoisonBlotRenderer extends EntityRenderer<PoisonBlotEntity> {

    public PoisonBlotRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager);
    }

    public void render(final PoisonBlotEntity entity, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
        stack.push();
        stack.multiply(this.dispatcher.getRotation());
        stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
        final VertexConsumer buffer = TropicraftRenderUtils.getEntityCutoutBuilder(bufferIn, getTexture(entity));
        buffer.vertex(-.5, -.5, 0).texture(0, 1).next();
        buffer.vertex( .5, -.5, 0).texture(1, 1).next();
        buffer.vertex( .5,  .5, 0).texture(1, 0).next();
        buffer.vertex(-.5,  .5, 0).texture(0, 0).next();
        stack.pop();
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    @Nullable
    @Override
    public Identifier getTexture(PoisonBlotEntity entity) {
        return TropicraftRenderUtils.getTextureEntity("treefrog/blot");
    }
}
