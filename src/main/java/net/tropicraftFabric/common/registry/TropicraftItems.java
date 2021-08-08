package net.tropicraftFabric.common.registry;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.tropicraftFabric.Constants;
import net.tropicraftFabric.common.drinks.Drink;
import net.tropicraftFabric.common.entity.placeable.BeachFloatEntity;
import net.tropicraftFabric.common.entity.placeable.ChairEntity;
import net.tropicraftFabric.common.entity.placeable.UmbrellaEntity;
import net.tropicraftFabric.common.item.*;
import net.tropicraftFabric.common.item.armor.FireArmorItem;
import net.tropicraftFabric.common.item.armor.ScaleArmorItem;
import net.tropicraftFabric.common.item.tools.*;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import static net.tropicraftFabric.common.item.Builder.getDefaultProperties;

public class TropicraftItems {
    //public static BlockItem COCONUT = new TropicBlockItem(TropicBlocks.COCONUT);

    public static BlockItem BAMBOO_CHEST = new TropicBlockItem(TropicraftBlocks.BAMBOO_CHEST);

    public static final Item AZURITE = registerItem("azurite_gem", new TropicItem());
    public static final Item EUDIALYTE = registerItem("eudialyte_gem", new TropicItem());
    public static final Item ZIRCON = registerItem("zircon_gem", new TropicItem());
    public static final Item SHAKA = registerItem("shaka_ingot", new TropicItem());
    public static final Item MANGANESE = registerItem("manganese_ingot", new TropicItem());
    public static final Item ZIRCONIUM = registerItem("zirconium_gem", new TropicItem());

    public static final Item BAMBOO_STICK = registerItem("bamboo_stick", new TropicItem());


    public static final Item SOLONOX_SHELL = registerItem("solonox_shell", new TropicItem());
    public static final Item FROX_CONCH = registerItem("frox_conch", new TropicItem());
    public static final Item RUBE_NAUTILUS = registerItem("rube_nautilus", new TropicItem());
    public static final Item PAB_SHELL = registerItem("pab_shell", new TropicItem());
    public static final Item STARFISH = registerItem("starfish", new TropicItem());
    public static final Item TURTLE_SHELL = registerItem("turtle_shell", new TropicItem());

    public static final Item LEMON = registerItem("lemon", new Item(getDefaultProperties().food(TropicFood.LEMON)));
    public static final Item LIME = registerItem("lime", new Item(getDefaultProperties().food(TropicFood.LIME)));
    public static final Item GRAPEFRUIT = registerItem("grapefruit", new Item(getDefaultProperties().food(TropicFood.GRAPEFRUIT)));
    public static final Item ORANGE = registerItem("orange", new Item(getDefaultProperties().food(TropicFood.ORANGE)));
    public static final Item PINEAPPLE_CUBES = registerItem("pineapple_cubes", new Item(getDefaultProperties().food(TropicFood.PINEAPPLE_CUBES)));
    public static final Item COCONUT_CHUNK = registerItem("coconut_chunk", new Item(getDefaultProperties().food(TropicFood.COCONUT_CHUNK)));


    public static final Item FRESH_MARLIN = registerItem("fresh_marlin", new Item(getDefaultProperties().food(TropicFood.FRESH_MARLIN)));
    public static final Item SEARED_MARLIN = registerItem("seared_marlin", new Item(getDefaultProperties().food(TropicFood.SEARED_MARLIN)));
    public static final Item RAW_RAY = registerItem("raw_ray", new Item(getDefaultProperties().food(TropicFood.RAW_RAY)));
    public static final Item COOKED_RAY = registerItem("cooked_ray", new Item(getDefaultProperties().food(TropicFood.COOKED_RAY)));
    public static final Item FROG_LEG = registerItem("frog_leg", new Item(getDefaultProperties().food(TropicFood.RAW_FROG_LEG)));
    public static final Item COOKED_FROG_LEG = registerItem("cooked_frog_leg", new Item(getDefaultProperties().food(TropicFood.COOKED_FROG_LEG)));
    public static final Item SEA_URCHIN_ROE = registerItem("sea_urchin_roe", new Item(getDefaultProperties().food(TropicFood.SEA_URCHIN_ROE)));
    public static final Item TOASTED_NORI = registerItem("toasted_nori", new Item(getDefaultProperties().food(TropicFood.TOASTED_NORI)));
    public static final Item RAW_FISH = registerItem("raw_fish", new Item(getDefaultProperties().food(TropicFood.RAW_FISH)));
    public static final Item COOKED_FISH = registerItem("cooked_fish", new Item(getDefaultProperties().food(TropicFood.COOKED_FISH)));

