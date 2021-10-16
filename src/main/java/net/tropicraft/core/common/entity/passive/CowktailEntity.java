package net.tropicraft.core.common.entity.passive;

import net.tropicraft.core.common.drinks.Drink;
import net.tropicraft.core.common.item.CocktailItem;
import net.tropicraft.core.common.registry.TropicraftBlocks;
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.tropicraft.core.common.registry.TropicraftItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CowktailEntity extends Cow implements Shearable {
	private static final EntityDataAccessor<String> COWKTAIL_TYPE = SynchedEntityData.defineId(CowktailEntity.class, EntityDataSerializers.STRING);

	public CowktailEntity(EntityType<? extends CowktailEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	@Override
	public float getWalkTargetValue(BlockPos pos, LevelReader worldIn) {
		return worldIn.getBlockState(pos.below()).getBlock() == Blocks.MYCELIUM ? 10.0F : worldIn.getBrightness(pos) - 0.5F;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(COWKTAIL_TYPE, Type.IRIS.name);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (itemstack.getItem() == TropicraftItems.BAMBOO_MUG && !this.isBaby()) {
			if (player.getAbilities().instabuild) {
				itemstack.shrink(1);
			}

			final List<CocktailItem> cocktails = new ArrayList(TropicraftItems.COCKTAILS.values());
			// Remove generic cocktail from cowktail
			cocktails.removeIf(cocktail -> player.getMainHandItem().getItem() instanceof CocktailItem && cocktail.getDrink() == Drink.COCKTAIL);
			final ItemStack cocktailItem = new ItemStack(cocktails.get(random.nextInt(cocktails.size())));

			if (itemstack.isEmpty()) {
				player.setItemInHand(hand, cocktailItem);
			} else if (!player.getInventory().add(cocktailItem)) {
				player.drop(cocktailItem, false);
			}

			this.playSound(SoundEvents.MOOSHROOM_MILK_SUSPICIOUSLY, 1.0F, 1.0F);
			return InteractionResult.SUCCESS;
		}

		return super.mobInteract(player, hand);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Type", this.getCowktailType().name);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setCowktailType(Type.getTypeByName(compound.getString("Type")));
	}

	private void setCowktailType(Type typeIn) {
		this.entityData.set(COWKTAIL_TYPE, typeIn.name);
	}

	public Type getCowktailType() {
		return Type.getTypeByName(this.entityData.get(COWKTAIL_TYPE));
	}

	@Override
	public CowktailEntity getBreedOffspring(ServerLevel world, AgeableMob ageable) {
		CowktailEntity child = TropicraftEntities.COWKTAIL.create(this.level);
		child.setCowktailType(this.getOffspringType((CowktailEntity)ageable));
		return child;
	}

	private Type getOffspringType(CowktailEntity p_213445_1_) {
		Type CowktailEntity$type = this.getCowktailType();
		Type CowktailEntity$type1 = p_213445_1_.getCowktailType();
		Type CowktailEntity$type2;
		if (CowktailEntity$type == CowktailEntity$type1 && this.random.nextInt(1024) == 0) {
			CowktailEntity$type2 = Type.getRandomType(random);
		} else {
			CowktailEntity$type2 = this.random.nextBoolean() ? CowktailEntity$type : CowktailEntity$type1;
		}

		return CowktailEntity$type2;
	}



	@Override
	public boolean readyForShearing() {
		return !this.isBaby();
	}

	@NotNull
	@Override
	public void shear(SoundSource shearedSoundCategory) {
		List<ItemStack> ret = new ArrayList<>();
		this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(0.5D), this.getZ(), 0.0D, 0.0D, 0.0D);
		if (!this.level.isClientSide) {
			this.discard();
			Cow cowentity = EntityType.COW.create(this.level);
			cowentity.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
			cowentity.setHealth(this.getHealth());
			cowentity.yBodyRot = this.yBodyRot;
			if (this.hasCustomName()) {
				cowentity.setCustomName(this.getCustomName());
				cowentity.setCustomNameVisible(this.isCustomNameVisible());
			}
			this.level.addFreshEntity(cowentity);
			for(int i = 0; i < 5; ++i) {
				//ret.add(new ItemStack(this.getCowktailType().renderState.getBlock()));
				this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(1.0D), this.getZ(), new ItemStack(this.getCowktailType().renderState.getBlock())));
			}

			this.playSound(SoundEvents.MOOSHROOM_SHEAR, 1.0F, 1.0F);
		}
		//return ret;
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficultyInstance, MobSpawnType spawnReason, @Nullable SpawnGroupData data, @Nullable CompoundTag nbt) {
		setCowktailType(Type.getRandomType(random));
		return super.finalizeSpawn(world, difficultyInstance, spawnReason, data, nbt);
	}


	/*
	@Override
	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(TropicraftItems.COWKTAIL_SPAWN_EGG.get());
	}
	 */

	public enum Type {
		IRIS("iris", TropicraftBlocks.IRIS.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)),
		ANEMONE("anemone", TropicraftBlocks.ANEMONE.defaultBlockState());

		private final String name;
		private final BlockState renderState;

		Type(String nameIn, BlockState renderStateIn) {
			this.name = nameIn;
			this.renderState = renderStateIn;
		}

		public static Type getRandomType(final Random rand) {
			return values()[rand.nextInt(values().length)];
		}

		/**
		 * A block state that is rendered on the back of the mooshroom.
		 */
		@Environment(EnvType.CLIENT)
		public BlockState getRenderState() {
			return this.renderState;
		}

		private static Type getTypeByName(String nameIn) {
			for(Type CowktailEntity$type : values()) {
				if (CowktailEntity$type.name.equals(nameIn)) {
					return CowktailEntity$type;
				}
			}

			return IRIS;
		}
	}
}
