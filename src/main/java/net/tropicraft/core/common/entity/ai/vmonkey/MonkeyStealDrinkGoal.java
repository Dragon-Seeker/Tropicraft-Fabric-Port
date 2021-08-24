package net.tropicraft.core.common.entity.ai.vmonkey;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.tropicraft.core.common.drinks.Drink;
import net.tropicraft.core.common.drinks.MixerRecipes;
import net.tropicraft.core.common.entity.neutral.VMonkeyEntity;
import net.tropicraft.core.common.item.CocktailItem;

import java.util.EnumSet;

public class MonkeyStealDrinkGoal extends Goal {
    private VMonkeyEntity entity;

    public MonkeyStealDrinkGoal(VMonkeyEntity monkey) {
        this.entity = monkey;
        setControls(EnumSet.of(Control.LOOK, Control.MOVE));
    }

    @Override
    public boolean shouldContinue() {
        return entity.getOwner() == null && VMonkeyEntity.FOLLOW_PREDICATE.test(entity.getFollowing()) && !entity.selfHoldingDrink(Drink.PINA_COLADA);
    }

    @Override
    public boolean canStart() {
        return entity.getOwner() == null && VMonkeyEntity.FOLLOW_PREDICATE.test(entity.getFollowing()) && !entity.selfHoldingDrink(Drink.PINA_COLADA) && entity.isAttacking();
    }

    private void leapTowardTarget() {
        LivingEntity leapTarget = entity.getTarget();

        if (leapTarget == null) return;

        double d0 = leapTarget.getX() - entity.getX();
        double d1 = leapTarget.getZ() - entity.getZ();
        float f = (float) Math.sqrt(d0 * d0 + d1 * d1);
        final Vec3d motion = entity.getVelocity();

        if ((double)f >= 1.0E-4D) {
            entity.setVelocity(motion.add(d0 / (double)f * 0.5D * 0.800000011920929D + motion.x * 0.20000000298023224D, 0, d1 / (double)f * 0.5D * 0.800000011920929D + motion.z * 0.20000000298023224D));
        }

        entity.setVelocity(new Vec3d(motion.x, 0.25, motion.z));
    }

    @Override
    public void tick() {
        if (entity.squaredDistanceTo(entity.getFollowing()) < 4.0F) {
            for (final Hand hand : Hand.values()) {
                if (CocktailItem.getDrink(entity.getFollowing().getStackInHand(hand)) == Drink.PINA_COLADA) {
                    leapTowardTarget();
                    entity.getFollowing().setStackInHand(hand, ItemStack.EMPTY);
                    entity.setStackInHand(hand, MixerRecipes.getItemStack(Drink.PINA_COLADA));
                }
            }
        }
    }
}
