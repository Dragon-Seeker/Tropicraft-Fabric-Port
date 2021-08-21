package net.tropicraft.core.common.entity.ai;

import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;
import java.util.EnumSet;

public class EntityAIWanderNotLazy extends Goal {

    private final PathAwareEntity entity;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private final double speed;
    private int executionChance;
    private boolean mustUpdate;

    public EntityAIWanderNotLazy(PathAwareEntity creatureIn, double speedIn)
    {
        this(creatureIn, speedIn, 120);
    }

    public EntityAIWanderNotLazy(PathAwareEntity creatureIn, double speedIn, int chance)
    {
        this.entity = creatureIn;
        this.speed = speedIn;
        this.executionChance = chance;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean canStart()
    {
        if (!this.mustUpdate)
        {
            /*if (this.entity.getAge() >= 100)
            {
                return false;
            }*/

            if (this.entity.getRandom().nextInt(this.executionChance) != 0)
            {
                return false;
            }
        }

        Vec3d vec = TargetFinder.findTarget(this.entity, 10, 7);
        if (vec == null)
        {
            return false;
        }
        else
        {
            this.xPosition = vec.x;
            this.yPosition = vec.y;
            this.zPosition = vec.z;
            this.mustUpdate = false;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinue()
    {
        return !this.entity.getNavigation().isIdle();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start()
    {
        this.entity.getNavigation().startMovingTo(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }

    /**
     * Makes task to bypass chance
     */
    public void makeUpdate()
    {
        this.mustUpdate = true;
    }

    /**
     * Changes task random possibility for execution
     */
    public void setExecutionChance(int newchance)
    {
        this.executionChance = newchance;
    }
}



