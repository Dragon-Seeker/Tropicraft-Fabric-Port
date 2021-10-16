package net.tropicraft.core.common.item;

import net.tropicraft.Tropicraft;
import net.tropicraft.core.common.drinks.Drink;
import net.tropicraft.core.common.drinks.MixerRecipes;
import net.tropicraft.core.common.entity.placeable.BeachFloatEntity;
import net.tropicraft.core.common.entity.placeable.ChairEntity;
import net.tropicraft.core.common.entity.placeable.FurnitureEntity;
import net.tropicraft.core.common.entity.placeable.UmbrellaEntity;
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.material.Fluids;


public class Builder {

    public static Item.Properties getDefaultProperties() {
        return new FabricItemSettings().tab(Tropicraft.ITEM_GROUP_ITEMS);
    }

    public static CocktailItem cocktail(final Drink drink) {
        CocktailItem ret = new CocktailItem(drink, getDefaultProperties().durability(0).stacksTo(1));
        MixerRecipes.setDrinkItem(drink, ret);
        return ret;
    }

    public static Item foodItem(final FoodProperties food) {
        return new Item(getDefaultProperties().food(food));
    }

    public static AshenMaskItem mask(final AshenMasks mask) {
        return new AshenMaskItem(ArmorMaterials.ASHEN_MASK, mask, getDefaultProperties());
    }


    public static TropicalMusicDiscItem musicDisc(RecordMusic type) {
        TropicalMusicDiscItem musicDisc = new TropicalMusicDiscItem(type, getDefaultProperties().rarity(Rarity.RARE).stacksTo(1));
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
        return new FurnitureItem(getDefaultProperties().stacksTo(1), type, color);
    }

    public static <T extends AbstractFish> Item fishBucket(final EntityType<T> type) {
        return new TropicraftFishBucketItem(type, Fluids.WATER, getDefaultProperties().stacksTo(1));
    }

    public static ShellItem shell() {
        return new ShellItem(getDefaultProperties());
    }



}
