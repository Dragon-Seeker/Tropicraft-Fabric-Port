package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tropicraft.core.client.entity.models.TreeFrogModel;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.neutral.TreeFrogEntity;

public class TreeFrogRenderer extends MobRenderer<TreeFrogEntity, TreeFrogModel> {

    public TreeFrogRenderer(EntityRendererProvider.Context context) {
        super(context, new TreeFrogModel(context.bakeLayer(TropicraftEntityRendering.TREE_FROG_LAYER)), 0.5F);
        shadowStrength = 0.5f;
        shadowRadius = 0.3f;
    }
    
    @Override
    public ResourceLocation getTextureLocation(TreeFrogEntity entity) {
        return TropicraftRenderUtils.getTextureEntity("treefrog/treefrog" + entity.getColor());
    }
}


