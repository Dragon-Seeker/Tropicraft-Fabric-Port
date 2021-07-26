package net.tropicraftFabric.common.entity.hostile;

import net.tropicraftFabric.common.Util;
import net.tropicraftFabric.common.entity.ai.EntityAIWanderNotLazy;
import net.tropicraftFabric.common.entity.egg.TropiSpiderEggEntity;
import net.tropicraftFabric.common.registry.TropicraftEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TropiSpiderEntity extends SpiderEntity {

    public enum Type {
        ADULT, MOTHER, CHILD;

        private static final Type[] VALUES = values();
    }

    private static final TrackedData<Byte> TYPE = DataTracker.<Byte>registerData(TropiSpiderEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final int SPIDER_MATURE_AGE = 20 * 60 * 10; // From child to adult in 10 real minutes
    private static final int SPIDER_MAX_EGGS = 10;
    private static final long SPIDER_MIN_EGG_DELAY = 12000; // Once per half minecraft day minimum
    private static final int SPIDER_EGG_CHANCE = 1000;

    private BlockPos nestSite;
    private TropiSpiderEntity mother = null;
    private long ticksSinceLastEgg = 0L;
    public byte initialType = 0;

    public TropiSpiderEntity(final EntityType<? extends SpiderEntity> type, World world) {
        super(type, world);
        age = SPIDER_MATURE_AGE;
        ticksSinceLastEgg = age;
    }

    public static TropiSpiderEntity haveBaby(final TropiSpiderEntity mother) {
        final TropiSpiderEntity baby = new TropiSpiderEntity(TropicraftEntities.TROPI_SPIDER, mother.world);
        baby.setSpiderType(Type.CHILD);
        baby.age = 0;
        baby.mother = mother;
        return baby;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(TYPE, initialType);
    }

    @Override
    protected void initGoals() {
        goalSelector.add(0, new SwimGoal(this));
        goalSelector.add(2, new MeleeAttackGoal(this, 0.8D, false));
        goalSelector.add(7, new EntityAIWanderNotLazy(this, 0.8D, 40));
        goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        goalSelector.add(8, new LookAroundGoal(this));
    }
    
    @Override
    protected void applyDamage(DamageSource damageSrc, float damageAmount) {
        if (damageSrc.getAttacker() != null && damageSrc.getAttacker() instanceof LivingEntity) {
            setTarget((LivingEntity) damageSrc.getAttacker());
        }
        super.applyDamage(damageSrc, damageAmount);
    }

    @Override
    public boolean isClimbing() {
        return isClimbingWall() && getNavigation().isIdle();
    }

    @Override
    public boolean isClimbingWall() {
        return horizontalCollision;
    }

    @Override
    public void tick() {
        fallDistance = 0;
        // TODO port to new getSize() method
//        if (this.getType() == Type.CHILD) {
//            this.setSize(0.9F, 0.7F);
//        } else {
//            this.setSize(1.4F, 0.9F);
//        }
        super.tick();
        LivingEntity attackTarget = getTarget();
        if (attackTarget != null) {
            if (squaredDistanceTo(attackTarget) < 128D) {
                Util.tryMoveToEntityLivingLongDist(this, attackTarget, 0.8f);
            }
        }
        if (!world.isClient && attackTarget != null && onGround && random.nextInt(3) == 0 && attackTarget.distanceTo(this) < 5) {
            getNavigation().stop();
            jump();
            flyingSpeed = 0.3f;
        } else {
            flyingSpeed = 0.2f;
        }
        if (!world.isClient) {
            if (getSpiderType() == Type.CHILD) {
                if (age >= SPIDER_MATURE_AGE) {
                    setSpiderType(Type.ADULT);
                }
                if (mother != null) {
                    if (squaredDistanceTo(mother) > 16D) {
                        Util.tryMoveToEntityLivingLongDist(this, mother, 0.8f);
                    } else {
                        getNavigation().stop();
                    }
                    if (mother.getTarget() != null) {
                        setTarget(mother.getTarget());
                    }
                }
            }

            if (getSpiderType() == Type.ADULT) {
                if (mother != null) {
                    if (!mother.isAlive()) {
                        mother = null;
                        getNavigation().stop();
                        setTarget(null);
                    }
                    // issues much?
                    setTarget(this.mother);
                }
                if (random.nextInt(SPIDER_EGG_CHANCE) == 0 && this.ticksSinceLastEgg > SPIDER_MIN_EGG_DELAY && this.age % 80 == 0) {
                    buildNest();
                }
            }

            if (getSpiderType() == Type.MOTHER) {
                if (nestSite != null) {
                    if (ticksSinceLastEgg < 2000) {
                        if (!getBlockPos().isWithinDistance(nestSite, 16)) {
                            Util.tryMoveToXYZLongDist(this, nestSite, 0.9f);
                        }
                    } else {
                        nestSite = null;
                    }
                }
            }
            ticksSinceLastEgg++;
        }
    }
    
    
    @Override
    protected SoundEvent getAmbientSound() {
        return random.nextInt(20) == 0 ? super.getAmbientSound() : null;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockState) {
        if (getSpiderType() == Type.CHILD) {
            if (random.nextInt(20) == 0) {
                super.playStepSound(pos, blockState);
            }
        } else {
            super.playStepSound(pos, blockState);
        }
    }

    @Override
    public boolean isPushable() {
        return getSpiderType() != Type.MOTHER;
    }

    public void buildNest() {
        if (!world.isClient) {
            setSpiderType(Type.MOTHER);
            int r = random.nextInt(SPIDER_MAX_EGGS) + 1;
            
            if (r < 2) {
                return;
            }
            
            for (int i = 0; i < r; i++) {
                TropiSpiderEggEntity egg = TropicraftEntities.TROPI_SPIDER_EGG.create(world);
                egg.setMotherId(getUuid());
                egg.setPosition(getBlockPos().getX() + random.nextFloat(), getBlockPos().getY(), getBlockPos().getZ() + random.nextFloat());
                world.spawnEntity(egg);
                ticksSinceLastEgg = 0;
            }
            
            for (int x = 0; x < 5; x++) {
                for (int z = 0; z < 5; z++) {
                    if (random.nextInt(8) == 0) {
                        BlockPos pos = new BlockPos(getBlockPos().getX() - 2 + x, getBlockPos().getY(),
                                getBlockPos().getZ() - 2 + z);
                        if (world.getBlockState(pos).getBlock().equals(Blocks.AIR) && world.getBlockState(pos.down()).getMaterial().isSolid()) {
                            world.setBlockState(pos, Blocks.COBWEB.getDefaultState());
                        }
                    }
                }
            }
            nestSite = getBlockPos();
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound n) {
        n.putInt("ticks", age);
        n.putByte("spiderType", (byte) getSpiderType().ordinal());
        n.putLong("timeSinceLastEgg", ticksSinceLastEgg);
        super.writeCustomDataToNbt(n);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound n) {
        age = n.getInt("ticks");
        setSpiderType(n.getByte("spiderType"));
        ticksSinceLastEgg = n.getLong("timeSinceLastEgg");
        super.readCustomDataFromNbt(n);
    }
    
    public Type getSpiderType() {
        return Type.VALUES[getDataTracker().get(TYPE)];
    }

    public void setSpiderType(Type type) {
        getDataTracker().set(TYPE, (byte) type.ordinal());
        calculateDimensions();
    }

    public void setSpiderType(byte b) {
        getDataTracker().set(TYPE, b);
        calculateDimensions();
    }

    /*
    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(TropicraftItems.TROPI_SPIDER_SPAWN_EGG.get());
    }
     */
}