    public static final Item RAW_COFFEE_BEAN = registerItem("raw_coffee_bean", new CoffeeBeanItem(TropicraftBlocks.COFFEE_BUSH, getDefaultProperties()));
    public static final Item ROASTED_COFFEE_BEAN = registerItem("roasted_coffee_bean", new Item(getDefaultProperties()));
    public static final Item COFFEE_BERRY = registerItem("coffee_berry", new Item(getDefaultProperties()));
    public static final Item BAMBOO_MUG = registerItem("bamboo_mug", new Item(getDefaultProperties()));

    /*
    public static final RegistryObject<LoveTropicsShellItem> LOVE_TROPICS_SHELL = register(
            "love_tropics_shell", () -> new LoveTropicsShellItem(new Item.Properties()));

    public static final RegistryObject<BlockNamedItem> RAW_COFFEE_BEAN = register("raw_coffee_bean", Builder.blockNamedItem(TropicraftBlocks.COFFEE_BUSH));
    */

    public static final ImmutableMap<Drink, Item> COCKTAILS = ImmutableMap.copyOf(
            Drink.DRINKS.values().stream()
                .collect(Collectors.toMap(Function.identity(), d -> registerItem(d.name, Builder.cocktail(d)))));


    public static final Item WHITE_PEARL = registerItem("white_pearl", new TropicItem());
    public static final Item BLACK_PEARL = registerItem("black_pearl", new TropicItem());
    public static final Item SCALE = registerItem("scale", new TropicItem());

    //TODO: Not done with this item as it isn't correct
    public static final Item NIGEL_STACHE = registerItem("nigel_stache", new TropicItem());
    /*
    public static final RegistryObject<Item> NIGEL_STACHE = register(
            "nigel_stache", () -> new NigelStacheItem(new Item.Properties().group(Tropicraft.TROPICRAFT_ITEM_GROUP)));
     */

    public static final Item POISON_FROG_SKIN = registerItem("poison_frog_skin", new TropicItem());
    public static final Item IGUANA_LEATHER = registerItem("iguana_leather", new TropicItem());

    public enum TropicTiers{
        BAMBOO(new TropicItemTier(1,110, 1F, 6, 1.2F, TropicraftItems.BAMBOO_STICK)),
        ZIRCON(new TropicItemTier(2,200, 1F, 14, 4.5F, TropicraftItems.ZIRCON)),
        EUDIALYTE(new TropicItemTier(2,750, 2F, 14, 6.5F, TropicraftItems.EUDIALYTE)),
        ZIRCONIUM(new TropicItemTier(3,1800, 3F, 10, 8.5f, TropicraftItems.ZIRCONIUM));


        public final TropicItemTier defaultTier;

        TropicTiers(TropicItemTier defaultTier) {
            this.defaultTier = defaultTier;
        }

        public TropicItemTier getDefaultTier() {
            return this.defaultTier;
        }
    }

