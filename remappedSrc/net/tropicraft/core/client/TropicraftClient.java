package net.tropicraft.core.client;

import com.google.common.collect.ImmutableMap;
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
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
import net.tropicraft.core.client.blockEntity.BambooChestBlockEntityRenderer;
import net.tropicraft.core.client.item.MaskArmorProvider;
import net.tropicraft.core.client.entity.renderers.*;
import net.tropicraft.core.client.item.StacheArmorProvider;
import net.tropicraft.core.common.block.blockentity.TropicBambooChestBlockEntity;
import net.tropicraft.core.common.block.testContainer.BoxChestScreen;
import net.tropicraft.core.common.dimension.TropicraftDimension;
import net.tropicraft.core.common.item.AshenMaskItem;
import net.tropicraft.core.common.item.IColoredItem;
import net.tropicraft.core.common.registry.*;
import net.tropicraft.core.mixins.ModelLoaderSTATICDEFINITIONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class TropicraftClient implements ClientModInitializer {

    @Environment(EnvType.CLIENT)
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

        ArmorRenderingRegistryInitialization();

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

    @Environment(EnvType.CLIENT)
    public static void ArmorRenderingRegistryInitialization() {
        ArrayList<MaskArmorProvider> MASK_PROVIDER = new ArrayList<>();
        final List<AshenMaskItem> values = TropicraftItems.ASHEN_MASKS.values().asList();
        final int size = values.size();

        for (int i = 0; i < size; i++) {
            AshenMaskItem maskItem = values.get(i);
            MASK_PROVIDER.add(new MaskArmorProvider(maskItem.getMaskType()));
            ArmorRenderingRegistry.registerModel(MASK_PROVIDER.get(i), maskItem);
            ArmorRenderingRegistry.registerTexture(MASK_PROVIDER.get(i), maskItem);
        }

        final StacheArmorProvider STACHPROVIDER = new StacheArmorProvider();

        ArmorRenderingRegistry.registerModel(STACHPROVIDER, TropicraftItems.NIGEL_STACHE);
        ArmorRenderingRegistry.registerTexture(STACHPROVIDER, TropicraftItems.NIGEL_STACHE);


    }

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
    public static void setupEntityRenderers() {
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.KOA_HUNTER, (dispatcher, context) -> new KoaRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TROPI_CREEPER, (dispatcher, context) -> new TropiCreeperRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.IGUANA, (dispatcher, context) -> new IguanaRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.UMBRELLA, (dispatcher, context) -> new UmbrellaRenderer(dispatcher));

        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.BEACH_FLOAT, (dispatcher, context) -> new BeachFloatRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.CHAIR, (dispatcher, context) -> new ChairRenderer(dispatcher));

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
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.EXPLODING_COCONUT, (dispatcher, context) -> new ThrownItemRenderer(dispatcher, context.getItemRenderer()));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.LAVA_BALL, (dispatcher, context) -> new FlyingItemEntityRenderer(dispatcher, context.getItemRenderer()));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.HAMMERHEAD, (dispatcher, context) -> new SharkRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.SEA_TURTLE_EGG, (dispatcher, context) -> new EggRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TROPI_BEE, (dispatcher, context) -> new TropiBeeRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.COWKTAIL, (dispatcher, context) -> new CowktailRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.MAN_O_WAR, (dispatcher, context) -> new ManOWarRenderer(dispatcher));
    }

    @Environment(EnvType.CLIENT)
    public static void setupBlockEntityRenderers() {
        BlockEntityRendererRegistry.INSTANCE.register(TropicBlockEntities.BAMBOO_CHEST, BambooChestBlockEntityRenderer::new);
        BuiltinItemRendererRegistry.INSTANCE.register(TropicraftBlocks.BAMBOO_CHEST, (itemStack, transform, stack, source, light, overlay) ->
                BlockEntityRenderDispatcher.instance.renderItem(new TropicBambooChestBlockEntity(), stack, source, light, overlay));

        /*
        ClientRegistry.bindTileEntityRenderer(TropicraftTileEntityTypes.BAMBOO_CHEST, BambooChestRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TropicraftTileEntityTypes.SIFTER, SifterRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TropicraftTileEntityTypes.DRINK_MIXER, DrinkMixerRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TropicraftTileEntityTypes.AIR_COMPRESSOR, AirCompressorRenderer::new);
         */
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
