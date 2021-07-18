package com.dragonseeker.tropicfabricport;

import com.dragonseeker.tropicfabricport.block.blockentity.TropicBambooChestBlockEntity;
import com.dragonseeker.tropicfabricport.client.models.MaskArmorProvider;
import com.dragonseeker.tropicfabricport.client.renderers.*;
import com.dragonseeker.tropicfabricport.block.testContainer.BoxChestScreen;
import com.dragonseeker.tropicfabricport.item.IColoredItem;
import com.dragonseeker.tropicfabricport.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Environment(EnvType.CLIENT)
public class TropicFabricPortClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        TropicBlocks.initClient();

        TropicClientPackets.init();
        //Test comment
        //new TropicBambooChestBlockEntity(getBlockEntityType(), new Identifier(Tropicfabricport.MOD_ID, "bamboo_chest")); //((TropicBambooChestBlock) bamboochest.getBlock()).blockId()
        //TropicBambooChestBlockEntity renderEntity = new ChestBlockEntity(ChestCommon.getBlockEntityType(), ((ChestBlock) item.getBlock()).blockId());

        //ScreenRegistry.register(TropicScreenHandler.BOX_SCREEN_HANDLER, BoxScreen::new);
        ScreenRegistry.register(TropicScreenHandler.BOX_CHEST_SCREEN_HANDLER, BoxChestScreen::new);

        //look at the chest item model file and copy that
        //then mixin to builtinmodelitemrenderer and manually render the block entity how you want
        //i think there's a fabric api event for it but i forget
        BlockEntityRendererRegistry.INSTANCE.register(TropicBlockEntities.BAMBOO_CHEST, BambooChestBlockEntityRenderer::new);

        BuiltinItemRendererRegistry.INSTANCE.register(TropicBlocks.BAMBOO_CHEST, (itemStack, transform, stack, source, light, overlay) ->
                BlockEntityRenderDispatcher.INSTANCE.renderEntity(new TropicBambooChestBlockEntity(), stack, source, light, overlay));



        for(Item item : TropicItems.COCKTAILS.values().asList()) {
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((IColoredItem) stack.getItem()).getColor(stack, tintIndex), item); //tintIndex > 0 ? CocktailItem.getCocktailColor(stack) :
            //ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? CocktailItem.getCocktailColor(stack) : ((DyeableItem) stack.getItem()).getColor(stack), item);
        }

        //TODO: Currently not rendering at all so... It's a feature... for now (:
        EntityRendererRegistry.INSTANCE.register(TropicEntities.BAMBOO_ITEM_FRAME, BambooItemFrameEntityRenderer::new);



        EntityRendererRegistry.INSTANCE.register(TropicEntities.EXPLODING_COCONUT, (dispatcher, context) -> new FlyingItemEntityRenderer(dispatcher, context.getItemRenderer()));//manager -> new SpriteRenderer<>(manager, event.getMinecraftSupplier().get().getItemRenderer())

        EntityRendererRegistry.INSTANCE.register(TropicEntities.ASHEN_MASK, (dispatcher, context) -> new AshenMaskRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(TropicEntities.ASHEN, (dispatcher, context) -> new AshenRenderer(dispatcher));

        EntityRendererRegistry.INSTANCE.register(TropicEntities.WALL_ITEM, (dispatcher, context) -> new WallItemRenderer(dispatcher));


        //final MaskArmorProvider MASK_PROVIDER = null;


        /*
        ArmorRenderingRegistry.registerModel(MASK_PROVIDER, (Item) MASK_PROVIDER.getRenderedItems());

       TropicItems.ASHEN_MASKS.forEach((maskItem) -> {
           ArmorRenderingRegistry.registerModel(PlayerHeadpieceRenderer(maskItem.maskType.ordinal(), maskType.getXOffset(), maskType.getYOffset()));
       });

        ArmorRenderingRegistryImpl.registerModel();
         */

        ArrayList<MaskArmorProvider> MASK_PROVIDER = new ArrayList<MaskArmorProvider>();

        AtomicInteger i = new AtomicInteger();
        i.set(0);

        TropicItems.ASHEN_MASKS.values().forEach((maskItem) -> {
            MASK_PROVIDER.add(new MaskArmorProvider(maskItem.getMaskType()));
            int currentIndex = i.getAndIncrement();
            ArmorRenderingRegistry.registerModel(MASK_PROVIDER.get(currentIndex), maskItem);
            ArmorRenderingRegistry.registerTexture(MASK_PROVIDER.get(currentIndex), maskItem);
        });


        /*
	    @OnlyIn(Dist.CLIENT)
	    @Nullable
	    @Override
	    public BipedEntityModel getArmorModel(final LivingEntity entityLiving, final ItemStack itemStack, final EquipmentSlot armorSlot, final BipedEntityModel model) {
		    return armorSlot == EquipmentSlot.HEAD ? new PlayerHeadpieceRenderer(maskType.ordinal(), maskType.getXOffset(), maskType.getYOffset()) : null;
	    }

	    @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return TropicraftRenderUtils.getTextureEntity("ashen/mask").toString();
        }
	    */



        //RenderingRegistry.registerEntityRenderingHandler(TropicraftEntities.WALL_ITEM.get(), WallItemRenderer::new);


        /*
        EntityRendererRegistry.INSTANCE.register(TropicEntities.BAMBOO_ITEM_FRAME, (dispatcher, context) -> {
            return new BambooItemFrameEntityRenderer(dispatcher, context.getItemRenderer());
        });
         */

    }
}
