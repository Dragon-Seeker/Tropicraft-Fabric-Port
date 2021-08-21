package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.tropicraft.Constants;
import net.tropicraft.core.client.entity.models.IguanaModel;
import net.tropicraft.core.common.entity.neutral.IguanaEntity;

public class IguanaRenderer extends MobEntityRenderer<IguanaEntity, IguanaModel> {
    private static final String IGOR = "igor";

    private static final Identifier DEFAULT_TEXTURE = new Identifier(Constants.MODID, "textures/entity/iggy.png");
    private static final Identifier IGOR_TEXTURE = new Identifier(Constants.MODID, "textures/entity/iggy_igor.png");

    public IguanaRenderer(final EntityRenderDispatcher rendererManager) {
        super(rendererManager, new IguanaModel(), 0.5F);
        this.shadowOpacity = 0.5f;
    }

    @Override
    public Identifier getTexture(final IguanaEntity entity) {
        if (entity.getName().getString().equalsIgnoreCase(IGOR)) {
            return IGOR_TEXTURE;
        }

        return DEFAULT_TEXTURE;
    }
}
