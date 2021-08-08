package net.tropicraftFabric.common.dimension.layer;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.util.collection.WeightedPicker;
import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

import java.util.List;

public final class TropicraftAddWeightedSubBiomesLayer implements IdentitySamplingLayer {
    private List<WeightedPicker.Entry> biomeWeights;
    private int totalWeight;
    final int baseID;
    final int[] subBiomeIDs;
    private final Object2IntMap<WeightedPicker.Entry> biomeLookup = new Object2IntOpenHashMap<>();

    TropicraftAddWeightedSubBiomesLayer(final int baseID, final int[] subBiomeIDs, WeightedPicker.Entry... weights) {
        if (weights.length > 0) {
            biomeWeights = Lists.newArrayList(weights);
            totalWeight = WeightedPicker.getWeightSum(biomeWeights);
            for (int i = 0; i < weights.length; i++) {
                biomeLookup.put(weights[i], subBiomeIDs[i]);
            }
        }
        this.baseID = baseID;
        this.subBiomeIDs = subBiomeIDs;
    }

    public static TropicraftAddWeightedSubBiomesLayer ocean(TropicraftBiomeIds biomeIds) {
        return new TropicraftAddWeightedSubBiomesLayer(biomeIds.ocean, new int[]{biomeIds.ocean, biomeIds.kelpForest}, new WeightedPicker.Entry(20), new WeightedPicker.Entry(4));
    }

    @Override
    public int sample(LayerRandomnessSource random, int center) {
        if (center == baseID) {
            if (biomeLookup.size() > 0) {
                return biomeLookup.getInt(WeightedPicker.getAt(biomeWeights, random.nextInt(totalWeight)));
            }
            return subBiomeIDs[random.nextInt(subBiomeIDs.length)];
        } else {
            return center;
        }
    }
}
