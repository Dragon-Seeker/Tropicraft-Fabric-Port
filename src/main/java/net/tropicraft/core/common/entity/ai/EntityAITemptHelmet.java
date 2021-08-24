package net.tropicraft.core.common.entity.ai;

import net.tropicraft.core.common.entity.passive.EntityKoaBase;
import com.google.common.collect.Sets;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;
import java.util.Set;

public class EntityAITemptHelmet extends Goal
{
    /** The entity using this AI that is tempted by the player. */
    private final PathAwareEntity temptedEntity;
    private final double speed;
    /** X position of player tempting this mob */
    private double targetX;
    /** Y position of player tempting this mob */
    private double targetY;
    /** Z position of player tempting this mob */
    private double targetZ;
    /** Tempting player's pitch */
    private double pitch;
    /** Tempting player's yaw */
    private double yaw;
    /** The player that is tempting the entity that is using this AI. */
    private PlayerEntity temptingPlayer;
    /**
     * A counter that is decremented each time the shouldExecute method is called. The shouldExecute method will always
     * return false if delayTemptCounter is greater than 0.
     */
    private int delayTemptCounter;
    /** True if this EntityAITempt task is running */
    private boolean isRunning;
    private final Set<Item> temptItem;
    /** Whether the entity using this AI will be scared by the tempter's sudden movement. */
    private final boolean scaredByPlayerMovement;

    public EntityAITemptHelmet(PathAwareEntity temptedEntityIn, double speedIn, Item temptItemIn, boolean scaredByPlayerMovementIn) {
        this(temptedEntityIn, speedIn, scaredByPlayerMovementIn, Sets.newHashSet(temptItemIn));
    }

    public EntityAITemptHelmet(PathAwareEntity temptedEntityIn, double speedIn, boolean scaredByPlayerMovementIn, Set<Item> temptItemIn) {
        this.temptedEntity = temptedEntityIn;
        this.speed = speedIn;
        this.temptItem = temptItemIn;
        this.scaredByPlayerMovement = scaredByPlayerMovementIn;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));

        if (!(temptedEntityIn.getNavigation() instanceof MobNavigation))
        {
            throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
        }
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canStart()
    {

        if (temptedEntity instanceof EntityKoaBase && ((EntityKoaBase) temptedEntity).druggedTime <= 0) {
            return false;
        }

        if (this.delayTemptCounter > 0)
        {
            --this.delayTemptCounter;
            return false;
        }
        else
        {
            this.temptingPlayer = this.temptedEntity.world.getClosestPlayer(this.temptedEntity, 10.0D);

            if (this.temptingPlayer == null)
            {
                return false;
            }
            else
            {
                return isTempting(this.temptingPlayer.getInventory().armor.get(3));
                //return this.isTempting(this.temptingPlayer.getHeldItemMainhand()) || this.isTempting(this.temptingPlayer.getHeldItemOffhand());
            }
        }
    }

    protected boolean isTempting(ItemStack stack) {
        for (Item items : temptItem) {
            if (items.getDefaultStack().isInFrame() && items == stack.getItem()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinue() {
        if (this.scaredByPlayerMovement) {
            if (this.temptedEntity.squaredDistanceTo(this.temptingPlayer) < 36.0D) {
                if (this.temptingPlayer.squaredDistanceTo(this.targetX, this.targetY, this.targetZ) > 0.010000000000000002D) {
                    return false;
                }

                if (Math.abs((double)this.temptingPlayer.getPitch() - this.pitch) > 5.0D || Math.abs((double)this.temptingPlayer.getYaw() - this.yaw) > 5.0D) {
                    return false;
                }
            } else {
                this.targetX = this.temptingPlayer.getX();
                this.targetY = this.temptingPlayer.getY();
                this.targetZ = this.temptingPlayer.getZ();
            }

            pitch = temptingPlayer.getPitch();
            yaw = temptingPlayer.getYaw();
        }

        return this.canStart();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        this.targetX = this.temptingPlayer.getX();
        this.targetY = this.temptingPlayer.getY();
        this.targetZ = this.temptingPlayer.getZ();
        this.isRunning = true;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        this.temptingPlayer = null;
        this.temptedEntity.getNavigation().stop();
        this.delayTemptCounter = 100;
        this.isRunning = false;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        this.temptedEntity.getLookControl().lookAt(this.temptingPlayer, (float)(this.temptedEntity.getBodyYawSpeed() + 20), (float)this.temptedEntity.getLookPitchSpeed());

        if (this.temptedEntity.squaredDistanceTo(this.temptingPlayer) < 6.25D) {
            this.temptedEntity.getNavigation().stop();
        } else {
            this.temptedEntity.getNavigation().startMovingTo(this.temptingPlayer, this.speed);
        }
    }

    /**
     * @see #isRunning
     */
    public boolean isRunning()
    {
        return this.isRunning;
    }
}


