package net.tropicraft.core.common.item;

import net.tropicraft.core.common.entity.projectile.ExplodingCoconutEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ExplodingCoconutItem extends Item {

    public ExplodingCoconutItem(FabricItemSettings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        // TODO config option
        final boolean canPlayerThrow = player.isCreative() || player.isCreativeLevelTwoOp();
        //allow to use anywhere but in the main area of the server
        final boolean ltOverride = !world.getDimension().toString().equals("tropicraft:tropics");
        //final boolean ltOverride = !world.getDimension().getType().getRegistryName().toString().equals("tropicraft:tropics");

        ItemStack itemstack = player.getStackInHand(hand);
        if (!canPlayerThrow && !ltOverride) {
            if (!world.isClient) {
                player.sendMessage(new TranslatableText("tropicraft.coconutBombWarning"), true);
                //player.sendMessage(new TranslationTextComponent("tropicraft.coconutBombWarning"));
            }
            return TypedActionResult.pass(itemstack);
        }
        
        if (!player.isCreative()) {
            itemstack.decrement(1);
        }

        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (RANDOM.nextFloat() * 0.4F + 0.8F));
        if (!world.isClient) {
            ExplodingCoconutEntity snowballentity = new ExplodingCoconutEntity(world, player);
            snowballentity.setItem(itemstack);
            snowballentity.setProperties(player, player.pitch, player.yaw, 0.0F, 1.5F, 1.0F);
            world.spawnEntity(snowballentity);
        }

        player.incrementStat(Stats.USED.getOrCreateStat(this));//Stats.USED.get(this)
        return TypedActionResult.pass(itemstack);
    }
}
