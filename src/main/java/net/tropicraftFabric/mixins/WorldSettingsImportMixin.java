package net.tropicraftFabric.mixins;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.RegistryOps;
import net.minecraft.util.registry.*;
import net.minecraft.world.dimension.DimensionOptions;
import net.tropicraftFabric.Constants;
import net.tropicraftFabric.common.dimension.TropicraftDimension;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalInt;

@Mixin(RegistryOps.class)
public class WorldSettingsImportMixin {
    @Shadow
    @Final
    private DynamicRegistryManager.Impl registryManager;

    /**
     * Add the tropicraft dimension to both new worlds and existing worlds when they get loaded.
     */
    @Inject(method = "loadToRegistry(Lnet/minecraft/util/registry/SimpleRegistry;Lnet/minecraft/util/RegistryKey;Lcom/mojang/serialization/Codec;)Lcom/mojang/serialization/DataResult;", at = @At("HEAD"))
    @SuppressWarnings("unchecked")
    protected void loadToRegistry(SimpleRegistry<?> registry, RegistryKey<?> registryKey, Codec<?> codec, CallbackInfoReturnable<DataResult<SimpleRegistry<?>>> ci) {
        if (registryKey == Registry.DIMENSION_KEY && registry.get(TropicraftDimension.ID) == null) {
            this.addDimensions((SimpleRegistry<DimensionOptions>) registry);
        }
    }

    private void addDimensions(SimpleRegistry<DimensionOptions> registry) {
        DimensionOptions overworld = registry.get(DimensionOptions.OVERWORLD);
        if (overworld == null) {
            return;
        }

        // steal the seed from the overworld chunk generator.
        // not necessarily the world seed if a datapack changes it, but it's probably a safe bet.
        long seed = ((SeedAccessor)overworld.getChunkGenerator()).getWorldSeed();

        DimensionOptions dimension = TropicraftDimension.createDimension(
                this.registryManager.get(Registry.DIMENSION_TYPE_KEY),
                this.registryManager.get(Registry.BIOME_KEY),
                this.registryManager.get(Registry.NOISE_SETTINGS_WORLDGEN),
                seed
        );
        registry.replace(OptionalInt.empty(), TropicraftDimension.DIMENSION, dimension, Lifecycle.stable());
    }


    /**
     * Remove the experimental world settings warning screen for tropicraft content.
     */

    /*
    @ModifyVariable(
            method = "readSupplier",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/util/dynamic/RegistryOps$EntryLoader;load(Lcom/mojang/serialization/DynamicOps;Lnet/minecraft/util/RegistryKey;Lnet/minecraft/util/RegistryKey;Lcom/mojang/serialization/Decoder;)Lcom/mojang/serialization/DataResult;"
            ),
            ordinal = 1
    )
    private <E> DataResult<Pair<E, OptionalInt>> modifyDataResult(
            DataResult<Pair<E, OptionalInt>> result,
            RegistryKey<? extends Registry<E>> registryKey, MutableRegistry<E> registry, Codec<E> mapCodec, Identifier id
    ) {
        if (id.getNamespace().equals(Constants.MODID)) {
            return result.setLifecycle(Lifecycle.stable());
        }
        return result;
    }

     */
}
