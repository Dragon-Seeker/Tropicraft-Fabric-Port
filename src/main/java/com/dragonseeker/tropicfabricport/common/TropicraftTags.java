package com.dragonseeker.tropicfabricport.common;

import com.dragonseeker.tropicfabricport.Tropicfabricport;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class TropicraftTags {

    public static class Blocks extends TropicraftTags{

        public static final Tag.Identified<Block> SAND = modTag("sand");

        public static final Tag.Identified<Block> SAPLINGS = modTag("saplings");
        public static final Tag.Identified<Block> LEAVES = modTag("leaves");
        
        public static final Tag.Identified<Block> SMALL_FLOWERS = modTag("small_flowers");
        public static final Tag.Identified<Block> TROPICS_FLOWERS = modTag("tropics_flowers");
        public static final Tag.Identified<Block> RAINFOREST_FLOWERS = modTag("rainforest_flowers");
        public static final Tag.Identified<Block> OVERWORLD_FLOWERS = modTag("overworld_flowers");

        public static final Tag.Identified<Block> LOGS = modTag("logs");
        public static final Tag.Identified<Block> PLANKS = modTag("planks");
        
        public static final Tag.Identified<Block> WOODEN_SLABS = modTag("wooden_slabs");
        public static final Tag.Identified<Block> WOODEN_STAIRS = modTag("wooden_stairs");
        public static final Tag.Identified<Block> WOODEN_DOORS = modTag("wooden_doors");
        public static final Tag.Identified<Block> WOODEN_TRAPDOORS = modTag("wooden_trapdoors");
        public static final Tag.Identified<Block> WOODEN_FENCES = modTag("wooden_fences");
        
        public static final Tag.Identified<Block> SLABS = modTag("slabs");
        public static final Tag.Identified<Block> STAIRS = modTag("stairs");
        public static final Tag.Identified<Block> DOORS = modTag("doors");
        public static final Tag.Identified<Block> TRAPDOORS = modTag("trapdoors");
        public static final Tag.Identified<Block> FENCES = modTag("fences");
        public static final Tag.Identified<Block> WALLS = modTag("walls");

        public static final Tag.Identified<Block> FLOWER_POTS = modTag("flower_pots");
        public static final Tag.Identified<Block> BONGOS = modTag("bongos");
        /*
        static Identified<Block> Identified(String modid, String name) {
            return Identified(BlockTags::makeWrapperTag, modid, name);
        }

         */
        static Tag.Identified<Block> modTag(String name) {
            return (Tag.Identified<Block>) TagRegistry.block(new Identifier(Tropicfabricport.MOD_ID, name));
        }

        static Tag.Identified<Block> compatTag(String name) {
            return (Tag.Identified<Block>) TagRegistry.block(new Identifier("c", name));
        }
        public static void init(){

        }
    }
    
    public static class Items extends TropicraftTags {

        public static final Tag.Identified<Item> AZURITE_ORE = commonOreTag("azurite");
        public static final Tag.Identified<Item> EUDIALYTE_ORE = commonOreTag("eudialyte");
        public static final Tag.Identified<Item> MANGANESE_ORE = commonOreTag("manganese");
        public static final Tag.Identified<Item> SHAKA_ORE = commonOreTag("shaka");
        public static final Tag.Identified<Item> ZIRCON_ORE = commonOreTag("zircon");

        
        public static final Tag.Identified<Item> AZURITE_GEM = commonGemTag("azurite");
        public static final Tag.Identified<Item> EUDIALYTE_GEM = commonGemTag("eudialyte");
        public static final Tag.Identified<Item> MANGANESE_INGOT = commonIngotTag("manganese");
        public static final Tag.Identified<Item> SHAKA_INGOT = commonIngotTag("shaka");
        public static final Tag.Identified<Item> ZIRCON_GEM = commonGemTag("zircon");
        public static final Tag.Identified<Item> ZIRCONIUM_GEM = commonGemTag("zirconium");

        public static final Tag.Identified<Item> GEMS = commonTag("gems");
        
        public static final Tag.Identified<Item> SWORDS = commonTag("swords");

        public static final Tag.Identified<Item> SAND = modTag("sand");

        public static final Tag.Identified<Item> SAPLINGS = modTag("saplings");
        public static final Tag.Identified<Item> LEAVES = modTag("leaves");
        
        public static final Tag.Identified<Item> SMALL_FLOWERS = modTag("small_flowers");
        
        public static final Tag.Identified<Item> LOGS = modTag("logs");
        public static final Tag.Identified<Item> PLANKS = modTag("planks");

        public static final Tag.Identified<Item> WOODEN_RODS_C = commonTag("wooden_rods");
        
        public static final Tag.Identified<Item> WOODEN_SLABS = modTag("wooden_slabs");
        public static final Tag.Identified<Item> WOODEN_STAIRS = modTag("wooden_stairs");
        public static final Tag.Identified<Item> WOODEN_DOORS = modTag("wooden_doors");
        public static final Tag.Identified<Item> WOODEN_TRAPDOORS = modTag("wooden_trapdoors");
        public static final Tag.Identified<Item> WOODEN_FENCES = modTag("wooden_fences");
        
        public static final Tag.Identified<Item> SLABS = modTag("slabs");
        public static final Tag.Identified<Item> STAIRS = modTag("stairs");
        public static final Tag.Identified<Item> DOORS = modTag("doors");
        public static final Tag.Identified<Item> TRAPDOORS = modTag("trapdoors");
        public static final Tag.Identified<Item> FENCES = modTag("fences");
        public static final Tag.Identified<Item> WALLS = modTag("walls");

        public static final Tag.Identified<Item> LEATHER = modTag("leather");

        public static final Tag.Identified<Item> LEATHER_C = commonTag("leather");
        
        public static final Tag.Identified<Item> FISHES = modTag("fishes");

        public static final Tag.Identified<Item> FISHES_C = commonTag("raw_fish");

        public static final Tag.Identified<Item> SHELLS = modTag("shells");

        public static final Tag.Identified<Item> ASHEN_MASKS = modTag("ashen_masks");
        /*
        static Identified<Item> Identified(String modid, String name) {
            return Identified(ItemTags::register, modid, name);
        }
         */

        static Tag.Identified<Item> modTag(String name) {
            Tag.Identified<Item> tempTag = (Tag.Identified<Item>) TagRegistry.item(new Identifier(Tropicfabricport.MOD_ID, name));
            //System.out.println("System Test Ouput: " + tempTag.getId());
            return tempTag;
        }

        static Tag.Identified<Item> commonOreTag(String name) {
            return (Tag.Identified<Item>) TagRegistry.item(new Identifier("c", name + "_ores"));
        }

        static Tag.Identified<Item> commonGemTag(String name) {
            return (Tag.Identified<Item>) TagRegistry.item(new Identifier("c", name + "_gems"));
        }

        static Tag.Identified<Item> commonIngotTag(String name) {
            return (Tag.Identified<Item>) TagRegistry.item(new Identifier("c", name + "_ingots"));
        }

        static Tag.Identified<Item> commonTag(String name) {
            return (Tag.Identified<Item>) TagRegistry.item(new Identifier("c", name));
        }

        public static void init(){

        }
    }
    
    static <T extends Tag.Identified<?>> T Identified(Function<String, T> creator, String modid, String name) {
        return creator.apply(new Identifier(modid, name).toString());
    }

    static <T extends Tag.Identified<?>> T modTag(Function<String, T> creator, String name) {
        return Identified(creator, Tropicfabricport.MOD_ID, name);
    }

    static <T extends Tag.Identified<?>> T compatTag(Function<String, T> creator, String name) {
        return Identified(creator, "fabric", name);
    }




}
