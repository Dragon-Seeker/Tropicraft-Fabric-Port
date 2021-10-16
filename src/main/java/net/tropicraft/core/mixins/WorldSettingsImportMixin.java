package net.tropicraft.core.mixins;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryReadOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.LevelStem;
import net.tropicraft.core.common.dimension.TropicraftDimension;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalInt;

@Mixin(RegistryReadOps.class)
public class WorldSettingsImportMixin {
    @Shadow
    @Final
    //private DynamicRegistryManager.Impl registryManager;
    private RegistryAccess registryAccess;

    /**
     * Add the tropicraft dimension to both new worlds and existing worlds when they get loaded.
     */
    //@Inject(method = "loadToRegistry(Lnet/minecraft/util/registry/SimpleRegistry;Lnet/minecraft/util/RegistryKey;Lcom/mojang/serialization/Codec;)Lcom/mojang/serialization/DataResult;", at = @At("HEAD"))
    @Inject(at = @At("HEAD"), method = "decodeElements")
    @SuppressWarnings("unchecked")
    protected <E> void loadToRegistry(MappedRegistry<E> registry, ResourceKey<E> registryKey, Codec<E> codec, CallbackInfoReturnable<DataResult<MappedRegistry<E>>> ci) {
        if (registryKey == Registry.LEVEL_STEM_REGISTRY && registry.get(TropicraftDimension.ID) == null) {
            this.addDimensions((MappedRegistry<LevelStem>) registry);
        }
    }

    private void addDimensions(MappedRegistry<LevelStem> registry) {
        LevelStem overworld = registry.get(LevelStem.OVERWORLD);
        if (overworld == null) {
            return;
        }

        // steal the seed from the overworld chunk generator.
        // not necessarily the world seed if a datapack changes it, but it's probably a safe bet.
        long seed = ((SeedAccessor)overworld.generator()).getStrongholdSeed();

        LevelStem dimension = TropicraftDimension.createDimension(
                this.registryAccess.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY),
                this.registryAccess.registryOrThrow(Registry.BIOME_REGISTRY),
                this.registryAccess.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY),
                seed
        );
        registry.registerOrOverride(OptionalInt.empty(), TropicraftDimension.DIMENSION, dimension, Lifecycle.stable());
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
