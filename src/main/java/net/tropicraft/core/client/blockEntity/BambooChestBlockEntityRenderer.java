package net.tropicraft.core.client.blockEntity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.tropicraft.Constants;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.common.block.blockentity.TropicBambooChestBlockEntity;
import net.tropicraft.core.common.registry.TropicraftBlocks;
import net.tropicraft.core.common.registry.TropicraftItems;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.LightmapCoordinatesRetriever;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.HashMap;

public class BambooChestBlockEntityRenderer<T extends BlockEntity & ChestAnimationProgress> implements BlockEntityRenderer<TropicBambooChestBlockEntity> {
    private static final HashMap<Block, RenderLayer[]> LAYERS = Maps.newHashMap();
    private static final RenderLayer[] defaultLayer;
    private static final RenderLayer[] bambooChestLayer;

    private static ItemStack stack = new ItemStack(TropicraftItems.BAMBOO_CHEST, 1);


    private static final int ID_NORMAL = 0;
    private static final int ID_LEFT = 1;
    private static final int ID_RIGHT = 2;

    private boolean christmas;
    private final ModelPart partA; //singleChestLid
    private final ModelPart partC; //singleChestBase
    private final ModelPart partB; //singleChestLatch
    private final ModelPart partRightA; //doubleChestRightLid
    private final ModelPart partRightC; //doubleChestRightBase
    private final ModelPart partRightB; //doubleChestRightLatch
    private final ModelPart partLeftA;  //doubleChestLeftLid
    private final ModelPart partLeftC;  //doubleChestLeftBase
    private final ModelPart partLeftB; //doubleChestLeftLatch


    public BambooChestBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(TropicraftEntityRendering.BAMBOO_CHEST);
        this.partC = modelPart.getChild("bottom");
        this.partA = modelPart.getChild("lid");
        this.partB = modelPart.getChild("lock");

        ModelPart doubleChestLeft = ctx.getLayerModelPart(TropicraftEntityRendering.BAMBOO_DOUBLE_CHEST_RIGHT);
        this.partRightC = doubleChestLeft.getChild("bottom");
        this.partRightA = doubleChestLeft.getChild("lid");
        this.partRightB = doubleChestLeft.getChild("lock");

