package net.tropicraft.core.common.entity.hostile;

import net.tropicraft.core.common.entity.passive.EntityKoaBase;
import net.tropicraft.core.common.registry.TropicraftItems;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

public class TropiSkellyEntity extends HostileEntity {
    public TropiSkellyEntity(EntityType<? extends HostileEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initGoals() {
        goalSelector.add(0, new SwimGoal(this));
        goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, false));
        goalSelector.add(5, new GoToWalkTargetGoal(this, 1.0D));
        goalSelector.add(7, new WanderAroundFarGoal(this, 1.0D));
        goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        goalSelector.add(8, new LookAroundGoal(this));
        
        targetSelector.add(1, new RevengeGoal(this));
        targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));
        //TODO targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, EntityAshen.class, false));
        targetSelector.add(3, new FollowTargetGoal<>(this, EntityKoaBase.class, false));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isClient && this.world.getDifficulty() == Difficulty.PEACEFUL) {
            this.remove();
        }
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0);
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess worldIn, LocalDifficulty difficultyIn, SpawnReason reason, @Nullable EntityData spawnDataIn, @Nullable NbtCompound dataTag) {
        this.setStackInHand(Hand.MAIN_HAND, new ItemStack(TropicraftItems.BAMBOO_SPEAR));
        return super.initialize(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    private boolean isValidLightLevel() {
        BlockPos blockpos = new BlockPos(getX(), getBoundingBox().minY, getZ());
        if (world.getLightLevel(LightType.SKY, blockpos) > random.nextInt(32)) {
            return false;
        } else {
            int i = world.isThundering() ? world.getLightLevel(blockpos, 10) : world.getLightLevel(blockpos);
            return i <= random.nextInt(8);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    @Override
    public boolean canSpawn(WorldAccess worldIn, SpawnReason spawnReasonIn) {
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && this.isValidLightLevel() && super.canSpawn(worldIn, spawnReasonIn);
    }
    /*
    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(TropicraftItems.TROPISKELLY_SPAWN_EGG.get());
    }

     */
}
