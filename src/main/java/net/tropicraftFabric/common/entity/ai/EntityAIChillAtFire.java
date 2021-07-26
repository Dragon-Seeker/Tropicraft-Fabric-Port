package net.tropicraftFabric.common.entity.ai;

import net.tropicraftFabric.common.Util;
import net.tropicraftFabric.common.entity.passive.EntityKoaBase;
import net.tropicraftFabric.common.registry.TropicraftItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;

import java.util.EnumSet;

public class EntityAIChillAtFire extends Goal
{
    private final EntityKoaBase entityObj;

    private int walkingTimeoutMax = 20*10;

    private int walkingTimeout;
    private int repathPentalty = 0;

    private int lookUpdateTimer = 0;
    private int randXPos = 0;
    private int randYPos = 0;
    private int randZPos = 0;

    public EntityAIChillAtFire(EntityKoaBase entityObjIn)
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

        if ((entityObj.getWantsToParty() || this.entityObj.druggedTime > 0) && entityObj.listPosDrums.size() > 0) {
            return false;
        }

        BlockPos blockpos = this.entityObj.getBlockPos();

        if (!this.entityObj.world.isDay() || this.entityObj.world.isRaining() && this.entityObj.world.getBiome(blockpos).getPrecipitation() != Biome.Precipitation.RAIN) {
            if (!isTooClose()) {
                return entityObj.world.random.nextInt(20) == 0;
            } else {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinue()
    {

        if ((entityObj.getWantsToParty() || this.entityObj.druggedTime > 0) && entityObj.listPosDrums.size() > 0) {
            return false;
        }

        BlockPos blockpos = this.entityObj.getBlockPos();
        //return !this.entityObj.getNavigation().noPath();
        if (!this.entityObj.world.isDay() || this.entityObj.world.isRaining() && this.entityObj.world.getBiome(blockpos).getPrecipitation() != Biome.Precipitation.RAIN)
        {
            return !isTooClose();

        } else {
            return entityObj.world.random.nextInt(60) != 0;
        }
    }

    @Override
    public void tick() {
        super.tick();

        boolean isClose = false;

        BlockPos blockposGoal = null;
        if (this.entityObj.posLastFireplaceFound != null) {
            //path to base of fire
            blockposGoal = this.entityObj.posLastFireplaceFound.add(0, -1, 0);
        } else {
            blockposGoal = this.entityObj.getPositionTarget();
        }

        if (blockposGoal == null) {
            stop();
            return;
        }

        //prevent walking into the fire
        double dist = entityObj.getPos().distanceTo(new Vec3d(blockposGoal.getX(), blockposGoal.getY(), blockposGoal.getZ()));
        if (dist < 4D && entityObj.isOnGround()) {
            entityObj.setSitting(true);
            entityObj.getNavigation().stop();
            isClose = true;
            if (lookUpdateTimer <= 0) {
                lookUpdateTimer = 200 + entityObj.world.random.nextInt(100);
                int range = 2;
                randXPos = entityObj.world.random.nextInt(range) - entityObj.world.random.nextInt(range);
                //stargaze
                if (entityObj.world.random.nextInt(3) == 0) {
                    randYPos = 5+entityObj.world.random.nextInt(5);
                } else {
                    randYPos = 0;
                }
                randZPos = entityObj.world.random.nextInt(range) - entityObj.world.random.nextInt(range);

                if (entityObj.getEntityId() % 3 == 0) {
                    entityObj.equipStack(EquipmentSlot.MAINHAND, new ItemStack(TropicraftItems.BAMBOO_MUG));
                } else if (entityObj.getEntityId() % 5 == 0) {
                    entityObj.equipStack(EquipmentSlot.MAINHAND, new ItemStack(TropicraftItems.COOKED_FROG_LEG));
                } else {
                    entityObj.equipStack(EquipmentSlot.MAINHAND, new ItemStack(TropicraftItems.ORANGE));
                }

                entityObj.heal(1);

            }
            this.entityObj.getLookControl().lookAt(blockposGoal.getX() + randXPos, blockposGoal.getY() + randYPos + 1D, blockposGoal.getZ() + randZPos,
                    8F, 8F);
        } else {
            entityObj.setSitting(false);
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
                    } else {
                        success = Util.tryMoveToXYZLongDist(this.entityObj, new BlockPos(i, j, k), 1);
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
        return dist <= 3D;
    }
}


