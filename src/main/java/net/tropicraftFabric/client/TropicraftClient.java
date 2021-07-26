package net.tropicraftFabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.item.Item;
import net.tropicraftFabric.client.blockEntity.BambooChestBlockEntityRenderer;
import net.tropicraftFabric.client.entity.models.MaskArmorProvider;
import net.tropicraftFabric.client.entity.renderers.*;
import net.tropicraftFabric.common.block.blockentity.TropicBambooChestBlockEntity;
import net.tropicraftFabric.common.block.testContainer.BoxChestScreen;
import net.tropicraftFabric.common.item.AshenMaskItem;
import net.tropicraftFabric.common.item.IColoredItem;
import net.tropicraftFabric.common.registry.*;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class TropicraftClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        TropicClientPackets.init();
        //Test comment
        //new TropicBambooChestBlockEntity(getBlockEntityType(), new Identifier(Tropicfabricport.MOD_ID, "bamboo_chest")); //((TropicBambooChestBlock) bamboochest.getBlock()).blockId()
        //TropicBambooChestBlockEntity renderEntity = new ChestBlockEntity(ChestCommon.getBlockEntityType(), ((ChestBlock) item.getBlock()).blockId());

        //ScreenRegistry.register(TropicScreenHandler.BOX_SCREEN_HANDLER, BoxScreen::new);
        ScreenRegistry.register(TropicScreenHandler.BOX_CHEST_SCREEN_HANDLER, BoxChestScreen::new);

        setupEntityRenderers();
        itemColorinit();
        setupBlockRenderLayers();
        setupBlockEntityRenderers();

        ArrayList<MaskArmorProvider> MASK_PROVIDER = new ArrayList<>();
        final List<AshenMaskItem> values = TropicraftItems.ASHEN_MASKS.values().asList();
        final int size = values.size();

        for (int i = 0; i < size; i++) {
            AshenMaskItem maskItem = values.get(i);
            MASK_PROVIDER.add(new MaskArmorProvider(maskItem.getMaskType()));
            ArmorRenderingRegistry.registerModel(MASK_PROVIDER.get(i), maskItem);
            ArmorRenderingRegistry.registerTexture(MASK_PROVIDER.get(i), maskItem);
        }

    }

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

    public static void setupBlockRenderLayers() {

        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.THATCH_STAIRS_FUZZY, RenderLayer.getCutoutMipped());

        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.BAMBOO_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.PALM_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.MAHOGANY_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.THATCH_DOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.BAMBOO_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.PALM_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.MAHOGANY_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.THATCH_TRAPDOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.BAMBOO_FLOWER_POT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.BAMBOO_LADDER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.TIKI_TORCH, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.PINEAPPLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.COCONUT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.IRIS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.COFFEE_BUSH, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.ACAI_VINE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.ANEMONE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.BROMELIAD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.CANNA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.COMMELINA_DIFFUSA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.CROCOSMIA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.CROTON, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.DRACAENA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.TROPICAL_FERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.FOLIAGE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.MAGIC_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.ORANGE_ANTHURIUM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.ORCHID, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.PATHOS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.RED_ANTHURIUM, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.GRAPEFRUIT_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.LEMON_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.LIME_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.ORANGE_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.MAHOGANY_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(TropicBlocks.PALM_SAPLING, RenderLayer.getCutout());

        TropicBlocks.BAMBOO_POTTED_TROPICS_PLANTS.forEach(value -> BlockRenderLayerMap.INSTANCE.putBlock(value, RenderLayer.getCutout()));
        TropicBlocks.BAMBOO_POTTED_VANILLA_PLANTS.forEach(value -> BlockRenderLayerMap.INSTANCE.putBlock(value, RenderLayer.getCutout()));
        TropicBlocks.VANILLA_POTTED_TROPICS_PLANTS.forEach(value -> BlockRenderLayerMap.INSTANCE.putBlock(value, RenderLayer.getCutout()));
    }


    public static void setupEntityRenderers() {
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.KOA_HUNTER, (dispatcher, context) -> new KoaRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TROPI_CREEPER, (dispatcher, context) -> new TropiCreeperRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.IGUANA, (dispatcher, context) -> new IguanaRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.UMBRELLA, (dispatcher, context) -> new UmbrellaRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.CHAIR, (dispatcher, context) -> new ChairRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.BEACH_FLOAT, (dispatcher, context) -> new BeachFloatRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TROPI_SKELLY, (dispatcher, context) -> new TropiSkellyRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.EIH, (dispatcher, context) -> new EIHRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.WALL_ITEM, (dispatcher, context) -> new WallItemRenderer(dispatcher));

        //TODO: Currently Rendering, something (It seems to render the eneity as the item idk why tho) >:
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.BAMBOO_ITEM_FRAME, BambooItemFrameEntityRenderer::new);

        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.SEA_TURTLE, (dispatcher, context) -> new SeaTurtleRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.MARLIN, (dispatcher, context) -> new MarlinRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.FAILGULL, (dispatcher, context) -> new FailgullRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.DOLPHIN, (dispatcher, context) -> new TropicraftDolphinRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.SEAHORSE, (dispatcher, context) -> new SeahorseRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TREE_FROG, (dispatcher, context) -> new TreeFrogRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.POISON_BLOT, (dispatcher, context) -> new PoisonBlotRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.SEA_URCHIN, (dispatcher, context) -> new SeaUrchinRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.SEA_URCHIN_EGG_ENTITY, (dispatcher, context) -> new EggRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.STARFISH, (dispatcher, context) -> new StarfishRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.STARFISH_EGG, (dispatcher, context) -> new EggRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.V_MONKEY, (dispatcher, context) -> new VMonkeyRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.PIRANHA, (dispatcher, context) -> new PiranhaRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.RIVER_SARDINE, (dispatcher, context) -> new SardineRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TROPICAL_FISH, (dispatcher, context) -> new TropicraftTropicalFishRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.EAGLE_RAY, (dispatcher, context) -> new EagleRayRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TROPI_SPIDER, (dispatcher, context) -> new TropiSpiderRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TROPI_SPIDER_EGG, (dispatcher, context) -> new EggRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.ASHEN, (dispatcher, context) -> new AshenRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.ASHEN_MASK, (dispatcher, context) -> new AshenMaskRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.EXPLODING_COCONUT, (dispatcher, context) -> new FlyingItemEntityRenderer(dispatcher, context.getItemRenderer()));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.HAMMERHEAD, (dispatcher, context) -> new SharkRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.SEA_TURTLE_EGG, (dispatcher, context) -> new EggRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TROPI_BEE, (dispatcher, context) -> new TropiBeeRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.COWKTAIL, (dispatcher, context) -> new CowktailRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.MAN_O_WAR, (dispatcher, context) -> new ManOWarRenderer(dispatcher));
    }


    public static void setupBlockEntityRenderers() {
        BlockEntityRendererRegistry.INSTANCE.register(TropicBlockEntities.BAMBOO_CHEST, BambooChestBlockEntityRenderer::new);
        BuiltinItemRendererRegistry.INSTANCE.register(TropicBlocks.BAMBOO_CHEST, (itemStack, transform, stack, source, light, overlay) ->
                BlockEntityRenderDispatcher.INSTANCE.renderEntity(new TropicBambooChestBlockEntity(), stack, source, light, overlay));

        /*
        ClientRegistry.bindTileEntityRenderer(TropicraftTileEntityTypes.BAMBOO_CHEST, BambooChestRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TropicraftTileEntityTypes.SIFTER, SifterRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TropicraftTileEntityTypes.DRINK_MIXER, DrinkMixerRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TropicraftTileEntityTypes.AIR_COMPRESSOR, AirCompressorRenderer::new);
         */
    }
    /*
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
    */
}