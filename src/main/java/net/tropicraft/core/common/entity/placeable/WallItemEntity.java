package net.tropicraft.core.common.entity.placeable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import net.tropicraft.Constants;
import net.tropicraft.core.common.entity.BambooItemFrameEntity;
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
import net.tropicraft.core.common.registry.TropicraftEntities;
import net.tropicraft.core.common.registry.TropicraftItems;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class WallItemEntity extends BambooItemFrameEntity {

	public static Identifier SPAWN_PACKET = new Identifier(Constants.MODID, "wall_item");

    public WallItemEntity(EntityType<? extends BambooItemFrameEntity> entityType, World world) {
        super(entityType, world);
    }

	public WallItemEntity(World worldIn, BlockPos pos, Direction on) {
		super(TropicraftEntities.WALL_ITEM, worldIn, pos, on);

	}

	@Environment(EnvType.CLIENT)
	public WallItemEntity(EntityType<? extends BambooItemFrameEntity> entityType, World world, double x, double y, double z, Direction facing, int id, UUID uuid) {
		super(entityType, world, x, y, z, facing, id, uuid);
	}

	@Override
	public int getWidthPixels() {
		return 16;
	}

	@Override
	public int getHeightPixels() {
		return 16;
	}

	//@Override
	//protected ItemStack getAsItemStack() {
	//	return ItemStack.EMPTY;
	//}


	@Override
	protected void dropHeldStack(@Nullable Entity entity, boolean alwaysDrop) {
		super.dropHeldStack(entity, false);
		this.remove(RemovalReason.KILLED);
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
		packet.writeInt(getId());
		packet.writeUuid(getUuid());

		//Entity Facing direction

		//packet.writeIdentifier(Registry.ITEM.getId(getAsItemStack().getItem()));

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
