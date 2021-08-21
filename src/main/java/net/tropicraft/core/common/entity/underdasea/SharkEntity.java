package net.tropicraft.core.common.entity.underdasea;

import net.tropicraft.core.common.entity.ai.fishies.AvoidWallsGoal;
import net.tropicraft.core.common.entity.ai.fishies.RandomSwimGoal;
import net.tropicraft.core.common.entity.ai.fishies.SwimToAvoidEntityGoal;
import net.tropicraft.core.common.entity.ai.fishies.TargetPreyGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.EnumSet;

public class SharkEntity extends TropicraftFishEntity {

    private static final TrackedData<Boolean> IS_BOSS = DataTracker.registerData(SharkEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private final ServerBossBar bossInfo = new ServerBossBar(getDisplayName(), BossBar.Color.BLUE, BossBar.Style.PROGRESS);
    private ArrayList<ServerPlayerEntity> bossTargets = new ArrayList<>();
    private boolean hasSetBoss = false;

    public SharkEntity(EntityType<? extends WaterCreatureEntity> type, World world) {
        super(type, world);
        experiencePoints = 20;
        this.setApproachesPlayers(true);
        // TODO - this.setDropStack(ItemRegistry.fertilizer, 3);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        //goalSelector.addGoal(0, new EntityAISwimAvoidPredator(0, this, 2D));
        goalSelector.add(0, new AvoidWallsGoal(EnumSet.of(Goal.Control.MOVE), this));
        if (fleeFromPlayers) {
            goalSelector.add(0, new SwimToAvoidEntityGoal(EnumSet.of(Goal.Control.MOVE), this, 5F, new Class[] {PlayerEntity.class}));
        }
        goalSelector.add(2, new TargetPreyGoal(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK), this));
        goalSelector.add(2, new RandomSwimGoal(EnumSet.of(Goal.Control.MOVE), this));
    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        getDataTracker().startTracking(IS_BOSS, false);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0);
    }

    public void setBoss() {
        getDataTracker().set(IS_BOSS, true);
    }

    public boolean isBoss() {
        return this.getDataTracker().get(IS_BOSS);
    }

    private void setBossTraits() {
        getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(8);
        //TODO this.setDropStack(ItemRegistry.yellowFlippers, 1);
        setCustomName(new LiteralText("Elder Hammerhead"));
        setCustomNameVisible(true);
        setSwimSpeeds(1.1f, 2.2f, 1.5f, 3f, 5f);
        getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20);
        // TODO in renderer - this.setTexture("hammerhead4");
        if (!world.isClient) {
            bossInfo.setName(new LiteralText("Elder Hammerhead"));
        }
        hasSetBoss = true;
    }

    @Override
    public void tick() {
        super.tick();
        if (isBoss()) {
            // Set state to boss
            if (!hasSetBoss) {
                setBossTraits();
            }

            if (!world.isClient) {
                // Search for suitable target
                PlayerEntity nearest = world.getClosestPlayer(this, 64D);
                if (nearest != null) {
                    if (canSee(nearest) && nearest.isTouchingWater() && !nearest.isCreative() && nearest.isAlive()) {
                        aggressTarget = nearest;
                        setTargetHeading(aggressTarget.getX(), aggressTarget.getY() + 1, aggressTarget.getZ(), true);
                        // Show health bar to target player
                        if (nearest instanceof ServerPlayerEntity) {
                            if (!bossInfo.getPlayers().contains(nearest)) {
                                bossTargets.add((ServerPlayerEntity) nearest);
                                bossInfo.addPlayer((ServerPlayerEntity) nearest);
                            }
                        }
                    } else {
                        clearBossTargets();
                    }
                } else {
                    clearBossTargets();
                }

                // Heal if no target
                if (this.getHealth() < this.getMaxHealth() && this.age % 80 == 0 && this.aggressTarget == null) {
                    this.heal(1f);
                    this.playSpawnEffects();
                }
                // Update health bar
                this.bossInfo.setPercent(this.rangeMap(this.getHealth(), 0, this.getMaxHealth(), 0, 1));
            }
        }
    }

    private void clearBossTargets() {
        if (bossTargets.size() > 0) {
            for (ServerPlayerEntity p : bossTargets) {
                bossInfo.removePlayer(p);
            }
            bossTargets.clear();
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound n) {
        n.putBoolean("isBoss", isBoss());
        super.writeCustomDataToNbt(n);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound n) {
        if (n.getBoolean("isBoss")) {
            setBoss();
        }
        super.readCustomDataFromNbt(n);
    }

    @Override
    public boolean canImmediatelyDespawn(double p) {
        return !isBoss() && super.canImmediatelyDespawn(p);
    }
    /*
    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(TropicraftItems.HAMMERHEAD_SPAWN_EGG.get());
    }
     */
}
