package net.tropicraftFabric.client.entity.renderers;

import net.tropicraftFabric.Constants;
import net.tropicraftFabric.client.entity.models.EIHModel;
import net.tropicraftFabric.common.entity.neutral.EIHEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;


public class EIHRenderer extends MobEntityRenderer<EIHEntity, EIHModel> {

    private static final Identifier TEXTURE_SLEEP = new Identifier(Constants.MODID, "textures/entity/eih/headtext.png");
    private static final Identifier TEXTURE_AWARE = new Identifier(Constants.MODID, "textures/entity/eih/headawaretext.png");
    private static final Identifier TEXTURE_ANGRY = new Identifier(Constants.MODID, "textures/entity/eih/headangrytext.png");

    public EIHRenderer(final EntityRenderDispatcher rendererManager) {
        super(rendererManager, new EIHModel(), 1.2F);
    }

    @Override
    protected void scale(EIHEntity eih, MatrixStack stack, float partialTickTime) {
        stack.scale(2.0F, 1.75F, 2.0F);
    }

    @Nullable
    @Override
    public Identifier getTexture(final EIHEntity eih) {
        if (eih.isAware()) {
            return TEXTURE_AWARE;
        } else if (eih.isAngry()) {
            return TEXTURE_ANGRY;
        } else {
            return TEXTURE_SLEEP;
        }
    }
}
