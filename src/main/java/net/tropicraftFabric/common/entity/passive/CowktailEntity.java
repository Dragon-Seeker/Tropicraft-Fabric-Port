package net.tropicraftFabric.common.entity.passive;

import net.tropicraftFabric.common.drinks.Drink;
import net.tropicraftFabric.common.item.CocktailItem;
import net.tropicraftFabric.common.registry.TropicBlocks;
import net.tropicraftFabric.common.registry.TropicraftEntities;
import net.tropicraftFabric.common.registry.TropicraftItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CowktailEntity extends CowEntity implements Shearable {
	private static final TrackedData<String> COWKTAIL_TYPE = DataTracker.registerData(CowktailEntity.class, TrackedDataHandlerRegistry.STRING);

	public CowktailEntity(EntityType<? extends CowktailEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Override
	public float getPathfindingFavor(BlockPos pos, WorldView worldIn) {
		return worldIn.getBlockState(pos.down()).getBlock() == Blocks.MYCELIUM ? 10.0F : worldIn.getBrightness(pos) - 0.5F;
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(COWKTAIL_TYPE, Type.IRIS.name);
	}

	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getStackInHand(hand);
		if (itemstack.getItem() == TropicraftItems.BAMBOO_MUG && !this.isBaby()) {
			if (player.abilities.creativeMode) {
				itemstack.decrement(1);
			}

			final List<CocktailItem> cocktails = new ArrayList(TropicraftItems.COCKTAILS.values());
			// Remove generic cocktail from cowktail
			cocktails.removeIf(cocktail -> player.getMainHandStack().getItem() instanceof CocktailItem && cocktail.getDrink() == Drink.COCKTAIL);
			final ItemStack cocktailItem = new ItemStack(cocktails.get(random.nextInt(cocktails.size())));

			if (itemstack.isEmpty()) {
				player.setStackInHand(hand, cocktailItem);
			} else if (!player.inventory.insertStack(cocktailItem)) {
				player.dropItem(cocktailItem, false);
			}

			this.playSound(SoundEvents.ENTITY_MOOSHROOM_SUSPICIOUS_MILK, 1.0F, 1.0F);
			return ActionResult.SUCCESS;
		}

		return super.interactMob(player, hand);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound compound) {
		super.writeCustomDataToNbt(compound);
		compound.putString("Type", this.getCowktailType().name);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readCustomDataFromNbt(NbtCompound compound) {
		super.readCustomDataFromNbt(compound);
		this.setCowktailType(Type.getTypeByName(compound.getString("Type")));
	}

	private void setCowktailType(Type typeIn) {
		this.dataTracker.set(COWKTAIL_TYPE, typeIn.name);
	}

	public Type getCowktailType() {
		return Type.getTypeByName(this.dataTracker.get(COWKTAIL_TYPE));
	}

	@Override
	public CowktailEntity createChild(ServerWorld world, PassiveEntity ageable) {
		CowktailEntity child = TropicraftEntities.COWKTAIL.create(this.world);
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
	public boolean isShearable() {
		return !this.isBaby();
	}

	@NotNull
	@Override
	public void sheared(SoundCategory shearedSoundCategory) {
		List<ItemStack> ret = new ArrayList<>();
		this.world.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getBodyY(0.5D), this.getZ(), 0.0D, 0.0D, 0.0D);
		if (!this.world.isClient) {
			this.remove();
			CowEntity cowentity = EntityType.COW.create(this.world);
			cowentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.yaw, this.pitch);
			cowentity.setHealth(this.getHealth());
			cowentity.bodyYaw = this.bodyYaw;
			if (this.hasCustomName()) {
				cowentity.setCustomName(this.getCustomName());
				cowentity.setCustomNameVisible(this.isCustomNameVisible());
			}
			this.world.spawnEntity(cowentity);
			for(int i = 0; i < 5; ++i) {
				//ret.add(new ItemStack(this.getCowktailType().renderState.getBlock()));
				this.world.spawnEntity(new ItemEntity(this.world, this.getX(), this.getBodyY(1.0D), this.getZ(), new ItemStack(this.getCowktailType().renderState.getBlock())));
			}

			this.playSound(SoundEvents.ENTITY_MOOSHROOM_SHEAR, 1.0F, 1.0F);
		}
		//return ret;
	}

	@Nullable
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficultyInstance, SpawnReason spawnReason, @Nullable EntityData data, @Nullable NbtCompound nbt) {
		setCowktailType(Type.getRandomType(random));
		return super.initialize(world, difficultyInstance, spawnReason, data, nbt);
	}


	/*
	@Override
	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(TropicraftItems.COWKTAIL_SPAWN_EGG.get());
	}
	 */

	public enum Type {
		IRIS("iris", TropicBlocks.IRIS.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER)),
		ANEMONE("anemone", TropicBlocks.ANEMONE.getDefaultState());

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
