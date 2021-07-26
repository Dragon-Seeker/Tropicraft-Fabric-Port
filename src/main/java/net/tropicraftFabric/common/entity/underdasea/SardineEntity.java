package net.tropicraftFabric.common.entity.underdasea;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SardineEntity extends SchoolingFishEntity implements IAtlasFish {

    public SardineEntity(EntityType<? extends SchoolingFishEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return FishEntity.createFishAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 5.0);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        return ActionResult.PASS;
    }

    @Override
    public int getMaxGroupSize() {
        return 20;
    }

    @Override
    protected ItemStack getFishBucketItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SALMON_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SALMON_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SALMON_HURT;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_SALMON_FLOP;
    }

    @Override
    public int getAtlasSlot() {
        return 8;
    }
    /*
    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(TropicraftItems.SARDINE_SPAWN_EGG.get());
    }

     */
}
