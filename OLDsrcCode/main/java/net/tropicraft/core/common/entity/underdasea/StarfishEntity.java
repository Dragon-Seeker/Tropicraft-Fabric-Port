package net.tropicraft.core.common.entity.underdasea;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
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

	private static final TrackedData<Byte> DATA_STARFISH_TYPE = DataTracker.registerData(StarfishEntity.class, TrackedDataHandlerRegistry.BYTE);

	public StarfishEntity(final EntityType<? extends StarfishEntity> type, World world) {
		super(type, world);
		experiencePoints = 5;
	}

	@Override
	@Nullable
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficultyInstance, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound nbt) {
		setStarfishType(StarfishType.values()[random.nextInt(StarfishType.values().length)]);
		return super.initialize(world, difficultyInstance, spawnReason, entityData, nbt);
	}

	@Override
	public void initDataTracker() {
		super.initDataTracker();
		getDataTracker().startTracking(DATA_STARFISH_TYPE, (byte) 0);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0);
	}
	
	public StarfishType getStarfishType() {
		return StarfishType.values()[dataTracker.get(DATA_STARFISH_TYPE)];
	}
	
	public void setStarfishType(StarfishType starfishType) {
		dataTracker.set(DATA_STARFISH_TYPE, (byte) starfishType.ordinal());
	}
	
	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putByte("StarfishType", (byte) getStarfishType().ordinal());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("StarfishType")) {
			setStarfishType(StarfishType.values()[nbt.getByte("StarfishType")]);
		} else {
			setStarfishType(StarfishType.RED);
		}
	}

	public Packet<?> createSpawnPacket() {
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
		StarfishEggEntity entity = new StarfishEggEntity(TropicraftEntities.STARFISH_EGG, world);
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
	public void onDeath(DamageSource d) {
		super.onDeath(d);
		// TODO replace with loot table
		if (!world.isClient) {
			dropStack(new ItemStack(TropicraftItems.STARFISH), 0);
		}
	}
	/*
	@Override
	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(TropicraftItems.STARFISH);
	}

	 */
}
