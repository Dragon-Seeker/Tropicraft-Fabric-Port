package net.tropicraftFabric.common.dimension.feature.block_state_provider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.ServerTagManagerHolder;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;
import net.tropicraftFabric.client.data.TropicraftTags;

import java.util.List;
import java.util.Random;

public final class NoiseFromTagTropicFlowerBlockStateProvider extends BlockStateProvider {
    public static Tag<Block> tag = TropicraftTags.Blocks.TROPICS_FLOWERS;

    /*
    public static final Codec<NoiseFromTagTropicFlowerBlockStateProvider> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Tag.codec(() -> ServerTagManagerHolder.getTagManager().getBlocks()).fieldOf("tag").forGetter(c -> c.tag)
    ).apply(instance, NoiseFromTagTropicFlowerBlockStateProvider::new));

     */


    public static final NoiseFromTagTropicFlowerBlockStateProvider INSTANCE = new NoiseFromTagTropicFlowerBlockStateProvider();
    public static final Codec<NoiseFromTagTropicFlowerBlockStateProvider> CODEC = Codec.unit(() -> INSTANCE);


    public NoiseFromTagTropicFlowerBlockStateProvider() {
    }

    @Override
    protected BlockStateProviderType<?> getType() {
        return TropicraftBlockStateProviders.NOISE_FROM_TAG_TROPIC_FLOWER;
    }

    @Override
    public BlockState getBlockState(Random random, BlockPos pos) {
        List<Block> blocks = this.tag.values();
        if (blocks.isEmpty()) {
            return Blocks.AIR.getDefaultState();
        }

        double noise = Biome.FOLIAGE_NOISE.sample(pos.getX() / 48.0, pos.getZ() / 48.0, false);
        noise = MathHelper.clamp((1.0 + noise) / 2.0, 0.0, 0.9999);

        Block block = blocks.get(MathHelper.floor(noise * blocks.size()));
        return block.getDefaultState();
    }
}
