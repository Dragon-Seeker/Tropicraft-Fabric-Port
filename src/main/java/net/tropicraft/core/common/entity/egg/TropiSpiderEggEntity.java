package net.tropicraft.core.common.entity.egg;

import net.tropicraft.core.common.entity.hostile.TropiSpiderEntity;
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class TropiSpiderEggEntity extends EggEntity {

	protected static final TrackedData<Optional<UUID>> MOTHER_UNIQUE_ID = DataTracker.registerData(TropiSpiderEggEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

	public TropiSpiderEggEntity(final EntityType<? extends EggEntity> type, World world) {
		super(type, world);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		dataTracker.startTracking(MOTHER_UNIQUE_ID, Optional.empty());
	}

	@Nullable
	public UUID getMotherId() {
		return dataTracker.get(MOTHER_UNIQUE_ID).orElse(null);
	}

	public void setMotherId(@Nullable UUID uuid) {
		dataTracker.set(MOTHER_UNIQUE_ID, Optional.ofNullable(uuid));
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		if (getMotherId() == null) {
			nbt.putString("MotherUUID", "");
		} else {
			nbt.putString("MotherUUID", getMotherId().toString());
		}
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		String motherUUID = "";
		if (nbt.contains("MotherUUID", 8)) {
			motherUUID = nbt.getString("MotherUUID");
		}

		if (!motherUUID.isEmpty()) {
			setMotherId(UUID.fromString(motherUUID));
		}
	}

	@Override
	public boolean shouldEggRenderFlat() {
		return false;
	}

	@Override
	public String getEggTexture() {
		return "spideregg";
	}

	@Override
	public Entity onHatch() {
		if (world instanceof ServerWorld && getMotherId() != null) {
			final ServerWorld serverWorld = (ServerWorld) world;
			final Entity e = serverWorld.getEntity(getMotherId());

			if (e instanceof TropiSpiderEntity) {
				return TropiSpiderEntity.haveBaby((TropiSpiderEntity) e);
			}
		}
		return TropicraftEntities.TROPI_SPIDER.create(world);
	}

	@Override
	public int getHatchTime() {
		return 2000;
	}

	@Override
	public int getPreHatchMovement() {
		return 20;
	}
	/*
	@Override
	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(TropicraftItems.TROPI_SPIDER_SPAWN_EGG.get());
	}
	 */
}
