package net.tropicraft.core.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.tropicraft.core.common.registry.TropicraftBlocks;

import java.util.Random;

public class TropicOreBlock extends OreBlock {
    public TropicOreBlock(FabricBlockSettings settings) {
        super(settings); //UniformIntProvider.create(0, 2)
    }

    public TropicOreBlock(FabricBlockSettings settings, UniformIntProvider provider) {
        super(settings, provider); //UniformIntProvider.create(0, 2)
    }

}
