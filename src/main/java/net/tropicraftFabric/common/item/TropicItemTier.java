package net.tropicraftFabric.common.item;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Lazy;

public class TropicItemTier implements ToolMaterial {
    private final int harvestLevel;
    private final int maxUses;
    private final float damageVsEntity;
    private final int enchantability;
    private final float speedMultiplier;
    private final Lazy<Ingredient> ingredientLoader;

    public TropicItemTier(int harvestLevel, int maxUses, float damageVsEntity, int enchantability, float speedMultiplier, ItemConvertible repairMaterial){
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.speedMultiplier = speedMultiplier;
        this.damageVsEntity = damageVsEntity;
        this.enchantability = enchantability;
        this.ingredientLoader = new Lazy<>(() -> Ingredient.ofItems(repairMaterial));
    }

    @Override
    public int getDurability() {
        return this.maxUses;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.speedMultiplier;
    }

    @Override
    public float getAttackDamage() {
        return this.damageVsEntity;
    }

    @Override
    public int getMiningLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.ingredientLoader.get();
    }

}
