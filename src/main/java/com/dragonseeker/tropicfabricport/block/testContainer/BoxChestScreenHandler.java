package com.dragonseeker.tropicfabricport.block.testContainer;

import com.dragonseeker.tropicfabricport.registry.TropicScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class BoxChestScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private static final int invSize = 27;
    private final int rows;

    //This constructor gets called on the client when the server wants it to open the screenHandler,
    //The client will call the other constructor with an empty Inventory and the screenHandler will automatically
    //sync this empty inventory with the inventory on the server.
    public BoxChestScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(invSize));
    }

    //This constructor gets called from the BlockEntity on the server without calling the other constructor first, the server knows the inventory of the container
    //and can therefore directly provide it as an argument. This inventory will then be synced to the client.
    public BoxChestScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(TropicScreenHandler.BOX_CHEST_SCREEN_HANDLER, syncId);
        checkSize(inventory, invSize);
        this.inventory = inventory;
        //some inventories do custom logic when a player opens it.
        inventory.onOpen(playerInventory.player);
        this.rows = 3;

        int rowadjus = (rows - 4) * 18;

        int i;
        int j;

        // Chest Inventory
        for (i = 0; i < rows; i++) {
            for (j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, i * 9 + j, 8 + j * 18, 18 + i * 18));
            }
        }


        // Player Inventory (27 storage + 9 hotbar)
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, i * 9 + j + 9, 8 + j * 18,  103 + i * 18 + rowadjus));
            }
        }


        for (j = 0; j < 9; j++) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 161 + rowadjus));
        }

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    // Shift + Player Inv Slot
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.inventory.onClose(player);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    @Environment(EnvType.CLIENT)
    public int getRows() {
        return this.rows;
    }
}
