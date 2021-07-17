package com.dragonseeker.tropicfabricport.item;

import com.dragonseeker.tropicfabricport.Tropicfabricport;
import com.dragonseeker.tropicfabricport.drinks.Drink;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

import java.util.function.Function;
import java.util.function.Supplier;


public class Builder {

    public static FabricItemSettings getDefaultProperties() {
        return new FabricItemSettings().group(Tropicfabricport.ITEM_GROUP_ITEMS);
    }

    public static Supplier<Item> item() {

        return item(getDefaultProperties());
    }

    public static Supplier<Item> item(FabricItemSettings properties) {
        return item(Item::new, properties);
    }

    public static <T> Supplier<T> item(Function<FabricItemSettings, T> ctor) {
        return item(ctor, getDefaultProperties());
    }

    public static <T> Supplier<T> item(Function<FabricItemSettings, T> ctor, FabricItemSettings properties) {
        return item(ctor, () -> properties);
    }

    public static <T> Supplier<T> item(Function<FabricItemSettings, T> ctor, Supplier<FabricItemSettings> properties) {
        return () -> ctor.apply(properties.get());
    }
    /*
    public static Supplier<BlockNamedItem> blockNamedItem(Supplier<? extends Block> block) {
        return item(p -> new BlockNamedItem(block.get(), p));
    }

    private static <T extends FurnitureEntity> Supplier<FurnitureItem<T>> furniture(Supplier<EntityType<T>> type, DyeColor color) {
        return item(p -> new FurnitureItem<>(p, type, color));
    }

    public static Supplier<FurnitureItem<UmbrellaEntity>> umbrella(final DyeColor color) {
        return furniture(TropicraftEntities.UMBRELLA, color);
    }

    public static Supplier<FurnitureItem<ChairEntity>> chair(final DyeColor color) {
        return furniture(TropicraftEntities.CHAIR, color);
    }

    public static Supplier<FurnitureItem<BeachFloatEntity>> beachFloat(final DyeColor color) {
        return furniture(TropicraftEntities.BEACH_FLOAT, color);
    }

    public static <T extends AbstractFishEntity> Supplier<Item> fishBucket(final Supplier<EntityType<T>> type) {
        return item(p -> new TropicraftFishBucketItem<>(type, Fluids.WATER, getDefaultProperties().maxStackSize(1)));
    }

    public static Supplier<Item> shell() {
        return item(ShellItem::new);
    }

    public static Supplier<Item> food(final FoodComponent food) {
        return new Item(getDefaultProperties().food(food));
    }

    public static Supplier<CocktailItem> cocktail(final Drink drink) {
        return item(p -> {
            CocktailItem ret = new CocktailItem(drink, p);
            //MixerRecipes.setDrinkItem(drink, ret);
            return ret;
        }, () -> getDefaultProperties().maxDamage(0).maxCount(1));
    }
    */

    public static CocktailItem cocktail(final Drink drink) {
        CocktailItem ret = new CocktailItem(drink, getDefaultProperties().maxDamage(0).maxCount(1));
        //MixerRecipes.setDrinkItem(drink, ret);
        return ret;

    }

    public static AshenMaskItem mask(final AshenMasks mask) {
        //return item(p -> new AshenMaskItem(ArmorMaterials.ASHEN_MASK, mask, p));
        return new AshenMaskItem(ArmorMaterials.ASHEN_MASK, mask, getDefaultProperties());
    }


    public static TropicalMusicDiscItem musicDisc(RecordMusic type) {
        TropicalMusicDiscItem musicDisc = new TropicalMusicDiscItem(type, getDefaultProperties().rarity(Rarity.RARE).maxCount(1));
        return musicDisc;
    }

    /*
    public static <T extends Entity> Supplier<Item> spawnEgg(final RegistryObject<EntityType<T>> type) {
        return item(p -> new TropicraftSpawnEgg<>(type, p), Builder::getDefaultProperties);
    }

    public static Supplier<Item> hoe(final IItemTier tier) {
        return item(p -> new HoeItem(tier, 0, -2.0f, getDefaultProperties()));
    }

    public static Supplier<Item> shovel(final IItemTier tier) {
        return item(p -> new ShovelItem(tier, 2.0f, -3.0f, getDefaultProperties()));
    }

    public static Supplier<Item> pickaxe(final IItemTier tier) {
        return item(p -> new PickaxeItem(tier, 2, -2.0f, getDefaultProperties()));
    }

    public static Supplier<Item> axe(final IItemTier tier) {
        return item(p -> new AxeItem(tier, 5.0f, -2.0f, getDefaultProperties()));
    }

    public static Supplier<Item> sword(final IItemTier tier) {
        return item(p -> new SwordItem(tier, 3, -3.0f, getDefaultProperties()));
    }



    public static Supplier<Item> fireArmor(EquipmentSlotType slotType) {
        return item(p -> new FireArmorItem(slotType, getDefaultProperties().maxStackSize(1).maxDamage(300)));
    }

    public static Supplier<Item> scaleArmor(EquipmentSlotType slotType) {
        return item(p -> new ScaleArmorItem(slotType, getDefaultProperties().maxStackSize(1)));
    }

    public static Supplier<ScubaGogglesItem> scubaGoggles(ScubaType type) {
        return item(p -> new ScubaGogglesItem(type, p));
    }

    public static Supplier<ScubaHarnessItem> scubaHarness(ScubaType type) {
        return item(p -> new ScubaHarnessItem(type, p));
    }

    public static Supplier<ScubaFlippersItem> scubaFlippers(ScubaType type) {
        return item(p -> new ScubaFlippersItem(type, p));
    }
    */



}
