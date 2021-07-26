package net.tropicraftFabric.common.entity.egg;

import net.tropicraftFabric.common.entity.underdasea.SeaUrchinEntity;
import net.tropicraftFabric.common.registry.TropicraftEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class SeaUrchinEggEntity extends EchinodermEggEntity {
	public SeaUrchinEggEntity(final EntityType<? extends SeaUrchinEggEntity> type, World world) {
		super(type, world);
	}

	@Override
	public String getEggTexture() {
		return "seaurchinegg";
	}

	@Override
	public Entity onHatch() {
		return new SeaUrchinEntity(TropicraftEntities.SEA_URCHIN, world);
	}
	/*
	@Override
	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(TropicraftItems.SEA_URCHIN_SPAWN_EGG.get());
	}
	 */
}
