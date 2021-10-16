package net.tropicraft.core.client.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.impl.client.rendering.BuiltinItemRendererRegistryImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.tropicraft.Constants;
import net.tropicraft.core.client.blockEntity.AirCompressorRenderer;
import net.tropicraft.core.client.blockEntity.BambooChestBlockEntityRenderer;
import net.tropicraft.core.client.blockEntity.DrinkMixerRenderer;
import net.tropicraft.core.client.blockEntity.SifterRenderer;
import net.tropicraft.core.client.entity.models.*;
import net.tropicraft.core.client.entity.renderers.*;
import net.tropicraft.core.client.item.MaskArmorProvider;
import net.tropicraft.core.client.item.StacheArmorProvider;
import net.tropicraft.core.common.block.blockentity.AirCompressorTileEntity;
import net.tropicraft.core.common.block.blockentity.DrinkMixerTileEntity;
import net.tropicraft.core.common.block.blockentity.TropicBambooChestBlockEntity;
import net.tropicraft.core.common.item.AshenMaskItem;
import net.tropicraft.core.common.registry.TropicBlockEntities;
import net.tropicraft.core.common.registry.TropicraftBlocks;
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.tropicraft.core.common.registry.TropicraftItems;
import shadow.fabric.api.client.rendering.v1.ArmorRenderingRegistry;
//import net.tropicraft.core.client.entity.renderlayer.AshenMaskLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Environment(EnvType.CLIENT)
public class TropicraftEntityRendering {
    public static ModelLayerLocation KOA_HUNTER_LAYER;
    public static ModelLayerLocation TROPI_CREEPER_LAYER;
    public static ModelLayerLocation IGUANA_LAYER;
    public static ModelLayerLocation UMBRELLA_LAYER;
    public static ModelLayerLocation BEACH_FLOAT_LAYER;
    public static ModelLayerLocation CHAIR_LAYER;
    public static ModelLayerLocation TROPI_SKELLY_LAYER;
    public static ModelLayerLocation EIH_LAYER;
    //public static EntityModelLayer WALL_ITEM_LAYER;
    //public static EntityModelLayer BAMBOO_ITEM_FRAME_LAYER;
    public static ModelLayerLocation SEA_TURTLE_LAYER;
    public static ModelLayerLocation MARLIN_LAYER;
    public static ModelLayerLocation FAILGULL_LAYER;
    public static ModelLayerLocation DOLPHIN_LAYER;
    public static ModelLayerLocation SEAHORSE_LAYER;
    public static ModelLayerLocation TREE_FROG_LAYER;
    //public static EntityModelLayer POISON_BLOT_LAYER;
    public static ModelLayerLocation SEA_URCHIN_LAYER;
    public static ModelLayerLocation SEA_URCHIN_EGG_ENTITY_LAYER;
    //public static EntityModelLayer STARFISH_LAYER;
    public static ModelLayerLocation STARFISH_EGG_LAYER;
    public static ModelLayerLocation V_MONKEY_LAYER;
    public static ModelLayerLocation PIRANHA_LAYER;
    public static ModelLayerLocation RIVER_SARDINE_LAYER;
    public static ModelLayerLocation TROPICAL_FISH_LAYER;
    public static ModelLayerLocation EAGLE_RAY_LAYER;
    //public static EntityModelLayer TROPI_SPIDER_LAYER;
    public static ModelLayerLocation TROPI_SPIDER_EGG_LAYER;
    public static ModelLayerLocation ASHEN_LAYER;
    //public static EntityModelLayer ASHEN_MASK_LAYER;
    public static ModelLayerLocation EXPLODING_COCONUT_LAYER;
    public static ModelLayerLocation LAVA_BALL_LAYER;
    public static ModelLayerLocation HAMMERHEAD_LAYER;
    public static ModelLayerLocation SEA_TURTLE_EGG_LAYER;
    public static ModelLayerLocation TROPI_BEE_LAYER;
    public static ModelLayerLocation COWKTAIL_LAYER;
    public static ModelLayerLocation MAN_O_WAR_LAYER;

    public static ModelLayerLocation BAMBOO_MUG;

    public static ArrayList<ModelLayerLocation> ASHEN_MASK_LAYERS = new ArrayList<>();//= registerMain("mask", PlayerHeadpieceModel::getTexturedModelData);

    public static ModelLayerLocation STACHE_LAYER;

    public static ModelLayerLocation BAMBOO_CHEST;
    public static ModelLayerLocation BAMBOO_DOUBLE_CHEST_LEFT;
    public static ModelLayerLocation BAMBOO_DOUBLE_CHEST_RIGHT;
    public static ModelLayerLocation EIHMACHINE_LAYER;
    public static ModelLayerLocation AIRCOMPRESSOR_LAYER;

