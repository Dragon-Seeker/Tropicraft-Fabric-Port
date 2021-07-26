package net.tropicraftFabric.common.entity.egg;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.world.World;
import net.tropicraftFabric.client.packets.StarFishSpawnS2CPacket;
import net.tropicraftFabric.common.entity.underdasea.StarfishEntity;
import net.tropicraftFabric.common.entity.underdasea.StarfishType;
import net.tropicraftFabric.common.registry.TropicraftEntities;

public class StarfishEggEntity extends EchinodermEggEntity {
	private StarfishType starfishType;

	public StarfishEggEntity(final EntityType<? extends StarfishEggEntity> type, World world) {
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

	public Packet<?> createSpawnPacket() {
		return new StarFishSpawnS2CPacket(this);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putByte("StarfishType", (byte) getStarfishType().ordinal());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		setStarfishType(StarfishType.values()[nbt.getByte("StarfishType")]);
	}

	@Override
	public String getEggTexture() {
		return "starfishegg";
	}

	@Override
	public Entity onHatch() {
		StarfishEntity baby = new StarfishEntity(TropicraftEntities.STARFISH, world);
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