package net.tropicraft.core.common.block.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.ItemScatterer;
//import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.tropicraft.core.common.block.DrinkMixerBlock;
import net.tropicraft.core.common.drinks.Drink;
import net.tropicraft.core.common.drinks.Drinks;
import net.tropicraft.core.common.drinks.Ingredient;
import net.tropicraft.core.common.drinks.MixerRecipes;
import net.tropicraft.core.common.item.CocktailItem;
//import net.tropicraft.core.common.network.TropicraftPackets;
//import net.tropicraft.core.common.network.message.MessageMixerInventory;
//import net.tropicraft.core.common.network.message.MessageMixerStart;
import net.tropicraft.core.common.registry.TropicBlockEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrinkMixerTileEntity extends BlockEntity implements IMachineTile {
    /** Number of ticks to mix */
    private static final int TICKS_TO_MIX = 4*20;
    private static final int MAX_NUM_INGREDIENTS = 3;

    /** Number of ticks the mixer has been mixin' */
    private int ticks;
    public DefaultedList<ItemStack> ingredients;
    private boolean mixing;
    public ItemStack result = ItemStack.EMPTY;

    public DrinkMixerTileEntity(BlockPos pos, BlockState state) {
        super(TropicBlockEntities.DRINK_MIXER, pos, state);
        mixing = false;
        ingredients = DefaultedList.ofSize(MAX_NUM_INGREDIENTS, ItemStack.EMPTY);
    }

    @Override
    public void readNbt(@NotNull NbtCompound nbt) {
        super.readNbt(nbt);
        ticks = nbt.getInt("MixTicks");
        mixing = nbt.getBoolean("Mixing");

        for (int i = 0; i < MAX_NUM_INGREDIENTS; i++) {
            if (nbt.contains("Ingredient" + i)) {
                ingredients.set(i, ItemStack.fromNbt(nbt.getCompound("Ingredient" + i)));
            }
        }

        if (nbt.contains("Result")) {
            result = ItemStack.fromNbt(nbt.getCompound("Result"));
        }
    }

    @Override
    public @NotNull NbtCompound writeNbt(@NotNull NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("MixTicks", ticks);
        nbt.putBoolean("Mixing", mixing);

        for (int i = 0; i < MAX_NUM_INGREDIENTS; i++) {
            NbtCompound ingredientNbt = new NbtCompound();
            ingredients.get(i).writeNbt(ingredientNbt);
            nbt.put("Ingredient" + i, ingredientNbt);
        }

        NbtCompound resultNbt = new NbtCompound();
        result.writeNbt(resultNbt);
        nbt.put("Result", resultNbt);

        return nbt;
    }

    public void tick() {
        if (ticks < TICKS_TO_MIX && mixing) {
            ticks++;
            if (ticks == TICKS_TO_MIX) {
                finishMixing();
            }
        }
    }

    public boolean isDoneMixing() {
        return !result.isEmpty();
    }

    public DefaultedList<ItemStack> getIngredients() {
        return this.ingredients;
    }
    
    public static List<Ingredient> listIngredients(@NotNull ItemStack stack) {
        List<Ingredient> is = new ArrayList<>();

        if (Drink.isDrink(stack.getItem())) {
            Collections.addAll(is, CocktailItem.getIngredients(stack));
        } else {
            final Ingredient i = Ingredient.findMatchingIngredient(stack);
            if (i != null) {
                is.add(i);
            }
        }

        return is;
    }

    public void startMixing() {
        this.ticks = 0;
        this.mixing = true;
        if (!world.isClient) {
            //TropicraftPackets.sendToDimension(new MessageMixerStart(this), world.getRegistryKey());
        }
    }
    
    private void dropItem(@NotNull ItemStack stack, @Nullable PlayerEntity at) {
        if (at == null) {
            BlockPos pos = getPos().offset(getCachedState().get(DrinkMixerBlock.FACING));
            ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), stack);
        } else {
            ItemScatterer.spawn(world, at.getX(), at.getY(), at.getZ(), stack);
        }
    }

    public void emptyMixer(@Nullable PlayerEntity at) {
        for (int i = 0; i < MAX_NUM_INGREDIENTS; i++) {
            if (!ingredients.get(i).isEmpty()) {
                dropItem(ingredients.get(i), at);
                ingredients.set(i, ItemStack.EMPTY);
            }
        }

        ticks = TICKS_TO_MIX;
        mixing = false;
        syncInventory();
    }

    public void retrieveResult(@Nullable PlayerEntity at) {
        if (result.isEmpty()) {
            return;
        }
        
        dropItem(result, at);

        for (int i = 0; i < MAX_NUM_INGREDIENTS; i++) {
            // If we're not using one of the ingredient slots, just move along
            if (ingredients.get(i).isEmpty()) {
                continue;
            }

            final ItemStack container = getContainerItem(ingredients.get(i).getItem(), ingredients.get(i));

            if (!container.isEmpty()) {
                dropItem(container, at);
            }
        }

        ingredients.clear();
        result = ItemStack.EMPTY;
        syncInventory();
    }

    private ItemStack getContainerItem(Item item, ItemStack itemStack) {
        if (item.hasRecipeRemainder()) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(item.getRecipeRemainder());
    }

    public void finishMixing() {
        result = getResult(getIngredients());
        mixing = false;
        ticks = 0;
        syncInventory();
    }

    public boolean addToMixer(@NotNull ItemStack ingredient) {
        if (ingredients.get(0).isEmpty()) {
            if (!Drink.isDrink(ingredient.getItem())) {
                Ingredient i = Ingredient.findMatchingIngredient(ingredient);
                // Ordinarily we check for primary here, but I don't think that feature
                // is as relevant anymore. Will leave it here just in case!
                if (i == null/* || !i.isPrimary()*/) {
                    return false;
                }
            }
            ingredients.set(0, ingredient);
            syncInventory();
            return true;
        } else if (ingredients.get(1).isEmpty()) {
            if (Drink.isDrink(ingredient.getItem())) {
                // prevent mixing multiple primary ingredients
                // all cocktails already contain one
                return false;
            }

            Ingredient ing0 = Ingredient.findMatchingIngredient(ingredients.get(0));
            Ingredient i = Ingredient.findMatchingIngredient(ingredient);

            // See above comment about isPrimary()
            if (i == null/* || i.isPrimary()*/ || ing0.id == i.id) {
                return false;
            }

            ingredients.set(1, ingredient);
            syncInventory();
            return true;
        } else if (ingredients.get(2).isEmpty()) {
            if (Drink.isDrink(ingredient.getItem())) {
                // prevent mixing multiple primary ingredients
                // all cocktails already contain one
                return false;
            }

            Ingredient ing0 = Ingredient.findMatchingIngredient(ingredients.get(0));
            Ingredient ing1 = Ingredient.findMatchingIngredient(ingredients.get(1));
            Ingredient i = Ingredient.findMatchingIngredient(ingredient);

            // See above comment about isPrimary()
            if (i == null/* || i.isPrimary()*/ || ing0.id == i.id || ing1.id == i.id) {
                return false;
            }

            ingredients.set(2, ingredient);
            syncInventory();
            return true;
        } else {
            return false;
        }
    }

    public boolean isMixing() {
        return mixing;
    }

    private boolean isMixerFull() {
        return MixerRecipes.isValidRecipe(ingredients);
        //return ingredients[0] != null && ingredients[1] != null;
    }

    public boolean canMix() {
        return !mixing && isMixerFull();
    }
    
    /* == IMachineTile == */
    
    @Override
    public boolean isActive() {
        return isMixing();
    }
    
    @Override
    public float getProgress(float partialTicks) {
        return (ticks + partialTicks) / TICKS_TO_MIX;
    }
    
    @Override
    public Direction getDirection(BlockState state) {
        return state.get(DrinkMixerBlock.FACING);
    }

    /**
     * Called when you receive a TileEntityData packet for the location this
     * TileEntity is currently in. On the client, the NetworkManager will always
     * be the remote server. On the server, it will be whomever is responsible for
     * sending the packet.
     *
     * @param //net The NetworkManager the packet originated from
     * @param //pkt The data packet
     */

    //@Override
    //public void onDataPacket(ClientConnection net, BlockEntityUpdateS2CPacket pkt) {
    //    readNbt(pkt.getNbt());
    //}


    protected void syncInventory() {
        if (!world.isClient) {
            //TropicraftPackets.sendToDimension(new MessageMixerInventory(this), world.getRegistryKey());
        }
    }


    @Nullable
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(this.pos, 1, this.toInitialChunkDataNbt());
    }

    public NbtCompound toInitialChunkDataNbt() {
        return writeItems(new NbtCompound());
    }

    private NbtCompound writeItems(final NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, ingredients, true);
        Inventories.writeNbt(nbt, DefaultedList.copyOf(result), true);
        return nbt;
    }

    public ItemStack getResult(DefaultedList<ItemStack> ingredients2) {
        return Drinks.getResult(ingredients2);
    }
}