    @Environment(EnvType.CLIENT)
    public static void setupEntityModelLayers() {
        KOA_HUNTER_LAYER = registerMain("koa_hunter", () -> KoaModel.getTexturedModelData());
        TROPI_CREEPER_LAYER = registerMain("tropi_creeper", () -> TropiCreeperModel.getTexturedModelData());
        IGUANA_LAYER = registerMain("iguana", () -> IguanaModel.getTexturedModelData());
        UMBRELLA_LAYER = registerMain("umbrella", () -> UmbrellaModel.getTexturedModelData());
        BEACH_FLOAT_LAYER = registerMain("beach_float", () -> BeachFloatModel.getTexturedModelData());
        CHAIR_LAYER = registerMain("chair", () -> ChairModel.getTexturedModelData());
        TROPI_SKELLY_LAYER = registerMain("tropi_skelly", () -> TropiSkellyModel.getTexturedModelData());
        EIH_LAYER = registerMain("eih", () -> EIHModel.getTexturedModelData());
        //WALL_ITEM_LAYER = registerMain("wall_item", () -> KoaModel.getTexturedModelData());
        //TODO: Currently Rendering, something (It seems to render the eneity as the item idk why tho) >:
        //BAMBOO_ITEM_FRAME_LAYER = registerMain("eih", () -> EntityModelLayers.getTexturedModelData());
        SEA_TURTLE_LAYER = registerMain("sea_turtle", () -> SeaTurtleModel.getTexturedModelData());
        MARLIN_LAYER = registerMain("marlin", () -> MarlinModel.getTexturedModelData());
        FAILGULL_LAYER = registerMain("failgull", () -> FailgullModel.getTexturedModelData());
        DOLPHIN_LAYER = registerMain("dolphin", () -> TropicraftDolphinModel.getTexturedModelData());
        SEAHORSE_LAYER = registerMain("seahorse", () -> SeahorseModel.getTexturedModelData());
        TREE_FROG_LAYER = registerMain("tree_frog", () -> TreeFrogModel.getTexturedModelData());
        //POISON_BLOT_LAYER = registerMain("eih", () -> EIHModel.getTexturedModelData());
        SEA_URCHIN_LAYER = registerMain("sea_urchin", () -> SeaUrchinModel.getTexturedModelData());
        SEA_URCHIN_EGG_ENTITY_LAYER = registerMain("sea_urchin_egg", () -> EggModel.getTexturedModelData());
        //STARFISH_LAYER = registerMain("starfish", () -> StarfishRenderer.getTexturedModelData());
        STARFISH_EGG_LAYER = registerMain("starfish_egg", () -> EggModel.getTexturedModelData());
        V_MONKEY_LAYER = registerMain("v_monkey", () -> VMonkeyModel.getTexturedModelData());
        PIRANHA_LAYER = registerMain("piranha", PiranhaModel::getTexturedModelData);
        RIVER_SARDINE_LAYER = registerMain("river_sardine", SardineModel::getTexturedModelData);
        TROPICAL_FISH_LAYER = registerMain("tropical_fish", TropicraftTropicalFishModel::getTexturedModelData);
        EAGLE_RAY_LAYER = registerMain("eagle_ray", EagleRayModel::getTexturedModelData);
        //TROPI_SPIDER_LAYER = registerMain("tropi_spider", SpiderEntityModel::getTexturedModelData);
        TROPI_SPIDER_EGG_LAYER = registerMain("tropi_spider_egg", () -> EggModel.getTexturedModelData());
        ASHEN_LAYER = registerMain("ashen", AshenModel::getTexturedModelData);
        //ASHEN_MASK_LAYER = registerMain("ashen_mask", () -> AshenMaskLayer.getTexturedModelData());
        HAMMERHEAD_LAYER = registerMain("hammerhead", () -> SharkModel.getTexturedModelData());
        SEA_TURTLE_EGG_LAYER  = registerMain("turtle_egg", () -> EggModel.getTexturedModelData());
        TROPI_BEE_LAYER = registerMain("tropi_bee", () -> TropiBeeModel.createBodyLayer());
        COWKTAIL_LAYER = registerMain("cowktail", () -> CowModel.createBodyLayer());
        MAN_O_WAR_LAYER = registerMain("man_o_war", () -> ManOWarModel.getTexturedModelData());

        armorRenderingRegistryInitialization();
    }


