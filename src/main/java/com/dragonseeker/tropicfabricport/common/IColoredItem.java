package com.dragonseeker.tropicfabricport.common;

import net.minecraft.item.ItemStack;

public interface IColoredItem {
    int getColor(ItemStack stack, int tintIndex);
}
