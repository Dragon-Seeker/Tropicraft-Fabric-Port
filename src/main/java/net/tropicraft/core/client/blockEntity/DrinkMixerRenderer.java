package net.tropicraft.core.client.blockEntity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3f;
import net.tropicraft.core.client.entity.models.BambooMugModel;
import net.tropicraft.core.client.entity.models.EIHMachineModel;
import net.tropicraft.core.client.registry.TropicraftEntityRendering;
import net.tropicraft.core.client.util.TropicraftRenderUtils;
import net.tropicraft.core.common.block.blockentity.DrinkMixerTileEntity;
import net.tropicraft.core.common.item.CocktailItem;
import net.tropicraft.core.common.registry.TropicraftBlocks;

public class DrinkMixerRenderer extends MachineRenderer<DrinkMixerTileEntity> {
    private final BambooMugModel modelBambooMug; // = new BambooMugModel(RenderLayer::getEntityCutout);
    private final ItemRenderer renderItem;
    private ItemEntity dummyEntityItem;

    private static final float[][] INGREDIENT_OFFSETS = new float[][] {
        {0.3f, -0.5f, 0.05f},
        {-0.3f, -0.5f, 0.05f},
        {0.0f, 0.3f, -0.1f}
    };

    private static final float[][] INGREDIENT_SCALES = new float[][] {
        {1, 1, 1},
        {1, 1, 1},
        {0.8f, 0.8f, 0.8f}
    };

    public DrinkMixerRenderer(final BlockEntityRendererFactory.Context ctx) {
        super(ctx, TropicraftBlocks.DRINK_MIXER, new EIHMachineModel(ctx.getLayerModelPart(TropicraftEntityRendering.EIH_LAYER)));
        modelBambooMug = new BambooMugModel(ctx.getLayerModelPart(TropicraftEntityRendering.BAMBOO_MUG));
        this.renderItem = MinecraftClient.getInstance().getItemRenderer();
    }

    @Override
    protected SpriteIdentifier getMaterial() {
        return TropicraftRenderUtils.getTEMaterial("drink_mixer");
    }

    @Override
    public void renderIngredients(final DrinkMixerTileEntity te, final MatrixStack stack, final VertexConsumerProvider buffer, int combinedLightIn, int combinedOverlayIn) {
        if (dummyEntityItem == null) {
             dummyEntityItem = new ItemEntity(MinecraftClient.getInstance().world, 0.0, 0.0, 0.0, new ItemStack(Items.SUGAR));
        }
        final DefaultedList<ItemStack> ingredients = te.getIngredients();

        if (!te.isDoneMixing()) {
            for (int index = 0; index < ingredients.size(); index++) {
                final ItemStack ingredient = ingredients.get(index);
                if (!ingredient.isEmpty()) {
                    renderIngredient(stack, buffer, combinedOverlayIn, combinedLightIn, ingredient, index);
                }
            }
        }

        if (te.isMixing() || !te.result.isEmpty()) {
            stack.push();
            stack.translate(-0.2f, -0.25f, 0.0f);
            if (te.isDoneMixing()) {
                modelBambooMug.renderLiquid = true;
                modelBambooMug.liquidColor = CocktailItem.getCocktailColor(te.result);
            } else {
                modelBambooMug.renderLiquid = false;
            }
            VertexConsumer ivertexbuilder = buffer.getBuffer(modelBambooMug.getLayer(TropicraftRenderUtils.getTextureTE("bamboo_mug")));
            modelBambooMug.render(stack, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1, 1, 1, 1);
            stack.pop();
        }
    }

    private void renderIngredient(final MatrixStack stack, final VertexConsumerProvider buffer, final int combinedOverlayIn, final int combinedLight, final ItemStack ingredient, final int ingredientIndex) {
        stack.push();
        stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
        stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
        stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90));
        final float[] offsets = INGREDIENT_OFFSETS[ingredientIndex];
        final float[] scales = INGREDIENT_SCALES[ingredientIndex];
        stack.translate(offsets[0], offsets[1], offsets[2]);
        stack.scale(scales[0], scales[1], scales[2]);
        dummyEntityItem.setStack(ingredient);
        final BakedModel bakedModel = TropicraftRenderUtils.getBakedModel(renderItem, ingredient);
        renderItem.renderItem(ingredient, ModelTransformation.Mode.FIXED, false, stack, buffer, combinedLight, combinedOverlayIn, bakedModel);
        stack.pop();
    }
}
