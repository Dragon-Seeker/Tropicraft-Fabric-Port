package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.tropicraft.core.client.entity.models.AbstractFishModel;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.client.util.TropicraftSpecialRenderHelper;
import net.tropicraft.core.common.entity.underdasea.IAtlasFish;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TropicraftFishRenderer<T extends FishEntity, M extends AbstractFishModel<T>> extends MobEntityRenderer<T, M> {
    private TropicraftSpecialRenderHelper renderHelper;
    private static final Logger LOGGER = LogManager.getLogger();

    public TropicraftFishRenderer(final EntityRendererFactory.Context context, M modelbase, float f) {
        super(context, modelbase, f);
        renderHelper = new TropicraftSpecialRenderHelper();
    }

    /**
     * This override is a hack
     */
    @Override
    public void render(T entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn) {
        boolean isVisible = this.isVisible(entity);
        boolean shouldRender = !isVisible && !entity.isInvisibleTo(MinecraftClient.getInstance().player);
        if (isVisible || shouldRender) {
            boolean glowing = MinecraftClient.getInstance().hasOutline(entity);
            renderFishy(entity, partialTicks, matrixStackIn, bufferIn.getBuffer(getRenderLayer(entity, isVisible, shouldRender, glowing)), packedLightIn, getOverlay(entity, getAnimationCounter(entity, partialTicks)));
        }
        super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    protected void renderFishy(T entity, float partialTicks, MatrixStack stack, VertexConsumer buffer, int light, int overlay) {
        stack.push();

        stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90));
        stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-(MathHelper.lerp(partialTicks, entity.prevHeadYaw, entity.headYaw))));
        stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
        stack.scale(0.3f, 0.3f, 0.5f);
        stack.translate(.85F, -0.3F, 0.0F);

        int fishTex = 0;
        if (entity instanceof IAtlasFish) {
            fishTex = ((IAtlasFish) entity).getAtlasSlot() * 2;
        }

        renderHelper.renderFish(stack, buffer, fishTex, light, overlay);

        stack.translate(-1.7f, 0, 0);
        stack.translate(.85f, 0, 0.025f);
        stack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(model.tail.yaw));
        stack.translate(-.85f, 0, -0.025f);
        renderHelper.renderFish(stack, buffer, fishTex + 1, light, overlay);

        stack.pop();
    }

    @Override
    protected void scale(T entity, MatrixStack stack, float partialTickTime) {
        stack.scale(.75F, .20F, .20F);
    }

    @Override
    public Identifier getTexture(T entity) {
        return TropicraftRenderUtils.getTextureEntity("tropical_fish");
    }
}
