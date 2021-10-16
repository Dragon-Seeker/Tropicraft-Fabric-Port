package net.tropicraft.core.common.item;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class TropicItemTier implements Tier {
    private final int harvestLevel;
    private final int maxUses;
    private final float damageVsEntity;
    private final int enchantability;
    private final float speedMultiplier;
    private final LazyLoadedValue<Ingredient> ingredientLoader;

    public TropicItemTier(int harvestLevel, int maxUses, float damageVsEntity, int enchantability, float speedMultiplier, ItemLike repairMaterial){
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.speedMultiplier = speedMultiplier;
        this.damageVsEntity = damageVsEntity;
        this.enchantability = enchantability;
        this.ingredientLoader = new LazyLoadedValue<>(() -> Ingredient.of(repairMaterial));
    }

    @Override
    public int getUses() {
        return this.maxUses;
    }

    @Override
    public float getSpeed() {
        return this.speedMultiplier;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damageVsEntity;
    }

    @Override
    public int getLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.ingredientLoader.get();
    }

}
