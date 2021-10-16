package net.tropicraft.core.common.drinks;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;


public class DrinkActionPotion extends DrinkAction {
    private final StatusEffect potion;
    private final int duration;
    private final int amplifier;

    public DrinkActionPotion(StatusEffect potion, int duration, int amplifier) {
        this.potion = potion;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    @Override
    public void onDrink(PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(potion, duration * 20, amplifier));
    }
}
