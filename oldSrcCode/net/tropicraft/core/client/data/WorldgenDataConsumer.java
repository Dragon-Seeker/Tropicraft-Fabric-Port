package net.tropicraft.core.client.data;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;

public interface WorldgenDataConsumer<T> {
    T register(Identifier id, T entry);

    default T register(RegistryKey<T> id, T entry) {
        return this.register(id.getValue(), entry);
    }
}
