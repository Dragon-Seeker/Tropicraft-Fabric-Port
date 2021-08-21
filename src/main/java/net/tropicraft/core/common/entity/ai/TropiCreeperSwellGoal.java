package net.tropicraft.core.common.entity.ai;

import net.tropicraft.core.common.entity.passive.TropiCreeperEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class TropiCreeperSwellGoal extends Goal {
    private final TropiCreeperEntity creeper;
    private LivingEntity target;

    public TropiCreeperSwellGoal(TropiCreeperEntity creeper) {
        this.creeper = creeper;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        LivingEntity lvt_1_1_ = this.creeper.getTarget();
        return this.creeper.getCreeperState() > 0 || lvt_1_1_ != null && this.creeper.squaredDistanceTo(lvt_1_1_) < 9.0D;
    }

    @Override
    public void start() {
        this.creeper.getNavigation().stop();
        this.target = this.creeper.getTarget();
    }

    @Override
    public void stop() {
        this.target = null;
    }

    @Override
    public void tick() {
        if (this.target == null) {
            this.creeper.setCreeperState(-1);
        } else if (this.creeper.squaredDistanceTo(this.target) > 49.0D) {
            this.creeper.setCreeperState(-1);
        } else if (!this.creeper.getVisibilityCache().canSee(this.target)) {
            this.creeper.setCreeperState(-1);
        } else {
            this.creeper.setCreeperState(1);
        }
    }
}
