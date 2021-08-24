package net.tropicraft.core.common.entity;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import net.tropicraft.core.common.entity.egg.SeaTurtleEggEntity;
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.tropicraft.core.common.registry.TropicraftEntityAttributes;
import net.tropicraft.core.mixins.SeaTurtleAccessor;
import net.tropicraft.core.mixins.SeaTurtleInvoker;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class SeaTurtleEntity extends TurtleEntity {

    private static final TrackedData<Boolean> IS_MATURE = DataTracker.registerData(SeaTurtleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> TURTLE_TYPE = DataTracker.registerData(SeaTurtleEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> NO_BRAKES = DataTracker.registerData(SeaTurtleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> CAN_FLY = DataTracker.registerData(SeaTurtleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IS_DIGGING = DataTracker.registerData(SeaTurtleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> HAS_EGG = DataTracker.registerData(SeaTurtleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private static final int NUM_TYPES = 6;
    
    private double lastPosY;
    private int digCounter;

    public SeaTurtleEntity(EntityType<? extends TurtleEntity> type, World world) {
        super(type, world);
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.AQUATIC;
    }

    @Override
    protected float calculateNextStepSoundDistance() {
        return this.distanceTraveled + 0.15F;
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficultyInstance, SpawnReason spawnReason, @Nullable EntityData data, @Nullable NbtCompound nbt) {
        setRandomTurtleType();
        this.lastPosY = getY();
        return super.initialize(world, difficultyInstance, spawnReason, data, nbt);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        // goalSelector



        GoalSelector goalSelector = this.goalSelector;
        // goals
        //Set<PrioritizedGoal> goalSet = ObfuscationReflectionHelper.getPrivateValue(GoalSelector.class, goalSelector, "field_220892_d");
        Set<PrioritizedGoal> goalSet = ((SeaTurtleAccessor) goalSelector).getGoals();

        final Optional<PrioritizedGoal> eggGoal = goalSet.stream().filter(p -> p.getGoal().toString().contains("Egg")).findFirst();
        if (eggGoal.isPresent()) {
            goalSelector.remove(eggGoal.get().getGoal());
            goalSelector.add(1, new BetterLayEggGoal(this, 1.0));
        }

        final Optional<PrioritizedGoal> mateGoal = goalSet.stream().filter(p -> p.getGoal().toString().contains("Mate")).findFirst();
        if (mateGoal.isPresent()) {
            goalSelector.remove(mateGoal.get().getGoal());
            goalSelector.add(1, new BetterMateGoal(this, 1.0));
        }
    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        getDataTracker().startTracking(IS_MATURE, true);
        getDataTracker().startTracking(TURTLE_TYPE, 1);
        getDataTracker().startTracking(NO_BRAKES, false);
        getDataTracker().startTracking(CAN_FLY, false);
        getDataTracker().startTracking(IS_DIGGING, false);
        getDataTracker().startTracking(HAS_EGG, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("TurtleType", getTurtleType());
        nbt.putBoolean("IsMature", isMature());
        nbt.putBoolean("NoBrakesOnThisTrain", getNoBrakes());
        nbt.putBoolean("LongsForTheSky", getCanFly());
        nbt.putBoolean("HasEgg", hasEgg());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("TurtleType")) {
            setTurtleType(nbt.getInt("TurtleType"));
        } else {
            setRandomTurtleType();
        }
        if (nbt.contains("IsMature")) {
            setIsMature(nbt.getBoolean("IsMature"));
        } else {
            setIsMature(true);
        }
        setNoBrakes(nbt.getBoolean("NoBrakesOnThisTrain"));
        setCanFly(nbt.getBoolean("LongsForTheSky"));
        setHasEgg(nbt.getBoolean("HasEgg"));
        this.lastPosY = this.getY();
    }

    public boolean isMature() {
        return getDataTracker().get(IS_MATURE);
    }

    public SeaTurtleEntity setIsMature(final boolean mature) {
        getDataTracker().set(IS_MATURE, mature);
        return this;
    }

    public int getTurtleType() {
        return getDataTracker().get(TURTLE_TYPE);
    }
    
    public void setRandomTurtleType() {
        setTurtleType(random.nextInt(NUM_TYPES) + 1);
    }

    public SeaTurtleEntity setTurtleType(final int type) {
        getDataTracker().set(TURTLE_TYPE, MathHelper.clamp(type, 1, NUM_TYPES));
        return this;
    }
    
    public boolean getNoBrakes() {
        return getDataTracker().get(NO_BRAKES);
    }
    
    public SeaTurtleEntity setNoBrakes(final boolean noBrakes) {
        getDataTracker().set(NO_BRAKES, noBrakes);
        return this;
    }

    public boolean getCanFly() {
        return getDataTracker().get(CAN_FLY);
    }
    
    public SeaTurtleEntity setCanFly(final boolean canFly) {
        getDataTracker().set(CAN_FLY, canFly);
        return this;
    }
    
    @Override
    @Nullable
    public Entity getPrimaryPassenger() {
        final List<Entity> passengers = getPassengerList();
        return passengers.isEmpty() ? null : passengers.get(0);
    }

    public static boolean canSpawnOnLand(EntityType<SeaTurtleEntity> turtle, WorldAccess world, SpawnReason reason, BlockPos pos, Random rand) {
        return pos.getY() < world.getSeaLevel() + 4 && world.getBlockState(pos.down()).getBlock() == Blocks.SAND && world.getBaseLightLevel(pos, 0) > 8;
    }

    @Override
    public boolean canBeControlledByRider() {
        return getPrimaryPassenger() instanceof LivingEntity;
    }
    
    @Override
    public double getMountedHeightOffset() {
        return super.getMountedHeightOffset() - 0.1;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity partner) {
        return TropicraftEntities.SEA_TURTLE.create(this.world)
                .setTurtleType(random.nextBoolean() && partner instanceof SeaTurtleEntity ? ((SeaTurtleEntity)partner).getTurtleType() : getTurtleType())
                .setIsMature(false);
    }


    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ActionResult result = super.interactMob(player, hand);
        if (result != ActionResult.PASS) {
            return result;
        }

        if (!world.isClient && !player.isSneaking() && canAddPassenger(player) && isMature()) {
            player.startRiding(this);
        }

        return ActionResult.SUCCESS;
    }
    
    @Override
    public boolean shouldRender(double x, double y, double z) {
        Entity controller = getPrimaryPassenger();
        if (controller != null) {
            return controller.shouldRender(x, y, z);
        }
        return super.shouldRender(x, y, z);
    }
    
    @Override
    public void tick() {
        super.tick();
        lastPosY = getY();
    }
    
    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.isAlive() && this.isDiggingSand() && this.digCounter >= 1 && this.digCounter % 5 == 0) {
            BlockPos pos = this.getBlockPos();
            if (this.world.getBlockState(pos.down()).getMaterial() == Material.AGGREGATE) {
                this.world.syncWorldEvent(2001, pos, Block.getRawIdFromState(Blocks.SAND.getDefaultState()));
            }
        }

        if (this.world.isClient) {
            if (hasPassengers() && canBeControlledByRider()) {
                if (isTouchingWater() || getCanFly()) {
                    Vec3d movement = new Vec3d(getX(), getY(), getZ()).subtract(prevX, prevY, prevZ);
                    double speed = movement.length();
                    Vec3d particleOffset = movement.negate().multiply(2);
                    if (speed > 0.05) {
                        int maxParticles = MathHelper.ceil(speed * 5);
                        int particlesToSpawn = random.nextInt(1 + maxParticles);
                        ParticleEffect particle = isTouchingWater() ? ParticleTypes.BUBBLE : ParticleTypes.END_ROD;
                        for (int i = 0; i < particlesToSpawn; i++) {
                            Vec3d particleMotion = movement.multiply(1);
                            world.addParticle(particle, true,
                                    particleOffset.getX() + getX() - 0.25 + random.nextDouble() * 0.5,
                                    particleOffset.getY() + getY() + 0.1 + random.nextDouble() * 0.1,
                                    particleOffset.getZ() + getZ() - 0.25 + random.nextDouble() * 0.5, particleMotion.x, particleMotion.y, particleMotion.z);
                        }
                    }
                }
            }
        }
    }

    public float lerp(float x1, float x2, float t) {
        return x1 + (t*0.03f) * MathHelper.wrapDegrees(x2 - x1);
    }

    private float swimSpeedCurrent;

    @Override
    public void updatePassengerPosition(Entity passenger) {
        super.updatePassengerPosition(passenger);
        if (this.hasPassenger(passenger)) {
            if(passenger instanceof PlayerEntity) {
                PlayerEntity p = (PlayerEntity)passenger;
                if(this.isTouchingWater()) {
                    if(p.forwardSpeed > 0f) {
                        this.setPitch(this.lerp(getPitch(), -(passenger.getPitch()*0.5f), 6f));
                        this.setYaw(this.lerp(getYaw(), -passenger.getYaw(), 6f));
//                        this.targetVector = null;
//                        this.targetVectorHeading = null;
                        this.swimSpeedCurrent += 0.05f;
                        if(this.swimSpeedCurrent > 4f) {
                            this.swimSpeedCurrent = 4f;
                        }
                    }
                    if(p.forwardSpeed < 0f) {
                        this.swimSpeedCurrent *= 0.89f;
                        if(this.swimSpeedCurrent < 0.1f) {
                            this.swimSpeedCurrent = 0.1f;
                        }
                    }
                    if(p.forwardSpeed == 0f) {
                        if(this.swimSpeedCurrent > 1f) {
                            this.swimSpeedCurrent *= 0.94f;
                            if(this.swimSpeedCurrent <= 1f) {
                                this.swimSpeedCurrent = 1f;
                            }
                        }
                        if(this.swimSpeedCurrent < 1f) {
                            this.swimSpeedCurrent *= 1.06f;
                            if(this.swimSpeedCurrent >= 1f) {
                                this.swimSpeedCurrent = 1f;
                            }
                        }
                        //this.swimSpeedCurrent = 1f;
                    }
                    //    this.swimYaw = -passenger.rotationYaw;
                }
                //p.rotationYaw = this.rotationYaw;
            } else
            if (passenger instanceof MobEntity) {
                MobEntity mobentity = (MobEntity)passenger;
                this.bodyYaw = mobentity.bodyYaw;
                this.prevHeadYaw = mobentity.prevHeadYaw;
            }
        }
    }
        
    @Override
    public void setPosition(double x, double y, double z) {
        super.setPosition(x, y, z);
    }

    @Override
    public void travel(Vec3d input) {
        if (isAlive()) {
            if (hasPassengers() && canBeControlledByRider()) {
                final Entity controllingPassenger = getPrimaryPassenger();

                if (!(controllingPassenger instanceof LivingEntity)) {
                    return;
                }

                final LivingEntity controllingEntity = (LivingEntity) controllingPassenger;

                this.setYaw(controllingPassenger.getYaw());
                this.prevYaw = this.getYaw();
                this.setPitch(controllingPassenger.getPitch());
                this.setRotation(this.getYaw(), this.getPitch());
                this.bodyYaw = this.getYaw();
                this.headYaw = this.getYaw();
                this.stepHeight = 1.0F;
                this.flyingSpeed = this.getMovementSpeed() * 0.1F;

                float strafe = controllingEntity.sidewaysSpeed;
                float forward = getNoBrakes() ? 1 : controllingEntity.forwardSpeed;
                float vertical = controllingEntity.upwardSpeed; // Players never use this?

                double verticalFromPitch = -Math.sin(Math.toRadians(getPitch())) * forward;
                forward *= MathHelper.clamp(1 - (Math.abs(getPitch()) / 90), 0.01f, 1);

                if (!isTouchingWater()) {
                    if (getCanFly()) {


                        this.setVelocity(this.getVelocity().add(0, -this.getAttributeInstance(TropicraftEntityAttributes.ENTITY_GRAVITY).getValue() * 0.05, 0));
                    } else {
                        // Lower max speed when breaching, as a penalty to uncareful driving
                        this.setVelocity(this.getVelocity().multiply(0.9, 0.99, 0.9).add(0, -this.getAttributeInstance(TropicraftEntityAttributes.ENTITY_GRAVITY).getValue(), 0));
                    }
                }

                if (this.isLogicalSideForUpdatingMovement()) {
                    Vec3d travel = new Vec3d(strafe, verticalFromPitch + vertical, forward)
                            .multiply(this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).getValue())
                            // This scale controls max speed. We reduce it significantly here so that the range of speed is higher
                            // This is compensated for by the high value passed to moveRelative
                            .multiply(0.025F);
                    // This is the effective speed modifier, controls the post-scaling of the movement vector
                    updateVelocity(1F, travel);
                    move(MovementType.SELF, getVelocity());
                    // This value controls how much speed is "dampened" which effectively controls how much drift there is, and the max speed
                    this.setVelocity(this.getVelocity().multiply(forward > 0 || !isTouchingWater() ? 0.975 : 0.9));
                } else {
                    this.fallDistance = (float) Math.max(0, (getY() - lastPosY) * -8);
                    this.setVelocity(Vec3d.ZERO);
                }
                this.lastLimbDistance = this.limbDistance;
                double d1 = this.getX() - this.prevX;
                double d0 = this.getZ() - this.prevZ;
                float swinger = (float) Math.sqrt(d1 * d1 + d0 * d0) * 4.0F;
                if (swinger > 1.0F) {
                    swinger = 1.0F;
                }

                this.limbDistance += (swinger - this.limbDistance) * 0.4F;
                this.limbAngle += this.limbDistance;
            } else {
                this.flyingSpeed = 0.02F;
                super.travel(input);
            }
        }
    }
    /*
    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(TropicraftItems.SEA_TURTLE_SPAWN_EGG.get());
    }
     */

    static class BetterLayEggGoal extends MoveToTargetPosGoal {
        private final SeaTurtleEntity turtle;

        BetterLayEggGoal(SeaTurtleEntity turtle, double speedIn) {
            super(turtle, speedIn, 16);
            this.turtle = turtle;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        @Override
        public boolean canStart() {

            return turtle.hasEgg() && ((SeaTurtleInvoker) turtle).inokverGetHomePos().isWithinDistance(turtle.getPos(), 9.0D) && super.canStart();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        @Override
        public boolean shouldContinue() {
            return super.shouldContinue() && turtle.hasEgg() && ((SeaTurtleInvoker) turtle).inokverGetHomePos().isWithinDistance(turtle.getPos(), 9.0D);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void tick() {
            super.tick();
            BlockPos blockpos = this.turtle.getBlockPos();
            if (!this.turtle.isTouchingWater() && this.hasReached()) {
                if (!this.turtle.isDiggingSand()) {
                    this.turtle.setDiggingSand(true);
                } else if (this.turtle.digCounter > 200) {
                    World world = this.turtle.world;
                    world.playSound(null, blockpos, SoundEvents.ENTITY_TURTLE_LAY_EGG, SoundCategory.BLOCKS, 0.3F, 0.9F + world.random.nextFloat() * 0.2F);
                    //world.setBlockState(this.destinationBlock.up(), Blocks.TURTLE_EGG.defaultBlockState().with(TurtleEggBlock.EGGS, Integer.valueOf(this.turtle.rand.nextInt(4) + 1)), 3);
                    final SeaTurtleEggEntity egg = TropicraftEntities.SEA_TURTLE_EGG.create(world);
                    final BlockPos spawnPos = targetPos.up();
                    egg.setPosition(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
                    world.spawnEntity(egg);
                    this.turtle.setHasEgg(false);
                    this.turtle.setDiggingSand(false);
                    this.turtle.setLoveTicks(600);
                }

                if (this.turtle.isDiggingSand()) {
                    this.turtle.digCounter++;
                }
            }
        }

        @Override
        protected boolean isTargetPos(WorldView worldIn, BlockPos pos) {
            if (!worldIn.isAir(pos.up())) {
                return false;
            } else {
                return worldIn.getBlockState(pos).getMaterial() == Material.AGGREGATE;
            }
        }
    }

    static class BetterMateGoal extends AnimalMateGoal {
        private final SeaTurtleEntity turtle;

        BetterMateGoal(SeaTurtleEntity turtle, double speedIn) {
            super(turtle, speedIn);
            this.turtle = turtle;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        @Override
        public boolean canStart() {
            return super.canStart() && !this.turtle.hasEgg();
        }

        /**
         * Spawns a baby animal of the same type.
         */
        @Override
        protected void breed() {
            ServerPlayerEntity serverplayerentity = this.animal.getLovingPlayer();
            if (serverplayerentity == null && this.mate.getLovingPlayer() != null) {
                serverplayerentity = this.mate.getLovingPlayer();
            }

            if (serverplayerentity != null) {
                serverplayerentity.incrementStat(Stats.ANIMALS_BRED);
                Criteria.BRED_ANIMALS.trigger(serverplayerentity, this.animal, this.mate, null);
            }

            this.turtle.setHasEgg(true);
            this.animal.resetLoveTicks();
            this.mate.resetLoveTicks();
            Random random = this.animal.getRandom();
            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
            }

        }
    }

    private void setDiggingSand(boolean digging) {
        this.digCounter = digging ? 1 : 0;
        this.dataTracker.set(IS_DIGGING, digging);
    }

    @Override
    public boolean isDiggingSand() {
        return digCounter > 0;
    }

    private void setHasEgg(boolean hasEgg) {
        this.dataTracker.set(HAS_EGG, hasEgg);
    }

    @Override
    public boolean hasEgg() {
        return dataTracker.get(HAS_EGG);
    }
}
