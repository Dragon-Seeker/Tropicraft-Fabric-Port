package net.tropicraftFabric.common.entity.ai.vmonkey;

import net.tropicraftFabric.common.drinks.Drink;
import net.tropicraftFabric.common.entity.neutral.VMonkeyEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class MonkeyFollowNearestPinaColadaHolderGoal extends Goal {
    private final VMonkeyEntity monkey;
    private final float areaSize;

    private final double speedModifier;
    private final EntityNavigation navigation;
    private int timeToRecalcPath;
    private final float stopDistance;
    private float oldWaterCost;

    public MonkeyFollowNearestPinaColadaHolderGoal(final VMonkeyEntity entity, double speedModifier, float stopDistance, float areaSize) {
        this.monkey = entity;
        this.speedModifier = speedModifier;
        this.stopDistance = stopDistance;
        this.areaSize = areaSize;

        navigation = monkey.getNavigation();
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean canStart() {
        if (monkey.isSitting()) return false;
        if (monkey.isTamed()) return false;
        if (monkey.selfHoldingDrink(Drink.PINA_COLADA)) return false;

        List<PlayerEntity> list = monkey.world.getEntitiesByClass(PlayerEntity.class, monkey.getBoundingBox().expand(areaSize), VMonkeyEntity.FOLLOW_PREDICATE);

        if (!list.isEmpty()) {
            for (PlayerEntity entityliving : list) {
                if (!entityliving.isInvisible()) {
                    monkey.setFollowing(entityliving);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinue() {
        if (monkey.getFollowing() == null) {
            return false;
        }
        return VMonkeyEntity.FOLLOW_PREDICATE.test(monkey.getFollowing()) && canStart()
                && !navigation.isIdle() && monkey.squaredDistanceTo(monkey.getFollowing()) > (double)(stopDistance * stopDistance);
    }

    @Override
    public void start() {
        timeToRecalcPath = 0;
        oldWaterCost = monkey.getPathfindingPenalty(PathNodeType.WATER);
        monkey.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    @Override
    public void stop() {
        monkey.setFollowing(null);
        navigation.stop();
        monkey.setPathfindingPenalty(PathNodeType.WATER, oldWaterCost);
    }

    public void tick() {
        LivingEntity following = monkey.getFollowing();
        if (following != null && !monkey.isLeashed()) {
            monkey.getLookControl().lookAt(following, 10.0F, (float) monkey.getLookPitchSpeed());
            if (--timeToRecalcPath <= 0) {
                timeToRecalcPath = 10;
                double xDist = monkey.getX() - following.getX();
                double yDist = monkey.getY() - following.getY();
                double zDist = monkey.getZ() - following.getZ();
                double sqrDist = xDist * xDist + yDist * yDist + zDist * zDist;
                if (sqrDist > (double)(stopDistance * stopDistance)) {
                    navigation.startMovingTo(following, speedModifier);
                } else {
                    navigation.stop();
                    if (sqrDist <= (double)stopDistance) {
                        double xDist2 = following.getX() - monkey.getX();
                        double zDist2 = following.getZ() - monkey.getZ();
                        navigation.startMovingTo(monkey.getX() - xDist2, monkey.getY(), monkey.getZ() - zDist2, speedModifier);
                    }

                }
            }
        }
    }
}