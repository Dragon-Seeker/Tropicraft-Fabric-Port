package net.tropicraft.core.common.entity.underdasea;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.tropicraft.core.client.packets.StarFishSpawnS2CPacket;
import net.tropicraft.core.common.entity.egg.EggEntity;
import net.tropicraft.core.common.entity.egg.StarfishEggEntity;
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.tropicraft.core.common.registry.TropicraftItems;
import org.jetbrains.annotations.Nullable;


public class StarfishEntity extends EchinodermEntity {
	public static final float BABY_WIDTH = 0.25f;
	public static final float ADULT_WIDTH = 1f;
	public static final float BABY_HEIGHT = 0.1f;
	public static final float ADULT_HEIGHT = 0.2f;
	public static final float BABY_YOFFSET = 0.03125f;
	public static final float ADULT_YOFFSET = 0.03125f;

	private static final EntityDataAccessor<Byte> DATA_STARFISH_TYPE = SynchedEntityData.defineId(StarfishEntity.class, EntityDataSerializers.BYTE);

	public StarfishEntity(final EntityType<? extends StarfishEntity> type, Level world) {
		super(type, world);
		xpReward = 5;
	}

	@Override
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficultyInstance, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag nbt) {
		setStarfishType(StarfishType.values()[random.nextInt(StarfishType.values().length)]);
		return super.finalizeSpawn(world, difficultyInstance, spawnReason, entityData, nbt);
	}

	@Override
	public void defineSynchedData() {
		super.defineSynchedData();
		getEntityData().define(DATA_STARFISH_TYPE, (byte) 0);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return createMobAttributes()
				.add(Attributes.MAX_HEALTH, 10.0);
	}
	
	public StarfishType getStarfishType() {
		return StarfishType.values()[entityData.get(DATA_STARFISH_TYPE)];
	}
	
	public void setStarfishType(StarfishType starfishType) {
		entityData.set(DATA_STARFISH_TYPE, (byte) starfishType.ordinal());
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putByte("StarfishType", (byte) getStarfishType().ordinal());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt) {
		super.readAdditionalSaveData(nbt);
		if (nbt.contains("StarfishType")) {
			setStarfishType(StarfishType.values()[nbt.getByte("StarfishType")]);
		} else {
			setStarfishType(StarfishType.RED);
		}
	}

	public Packet<?> getAddEntityPacket() {
		return new StarFishSpawnS2CPacket(this);
	}

	/*
	@Override
	public void writeSpawnData(PacketByteBuf buffer) {
		buffer.writeByte(getStarfishType().ordinal());
	}

	@Override
	public void readSpawnData(PacketByteBuf additionalData) {
		setStarfishType(StarfishType.values()[additionalData.readByte()]);
	}

	 */

	@Override
	public EggEntity createEgg() {
		StarfishEggEntity entity = new StarfishEggEntity(TropicraftEntities.STARFISH_EGG, level);
		entity.setStarfishType(getStarfishType());
		return entity;
	}

	@Override
	public float getBabyWidth() {
		return BABY_WIDTH;
	}

	@Override
	public float getAdultWidth() {
		return ADULT_WIDTH;
	}

	@Override
	public float getBabyHeight() {
		return BABY_HEIGHT;
	}

	@Override
	public float getAdultHeight() {
		return ADULT_HEIGHT;
	}

	@Override
	public float getBabyYOffset() {
		return BABY_YOFFSET;
	}

	@Override
	public float getAdultYOffset() {
		return ADULT_YOFFSET;
	}

	@Override
	public boolean isPotentialMate(EchinodermEntity other) {
		return super.isPotentialMate(other) && ((StarfishEntity) other).getStarfishType() == getStarfishType();
	}
	
	@Override
	public void die(DamageSource d) {
		super.die(d);
		// TODO replace with loot table
		if (!level.isClientSide) {
			spawnAtLocation(new ItemStack(TropicraftItems.STARFISH), 0);
		}
	}
	/*
	@Override
	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(TropicraftItems.STARFISH);
	}

	 */
}
