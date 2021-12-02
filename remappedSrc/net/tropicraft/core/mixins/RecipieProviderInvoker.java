package net.tropicraft.core.mixins;

import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RecipeProvider.class)
public interface RecipieProviderInvoker {
    @Invoker("conditionsFromItem")
    public static net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance invokerConditionsFromItem(ItemLike itemConvertible) {
        throw new AssertionError();
    }
    /*
    @Invoker("generate")
    public void generateTest(Consumer<RecipeJsonProvider> consumer);

     */
}
