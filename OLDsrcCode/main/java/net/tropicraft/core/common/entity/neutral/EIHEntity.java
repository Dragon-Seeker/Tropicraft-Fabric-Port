package net.tropicraft.core.common.entity.neutral;

import net.tropicraft.core.common.entity.hostile.TropicraftCreatureEntity;
import net.tropicraft.core.common.registry.TropicraftBlocks;
import net.tropicraft.core.common.sound.Sounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class EIHEntity extends TropicraftCreatureEntity {

    private static final TrackedData<Byte> STATE = DataTracker.registerData(EIHEntity.class, TrackedDataHandlerRegistry.BYTE);
    public int FLAG_SLEEP = 1 << 0;
    public int FLAG_AWARE = 1 << 1;
    public int FLAG_ANGRY = 1 << 2;

    public EIHEntity(EntityType<? extends PathAwareEntity> type, World world) {
        super(type, world);
        experiencePoints = 10;
    }
    /*
    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(TropicraftItems.EIH_SPAWN_EGG);
    }
     */

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        getDataTracker().startTracking(STATE, (byte) 0);
    }

    public byte getState() {
        return getDataTracker().get(STATE);
    }

    private void setState(final byte state) {
        getDataTracker().set(STATE, state);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 100.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0);
    }

    @Override
    public void initGoals() {
        goalSelector.add(0, new SwimGoal(this));
        goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, false) {
            @Override
            public boolean canStart() {
                if (!isAngry()) return false;
                return super.canStart();
            }
        });

        PounceAtTargetGoal leap = new PounceAtTargetGoal(this, 0.4F);
        //leap.setMutexFlags();
        goalSelector.add(3, leap);

        goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D) {
            @Override
            public boolean canStart() {
                if (!isAngry()) return false;
                return super.canStart();
            }
        });


        goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
//        goalSelector.addGoal(8, new LookRandomlyGoal(this));
        targetSelector.add(1, new RevengeGoal(this));
        targetSelector.add(2, new TargetAggressorGoal(this));
    }

    @Override
    public void writeCustomDataToNbt(final NbtCompound compound) {
        super.writeCustomDataToNbt(compound);
        compound.putByte("State", getState());
    }

    @Override
    public void readCustomDataFromNbt(final NbtCompound compound) {
        super.readCustomDataFromNbt(compound);
        setState(compound.getByte("State"));
    }

    @Override
    public int getLimitPerChunk() {
        return 1;
    }

    @Override
    public void baseTick() {
        super.baseTick();

        if (isAsleep()) {
            setVelocity(Vec3d.ZERO);
        }

        if (!isAsleep()) {
            prevYaw = this.getYaw();
            prevPitch = this.getPitch();
        }

        if (age % 20 == 0) {
            final LivingEntity attackTarget = getTarget();
            if (attackTarget == null) {
                final PlayerEntity closestPlayer = world.getClosestPlayer(this, 10);
                if (closestPlayer != null && !closestPlayer.getAbilities().creativeMode && !closestPlayer.isSpectator()) {
                    setTarget(closestPlayer);
                }
            } else if (distanceTo(attackTarget) > 16) {
                setTarget(null);
                setAwake(false);
                setImmobile(true);
                setAngry(false);
            }

            if (attackTarget != null && !isNavigating() && !isAngry()) {
                if (attackTarget instanceof PlayerEntity) {
                    final PlayerEntity player = (PlayerEntity) attackTarget;
                    if (!player.getAbilities().creativeMode && !player.isSpectator()) {
                        final float distance = distanceTo(player);
                        if (distance < 10F) {
                            setAwake(true);
                            final ItemStack itemstack = player.getInventory().getMainHandStack();

                            if (!itemstack.isEmpty()) {
                                if (isAware() && itemstack.getItem() == TropicraftBlocks.CHUNK.asItem()) {
                                    setAngry(true);
                                    setImmobile(false);
                                }
                            }
                        }

                        if (distanceTo(player) < 3 && world.getDifficulty() != Difficulty.PEACEFUL) {
                            setAwake(false);
                            setImmobile(false);
                            setAngry(true);
                        }
                    } else {
                        setImmobile(true);
                        setAngry(false);
                        setAwake(false);
                        setVelocity(0D, -.1D, 0D);
                        setRotation(prevYaw, prevPitch);
                    }
                }
            }

            if (isAsleep()) {
                setRotation(prevYaw, prevPitch);
            } else {
                setAwake(false);
            }
        }
    }

    public boolean isAngry() {
        return getEIHFlag(FLAG_ANGRY);
    }

    public void setAngry(final boolean angry) {
        setEIHFlag(FLAG_ANGRY, angry);
    }

    public boolean isAware() {
        return getEIHFlag(FLAG_AWARE);
    }

    public void setAwake(final boolean aware) {
        setEIHFlag(FLAG_AWARE, aware);
    }

    public boolean isAsleep() {
        return getEIHFlag(FLAG_SLEEP);
    }

    public void setImmobile(final boolean asleep) {
        setEIHFlag(FLAG_SLEEP, asleep);
    }

    public void setEIHFlag(int id, boolean flag) {
        if (flag) {
            this.dataTracker.set(STATE, (byte)(this.dataTracker.get(STATE) | id));
        } else {
            this.dataTracker.set(STATE, (byte)(this.dataTracker.get(STATE) & ~id));
        }
    }

    private boolean getEIHFlag(int id) {
        return (this.dataTracker.get(STATE) & id) != 0;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (isAware()) {
            return random.nextInt(10) == 0 ? Sounds.HEAD_MED : null;
        } else if (isAngry()) {
            return random.nextInt(10) == 0 ? Sounds.HEAD_SHORT : null;
        } else {
            return null;
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return Sounds.HEAD_PAIN;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.HEAD_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public boolean damage(final DamageSource source, final float amount) {
        if (source.equals(DamageSource.OUT_OF_WORLD)) {
            return super.damage(source, amount);
        }

        if (source.getSource() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) source.getSource();
            if (player.getAbilities().creativeMode || player.isSpectator()) {
                return super.damage(source, amount);
            }
            ItemStack heldItemStack = player.getMainHandStack();
            Item heldItem = heldItemStack.getItem();


            //int tempMiningLevel = heldItem instanceof PickaxeItem toolItem ? toolItem.getMaterial().getMiningLevel() : 0;

            int tempMiningLevel = heldItem instanceof PickaxeItem ? ((PickaxeItem) heldItem).getMaterial().getMiningLevel() : 0;

            if (!heldItemStack.isEmpty() && tempMiningLevel >= 1) {
                System.out.println("ATTACKING THE HEAD: SUCCESS");
                return super.damage(source, amount);
            } else {
                System.out.println("ATTACKING THE HEAD: FAILED!!");
                playSound(Sounds.HEAD_LAUGHING, getSoundVolume(), getSoundPitch());
                setAttacker(player);
            }

            setAngry(true);
            setImmobile(false);
        }

        return true;
    }

    private static class TargetAggressorGoal extends FollowTargetGoal<PlayerEntity> {
        public TargetAggressorGoal(EIHEntity eih) {
            super(eih, PlayerEntity.class, true);
        }

        public boolean canStart() {
            return ((EIHEntity) mob).isAngry() && super.canStart();
        }
    }
}
