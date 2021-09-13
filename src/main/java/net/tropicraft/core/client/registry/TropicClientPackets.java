package net.tropicraft.core.client.registry;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.tropicraft.core.common.entity.placeable.*;
import net.tropicraft.core.common.entity.BambooItemFrameEntity;
import net.tropicraft.core.common.entity.projectile.ExplodingCoconutEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Direction;
import net.tropicraft.core.common.entity.projectile.LavaBallEntity;
import net.tropicraft.core.common.entity.projectile.PoisonBlotEntity;
import net.tropicraft.core.common.registry.TropicraftEntities;

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
                BambooItemFrameEntity bambooFrame = new BambooItemFrameEntity(TropicraftEntities.BAMBOO_ITEM_FRAME, MinecraftClient.getInstance().world, x, y, z, direction, entityID, entityUUID);;
                MinecraftClient.getInstance().world.addEntity(entityID, bambooFrame);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(WallItemEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUuid();
            //Identifier itemID = packet.readIdentifier();

            Direction direction = Direction.byId(packet.readInt());
            context.getTaskQueue().execute(() -> {
                WallItemEntity wallItem = new WallItemEntity(TropicraftEntities.WALL_ITEM, MinecraftClient.getInstance().world, x, y, z, direction, entityID, entityUUID);
                MinecraftClient.getInstance().world.addEntity(entityID, wallItem);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(LavaBallEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUuid();

            context.getTaskQueue().execute(() -> {
                LavaBallEntity lavaBall = new LavaBallEntity(MinecraftClient.getInstance().world, x, y, z, entityID, entityUUID);
                MinecraftClient.getInstance().world.addEntity(entityID, lavaBall);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(PoisonBlotEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUuid();

            context.getTaskQueue().execute(() -> {
                PoisonBlotEntity poisonBlot = new PoisonBlotEntity(MinecraftClient.getInstance().world, x, y, z, entityID, entityUUID);
                MinecraftClient.getInstance().world.addEntity(entityID, poisonBlot);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(BeachFloatEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUuid();

            context.getTaskQueue().execute(() -> {
                BeachFloatEntity beachFloat = new BeachFloatEntity(MinecraftClient.getInstance().world, x, y, z, entityID, entityUUID);
                MinecraftClient.getInstance().world.addEntity(entityID, beachFloat);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(ChairEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUuid();

            context.getTaskQueue().execute(() -> {
                ChairEntity chair = new ChairEntity(MinecraftClient.getInstance().world, x, y, z, entityID, entityUUID);
                MinecraftClient.getInstance().world.addEntity(entityID, chair);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(UmbrellaEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUuid();

            context.getTaskQueue().execute(() -> {
                UmbrellaEntity chair = new UmbrellaEntity(MinecraftClient.getInstance().world, x, y, z, entityID, entityUUID);
                MinecraftClient.getInstance().world.addEntity(entityID, chair);
            });
        });

    }
}
