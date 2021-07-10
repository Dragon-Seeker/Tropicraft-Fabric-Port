package com.dragonseeker.tropicfabricport.item;

import com.dragonseeker.tropicfabricport.sound.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
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
