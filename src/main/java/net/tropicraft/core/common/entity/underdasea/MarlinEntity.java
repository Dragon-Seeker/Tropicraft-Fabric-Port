package net.tropicraft.core.common.entity.underdasea;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


public class MarlinEntity extends FishEntity {

    private static final TrackedData<String> TEXTURE_NAME = DataTracker.registerData(MarlinEntity.class, TrackedDataHandlerRegistry.STRING);

    public MarlinEntity(EntityType<? extends FishEntity> type, World world) {
        super(type, world);
        experiencePoints = 5;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(TEXTURE_NAME, "marlin");
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return FishEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 5.0);
    }
    
    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        return ActionResult.PASS;
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficultyInstance, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound nbt) {
        setTexture(random.nextInt(50) == 0 ? "purple_marlin" : "marlin");
        return super.initialize(world, difficultyInstance, spawnReason, entityData, nbt);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("Texture", getTexture());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        setTexture(nbt.getString("Texture"));
    }

    @Override
    public ItemStack getBucketItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return null;
    }

    public void setTexture(final String textureName) {
        getDataTracker().set(TEXTURE_NAME, textureName);
    }

    public String getTexture() {
        return getDataTracker().get(TEXTURE_NAME);
    }

    /*
    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(TropicraftItems.MARLIN_SPAWN_EGG.get());
    }
     */
}