    public static final Item ZIRCON_HOE = registerItem("zircon_hoe", new TropicHoe(TropicTiers.ZIRCON, 0, -2.0f, getDefaultProperties()));
    public static final Item ZIRCONIUM_HOE = registerItem("zirconium_hoe", new TropicHoe(TropicTiers.EUDIALYTE, 0, -2.0f, getDefaultProperties()));
    public static final Item EUDIALYTE_HOE = registerItem("eudialyte_hoe", new TropicHoe(TropicTiers.ZIRCONIUM, 0, -2.0f, getDefaultProperties()));

    public static final Item ZIRCON_AXE = registerItem("zircon_axe", new TropicAxe(TropicTiers.ZIRCON, 5.0F, -2.0f, getDefaultProperties()));
    public static final Item ZIRCONIUM_AXE = registerItem("zirconium_axe", new TropicAxe(TropicTiers.EUDIALYTE, 5.0F, -2.0f, getDefaultProperties()));
    public static final Item EUDIALYTE_AXE = registerItem("eudialyte_axe", new TropicAxe(TropicTiers.ZIRCONIUM, 5.0F, -2.0f, getDefaultProperties()));

    public static final Item ZIRCON_PICKAXE = registerItem("zircon_pickaxe", new TropicPickaxe(TropicTiers.ZIRCON, 2, -2.0f, getDefaultProperties()));
    public static final Item ZIRCONIUM_PICKAXE = registerItem("zirconium_pickaxe", new TropicPickaxe(TropicTiers.EUDIALYTE, 2, -2.0f, getDefaultProperties()));
    public static final Item EUDIALYTE_PICKAXE = registerItem("eudialyte_pickaxe", new TropicPickaxe(TropicTiers.ZIRCONIUM, 2, -2.0f, getDefaultProperties()));

    public static final Item ZIRCON_SHOVEL = registerItem("zircon_shovel", new TropicShovel(TropicTiers.ZIRCON, 2.0F, -3.0f, getDefaultProperties()));
    public static final Item ZIRCONIUM_SHOVEL = registerItem("zirconium_shovel", new TropicShovel(TropicTiers.EUDIALYTE, 2.0F, -3.0f, getDefaultProperties()));
    public static final Item EUDIALYTE_SHOVEL = registerItem("eudialyte_shovel", new TropicShovel(TropicTiers.ZIRCONIUM, 2.0F, -3.0f, getDefaultProperties()));

    public static final Item ZIRCON_SWORD = registerItem("zircon_sword", new TropicSword(TropicTiers.ZIRCON, 3, -3.0f, getDefaultProperties()));
    public static final Item ZIRCONIUM_SWORD = registerItem("zirconium_sword", new TropicSword(TropicTiers.EUDIALYTE, 3, -3.0f, getDefaultProperties()));
    public static final Item EUDIALYTE_SWORD = registerItem("eudialyte_sword", new TropicSword(TropicTiers.ZIRCONIUM, 3, -3.0f, getDefaultProperties()));

    public static final Item DAGGER = registerItem("dagger", new DaggerItem(TropicTiers.ZIRCON, getDefaultProperties().maxCount(1)));
    public static final Item BAMBOO_SPEAR = registerItem("bamboo_spear", new SpearItem(TropicTiers.BAMBOO, 3, -2.4F, getDefaultProperties().maxCount(1)));

    public static final Item FIRE_BOOTS = registerItem("fire_boots", new FireArmorItem(EquipmentSlot.FEET, getDefaultProperties().maxCount(1).maxDamage(300)));
    public static final Item FIRE_LEGGINGS = registerItem("fire_leggings", new FireArmorItem(EquipmentSlot.LEGS, getDefaultProperties().maxCount(1).maxDamage(300)));
    public static final Item FIRE_CHESTPLATE = registerItem("fire_chestplate", new FireArmorItem(EquipmentSlot.CHEST, getDefaultProperties().maxCount(1).maxDamage(300)));
    public static final Item FIRE_HELMET = registerItem("fire_helmet", new FireArmorItem(EquipmentSlot.HEAD, getDefaultProperties().maxCount(1).maxDamage(300)));

