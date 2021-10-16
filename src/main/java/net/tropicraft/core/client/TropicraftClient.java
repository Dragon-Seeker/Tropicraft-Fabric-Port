package net.tropicraft.core.client;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
//import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
//import net.tropicraft.core.client.blockEntity.BambooChestBlockEntityRenderer;
import net.tropicraft.core.client.registry.TropicClientPackets;
//import net.tropicraft.core.common.block.testContainer.BoxChestScreen;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.common.dimension.TropicraftDimension;
import net.tropicraft.core.common.item.IColoredItem;
import net.tropicraft.core.common.registry.*;
import net.tropicraft.core.mixins.ModelLoaderSTATICDEFINITIONS;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class TropicraftClient implements ClientModInitializer {

    @Environment(EnvType.CLIENT)
    @Override
    public void onInitializeClient() {
        TropicClientPackets.init();

        TropicraftEntityRendering.setupEntityModelLayers();
        TropicraftEntityRendering.setupEntityRenderers();

        TropicraftEntityRendering.setupBlockEntityLayers();
        TropicraftEntityRendering.setupBlockEntityRenderers();

        itemColorinit();

        setupBlockRenderLayers();

        //ArmorRenderingRegistryInitialization();

        //bamboItemFrameModelLoaderInit();

        setupDimensionRenderInfo();


    }

    @Environment(EnvType.CLIENT)
    public static void bamboItemFrameModelLoaderInit(){
        //TODO:Check if item frames are now working
        StateDefinition<Block, BlockState> frameState = new StateDefinition.Builder<Block, BlockState>(Blocks.AIR).add(BooleanProperty.create("map")).create(Block::defaultBlockState, BlockState::new);

        final Map<ResourceLocation, StateDefinition<Block, BlockState>> TEMP_STATIC_DEFINITIONS = ModelLoaderSTATICDEFINITIONS.getStaticDimensions();

        ModelLoaderSTATICDEFINITIONS.setStaticDimensions(ImmutableMap.<ResourceLocation, StateDefinition<Block, BlockState>>builder()
                .putAll(TEMP_STATIC_DEFINITIONS)
                .put(Registry.ITEM.getKey(TropicraftItems.BAMBOO_ITEM_FRAME), frameState)
                .build());
    }


    /*
    @Environment(EnvType.CLIENT)
    public static void ArmorRenderingRegistryInitialization() {
        ArrayList<MaskArmorProvider> MASK_PROVIDER = new ArrayList<>();
        final List<AshenMaskItem> values = TropicraftItems.ASHEN_MASKS.values().asList();
        final int size = values.size();

        for (int i = 0; i < size; i++) {
            AshenMaskItem maskItem = values.get(i);
            MASK_PROVIDER.add(new MaskArmorProvider(maskItem.getMaskType()));
            ArmorRendererRegistryImpl.register();
            ArmorRenderingRegistry.registerModel(MASK_PROVIDER.get(i), maskItem);
            ArmorRenderingRegistry.registerTexture(MASK_PROVIDER.get(i), maskItem);
        }

        final StacheArmorProvider STACHPROVIDER = new StacheArmorProvider();

        ArmorRenderingRegistry.registerModel(STACHPROVIDER, TropicraftItems.NIGEL_STACHE);
        ArmorRenderingRegistry.registerTexture(STACHPROVIDER, TropicraftItems.NIGEL_STACHE);
    }
     */

    @Environment(EnvType.CLIENT)
    public static void itemColorinit(){
        for(Item item : TropicraftItems.COCKTAILS.values().asList()) {
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((IColoredItem) stack.getItem()).getColor(stack, tintIndex), item); //tintIndex > 0 ? CocktailItem.getCocktailColor(stack) :
            //ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? CocktailItem.getCocktailColor(stack) : ((DyeableItem) stack.getItem()).getColor(stack), item);
        }

        for(Item item : TropicraftItems.CHAIRS.values().asList()) {
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((IColoredItem) stack.getItem()).getColor(stack, tintIndex), item); }

        for(Item item : TropicraftItems.BEACH_FLOATS.values().asList()) {
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((IColoredItem) stack.getItem()).getColor(stack, tintIndex), item); }

        for(Item item : TropicraftItems.UMBRELLAS.values().asList()) {
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((IColoredItem) stack.getItem()).getColor(stack, tintIndex), item); }
    }

    @Environment(EnvType.CLIENT)
    public static void setupBlockRenderLayers() {

        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.THATCH_STAIRS_FUZZY, RenderType.cutoutMipped());

        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.BAMBOO_DOOR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.PALM_DOOR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.MAHOGANY_DOOR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.THATCH_DOOR, RenderType.cutout());

        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.BAMBOO_TRAPDOOR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.PALM_TRAPDOOR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.MAHOGANY_TRAPDOOR, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.THATCH_TRAPDOOR, RenderType.cutout());

        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.BAMBOO_FLOWER_POT, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.BAMBOO_LADDER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.TIKI_TORCH, RenderType.cutout());

        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.PINEAPPLE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.COCONUT, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.IRIS, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.COFFEE_BUSH, RenderType.cutout());

        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.ACAI_VINE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.ANEMONE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.BROMELIAD, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.CANNA, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.COMMELINA_DIFFUSA, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.CROCOSMIA, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.CROTON, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.DRACAENA, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.TROPICAL_FERN, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.FOLIAGE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.MAGIC_MUSHROOM, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.ORANGE_ANTHURIUM, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.ORCHID, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.PATHOS, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.RED_ANTHURIUM, RenderType.cutout());

        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.GRAPEFRUIT_SAPLING, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.LEMON_SAPLING, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.LIME_SAPLING, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.ORANGE_SAPLING, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.MAHOGANY_SAPLING, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.PALM_SAPLING, RenderType.cutout());

        TropicraftBlocks.BAMBOO_POTTED_TROPICS_PLANTS.forEach(value -> BlockRenderLayerMap.INSTANCE.putBlock(value, RenderType.cutout()));
        TropicraftBlocks.BAMBOO_POTTED_VANILLA_PLANTS.forEach(value -> BlockRenderLayerMap.INSTANCE.putBlock(value, RenderType.cutout()));
        TropicraftBlocks.VANILLA_POTTED_TROPICS_PLANTS.forEach(value -> BlockRenderLayerMap.INSTANCE.putBlock(value, RenderType.cutout()));


    }

    @Environment(EnvType.CLIENT)
    public static void setupDimensionRenderInfo() {

        DimensionSpecialEffects.EFFECTS.put(TropicraftDimension.WORLD.location(), new DimensionSpecialEffects(192.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false) {
            @Override
            public Vec3 getBrightnessDependentFogColor(Vec3 color, float brightness) {
                return color.multiply(brightness * 0.94F + 0.06F, brightness * 0.94F + 0.06F, brightness * 0.91F + 0.09F);
            }

            @Override
            public boolean isFoggyAt(int x, int z) {
                return false;
            }
        });

    }

}
