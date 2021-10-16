package net.tropicraft.core.common.item;

import net.minecraft.item.FoodComponent;
import net.tropicraft.Tropicraft;
import net.tropicraft.core.common.drinks.Drink;
import net.tropicraft.core.common.drinks.MixerRecipes;
import net.tropicraft.core.common.entity.placeable.BeachFloatEntity;
import net.tropicraft.core.common.entity.placeable.ChairEntity;
import net.tropicraft.core.common.entity.placeable.FurnitureEntity;
import net.tropicraft.core.common.entity.placeable.UmbrellaEntity;
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Rarity;


public class Builder {

    public static FabricItemSettings getDefaultProperties() {
        return new FabricItemSettings().group(Tropicraft.ITEM_GROUP_ITEMS);
    }

    public static CocktailItem cocktail(final Drink drink) {
        CocktailItem ret = new CocktailItem(drink, getDefaultProperties().maxDamage(0).maxCount(1));
        MixerRecipes.setDrinkItem(drink, ret);
        return ret;
    }

    public static Item foodItem(final FoodComponent food) {
        return new Item(getDefaultProperties().food(food));
    }

    public static AshenMaskItem mask(final AshenMasks mask) {
        return new AshenMaskItem(ArmorMaterials.ASHEN_MASK, mask, getDefaultProperties());
    }


    public static TropicalMusicDiscItem musicDisc(RecordMusic type) {
        TropicalMusicDiscItem musicDisc = new TropicalMusicDiscItem(type, getDefaultProperties().rarity(Rarity.RARE).maxCount(1));
        return musicDisc;
    }

    public static <T extends Entity> TropicraftSpawnEgg spawnEgg(EntityType<T> type){
        return new TropicraftSpawnEgg(type, getDefaultProperties());
    }

    public static FurnitureItem<UmbrellaEntity> umbrella(final DyeColor color) {
        return furniture(TropicraftEntities.UMBRELLA, color);
    }

    public static FurnitureItem<ChairEntity> chair(final DyeColor color) {
        return furniture(TropicraftEntities.CHAIR, color);
    }

    public static FurnitureItem<BeachFloatEntity> beachFloat(final DyeColor color) {
        return furniture(TropicraftEntities.BEACH_FLOAT, color);
    }

    private static <T extends FurnitureEntity> FurnitureItem<T> furniture(EntityType<T> type, DyeColor color) {
        return new FurnitureItem(getDefaultProperties().maxCount(1), type, color);
    }

    public static <T extends FishEntity> Item fishBucket(final EntityType<T> type) {
        return new TropicraftFishBucketItem(type, Fluids.WATER, getDefaultProperties().maxCount(1));
    }

    public static ShellItem shell() {
        return new ShellItem(getDefaultProperties());
    }



}
