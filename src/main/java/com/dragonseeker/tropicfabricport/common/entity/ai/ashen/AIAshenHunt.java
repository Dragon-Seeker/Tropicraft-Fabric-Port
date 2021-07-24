package com.dragonseeker.tropicfabricport.common.entity.ai.ashen;

import com.dragonseeker.tropicfabricport.common.entity.AshenEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Vec3d;

public class AIAshenHunt extends Goal {

    public AshenEntity ashen;
    public long huntRange = 24;
    public long keepDistantRange = 14;
    
    public boolean xRay = false;
    
    public boolean useMelee = false;
    public int useMeleeCountdown = 0;
    public int useMeleeCountdownMax = 80;
    
    public Vec3d targetLastPos = null;
    public int targetNoMoveTicks = 0;
    public int targetNoMoveTicksMax = 4;
    public int panicTicks = 0;
    
    public LivingEntity target;
    
    public AIAshenHunt(AshenEntity ashen) {
        this.ashen = ashen;
    }

    @Override
    public boolean canStart() {
        LivingEntity entitylivingbase = ashen.getTarget();

        if (entitylivingbase == null) {
            return false;
        } else {
            this.target = entitylivingbase;
            return true;
        }
    }
    
    @Override
    public boolean shouldContinue() {
        return this.canStart() || !this.ashen.getNavigation().isIdle();
    }

    @Override
    public void stop() {
        this.target = null;
    }

}
