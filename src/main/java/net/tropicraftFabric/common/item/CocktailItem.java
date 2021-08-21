package net.tropicraftFabric.common.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.tropicraftFabric.common.drinks.*;
import net.tropicraftFabric.common.registry.TropicraftItems;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//import net.minecraft.recipe.Ingredient;

public class CocktailItem extends Item implements IColoredItem {//implements IColoredItem {//ItemColorProvider

	private static final int DEFAULT_COLOR = 0xf3be36;
	
	private final Drink drink;

	// nbt layout:
	// - byte DrinkID: 0 if no known drink, else the Drink.drinkList index
	// - int Color: alpha blended mix of colors based on ingredients
	// - NBTTagList Ingredients
	//   - byte IngredientID: Ingredient.ingredientList index
	//   - short Count: count of this ingredient in the mixture, typically 1

	public CocktailItem(final Drink drink, final Item.Settings Settings) {
		super(Settings);
		this.drink = drink;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext flag) {
		Drink drink = getDrink(stack);

		if (drink == Drink.COCKTAIL && stack.hasTag() && stack.getTag().contains("Ingredients")) {
    		final NbtList ingredients = stack.getTag().getList("Ingredients", 10);
    
    		for (int i = 0; i < ingredients.size(); ++i) {
				NbtCompound ingredient = ingredients.getCompound(i);
    			int id = ingredient.getByte("IngredientID");
				Text ingredientName = Ingredient.ingredientsList[id].getName();
    			int ingredientColor = Ingredient.ingredientsList[id].getColor();
    			//String lvl = StatCollector.translateToLocal("enchantment.level." + count);
    			//par3List.add(ingredientName + " " + lvl);
    			tooltip.add(ingredientName);
    		}
		}
	}

	public static int getCocktailColor(ItemStack stack) {
		final NbtCompound tag = stack.getTag();
		if (tag != null && !tag.isEmpty()) {
			if (tag.contains("Color")) {
				return tag.getInt("Color");
			}
		}
		return DEFAULT_COLOR;
	}

	public static @NotNull ItemStack makeCocktail(MixerRecipe recipe) {
		final ItemStack stack = MixerRecipes.getItemStack(recipe.getCraftingResult());
		NbtCompound tag = new NbtCompound();
		Drink drink = recipe.getCraftingResult();
		tag.putByte("DrinkID", (byte) drink.drinkId);
		NbtList tagList = new NbtList();

		Ingredient primary = null;
		List<Ingredient> additives = new LinkedList<>();

		for (Ingredient ingredient: recipe.getIngredients()) {
			NbtCompound ingredientNbt = new NbtCompound();
			ingredientNbt.putByte("IngredientID", (byte)ingredient.id);
			tagList.add(ingredientNbt);

			if (ingredient.isPrimary()) {
				primary = ingredient;
			} else {
				additives.add(ingredient);
			}
		}

		tag.put("Ingredients", tagList);

		int color = primary == null ? DEFAULT_COLOR : primary.getColor();

		for (Ingredient additive: additives) {
			color = ColorMixer.getInstance().alphaBlendRGBA(color, additive.getColor(), additive.getAlpha());
		}

		tag.putInt("Color", color);

		stack.setTag(tag);
		return stack;
	}



	public static ItemStack makeCocktail(final List<ItemStack> itemStacks) {
		//TODO: fixme this is so ugly ugh
		final ItemStack stack = new ItemStack(TropicraftItems.COCKTAILS.get(Drink.COCKTAIL));
		NbtCompound tag = new NbtCompound();
		tag.putByte("DrinkID", (byte) Drink.COCKTAIL.drinkId);
		NbtList tagList = new NbtList();

		List<Ingredient> ingredients = new ArrayList<>();
		for (ItemStack ingredientStack : itemStacks) {
			ingredients.addAll(Ingredient.listIngredients(ingredientStack));
		}
		Collections.sort(ingredients);

		Ingredient primary = null;
		List<Ingredient> additives = new LinkedList<>();

		for (Ingredient ingredient : ingredients) {
			NbtCompound ingredientTag = new NbtCompound();
			ingredientTag.putByte("IngredientID", (byte)ingredient.id);
			tagList.add(ingredientTag);

			if (ingredient.isPrimary()) {
				primary = ingredient;
			} else {
				additives.add(ingredient);
			}
		}

		tag.put("Ingredients", tagList);

		int color = primary == null ? DEFAULT_COLOR : primary.getColor();

		for (Ingredient additive: additives) {
			color = ColorMixer.getInstance().alphaBlendRGBA(color, additive.getColor(), additive.getAlpha());
		}

		tag.putInt("Color", color);

		stack.setTag(tag);
		return stack;

	}

