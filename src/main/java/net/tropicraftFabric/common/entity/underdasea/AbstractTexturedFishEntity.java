package net.tropicraftFabric.common.entity.underdasea;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


public abstract class AbstractTexturedFishEntity extends FishEntity {
    private static final TrackedData<String> TEXTURE_NAME = DataTracker.registerData(AbstractTexturedFishEntity.class, TrackedDataHandlerRegistry.STRING);

    public AbstractTexturedFishEntity(EntityType<? extends FishEntity> type, World world) {
        super(type, world);
    }

    abstract String getRandomTexture();
    abstract String getDefaultTexture();

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(TEXTURE_NAME, getDefaultTexture());
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficultyInstance, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound nbt) {
        setTexture(getRandomTexture());
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

    public void setTexture(final String textureName) {
        getDataTracker().set(TEXTURE_NAME, textureName);
    }

    public String getTexture() {
        return getDataTracker().get(TEXTURE_NAME);
    }
}
