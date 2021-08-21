package net.tropicraft.core.common.entity.passive;

import net.tropicraft.core.common.registry.TropicraftEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class FailgullEntity extends AnimalEntity implements Flutterer {

	private boolean isFlockLeader;
	private static final TrackedData<Optional<UUID>> FLOCK_LEADER_UUID = DataTracker.registerData(FailgullEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

	public FailgullEntity(EntityType<? extends FailgullEntity> type, World world) {
		super(type, world);
		experiencePoints = 1;
		moveControl = new FlightMoveControl(this, 5, true);
		this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
		this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.FENCE, -1.0F);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return AnimalEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 3.0)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6)
				.add(EntityAttributes.GENERIC_FLYING_SPEED, 0.9)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12.0);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		dataTracker.startTracking(FLOCK_LEADER_UUID, Optional.empty());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		isFlockLeader = nbt.getBoolean("IsFlockLeader");
		if (nbt.contains("FlockLeader")) {
			setFlockLeader(Optional.of(nbt.getUuid("FlockLeader")));
		} else {
			setFlockLeader(Optional.empty());
		}
	}

	@Override
	public void writeCustomDataToNbt(final NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putBoolean("IsFlockLeader", isFlockLeader);
		dataTracker.get(FLOCK_LEADER_UUID).ifPresent(uuid -> nbt.putUuid("FlockLeader", uuid));
	}

	@Override
	public float getPathfindingFavor(final BlockPos pos, final WorldView worldIn) {
		return worldIn.getBlockState(pos).isAir() ? 10.0F : 0.0F;
	}

	@Override
	public void initGoals() {
		goalSelector.add(0, new ValidateFlockLeader(this));
		goalSelector.add(1, new SelectFlockLeader(this));
		goalSelector.add(2, new SetTravelDestination());
		goalSelector.add(2, new FollowLeaderGoal());
	}

	@Override
	protected float getActiveEyeHeight(EntityPose poseIn, EntityDimensions sizeIn) {
		return sizeIn.height * 0.5F;
	}

	@Override
	protected void fall(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	@Override
	protected boolean hasWings() {
		return false;
	}

	@Override
	protected EntityNavigation createNavigation(World worldIn) {
		BirdNavigation flyingpathnavigator = new BirdNavigation(this, worldIn) {
			public boolean isValidPosition(BlockPos pos) {
				return !this.world.getBlockState(pos.down()).isAir();
			}
		};
		flyingpathnavigator.setCanPathThroughDoors(false);
		flyingpathnavigator.setCanSwim(false);
		flyingpathnavigator.setCanEnterOpenDoors(true);
		return flyingpathnavigator;
	}

	private void poop() {
		if (!world.isClient && world.random.nextInt(20) == 0) {
			SnowballEntity s = new SnowballEntity(world, getX(), getY(), getZ());
			s.setVelocity(0, 0, 0, 0, 0);
			world.spawnEntity(s);
		}
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return null;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return null;
	}

	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	@Nullable
	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity partner) {
		return null;
	}

	private void setIsFlockLeader(final boolean isFlockLeader) {
		this.isFlockLeader = isFlockLeader;
	}

	private void setFlockLeader(final Optional<UUID> flockLeaderUUID) {
		dataTracker.set(FLOCK_LEADER_UUID, flockLeaderUUID);
	}

	private boolean getIsFlockLeader() {
		return isFlockLeader;
	}

	private boolean hasFlockLeader() {
		return dataTracker.get(FLOCK_LEADER_UUID).isPresent();
	}

	@Nullable
	private Entity getFlockLeader() {
		if (world instanceof ServerWorld && hasFlockLeader()) {
			return ((ServerWorld) world).getEntity(dataTracker.get(FLOCK_LEADER_UUID).get());
		}

		return null;
	}

	@Nullable
	private BlockPos getRandomLocation() {
		final Random random = getRandom();
		for (int i = 0; i < 20; i++) {
			double nextXPos = getX() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 48);
			double nextYPos = getY() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 3);
			double nextZPos = getZ() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 48);
			final BlockPos pos = new BlockPos(nextXPos, nextYPos, nextZPos);
			if (world.isAir(pos)) {
				return pos;
			}
		}

		Vec3d Vector3d = getRotationVec(0.0F);

		Vec3d Vector3d2 = TargetFinder.findAirTarget(FailgullEntity.this, 40, 3, Vector3d, ((float)Math.PI / 2F), 2, 1);
		final Vec3d groundTarget = TargetFinder.findGroundTarget(FailgullEntity.this, 40, 4, -2, Vector3d, (double) ((float) Math.PI / 2F));
		return Vector3d2 != null ? new BlockPos(Vector3d2) : groundTarget != null ? new BlockPos(groundTarget) : null;
	}
	/*
	@Override
	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(TropicraftItems.FAILGULL_SPAWN_EGG.get());
	}
	*/

	class FollowLeaderGoal extends Goal {
		FollowLeaderGoal() {
			this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		}

		private boolean canFollow() {
			return !getIsFlockLeader() && hasFlockLeader();
		}

		@Override
		public boolean canStart() {
			return canFollow() && getNavigation().isIdle() && FailgullEntity.this.random.nextInt(10) == 0;
		}

		@Override
		public boolean shouldContinue() {
			return canFollow() && getNavigation().isFollowingPath();
		}

		@Override
		public void start() {
			final Entity flockLeader = getFlockLeader();
			final EntityNavigation navigator = getNavigation();
			if (flockLeader != null && flockLeader.getType() == TropicraftEntities.FAILGULL) {
				navigator.startMovingAlong(navigator.findPathTo(flockLeader.getBlockPos(), 1), 1.0D);
				return;
			}
			BlockPos Vector3d = getRandomLocation();
			if (Vector3d != null) {
				navigator.startMovingAlong(navigator.findPathTo(Vector3d, 1), 1.0D);
			}

		}
	}

	class SetTravelDestination extends Goal {
		SetTravelDestination() {
			setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		}

		private boolean shouldLead() {
			return getIsFlockLeader() || !hasFlockLeader();
		}

		@Override
		public boolean canStart() {
			return shouldLead() && getNavigation().isIdle() && getRandom().nextInt(10) == 0;
		}

		@Override
		public boolean shouldContinue() {
			return shouldLead() && getNavigation().isFollowingPath();
		}

		@Override
		public void start() {
			BlockPos Vector3d = getRandomLocation();
			if (Vector3d != null) {
				final EntityNavigation navigator = getNavigation();
				navigator.startMovingAlong(navigator.findPathTo(Vector3d, 1), 1.0D);
			}
		}
	}

	private static class ValidateFlockLeader extends Goal {
		final FailgullEntity mob;

		public ValidateFlockLeader(FailgullEntity failgullEntity) {
			mob = failgullEntity;
		}

		@Override
		public boolean canStart() {
			if (mob.getIsFlockLeader()) {
				return false;
			}

			final Entity flockLeader = mob.getFlockLeader();
			return flockLeader == null || !flockLeader.isAlive();
		}

		@Override
		public void start() {
			mob.setFlockLeader(Optional.empty());
		}
	}

	private static class SelectFlockLeader extends Goal {
		final FailgullEntity mob;

		public SelectFlockLeader(FailgullEntity failgullEntity) {
			mob = failgullEntity;
		}

		@Override
		public boolean canStart() {
			return !mob.hasFlockLeader();
		}

		@Override
		public void start() {
			List<FailgullEntity> list = mob.world.getNonSpectatingEntities(FailgullEntity.class, mob.getBoundingBox().expand(10D, 10D, 10D));
			list.remove(mob);

			final Optional<FailgullEntity> oldest = list.stream().min(Comparator.comparingInt(FailgullEntity::getEntityId));
			// Found an older one nearby, set it as the flock leader
			if (oldest.isPresent() && !oldest.get().uuid.equals(mob.getUuid())) {
				final FailgullEntity oldestFailgull = oldest.get();
				oldestFailgull.setIsFlockLeader(true);
				oldestFailgull.setFlockLeader(Optional.empty());
				mob.setIsFlockLeader(false);
				mob.setFlockLeader(Optional.of(oldestFailgull.getUuid()));
			}
		}
	}
}
