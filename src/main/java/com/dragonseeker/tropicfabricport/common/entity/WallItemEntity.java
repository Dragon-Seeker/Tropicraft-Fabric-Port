package com.dragonseeker.tropicfabricport.common.entity;

import com.dragonseeker.tropicfabricport.Tropicfabricport;
import com.dragonseeker.tropicfabricport.common.registry.TropicEntities;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class WallItemEntity extends BambooItemFrameEntity {

	public static Identifier SPAWN_PACKET = new Identifier(Tropicfabricport.MOD_ID, "wall_item");

    public WallItemEntity(EntityType<? extends BambooItemFrameEntity> entityType, World world) {
        super((EntityType<BambooItemFrameEntity>) entityType, world);
    }

	public WallItemEntity(World worldIn, BlockPos pos, Direction on) {
		super(TropicEntities.WALL_ITEM, worldIn, pos, on);
	}

	@Environment(EnvType.CLIENT)
	public WallItemEntity(World world, double x, double y, double z, Direction facing, int id, UUID uuid) {
		super(TropicEntities.BAMBOO_ITEM_FRAME, world);
		updatePosition(x, y, z);
		updateTrackedPosition(x, y, z);
		setEntityId(id);
		setUuid(uuid);
		setFacing(facing);
	}

	@Override
	public int getWidthPixels() {
		return 16;
	}

	@Override
	public int getHeightPixels() {
		return 16;
	}

    @Override
    public void invokedropHeldStack(@Nullable Entity entityIn, boolean p_146065_2_) {
    	super.invokedropHeldStack(entityIn, false);
    	this.remove();
    }

    @Override
    public void onPlace() {
    }

	@Override
	public Packet<?> createSpawnPacket() {
		//return new EntitySpawnS2CPacket(this, this.getType(), this.facing.getId(), this.getDecorationBlockPos());

		PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());

		// entity position
		packet.writeDouble(getX());
		packet.writeDouble(getY());
		packet.writeDouble(getZ());

		// entity id & uuid
		packet.writeInt(getEntityId());
		packet.writeUuid(getUuid());

		//Entity Facing direction
		packet.writeInt(getHorizontalFacing().getId());

		return ServerSidePacketRegistry.INSTANCE.toPacket(SPAWN_PACKET, packet);

	}

	/*
	@Override
	public ItemStack getPickedResult(HitResult target) {
		return getHeldItemStack();
	}

	 */
}
