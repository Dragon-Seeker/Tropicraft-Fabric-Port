package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tropicraft.Constants;
import net.tropicraft.core.client.entity.models.KoaModel;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.common.entity.passive.EntityKoaBase;
import org.jetbrains.annotations.Nullable;

public class KoaRenderer extends HumanoidMobRenderer<EntityKoaBase, KoaModel> {

    private static final ResourceLocation MALE_FISHER = new ResourceLocation(Constants.MODID, "textures/entity/koa/koa_man_fisher.png");
    private static final ResourceLocation FEMALE_FISHER = new ResourceLocation(Constants.MODID, "textures/entity/koa/koa_woman_fisher.png");
    private static final ResourceLocation MALE_HUNTER = new ResourceLocation(Constants.MODID, "textures/entity/koa/koa_man_hunter.png");
    private static final ResourceLocation FEMALE_HUNTER = new ResourceLocation(Constants.MODID, "textures/entity/koa/koa_woman_hunter.png");

    public KoaRenderer(EntityRendererProvider.Context context) {
        super(context, new KoaModel(context.bakeLayer(TropicraftEntityRendering.KOA_HUNTER_LAYER)), 0.5F);
        this.shadowStrength = 0.5f;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */

    @Override
    public ResourceLocation getTextureLocation(EntityKoaBase entity) {
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
    protected RenderType getRenderType(EntityKoaBase entity, boolean showBody, boolean translucent, boolean showOutline) {
        return RenderType.entityCutout(getTextureLocation(entity));
    }

}



