package net.tropicraft.core.client.entity.renderers;

import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.placeable.FurnitureEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

public class FurnitureRenderer<T extends FurnitureEntity> extends EntityRenderer<T> {
    private final String textureName;
    private final EntityModel<T> model;
    private final float scale;
    private float red = 0.0F, green = 0.0F, blue = 0.0F;

    public FurnitureRenderer(EntityRenderDispatcher renderManager, String textureName, EntityModel<T> model) {
        this(renderManager, textureName, model, 1);
    }
    
    public FurnitureRenderer(EntityRenderDispatcher renderManager, String textureName, EntityModel<T> model, float scale) {
        super(renderManager);
        this.textureName = textureName;
        this.model = model;
        this.scale = scale;
    }

    @Override
    public void render(T furniture, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider buffer, int packedLightIn) {
        stack.push();
        stack.translate(0, getYOffset(), 0);
        stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180 - entityYaw));
        // it used to scale by 0.25, but for some reason this gets it to be around the proper size again?
        stack.scale(scale, scale, scale);
        setupTransforms(stack);

        final float rockingAngle = getRockingAngle(furniture, partialTicks);;
        if (!MathHelper.approximatelyEquals(rockingAngle, 0.0F)) {
            stack.multiply(new Quaternion(getRockingAxis(), rockingAngle, true));
        }

        final float[] colorComponents = furniture.getColor().getColorComponents();
        red = colorComponents[0];
        green = colorComponents[1];
        blue = colorComponents[2];

        // Draw uncolored layer
        VertexConsumer ivertexbuilder = buffer.getBuffer(model.getLayer(TropicraftRenderUtils.getTextureEntity(textureName + "_base_layer")));
        stack.scale(-1.0F, -1.0F, 1.0F);
        model.render(stack, ivertexbuilder, getLight(furniture, partialTicks), OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

        // Draw the colored part
        ivertexbuilder = buffer.getBuffer(model.getLayer(TropicraftRenderUtils.getTextureEntity(textureName + "_color_layer")));
        model.render(stack, ivertexbuilder, getLight(furniture, partialTicks), OverlayTexture.DEFAULT_UV, red, green, blue, 1.0F);

        super.render(furniture, entityYaw, partialTicks, stack, buffer, packedLightIn);
        stack.pop();
    }
    
    protected double getYOffset() {
        return 0.3125;
    }
    
    protected void setupTransforms(MatrixStack stack) {

    }
    
    protected float getRockingAngle(T entity, float partialTicks) {
        float f2 = entity.getTimeSinceHit() - partialTicks;
        float f3 = entity.getDamage() - partialTicks;
        if (f3 < 0.0F) {
            f3 = 0.0F;
        }
        if (f2 > 0.0F) {
            return ((MathHelper.sin(f2) * f2 * f3) / getRockAmount()) * (float) entity.getForwardDirection();
        }
        return 0;
    }

    protected Vec3f getRockingAxis() {
        return new Vec3f(1.0F, 0.0F, 1.0F);
    }

    protected float getRockAmount() {
        return 10;
    }

    @Override
    public Identifier getTexture(final T furniture) {
        return null;
    }
}
