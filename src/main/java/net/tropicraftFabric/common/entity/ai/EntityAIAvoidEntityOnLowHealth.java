package net.tropicraftFabric.common.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class EntityAIAvoidEntityOnLowHealth<T extends Entity> extends Goal {
    private final Predicate<Entity> canBeSeenSelector;
    /** The entity we are attached to */
    protected PathAwareEntity theEntity;
    private final double farSpeed;
    private final double nearSpeed;
    protected T closestLivingEntity;
    private final float avoidDistance;
    /** The PathEntity of our entity */
    private Path entityPathEntity;
    /** The PathNavigate of our entity */
    private final EntityNavigation entityPathNavigate;
    /** Class of entity this behavior seeks to avoid */
    private final Class<T> classToAvoid;
    private final Predicate<Entity> avoidTargetSelector;
    private float healthToAvoid = 0F;

    public EntityAIAvoidEntityOnLowHealth(PathAwareEntity theEntityIn, Class<T> classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn, float healthToAvoid)
    {
        this(theEntityIn, classToAvoidIn, (entity) -> true, avoidDistanceIn, farSpeedIn, nearSpeedIn, healthToAvoid);
    }

    public EntityAIAvoidEntityOnLowHealth(PathAwareEntity theEntityIn, Class<T> classToAvoidIn, Predicate<Entity> avoidTargetSelectorIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn, float healthToAvoid)
    {
        this.canBeSeenSelector = entity -> entity.isAlive() && theEntity.getVisibilityCache().canSee(entity);
        this.theEntity = theEntityIn;
        this.classToAvoid = classToAvoidIn;
        this.avoidTargetSelector = avoidTargetSelectorIn;
        this.avoidDistance = avoidDistanceIn;
        this.farSpeed = farSpeedIn;
        this.nearSpeed = nearSpeedIn;
        this.entityPathNavigate = theEntityIn.getNavigation();
        this.healthToAvoid = healthToAvoid;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean canStart()
    {

        if (this.theEntity.getHealth() > healthToAvoid) return false;

        List<T> list = this.theEntity.world.getEntitiesByClass(this.classToAvoid,
                this.theEntity.getBoundingBox().stretch((double)this.avoidDistance, 3.0D, (double)this.avoidDistance),
                EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.and(this.canBeSeenSelector).and(this.avoidTargetSelector)
        );

        if (list.isEmpty()) {
            return false;
        } else {
            this.closestLivingEntity = list.get(0);
            Vec3d Vector3d = TargetFinder.findTargetAwayFrom(this.theEntity, 16, 7, new Vec3d(this.closestLivingEntity.getX(), this.closestLivingEntity.getY(), this.closestLivingEntity.getZ()));

            if (Vector3d == null) {
                return false;
            } else if (this.closestLivingEntity.squaredDistanceTo(Vector3d.x, Vector3d.y, Vector3d.z) < this.closestLivingEntity.squaredDistanceTo(this.theEntity)) {
                return false;
            } else {
                this.entityPathEntity = this.entityPathNavigate.findPathTo(Vector3d.x, Vector3d.y, Vector3d.z, 0);
                return this.entityPathEntity != null;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinue()
    {
        return this.entityPathNavigate.isFollowingPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start()
    {
        this.entityPathNavigate.startMovingAlong(this.entityPathEntity, this.farSpeed);
    }

    /**
     * Resets the task
     */
    @Override
    public void stop()
    {
        this.closestLivingEntity = null;
    }

    /**
     * Updates the task
     */
    @Override
    public void tick()
    {
        if (this.theEntity.squaredDistanceTo(this.closestLivingEntity) < 49.0D)
        {
            this.theEntity.getNavigation().setSpeed(this.nearSpeed);
        }
        else
        {
            this.theEntity.getNavigation().setSpeed(this.farSpeed);
        }
    }
}


