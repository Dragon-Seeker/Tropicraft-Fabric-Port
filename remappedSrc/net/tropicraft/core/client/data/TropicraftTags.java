package net.tropicraft.core.client.data;

import net.tropicraft.Constants;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import java.util.function.Function;

public class TropicraftTags {

    public static class Blocks extends TropicraftTags{

        public static final Tag.Named<Block> SAND = modTag("sand");

        public static final Tag.Named<Block> SAPLINGS = modTag("saplings");
        public static final Tag.Named<Block> LEAVES = modTag("leaves");
        
        public static final Tag.Named<Block> SMALL_FLOWERS = modTag("small_flowers");
        public static final Tag.Named<Block> TROPICS_FLOWERS = modTag("tropics_flowers");
        public static final Tag.Named<Block> RAINFOREST_FLOWERS = modTag("rainforest_flowers");
        public static final Tag.Named<Block> OVERWORLD_FLOWERS = modTag("overworld_flowers");

        public static final Tag.Named<Block> LOGS = modTag("logs");
        public static final Tag.Named<Block> PLANKS = modTag("planks");
        
        public static final Tag.Named<Block> WOODEN_SLABS = modTag("wooden_slabs");
        public static final Tag.Named<Block> WOODEN_STAIRS = modTag("wooden_stairs");
        public static final Tag.Named<Block> WOODEN_DOORS = modTag("wooden_doors");
        public static final Tag.Named<Block> WOODEN_TRAPDOORS = modTag("wooden_trapdoors");
        public static final Tag.Named<Block> WOODEN_FENCES = modTag("wooden_fences");
        
        public static final Tag.Named<Block> SLABS = modTag("slabs");
        public static final Tag.Named<Block> STAIRS = modTag("stairs");
        public static final Tag.Named<Block> DOORS = modTag("doors");
        public static final Tag.Named<Block> TRAPDOORS = modTag("trapdoors");
        public static final Tag.Named<Block> FENCES = modTag("fences");
        public static final Tag.Named<Block> WALLS = modTag("walls");

        public static final Tag.Named<Block> FLOWER_POTS = modTag("flower_pots");
        public static final Tag.Named<Block> BONGOS = modTag("bongos");
        /*
        static Identified<Block> Identified(String modid, String name) {
            return Identified(BlockTags::makeWrapperTag, modid, name);
        }

         */


        static Tag.Named<Block> modTag(String name) {
            return (Tag.Named<Block>) TagRegistry.block(new ResourceLocation(Constants.MODID, name));
        }

        static Tag.Named<Block> compatTag(String name) {
            return (Tag.Named<Block>) TagRegistry.block(new ResourceLocation("c", name));
        }
        public static void init(){

        }
    }
    
    public static class Items extends TropicraftTags {

        public static final Tag.Named<Item> AZURITE_ORE = commonOreTag("azurite");
        public static final Tag.Named<Item> EUDIALYTE_ORE = commonOreTag("eudialyte");
        public static final Tag.Named<Item> MANGANESE_ORE = commonOreTag("manganese");
        public static final Tag.Named<Item> SHAKA_ORE = commonOreTag("shaka");
        public static final Tag.Named<Item> ZIRCON_ORE = commonOreTag("zircon");

        
        public static final Tag.Named<Item> AZURITE_GEM = commonGemTag("azurite");
        public static final Tag.Named<Item> EUDIALYTE_GEM = commonGemTag("eudialyte");
        public static final Tag.Named<Item> MANGANESE_INGOT = commonIngotTag("manganese");
        public static final Tag.Named<Item> SHAKA_INGOT = commonIngotTag("shaka");
        public static final Tag.Named<Item> ZIRCON_GEM = commonGemTag("zircon");
        public static final Tag.Named<Item> ZIRCONIUM_GEM = commonGemTag("zirconium");

        public static final Tag.Named<Item> GEMS = commonTag("gems");
        
        public static final Tag.Named<Item> SWORDS = commonTag("swords");

        public static final Tag.Named<Item> SAND = modTag("sand");

        public static final Tag.Named<Item> SAPLINGS = modTag("saplings");
        public static final Tag.Named<Item> LEAVES = modTag("leaves");
        
        public static final Tag.Named<Item> SMALL_FLOWERS = modTag("small_flowers");
        
        public static final Tag.Named<Item> LOGS = modTag("logs");
        public static final Tag.Named<Item> PLANKS = modTag("planks");

        public static final Tag.Named<Item> WOODEN_RODS_C = commonTag("wooden_rods");
        
        public static final Tag.Named<Item> WOODEN_SLABS = modTag("wooden_slabs");
        public static final Tag.Named<Item> WOODEN_STAIRS = modTag("wooden_stairs");
        public static final Tag.Named<Item> WOODEN_DOORS = modTag("wooden_doors");
        public static final Tag.Named<Item> WOODEN_TRAPDOORS = modTag("wooden_trapdoors");
        public static final Tag.Named<Item> WOODEN_FENCES = modTag("wooden_fences");
        
        public static final Tag.Named<Item> SLABS = modTag("slabs");
        public static final Tag.Named<Item> STAIRS = modTag("stairs");
        public static final Tag.Named<Item> DOORS = modTag("doors");
        public static final Tag.Named<Item> TRAPDOORS = modTag("trapdoors");
        public static final Tag.Named<Item> FENCES = modTag("fences");
        public static final Tag.Named<Item> WALLS = modTag("walls");

        public static final Tag.Named<Item> LEATHER = modTag("leather");

        public static final Tag.Named<Item> LEATHER_C = commonTag("leather");
        
        public static final Tag.Named<Item> FISHES = modTag("fishes");

        public static final Tag.Named<Item> FISHES_C = commonTag("raw_fish");

        public static final Tag.Named<Item> SHELLS = modTag("shells");

        public static final Tag.Named<Item> ASHEN_MASKS = modTag("ashen_masks");
        /*
        static Identified<Item> Identified(String modid, String name) {
            return Identified(ItemTags::register, modid, name);
        }
         */

        static Tag.Named<Item> modTag(String name) {
            Tag.Named<Item> tempTag = (Tag.Named<Item>) TagRegistry.item(new ResourceLocation(Constants.MODID, name));
            //System.out.println("System Test Ouput: " + tempTag.getId());
            return tempTag;
        }

        static Tag.Named<Item> commonOreTag(String name) {
            return (Tag.Named<Item>) TagRegistry.item(new ResourceLocation("c", name + "_ores"));
        }

        static Tag.Named<Item> commonGemTag(String name) {
            return (Tag.Named<Item>) TagRegistry.item(new ResourceLocation("c", name + "_gems"));
        }

        static Tag.Named<Item> commonIngotTag(String name) {
            return (Tag.Named<Item>) TagRegistry.item(new ResourceLocation("c", name + "_ingots"));
        }

        static Tag.Named<Item> commonTag(String name) {
            return (Tag.Named<Item>) TagRegistry.item(new ResourceLocation("c", name));
        }

        public static void init(){

        }
    }
    
    static <T extends Tag.Named<?>> T Identified(Function<String, T> creator, String modid, String name) {
        return creator.apply(new ResourceLocation(modid, name).toString());
    }

    static <T extends Tag.Named<?>> T modTag(Function<String, T> creator, String name) {
        return Identified(creator, Constants.MODID, name);
    }

    static <T extends Tag.Named<?>> T compatTag(Function<String, T> creator, String name) {
        return Identified(creator, "fabric", name);
    }




}
