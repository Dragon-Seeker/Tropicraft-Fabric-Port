package com.dragonseeker.tropicfabricport.common.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;


public class TropicalMusicDiscItem extends MusicDiscItem {
    
    private final RecordMusic type;

    public TropicalMusicDiscItem(RecordMusic type, Settings builder) {
        super(13, type.getSound(), builder);
        this.type = type;
    }

    @Override
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        super.appendTooltip(stack, worldIn, tooltip, flagIn);
        tooltip.add(getDescLine(1).shallowCopy().formatted(Formatting.GRAY));
        //tooltip.add(getDescLine(1).applyTextStyle(TextFormatting.GRAY));
    }
    
    private Text getDescLine(int i) {
        return new TranslatableText(this.getTranslationKey() + ".desc." + i);
    }
    
    @Override
    public MutableText getDescription() {
        return (MutableText) getDescLine(0);
    }
    
    public RecordMusic getType() {
        return type;
    }
}
