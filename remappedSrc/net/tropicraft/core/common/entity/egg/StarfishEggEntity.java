package net.tropicraft.core.common.entity.egg;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.tropicraft.core.client.packets.StarFishSpawnS2CPacket;
import net.tropicraft.core.common.entity.underdasea.StarfishEntity;
import net.tropicraft.core.common.entity.underdasea.StarfishType;
import net.tropicraft.core.common.registry.TropicraftEntities;

public class StarfishEggEntity extends EchinodermEggEntity {
	private StarfishType starfishType;

	public StarfishEggEntity(final EntityType<? extends StarfishEggEntity> type, Level world) {
		super(type, world);
		starfishType = StarfishType.values()[random.nextInt(StarfishType.values().length)];
	}

	public StarfishType getStarfishType() {
		return starfishType;
	}

	public void setStarfishType(StarfishType starfishType) {
		this.starfishType = starfishType;
	}

	/*
	@Override
	public void writeSpawnData(PacketByteBuf buffer) {
		buffer.writeByte(starfishType.ordinal());
	}

	@Override
	public void readSpawnData(PacketByteBuf additionalData) {
		starfishType = StarfishType.values()[additionalData.readByte()];
	}

	 */

	public Packet<?> getAddEntityPacket() {
		return new StarFishSpawnS2CPacket(this);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt) {
		super.addAdditionalSaveData(nbt);
		nbt.putByte("StarfishType", (byte) getStarfishType().ordinal());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt) {
		super.readAdditionalSaveData(nbt);
		setStarfishType(StarfishType.values()[nbt.getByte("StarfishType")]);
	}

	@Override
	public String getEggTexture() {
		return "starfishegg";
	}

	@Override
	public Entity onHatch() {
		StarfishEntity baby = new StarfishEntity(TropicraftEntities.STARFISH, level);
		baby.setBaby();
		baby.setStarfishType(starfishType);
		return baby;
	}

	/*
	@Override
	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(TropicraftItems.STARFISH);
	}
	 */
}