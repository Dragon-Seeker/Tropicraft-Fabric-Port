package net.tropicraft.core.common.dimension.feature.block_state_provider;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.tropicraft.Constants;

public final class TropicraftBlockStateProviders<P extends BlockStateProvider> {
    //public static final DeferredRegister<BlockStateProviderType<?>> BLOCK_STATE_PROVIDERS = DeferredRegister.create(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES, Constants.MODID);

    //public static final BlockStateProviderType<?> NOISE_FROM_TAG = (BlockStateProviderType)Registry.register(Registry.BLOCK_STATE_PROVIDER_TYPE, new Identifier(Constants.MODID, "noise_from_tag"), new BlockStateProviderType(NoiseFromTagBlockStateProvider.CODEC));

    public static final BlockStateProviderType<?> NOISE_FROM_TAG_TROPIC_FLOWER = (BlockStateProviderType)Registry.register(Registry.BLOCKSTATE_PROVIDER_TYPES, new ResourceLocation(Constants.MODID, "noise_from_tag_tropic_flower"), new BlockStateProviderType(NoiseFromTagTropicFlowerBlockStateProvider.CODEC));
    public static final BlockStateProviderType<?> NOISE_FROM_TAG_RAINFOREST_FLOWER = (BlockStateProviderType)Registry.register(Registry.BLOCKSTATE_PROVIDER_TYPES, new ResourceLocation(Constants.MODID, "noise_from_tag_rainforest_flower"), new BlockStateProviderType(NoiseFromTagRainForestFlowerBlockStateProvider.CODEC));

            //(BlockStateProviderType)Registry.register(Registry.BLOCK_STATE_PROVIDER_TYPE, "noise_from_tag", new BlockStateProviderType(NoiseFromTagBlockStateProvider.TYPE_CODEC));

    /*
    private static <P extends BlockStateProvider> BlockStateProviderType<P> register(String id, Codec<P> codec) {
        return (BlockStateProviderType)Registry.register(Registry.BLOCK_STATE_PROVIDER_TYPE, id, new BlockStateProviderType(codec));
    }

     */


    public static void init(){
    }
}
