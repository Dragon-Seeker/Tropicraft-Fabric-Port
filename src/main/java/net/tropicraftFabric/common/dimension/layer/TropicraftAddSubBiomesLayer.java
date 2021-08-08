package net.tropicraftFabric.common.dimension.layer;

import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public final class TropicraftAddSubBiomesLayer implements IdentitySamplingLayer {
	final int baseID;
	final int[] subBiomeIDs;

	TropicraftAddSubBiomesLayer(final int baseID, final int[] subBiomeIDs) {
		this.baseID = baseID;
		this.subBiomeIDs = subBiomeIDs;
	}

	public static TropicraftAddSubBiomesLayer rainforest(TropicraftBiomeIds biomeIds) {
		return new TropicraftAddSubBiomesLayer(biomeIds.rainforestPlains, new int[] { biomeIds.rainforestPlains, biomeIds.rainforestHills, biomeIds.rainforestMountains });
	}

	@Override
	public int sample(LayerRandomnessSource random, int center) {
		if (center == baseID) {
			return subBiomeIDs[random.nextInt(subBiomeIDs.length)];
		} else {
			return center;
		}
	}
}
