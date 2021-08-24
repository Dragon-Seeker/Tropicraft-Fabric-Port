package net.tropicraft.core.common.entity.underdasea;

import net.tropicraft.core.common.registry.TropicraftEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ManOWarEntity extends WaterCreatureEntity {
    public float squidPitch;
    public float prevSquidPitch;
    public float squidYaw;
    public float prevSquidYaw;
    public float squidRotation;
    public float prevSquidRotation;
    public float tentacleAngle;
    public float lastTentacleAngle;
    private float randomMotionSpeed;
    private float rotationVelocity;
    private float rotateSpeed;
    private float randomMotionVecX;
    private float randomMotionVecY;
    private float randomMotionVecZ;
    private int attackTimer = 0;

    public ManOWarEntity(final EntityType<? extends ManOWarEntity> type, World world){
        super(type, world);
        this.random.setSeed(this.getId());
        this.rotationVelocity = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
        this.experiencePoints = 7;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new MoveRandomGoal(this));
        this.goalSelector.add(1, new FleeGoal());
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return WaterCreatureEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions size) {
        return size.height * 0.5F;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    /*
    @Override
    protected boolean canClimb() {
        return false;
    }
     */

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public LivingEntity getTarget() {
        return null;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        this.prevSquidPitch = this.squidPitch;
        this.prevSquidYaw = this.squidYaw;
        this.prevSquidRotation = this.squidRotation;
        this.lastTentacleAngle = this.tentacleAngle;
        this.squidRotation += this.rotationVelocity;
        if ((double)this.squidRotation > 6.283185307179586D) {
            if (this.world.isClient) {
                this.squidRotation = 6.2831855F;
            } else {
                this.squidRotation = (float)((double)this.squidRotation - 6.283185307179586D);
                if (this.random.nextInt(10) == 0) {
                    this.rotationVelocity = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
                }

                this.world.sendEntityStatus(this, (byte)19);
            }
        }

        if (attackTimer > 0) {
            attackTimer--;
        }

        if (isInsideWaterOrBubbleColumn()) {
            if (random.nextInt(5) == 0 && attackTimer <= 0) {
                List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, getBoundingBox().expand(2D, 4D, 2D).offset(0.0D, -2.0D, 0.0D), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR);
                for (LivingEntity ent : list) {
                    if (ent.getType() != TropicraftEntities.MAN_O_WAR) {
                        if (ent.isTouchingWater()) {
                            // TODO change so death msg isn't "struck by lightning"
                            ent.damage(DamageSource.LIGHTNING_BOLT, (float) getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getValue());
                            attackTimer = 20;
                        }
                    }
                }
            }

            if (this.squidRotation < 3.1415927F) {
                float lvt_1_1_ = this.squidRotation / 3.1415927F;
                this.tentacleAngle = MathHelper.sin(lvt_1_1_ * lvt_1_1_ * 3.1415927F) * 3.1415927F * 0.25F;
                if ((double)lvt_1_1_ > 0.75D) {
                    this.randomMotionSpeed = 1.0F;
                    this.rotateSpeed = 1.0F;
                } else {
                    this.rotateSpeed *= 0.8F;
                }
            } else {
                this.tentacleAngle = 0.0F;
                this.randomMotionSpeed *= 0.9F;
                this.rotateSpeed *= 0.99F;
            }

            if (!this.world.isClient) {
                this.setVelocity(this.randomMotionVecX * this.randomMotionSpeed, this.randomMotionVecY * this.randomMotionSpeed, this.randomMotionVecZ * this.randomMotionSpeed);
            }

            Vec3d motion = this.getVelocity();
            float lvt_2_1_ = (float) Math.sqrt(motion.horizontalLengthSquared());
            this.bodyYaw += (-((float)MathHelper.atan2(motion.x, motion.z)) * 57.295776F - this.bodyYaw) * 0.1F;
            this.setYaw(this.bodyYaw);
            this.squidYaw = (float)((double)this.squidYaw + 3.141592653589793D * (double)this.rotateSpeed * 1.5D);
            this.squidPitch += (-((float)MathHelper.atan2(lvt_2_1_, motion.y)) * 57.295776F - this.squidPitch) * 0.1F;
        } else {
            this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.squidRotation)) * 3.1415927F * 0.25F;
            if (!this.world.isClient) {
                double lvt_1_3_ = this.getVelocity().y;
                if (this.hasStatusEffect(StatusEffects.LEVITATION)) {
                    lvt_1_3_ = 0.05D * (double)(this.getStatusEffect(StatusEffects.LEVITATION).getAmplifier() + 1);
                } else if (!this.hasNoGravity()) {
                    lvt_1_3_ -= 0.08D;
                }

                this.setVelocity(0.0D, lvt_1_3_ * 0.9800000190734863D, 0.0D);
            }

            this.squidPitch = (float)((double)this.squidPitch + (double)(-90.0F - this.squidPitch) * 0.02D);
        }

    }

    @Override
    public void onDeath(DamageSource d) {
        super.onDeath(d);
        if (!this.world.isClient) {
            int numDrops = 3 + this.random.nextInt(1);

            for (int i = 0; i < numDrops; i++) {
                dropItem(Items.SLIME_BALL, 1);
            }
        }
    }

    @Override
    public int getMinAmbientSoundDelay() {
        return 120;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SQUID_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_SQUID_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SQUID_DEATH;
    }

    @Override
    public void travel(Vec3d vector) {
        this.move(MovementType.SELF, this.getVelocity());
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void handleStatus(byte id) {
        if (id == 19) {
            this.squidRotation = 0.0F;
        } else {
            super.handleStatus(id);
        }
    }

    public void setMovementVector(float randomMotionVecXIn, float randomMotionVecYIn, float randomMotionVecZIn) {
        this.randomMotionVecX = randomMotionVecXIn;
        this.randomMotionVecY = randomMotionVecYIn;
        this.randomMotionVecZ = randomMotionVecZIn;
    }

    public boolean hasMovementVector() {
        return this.randomMotionVecX != 0.0F || this.randomMotionVecY != 0.0F || this.randomMotionVecZ != 0.0F;
    }

    /*
    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(TropicraftItems.MAN_O_WAR_SPAWN_EGG.get());
    }
     */

    class FleeGoal extends Goal {
        private int tickCounter;

        private FleeGoal() {
        }

        @Override
        public boolean canStart() {
            LivingEntity lvt_1_1_ = ManOWarEntity.this.getAttacker();
            if (ManOWarEntity.this.isTouchingWater() && lvt_1_1_ != null) {
                return ManOWarEntity.this.squaredDistanceTo(lvt_1_1_) < 100.0D;
            } else {
                return false;
            }
        }

        @Override
        public void start() {
            this.tickCounter = 0;
        }

        @Override
        public void tick() {
            ++this.tickCounter;
            LivingEntity target = ManOWarEntity.this.getAttacker();
            if (target != null) {
                Vec3d lvt_2_1_ = new Vec3d(ManOWarEntity.this.getX() - target.getX(), ManOWarEntity.this.getY() - target.getY(), ManOWarEntity.this.getZ() - target.getZ());
                BlockState block = ManOWarEntity.this.world.getBlockState(new BlockPos(ManOWarEntity.this.getX() + lvt_2_1_.x, ManOWarEntity.this.getY() + lvt_2_1_.y, ManOWarEntity.this.getZ() + lvt_2_1_.z));
                FluidState fluid = ManOWarEntity.this.world.getFluidState(new BlockPos(ManOWarEntity.this.getX() + lvt_2_1_.x, ManOWarEntity.this.getY() + lvt_2_1_.y, ManOWarEntity.this.getZ() + lvt_2_1_.z));
                if (fluid.isIn(FluidTags.WATER) || block.isAir()) {
                    double lvt_5_1_ = lvt_2_1_.length();
                    if (lvt_5_1_ > 0.0D) {
                        lvt_2_1_.normalize();
                        float lvt_7_1_ = 3.0F;
                        if (lvt_5_1_ > 5.0D) {
                            lvt_7_1_ = (float)((double)lvt_7_1_ - (lvt_5_1_ - 5.0D) / 5.0D);
                        }

                        if (lvt_7_1_ > 0.0F) {
                            lvt_2_1_ = lvt_2_1_.multiply(lvt_7_1_);
                        }
                    }

                    if (block.isAir()) {
                        lvt_2_1_ = lvt_2_1_.subtract(0.0D, lvt_2_1_.y, 0.0D);
                    }

                    ManOWarEntity.this.setMovementVector((float)lvt_2_1_.x / 20.0F, (float)lvt_2_1_.y / 20.0F, (float)lvt_2_1_.z / 20.0F);
                }

                if (this.tickCounter % 10 == 5) {
                    ManOWarEntity.this.world.addParticle(ParticleTypes.BUBBLE, ManOWarEntity.this.getX(), ManOWarEntity.this.getY(), ManOWarEntity.this.getZ(), 0.0D, 0.0D, 0.0D);
                }

            }
        }
    }

    static class MoveRandomGoal extends Goal {
        private final ManOWarEntity manOWarEntity;

        public MoveRandomGoal(ManOWarEntity p_i48823_2_) {
            this.manOWarEntity = p_i48823_2_;
        }

        @Override
        public boolean canStart() {
            return true;
        }

        @Override
        public void tick() {
            int lvt_1_1_ = this.manOWarEntity.getDespawnCounter();
            if (lvt_1_1_ > 100) {
                this.manOWarEntity.setMovementVector(0.0F, 0.0F, 0.0F);
            } else if (this.manOWarEntity.getRandom().nextInt(50) == 0 || !this.manOWarEntity.isTouchingWater() || !this.manOWarEntity.hasMovementVector()) {
                float lvt_2_1_ = this.manOWarEntity.getRandom().nextFloat() * 6.2831855F;
                float lvt_3_1_ = MathHelper.cos(lvt_2_1_) * 0.2F;
                float lvt_4_1_ = -0.1F + this.manOWarEntity.getRandom().nextFloat() * 0.2F;
                float lvt_5_1_ = MathHelper.sin(lvt_2_1_) * 0.2F;
                this.manOWarEntity.setMovementVector(lvt_3_1_, lvt_4_1_, lvt_5_1_);
            }

        }
    }
}
