package net.tropicraftFabric.common.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.tropicraftFabric.common.entity.ai.TropiCreeperSwellGoal;
import net.tropicraftFabric.common.registry.TropicBlocks;

import java.util.Collection;
import java.util.List;

public class TropiCreeperEntity extends PathAwareEntity {
    private static final TrackedData<Integer> STATE = DataTracker.registerData(TropiCreeperEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> IGNITED = DataTracker.registerData(TropiCreeperEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private int prevTimeSinceIgnited, timeSinceIgnited;
    private int fuseTime = 30;
    private int explosionRadius = 3;

    public TropiCreeperEntity(final EntityType<? extends PathAwareEntity> entityType, final World worldIn) {
        super(entityType, worldIn);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new TropiCreeperSwellGoal(this));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, OcelotEntity.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, CatEntity.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new FollowTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(STATE, -1);
        this.dataTracker.startTracking(IGNITED, false);
    }

    /**
     * The maximum height from where the entity is alowed to jump (used in pathfinder)
     */
    @Override
    public int getSafeFallDistance() {
        return this.getTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
    }

    @Override
    public boolean handleFallDamage(float distance, float damageMultiplier) {
        boolean fall = super.handleFallDamage(distance, damageMultiplier);
        this.timeSinceIgnited = (int)((float)this.timeSinceIgnited + distance * 1.5F);
        if (this.timeSinceIgnited > this.fuseTime - 5) {
            this.timeSinceIgnited = this.fuseTime - 5;
        }

        return fall;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound compound) {
        super.writeCustomDataToNbt(compound);
        compound.putShort("Fuse", (short)this.fuseTime);
        compound.putByte("ExplosionRadius", (byte)this.explosionRadius);
        compound.putBoolean("ignited", this.hasIgnited());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readCustomDataFromNbt(NbtCompound compound) {
        super.readCustomDataFromNbt(compound);
        if (compound.contains("Fuse", 99)) {
            this.fuseTime = compound.getShort("Fuse");
        }

        if (compound.contains("ExplosionRadius", 99)) {
            this.explosionRadius = compound.getByte("ExplosionRadius");
        }

        if (compound.getBoolean("ignited")) {
            this.ignite();
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        if (this.isAlive()) {
            this.prevTimeSinceIgnited = this.timeSinceIgnited;
            if (this.hasIgnited()) {
                this.setCreeperState(1);
            }

            int i = this.getCreeperState();
            if (i > 0 && this.timeSinceIgnited == 0) {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
            }

            this.timeSinceIgnited += i;
            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.fuseTime) {
                this.timeSinceIgnited = this.fuseTime;
                this.explode();
            }
        }

        super.tick();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_CREEPER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CREEPER_DEATH;
    }

    /**
     * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
     */
    public int getCreeperState() {
        return this.dataTracker.get(STATE);
    }

    /**
     * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
     */
    public void setCreeperState(int state) {
        this.dataTracker.set(STATE, state);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getStackInHand(hand);
        if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
            this.world.playSound(player, getX(), getY(), getZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, this.getSoundCategory(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            player.swingHand(hand);
            if (!this.world.isClient) {
                this.ignite();
                itemstack.damage(1, player, p -> {
                    p.sendToolBreakStatus(hand);
                });
                return ActionResult.SUCCESS;
            }
        }

        return super.interactMob(player, hand);
    }

    /**
     * Creates an explosion as determined by this creeper's power and explosion radius.
     */
    private void explode() {
        if (!this.world.isClient) {
            this.dead = true;
            //this.world.createExplosion(this, this.posX, this.posY, this.posZ, (float)this.explosionRadius, Explosion.Mode.NONE);
            //TODO: readd coconut bomb drop for creeper
            // this.dropItem(TCItemRegistry.coconutBomb.itemID, rand.nextInt(3) + 1);
            int radius = 5;
            int radiusSq = radius * radius;
            BlockPos center = getBlockPos();
            for (int i = 0; i < 3 * radiusSq; i++) {
                BlockPos attempt = center.add(random.nextInt((radius * 2) + 1) - radius, 0, random.nextInt((radius * 2) + 1) - radius);
                if (attempt.getSquaredDistance(center) < radiusSq) {
                    attempt = attempt.up(radius);
                    while (world.getBlockState(attempt).getMaterial().isReplaceable() && attempt.getY() > center.getY() - radius) {
                        attempt = attempt.down();
                    }
                    attempt = attempt.up();

                    List<Block> SMALL_FLOWERS = List.of(TropicBlocks.ACAI_VINE,
                                                        TropicBlocks.ANEMONE,
                                                        TropicBlocks.BROMELIAD,
                                                        TropicBlocks.CANNA,
                                                        TropicBlocks.COMMELINA_DIFFUSA,
                                                        TropicBlocks.CROCOSMIA,
                                                        TropicBlocks.CROTON,
                                                        TropicBlocks.DRACAENA,
                                                        TropicBlocks.TROPICAL_FERN,
                                                        TropicBlocks.FOLIAGE,
                                                        TropicBlocks.MAGIC_MUSHROOM,
                                                        TropicBlocks.ORANGE_ANTHURIUM,
                                                        TropicBlocks.ORCHID,
                                                        TropicBlocks.PATHOS,
                                                        TropicBlocks.RED_ANTHURIUM);

                    BlockState state = SMALL_FLOWERS.get(random.nextInt(SMALL_FLOWERS.size()) - 1).getDefaultState(); //.Blocks.SMALL_FLOWERS.getRandom(random).getDefaultState();
                    if (state.canPlaceAt(world, attempt)) {
                        world.setBlockState(attempt, state);
                    }
                }
            }
            this.remove();
            this.spawnLingeringCloud();
        } else {
            world.addParticle(ParticleTypes.EXPLOSION_EMITTER, getX(), getY() + 1F, getZ(), 1.0D, 0.0D, 0.0D);
        }
    }

    private void spawnLingeringCloud() {
        Collection<StatusEffectInstance> collection = this.getStatusEffects();
        if (!collection.isEmpty()) {
            AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(world, getX(), getY(), getZ());
            areaeffectcloudentity.setRadius(2.5F);
            areaeffectcloudentity.setRadiusOnUse(-0.5F);
            areaeffectcloudentity.setWaitTime(10);
            areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
            areaeffectcloudentity.setRadiusGrowth(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());

            for(StatusEffectInstance effectinstance : collection) {
                areaeffectcloudentity.addEffect(new StatusEffectInstance(effectinstance));
            }

            this.world.spawnEntity(areaeffectcloudentity);
        }
    }

    public boolean hasIgnited() {
        return this.dataTracker.get(IGNITED);
    }

    public void ignite() {
        this.dataTracker.set(IGNITED, true);
    }
    
    public float getCreeperFlashIntensity(float partialTicks) {
       return MathHelper.lerp(partialTicks, (float)this.prevTimeSinceIgnited, (float)this.timeSinceIgnited) / (float)(this.fuseTime - 2);
    }
    /*
    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(TropicraftItems.TROPICREEPER_SPAWN_EGG.get());
    }

     */
}
