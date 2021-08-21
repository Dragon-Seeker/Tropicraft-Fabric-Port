package net.tropicraftFabric.common.item;

import net.minecraft.item.DyeItem;
import net.tropicraftFabric.common.entity.placeable.FurnitureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.tropicraftFabric.mixins.DyeColorAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public class FurnitureItem<T extends FurnitureEntity> extends Item implements IColoredItem {

    private final EntityType<T> entityType;
    private final DyeColor color;

    public FurnitureItem(final Settings properties, final EntityType<T> entityType, final DyeColor color) {
        super(properties);
        this.entityType = entityType;
        this.color = color;
    }

    @Override
    public int getColor(ItemStack stack, int pass) {
        return (pass == 0 ? 16777215 : ((DyeColorAccessor) ((Object) this.color)).getColor());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity placer, Hand hand) {
        ItemStack heldItem = placer.getStackInHand(hand);
        HitResult rayTraceResult = raycast(world, placer, RaycastContext.FluidHandling.ANY);
        if (rayTraceResult.getType() == HitResult.Type.MISS) {
            return new TypedActionResult<>(ActionResult.PASS, heldItem);
        } else {
            Vec3d lookvec = placer.getRotationVec(1.0F);
            List<Entity> nearbyEntities = world.getOtherEntities(placer, placer.getBoundingBox().stretch(lookvec.multiply(5.0D)).expand(1.0D), EntityPredicates.EXCEPT_SPECTATOR);
            if (!nearbyEntities.isEmpty()) {
                Vec3d eyePosition = placer.getCameraPosVec(1.0F);
                Iterator<Entity> nearbyEntityIterator = nearbyEntities.iterator();

                while (nearbyEntityIterator.hasNext()) {
                    Entity nearbyEnt = nearbyEntityIterator.next();
                    Box nearbyBB = nearbyEnt.getBoundingBox().expand((double)nearbyEnt.getTargetingMargin());
                    if (nearbyBB.contains(eyePosition)) {
                        return new TypedActionResult<>(ActionResult.PASS, heldItem);
                    }
                }
            }

            if (rayTraceResult.getType() == HitResult.Type.BLOCK) {
                Vec3d hitVec = rayTraceResult.getPos();

                final T entity = this.entityType.create(world);
                entity.refreshPositionAndAngles(new BlockPos(hitVec.x, hitVec.y, hitVec.z), 0, 0);
                entity.setVelocity(Vec3d.ZERO);
                entity.setRotation(placer.yaw + 180);
                entity.setColor(this.color);

                if (!world.isSpaceEmpty(entity, entity.getBoundingBox().expand(-0.1D))) {
                    return new TypedActionResult<>(ActionResult.FAIL, heldItem);
                } else {
                    if (!world.isClient) {
                        world.spawnEntity(entity);
                    }

                    if (!placer.abilities.creativeMode) {
                        heldItem.decrement(1);
                    }

                    placer.incrementStat(Stats.USED.getOrCreateStat(this));
                    return new TypedActionResult<>(ActionResult.SUCCESS, heldItem);
                }
            } else {
                return new TypedActionResult<>(ActionResult.PASS, heldItem);
            }
        }
    }
}
