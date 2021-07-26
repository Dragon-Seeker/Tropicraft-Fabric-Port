package net.tropicraftFabric.common.entity.neutral;

import net.tropicraftFabric.common.entity.hostile.TropicraftCreatureEntity;
import net.tropicraftFabric.common.registry.TropicraftItems;
import net.tropicraftFabric.common.sound.Sounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class IguanaEntity extends TropicraftCreatureEntity {

    /** Timer for how much longer the iggy will be enraged */
    private int angerLevel;
    private UUID angerTargetUUID;

    private static final UUID ATTACK_SPEED_BOOST_MODIFIER_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
    private static final EntityAttributeModifier ATTACK_SPEED_BOOST_MODIFIER = new EntityAttributeModifier(ATTACK_SPEED_BOOST_MODIFIER_UUID, "Attacking speed boost", 0.05D, EntityAttributeModifier.Operation.ADDITION);

    public IguanaEntity(EntityType<? extends PathAwareEntity> type, World world) {
        super(type, world);
    }

    /*
    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(TropicraftItems.IGUANA_SPAWN_EGG);
    }
     */

    @Override
    public void setAttacker(@Nullable LivingEntity entity) {
        super.setAttacker(entity);
        if (entity != null) {
            angerTargetUUID = entity.getUuid();
        }

    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0);
    }

    @Override
    protected void initGoals() {
        goalSelector.add(0, new SwimGoal(this));
        goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, false));
        goalSelector.add(5, new GoToWalkTargetGoal(this, 1.0D));
        goalSelector.add(7, new WanderAroundFarGoal(this, 1.0D));
        goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        goalSelector.add(8, new LookAroundGoal(this));
        targetSelector.add(1, new HurtByAggressorGoal(this));
        targetSelector.add(2, new TargetAggressorGoal(this));
    }

    @Override
    public void writeCustomDataToNbt(final NbtCompound compound) {
        super.writeCustomDataToNbt(compound);
        compound.putShort("Anger", (short)angerLevel);

        if (angerTargetUUID != null) {
            compound.putString("HurtBy", angerTargetUUID.toString());
        } else {
            compound.putString("HurtBy", "");
        }
    }

    @Override
    public void readCustomDataFromNbt(final NbtCompound compound) {
        super.readCustomDataFromNbt(compound);
        angerLevel = compound.getShort("Anger");
        String hurtBy = compound.getString("HurtBy");

        if (!hurtBy.isEmpty()) {
            angerTargetUUID = UUID.fromString(hurtBy);
            final PlayerEntity entityplayer = world.getPlayerByUuid(angerTargetUUID);
            setAttacker(entityplayer);

            if (entityplayer != null) {
                attackingPlayer = entityplayer;
                playerHitTimer = getLastAttackedTime();
            }
        }
    }

    @Override
    protected void mobTick() {
        EntityAttributeInstance attribute = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);

        if (this.isAngry()) {
            if (!this.isBaby() && !attribute.hasModifier(ATTACK_SPEED_BOOST_MODIFIER)) {
                attribute.addTemporaryModifier(ATTACK_SPEED_BOOST_MODIFIER);
            }

            --this.angerLevel;
        } else if (attribute.hasModifier(ATTACK_SPEED_BOOST_MODIFIER)) {
            attribute.removeModifier(ATTACK_SPEED_BOOST_MODIFIER);
        }

        if (this.angerLevel > 0 && this.angerTargetUUID != null && this.getAttacker() == null) {
            PlayerEntity entityplayer = this.world.getPlayerByUuid(this.angerTargetUUID);
            this.setAttacker(entityplayer);
            this.attackingPlayer = entityplayer;
            this.playerHitTimer = this.getLastAttackedTime();
        }

        super.mobTick();
    }

    public boolean damage(DamageSource damageSource, float amount) {
        if (isInvulnerableTo(damageSource)) {
            return false;
        } else {
            Entity sourceEntity = damageSource.getAttacker();
            if (sourceEntity instanceof PlayerEntity && !((PlayerEntity)sourceEntity).isCreative() && canSee(sourceEntity)) {
                becomeAngryAt(sourceEntity);
            }

            return super.damage(damageSource, amount);
        }
    }

    private boolean becomeAngryAt(Entity target) {
        angerLevel = 400 + random.nextInt(400);
        if (target instanceof LivingEntity) {
            setAttacker((LivingEntity)target);
        }

        return true;
    }

    public boolean isAngry() {
        return this.angerLevel > 0;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return Sounds.IGGY_LIVING;
    }

    @Override
    protected SoundEvent getHurtSound(final DamageSource damageSource) {
        return Sounds.IGGY_ATTACK;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.IGGY_DEATH;
    }

    static class TargetAggressorGoal extends FollowTargetGoal<PlayerEntity> {
        public TargetAggressorGoal(IguanaEntity iggy) {
            super(iggy, PlayerEntity.class, true);
        }

        public boolean canStart() {
            return ((IguanaEntity)this.mob).isAngry() && super.canStart();
        }
    }

    static class HurtByAggressorGoal extends RevengeGoal {
        public HurtByAggressorGoal(IguanaEntity iguana) {
            super(iguana);
            this.setGroupRevenge(IguanaEntity.class);
        }

        protected void setMobEntityTarget(MobEntity mob, LivingEntity target) {
            if (mob instanceof IguanaEntity && this.mob.canSee(target) && ((IguanaEntity)mob).becomeAngryAt(target)) {
                mob.setTarget(target);
            }

        }
    }
}
