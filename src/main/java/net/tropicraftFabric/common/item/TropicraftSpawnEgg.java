package net.tropicraftFabric.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.Objects;

public class TropicraftSpawnEgg<T extends Entity> extends Item {

    private final EntityType<T> typeIn;

    public TropicraftSpawnEgg(final EntityType<T> type, Settings properties) {
        super(properties);
        this.typeIn = type;
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            ItemStack itemStack = context.getStack();
            BlockPos pos = context.getBlockPos();
            Direction dir = context.getSide();
            BlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if (block == Blocks.SPAWNER) {
                BlockEntity te = world.getBlockEntity(pos);
                if (te instanceof MobSpawnerBlockEntity) {
                    MobSpawnerLogic spawner = ((MobSpawnerBlockEntity)te).getLogic();
                    EntityType<?> spawnType = typeIn;
                    spawner.setEntityId(spawnType);
                    te.markDirty();
                    world.updateListeners(pos, state, state, 3);
                    itemStack.decrement(1);
                    return ActionResult.SUCCESS;
                }
            }

            BlockPos spawnPos;
            if (state.getCollisionShape(world, pos).isEmpty()) {
                spawnPos = pos;
            } else {
                spawnPos = pos.offset(dir);
            }

            EntityType<?> type3 = typeIn;
            if (type3.spawnFromItemStack((ServerWorld) world, itemStack, context.getPlayer(), spawnPos, SpawnReason.SPAWN_EGG, true, !Objects.equals(pos, spawnPos) && dir == Direction.UP) != null) {
                itemStack.decrement(1);
            }

            return ActionResult.SUCCESS;
        }
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getStackInHand(hand);
        if (world.isClient) {
            return TypedActionResult.pass(heldItem);
        } else {
            HitResult rayTraceResult = raycast(world, player, RaycastContext.FluidHandling.SOURCE_ONLY);
            if (rayTraceResult.getType() != HitResult.Type.BLOCK) {
                return TypedActionResult.pass(heldItem);
            } else {
                BlockHitResult traceResult = (BlockHitResult) rayTraceResult;
                BlockPos tracePos = traceResult.getBlockPos();
                if (!(world.getBlockState(tracePos).getBlock() instanceof FluidBlock)) {
                    return TypedActionResult.pass(heldItem);
                } else if (world.canPlayerModifyAt(player, tracePos) && player.canPlaceOn(tracePos, traceResult.getSide(), heldItem)) {
                    EntityType<?> type = typeIn;
                    if (type.spawnFromItemStack((ServerWorld) world, heldItem, player, tracePos, SpawnReason.SPAWN_EGG, false, false) == null) {
                        return TypedActionResult.pass(heldItem);
                    } else {
                        if (!player.abilities.creativeMode) {
                            heldItem.decrement(1);
                        }

                        player.incrementStat(Stats.USED.getOrCreateStat(this));
                        return TypedActionResult.success(heldItem);
                    }
                } else {
                    return TypedActionResult.fail(heldItem);
                }
            }
        }
    }
}
