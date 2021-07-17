package com.dragonseeker.tropicfabricport.registry;

import com.dragonseeker.tropicfabricport.entity.AshenMaskEntity;
import com.dragonseeker.tropicfabricport.entity.ExplodingCoconutEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class TropicClientPackets {

    public static void init() {
        ClientSidePacketRegistry.INSTANCE.register(AshenMaskEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUuid();

            context.getTaskQueue().execute(() -> {
                AshenMaskEntity ashenMask = new AshenMaskEntity(MinecraftClient.getInstance().world, x, y, z, entityID, entityUUID);
                MinecraftClient.getInstance().world.addEntity(entityID, ashenMask);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(ExplodingCoconutEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUuid();

            context.getTaskQueue().execute(() -> {
                ExplodingCoconutEntity explCoconut = new ExplodingCoconutEntity(MinecraftClient.getInstance().world, x, y, z, entityID, entityUUID);
                MinecraftClient.getInstance().world.addEntity(entityID, explCoconut);
            });
        });
    }
}
