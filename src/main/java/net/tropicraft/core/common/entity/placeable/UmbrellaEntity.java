package net.tropicraft.core.common.entity.placeable;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.tropicraft.Constants;
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.tropicraft.core.common.registry.TropicraftItems;
import java.util.UUID;

public class UmbrellaEntity extends FurnitureEntity {

    public static ResourceLocation SPAWN_PACKET = new ResourceLocation(Constants.MODID, "furniture_entity_umbrella");

    public UmbrellaEntity(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn, TropicraftItems.UMBRELLAS);
    }

    @Environment(EnvType.CLIENT)
    public UmbrellaEntity(Level world, double x, double y, double z, int id, UUID uuid) {
        super(TropicraftEntities.UMBRELLA, world, TropicraftItems.UMBRELLAS);
        absMoveTo(x, y, z);
        setPacketCoordinates(x, y, z);
        setId(id);
        setUUID(uuid);
    }

    /*
    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(TropicraftItems.UMBRELLAS.get(DyeColor.byId(getColor().getId())));
    }
     */

    @Override
    public Packet<?> getAddEntityPacket() {
        //return new EntitySpawnS2CPacket(this);

        //NetworkHooks.getEntitySpawningPacket(this);

        FriendlyByteBuf packet = new FriendlyByteBuf(Unpooled.buffer());

        // entity position
        packet.writeDouble(getX());
        packet.writeDouble(getY());
        packet.writeDouble(getZ());

        // entity id & uuid
        packet.writeInt(getId());
        packet.writeUUID(getUUID());

        return ServerSidePacketRegistry.INSTANCE.toPacket(SPAWN_PACKET, packet);
    }
}
