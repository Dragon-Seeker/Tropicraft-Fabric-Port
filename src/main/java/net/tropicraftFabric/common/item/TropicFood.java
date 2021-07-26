package net.tropicraftFabric.common.item;

import net.minecraft.item.FoodComponent;

public class TropicFood {
    public static final FoodComponent LEMON = new FoodComponent.Builder().hunger(2).saturationModifier(0.2f).snack().build();
    public static final FoodComponent LIME = new FoodComponent.Builder().hunger(2).saturationModifier(0.2f).snack().build();
    public static final FoodComponent GRAPEFRUIT = new FoodComponent.Builder().hunger(2).saturationModifier(0.2f).snack().build();
    public static final FoodComponent ORANGE = new FoodComponent.Builder().hunger(2).saturationModifier(0.2f).snack().build();
    public static final FoodComponent PINEAPPLE_CUBES = new FoodComponent.Builder().hunger(1).saturationModifier(0.1f).snack().build();
    public static final FoodComponent COCONUT_CHUNK = new FoodComponent.Builder().hunger(1).saturationModifier(0.1f).snack().build();

    public static final FoodComponent COOKED_RAY = new FoodComponent.Builder().hunger(5).saturationModifier(0.5f).build();
    public static final FoodComponent FRESH_MARLIN = new FoodComponent.Builder().hunger(2).saturationModifier(0.3f).build();
    public static final FoodComponent SEARED_MARLIN = new FoodComponent.Builder().hunger(5).saturationModifier(0.65f).build();
    public static final FoodComponent COOKED_FROG_LEG = new FoodComponent.Builder().hunger(2).saturationModifier(0.15f).build();
    public static final FoodComponent SEA_URCHIN_ROE = new FoodComponent.Builder().hunger(3).saturationModifier(0.3f).build();
    public static final FoodComponent TOASTED_NORI = new FoodComponent.Builder().hunger(2).saturationModifier(0.2f).build();
    public static final FoodComponent RAW_FISH = new FoodComponent.Builder().hunger(2).saturationModifier(0.2f).build();
    public static final FoodComponent RAW_RAY = new FoodComponent.Builder().hunger(1).saturationModifier(0.2f).build();
    public static final FoodComponent RAW_FROG_LEG = new FoodComponent.Builder().hunger(1).saturationModifier(0.2f).build();
    public static final FoodComponent COOKED_FISH = new FoodComponent.Builder().hunger(4).saturationModifier(0.4f).build();

}
