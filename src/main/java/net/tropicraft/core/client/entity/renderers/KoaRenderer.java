package net.tropicraft.core.client.entity.renderers;
/*
import net.tropicraft.Constants;
import net.tropicraft.core.client.entity.models.KoaModel;
import net.tropicraft.core.common.entity.passive.EntityKoaBase;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class KoaRenderer extends BipedEntityRenderer<EntityKoaBase, KoaModel> {

    private static final Identifier MALE_FISHER = new Identifier(Constants.MODID, "textures/entity/koa/koa_man_fisher.png");
    private static final Identifier FEMALE_FISHER = new Identifier(Constants.MODID, "textures/entity/koa/koa_woman_fisher.png");
    private static final Identifier MALE_HUNTER = new Identifier(Constants.MODID, "textures/entity/koa/koa_man_hunter.png");
    private static final Identifier FEMALE_HUNTER = new Identifier(Constants.MODID, "textures/entity/koa/koa_woman_hunter.png");

    public KoaRenderer(EntityRenderDispatcher rendermanagerIn) {
        super(rendermanagerIn, new KoaModel(0), 0.5F);
        this.shadowOpacity = 0.5f;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
/*
    @Override
    public Identifier getTexture(EntityKoaBase entity) {
        if (entity.getGender() == EntityKoaBase.Genders.MALE) {
            if (entity.getRole() == EntityKoaBase.Roles.HUNTER) {
                return MALE_HUNTER;
            }
            return MALE_FISHER;
        } else {
            if (entity.getRole() == EntityKoaBase.Roles.HUNTER) {
                return FEMALE_HUNTER;
            }
            return FEMALE_FISHER;
        }
    }

    @Nullable
    @Override
    protected RenderLayer getRenderLayer(EntityKoaBase entity, boolean showBody, boolean translucent, boolean showOutline) {
        return RenderLayer.getEntityCutout(getTexture(entity));
    }

}

 */

