package net.tropicraft.core.client.entity.renderers;

import net.tropicraft.Constants;
import net.tropicraft.core.client.entity.renderlayer.CowktailLayer;
import net.tropicraft.core.common.entity.passive.CowktailEntity;
import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class CowktailRenderer extends MobRenderer<CowktailEntity, CowModel<CowktailEntity>>
{
	private static final Map<CowktailEntity.Type, ResourceLocation> textures = Util.make(Maps.newHashMap(), (map) -> {
		map.put(CowktailEntity.Type.IRIS, new ResourceLocation(Constants.MODID, "textures/entity/cowktail/iris_cowktail.png"));
		map.put(CowktailEntity.Type.ANEMONE, new ResourceLocation(Constants.MODID, "textures/entity/cowktail/anemone_cowktail.png"));
	});

	public CowktailRenderer(EntityRendererProvider.Context context) {
		super(context, new CowModel<>(context.bakeLayer(ModelLayers.COW)), 0.7F);
		this.addLayer(new CowktailLayer<>(this));
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	public ResourceLocation getTextureLocation(CowktailEntity entity) {
		return textures.get(entity.getCowktailType());
	}
}