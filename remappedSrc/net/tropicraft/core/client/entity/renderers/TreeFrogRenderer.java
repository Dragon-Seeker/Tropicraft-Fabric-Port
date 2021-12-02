package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tropicraft.core.client.entity.models.TreeFrogModel;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.neutral.TreeFrogEntity;

public class TreeFrogRenderer extends MobRenderer<TreeFrogEntity, TreeFrogModel> {

    public TreeFrogRenderer(final EntityRenderDispatcher rendererManager) {
        super(rendererManager, new TreeFrogModel(), 0.5F);
        shadowStrength = 0.5f;
        shadowRadius = 0.3f;
    }
    
    @Override
    public ResourceLocation getTexture(TreeFrogEntity entity) {
        return TropicraftRenderUtils.getTextureEntity("treefrog/treefrog" + entity.getColor());
    }
}
