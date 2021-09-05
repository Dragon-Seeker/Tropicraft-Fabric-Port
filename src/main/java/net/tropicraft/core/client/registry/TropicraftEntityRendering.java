package net.tropicraft.core.client.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.client.rendering.ArmorRendererRegistryImpl;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.util.Identifier;
import net.tropicraft.Constants;
import net.tropicraft.core.client.entity.models.*;
import net.tropicraft.core.client.entity.renderers.*;
import net.tropicraft.core.client.item.MaskArmorProvider;
import net.tropicraft.core.client.item.StacheArmorProvider;
import net.tropicraft.core.common.item.AshenMaskItem;
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.tropicraft.core.common.registry.TropicraftItems;
import shadow.fabric.api.client.rendering.v1.ArmorRenderingRegistry;
//import net.tropicraft.core.client.entity.renderlayer.AshenMaskLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class TropicraftEntityRendering {
    //public static EntityModelLayer KOA_HUNTER_LAYER;
    //public static EntityModelLayer TROPI_CREEPER_LAYER;
    //public static EntityModelLayer IGUANA_LAYER;
    //public static EntityModelLayer UMBRELLA_LAYER;
    //public static EntityModelLayer BEACH_FLOAT_LAYER;
    //public static EntityModelLayer CHAIR_LAYER;
    //public static EntityModelLayer TROPI_SKELLY_LAYER;
    //public static EntityModelLayer EIH_LAYER;
    //public static EntityModelLayer WALL_ITEM_LAYER;
    //public static EntityModelLayer BAMBOO_ITEM_FRAME_LAYER;
    //public static EntityModelLayer SEA_TURTLE_LAYER;
    //public static EntityModelLayer MARLIN_LAYER;
    //public static EntityModelLayer FAILGULL_LAYER;
    //public static EntityModelLayer DOLPHIN_LAYER;
    //public static EntityModelLayer SEAHORSE_LAYER;
    //public static EntityModelLayer TREE_FROG_LAYER;
    //public static EntityModelLayer POISON_BLOT_LAYER;
    //public static EntityModelLayer SEA_URCHIN_LAYER;
    //public static EntityModelLayer SEA_URCHIN_EGG_ENTITY_LAYER;
    //public static EntityModelLayer STARFISH_LAYER;
    //public static EntityModelLayer STARFISH_EGG_LAYER;
    //public static EntityModelLayer V_MONKEY_LAYER;
    public static EntityModelLayer PIRANHA_LAYER;
    public static EntityModelLayer RIVER_SARDINE_LAYER;
    public static EntityModelLayer TROPICAL_FISH_LAYER;
    public static EntityModelLayer EAGLE_RAY_LAYER;
    public static EntityModelLayer TROPI_SPIDER_LAYER;
    //public static EntityModelLayer TROPI_SPIDER_EGG_LAYER;
    public static EntityModelLayer ASHEN_LAYER;
    //public static EntityModelLayer ASHEN_MASK_LAYER;
    //public static EntityModelLayer EXPLODING_COCONUT_LAYER;
    //public static EntityModelLayer LAVA_BALL_LAYER;
    //public static EntityModelLayer HAMMERHEAD_LAYER;
    //public static EntityModelLayer SEA_TURTLE_EGG_LAYER;
    //public static EntityModelLayer TROPI_BEE_LAYER;
    //public static EntityModelLayer COWKTAIL_LAYER;
    //public static EntityModelLayer MAN_O_WAR_LAYER;

    public static ArrayList<EntityModelLayer> ASHEN_MASK_LAYERS = new ArrayList<>();//= registerMain("mask", PlayerHeadpieceModel::getTexturedModelData);

    public static EntityModelLayer STACHE_LAYER;

    @Environment(EnvType.CLIENT)
    public static void init() {

    }

    @Environment(EnvType.CLIENT)
    public static void setupEntityModelLayers() {
        //KOA_HUNTER_LAYER = registerMain("koa_hunter", () -> KoaModel.getTexturedModelData());
        //TROPI_CREEPER_LAYER = registerMain("tropi_creeper", () -> TropiCreeperModel.getTexturedModelData());
        //IGUANA_LAYER = registerMain("iguana", () -> IguanaModel.getTexturedModelData());
        //UMBRELLA_LAYER = registerMain("umbrella", () -> UmbrellaModel.getTexturedModelData());
        //BEACH_FLOAT_LAYER = registerMain("beach_float", () -> BeachFloatModel.getTexturedModelData());
        //CHAIR_LAYER = registerMain("chair", () -> ChairModel.getTexturedModelData());
        //TROPI_SKELLY_LAYER = registerMain("tropi_skelly", () -> TropiSkellyModel.getTexturedModelData());
        //EIH_LAYER = registerMain("eih", () -> EIHModel.getTexturedModelData());
        //WALL_ITEM_LAYER = registerMain("wall_item", () -> KoaModel.getTexturedModelData());
        //TODO: Currently Rendering, something (It seems to render the eneity as the item idk why tho) >:
        //BAMBOO_ITEM_FRAME_LAYER = registerMain("eih", () -> EntityModelLayers.getTexturedModelData());
        //SEA_TURTLE_LAYER = registerMain("sea_turtle", () -> SeaTurtleModel.getTexturedModelData());
        //MARLIN_LAYER = registerMain("marlin", () -> MarlinModel.getTexturedModelData());
        //FAILGULL_LAYER = registerMain("failgull", () -> FailgullModel.getTexturedModelData());
        //DOLPHIN_LAYER = registerMain("dolphin", () -> TropicraftDolphinModel.getTexturedModelData());
        //SEAHORSE_LAYER = registerMain("seahorse", () -> SeahorseModel.getTexturedModelData());
        //TREE_FROG_LAYER = registerMain("tree_frog", () -> TreeFrogModel.getTexturedModelData());
        //POISON_BLOT_LAYER = registerMain("eih", () -> EIHModel.getTexturedModelData());
        //SEA_URCHIN_LAYER = registerMain("sea_urchin", () -> SeaUrchinModel.getTexturedModelData());
        //SEA_URCHIN_EGG_ENTITY_LAYER = registerMain("sea_urchin_egg", () -> EggModel.getTexturedModelData());
        //STARFISH_LAYER = registerMain("starfish", () -> StarfishRenderer.getTexturedModelData());
        //STARFISH_EGG_LAYER = registerMain("starfish_egg", () -> EggModel.getTexturedModelData());
        //V_MONKEY_LAYER = registerMain("v_monkey", () -> VMonkeyModel.getTexturedModelData());
        PIRANHA_LAYER = registerMain("piranha", PiranhaModel::getTexturedModelData);
        RIVER_SARDINE_LAYER = registerMain("river_sardine", SardineModel::getTexturedModelData);
        TROPICAL_FISH_LAYER = registerMain("tropical_fish", TropicraftTropicalFishModel::getTexturedModelData);
        EAGLE_RAY_LAYER = registerMain("eagle_ray", EagleRayModel::getTexturedModelData);
        TROPI_SPIDER_LAYER = registerMain("tropi_spider", SpiderEntityModel::getTexturedModelData);
        // TROPI_SPIDER_EGG_LAYER =  = registerMain("sea_urchin_egg", () -> EggModel.getTexturedModelData());
        ASHEN_LAYER = registerMain("ashen", AshenModel::getTexturedModelData);
        // ASHEN_MASK_LAYER = registerMain("sea_urchin_egg", () -> AshenMaskLayer.getTexturedModelData());
        // EXPLODING_COCONUT_LAYER;
        // LAVA_BALL_LAYER;
        // HAMMERHEAD_LAYER = registerMain("hammerhead", () -> SharkModel.getTexturedModelData());
        // SEA_TURTLE_EGG_LAYER  = registerMain("sea_urchin_egg", () -> EggModel.getTexturedModelData());
        // TROPI_BEE_LAYER = registerMain("tropi_bee", () -> TropiBeeModel.getTexturedModelData());
        // COWKTAIL_LAYER = registerMain("cowktail", () -> CowEntityModel.getTexturedModelData());
        // MAN_O_WAR_LAYER = registerMain("man_o_war", () -> ManOWarModel.getTexturedModelData());

        armorRenderingRegistryInitialization();
    }


    @Environment(EnvType.CLIENT)
    public static void setupEntityRenderers() {
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.KOA_HUNTER, KoaRenderer::new);
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TROPI_CREEPER, (dispatcher, context) -> new TropiCreeperRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.IGUANA, (dispatcher, context) -> new IguanaRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.UMBRELLA, (dispatcher, context) -> new UmbrellaRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.BEACH_FLOAT, (dispatcher, context) -> new BeachFloatRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.CHAIR, (dispatcher, context) -> new ChairRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TROPI_SKELLY, (dispatcher, context) -> new TropiSkellyRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.EIH, (dispatcher, context) -> new EIHRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.WALL_ITEM, (dispatcher, context) -> new WallItemRenderer(dispatcher));
        //TODO: Currently Rendering, something (It seems to render the eneity as the item idk why tho) >:
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.BAMBOO_ITEM_FRAME, BambooItemFrameEntityRenderer::new);
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.SEA_TURTLE, (dispatcher, context) -> new SeaTurtleRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.MARLIN, (dispatcher, context) -> new MarlinRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.FAILGULL, (dispatcher, context) -> new FailgullRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.DOLPHIN, (dispatcher, context) -> new TropicraftDolphinRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.SEAHORSE, (dispatcher, context) -> new SeahorseRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TREE_FROG, (dispatcher, context) -> new TreeFrogRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.POISON_BLOT, (dispatcher, context) -> new PoisonBlotRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.SEA_URCHIN, (dispatcher, context) -> new SeaUrchinRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.SEA_URCHIN_EGG_ENTITY, (dispatcher, context) -> new EggRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.STARFISH, (dispatcher, context) -> new StarfishRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.STARFISH_EGG, (dispatcher, context) -> new EggRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.V_MONKEY, (dispatcher, context) -> new VMonkeyRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.PIRANHA, PiranhaRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.RIVER_SARDINE, SardineRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TROPICAL_FISH, TropicraftTropicalFishRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.EAGLE_RAY, EagleRayRenderer::new);
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TROPI_SPIDER, (dispatcher, context) -> new TropiSpiderRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TROPI_SPIDER_EGG, (dispatcher, context) -> new EggRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.ASHEN, (dispatcher, context) -> new AshenRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.ASHEN_MASK, (dispatcher, context) -> new AshenMaskRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.EXPLODING_COCONUT, (dispatcher, context) -> new FlyingItemEntityRenderer(dispatcher, context.getItemRenderer()));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.LAVA_BALL, (dispatcher, context) -> new FlyingItemEntityRenderer(dispatcher, context.getItemRenderer()));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.HAMMERHEAD, (dispatcher, context) -> new SharkRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.SEA_TURTLE_EGG, (dispatcher, context) -> new EggRenderer(dispatcher));
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TROPI_BEE, (dispatcher, context) -> new TropiBeeRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.COWKTAIL, CowktailRenderer::new);
        //EntityRendererRegistry.INSTANCE.register(TropicraftEntities.MAN_O_WAR, (dispatcher, context) -> new ManOWarRenderer(dispatcher));
    }

    @Environment(EnvType.CLIENT)
    public static void setupBlockEntityRenderers() {
        //BlockEntityRendererRegistry.INSTANCE.register(TropicBlockEntities.BAMBOO_CHEST, BambooChestBlockEntityRenderer::new);
        //BuiltinItemRendererRegistry.INSTANCE.register(TropicraftBlocks.BAMBOO_CHEST, (itemStack, transform, stack, source, light, overlay) ->
        //        BlockEntityRenderDispatcher.renderEntity(new TropicBambooChestBlockEntity(), stack, source, light, overlay));

        /*
        ClientRegistry.bindTileEntityRenderer(TropicraftTileEntityTypes.BAMBOO_CHEST, BambooChestRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TropicraftTileEntityTypes.SIFTER, SifterRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TropicraftTileEntityTypes.DRINK_MIXER, DrinkMixerRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TropicraftTileEntityTypes.AIR_COMPRESSOR, AirCompressorRenderer::new);
         */
    }

    @Environment(EnvType.CLIENT)
    public static void armorRenderingRegistryInitialization() {
        ArrayList<MaskArmorProvider> MASK_PROVIDER = new ArrayList<>();
        //ArrayList<EntityModelLayer> TEMP_ASHEN_MASK_LAYERS = new ArrayList<>();
        final List<AshenMaskItem> values = TropicraftItems.ASHEN_MASKS.values().asList();
        final int size = values.size();

        for (int i = 0; i < size; i++) {
            AshenMaskItem maskItem = values.get(i);
            EntityModelLayer ashen_mask_layer = registerMain("ashen_mask_" + maskItem.getMaskType().name().toLowerCase(Locale.ROOT), PlayerHeadpieceModel::getTexturedModelData);
            ASHEN_MASK_LAYERS.add(ashen_mask_layer);

            MASK_PROVIDER.add(new MaskArmorProvider(maskItem.getMaskType()));
            //ArmorRendererRegistryImpl.register();
            ArmorRenderingRegistry.registerModel(MASK_PROVIDER.get(i), maskItem);
            ArmorRenderingRegistry.registerTexture(MASK_PROVIDER.get(i), maskItem);


        }

        STACHE_LAYER = registerMain("nigel_stache", PlayerHeadpieceModel::getTexturedModelData);

        final StacheArmorProvider STACHPROVIDER = new StacheArmorProvider();

        ArmorRenderingRegistry.registerModel(STACHPROVIDER, TropicraftItems.NIGEL_STACHE);
        ArmorRenderingRegistry.registerTexture(STACHPROVIDER, TropicraftItems.NIGEL_STACHE);


    }




    @Environment(EnvType.CLIENT)
    private static EntityModelLayer registerMain(String id, EntityModelLayerRegistry.TexturedModelDataProvider textureModelData) {
        EntityModelLayer modelLayer = new EntityModelLayer(new Identifier(Constants.MODID, id), "main");
        EntityModelLayerRegistry.registerModelLayer(modelLayer, textureModelData);
        return modelLayer;
    }
}
