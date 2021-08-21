package net.tropicraft.core.client.entity.renderers;

import net.tropicraft.Constants;
import net.tropicraft.core.client.entity.models.TropiCreeperModel;
import net.tropicraft.core.common.entity.passive.TropiCreeperEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class TropiCreeperRenderer extends MobEntityRenderer<TropiCreeperEntity, TropiCreeperModel> {

    private static final Identifier CREEPER_TEXTURE = new Identifier(Constants.MODID, "textures/entity/tropicreeper.png");

    public TropiCreeperRenderer(EntityRenderDispatcher rendererManager) {
        super(rendererManager, new TropiCreeperModel(), 0.5F);
    }

    // From CreperRenderer
    @Override
    protected void scale(TropiCreeperEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
        float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        matrixStackIn.scale(f2, f3, f2);
    }

    // From Creeper Renderer
    @Override
    protected float getAnimationCounter(TropiCreeperEntity livingEntityIn, float partialTicks) {
        float f = livingEntityIn.getCreeperFlashIntensity(partialTicks);
        return (int) (f * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(f, 0.5F, 1.0F);
    }

    public Identifier getTexture(TropiCreeperEntity e) {
        return CREEPER_TEXTURE;
    }
}
