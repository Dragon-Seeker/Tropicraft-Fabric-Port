package net.tropicraft.core.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

public class TropicraftFishBucketItem<T extends AbstractFish> extends MobBucketItem {
    private final EntityType<T> fishType;

    public TropicraftFishBucketItem(final EntityType<T> type, Fluid fluid, Properties props) {
        super(type, fluid, SoundEvents.BUCKET_EMPTY_FISH, props);
        this.fishType = type;
    }

    /*
    @Override
    public void onEmptied(@Nullable PlayerEntity player, World world, ItemStack stack, BlockPos pos) {
        if (!world.isClient) {
            this.spawnEntity((ServerWorld) world, stack, pos);
        }
    }
     */

    private void spawn(ServerLevel world, ItemStack stack, BlockPos pos) {
        Entity fishy = fishType.spawn(world, stack, null, pos, MobSpawnType.BUCKET, true, false);
        if (fishy != null) {
            ((AbstractFish) fishy).setFromBucket(true);
        }

    }
}
