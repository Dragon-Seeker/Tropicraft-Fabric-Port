package net.tropicraft.core.mixins;

import net.minecraft.data.server.RecipesProvider;
import net.minecraft.item.ItemConvertible;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

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
