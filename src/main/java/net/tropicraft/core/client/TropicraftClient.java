package net.tropicraft.core.client;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
//import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.impl.client.rendering.ArmorRendererRegistryImpl;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
//import net.tropicraft.core.client.blockEntity.BambooChestBlockEntityRenderer;
import net.tropicraft.core.client.item.MaskArmorProvider;
import net.tropicraft.core.client.entity.renderers.*;
import net.tropicraft.core.client.item.StacheArmorProvider;
import net.tropicraft.core.client.registry.TropicClientPackets;
//import net.tropicraft.core.common.block.testContainer.BoxChestScreen;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.common.dimension.TropicraftDimension;
import net.tropicraft.core.common.item.AshenMaskItem;
import net.tropicraft.core.common.item.IColoredItem;
import net.tropicraft.core.common.registry.*;
import net.tropicraft.core.mixins.ModelLoaderSTATICDEFINITIONS;
import shadow.fabric.api.client.rendering.v1.ArmorRenderingRegistry;

import java.util.ArrayList;
import java.util.List;
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

        //Test comment
        //new TropicBambooChestBlockEntity(getBlockEntityType(), new Identifier(Tropicfabricport.MOD_ID, "bamboo_chest")); //((TropicBambooChestBlock) bamboochest.getBlock()).blockId()
        //TropicBambooChestBlockEntity renderEntity = new ChestBlockEntity(ChestCommon.getBlockEntityType(), ((ChestBlock) item.getBlock()).blockId());

        //ScreenRegistry.register(TropicScreenHandler.BOX_SCREEN_HANDLER, BoxScreen::new);
        //ScreenRegistry.register(TropicScreenHandler.BOX_CHEST_SCREEN_HANDLER, BoxChestScreen::new);

        itemColorinit();

        setupBlockRenderLayers();


        //ArmorRenderingRegistryInitialization();

        //bamboItemFrameModelLoaderInit();

        setupDimensionRenderInfo();


    }

    @Environment(EnvType.CLIENT)
    public static void bamboItemFrameModelLoaderInit(){
        //TODO:Check if item frames are now working
        StateManager<Block, BlockState> frameState = new StateManager.Builder<Block, BlockState>(Blocks.AIR).add(BooleanProperty.of("map")).build(Block::getDefaultState, BlockState::new);

        final Map<Identifier, StateManager<Block, BlockState>> TEMP_STATIC_DEFINITIONS = ModelLoaderSTATICDEFINITIONS.getStaticDimensions();

        ModelLoaderSTATICDEFINITIONS.setStaticDimensions(ImmutableMap.<Identifier, StateManager<Block, BlockState>>builder()
                .putAll(TEMP_STATIC_DEFINITIONS)
                .put(Registry.ITEM.getId(TropicraftItems.BAMBOO_ITEM_FRAME), frameState)
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

        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.THATCH_STAIRS_FUZZY, RenderLayer.getCutoutMipped());

        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.BAMBOO_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.PALM_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.MAHOGANY_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.THATCH_DOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.BAMBOO_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.PALM_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.MAHOGANY_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.THATCH_TRAPDOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.BAMBOO_FLOWER_POT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.BAMBOO_LADDER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.TIKI_TORCH, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.PINEAPPLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.COCONUT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.IRIS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.COFFEE_BUSH, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.ACAI_VINE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.ANEMONE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.BROMELIAD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.CANNA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.COMMELINA_DIFFUSA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.CROCOSMIA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.CROTON, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.DRACAENA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.TROPICAL_FERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.FOLIAGE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.MAGIC_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.ORANGE_ANTHURIUM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.ORCHID, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.PATHOS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.RED_ANTHURIUM, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.GRAPEFRUIT_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.LEMON_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.LIME_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.ORANGE_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.MAHOGANY_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicraftBlocks.PALM_SAPLING, RenderLayer.getCutout());

        TropicraftBlocks.BAMBOO_POTTED_TROPICS_PLANTS.forEach(value -> BlockRenderLayerMap.INSTANCE.putBlock(value, RenderLayer.getCutout()));
        TropicraftBlocks.BAMBOO_POTTED_VANILLA_PLANTS.forEach(value -> BlockRenderLayerMap.INSTANCE.putBlock(value, RenderLayer.getCutout()));
        TropicraftBlocks.VANILLA_POTTED_TROPICS_PLANTS.forEach(value -> BlockRenderLayerMap.INSTANCE.putBlock(value, RenderLayer.getCutout()));


    }

    @Environment(EnvType.CLIENT)
    public static void setupDimensionRenderInfo() {

        SkyProperties.BY_IDENTIFIER.put(TropicraftDimension.WORLD.getValue(), new SkyProperties(192.0F, true, SkyProperties.SkyType.NORMAL, false, false) {
            @Override
            public Vec3d adjustFogColor(Vec3d color, float brightness) {
                return color.multiply(brightness * 0.94F + 0.06F, brightness * 0.94F + 0.06F, brightness * 0.91F + 0.09F);
            }

            @Override
            public boolean useThickFog(int x, int z) {
                return false;
            }
        });

    }

}
