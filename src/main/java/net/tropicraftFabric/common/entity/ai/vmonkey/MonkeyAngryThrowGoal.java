package net.tropicraftFabric.common.entity.ai.vmonkey;

import net.tropicraftFabric.common.entity.neutral.VMonkeyEntity;
import net.tropicraftFabric.common.registry.TropicraftItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;
import java.util.List;

public class MonkeyAngryThrowGoal extends Goal {
  private final VMonkeyEntity entity;
  private final float speedModifier;
  private final float stopDistance;
  private final EntityNavigation navigation;
  private float oldWaterCost;
  private int timeToRecalcPath;
  private int madMeter;
  private ItemEntity trackedMug;
  private LivingEntity trackedPlayer;

  public MonkeyAngryThrowGoal(VMonkeyEntity monkeyEntity) {
    this.entity = monkeyEntity;
    setControls(EnumSet.of(Control.LOOK, Control.MOVE));
    this.speedModifier = 1.2F;
    this.stopDistance = 1.0F;
    this.navigation = monkeyEntity.getNavigation();
  }

  @Override
  public void stop() {
    navigation.stop();
    this.madMeter = 0;
    entity.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterCost);
    this.trackedMug = null;
    this.trackedPlayer = null;
  }

  @Override
  public void start() {
    this.timeToRecalcPath = 0;
    this.madMeter = 100;
    this.oldWaterCost = entity.getPathfindingPenalty(PathNodeType.WATER);
    entity.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    this.trackedMug = null;
    this.trackedPlayer = null;
  }

  @Override
  public boolean canStart() {
    return !entity.isTamed() && !entity.isLeashed() && this.entity.isMadAboutStolenAlcohol();
  }

  @Override
  public boolean shouldContinue() {
    return !entity.isTamed() && !entity.isLeashed() && this.entity.isMadAboutStolenAlcohol();
  }

  @Override
  public void tick() {
    if (this.trackedMug != null && this.entity.getMainHandStack().getItem() == TropicraftItems.BAMBOO_MUG) {
      this.trackedPlayer = nearbyPlayer();

      if (this.trackedPlayer != null) {
        this.entity.getLookControl().lookAt(this.trackedPlayer, 10.0F, (float) entity.getLookPitchSpeed());

        if (entity.squaredDistanceTo(this.trackedPlayer) < 4) {
          leapTowardTarget(this.trackedPlayer);
          entity.dropStack(this.entity.getMainHandStack());
          entity.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
          entity.setMadAboutStolenAlcohol(false);
        } else {
          moveTowardsEntity(this.trackedPlayer);
        }
      }
      return;
    }

    if (this.trackedMug != null && this.trackedMug.isAlive()) {
      this.entity.getLookControl().lookAt(this.trackedMug, 10.0F, (float) entity.getLookPitchSpeed());

      if (entity.squaredDistanceTo(this.trackedMug) > (double) (stopDistance * stopDistance)) {
        moveTowardsEntity(this.trackedMug);
      } else {
        entity.setStackInHand(Hand.MAIN_HAND, this.trackedMug.getStack());
        this.trackedMug.remove();
      }
      return;
    }

    if (--this.madMeter <= 0) {
      this.entity.setMadAboutStolenAlcohol(false);
      return;
    }
    this.trackedMug = nearbyMug();
  }

  private LivingEntity nearbyPlayer() {
    List<PlayerEntity> list = entity.world.getNonSpectatingEntities(PlayerEntity.class, entity.getBoundingBox().expand(20.0D));

    if (!list.isEmpty()) {
      for (PlayerEntity entityliving : list) {
        if (!entityliving.isInvisible()) {
          return entityliving;
        }
      }
    }

    return null;
  }

  private void moveTowardsEntity(Entity itemEntity) {
    if (--timeToRecalcPath <= 0) {
      timeToRecalcPath = 10;
      double d0 = entity.getX() - itemEntity.getX();
      double d1 = entity.getY() - itemEntity.getY();
      double d2 = entity.getZ() - itemEntity.getZ();
      double d3 = d0 * d0 + d1 * d1 + d2 * d2;

      if (d3 > (double)(stopDistance * stopDistance)) {
        navigation.startMovingTo(itemEntity, speedModifier);
      } else {
        navigation.stop();

        if (d3 <= (double)stopDistance) {
          double d4 = itemEntity.getX() - entity.getX();
          double d5 = itemEntity.getZ() - entity.getZ();
          navigation.startMovingTo(entity.getX() - d4, entity.getY(), entity.getZ() - d5, speedModifier);
        }
      }
    }
  }

  private void leapTowardTarget(LivingEntity leapTarget) {
    if (leapTarget == null) return;

    double d0 = leapTarget.getX() - entity.getX();
    double d1 = leapTarget.getZ() - entity.getZ();
    float f = MathHelper.sqrt(d0 * d0 + d1 * d1);
    final Vec3d motion = entity.getVelocity();

    if ((double)f >= 1.0E-4D) {
      entity.setVelocity(motion.add(d0 / (double)f * 0.5D * 0.800000011920929D + motion.x * 0.20000000298023224D, 0, d1 / (double)f * 0.5D * 0.800000011920929D + motion.z * 0.20000000298023224D));
    }

    entity.setVelocity(new Vec3d(motion.x, 0.25, motion.z));
  }

  private ItemEntity nearbyMug() {
    List<ItemEntity> list = entity.world.getNonSpectatingEntities(ItemEntity.class, entity.getBoundingBox().expand(10.0D));

    if (!list.isEmpty()) {
      for (ItemEntity item : list) {
        if (!item.isInvisible()) {
          if (item.getStack().isItemEqualIgnoreDamage(new ItemStack(TropicraftItems.BAMBOO_MUG)) && item.isAlive()) {
            return item;
          }
        }
      }
    }
    return null;
  }
}
