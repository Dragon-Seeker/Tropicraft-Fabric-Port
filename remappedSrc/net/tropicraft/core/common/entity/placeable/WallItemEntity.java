package net.tropicraft.core.common.entity.placeable;

import net.tropicraft.Constants;
import net.tropicraft.core.common.entity.BambooItemFrameEntity;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.tropicraft.core.common.registry.TropicraftEntities;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class WallItemEntity extends BambooItemFrameEntity {

	public static ResourceLocation SPAWN_PACKET = new ResourceLocation(Constants.MODID, "wall_item");

    public WallItemEntity(EntityType<? extends BambooItemFrameEntity> entityType, Level world) {
        super((EntityType<BambooItemFrameEntity>) entityType, world);
    }

	public WallItemEntity(Level worldIn, BlockPos pos, Direction on) {
		super(TropicraftEntities.WALL_ITEM, worldIn, pos, on);
	}

	@Environment(EnvType.CLIENT)
	public WallItemEntity(Level world, double x, double y, double z, Direction facing, int id, UUID uuid) {
		super(TropicraftEntities.BAMBOO_ITEM_FRAME, world);
		absMoveTo(x, y, z);
		setPacketCoordinates(x, y, z);
		setId(id);
		setUUID(uuid);
		setDirection(facing);
	}

	@Override
	public int getWidth() {
		return 16;
	}

	@Override
	public int getHeight() {
		return 16;
	}

    @Override
    public void invokedropHeldStack(@Nullable Entity entityIn, boolean p_146065_2_) {
    	super.invokedropHeldStack(entityIn, false);
    	this.remove();
    }

    @Override
    public void playPlacementSound() {
    }

	@Override
	public Packet<?> getAddEntityPacket() {
		//return new EntitySpawnS2CPacket(this, this.getType(), this.facing.getId(), this.getDecorationBlockPos());

		FriendlyByteBuf packet = new FriendlyByteBuf(Unpooled.buffer());

		// entity position
		packet.writeDouble(getX());
		packet.writeDouble(getY());
		packet.writeDouble(getZ());

		// entity id & uuid
		packet.writeInt(getId());
		packet.writeUUID(getUUID());

		//Entity Facing direction
		packet.writeInt(getDirection().get3DDataValue());

		return ServerSidePacketRegistry.INSTANCE.toPacket(SPAWN_PACKET, packet);

	}

	/*
	@Override
	public ItemStack getPickedResult(HitResult target) {
		return getHeldItemStack();
	}

	 */
}