	public static Ingredient[] getIngredients(ItemStack stack) {
		if (!Drink.isDrink(stack.getItem()) || !stack.hasTag()) {
			return new Ingredient[0];
		}

		NbtCompound nbt = stack.getTag();
		NbtList tagList = nbt.getList("Ingredients", 10);
		Ingredient[] ingredients = new Ingredient[tagList.size()];

		for (int i = 0; i < tagList.size(); ++i) {
			final int ingredientID = (tagList.getCompound(i)).getByte("IngredientID");
			ingredients[i] = Ingredient.ingredientsList[ingredientID];
		}

		return ingredients;
	}

	public static Drink getDrink(ItemStack stack) {
		if (!Drink.isDrink(stack.getItem())) {
			return null;
		}
		return ((CocktailItem)stack.getItem()).drink;
	}

	@Override
	public int getMaxUseTime(ItemStack par1ItemStack) {
		return 32;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}

	public ItemStack onFoodEaten(ItemStack itemstack, World world, PlayerEntity player) {
		world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);

		for (Ingredient ingredient: getIngredients(itemstack)) {
			ingredient.onDrink(player);
		}

		Drink drink = getDrink(itemstack);

		if (drink != null) {
			drink.onDrink(player);
		}

		return new ItemStack(TropicraftItems.BAMBOO_MUG); //?? Unknow if this is right
	}

	/**
	 * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
	 * the Item before the action is complete.
	 */
	@Override
	public ItemStack finishUsing(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		if (entityLiving instanceof PlayerEntity) {
			final PlayerEntity player = (PlayerEntity) entityLiving;
			onFoodEaten(stack, worldIn, player);

			Drink drink = getDrink(stack);

			if (worldIn.hasRain(player.getBlockPos()) && drink == Drink.PINA_COLADA) {
				// TODO advancements player.addStat(AchievementRegistry.drinkPinaColada);
			}
		}

		return new ItemStack(TropicraftItems.BAMBOO_MUG);
	}

	@Override
	public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand hand) {
	    ItemStack stack = playerIn.getStackInHand(hand);
		Drink drink = getDrink(stack);

		if (drink == null) {
			return new TypedActionResult<>(ActionResult.FAIL, stack);
		}

		playerIn.setCurrentHand(hand);

		return new TypedActionResult<>(ActionResult.SUCCESS, stack);
	}

	/*
	@Override
	public int getColor(ItemStack stack) {
		return this.drink.color;
	}
	 */

	@Override
	@Environment(EnvType.CLIENT)
	public int getColor(ItemStack itemstack, int tintIndex) {
		Drink drink = getDrink(itemstack);
		return (tintIndex == 0 || drink == null ? 16777215 : drink.color);
	}

	/*
	@Override
	public void setColor(ItemStack itemstack, int tintIndex) {
		Drink drink = getDrink(itemstack);
		//int color = (tintIndex == 0 || drink == null ? 16777215 : drink.color);
		return (tintIndex == 0 || drink == null ? 16777215 : drink.color);
		//itemstack.getOrCreateSubTag("display").putInt("color", color);

		//DyeableItem.setColor(itemstack, color);
	}

	 */
	
	@Override
	public Text getName(ItemStack stack) {
		Drink drink = getDrink(stack);
		if (drink != null) {
			return super.getName(stack).shallowCopy().formatted(drink.textFormatting).formatted(Formatting.BOLD);//applyTextStyle
			//return super.getName(stack);
		}
		return super.getName(stack).shallowCopy();
	}

	public Drink getDrink() {
		return drink;
	}
}
