package net.tropicraft.core.client.entity.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tropicraft.Constants;
import net.tropicraft.core.client.entity.models.EIHModel;
import net.tropicraft.core.common.entity.neutral.EIHEntity;
import org.jetbrains.annotations.Nullable;


public class EIHRenderer extends MobRenderer<EIHEntity, EIHModel> {

    private static final ResourceLocation TEXTURE_SLEEP = new ResourceLocation(Constants.MODID, "textures/entity/eih/headtext.png");
    private static final ResourceLocation TEXTURE_AWARE = new ResourceLocation(Constants.MODID, "textures/entity/eih/headawaretext.png");
    private static final ResourceLocation TEXTURE_ANGRY = new ResourceLocation(Constants.MODID, "textures/entity/eih/headangrytext.png");

    public EIHRenderer(final EntityRenderDispatcher rendererManager) {
        super(rendererManager, new EIHModel(), 1.2F);
    }

    @Override
    protected void scale(EIHEntity eih, PoseStack stack, float partialTickTime) {
        stack.scale(2.0F, 1.75F, 2.0F);
    }

    @Nullable
    @Override
    public ResourceLocation getTexture(final EIHEntity eih) {
        if (eih.isAware()) {
            return TEXTURE_AWARE;
        } else if (eih.isAngry()) {
            return TEXTURE_ANGRY;
        } else {
            return TEXTURE_SLEEP;
        }
    }
}
