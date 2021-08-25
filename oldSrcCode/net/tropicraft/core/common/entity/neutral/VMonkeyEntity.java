package net.tropicraft.core.common.entity.neutral;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.tropicraft.core.common.drinks.Drink;
import net.tropicraft.core.common.entity.ai.vmonkey.*;
import net.tropicraft.core.common.item.CocktailItem;
import org.jetbrains.annotations.Nullable;

public class VMonkeyEntity extends TameableEntity {

    private static final TrackedData<Byte> DATA_FLAGS = DataTracker.registerData(VMonkeyEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final int FLAG_CLIMBING = 1 << 0;

    public static final Predicate<LivingEntity> FOLLOW_PREDICATE = ent -> {
        if (ent == null) return false;
        if (!(ent instanceof PlayerEntity)) return false;

        PlayerEntity player = (PlayerEntity) ent;
        ItemStack heldMain = player.getMainHandStack();
        ItemStack heldOff = player.getOffHandStack();

        if (heldMain.getItem() instanceof CocktailItem) {
            if (CocktailItem.getDrink(heldMain) == Drink.PINA_COLADA) {
                return true;
            }
        }

        if (heldOff.getItem() instanceof CocktailItem) {
            return CocktailItem.getDrink(heldOff) == Drink.PINA_COLADA;
        }

        return false;
    };

    /** Entity this monkey is following around */
    private LivingEntity following;
    private boolean madAboutStolenAlcohol;

    public VMonkeyEntity(EntityType<? extends TameableEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(DATA_FLAGS, (byte) 0);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return TameableEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        goalSelector.add(1, new SwimGoal(this));
        goalSelector.add(3, new MonkeyFollowNearestPinaColadaHolderGoal(this, 1.0D, 2.0F, 10.0F));
        goalSelector.add(3, new PounceAtTargetGoal(this, 0.4F));
        goalSelector.add(3, new MonkeyPickUpPinaColadaGoal(this));
        goalSelector.add(2, new MonkeyStealDrinkGoal(this));
        goalSelector.add(2, new MonkeySitAndDrinkGoal(this));
        goalSelector.add(2, new MonkeyAngryThrowGoal(this));
        goalSelector.add(4, new MonkeySitInChairGoal(this));
        goalSelector.add(4, new SitGoal(this));
        goalSelector.add(6, new MeleeAttackGoal(this, 1.0D, true));
        goalSelector.add(7, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        goalSelector.add(8, new WanderAroundGoal(this, 1.0D));
        goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        goalSelector.add(9, new LookAroundGoal(this));
        targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        targetSelector.add(2, new AttackWithOwnerGoal(this));
        targetSelector.add(3, new RevengeGoal(this));
    }

    @Override
    public void writeCustomDataToNbt(final NbtCompound compound) {
        super.writeCustomDataToNbt(compound);
        compound.putByte("MonkeyFlags", getMonkeyFlags());
    }

    @Override
    public void readCustomDataFromNbt(final NbtCompound compound) {
        super.readCustomDataFromNbt(compound);
        setMonkeyFlags(compound.getByte("MonkeyFlags"));
    }

    public LivingEntity getFollowing() {
        return following;
    }

    public void setFollowing(@Nullable final LivingEntity following) {
        this.following = following;
    }

    public boolean selfHoldingDrink(Drink drink) {
        ItemStack heldItem = getMainHandStack();
        if (heldItem.getItem() instanceof CocktailItem) {
            return CocktailItem.getDrink(heldItem) == drink;
        }
        return false;
    }

    private void setMonkeyFlags(final byte flags) {
        getDataTracker().set(DATA_FLAGS, flags);
    }

    private byte getMonkeyFlags() {
        return getDataTracker().get(DATA_FLAGS);
    }

    public boolean isClimbing() {
        return getMonkeyFlag(FLAG_CLIMBING);
    }

    private void setClimbing(boolean state) {
        setMonkeyFlag(FLAG_CLIMBING, state);
    }

    public void setMonkeyFlag(int id, boolean flag) {
        if (flag) {
            dataTracker.set(DATA_FLAGS, (byte)(dataTracker.get(DATA_FLAGS) | id));
        } else {
            dataTracker.set(DATA_FLAGS, (byte)(dataTracker.get(DATA_FLAGS) & ~id));
        }
    }

    private boolean getMonkeyFlag(int flag) {
        return (dataTracker.get(DATA_FLAGS) & flag) != 0;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (isTamed()) {
            if (isOwner(player) && !world.isClient) {
                this.setSitting(!isSitting());
                jumping = false;
                navigation.stop();
                setTarget(null);
                setAttacking(false);
            }
        } else if (!stack.isEmpty() && isBreedingItem(stack)) {
            if (!player.abilities.creativeMode) {
                stack.decrement(1);
            }

            if (!world.isClient) {
                if (random.nextInt(3) == 0) {
                    setTamed(true);
                    navigation.stop();
                    setTarget(null);
                    this.setSitting(true);
                    setHealth(20.0F);
                    setOwnerUuid(player.getUuid());
                    world.sendEntityStatus(this, (byte) 7);
                } else {
                    world.sendEntityStatus(this, (byte) 6);
                }
            }

            return ActionResult.PASS;
        }

        return super.interactMob(player, hand);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return CocktailItem.getDrink(stack) == Drink.PINA_COLADA;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public boolean canAttackWithOwner(LivingEntity target, LivingEntity owner) {
        // Only attack players, and only when not tamed
        // NOTE: Maybe we want to attack other players though?
        return !isTamed() && target.getType() == EntityType.PLAYER;
    }

    @Override
    public boolean tryAttack(Entity entity) {
        boolean damaged = entity.damage(DamageSource.mob(this), (float) getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getValue());

        if (damaged) {
            dealDamage(this, entity);
        }

        return damaged;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getAttacker();
            this.setSitting(false);

            if (entity != null && entity.getType() != EntityType.PLAYER && !(entity instanceof ArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.damage(source, amount);
        }
    }
    /*
    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(TropicraftItems.V_MONKEY_SPAWN_EGG.get());
    }
     */

    public boolean isMadAboutStolenAlcohol() {
        return madAboutStolenAlcohol;
    }

    public void setMadAboutStolenAlcohol(boolean madAboutStolenAlcohol) {
        this.madAboutStolenAlcohol = madAboutStolenAlcohol;
    }
}
