package com.dragonseeker.tropicfabricport.registry;

import com.dragonseeker.tropicfabricport.entity.AshenMaskEntity;
import com.dragonseeker.tropicfabricport.entity.BambooItemFrameEntity;
import com.dragonseeker.tropicfabricport.entity.ExplodingCoconutEntity;
import com.dragonseeker.tropicfabricport.entity.WallItemEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Direction;

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

        ClientSidePacketRegistry.INSTANCE.register(BambooItemFrameEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUuid();

            Direction direction = Direction.byId(packet.readInt());
            context.getTaskQueue().execute(() -> {
                BambooItemFrameEntity bambooFrame = new BambooItemFrameEntity(MinecraftClient.getInstance().world, x, y, z, direction, entityID, entityUUID);;
                MinecraftClient.getInstance().world.addEntity(entityID, bambooFrame);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(WallItemEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUuid();

            Direction direction = Direction.byId(packet.readInt());
            context.getTaskQueue().execute(() -> {
                WallItemEntity wallItem = new WallItemEntity(MinecraftClient.getInstance().world, x, y, z, direction, entityID, entityUUID);;
                MinecraftClient.getInstance().world.addEntity(entityID, wallItem);
            });
        });

    }
}