    @Environment(EnvType.CLIENT)
    public static void setupEntityRenderers() {
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.KOA_HUNTER, KoaRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TROPI_CREEPER, TropiCreeperRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.IGUANA, IguanaRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.UMBRELLA, UmbrellaRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.BEACH_FLOAT, BeachFloatRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.CHAIR, ChairRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TROPI_SKELLY, TropiSkellyRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.EIH, EIHRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.WALL_ITEM, WallItemRenderer::new);
        //TODO: Currently Rendering, something (It seems to render the eneity as the item idk why tho) >:
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.BAMBOO_ITEM_FRAME, BambooItemFrameEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.SEA_TURTLE, SeaTurtleRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.MARLIN, MarlinRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.FAILGULL, FailgullRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.DOLPHIN, TropicraftDolphinRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.SEAHORSE, SeahorseRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TREE_FROG, TreeFrogRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.POISON_BLOT, PoisonBlotRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.SEA_URCHIN, SeaUrchinRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.SEA_URCHIN_EGG_ENTITY, (context) -> new EggRenderer(context, SEA_URCHIN_EGG_ENTITY_LAYER));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.STARFISH, StarfishRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.STARFISH_EGG, (context) -> new EggRenderer(context, STARFISH_EGG_LAYER));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.V_MONKEY, VMonkeyRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.PIRANHA, PiranhaRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.RIVER_SARDINE, SardineRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TROPICAL_FISH, TropicraftTropicalFishRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.EAGLE_RAY, EagleRayRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TROPI_SPIDER, TropiSpiderRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TROPI_SPIDER_EGG, (context) -> new EggRenderer(context, TROPI_SPIDER_EGG_LAYER));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.ASHEN, AshenRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.ASHEN_MASK, AshenMaskRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.EXPLODING_COCONUT, ThrownItemRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.LAVA_BALL, ThrownItemRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.HAMMERHEAD, SharkRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.SEA_TURTLE_EGG, (context) -> new EggRenderer(context, SEA_TURTLE_EGG_LAYER));
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.TROPI_BEE, TropiBeeRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.COWKTAIL, CowktailRenderer::new);
        EntityRendererRegistry.INSTANCE.register(TropicraftEntities.MAN_O_WAR, ManOWarRenderer::new);
    }

    public static void setupBlockEntityLayers(){
        BAMBOO_MUG = registerMain("bamboo_mug", () -> BambooMugModel.getTexturedModelData());

        BAMBOO_CHEST = registerMain("bamboo_chest", () -> BambooChestBlockEntityRenderer.getSingleTexturedModelData());
        BAMBOO_DOUBLE_CHEST_LEFT = registerMain("bamboo_double_chest_left", () -> BambooChestBlockEntityRenderer.getLeftDoubleTexturedModelData());
        BAMBOO_DOUBLE_CHEST_RIGHT = registerMain("bamboo_double_chest_right", () -> BambooChestBlockEntityRenderer.getRightDoubleTexturedModelData());

        EIHMACHINE_LAYER = registerMain("drink_mixer", ()-> EIHMachineModel.getTexturedModelData());

        AIRCOMPRESSOR_LAYER = registerMain("air_compressor", ()-> EIHMachineModel.getTexturedModelData());
    }

    @Environment(EnvType.CLIENT)
    public static void setupBlockEntityRenderers() {
        BlockEntityRendererRegistry.INSTANCE.register(TropicBlockEntities.BAMBOO_CHEST, BambooChestBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(TropicBlockEntities.DRINK_MIXER, DrinkMixerRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(TropicBlockEntities.SIFTER, SifterRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(TropicBlockEntities.AIR_COMPRESSOR, AirCompressorRenderer::new);

        BuiltinItemRendererRegistry.INSTANCE.register(TropicraftBlocks.BAMBOO_CHEST,
                (itemStack, transform, stack, source, light, overlay) ->
                        Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(new TropicBambooChestBlockEntity(BlockPos.ZERO, TropicraftBlocks.BAMBOO_CHEST.defaultBlockState()), stack, source, light, overlay));

        BuiltinItemRendererRegistry.INSTANCE.register(TropicraftBlocks.DRINK_MIXER,
                (itemStack, transform, stack, source, light, overlay) ->
                        Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(new DrinkMixerTileEntity(BlockPos.ZERO, TropicraftBlocks.DRINK_MIXER.defaultBlockState()), stack, source, light, overlay));

        BuiltinItemRendererRegistry.INSTANCE.register(TropicraftBlocks.AIR_COMPRESSOR,
                (itemStack, transform, stack, source, light, overlay) ->
                        Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(new AirCompressorTileEntity(BlockPos.ZERO, TropicraftBlocks.AIR_COMPRESSOR.defaultBlockState()), stack, source, light, overlay));


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
        final List<AshenMaskItem> values = TropicraftItems.ASHEN_MASKS.values().asList();
        final int size = values.size();

        for (int i = 0; i < size; i++) {
            AshenMaskItem maskItem = values.get(i);
            ModelLayerLocation ashen_mask_layer = registerMain("ashen_mask_" + maskItem.getMaskType().name().toLowerCase(Locale.ROOT), PlayerHeadpieceModel::getTexturedModelData);
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
    private static ModelLayerLocation registerMain(String id, EntityModelLayerRegistry.TexturedModelDataProvider textureModelData) {
        ModelLayerLocation modelLayer = new ModelLayerLocation(new ResourceLocation(Constants.MODID, id), "main");
        EntityModelLayerRegistry.registerModelLayer(modelLayer, textureModelData);
        return modelLayer;
    }
}
