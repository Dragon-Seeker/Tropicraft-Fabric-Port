package net.tropicraftFabric.common.entity.ai;

import net.tropicraftFabric.common.entity.passive.EntityKoaBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;
import java.util.List;

public class EntityAIPlayKoa extends Goal
{
    private final EntityKoaBase villagerObj;
    private LivingEntity targetVillager;
    private final double speed;
    private int playTime;

    public EntityAIPlayKoa(EntityKoaBase villagerObjIn, double speedIn)
    {
        this.villagerObj = villagerObjIn;
        this.speed = speedIn;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean canStart()
    {
        if (this.villagerObj.getBreedingAge() >= 0)
        {
            return false;
        }
        else if (this.villagerObj.getRandom().nextInt(200) != 0)
        {
            return false;
        }
        else
        {
            List<EntityKoaBase> list = this.villagerObj.world.getNonSpectatingEntities(EntityKoaBase.class, this.villagerObj.getBoundingBox().expand(6.0D, 3.0D, 6.0D));
            double d0 = Double.MAX_VALUE;

            for (EntityKoaBase entityvillager : list)
            {
                if (entityvillager != this.villagerObj && !entityvillager.isPlaying() && entityvillager.getBreedingAge() < 0)
                {
                    double d1 = entityvillager.squaredDistanceTo(this.villagerObj);

                    if (d1 <= d0)
                    {
                        d0 = d1;
                        this.targetVillager = entityvillager;
                    }
                }
            }

            if (this.targetVillager == null)
            {
                Vec3d vec = TargetFinder.findTarget(this.villagerObj, 16, 3);
                return vec != null;
            }

            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinue()
    {
        return this.playTime > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start()
    {
        if (this.targetVillager != null)
        {
            this.villagerObj.setPlaying(true);
        }

        this.playTime = 1000;
    }

    /**
     * Resets the task
     */
    @Override
    public void stop()
    {
        this.villagerObj.setPlaying(false);
        this.targetVillager = null;
    }

    /**
     * Updates the task
     */
    @Override
    public void tick()
    {
        --this.playTime;

        if (villagerObj.isOnGround() && villagerObj.world.random.nextInt(30) == 0) {
            this.villagerObj.getJumpControl().setActive();
        }

        if (this.targetVillager != null)
        {
            if (this.villagerObj.squaredDistanceTo(this.targetVillager) > 4.0D)
            {
                this.villagerObj.getNavigation().startMovingTo(this.targetVillager, this.speed);
            }
        }
        else if (this.villagerObj.getNavigation().isIdle())
        {
            Vec3d vec = TargetFinder.findTarget(this.villagerObj, 16, 3);
            if (vec == null)
            {
                return;
            }

            this.villagerObj.getNavigation().startMovingTo(vec.x, vec.y, vec.z, this.speed);
        }
    }
}


