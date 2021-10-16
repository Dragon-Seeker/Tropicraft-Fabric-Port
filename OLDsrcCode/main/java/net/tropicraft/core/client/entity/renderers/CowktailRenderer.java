package net.tropicraft.core.client.entity.renderers;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.tropicraft.Constants;
import net.tropicraft.core.client.entity.renderlayer.CowktailLayer;
import net.tropicraft.core.common.entity.passive.CowktailEntity;
import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;


import java.util.Map;

@Environment(EnvType.CLIENT)
public class CowktailRenderer extends MobEntityRenderer<CowktailEntity, CowEntityModel<CowktailEntity>>
{
	private static final Map<CowktailEntity.Type, Identifier> textures = Util.make(Maps.newHashMap(), (map) -> {
		map.put(CowktailEntity.Type.IRIS, new Identifier(Constants.MODID, "textures/entity/cowktail/iris_cowktail.png"));
		map.put(CowktailEntity.Type.ANEMONE, new Identifier(Constants.MODID, "textures/entity/cowktail/anemone_cowktail.png"));
	});

	public CowktailRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel<>(context.getPart(EntityModelLayers.COW)), 0.7F);
		this.addFeature(new CowktailLayer<>(this));
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	public Identifier getTexture(CowktailEntity entity) {
		return textures.get(entity.getCowktailType());
	}
}