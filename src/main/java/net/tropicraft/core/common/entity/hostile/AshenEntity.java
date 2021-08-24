package net.tropicraft.core.common.entity.hostile;


import net.tropicraft.core.common.entity.passive.EntityKoaBase;
import net.tropicraft.core.common.entity.placeable.AshenMaskEntity;
import net.tropicraft.core.common.entity.ai.ashen.AIAshenChaseAndPickupLostMask;
import net.tropicraft.core.common.entity.ai.ashen.AIAshenShootDart;
import net.tropicraft.core.common.entity.ai.ashen.EntityAIMeleeAndRangedAttack;
import net.tropicraft.core.common.item.AshenMaskItem;
import net.tropicraft.core.common.item.AshenMasks;
import net.tropicraft.core.common.item.BlowGunItem;
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.tropicraft.core.common.registry.TropicraftItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


public class AshenEntity extends TropicraftCreatureEntity implements RangedAttackMob {
    public enum AshenState {
        PEACEFUL,
        LOST_MASK,
        HOSTILE;

        public static final AshenState[] VALUES = values();
    }

    private static final TrackedData<Byte> MASK_TYPE = DataTracker.registerData(AshenEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Byte> ACTION_STATE = DataTracker.registerData(AshenEntity.class, TrackedDataHandlerRegistry.BYTE);

    public AshenMaskEntity maskToTrack;

    public AshenEntity(EntityType<? extends PathAwareEntity> type, World world) {
        super(type, world);
        setActionState(AshenState.HOSTILE);
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        setStackInHand(Hand.OFF_HAND, new ItemStack(TropicraftItems.BLOW_GUN));
        setStackInHand(Hand.MAIN_HAND, new ItemStack(TropicraftItems.DAGGER));
        setMaskType((byte) AshenMasks.VALUES[world.getRandom().nextInt(AshenMasks.VALUES.length)].ordinal());
        setActionState(AshenState.HOSTILE);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        getDataTracker().startTracking(MASK_TYPE, (byte) 0);
        getDataTracker().startTracking(ACTION_STATE, (byte) AshenState.HOSTILE.ordinal());
    }

    @Override
    protected void initGoals() {
        goalSelector.add(1, new SwimGoal(this));
        goalSelector.add(2, new AIAshenChaseAndPickupLostMask(this, 1.0D));
        goalSelector.add(3, new AIAshenShootDart(this));
        goalSelector.add(4, new WanderAroundGoal(this, 1.0D));
        goalSelector.add(5, new EntityAIMeleeAndRangedAttack(this, 1.0D, 20*2, 20*10, 5F));
        goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        goalSelector.add(7, new LookAroundGoal(this));
        targetSelector.add(1, new RevengeGoal(this));
        // TODO: Change predicate in last parameter below?
        targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));

        //TODO: re-enable THE CHASING OF KOA's
        targetSelector.add(3, new FollowTargetGoal<>(this, EntityKoaBase.class, true));
    }

    public static DefaultAttributeContainer.Builder createAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35D);
    }

    public boolean hasMask() {
        return getActionState() != AshenState.LOST_MASK;
    }

    public void setMaskType(byte type) {
        getDataTracker().set(MASK_TYPE, type);
    }

    public byte getMaskType() {
        return getDataTracker().get(MASK_TYPE);
    }

    public void setActionState(final AshenState state) {
        getDataTracker().set(ACTION_STATE, (byte) state.ordinal());
    }

    public AshenState getActionState() {
        return AshenState.VALUES[getActionStateValue()];
    }

    private byte getActionStateValue() {
        return getDataTracker().get(ACTION_STATE);
    }


    @Override
    public void attack(LivingEntity target, float velocity) {
        ItemStack headGear = target.getEquippedStack(EquipmentSlot.HEAD);
        // Don't shoot things wearing ashen masks
        if (headGear.getItem() instanceof AshenMaskItem) {
            return;
        }

        ArrowEntity tippedArrow = BlowGunItem.createArrow(world, this, BlowGunItem.getProjectile());
        double d0 = target.getX() - getX();
        double d1 = target.getBoundingBox().minY + (double)(target.getHeight() / 3.0F) - tippedArrow.getY();
        double d2 = target.getZ() - getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        tippedArrow.setVelocity(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, velocity);

        tippedArrow.setDamage(1);
        tippedArrow.setPunch(0);

        playSound(SoundEvents.ITEM_CROSSBOW_SHOOT, 1.0F, 1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
        world.spawnEntity(tippedArrow);
    }

    @Override
    public boolean damage(DamageSource source, float amt) {
        boolean wasHit = super.damage(source, amt);

        if (!world.isClient) {
            if (hasMask() && wasHit && !source.equals(DamageSource.OUT_OF_WORLD)) {
                dropMask();
            }
        }

        return wasHit;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) { //writeAdditional
        super.writeCustomDataToNbt(nbt);
        nbt.putByte("MaskType", getMaskType());
        nbt.putByte("ActionState", getActionStateValue());
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        setMaskType(nbt.getByte("MaskType"));
        setActionState(AshenState.VALUES[nbt.getByte("ActionState")]);
    }

    public void dropMask() {
        setActionState(AshenState.LOST_MASK);
        maskToTrack = new AshenMaskEntity(TropicraftEntities.ASHEN_MASK, world);
        maskToTrack.setMaskType(getMaskType());
        maskToTrack.updatePositionAndAngles(getX(), getY(), getZ(), this.getYaw(), 0);
        world.spawnEntity(maskToTrack);
    }

    public void pickupMask(AshenMaskEntity mask) {
        setActionState(AshenState.HOSTILE);
        maskToTrack = null;
        setMaskType(mask.getMaskType());
        mask.remove(RemovalReason.DISCARDED);
    }

    /*
    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(TropicraftItems.ASHEN_SPAWN_EGG.get());
    }
     */

}
