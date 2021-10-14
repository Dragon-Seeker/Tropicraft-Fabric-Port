package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.tropicraft.core.client.entity.models.TreeFrogModel;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.entity.neutral.TreeFrogEntity;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class TreeFrogRenderer extends MobEntityRenderer<TreeFrogEntity, TreeFrogModel> {

    public TreeFrogRenderer(EntityRendererFactory.Context context) {
        super(context, new TreeFrogModel(context.getPart(TropicraftEntityRendering.TREE_FROG_LAYER)), 0.5F);
        shadowOpacity = 0.5f;
        shadowRadius = 0.3f;
    }
    
    @Override
    public Identifier getTexture(TreeFrogEntity entity) {
        return TropicraftRenderUtils.getTextureEntity("treefrog/treefrog" + entity.getColor());
    }
}


