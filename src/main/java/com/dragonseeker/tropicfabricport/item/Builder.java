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
    
}
