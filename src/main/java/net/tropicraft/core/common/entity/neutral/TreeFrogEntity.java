package net.tropicraft.core.common.entity.neutral;

import net.tropicraft.core.common.entity.hostile.TropicraftCreatureEntity;
import net.tropicraft.core.common.entity.projectile.PoisonBlotEntity;
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.tropicraft.core.common.sound.Sounds;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TreeFrogEntity extends TropicraftCreatureEntity implements Monster, RangedAttackMob {

    private static final TrackedData<Integer> TYPE = DataTracker.registerData(TreeFrogEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public enum Type {
        GREEN("green"),
        RED("red"),
        BLUE("blue"),
        YELLOW("yellow");

        final String color;

        Type(String s) {
            this.color = s;
        }

        public String getColor() {
            return color;
        }
    }

    private FollowTargetGoal<PlayerEntity> hostileAI;

    public int jumpDelay = 0;
    private int attackTime;

    public TreeFrogEntity(EntityType<? extends PathAwareEntity> type, World world) {
        super(type, world);
        //TODO: Welp, figure out how to readd this later
        //pushSpeedReduction = 0.8F;
        experiencePoints = 5;
    }

    @Override
    public void initGoals() {
        goalSelector.add(0, new SwimGoal(this));
        goalSelector.add(1, new ProjectileAttackGoal(this, 1.0D, 60, 10.0F));
        goalSelector.add(2, new WanderAroundFarGoal(this, 1.0D));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 5.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }

    @Override
    protected void mobTick() {
        super.mobTick();
        if (!getNavigation().isIdle() || this.getTarget() != null) {
            if (onGround || isTouchingWater()) {
                if (jumpDelay > 0)
                    jumpDelay--;
                if (jumpDelay <= 0) {
                    jumpDelay = 5 + random.nextInt(4);

                    // this.jump();
                    // this.motionY += -0.01D + rand.nextDouble() * 0.1D;
                    final Vec3d motion = getVelocity();

                    double speed = Math.sqrt(motion.x * motion.x + motion.z * motion.z);
                    if (speed > 0.02D) {
                        final double motionY = motion.y + 0.4d;
                        final double motionX = motion.x * 1.1d;
                        final double motionZ = motion.z * 1.1d;
                        setVelocity(motionX, motionY, motionZ);
                    }
                }
            }
        }

        if (attackTime > 0)
            attackTime--;
    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        getDataTracker().startTracking(TYPE, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Type", getFrogType());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        setFrogType(nbt.getInt("Type"));
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason reason, @Nullable EntityData spawnData, @Nullable NbtCompound dataTag) {
        final int type = random.nextInt(Type.values().length);
        setFrogType(type);

        if (type != 0) {
            hostileAI = new FollowTargetGoal<>(this, PlayerEntity.class, true);
            targetSelector.add(1, hostileAI);
        }

        return super.initialize(world, difficulty, reason, spawnData, dataTag);
    }

    public void setFrogType(int i) {
        dataTracker.set(TYPE, i);
    }

    public int getFrogType() {
        return dataTracker.get(TYPE);
    }

    public String getColor() {
        return Type.values()[getFrogType()].getColor();
    }

    @Override
    public void attack(final LivingEntity entity, float dist) {
        if (dist < 4F && !world.isClient && attackTime == 0 && world.getDifficulty() != Difficulty.PEACEFUL) {
            double d = entity.getX() - getX();
            double d1 = entity.getZ() - getZ();

            final PoisonBlotEntity poison = new PoisonBlotEntity(TropicraftEntities.POISON_BLOT, this, world);
            poison.setPosition(poison.getX(), poison.getY() + 1.3999999761581421D, poison.getZ());
            final double shotHeight = (entity.getY() + (double) entity.getStandingEyeHeight()) - 0.20000000298023224D - poison.getY();
            float f1 = (float) Math.sqrt(d * d + d1 * d1) * 0.2F;
            entity.getEntityWorld().playSound(null, entity.getBlockPos(), Sounds.FROG_SPIT, SoundCategory.HOSTILE, 1, 1);
            world.spawnEntity(poison);
            poison.setVelocity(d, shotHeight + (double) f1, d1, 0.6F, 12F);
            attackTime = 50;
            this.setYaw((float) ((Math.atan2(d1, d) * 180D) / 3.1415927410125732D) - 90F);
        }
    }

    /*
    @Override
    public ItemStack getPickedResult(HitResult target) {
        // TODO - add one egg per type
        return new ItemStack(TropicraftItems.TREE_FROG_SPAWN_EGG);
    }
     */
}
