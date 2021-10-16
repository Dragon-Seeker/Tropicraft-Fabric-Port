package net.tropicraft.core.common.item;

import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.tropicraft.Constants;
import org.apache.commons.lang3.ArrayUtils;

import java.awt.*;
import java.util.Map;
import java.util.Random;

public class LoveTropicsShellItem extends ShellItem implements IColoredItem {

    public static class LTUtil {
        private static final Map<String, Integer> colors = Maps.newHashMap();
        private static final Random rand = new Random();

        static {
            for (final String name : ArrayUtils.addAll(Constants.LT17_NAMES, Constants.LT18_NAMES)) {
                rand.setSeed(name.hashCode());
                colors.put(name, Color.HSBtoRGB(rand.nextFloat(), (rand.nextFloat() * 0.2f) + 0.7f, 1));
            }
        }
    }

    public LoveTropicsShellItem(final Settings properties) {
        super(properties);
    }

    @Override
    public int getColor(ItemStack itemstack, int pass) {
        final NbtCompound tag = itemstack.getNbt();
        if (tag != null && !tag.isEmpty() && tag.contains("Name")) {
            return pass == 0 ? 0xFFFFFFFF : LTUtil.colors.get(tag.getString("Name"));
        }
        return pass == 0 ? 0xFFFFFFFF : LTUtil.colors.get(Constants.LT17_NAMES[0]);
    }

    @Override
    public Text getName(final ItemStack stack) {
        if (!stack.hasNbt() || !stack.getNbt().contains("Name")) {
            return super.getName(stack);
        }
        final String name = stack.getNbt().getString("Name");
        final String type = name.endsWith("s") ? "with_s" : "normal";
        return new TranslatableText("item.tropicraft.shell.owned." + type, name);
    }
}
