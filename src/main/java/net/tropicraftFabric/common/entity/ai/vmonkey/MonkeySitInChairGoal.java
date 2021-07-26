package net.tropicraftFabric.common.entity.ai.vmonkey;

import net.tropicraftFabric.common.entity.neutral.VMonkeyEntity;
import net.tropicraftFabric.common.entity.placeable.ChairEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class MonkeySitInChairGoal extends Goal {
    private VMonkeyEntity entity;

    public MonkeySitInChairGoal(VMonkeyEntity monkey) {
        this.entity = monkey;
        setControls(EnumSet.of(Control.MOVE, Control.LOOK, Control.JUMP));
    }

    private Optional<ChairEntity> getNearestEmptyChair() {
        List<ChairEntity> list = entity.world.getNonSpectatingEntities(ChairEntity.class, entity.getBoundingBox().expand(32D));
        return list.stream().filter(chair -> !chair.isInvisible() && !chair.hasPassengers()).findFirst();
    }

    private boolean isOwnerNear() {
        return entity != null && entity.getOwner() != null && entity.getOwner().squaredDistanceTo(entity) < 32D;
    }

    private boolean isOwnerNearAndSitting() {
        Entity ridingEntity = entity.getOwner().getVehicle();
        return isOwnerNear() && ridingEntity instanceof ChairEntity;
    }

    @Override
    public void stop() {
        entity.stopRiding();
        entity.setSitting(false);
        // TODO - no longer needed?
        // entity.resetRideCooldown();
    }

    @Override
    public boolean canStart() {
        if (entity == null || !entity.isTamed() || entity.getOwner() == null) {
            return false;
        }
        return hasNearbyEmptyChair() && isOwnerNearAndSitting();
    }

    private boolean hasNearbyEmptyChair() {
        return getNearestEmptyChair().isPresent();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinue() {
        return isOwnerNearAndSitting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        final Optional<ChairEntity> nearbyChair = getNearestEmptyChair();
        if (nearbyChair.isPresent()) {
            entity.setSitting(true);
            entity.startRiding(nearbyChair.get());
        }
    }
}
