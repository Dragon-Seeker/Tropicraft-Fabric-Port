package net.tropicraftFabric.common.entity.ai;

import net.tropicraftFabric.common.entity.passive.EntityKoaBase;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class EntityAIEatToHeal extends Goal
{
    private final EntityKoaBase entityObj;

    private int walkingTimeoutMax = 20*10;

    private int walkingTimeout;
    private int repathPentalty = 0;

    private int lookUpdateTimer = 0;
    private int randXPos = 0;
    private int randYPos = 0;
    private int randZPos = 0;

    private float missingHealthToHeal = 5;

    public EntityAIEatToHeal(EntityKoaBase entityObjIn)
    {
        this.entityObj = entityObjIn;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean canStart()
    {
        if (entityObj.getHealth() < entityObj.getMaxHealth() - missingHealthToHeal) {
            return hasFoodSource();
        } else {
            return false;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinue()
    {
        return canStart();
    }

    @Override
    public void tick() {
        super.tick();

        if (hasFoodSource(entityObj.inventory)) {
            consumeOneStackSizeOfFood(entityObj.inventory);
            entityObj.heal(5);
            entityObj.world.playSound(null, entityObj.getBlockPos(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.NEUTRAL, 1F, 1F);
            return;
        }

        if (hasFoodAtHome()) {
            boolean isClose = false;
            BlockPos blockposGoal = this.entityObj.getPositionTarget();

            if (blockposGoal == null) {
                stop();
                return;
            }

            //prevent walking into the fire
            double dist = entityObj.getPos().distanceTo(new Vec3d(blockposGoal.getX(), blockposGoal.getY(), blockposGoal.getZ()));
            if (dist < 5D) {
                consumeOneStackSizeOfFoodAtHome();
                entityObj.heal(5);
                entityObj.world.playSound(null, entityObj.getBlockPos(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.NEUTRAL, 1F, 1F);
                return;
            }

            if (!isClose) {
                if ((this.entityObj.getNavigation().isIdle() || walkingTimeout <= 0) && repathPentalty <= 0) {

                    int i = blockposGoal.getX();
                    int j = blockposGoal.getY();
                    int k = blockposGoal.getZ();

                    boolean success = false;

                    if (this.entityObj.squaredDistanceTo(Vec3d.ofCenter(blockposGoal)) > 256.0D) {
                        Vec3d Vector3d = TargetFinder.method_27929(this.entityObj, 14, 3, new Vec3d((double) i + 0.5D, (double) j, (double) k + 0.5D));

                        if (Vector3d != null) {
                            success = this.entityObj.getNavigation().startMovingTo(Vector3d.x, Vector3d.y, Vector3d.z, 1.0D);
                        }
                    } else {
                        success = this.entityObj.getNavigation().startMovingTo((double) i + 0.5D, (double) j, (double) k + 0.5D, 1.0D);
                    }

                    if (!success) {
                        repathPentalty = 40;
                    } else {
                        walkingTimeout = walkingTimeoutMax;
                    }
                } else {
                    if (walkingTimeout > 0) {
                        walkingTimeout--;
                    } else {

                    }
                }
            }

            if (repathPentalty > 0) {
                repathPentalty--;
            }

            if (lookUpdateTimer > 0) {
                lookUpdateTimer--;
            }
        }


    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start()
    {
        super.start();
        //this.insidePosX = -1;
        //reset any previous path so tick can start with a fresh path
        this.entityObj.getNavigation().stop();
    }

    /**
     * Resets the task
     */
    @Override
    public void stop()
    {
        super.stop();
        entityObj.setSitting(false);
        walkingTimeout = 0;
        /*this.insidePosX = this.doorInfo.getInsideBlockPos().getX();
        this.insidePosZ = this.doorInfo.getInsideBlockPos().getZ();
        this.doorInfo = null;*/
    }

    public boolean isTooClose() {
        BlockPos blockposGoal = null;
        if (this.entityObj.posLastFireplaceFound != null) {
            //path to base of fire
            blockposGoal = this.entityObj.posLastFireplaceFound.add(0, -1, 0);
        } else {
            blockposGoal = this.entityObj.getPositionTarget();
        }

        if (blockposGoal == null) {
            return false;
        }

        //prevent walking into the fire
        double dist = entityObj.getPos().distanceTo(new Vec3d(blockposGoal.getX(), blockposGoal.getY(), blockposGoal.getZ()));
        if (dist <= 3D) {
            return true;
        }
        return false;
    }

    public boolean hasFoodSource() {

        if (hasFoodSource(entityObj.inventory)) return true;

        return hasFoodAtHome();
    }

    public boolean hasFoodAtHome() {
        BlockPos blockposGoal = this.entityObj.getPositionTarget();
        if (blockposGoal != null) {
            BlockEntity tile = entityObj.world.getBlockEntity(blockposGoal);
            if (tile instanceof ChestBlockEntity) {
                ChestBlockEntity chest = (ChestBlockEntity) tile;

                if (hasFoodSource(chest)) return true;
            }
        }
        return false;
    }

    public boolean hasFoodSource(Inventory inv) {
        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getStack(i);
            if (!stack.isEmpty() && stack.getItem().isFood()) {
                return true;
            }
        }
        return false;
    }

    public ItemStack consumeOneStackSizeOfFoodAtHome() {
        BlockPos blockposGoal = this.entityObj.getPositionTarget();
        if (blockposGoal != null) {
            BlockEntity tile = entityObj.world.getBlockEntity(blockposGoal);
            if (tile instanceof ChestBlockEntity) {
                ChestBlockEntity chest = (ChestBlockEntity) tile;

                return consumeOneStackSizeOfFood(chest);
            }
        }
        return ItemStack.EMPTY;
    }

    /**
     * Return a snapshot of what its consuming incase we want to scale healing based on item/amount
     *
     * @param inv
     * @return
     */
    public ItemStack consumeOneStackSizeOfFood(Inventory inv) {
        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getStack(i);
            if (!stack.isEmpty()) {
                if (stack.getItem().isFood()) {
                    stack.decrement(1);
                    if (stack.getCount() <= 0) {
                        inv.setStack(i, ItemStack.EMPTY);
                    }

                    //returning the state of the single ate item, though this return value doesnt seem to be used anywhere atm
                    ItemStack newStack = stack.copy();
                    newStack.setCount(1);
                    return newStack;
                }
            }
        }
        return ItemStack.EMPTY;
    }
}