        ModelPart doubleChestRight = ctx.getLayerModelPart(TropicraftEntityRendering.BAMBOO_DOUBLE_CHEST_LEFT);
        this.partLeftC = doubleChestRight.getChild("bottom");
        this.partLeftA = doubleChestRight.getChild("lid");
        this.partLeftB = doubleChestRight.getChild("lock");

    }

    public static TexturedModelData getSingleTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        /*
        this.partC = new ModelPart(64, 64, 0, 19);
        this.partC.addCuboid(1.0F, 0.0F, 1.0F, 14.0F, 9.0F, 14.0F, 0.0F);

        this.partA = new ModelPart(64, 64, 0, 0);
        this.partA.addCuboid(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
        this.partA.pivotY = 9.0F;
        this.partA.pivotZ = 1.0F;

        this.partB = new ModelPart(64, 64, 0, 0);
        this.partB.addCuboid(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
        this.partB.pivotY = 8.0F;
         */

        modelPartData.addChild("bottom", ModelPartBuilder.create().uv(0, 19).cuboid(1.0F, 0.0F, 1.0F, 14.0F, 9.0F, 14.0F), ModelTransform.NONE);
        modelPartData.addChild("lid", ModelPartBuilder.create().uv(0, 0).cuboid(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F), ModelTransform.pivot(0.0F, 9.0F, 1.0F));
        modelPartData.addChild("lock", ModelPartBuilder.create().uv(0, 0).cuboid(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F), ModelTransform.pivot(0.0F, 8.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    public static TexturedModelData getRightDoubleTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        /*
        this.partRightC = new ModelPart(64, 64, 0, 19);
        this.partRightC.addCuboid(1.0F, 0.0F, 1.0F, 15.0F, 9.0F, 14.0F, 0.0F);

        this.partRightA = new ModelPart(64, 64, 0, 0);
        this.partRightA.addCuboid(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.partRightA.pivotY = 9.0F;
        this.partRightA.pivotZ = 1.0F;

        this.partRightB = new ModelPart(64, 64, 0, 0);
        this.partRightB.addCuboid(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.partRightB.pivotY = 8.0F;
         */

        modelPartData.addChild("bottom", ModelPartBuilder.create().uv(0, 19).cuboid(1.0F, 0.0F, 1.0F, 15.0F, 9.0F, 14.0F), ModelTransform.NONE);
        modelPartData.addChild("lid", ModelPartBuilder.create().uv(0, 0).cuboid(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F), ModelTransform.pivot(0.0F, 9.0F, 1.0F));
        modelPartData.addChild("lock", ModelPartBuilder.create().uv(0, 0).cuboid(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F), ModelTransform.pivot(0.0F, 8.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    public static TexturedModelData getLeftDoubleTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        /*
        this.partLeftC = new ModelPart(64, 64, 0, 19);
        this.partLeftC.addCuboid(0.0F, 0.0F, 1.0F, 15.0F, 9.0F, 14.0F, 0.0F);

        this.partLeftA = new ModelPart(64, 64, 0, 0);
        this.partLeftA.addCuboid(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.partLeftA.pivotY = 9.0F;
        this.partLeftA.pivotZ = 1.0F;

        this.partLeftB = new ModelPart(64, 64, 0, 0);
        this.partLeftB.addCuboid(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.partLeftB.pivotY = 8.0F;
         */

        modelPartData.addChild("bottom", ModelPartBuilder.create().uv(0, 19).cuboid(0.0F, 0.0F, 1.0F, 15.0F, 9.0F, 14.0F), ModelTransform.NONE);
        modelPartData.addChild("lid", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F), ModelTransform.pivot(0.0F, 9.0F, 1.0F));
        modelPartData.addChild("lock", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F), ModelTransform.pivot(0.0F, 8.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    public void render(TropicBambooChestBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        boolean worldExists = world != null;
        BlockState blockState = worldExists ? entity.getCachedState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
        ChestType chestType = blockState.contains(ChestBlock.CHEST_TYPE) ? blockState.get(ChestBlock.CHEST_TYPE) : ChestType.SINGLE;
        Block block = blockState.getBlock();
        if (block instanceof AbstractChestBlock) {
            AbstractChestBlock<?> abstractChestBlock = (AbstractChestBlock<?>) block;
            boolean isDouble = chestType != ChestType.SINGLE;
            float f = blockState.get(ChestBlock.FACING).asRotation();
            DoubleBlockProperties.PropertySource<? extends ChestBlockEntity> propertySource;



            matrices.push();
            matrices.translate(0.5D, 0.5D, 0.5D);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-f));
            matrices.translate(-0.5D, -0.5D, -0.5D);

            if (worldExists) {
                propertySource = abstractChestBlock.getBlockEntitySource(blockState, world, entity.getPos(), true);
            } else {
                propertySource = DoubleBlockProperties.PropertyRetriever::getFallback;
            }

            float pitch = propertySource.apply(ChestBlock.getAnimationProgressRetriever((ChestAnimationProgress)entity)).get(tickDelta);
            pitch = 1.0F - pitch;
            pitch = 1.0F - pitch * pitch * pitch;
            @SuppressWarnings({ "unchecked", "rawtypes" })
            int blockLight = ((Int2IntFunction) propertySource.apply(new LightmapCoordinatesRetriever())).applyAsInt(light);

            VertexConsumer vertexConsumer = getConsumer(vertexConsumers, block, chestType);

            if (isDouble) {
                if (chestType == ChestType.LEFT) {
                    this.renderParts(matrices, vertexConsumer, this.partLeftA, this.partLeftB, this.partLeftC, pitch, blockLight, overlay);
                } else {
                    this.renderParts(matrices, vertexConsumer, this.partRightA, this.partRightB, this.partRightC, pitch, blockLight, overlay);
                }
            } else {
                this.renderParts(matrices, vertexConsumer, this.partA, this.partB, this.partC, pitch, blockLight, overlay);
            }

            matrices.pop();
        }
    }

    private void renderParts(MatrixStack matrices, VertexConsumer vertices, ModelPart chestlid, ModelPart chestlatch, ModelPart chestbase, float pitch, int light, int overlay) {
        chestlid.pitch = -(pitch * 1.5707964F);
        chestlatch.pitch = chestlid.pitch;
        chestlid.render(matrices, vertices, light, overlay);
        chestlatch.render(matrices, vertices, light, overlay);
        chestbase.render(matrices, vertices, light, overlay);
    }

    private static RenderLayer getChestTexture(ChestType type, RenderLayer[] layers) {
        switch (type) {
            case LEFT:
                return layers[ID_LEFT];
            case RIGHT:
                return layers[ID_RIGHT];
            case SINGLE:
            default:
                return layers[ID_NORMAL];
        }
    }

    public static VertexConsumer getConsumer(VertexConsumerProvider provider, Block block, ChestType chestType) {
        RenderLayer[] layers = LAYERS.getOrDefault(block, bambooChestLayer);
        return provider.getBuffer(getChestTexture(chestType, layers));
    }

    //For the future of dynamic creation of chests rather than hard coding such

    public static void registerRenderLayer(Block block) {
        Identifier blockId = Registry.BLOCK.getId(block);
        String modId = blockId.getNamespace();
        String path = blockId.getPath();
        LAYERS.put(block, new RenderLayer[] {
                RenderLayer.getEntityCutout(new Identifier(modId, "textures/entity/chest/" + path + ".png")),
                RenderLayer.getEntityCutout(new Identifier(modId, "textures/entity/chest/" + path + "_left.png")),
                RenderLayer.getEntityCutout(new Identifier(modId, "textures/entity/chest/" + path + "_right.png"))
        });
    }

    static {
        defaultLayer = new RenderLayer[] {
                RenderLayer.getEntityCutout(new Identifier("textures/entity/chest/normal.png")),
                RenderLayer.getEntityCutout(new Identifier("textures/entity/chest/normal_left.png")),
                RenderLayer.getEntityCutout(new Identifier("textures/entity/chest/normal_right.png"))
        };

        bambooChestLayer = new RenderLayer[] {
                RenderLayer.getEntityCutout(new Identifier(Constants.MODID, "textures/entity/chest/" + Registry.BLOCK.getId(TropicraftBlocks.BAMBOO_CHEST).getPath() + "_normal.png")),
                RenderLayer.getEntityCutout(new Identifier(Constants.MODID, "textures/entity/chest/" + Registry.BLOCK.getId(TropicraftBlocks.BAMBOO_CHEST).getPath() + "_left.png")),
                RenderLayer.getEntityCutout(new Identifier(Constants.MODID, "textures/entity/chest/" + Registry.BLOCK.getId(TropicraftBlocks.BAMBOO_CHEST).getPath() + "_right.png"))
        };
    }

}


