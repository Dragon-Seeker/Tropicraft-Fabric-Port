package net.tropicraftFabric.common.entity.ai.fishies;

import net.tropicraftFabric.common.entity.underdasea.TropicraftFishEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.Random;

public class RandomSwimGoal extends Goal {
    public TropicraftFishEntity entity;
    public Random rand;

    public RandomSwimGoal(EnumSet<Control> flags, TropicraftFishEntity entityObjIn) {
        this.entity = entityObjIn;
        rand = this.entity.getRandom();
        setControls(flags);
    }

    @Override
    public boolean canStart() {
        return entity.isTouchingWater() && entity.age % 10+rand.nextInt(20) == 0;
    }

    @Override
    public void tick() {
        super.tick();
        
        entity.setRandomTargetHeading();
        if (entity.eatenFishAmount > 0 && rand.nextInt(10) == 0) {
            entity.eatenFishAmount--;
        }
    }

    @Override
    public boolean shouldContinue() {
        return entity.targetVector == null;
    }
}
