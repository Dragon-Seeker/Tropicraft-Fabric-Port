package net.tropicraft.core.common.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WaterWandItem extends Item {
    public WaterWandItem(FabricItemSettings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        double inc = Math.PI / 12;

        ItemStack itemstack = player.getStackInHand(hand);

        player.swingHand(Hand.MAIN_HAND);
        if (!world.isClient) {
            for (double lat = 0; lat < 2 * Math.PI; lat += inc) {
                for (double lng = 0; lng < 2 * Math.PI; lng += inc) {
                    for (double len = 1; len < 3; len += 0.5D) {
                        int x1 = (int)(Math.cos(lat) * len);
                        int z1 = (int)(Math.sin(lat) * len);
                        int y1 = (int)(Math.sin(lng) * len);
                        if (!removeWater(world, itemstack, player, new BlockPos(player.getBlockPos().add(x1, y1, z1)))) {
                            break;
                        }
                    }
                }
            }
        }

        return new TypedActionResult<>(ActionResult.PASS, itemstack);
    }

    private boolean removeWater(World world, ItemStack itemstack, PlayerEntity player, BlockPos pos) {
        if (!world.isClient) {
            if (world.getBlockState(pos).getMaterial() == Material.WATER) {
                itemstack.damage(1, player, (e) -> {
                    e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                });
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                return true;
            }

            return world.isAir(pos);
        }

        return false;
    }
}
