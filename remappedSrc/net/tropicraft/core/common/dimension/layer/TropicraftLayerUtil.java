package net.tropicraft.core.common.dimension.layer;

import net.minecraft.core.Registry;
import net.minecraft.world.biome.layer.util.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.area.AreaFactory;
import net.minecraft.world.level.newbiome.area.LazyArea;
import net.minecraft.world.level.newbiome.context.BigContext;
import net.minecraft.world.level.newbiome.context.LazyAreaContext;
import net.minecraft.world.level.newbiome.layer.Layer;
import net.minecraft.world.level.newbiome.layer.SmoothLayer;
import net.minecraft.world.level.newbiome.layer.ZoomLayer;
import net.minecraft.world.level.newbiome.layer.traits.AreaTransformer1;
import java.util.function.LongFunction;

public class TropicraftLayerUtil {
    public static Layer buildTropicsProcedure(long seed, Registry<Biome> biomes) {
        TropicraftBiomeIds biomeIds = new TropicraftBiomeIds(biomes);
        final AreaFactory<LazyArea> noiseLayer = buildTropicsProcedure(biomeIds, procedure -> new LazyAreaContext(25, seed, procedure));
        return new TropicraftLookupLayer(noiseLayer);
    }

    private static <T extends Area, C extends BigContext<T>> AreaFactory<T> buildTropicsProcedure(TropicraftBiomeIds biomeIds, final LongFunction<C> context) {
        AreaFactory<T> islandLayer = new TropicsIslandLayer(biomeIds).run(context.apply(1));
        AreaFactory<T> fuzzyZoomLayer = ZoomLayer.FUZZY.run(context.apply(2000), islandLayer);
        AreaFactory<T> addIslandLayer = TropicraftAddIslandLayer.basic3(biomeIds).run(context.apply(3), fuzzyZoomLayer);
        AreaFactory<T> zoomLayer = ZoomLayer.NORMAL.run(context.apply(2000), addIslandLayer);

        AreaFactory<T> oceanLayer = new TropicraftAddInlandLayer(20, biomeIds).run(context.apply(9), zoomLayer);
        oceanLayer = ZoomLayer.NORMAL.run(context.apply(9), oceanLayer);
        addIslandLayer = TropicraftAddIslandLayer.rainforest13(biomeIds).run(context.apply(6), oceanLayer);
        zoomLayer = ZoomLayer.NORMAL.run(context.apply(2001), addIslandLayer);
        zoomLayer = ZoomLayer.NORMAL.run(context.apply(2004), zoomLayer);
        addIslandLayer = TropicraftAddIslandLayer.basic2(biomeIds).run(context.apply(8), zoomLayer);

        AreaFactory<T> biomeLayerGen = new TropicraftBiomesLayer(biomeIds).run(context.apply(15), addIslandLayer);
        AreaFactory<T> oceanLayerGen = TropicraftAddWeightedSubBiomesLayer.ocean(biomeIds).run(context.apply(16), biomeLayerGen);
        AreaFactory<T> hillsLayerGen = TropicraftAddSubBiomesLayer.rainforest(biomeIds).run(context.apply(17), oceanLayerGen);
        zoomLayer = ZoomLayer.NORMAL.run(context.apply(2002), hillsLayerGen);

        AreaFactory<T> riverLayer = zoomLayer;
        riverLayer = new TropicraftRiverInitLayer(biomeIds).run(context.apply(12), riverLayer);
        riverLayer = magnify(2007, ZoomLayer.NORMAL, riverLayer, 5, context);
        riverLayer = new TropicraftRiverLayer(biomeIds).run(context.apply(13), riverLayer);
        riverLayer = SmoothLayer.INSTANCE.run(context.apply(2008L), riverLayer);

        AreaFactory<T> magnifyLayer = magnify(2007L, ZoomLayer.NORMAL, zoomLayer, 3, context);
        AreaFactory<T> biomeLayer = new TropicraftBeachLayer(biomeIds).run(context.apply(20), magnifyLayer);
        biomeLayer = magnify(20, ZoomLayer.NORMAL, biomeLayer, 2, context);

        biomeLayer = SmoothLayer.INSTANCE.run(context.apply(17L), biomeLayer);
        biomeLayer = new TropicraftRiverMixLayer(biomeIds).run(context.apply(17), biomeLayer, riverLayer);

        return biomeLayer;
    }

    private static <T extends Area, C extends BigContext<T>> AreaFactory<T> magnify(final long seed, final AreaTransformer1 zoomLayer, final AreaFactory<T> layer, final int count, final LongFunction<C> context) {
        AreaFactory<T> result = layer;
        for (int i = 0; i < count; i++) {
            result = zoomLayer.run(context.apply(seed + i), result);
        }
        return result;
    }
}
