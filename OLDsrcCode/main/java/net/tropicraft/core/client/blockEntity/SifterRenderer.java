package net.tropicraft.core.client.blockEntity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;
import net.tropicraft.core.common.block.blockentity.SifterTileEntity;

public class SifterRenderer implements BlockEntityRenderer<SifterTileEntity> {
	private ItemEntity item;

	public SifterRenderer(final BlockEntityRendererFactory.Context ctx) {
		//super(rendererDispatcher);
	}

	@Override
	public void render(SifterTileEntity sifter, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrixStackIn.push();
		matrixStackIn.translate(0.5D, 0.0D, 0.5D);

		if (!sifter.isSifting()) {
			item = null;
		} else if (!sifter.getSiftItem().isEmpty()) {
			final World world = sifter.getWorld();
			final float itemRenderSize = 0.4375F;

			if (item == null) {
				item = new ItemEntity(EntityType.ITEM, world);
				item.setStack(sifter.getSiftItem().copy());
				//TODO: Figure what command call is needed
				//item.setWorld(world);
			}

			matrixStackIn.translate(0.0D, 0.4F, 0.0D);
			matrixStackIn.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float)(sifter.yaw2 + (sifter.yaw - sifter.yaw2) * (double)partialTicks) * 10.0F));
			matrixStackIn.translate(0.0D, -0.4F, 0.0D);
			matrixStackIn.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-20.0F));
			matrixStackIn.scale(itemRenderSize * 3, itemRenderSize * 3, itemRenderSize * 3);
			final int light = WorldRenderer.getLightmapCoordinates(world, sifter.getPos().up());
			MinecraftClient.getInstance().getEntityRenderDispatcher().render(item, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, matrixStackIn, bufferIn, light);
		}

		matrixStackIn.pop();
	}
}
