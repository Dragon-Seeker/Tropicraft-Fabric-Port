package net.tropicraftFabric.common.dimension.feature.block_state_provider;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;
import net.tropicraftFabric.mixins.BlockStateProviderTypeInvoker;

public final class TropicraftBlockStateProviders {
    //public static final DeferredRegister<BlockStateProviderType<?>> BLOCK_STATE_PROVIDERS = DeferredRegister.create(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES, Constants.MODID);

    public static final BlockStateProviderType<?> NOISE_FROM_TAG = BlockStateProviderTypeInvoker.invokeRegister("noise_from_tag", NoiseFromTagBlockStateProvider.TYPE_CODEC);

    //public static final BlockStateProviderType<?> TEST = Registry.register(Registry.BLOCK_STATE_PROVIDER_TYPE, "noise_from_tag", () -> new BlockStateProviderType<>(NoiseFromTagBlockStateProvider.TYPE_CODEC));
}
