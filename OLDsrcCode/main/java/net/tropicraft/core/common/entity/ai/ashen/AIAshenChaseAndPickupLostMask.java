package net.tropicraft.core.common.entity.ai.ashen;

import net.minecraft.entity.ai.FuzzyTargeting;
import net.tropicraft.core.common.entity.hostile.AshenEntity;
import net.minecraft.entity.LivingEntity;
//import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class AIAshenChaseAndPickupLostMask extends Goal {
	public AshenEntity ashen;
	public LivingEntity target;
	public double speed = 1D;
	public double maskGrabDistance = 3D;
	public int panicTime = 0;

	public AIAshenChaseAndPickupLostMask(AshenEntity ashen, double speed) {
		this.ashen = ashen;
		this.speed = speed;
		this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
	}

	@Override
	public boolean canStart() {
		return !ashen.hasMask() && ashen.maskToTrack != null;
	}

	@Override
	public boolean shouldContinue() {
		return !ashen.hasMask() && ashen.maskToTrack != null && ashen.maskToTrack.isAlive();
	}

	@Override
	public void tick() {
		if (panicTime > 0) {
			panicTime--;

			if (ashen.world.getTime() % 10 == 0) {
				Vec3d vec3 = FuzzyTargeting.find(ashen, 10, 7);

				if (vec3 != null) {
					ashen.getNavigation().startMovingTo(vec3.x, vec3.y, vec3.z, speed);
				}
			}
		} else {
			if (ashen.squaredDistanceTo(ashen.maskToTrack) <= maskGrabDistance) {
				if (ashen.maskToTrack.isAlive()/* && ashen.world.loadedEntityList.contains(ashen.maskToTrack)*/) {
					ashen.pickupMask(ashen.maskToTrack);
				} else {
					ashen.maskToTrack = null;
				}
			} else {
				if (ashen.world.getTime() % 40 == 0) {
					ashen.getNavigation().startMovingTo(ashen.maskToTrack.getX(), ashen.maskToTrack.getY(), ashen.maskToTrack.getZ(), speed);
				}
			}
		}
	}

	@Override
	public void start() {
		super.start();
		panicTime = 120;
	}

	@Override
    public void stop() {
		this.target = null;
	}
}
