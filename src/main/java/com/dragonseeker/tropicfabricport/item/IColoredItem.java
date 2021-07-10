package com.dragonseeker.tropicfabricport.item;

import net.minecraft.item.ItemStack;

public interface IColoredItem {

    int getColor(ItemStack stack, int pass);

}