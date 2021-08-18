package net.tropicraftFabric.common.dimension.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.ScaleLayer;
import net.minecraft.world.biome.layer.SmoothLayer;
import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.*;
import net.minecraft.world.biome.source.BiomeLayerSampler;

import java.util.function.LongFunction;

public class TropicraftLayerUtil {
    public static BiomeLayerSampler buildTropicsProcedure(long seed, Registry<Biome> biomes) {
        TropicraftBiomeIds biomeIds = new TropicraftBiomeIds(biomes);
        final LayerFactory<CachingLayerSampler> noiseLayer = buildTropicsProcedure(biomeIds, procedure -> new CachingLayerContext(25, seed, procedure));
        return new TropicraftLookupLayer(noiseLayer);
    }

    private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> buildTropicsProcedure(TropicraftBiomeIds biomeIds, final LongFunction<C> context) {
        LayerFactory<T> islandLayer = new TropicsIslandLayer(biomeIds).create(context.apply(1));
        LayerFactory<T> fuzzyZoomLayer = ScaleLayer.FUZZY.create(context.apply(2000), islandLayer);
        LayerFactory<T> addIslandLayer = TropicraftAddIslandLayer.basic3(biomeIds).create(context.apply(3), fuzzyZoomLayer);
        LayerFactory<T> zoomLayer = ScaleLayer.NORMAL.create(context.apply(2000), addIslandLayer);

        LayerFactory<T> oceanLayer = new TropicraftAddInlandLayer(20, biomeIds).create(context.apply(9), zoomLayer);
        oceanLayer = ScaleLayer.NORMAL.create(context.apply(9), oceanLayer);
        addIslandLayer = TropicraftAddIslandLayer.rainforest13(biomeIds).create(context.apply(6), oceanLayer);
        zoomLayer = ScaleLayer.NORMAL.create(context.apply(2001), addIslandLayer);
        zoomLayer = ScaleLayer.NORMAL.create(context.apply(2004), zoomLayer);
        addIslandLayer = TropicraftAddIslandLayer.basic2(biomeIds).create(context.apply(8), zoomLayer);

        LayerFactory<T> biomeLayerGen = new TropicraftBiomesLayer(biomeIds).create(context.apply(15), addIslandLayer);
        LayerFactory<T> oceanLayerGen = TropicraftAddWeightedSubBiomesLayer.ocean(biomeIds).create(context.apply(16), biomeLayerGen);
        LayerFactory<T> hillsLayerGen = TropicraftAddSubBiomesLayer.rainforest(biomeIds).create(context.apply(17), oceanLayerGen);
        zoomLayer = ScaleLayer.NORMAL.create(context.apply(2002), hillsLayerGen);

        LayerFactory<T> riverLayer = zoomLayer;
        riverLayer = new TropicraftRiverInitLayer(biomeIds).create(context.apply(12), riverLayer);
        riverLayer = magnify(2007, ScaleLayer.NORMAL, riverLayer, 5, context);
        riverLayer = new TropicraftRiverLayer(biomeIds).create(context.apply(13), riverLayer);
        riverLayer = SmoothLayer.INSTANCE.create(context.apply(2008L), riverLayer);

        LayerFactory<T> magnifyLayer = magnify(2007L, ScaleLayer.NORMAL, zoomLayer, 3, context);
        LayerFactory<T> biomeLayer = new TropicraftBeachLayer(biomeIds).create(context.apply(20), magnifyLayer);
        biomeLayer = magnify(20, ScaleLayer.NORMAL, biomeLayer, 2, context);

        biomeLayer = SmoothLayer.INSTANCE.create(context.apply(17L), biomeLayer);
        biomeLayer = new TropicraftRiverMixLayer(biomeIds).create(context.apply(17), biomeLayer, riverLayer);

        return biomeLayer;
    }

    private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> magnify(final long seed, final ParentedLayer zoomLayer, final LayerFactory<T> layer, final int count, final LongFunction<C> context) {
        LayerFactory<T> result = layer;
        for (int i = 0; i < count; i++) {
            result = zoomLayer.create(context.apply(seed + i), result);
        }
        return result;
    }
}
