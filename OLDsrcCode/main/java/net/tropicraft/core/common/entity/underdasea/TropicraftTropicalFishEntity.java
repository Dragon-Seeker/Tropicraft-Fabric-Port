package net.tropicraft.core.common.entity.underdasea;

import net.tropicraft.core.common.registry.TropicraftItems;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class TropicraftTropicalFishEntity extends SchoolingFishEntity implements IAtlasFish {

    enum FishType {
        CLOWNFISH(0),
        QUEEN_ANGELFISH(1),
        YELLOW_TANG(2),
        BUTTERFLY_FISH(3),
        GEOPHAGUS_SURINAMENSIS(4),
        BETTA_FISH(5),
        REGAL_TANG(6),
        ROYAL_GAMMA(7);

        public static final FishType[] VALUES = values();
        private final int id;

        FishType(int id) {
            this.id = id;
        }

        private static FishType getById(final int id) {
            for (final FishType type : VALUES) {
                if (type.id == id) {
                    return type;
                }
            }
            return CLOWNFISH;
        }

        private static FishType getRandomType(final Random rand) {
            return VALUES[rand.nextInt(FishType.values().length)];
        }
    }

    private static final TrackedData<Byte> DATA_FISH_TYPE = DataTracker.registerData(TropicraftTropicalFishEntity.class, TrackedDataHandlerRegistry.BYTE);

    public TropicraftTropicalFishEntity(EntityType<? extends SchoolingFishEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return SchoolingFishEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 5.0);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(DATA_FISH_TYPE, (byte) 0);
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficultyInstance, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound nbt) {
        entityData = super.initialize(world, difficultyInstance, spawnReason, entityData, nbt);
        if (nbt != null && nbt.contains("BucketVariantTag", 3)) {
            setFishType(FishType.getById(nbt.getInt("BucketVariantTag")));
        } else {
            setFishType(FishType.getRandomType(random));
        }
        return entityData;
    }

    public FishType getFishType() {
        return FishType.VALUES[dataTracker.get(DATA_FISH_TYPE)];
    }

    public void setFishType(final FishType type) {
        dataTracker.set(DATA_FISH_TYPE, (byte) type.ordinal());
    }

    @Override
    public int getMaxGroupSize() {
        return 24;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(TropicraftItems.TROPICAL_FISH_BUCKET);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SALMON_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SALMON_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource dmgSrc) {
        return SoundEvents.ENTITY_SALMON_HURT;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_SALMON_FLOP;
    }

    @Override
    public int getAtlasSlot() {
        return getFishType().ordinal();
    }
    /*
    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(TropicraftItems.TROPICAL_FISH_SPAWN_EGG);
    }
     */

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!stack.isEmpty() && stack.getItem() == TropicraftItems.FISHING_NET) {
            final int firstHotbarSlot = 0;
            int bucketSlot = -1;
            for (int i = 0; i < PlayerInventory.getHotbarSize(); i++) {
                ItemStack s = player.getInventory().getStack(firstHotbarSlot + i);
                if (isFishHolder(s)) {
                    bucketSlot = firstHotbarSlot + i;
                    break;
                }
            }

            if (bucketSlot == -1 && isFishHolder(player.getOffHandStack())) {
                bucketSlot = 36;
            }

            if (bucketSlot >= 0) {
                ItemStack fishHolder = player.getInventory().getStack(bucketSlot);
                if (fishHolder.getItem() == Items.WATER_BUCKET) {
                    fishHolder = new ItemStack(TropicraftItems.TROPICAL_FISH_BUCKET);
                    player.getInventory().setStack(bucketSlot, fishHolder);
                }
                copyDataToStack(fishHolder);
                player.swingHand(hand);
                world.playSound(player, getBlockPos(), SoundEvents.ENTITY_GENERIC_SWIM, SoundCategory.PLAYERS, 0.25f, 1f + (random.nextFloat() * 0.4f));
                remove(RemovalReason.DISCARDED);
                return ActionResult.SUCCESS;
            }
        }

        return super.interactMob(player, hand);
    }

    @Override
    public void writeCustomDataToNbt(final NbtCompound compound) {
        super.writeCustomDataToNbt(compound);
        compound.putInt("FishType", getFishType().id);
    }

    @Override
    public void readCustomDataFromNbt(final NbtCompound compound) {
        super.readCustomDataFromNbt(compound);
        setFishType(FishType.getById(compound.getInt("FishType")));
    }

    /**
     * Add extra data to the bucket that just picked this fish up
     */
    @Override
    public void copyDataToStack(final ItemStack bucket) {
        super.copyDataToStack(bucket);
        NbtCompound compoundnbt = bucket.getOrCreateNbt();
        compoundnbt.putInt("BucketVariantTag", getFishType().id);
    }

    private boolean isFishHolder(ItemStack stack) {
        return !stack.isEmpty() && (stack.getItem() == Items.WATER_BUCKET || stack.getItem() == TropicraftItems.TROPICAL_FISH_BUCKET);
    }
}