    public static final Item SCALE_BOOTS = registerItem("scale_boots", new ScaleArmorItem(EquipmentSlot.FEET, getDefaultProperties().maxCount(1)));
    public static final Item SCALE_LEGGINGS = registerItem("scale_leggings", new ScaleArmorItem(EquipmentSlot.LEGS, getDefaultProperties().maxCount(1)));
    public static final Item SCALE_CHESTPLATE = registerItem("scale_chestplate", new ScaleArmorItem(EquipmentSlot.CHEST, getDefaultProperties().maxCount(1)));
    public static final Item SCALE_HELMET = registerItem("scale_helmet", new ScaleArmorItem(EquipmentSlot.HEAD, getDefaultProperties().maxCount(1)));

    //FIXME: Super Borked as it is invisible and directional placement seems to also be borked
    public static final Item BAMBOO_ITEM_FRAME = registerItem("bamboo_item_frame", new BambooItemFrameItem(getDefaultProperties()));
    /*
    public static final RegistryObject<Item> BAMBOO_ITEM_FRAME = register(
            "bamboo_item_frame", () -> new BambooItemFrameItem(new Item.Properties().group(Tropicraft.TROPICRAFT_ITEM_GROUP)));
     */

    public static final Item TROPICAL_FERTILIZER = registerItem("tropical_fertilizer", new TropicalFertilizerItem(getDefaultProperties()));


    public static final ImmutableMap<RecordMusic, Item> MUSIC_DISCS =
            Arrays
            .stream(RecordMusic.values())
            .collect(Maps.<RecordMusic, RecordMusic, Item>toImmutableEnumMap(Function.identity(), (type) -> registerItem("music_disc_" + type.name().toLowerCase(Locale.ROOT), Builder.musicDisc(type))));

    public static final Item BLOW_GUN = registerItem("blow_gun", new BlowGunItem(getDefaultProperties().maxCount(1)));
    public static final Item EXPLODING_COCONUT = registerItem("exploding_coconut", new ExplodingCoconutItem(getDefaultProperties()));

    public static final ImmutableMap<AshenMasks, AshenMaskItem> ASHEN_MASKS = Arrays.stream(AshenMasks.values())
            .collect(Maps.<AshenMasks, AshenMasks, AshenMaskItem>toImmutableEnumMap(Function.identity(), type -> registerItemMask("ashen_mask_" + type.name().toLowerCase(Locale.ROOT), Builder.mask(type))));

    /*
    public static final ImmutableMap<AshenMasks, RegistryObject<AshenMaskItem>> ASHEN_MASKS = Arrays.stream(AshenMasks.values())
            .collect(Maps.toImmutableEnumMap(Function.identity(), type -> register("ashen_mask_" + type.name().toLowerCase(Locale.ROOT), Builder.mask(type))));
    */

    /*
    public static final RegistryObject<Item> WATER_WAND = register(
            "water_wand", () -> new WaterWandItem(new Item.Properties().group(Tropicraft.TROPICRAFT_ITEM_GROUP).maxStackSize(1).maxDamage(2000)));
     */

    public static final Item WATER_WAND = registerItem("water_wand", new WaterWandItem(getDefaultProperties().maxCount(1).maxDamage(2000)));

    public static final Item FISHING_NET = registerItem("fishing_net", new Item(getDefaultProperties().maxCount(1)));


