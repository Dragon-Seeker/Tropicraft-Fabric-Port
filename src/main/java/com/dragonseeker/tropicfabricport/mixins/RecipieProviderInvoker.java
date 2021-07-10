package com.dragonseeker.tropicfabricport.mixins;

import net.minecraft.data.server.RecipesProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.ItemConvertible;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Consumer;

@Mixin(RecipesProvider.class)
public interface RecipieProviderInvoker {
    @Invoker("conditionsFromItem")
    public static net.minecraft.advancement.criterion.InventoryChangedCriterion.Conditions invokerConditionsFromItem(ItemConvertible itemConvertible) {
        throw new AssertionError();
    }
    /*
    @Invoker("generate")
    public void generateTest(Consumer<RecipeJsonProvider> consumer);

     */
}
