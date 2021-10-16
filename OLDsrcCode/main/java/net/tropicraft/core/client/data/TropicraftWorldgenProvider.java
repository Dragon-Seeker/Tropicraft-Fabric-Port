package net.tropicraft.core.client.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Lazy;
import net.minecraft.util.dynamic.RegistryReadingOps;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class TropicraftWorldgenProvider implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger LOGGER = LogManager.getLogger(TropicraftWorldgenProvider.class);

    private static final Lazy<DynamicRegistryManager.Impl> DYNAMIC_REGISTRIES = new Lazy<>(() -> {
        DynamicRegistryManager.Impl dynamicRegistries = new DynamicRegistryManager.Impl();
        for (Registry<?> registry : BuiltinRegistries.REGISTRIES) {
            copyAllToDynamicRegistry(registry, dynamicRegistries);
        }
        return dynamicRegistries;
    });

    private static <T> void copyAllToDynamicRegistry(Registry<T> from, DynamicRegistryManager dynamicRegistries) {
        dynamicRegistries.getOptionalMutable(from.getKey()).ifPresent(dynamicRegistry -> {
            copyAllToRegistry(from, dynamicRegistry);
        });
    }

    private static <T> void copyAllToRegistry(Registry<T> from, Registry<T> to) {
        for (Map.Entry<RegistryKey<T>, T> entry : from.getEntries()) {
            Registry.register(to, entry.getKey().getValue(), entry.getValue());
        }
    }

    private final Path root;
    private final Consumer<Generator> generatorFunction;

    public TropicraftWorldgenProvider(DataGenerator dataGenerator, Consumer<Generator> generatorFunction) {
        this.root = dataGenerator.getOutput().resolve("data");
        this.generatorFunction = generatorFunction;
    }

    @Override
    public void run(DataCache cache) {
        DynamicRegistryManager.Impl dynamicRegistries = DYNAMIC_REGISTRIES.get();
        DynamicOps<JsonElement> ops = RegistryReadingOps.of(JsonOps.INSTANCE, dynamicRegistries);

        Generator generator = new Generator(root, cache, dynamicRegistries, ops);
        this.generatorFunction.accept(generator);
    }

    @Override
    public String getName() {
        return "Tropicraft Worldgen";
    }

    public static final class Generator {
        private final Path root;
        private final DataCache cache;
        private final DynamicRegistryManager.Impl dynamicRegistries;
        private final DynamicOps<JsonElement> ops;

        Generator(Path root, DataCache cache, DynamicRegistryManager.Impl dynamicRegistries, DynamicOps<JsonElement> ops) {
            this.root = root;
            this.cache = cache;
            this.dynamicRegistries = dynamicRegistries;
            this.ops = ops;
        }

        public <R> R addConfiguredFeatures(EntryGenerator<ConfiguredFeature<?, ?>, R> entryGenerator) {
            return add(
                    "worldgen/configured_feature", BuiltinRegistries.CONFIGURED_FEATURE, ConfiguredFeature.REGISTRY_CODEC,
                    entryGenerator
            );
        }

        public <R> R addConfiguredSurfaceBuilders(EntryGenerator<ConfiguredSurfaceBuilder<?>, R> entryGenerator) {
            return add(
                    "worldgen/configured_surface_builder", BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, ConfiguredSurfaceBuilder.REGISTRY_CODEC,
                    entryGenerator
            );
        }

        public <R> R addConfiguredCarvers(EntryGenerator<ConfiguredCarver<?>, R> entryGenerator) {
            return add(
                    "worldgen/configured_carver", BuiltinRegistries.CONFIGURED_CARVER, ConfiguredCarver.REGISTRY_CODEC,
                    entryGenerator
            );
        }

        public <R> R addProcessorLists(EntryGenerator<StructureProcessorList, R> entryGenerator) {
            return add(
                    "worldgen/processor_list", BuiltinRegistries.STRUCTURE_PROCESSOR_LIST, StructureProcessorType.REGISTRY_CODEC,
                    entryGenerator
            );
        }

        public <R> R addTemplatePools(EntryGenerator<StructurePool, R> entryGenerator) {
            return add(
                    "worldgen/template_pool", BuiltinRegistries.STRUCTURE_POOL, StructurePool.REGISTRY_CODEC,
                    entryGenerator
            );
        }

        public <R> R addConfiguredStructures(EntryGenerator<ConfiguredStructureFeature<?, ?>, R> entryGenerator) {
            return add(
                    "worldgen/configured_structure_feature", BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, ConfiguredStructureFeature.REGISTRY_CODEC,
                    entryGenerator
            );
        }

        public <R> R addBiomes(EntryGenerator<Biome, R> entryGenerator) {
            return add(
                    "worldgen/biome", null, Biome.REGISTRY_CODEC,
                    entryGenerator
            );
        }

        public <T, R> R add(
                String path, @Nullable Registry<T> registry, Codec<Supplier<T>> codec,
                EntryGenerator<T, R> entryGenerator
        ) {
            return entryGenerator.generate((id, entry) -> {
                Path entryPath = root.resolve(id.getNamespace()).resolve(path).resolve(id.getPath() + ".json");

                Function<Supplier<T>, DataResult<JsonElement>> function = ops.withEncoder(codec);

                try {
                    Optional<JsonElement> serialized = function.apply(() -> entry).result();
                    if (serialized.isPresent()) {
                        DataProvider.writeToPath(GSON, cache, serialized.get(), entryPath);
                    } else {
                        LOGGER.error("Couldn't serialize worldgen entry at {}", entryPath);
                    }
                } catch (IOException e) {
                    LOGGER.error("Couldn't save worldgen entry at {}", entryPath, e);
                }

                if (registry != null) {
                    Registry.register(registry, id, entry);
                    dynamicRegistries.getOptionalMutable(registry.getKey()).ifPresent(dynamicRegistry -> {
                        Registry.register(dynamicRegistry, id, entry);
                    });
                }

                return entry;
            });
        }
    }

    public interface EntryGenerator<T, R> {
        R generate(WorldgenDataConsumer<T> consumer);
    }
}