    public static final Item KOA_SPAWN_EGG = registerItem("koa_spawn_egg", Builder.spawnEgg(TropicraftEntities.KOA_HUNTER));
    public static final Item TROPICREEPER_SPAWN_EGG = registerItem("tropicreeper_spawn_egg", Builder.spawnEgg(TropicraftEntities.TROPI_CREEPER));
    public static final Item IGUANA_SPAWN_EGG = registerItem("iguana_spawn_egg", Builder.spawnEgg(TropicraftEntities.IGUANA));
    public static final Item TROPISKELLY_SPAWN_EGG = registerItem("tropiskelly_spawn_egg", Builder.spawnEgg(TropicraftEntities.TROPI_SKELLY));
    public static final Item EIH_SPAWN_EGG = registerItem("eih_spawn_egg", Builder.spawnEgg(TropicraftEntities.EIH));
    public static final Item SEA_TURTLE_SPAWN_EGG = registerItem("sea_turtle_spawn_egg", Builder.spawnEgg(TropicraftEntities.SEA_TURTLE));
    public static final Item MARLIN_SPAWN_EGG = registerItem("marlin_spawn_egg", Builder.spawnEgg(TropicraftEntities.MARLIN));
    public static final Item FAILGULL_SPAWN_EGG = registerItem("failgull_spawn_egg", Builder.spawnEgg(TropicraftEntities.FAILGULL));
    public static final Item DOLPHIN_SPAWN_EGG = registerItem("dolphin_spawn_egg", Builder.spawnEgg(TropicraftEntities.DOLPHIN));
    public static final Item SEAHORSE_SPAWN_EGG = registerItem("seahorse_spawn_egg", Builder.spawnEgg(TropicraftEntities.SEAHORSE));
    public static final Item TREE_FROG_SPAWN_EGG = registerItem("tree_frog_spawn_egg", Builder.spawnEgg(TropicraftEntities.TREE_FROG));
    public static final Item SEA_URCHIN_SPAWN_EGG = registerItem("sea_urchin_spawn_egg", Builder.spawnEgg(TropicraftEntities.SEA_URCHIN));
    public static final Item V_MONKEY_SPAWN_EGG = registerItem("v_monkey_spawn_egg", Builder.spawnEgg(TropicraftEntities.V_MONKEY));
    public static final Item PIRANHA_SPAWN_EGG = registerItem("piranha_spawn_egg", Builder.spawnEgg(TropicraftEntities.PIRANHA));
    public static final Item SARDINE_SPAWN_EGG = registerItem("sardine_spawn_egg", Builder.spawnEgg(TropicraftEntities.RIVER_SARDINE));
    public static final Item TROPICAL_FISH_SPAWN_EGG = registerItem("tropical_fish_spawn_egg", Builder.spawnEgg(TropicraftEntities.TROPICAL_FISH));
    public static final Item EAGLE_RAY_SPAWN_EGG = registerItem("eagle_ray_spawn_egg", Builder.spawnEgg(TropicraftEntities.EAGLE_RAY));
    public static final Item TROPI_SPIDER_SPAWN_EGG = registerItem("tropi_spider_spawn_egg", Builder.spawnEgg(TropicraftEntities.TROPI_SPIDER));
    public static final Item ASHEN_SPAWN_EGG = registerItem("ashen_spawn_egg", Builder.spawnEgg(TropicraftEntities.ASHEN));
    public static final Item HAMMERHEAD_SPAWN_EGG = registerItem("hammerhead_spawn_egg", Builder.spawnEgg(TropicraftEntities.HAMMERHEAD));
    public static final Item COWKTAIL_SPAWN_EGG = registerItem("cowktail_spawn_egg", Builder.spawnEgg(TropicraftEntities.COWKTAIL));
    public static final Item MAN_O_WAR_SPAWN_EGG = registerItem("man_o_war_spawn_egg", Builder.spawnEgg(TropicraftEntities.MAN_O_WAR));
    public static final Item TROPIBEE_SPAWN_EGG = registerItem("tropibee_spawn_egg", Builder.spawnEgg(TropicraftEntities.TROPI_BEE));

    public static final ImmutableMap<DyeColor, FurnitureItem<UmbrellaEntity>> UMBRELLAS = Arrays.stream(DyeColor.values())
            .collect(Maps.<DyeColor, DyeColor, FurnitureItem<UmbrellaEntity>>toImmutableEnumMap(Function.identity(), c -> (FurnitureItem<UmbrellaEntity>) registerItem(c.asString() + "_umbrella", Builder.umbrella(c))));

