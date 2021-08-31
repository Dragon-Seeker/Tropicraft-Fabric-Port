package net.tropicraft.core.common.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.util.Identifier;
import net.tropicraft.Constants;
import net.tropicraft.core.client.entity.models.*;
import net.tropicraft.core.client.entity.renderers.*;
import net.tropicraft.core.client.entity.renderlayer.AshenMaskLayer;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class TropicraftEntityModelLayers {

        public static EntityModelLayer KOA_HUNTER_LAYER = registerMain("koa_hunter", () -> KoaModel.getTexturedModelData());
        public static EntityModelLayer TROPI_CREEPER_LAYER = registerMain("tropi_creeper", () -> TropiCreeperModel.getTexturedModelData());
        public static EntityModelLayer IGUANA_LAYER = registerMain("iguana", () -> IguanaModel.getTexturedModelData());
        public static EntityModelLayer UMBRELLA_LAYER = registerMain("umbrella", () -> UmbrellaModel.getTexturedModelData());
        public static EntityModelLayer BEACH_FLOAT_LAYER = registerMain("beach_float", () -> BeachFloatModel.getTexturedModelData());
        public static EntityModelLayer CHAIR_LAYER = registerMain("chair", () -> ChairModel.getTexturedModelData());

        public static EntityModelLayer TROPI_SKELLY_LAYER = registerMain("tropi_skelly", () -> TropiSkellyModel.getTexturedModelData());
        public static EntityModelLayer EIH_LAYER = registerMain("eih", () -> EIHModel.getTexturedModelData());
        //public static EntityModelLayer WALL_ITEM_LAYER = registerMain("wall_item", () -> KoaModel.getTexturedModelData());

    //TODO: Currently Rendering, something (It seems to render the eneity as the item idk why tho) >:
        //public static EntityModelLayer BAMBOO_ITEM_FRAME_LAYER = registerMain("eih", () -> EntityModelLayers.getTexturedModelData());

        public static EntityModelLayer SEA_TURTLE_LAYER = registerMain("sea_turtle", () -> SeaTurtleModel.getTexturedModelData());
        public static EntityModelLayer MARLIN_LAYER = registerMain("marlin", () -> MarlinModel.getTexturedModelData());
        public static EntityModelLayer FAILGULL_LAYER = registerMain("failgull", () -> FailgullModel.getTexturedModelData());
        public static EntityModelLayer DOLPHIN_LAYER = registerMain("dolphin", () -> TropicraftDolphinModel.getTexturedModelData());
        public static EntityModelLayer SEAHORSE_LAYER = registerMain("seahorse", () -> SeahorseModel.getTexturedModelData());
        public static EntityModelLayer TREE_FROG_LAYER = registerMain("tree_frog", () -> TreeFrogModel.getTexturedModelData());
        //public static EntityModelLayer POISON_BLOT_LAYER = registerMain("eih", () -> EIHModel.getTexturedModelData());
        public static EntityModelLayer SEA_URCHIN_LAYER = registerMain("sea_urchin", () -> SeaUrchinModel.getTexturedModelData());
        public static EntityModelLayer SEA_URCHIN_EGG_ENTITY_LAYER = registerMain("sea_urchin_egg", () -> EggModel.getTexturedModelData());
        //public static EntityModelLayer STARFISH_LAYER = registerMain("starfish", () -> StarfishRenderer.getTexturedModelData());
        public static EntityModelLayer STARFISH_EGG_LAYER = registerMain("starfish_egg", () -> EggModel.getTexturedModelData());
        public static EntityModelLayer V_MONKEY_LAYER = registerMain("v_monkey", () -> VMonkeyModel.getTexturedModelData());
        public static EntityModelLayer PIRANHA_LAYER = registerMain("piranha", () -> PiranhaModel.getTexturedModelData());
        public static EntityModelLayer RIVER_SARDINE_LAYER = registerMain("river_sardine", () -> SardineModel.getTexturedModelData());
        public static EntityModelLayer TROPICAL_FISH_LAYER  = registerMain("tropical_fish", () -> TropicraftTropicalFishModel.getTexturedModelData());
        public static EntityModelLayer EAGLE_RAY_LAYER  = registerMain("eagle_ray", () -> EagleRayModel.getTexturedModelData());
        public static EntityModelLayer TROPI_SPIDER_LAYER  = registerMain("tropi_spider", () -> SpiderEntityModel.getTexturedModelData());
        //public static EntityModelLayer TROPI_SPIDER_EGG_LAYER =  = registerMain("sea_urchin_egg", () -> EggModel.getTexturedModelData());
        public static EntityModelLayer ASHEN_LAYER = registerMain("ashen", () -> AshenModel.getTexturedModelData());
        //public static EntityModelLayer ASHEN_MASK_LAYER = registerMain("sea_urchin_egg", () -> AshenMaskLayer.getTexturedModelData());
        //public static EntityModelLayer EXPLODING_COCONUT_LAYER;
        //public static EntityModelLayer LAVA_BALL_LAYER;
        public static EntityModelLayer HAMMERHEAD_LAYER = registerMain("hammerhead", () -> SharkModel.getTexturedModelData());
        //public static EntityModelLayer SEA_TURTLE_EGG_LAYER  = registerMain("sea_urchin_egg", () -> EggModel.getTexturedModelData());
        public static EntityModelLayer TROPI_BEE_LAYER = registerMain("tropi_bee", () -> TropiBeeModel.getTexturedModelData());
        public static EntityModelLayer COWKTAIL_LAYER = registerMain("cowktail", () -> CowEntityModel.getTexturedModelData());
        public static EntityModelLayer MAN_O_WAR_LAYER = registerMain("man_o_war", () -> ManOWarModel.getTexturedModelData());

    @Environment(EnvType.CLIENT)
    public static void init(){

    }

    @Environment(EnvType.CLIENT)
    private static EntityModelLayer registerMain(String id, EntityModelLayerRegistry.TexturedModelDataProvider textureModelData) {
        EntityModelLayer modelLayer = new EntityModelLayer(new Identifier(Constants.MODID, id), "main");
        EntityModelLayerRegistry.registerModelLayer(modelLayer, textureModelData);
        return modelLayer;
    }
}
