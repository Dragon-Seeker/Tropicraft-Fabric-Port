package net.tropicraftFabric.common.dimension.carver;

import net.minecraft.util.Identifier;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CarverConfig;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.tropicraftFabric.Constants;
import net.tropicraftFabric.client.data.WorldgenDataConsumer;

public final class TropicraftConfiguredCarvers {
    public final ConfiguredCarver<?> cave;
    public final ConfiguredCarver<?> canyon;
    public final ConfiguredCarver<?> underwaterCave;
    public final ConfiguredCarver<?> underwaterCanyon;

    public TropicraftConfiguredCarvers(WorldgenDataConsumer<ConfiguredCarver<?>> worldgen) {
        Register carvers = new Register(worldgen);

        this.cave = carvers.register("cave", TropicraftCarvers.CAVE, new ProbabilityConfig(0.25F));
        this.canyon = carvers.register("canyon", TropicraftCarvers.CANYON, new ProbabilityConfig(0.02F));
        this.underwaterCave = carvers.register("underwater_cave", TropicraftCarvers.UNDERWATER_CAVE, new ProbabilityConfig(0.15F));
        this.underwaterCanyon = carvers.register("underwater_canyon", TropicraftCarvers.UNDERWATER_CANYON, new ProbabilityConfig(0.02F));
    }

    public void addLand(GenerationSettings.Builder generation) {
        generation.carver(GenerationStep.Carver.AIR, this.cave);
        generation.carver(GenerationStep.Carver.AIR, this.canyon);
    }

    public void addUnderwater(GenerationSettings.Builder generation) {
        generation.carver(GenerationStep.Carver.LIQUID, this.underwaterCave);
        generation.carver(GenerationStep.Carver.LIQUID, this.underwaterCanyon);
    }

    static final class Register {
        private final WorldgenDataConsumer<ConfiguredCarver<?>> worldgen;

        Register(WorldgenDataConsumer<ConfiguredCarver<?>> worldgen) {
            this.worldgen = worldgen;
        }

        public <C extends CarverConfig, WC extends Carver<C>> ConfiguredCarver<?> register(String id, WC carver, C config) {
            return this.worldgen.register(new Identifier(Constants.MODID, id), carver.configure(config));
        }
    }
}
