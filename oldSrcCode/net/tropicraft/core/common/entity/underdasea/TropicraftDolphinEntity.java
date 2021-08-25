package net.tropicraft.core.common.entity.underdasea;

import net.tropicraft.core.common.sound.Sounds;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TropicraftDolphinEntity extends DolphinEntity {

    private static final TrackedData<Boolean> MOUTH_OPEN = DataTracker.registerData(TropicraftDolphinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<String> TEXTURE_NAME = DataTracker.registerData(TropicraftDolphinEntity.class, TrackedDataHandlerRegistry.STRING);

    public TropicraftDolphinEntity(EntityType<? extends TropicraftDolphinEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(MOUTH_OPEN, false);
        dataTracker.startTracking(TEXTURE_NAME, "dolphin");
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficultyInstance, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound nbt) {
        setTexture(random.nextInt(50) == 0 ? "special_dolphin" : "dolphin");
        return super.initialize(world, difficultyInstance, spawnReason, entityData, nbt);
    }

    public void setTexture(final String textureName) {
        getDataTracker().set(TEXTURE_NAME, textureName);
    }

    public String getTexture() {
        return getDataTracker().get(TEXTURE_NAME);
    }

    public void setMouthOpen(boolean b) {
        getDataTracker().set(MOUTH_OPEN, b);
    }

    public boolean getMouthOpen() {
        return getDataTracker().get(MOUTH_OPEN);
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
    public void tick() {
        super.tick();
        if (!world.isClient) {
            if (ambientSoundChance < -(getMinAmbientSoundDelay() - 20)) {
                if (age % 3 > 1) {
                    if (!getMouthOpen()) {
                        setMouthOpen(true);
                    }
                } else if (getMouthOpen()) {
                    setMouthOpen(false);
                }
            } else if (getMouthOpen()) {
                setMouthOpen(false);
            }
        }
    }

    @Override
    public int getMinAmbientSoundDelay() {
        return 300;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return Sounds.DOLPHIN;
    }

    /*
    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(TropicraftItems.DOLPHIN_SPAWN_EGG.get());
    }
     */
}
