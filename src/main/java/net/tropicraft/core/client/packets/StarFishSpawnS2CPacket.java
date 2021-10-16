package net.tropicraft.core.client.packets;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.world.entity.LivingEntity;
import net.tropicraft.core.common.entity.egg.StarfishEggEntity;
import net.tropicraft.core.common.entity.underdasea.StarfishEntity;

import java.io.IOException;

public class StarFishSpawnS2CPacket extends ClientboundAddMobPacket {
    private byte starFishType;

    public StarFishSpawnS2CPacket(LivingEntity entity){
        super(entity);
        if(entity instanceof StarfishEntity){
            starFishType = (byte) ((StarfishEntity) entity).getStarfishType().ordinal();
        }

        else if(entity instanceof StarfishEggEntity){
            starFishType = (byte) ((StarfishEggEntity) entity).getStarfishType().ordinal();
        }

        else{
            starFishType = (byte) 0;
        }

    }

    //TODO: Fix/Update packets to support 1.17
    /*
    @Override
    public void read(PacketByteBuf buf) throws IOException {
        super.read(buf);
    }

    @Override
    public void write(PacketByteBuf buf) throws IOException {
        super.write(buf);
    }
     */

    @Environment(EnvType.CLIENT)
    public byte getStarFishType() {
        return this.starFishType;
    }
}
