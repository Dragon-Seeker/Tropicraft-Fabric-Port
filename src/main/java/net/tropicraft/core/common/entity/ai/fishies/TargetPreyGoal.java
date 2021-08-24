package net.tropicraft.core.common.entity.ai.fishies;

import net.tropicraft.core.common.entity.underdasea.TropicraftFishEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.Box;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class TargetPreyGoal extends Goal {
    public TropicraftFishEntity entity;
    public Random rand;

    public TargetPreyGoal(EnumSet<Control> flags, TropicraftFishEntity entityObjIn) {
        entity = entityObjIn;
        rand = entity.getRandom();
        setControls(flags);
    }

    @Override
    public boolean canStart() {
        return entity.isTouchingWater() && entity.canAggress && entity.eatenFishAmount < entity.maximumEatAmount;
    }

    @Override
    public void tick() {
        super.tick();
        
        // Target selection
        Box entityBB = entity.getBoundingBox();
        if (entity.age % 80 == 0 && entity.aggressTarget == null || entity.getEntityWorld().getEntityById(entity.aggressTarget.getId()) == null) {
                List<Entity> list = entity.world.getOtherEntities(entity, entityBB.expand(20D, 20D, 20D).offset(0.0D, -8.0D, 0.0D), e -> e.isAlive());
                if(list.size() > 0) {
                    Entity ent = list.get(rand.nextInt(list.size()));
                    boolean skip = false;
                    if(ent.equals(entity)) skip = true;    
                    if(ent.getClass().getName().equals(entity.getClass().getName())) skip = true;    
//                    if(entity instanceof IPredatorDiet) {
//                        Class[] prey = ((IPredatorDiet)entity).getPreyClasses();
//                        boolean contains = false;
//                        for(int i =0; i < prey.length; i++) {
//                            if(prey[i].getName().equals(ent.getClass().getName())) {
//                                contains = true;
//                            }
//                        }
//                        if(!contains) {
//                            skip = true;
//                        }
//                    }
                    if(!ent.isTouchingWater()) skip = true;                
                    if(!entity.canSee(ent)) skip = true;
                    
                    if(!skip) {
                        if (ent instanceof LivingEntity){
                            if (ent.isTouchingWater()) {
                                entity.aggressTarget = ent;
                            }
                        }
                    }
                }
            }
            if (rand.nextInt(200) == 0) {
                entity.aggressTarget = null;
                entity.setRandomTargetHeading();
            }

        // Hunt Target and/or Do damage
        if(entity.aggressTarget != null) {
            if(entity.squaredDistanceTo(entity.aggressTarget) <= entity.getWidth()) {
                if(entity.aggressTarget instanceof LivingEntity) {
                    entity.aggressTarget.damage(DamageSource.mob(entity), (float) entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getValue());
                }
                if(entity.aggressTarget instanceof TropicraftFishEntity) {
                    // Was eaten, cancel smoke
                    Box aggressBB = entity.aggressTarget.getBoundingBox();
                    if(entityBB.maxY - entityBB.minY > aggressBB.maxY - aggressBB.minY) {
                        entity.aggressTarget.remove(Entity.RemovalReason.KILLED);
                        entity.heal(1);
                        entity.eatenFishAmount++;
                    }
                }
                entity.setRandomTargetHeading();
            }else {
                if(entity.canSee(entity.aggressTarget) && entity.age % 20 == 0) {
                    entity.setTargetHeading(entity.aggressTarget.getX(), entity.aggressTarget.getY(), entity.aggressTarget.getZ(), true);
                }
            }
            if(entity.aggressTarget != null) {
                if(!entity.canSee(entity.aggressTarget) || !entity.aggressTarget.isTouchingWater()) {
                    entity.aggressTarget = null;
                    entity.setRandomTargetHeading();
                }
            }
        }

        if(entity.aggressTarget == null || entity.world.getEntityById(entity.aggressTarget.getId()) == null || !entity.aggressTarget.isAlive()) {
            entity.aggressTarget = null;
            entity.setRandomTargetHeading();
        }
    }

    @Override
    public boolean shouldContinue() {
        return this.canStart();
    }
}
