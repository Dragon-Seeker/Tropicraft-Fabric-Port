package net.tropicraft.core.client.registry;

import net.tropicraft.core.common.entity.placeable.*;
import net.tropicraft.core.common.entity.BambooItemFrameEntity;
import net.tropicraft.core.common.entity.projectile.ExplodingCoconutEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
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
            UUID entityUUID = packet.readUUID();

            context.getTaskQueue().execute(() -> {
                AshenMaskEntity ashenMask = new AshenMaskEntity(Minecraft.getInstance().level, x, y, z, entityID, entityUUID);
                Minecraft.getInstance().level.putNonPlayerEntity(entityID, ashenMask);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(ExplodingCoconutEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUUID();

            context.getTaskQueue().execute(() -> {
                ExplodingCoconutEntity explCoconut = new ExplodingCoconutEntity(Minecraft.getInstance().level, x, y, z, entityID, entityUUID);
                Minecraft.getInstance().level.putNonPlayerEntity(entityID, explCoconut);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(BambooItemFrameEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUUID();

            Direction direction = Direction.from3DDataValue(packet.readInt());
            context.getTaskQueue().execute(() -> {
                BambooItemFrameEntity bambooFrame = new BambooItemFrameEntity(TropicraftEntities.BAMBOO_ITEM_FRAME, Minecraft.getInstance().level, x, y, z, direction, entityID, entityUUID);;
                Minecraft.getInstance().level.putNonPlayerEntity(entityID, bambooFrame);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(WallItemEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUUID();
            //Identifier itemID = packet.readIdentifier();

            Direction direction = Direction.from3DDataValue(packet.readInt());
            context.getTaskQueue().execute(() -> {
                WallItemEntity wallItem = new WallItemEntity(TropicraftEntities.WALL_ITEM, Minecraft.getInstance().level, x, y, z, direction, entityID, entityUUID);
                Minecraft.getInstance().level.putNonPlayerEntity(entityID, wallItem);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(LavaBallEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUUID();

            context.getTaskQueue().execute(() -> {
                LavaBallEntity lavaBall = new LavaBallEntity(Minecraft.getInstance().level, x, y, z, entityID, entityUUID);
                Minecraft.getInstance().level.putNonPlayerEntity(entityID, lavaBall);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(PoisonBlotEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUUID();

            context.getTaskQueue().execute(() -> {
                PoisonBlotEntity poisonBlot = new PoisonBlotEntity(Minecraft.getInstance().level, x, y, z, entityID, entityUUID);
                Minecraft.getInstance().level.putNonPlayerEntity(entityID, poisonBlot);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(BeachFloatEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUUID();

            context.getTaskQueue().execute(() -> {
                BeachFloatEntity beachFloat = new BeachFloatEntity(Minecraft.getInstance().level, x, y, z, entityID, entityUUID);
                Minecraft.getInstance().level.putNonPlayerEntity(entityID, beachFloat);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(ChairEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUUID();

            context.getTaskQueue().execute(() -> {
                ChairEntity chair = new ChairEntity(Minecraft.getInstance().level, x, y, z, entityID, entityUUID);
                Minecraft.getInstance().level.putNonPlayerEntity(entityID, chair);
            });
        });

        ClientSidePacketRegistry.INSTANCE.register(UmbrellaEntity.SPAWN_PACKET, (context, packet) -> {
            double x = packet.readDouble();
            double y = packet.readDouble();
            double z = packet.readDouble();

            int entityID = packet.readInt();
            UUID entityUUID = packet.readUUID();

            context.getTaskQueue().execute(() -> {
                UmbrellaEntity chair = new UmbrellaEntity(Minecraft.getInstance().level, x, y, z, entityID, entityUUID);
                Minecraft.getInstance().level.putNonPlayerEntity(entityID, chair);
            });
        });

    }
}
