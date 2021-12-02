package net.tropicraft.core.client.entity.renderers;

import net.tropicraft.core.common.entity.BambooItemFrameEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;

public class BambooItemFrameEntityRenderer extends EntityRenderer<BambooItemFrameEntity> {
    private static final String ItemIdName = "bamboo_item_frame";
    public static final ModelResourceLocation LOCATION_MODEL = new ModelResourceLocation(ItemIdName, "map=false");
    private static final ModelResourceLocation LOCATION_MODEL_MAP = new ModelResourceLocation(ItemIdName, "map=true");

    private final Minecraft mc = Minecraft.getInstance();
    private final ItemRenderer itemRenderer;

    public BambooItemFrameEntityRenderer(EntityRenderDispatcher entityRenderDispatcher, EntityRendererRegistry.Context context) {
        super(entityRenderDispatcher);
        itemRenderer = context.getItemRenderer();
    }


    @Override
    public void render(BambooItemFrameEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.pushPose();
        Direction direction = entityIn.getDirection();
        Vec3 vec3d = this.getPositionOffset(entityIn, partialTicks);
        matrixStackIn.translate(-vec3d.x(), -vec3d.y(), -vec3d.z());
        double d0 = 0.46875D;

        matrixStackIn.translate((double) direction.getStepX() * 0.46875D, (double) direction.getStepY() * 0.46875D, (double) direction.getStepZ() * 0.46875D);
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(entityIn.xRot)); //matrixStackIn.rotate(Vector3f.XP.rotationDegrees(entityIn.rotationPitch));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F - entityIn.yRot));

        boolean isFrameInvis = entityIn.isInvisible();

        if (isFrameInvis == false) {
            BlockRenderDispatcher blockrendererdispatcher = this.mc.getBlockRenderer();
            ModelManager modelmanager = blockrendererdispatcher.getBlockModelShaper().getModelManager();
            ModelResourceLocation modelresourcelocation = entityIn.getItem().getItem() instanceof MapItem ? LOCATION_MODEL_MAP : LOCATION_MODEL;
            matrixStackIn.pushPose();
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            blockrendererdispatcher.getModelRenderer().renderModel(matrixStackIn.last(), bufferIn.getBuffer(Sheets.solidBlockSheet()), null, modelmanager.getModel(modelresourcelocation), 1.0F, 1.0F, 1.0F, packedLightIn, OverlayTexture.NO_OVERLAY);
            matrixStackIn.popPose();
        }

        ItemStack itemstack = entityIn.getItem();
        if (!itemstack.isEmpty()) {
            MapItemSavedData mapstate = MapItem.getSavedData(itemstack, entityIn.level);
            matrixStackIn.translate(0.0D, 0.0D, 0.4375D);

            if (isFrameInvis == true) {
                matrixStackIn.translate(0.0D, 0.0D, 0.5D);
            }

            else {
                matrixStackIn.translate(0.0D, 0.0D, 0.4375D);
            }

            int i = mapstate != null ? entityIn.getRotation() % 4 * 2 : entityIn.getRotation();
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees((float) i * 360.0F / 8.0F));
            if (mapstate != null) {
                matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
                float f = 0.0078125F;
                matrixStackIn.scale(0.0078125F, 0.0078125F, 0.0078125F);
                matrixStackIn.translate(-64.0D, -64.0D, 0.0D);
                matrixStackIn.translate(0.0D, 0.0D, -1.0D);
                if (mapstate != null) {
                    this.mc.gameRenderer.getMapRenderer().render(matrixStackIn, bufferIn, mapstate, true, packedLightIn);
                }
            } else {
                matrixStackIn.scale(0.5F, 0.5F, 0.5F);
                this.itemRenderer.renderStatic(itemstack, ItemTransforms.TransformType.FIXED, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
            }
        }

        matrixStackIn.popPose();
    }

    public Vec3 getPositionOffset(BambooItemFrameEntity entityIn, float partialTicks) {
        return new Vec3((float) entityIn.getDirection().getStepX() * 0.3F, -0.25D, (float) entityIn.getDirection().getStepZ() * 0.3F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    @Override
    public ResourceLocation getTexture(BambooItemFrameEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
        //return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }

    protected boolean hasLabel(BambooItemFrameEntity entity) {
        if (Minecraft.renderNames() && !entity.getItem().isEmpty() && entity.getItem().hasCustomHoverName() && this.entityRenderDispatcher.crosshairPickEntity == entity) {
            double dist = this.entityRenderDispatcher.distanceToSqr(entity);
            float f = entity.isDiscrete() ? 32.0F : 64.0F;
            return dist < (double) (f * f);
        } else {
            return false;
        }
    }

    protected void renderLabelIfPresent(BambooItemFrameEntity entityIn, Component displayNameIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.renderNameTag(entityIn, entityIn.getItem().getHoverName(), matrixStackIn, bufferIn, packedLightIn);

    }
}
