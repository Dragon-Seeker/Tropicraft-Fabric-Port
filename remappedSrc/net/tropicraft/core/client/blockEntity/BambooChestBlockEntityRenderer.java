package net.tropicraft.core.client.blockEntity;

import net.tropicraft.Constants;
import net.tropicraft.core.common.block.blockentity.TropicBambooChestBlockEntity;
import net.tropicraft.core.common.registry.TropicraftBlocks;
import net.tropicraft.core.common.registry.TropicraftItems;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.block.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import java.util.HashMap;

public class BambooChestBlockEntityRenderer<T extends BlockEntity & LidBlockEntity> extends BlockEntityRenderer<TropicBambooChestBlockEntity> {
    private static final HashMap<Block, RenderType[]> LAYERS = Maps.newHashMap();
    private static final RenderType[] defaultLayer;
    private static final RenderType[] bambooChestLayer;

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


    public BambooChestBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);

        this.partC = new ModelPart(64, 64, 0, 19);
        this.partC.addBox(1.0F, 0.0F, 1.0F, 14.0F, 9.0F, 14.0F, 0.0F);
        this.partA = new ModelPart(64, 64, 0, 0);
        this.partA.addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
        this.partA.y = 9.0F;
        this.partA.z = 1.0F;
        this.partB = new ModelPart(64, 64, 0, 0);
        this.partB.addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
        this.partB.y = 8.0F;
        this.partRightC = new ModelPart(64, 64, 0, 19);
        this.partRightC.addBox(1.0F, 0.0F, 1.0F, 15.0F, 9.0F, 14.0F, 0.0F);
        this.partRightA = new ModelPart(64, 64, 0, 0);
        this.partRightA.addBox(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.partRightA.y = 9.0F;
        this.partRightA.z = 1.0F;
        this.partRightB = new ModelPart(64, 64, 0, 0);
        this.partRightB.addBox(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.partRightB.y = 8.0F;
        this.partLeftC = new ModelPart(64, 64, 0, 19);
        this.partLeftC.addBox(0.0F, 0.0F, 1.0F, 15.0F, 9.0F, 14.0F, 0.0F);
        this.partLeftA = new ModelPart(64, 64, 0, 0);
        this.partLeftA.addBox(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
        this.partLeftA.y = 9.0F;
        this.partLeftA.z = 1.0F;
        this.partLeftB = new ModelPart(64, 64, 0, 0);
        this.partLeftB.addBox(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
        this.partLeftB.y = 8.0F;
    }



    public void render(TropicBambooChestBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        Level world = entity.getLevel();
        boolean worldExists = world != null;
        BlockState blockState = worldExists ? entity.getBlockState() : Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
        ChestType chestType = blockState.hasProperty(ChestBlock.TYPE) ? blockState.getValue(ChestBlock.TYPE) : ChestType.SINGLE;
        Block block = blockState.getBlock();
        if (block instanceof AbstractChestBlock) {
            AbstractChestBlock<?> abstractChestBlock = (AbstractChestBlock<?>) block;
            boolean isDouble = chestType != ChestType.SINGLE;
            float f = blockState.getValue(ChestBlock.FACING).toYRot();
            DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> propertySource;



            matrices.pushPose();
            matrices.translate(0.5D, 0.5D, 0.5D);
            matrices.mulPose(Vector3f.YP.rotationDegrees(-f));
            matrices.translate(-0.5D, -0.5D, -0.5D);

            if (worldExists) {
                propertySource = abstractChestBlock.combine(blockState, world, entity.getBlockPos(), true);
            } else {
                propertySource = DoubleBlockCombiner.Combiner::acceptNone;
            }

            float pitch = propertySource.apply(ChestBlock.opennessCombiner((LidBlockEntity)entity)).get(tickDelta);
            pitch = 1.0F - pitch;
            pitch = 1.0F - pitch * pitch * pitch;
            @SuppressWarnings({ "unchecked", "rawtypes" })
            int blockLight = ((Int2IntFunction) propertySource.apply(new BrightnessCombiner())).applyAsInt(light);

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

            matrices.popPose();
        }
    }

    private void renderParts(PoseStack matrices, VertexConsumer vertices, ModelPart chestlid, ModelPart chestlatch, ModelPart chestbase, float pitch, int light, int overlay) {
        chestlid.xRot = -(pitch * 1.5707964F);
        chestlatch.xRot = chestlid.xRot;
        chestlid.render(matrices, vertices, light, overlay);
        chestlatch.render(matrices, vertices, light, overlay);
        chestbase.render(matrices, vertices, light, overlay);
    }


    private static RenderType getChestTexture(ChestType type, RenderType[] layers) {
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

    public static VertexConsumer getConsumer(MultiBufferSource provider, Block block, ChestType chestType) {
        RenderType[] layers = LAYERS.getOrDefault(block, bambooChestLayer);
        return provider.getBuffer(getChestTexture(chestType, layers));
    }


    //For the future of dynamic creation of chests rather than hard coding such
    /*
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
     */

    static {
        defaultLayer = new RenderType[] {
                RenderType.entityCutout(new ResourceLocation("textures/entity/chest/normal.png")),
                RenderType.entityCutout(new ResourceLocation("textures/entity/chest/normal_left.png")),
                RenderType.entityCutout(new ResourceLocation("textures/entity/chest/normal_right.png"))
        };

        bambooChestLayer = new RenderType[] {
                RenderType.entityCutout(new ResourceLocation(Constants.MODID, "textures/entity/chest/" + Registry.BLOCK.getKey(TropicraftBlocks.BAMBOO_CHEST).getPath() + "_normal.png")),
                RenderType.entityCutout(new ResourceLocation(Constants.MODID, "textures/entity/chest/" + Registry.BLOCK.getKey(TropicraftBlocks.BAMBOO_CHEST).getPath() + "_left.png")),
                RenderType.entityCutout(new ResourceLocation(Constants.MODID, "textures/entity/chest/" + Registry.BLOCK.getKey(TropicraftBlocks.BAMBOO_CHEST).getPath() + "_right.png"))
        };
    }

}
