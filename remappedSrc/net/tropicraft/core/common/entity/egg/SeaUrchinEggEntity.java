package net.tropicraft.core.common.entity.egg;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.tropicraft.core.common.entity.underdasea.SeaUrchinEntity;
import net.tropicraft.core.common.registry.TropicraftEntities;

public class SeaUrchinEggEntity extends EchinodermEggEntity {
	public SeaUrchinEggEntity(final EntityType<? extends SeaUrchinEggEntity> type, Level world) {
		super(type, world);
	}

	@Override
	public String getEggTexture() {
		return "seaurchinegg";
	}

	@Override
	public Entity onHatch() {
		return new SeaUrchinEntity(TropicraftEntities.SEA_URCHIN, level);
	}
	/*
	@Override
	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(TropicraftItems.SEA_URCHIN_SPAWN_EGG.get());
	}
	 */
}
