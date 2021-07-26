package net.tropicraftFabric.common.registry;

import net.tropicraftFabric.Constants;
//import com.dragonseeker.tropicfabricport.common.block.entity.CoconutBlockEntity;
import net.tropicraftFabric.common.block.blockentity.TropicBambooChestBlockEntity;
import net.tropicraftFabric.common.block.testContainer.BoxBlockEntity;
import net.tropicraftFabric.common.block.testContainer.BoxChestBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TropicBlockEntities<T extends BlockEntity>{

    //public static final BlockEntityType<TChestBlockEntity> CHEST;
    public static BlockEntityType<BoxBlockEntity> BOX_BLOCK_ENTITY;
    public static BlockEntityType<BoxChestBlockEntity> BOX_BLOCK_CHEST_ENTITY;
    public static BlockEntityType<TropicBambooChestBlockEntity> BAMBOO_CHEST;
    //public static BlockEntityType<CoconutBlockEntity> COCONUT;

    static {
        //BOX_BLOCK_ENTITY = registerBlockEntityType("box_block", BlockEntityType.Builder.create(BoxBlockEntity::new, TropicBlocks.BOX_BLOCK));
        BAMBOO_CHEST = registerBlockEntityType("bamboo_chest", BlockEntityType.Builder.create(TropicBambooChestBlockEntity::new, TropicBlocks.BAMBOO_CHEST));
        //BOX_BLOCK_CHEST_ENTITY = registerBlockEntityType("box_chest_block", BlockEntityType.Builder.create(BoxChestBlockEntity::new, TropicBlocks.BOX_BLOCK_CHEST));
    }

    public static void init(){

    }

    public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(String id, BlockEntityType.Builder<T> builder) {
        return (BlockEntityType)Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Constants.MODID, id), builder.build(null));
    }


}
