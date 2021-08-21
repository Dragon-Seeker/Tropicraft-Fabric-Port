package net.tropicraft.core.common.entity.ai.vmonkey;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.tropicraft.core.common.drinks.Drink;
import net.tropicraft.core.common.drinks.MixerRecipes;
import net.tropicraft.core.common.entity.neutral.VMonkeyEntity;

import java.util.EnumSet;
import java.util.List;

public class MonkeyPickUpPinaColadaGoal extends Goal {

    private VMonkeyEntity entity;
    private ItemEntity drinkEntity;
    private final double speedModifier;
    private final EntityNavigation navigation;
    private int timeToRecalcPath;
    private final float stopDistance;
    private float oldWaterCost;
    
    public MonkeyPickUpPinaColadaGoal(VMonkeyEntity monkey) {
        entity = monkey;
        setControls(EnumSet.of(Control.LOOK, Control.MOVE));
        speedModifier = 1.0F;
        stopDistance = 1.0F;
        navigation = entity.getNavigation();
        drinkEntity = null;
    }
    
    @Override
    public boolean shouldContinue() {
        return !entity.isTamed() && !entity.selfHoldingDrink(Drink.PINA_COLADA) && drinkEntity != null;
    }

    @Override
    public boolean canStart() {
        // Add some variablity / throttling
        if (entity.getRandom().nextInt(20) != 0) {
            return false;
        }
        return !entity.isTamed() && !entity.selfHoldingDrink(Drink.PINA_COLADA) && hasNearbyDrink(Drink.PINA_COLADA) && drinkEntity != null;
    }

    @Override
    public void stop() {
        navigation.stop();
        entity.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterCost);
    }

    @Override
    public void start() {
        timeToRecalcPath = 0;
        oldWaterCost = entity.getPathfindingPenalty(PathNodeType.WATER);
        entity.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    private boolean hasNearbyDrink(final Drink drink) {
        ItemStack stack = MixerRecipes.getItemStack(drink);

        List<ItemEntity> list = entity.world.getNonSpectatingEntities(ItemEntity.class, entity.getBoundingBox().expand(10.0D));
        
        if (!list.isEmpty()) {
            for (ItemEntity item : list) {
                if (!item.isInvisible()) {
                    if (item.getStack().isItemEqualIgnoreDamage(stack) && item.isAlive()) {
                        drinkEntity = item;
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void tick() {
        if (drinkEntity != null && !entity.isLeashed()) {
            entity.getLookControl().lookAt(drinkEntity, 10.0F, (float) entity.getLookPitchSpeed());

            if (!drinkEntity.isAlive()) {
                drinkEntity = null;
                entity.setMadAboutStolenAlcohol(true);
                return;
            }

            if (entity.squaredDistanceTo(drinkEntity) > (double)(stopDistance * stopDistance)) {
                if (--timeToRecalcPath <= 0) {
                    timeToRecalcPath = 10;
                    double d0 = entity.getX() - drinkEntity.getX();
                    double d1 = entity.getY() - drinkEntity.getY();
                    double d2 = entity.getZ() - drinkEntity.getZ();
                    double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                    if (d3 > (double)(stopDistance * stopDistance)) {
                        navigation.startMovingTo(drinkEntity, speedModifier);
                    } else {
                        navigation.stop();

                        if (d3 <= (double)stopDistance) {
                            double d4 = drinkEntity.getX() - entity.getX();
                            double d5 = drinkEntity.getZ() - entity.getZ();
                            navigation.startMovingTo(entity.getX() - d4, entity.getY(), entity.getZ() - d5, speedModifier);
                        }
                    }
                }
            } else {
                entity.setStackInHand(Hand.MAIN_HAND, drinkEntity.getStack());
                drinkEntity.remove();
            }
        }
    }

}