    public static final ImmutableMap<DyeColor, FurnitureItem<ChairEntity>> CHAIRS = Arrays.stream(DyeColor.values())
            .collect(Maps.<DyeColor, DyeColor, FurnitureItem<ChairEntity>>toImmutableEnumMap(Function.identity(), c -> (FurnitureItem<ChairEntity>) registerItem(c.asString() + "_chair", Builder.chair(c))));

    public static final ImmutableMap<DyeColor, FurnitureItem<BeachFloatEntity>> BEACH_FLOATS = Arrays.stream(DyeColor.values())
            .collect(Maps.<DyeColor, DyeColor, FurnitureItem<BeachFloatEntity>>toImmutableEnumMap(Function.identity(), c -> (FurnitureItem<BeachFloatEntity>) registerItem(c.asString() + "_beach_float", Builder.beachFloat(c))));



    public static final Item TROPICAL_FISH_BUCKET = registerItem("tropical_fish_bucket", Builder.fishBucket(TropicraftEntities.TROPICAL_FISH));
    public static final Item SARDINE_BUCKET = registerItem("sardine_bucket", Builder.fishBucket(TropicraftEntities.RIVER_SARDINE));
    public static final Item PIRANHA_BUCKET = registerItem("piranha_bucket", Builder.fishBucket(TropicraftEntities.PIRANHA));



    //--------------------ITEMS HARDER TO PORT STILL--------------------

    /*
    public static final RegistryObject<PonyBottleItem> YELLOW_PONY_BOTTLE = register("yellow_pony_bottle", Builder.item(PonyBottleItem::new, Builder.getDefaultProperties().maxStackSize(1).maxDamage(32)));
    public static final RegistryObject<ScubaGogglesItem> YELLOW_SCUBA_GOGGLES = register("yellow_scuba_goggles", Builder.scubaGoggles(ScubaType.YELLOW));
    public static final RegistryObject<ScubaHarnessItem> YELLOW_SCUBA_HARNESS = register("yellow_scuba_harness", Builder.scubaHarness(ScubaType.YELLOW));
    public static final RegistryObject<ScubaFlippersItem> YELLOW_SCUBA_FLIPPERS = register("yellow_scuba_flippers", Builder.scubaFlippers(ScubaType.YELLOW));

     public static final RegistryObject<PonyBottleItem> PINK_PONY_BOTTLE = register("pink_pony_bottle", Builder.item(PonyBottleItem::new, Builder.getDefaultProperties().maxStackSize(1).maxDamage(32)));
    public static final RegistryObject<ScubaGogglesItem> PINK_SCUBA_GOGGLES = register("pink_scuba_goggles", Builder.scubaGoggles(ScubaType.PINK));
    public static final RegistryObject<ScubaHarnessItem> PINK_SCUBA_HARNESS = register("pink_scuba_harness", Builder.scubaHarness(ScubaType.PINK));
    public static final RegistryObject<ScubaFlippersItem> PINK_SCUBA_FLIPPERS = register("pink_scuba_flippers", Builder.scubaFlippers(ScubaType.PINK));


     */


    public static void init() {}

    public static Item registerItem(String id, Item item) {
        Registry.register(Registry.ITEM, new Identifier(Constants.MODID, id), item);

        return item;
    }

    public static AshenMaskItem registerItemMask(String id, AshenMaskItem item) {
        Registry.register(Registry.ITEM, new Identifier(Constants.MODID, id), item);

        return item;
    }

    public static BlockItem registerBlockItem(String id, BlockItem blockItem) {
        Registry.register(Registry.ITEM, new Identifier(Constants.MODID, id), blockItem);

        return blockItem;
    }



}
