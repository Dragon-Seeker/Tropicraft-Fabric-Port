package net.tropicraft.core.common.entity.underdasea;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SeahorseEntity extends AbstractTexturedFishEntity {
	private final static String[] SEAHORSE_TEXTURE_NAMES = new String[] {"razz", "blue", "cyan", "yellow", "green", "orange"};

	public SeahorseEntity(EntityType<? extends SeahorseEntity> type, World world) {
		super(type, world);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return createFishAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0);
	}

	@Override
	protected ActionResult interactMob(PlayerEntity player, Hand hand) {
		return ActionResult.PASS;
	}

	@Override
	String getRandomTexture() {
		return SEAHORSE_TEXTURE_NAMES[random.nextInt(SEAHORSE_TEXTURE_NAMES.length)];
	}

	@Override
	String getDefaultTexture() {
		return SEAHORSE_TEXTURE_NAMES[0];
	}

	@Override
	public ItemStack getBucketItem() {
		return ItemStack.EMPTY;
	}

	@Override
	protected SoundEvent getFlopSound() {
		return null;
	}

	/*
	@Override
	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(TropicraftItems.SEAHORSE_SPAWN_EGG.get());
	}
	 */
}
