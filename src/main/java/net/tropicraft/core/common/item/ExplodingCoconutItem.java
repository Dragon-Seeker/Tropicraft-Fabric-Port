package net.tropicraft.core.common.item;

import net.tropicraft.core.common.entity.projectile.ExplodingCoconutEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ExplodingCoconutItem extends Item {

    public ExplodingCoconutItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        // TODO config option
        final boolean canPlayerThrow = player.isCreative() || player.canUseGameMasterBlocks();
        //allow to use anywhere but in the main area of the server
        final boolean ltOverride = !world.dimensionType().toString().equals("tropicraft:tropics");
        //final boolean ltOverride = !world.getDimension().getType().getRegistryName().toString().equals("tropicraft:tropics");

        ItemStack itemstack = player.getItemInHand(hand);
        if (!canPlayerThrow && !ltOverride) {
            if (!world.isClientSide) {
                player.displayClientMessage(new TranslatableComponent("tropicraft.coconutBombWarning"), true);
                //player.sendMessage(new TranslationTextComponent("tropicraft.coconutBombWarning"));
            }
            return InteractionResultHolder.pass(itemstack);
        }
        
        if (!player.isCreative()) {
            itemstack.shrink(1);
        }


        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (world.random.nextFloat() * 0.4F + 0.8F));
        if (!world.isClientSide) {
            ExplodingCoconutEntity snowballentity = new ExplodingCoconutEntity(world, player);
            snowballentity.setItem(itemstack);
            snowballentity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            world.addFreshEntity(snowballentity);
        }

        player.awardStat(Stats.ITEM_USED.get(this));//Stats.USED.get(this)
        return InteractionResultHolder.pass(itemstack);
    }
}
