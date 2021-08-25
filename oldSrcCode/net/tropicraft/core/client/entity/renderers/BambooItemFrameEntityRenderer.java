package net.tropicraft.core.client.entity.renderers;

import net.tropicraft.core.common.entity.BambooItemFrameEntity;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapState;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

public class BambooItemFrameEntityRenderer extends EntityRenderer<BambooItemFrameEntity> {
    private static final String ItemIdName = "bamboo_item_frame";
    public static final ModelIdentifier LOCATION_MODEL = new ModelIdentifier(ItemIdName, "map=false");
    private static final ModelIdentifier LOCATION_MODEL_MAP = new ModelIdentifier(ItemIdName, "map=true");

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final ItemRenderer itemRenderer;

    public BambooItemFrameEntityRenderer(EntityRenderDispatcher entityRenderDispatcher, EntityRendererRegistry.Context context) {
        super(entityRenderDispatcher);
        itemRenderer = context.getItemRenderer();
    }


    @Override
    public void render(BambooItemFrameEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.push();
        Direction direction = entityIn.getHorizontalFacing();
        Vec3d vec3d = this.getPositionOffset(entityIn, partialTicks);
        matrixStackIn.translate(-vec3d.getX(), -vec3d.getY(), -vec3d.getZ());
        double d0 = 0.46875D;

        matrixStackIn.translate((double) direction.getOffsetX() * 0.46875D, (double) direction.getOffsetY() * 0.46875D, (double) direction.getOffsetZ() * 0.46875D);
        matrixStackIn.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(entityIn.pitch)); //matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entityIn.rotationPitch));
        matrixStackIn.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F - entityIn.yaw));

        boolean isFrameInvis = entityIn.isInvisible();

        if (isFrameInvis == false) {
            BlockRenderManager blockrendererdispatcher = this.mc.getBlockRenderManager();
            BakedModelManager modelmanager = blockrendererdispatcher.getModels().getModelManager();
            ModelIdentifier modelresourcelocation = entityIn.getHeldItemStack().getItem() instanceof FilledMapItem ? LOCATION_MODEL_MAP : LOCATION_MODEL;
            matrixStackIn.push();
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            blockrendererdispatcher.getModelRenderer().render(matrixStackIn.peek(), bufferIn.getBuffer(TexturedRenderLayers.getEntitySolid()), null, modelmanager.getModel(modelresourcelocation), 1.0F, 1.0F, 1.0F, packedLightIn, OverlayTexture.DEFAULT_UV);
            matrixStackIn.pop();
        }

        ItemStack itemstack = entityIn.getHeldItemStack();
        if (!itemstack.isEmpty()) {
            MapState mapstate = FilledMapItem.getMapState(itemstack, entityIn.world);
            matrixStackIn.translate(0.0D, 0.0D, 0.4375D);

            if (isFrameInvis == true) {
                matrixStackIn.translate(0.0D, 0.0D, 0.5D);
            }

            else {
                matrixStackIn.translate(0.0D, 0.0D, 0.4375D);
            }

            int i = mapstate != null ? entityIn.getRotation() % 4 * 2 : entityIn.getRotation();
            matrixStackIn.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) i * 360.0F / 8.0F));
            if (mapstate != null) {
                matrixStackIn.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
                float f = 0.0078125F;
                matrixStackIn.scale(0.0078125F, 0.0078125F, 0.0078125F);
                matrixStackIn.translate(-64.0D, -64.0D, 0.0D);
                matrixStackIn.translate(0.0D, 0.0D, -1.0D);
                if (mapstate != null) {
                    this.mc.gameRenderer.getMapRenderer().draw(matrixStackIn, bufferIn, mapstate, true, packedLightIn);
                }
            } else {
                matrixStackIn.scale(0.5F, 0.5F, 0.5F);
                this.itemRenderer.renderItem(itemstack, ModelTransformation.Mode.FIXED, packedLightIn, OverlayTexture.DEFAULT_UV, matrixStackIn, bufferIn);
            }
        }

        matrixStackIn.pop();
    }

    public Vec3d getPositionOffset(BambooItemFrameEntity entityIn, float partialTicks) {
        return new Vec3d((float) entityIn.getHorizontalFacing().getOffsetX() * 0.3F, -0.25D, (float) entityIn.getHorizontalFacing().getOffsetZ() * 0.3F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    @Override
    public Identifier getTexture(BambooItemFrameEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
        //return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }

    protected boolean hasLabel(BambooItemFrameEntity entity) {
        if (MinecraftClient.isHudEnabled() && !entity.getHeldItemStack().isEmpty() && entity.getHeldItemStack().hasCustomName() && this.dispatcher.targetedEntity == entity) {
            double dist = this.dispatcher.getSquaredDistanceToCamera(entity);
            float f = entity.isSneaky() ? 32.0F : 64.0F;
            return dist < (double) (f * f);
        } else {
            return false;
        }
    }

    protected void renderLabelIfPresent(BambooItemFrameEntity entityIn, Text displayNameIn, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn) {
        super.renderLabelIfPresent(entityIn, entityIn.getHeldItemStack().getName(), matrixStackIn, bufferIn, packedLightIn);

    }
}
