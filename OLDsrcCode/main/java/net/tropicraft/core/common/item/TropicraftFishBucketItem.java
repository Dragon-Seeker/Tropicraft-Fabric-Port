package net.tropicraft.core.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TropicraftFishBucketItem<T extends FishEntity> extends EntityBucketItem {
    private final EntityType<T> fishType;

    public TropicraftFishBucketItem(final EntityType<T> type, Fluid fluid, Settings props) {
        super(type, fluid, SoundEvents.ITEM_BUCKET_EMPTY_FISH, props);
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

    private void spawnEntity(ServerWorld world, ItemStack stack, BlockPos pos) {
        Entity fishy = fishType.spawnFromItemStack(world, stack, null, pos, SpawnReason.BUCKET, true, false);
        if (fishy != null) {
            ((FishEntity) fishy).setFromBucket(true);
        }

    }
}
