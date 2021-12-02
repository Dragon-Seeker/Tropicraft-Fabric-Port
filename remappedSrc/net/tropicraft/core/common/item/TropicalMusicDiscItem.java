package net.tropicraft.core.common.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;


public class TropicalMusicDiscItem extends RecordItem {
    
    private final RecordMusic type;

    public TropicalMusicDiscItem(RecordMusic type, Properties builder) {
        super(13, type.getSound(), builder);
        this.type = type;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(getDescLine(1).copy().withStyle(ChatFormatting.GRAY));
        //tooltip.add(getDescLine(1).applyTextStyle(TextFormatting.GRAY));
    }
    
    private Component getDescLine(int i) {
        return new TranslatableComponent(this.getDescriptionId() + ".desc." + i);
    }
    
    @Override
    public MutableComponent getDisplayName() {
        return (MutableComponent) getDescLine(0);
    }
    
    public RecordMusic getType() {
        return type;
    }
}
