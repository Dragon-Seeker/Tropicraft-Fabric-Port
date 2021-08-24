package net.tropicraft.core.common.entity.placeable;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.tropicraft.Constants;
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.tropicraft.core.common.registry.TropicraftItems;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

import java.util.UUID;

public class UmbrellaEntity extends FurnitureEntity {

    public static Identifier SPAWN_PACKET = new Identifier(Constants.MODID, "furniture_entity_umbrella");

    public UmbrellaEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn, TropicraftItems.UMBRELLAS);
    }

    @Environment(EnvType.CLIENT)
    public UmbrellaEntity(World world, double x, double y, double z, int id, UUID uuid) {
        super(TropicraftEntities.UMBRELLA, world, TropicraftItems.UMBRELLAS);
        updatePosition(x, y, z);
        updateTrackedPosition(x, y, z);
        setId(id);
        setUuid(uuid);
    }

    /*
    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(TropicraftItems.UMBRELLAS.get(DyeColor.byId(getColor().getId())));
    }
     */

    @Override
    public Packet<?> createSpawnPacket() {
        //return new EntitySpawnS2CPacket(this);

        //NetworkHooks.getEntitySpawningPacket(this);

        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());

        // entity position
        packet.writeDouble(getX());
        packet.writeDouble(getY());
        packet.writeDouble(getZ());

        // entity id & uuid
        packet.writeInt(getId());
        packet.writeUuid(getUuid());

        return ServerSidePacketRegistry.INSTANCE.toPacket(SPAWN_PACKET, packet);
    }
}
