package net.tropicraftFabric.client.entity.renderers;

import net.tropicraftFabric.client.entity.models.TreeFrogModel;
import net.tropicraftFabric.client.util.TropicraftRenderUtils;
import net.tropicraftFabric.common.entity.neutral.TreeFrogEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class TreeFrogRenderer extends MobEntityRenderer<TreeFrogEntity, TreeFrogModel> {

    public TreeFrogRenderer(final EntityRenderDispatcher rendererManager) {
        super(rendererManager, new TreeFrogModel(), 0.5F);
        shadowOpacity = 0.5f;
        shadowRadius = 0.3f;
    }
    
    @Override
    public Identifier getTexture(TreeFrogEntity entity) {
        return TropicraftRenderUtils.getTextureEntity("treefrog/treefrog" + entity.getColor());
    }
}
