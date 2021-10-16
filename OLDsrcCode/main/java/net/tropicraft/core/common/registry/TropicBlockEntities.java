package net.tropicraft.core.common.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.tropicraft.Constants;
//import com.dragonseeker.tropicfabricport.common.block.entity.CoconutBlockEntity;
import net.tropicraft.core.common.block.blockentity.*;
import net.tropicraft.core.common.block.testContainer.BoxBlockEntity;
import net.tropicraft.core.common.block.testContainer.BoxChestBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TropicBlockEntities<T extends BlockEntity>{

    //public static final BlockEntityType<TChestBlockEntity> CHEST;
    public static BlockEntityType<BoxBlockEntity> BOX_BLOCK_ENTITY;
    public static BlockEntityType<BoxChestBlockEntity> BOX_BLOCK_CHEST_ENTITY;
    public static BlockEntityType<TropicBambooChestBlockEntity> BAMBOO_CHEST;
    public static BlockEntityType<SifterTileEntity> SIFTER;
    public static BlockEntityType<DrinkMixerTileEntity> DRINK_MIXER;
    public static BlockEntityType<AirCompressorTileEntity> AIR_COMPRESSOR;
    //public static BlockEntityType<CoconutBlockEntity> COCONUT;

    public static final BlockEntityType<VolcanoBlockEntity> VOLCANO;

    static {
        //BOX_BLOCK_ENTITY = registerBlockEntityType("box_block", BlockEntityType.Builder.create(BoxBlockEntity::new, TropicBlocks.BOX_BLOCK));
        BAMBOO_CHEST = registerBlockEntityType("bamboo_chest", FabricBlockEntityTypeBuilder.create(TropicBambooChestBlockEntity::new, TropicraftBlocks.BAMBOO_CHEST));
        VOLCANO = registerBlockEntityType("tile_entity_volcano", FabricBlockEntityTypeBuilder.create(VolcanoBlockEntity::new, TropicraftBlocks.VOLCANO));
        SIFTER = registerBlockEntityType("sifter", FabricBlockEntityTypeBuilder.create(SifterTileEntity::new, TropicraftBlocks.SIFTER));
        DRINK_MIXER = registerBlockEntityType("drink_mixer", FabricBlockEntityTypeBuilder.create(DrinkMixerTileEntity::new, TropicraftBlocks.DRINK_MIXER));
        AIR_COMPRESSOR = registerBlockEntityType("air_compressor", FabricBlockEntityTypeBuilder.create(AirCompressorTileEntity::new, TropicraftBlocks.AIR_COMPRESSOR));
        //BOX_BLOCK_CHEST_ENTITY = registerBlockEntityType("box_chest_block", BlockEntityType.Builder.create(BoxChestBlockEntity::new, TropicBlocks.BOX_BLOCK_CHEST));
    }

    public static void init(){

    }

    public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(String id, FabricBlockEntityTypeBuilder<T> builder) {
        return (BlockEntityType)Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Constants.MODID, id), builder.build(null));
    }


}
