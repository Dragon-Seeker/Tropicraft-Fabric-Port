package net.tropicraft.core.common.entity.ai.vmonkey;

import net.tropicraft.core.common.drinks.Drink;
import net.tropicraft.core.common.entity.neutral.VMonkeyEntity;
import net.tropicraft.core.common.registry.TropicraftItems;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.EnumSet;

public class MonkeySitAndDrinkGoal extends Goal {
    private static final int DEFAULT_WAIT = 40;

    private VMonkeyEntity entity;
    private int waitCounter;

    public MonkeySitAndDrinkGoal(VMonkeyEntity monkey) {
        this.entity = monkey;
        waitCounter = DEFAULT_WAIT;
        setControls(EnumSet.of(Control.LOOK, Control.MOVE));
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    @Override
    public void stop() {
        entity.setInSittingPose(false);
        entity.dropStack(new ItemStack(TropicraftItems.BAMBOO_MUG));
        entity.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
        waitCounter = DEFAULT_WAIT;
    }

    @Override
    public boolean shouldContinue() {
        return entity.selfHoldingDrink(Drink.PINA_COLADA);
    }

    @Override
    public boolean canStart() {
        return entity.selfHoldingDrink(Drink.PINA_COLADA);
    }

    @Override
    public void start() {
        entity.setInSittingPose(true);
        entity.setAttacking(false);
        entity.setTarget(null);
        entity.setFollowing(null);
    }

    @Override
    public void tick() {
        waitCounter--;

        if (waitCounter <= 0) {
            entity.setCurrentHand(Hand.MAIN_HAND);
        }

        // If drinking complete
        ItemStack heldStack = entity.getMainHandStack();
        if (heldStack.getItem() == TropicraftItems.BAMBOO_MUG) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 10 * 20, 2));
        }
    }
}
