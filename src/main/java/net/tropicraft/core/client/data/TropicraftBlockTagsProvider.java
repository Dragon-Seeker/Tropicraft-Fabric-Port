package net.tropicraft.core.client.data;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.BlockTagsProvider;
import net.minecraft.tag.Tag.Identified;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.function.Supplier;

import static net.tropicraft.core.common.registry.TropicraftBlocks.*;
//import static net.tropicraftFabric.common.block.TropicraftFlower.*;

public class TropicraftBlockTagsProvider extends BlockTagsProvider {

    public TropicraftBlockTagsProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void configure() {
        // Filling vanilla tags
        /*
        createAndAppend(TropicraftTags.Blocks.SAND, BlockTags.SAND,
                PURIFIED_SAND, CORAL_SAND, FOAMY_SAND, MINERAL_SAND, VOLCANIC_SAND);
        
        // Saplings & Leaves
        createAndAppend(TropicraftTags.Blocks.SAPLINGS, BlockTags.SAPLINGS,
                PALM_SAPLING, MAHOGANY_SAPLING, GRAPEFRUIT_SAPLING, LEMON_SAPLING, LIME_SAPLING, ORANGE_SAPLING);
        createAndAppend(TropicraftTags.Blocks.LEAVES, BlockTags.LEAVES,
                MAHOGANY_LEAVES, PALM_LEAVES, KAPOK_LEAVES, FRUIT_LEAVES, GRAPEFRUIT_LEAVES, LEMON_LEAVES, LIME_LEAVES, ORANGE_LEAVES);
        
        // Flowers
        createAndAppend(TropicraftTags.Blocks.SMALL_FLOWERS, BlockTags.SMALL_FLOWERS,
                ACAI_VINE, ANEMONE, BROMELIAD, CANNA, COMMELINA_DIFFUSA, CROCOSMIA, CROTON, DRACAENA, TROPICAL_FERN, FOLIAGE, MAGIC_MUSHROOM, ORANGE_ANTHURIUM, ORCHID, PATHOS, RED_ANTHURIUM);
        */
        // Some extra flower tags for our own worldgen



        createTag(TropicraftTags.Blocks.TROPICS_FLOWERS,
                ACAI_VINE, ANEMONE, BROMELIAD, CANNA, COMMELINA_DIFFUSA, CROCOSMIA, CROTON, DRACAENA, TROPICAL_FERN, FOLIAGE, ORANGE_ANTHURIUM, ORCHID, PATHOS, RED_ANTHURIUM);
        createTag(TropicraftTags.Blocks.RAINFOREST_FLOWERS,
                MAGIC_MUSHROOM);
        createTag(TropicraftTags.Blocks.OVERWORLD_FLOWERS,
                ORCHID, PATHOS, RED_ANTHURIUM, COMMELINA_DIFFUSA, ANEMONE, ORANGE_ANTHURIUM);
        /*
        // Logs & Planks
        createAndAppend(TropicraftTags.Blocks.LOGS, BlockTags.LOGS,
                PALM_LOG, MAHOGANY_LOG);
        createAndAppend(TropicraftTags.Blocks.PLANKS, BlockTags.PLANKS,
                PALM_PLANKS, MAHOGANY_PLANKS);
        
        // Wooden deco blocks
        createAndAppend(TropicraftTags.Blocks.WOODEN_SLABS, BlockTags.WOODEN_SLABS,
                PALM_SLAB, MAHOGANY_SLAB);
        createAndAppend(TropicraftTags.Blocks.WOODEN_STAIRS, BlockTags.WOODEN_STAIRS,
                PALM_STAIRS, MAHOGANY_STAIRS);
        createAndAppend(TropicraftTags.Blocks.WOODEN_DOORS, BlockTags.WOODEN_DOORS,
                PALM_DOOR, MAHOGANY_DOOR);
        createAndAppend(TropicraftTags.Blocks.WOODEN_TRAPDOORS, BlockTags.WOODEN_TRAPDOORS,
                PALM_TRAPDOOR, MAHOGANY_TRAPDOOR);
        createAndAppend(TropicraftTags.Blocks.WOODEN_FENCES, BlockTags.WOODEN_FENCES,
                PALM_FENCE, MAHOGANY_FENCE);
         */

        /*
        // All deco blocks
        extendAndAppend(TropicraftTags.Blocks.SLABS, TropicraftTags.Blocks.WOODEN_SLABS, BlockTags.SLABS,
                BAMBOO_SLAB, CHUNK_SLAB, THATCH_SLAB);
        extendAndAppend(TropicraftTags.Blocks.STAIRS, TropicraftTags.Blocks.WOODEN_STAIRS, BlockTags.STAIRS,
                BAMBOO_STAIRS, CHUNK_STAIRS, THATCH_STAIRS);
        extendAndAppend(TropicraftTags.Blocks.DOORS, TropicraftTags.Blocks.WOODEN_DOORS, BlockTags.DOORS,
                BAMBOO_DOOR, THATCH_DOOR);
        extendAndAppend(TropicraftTags.Blocks.TRAPDOORS, TropicraftTags.Blocks.WOODEN_TRAPDOORS, BlockTags.TRAPDOORS,
                BAMBOO_TRAPDOOR, THATCH_TRAPDOOR);
        extendAndAppend(TropicraftTags.Blocks.FENCES, TropicraftTags.Blocks.WOODEN_FENCES, BlockTags.FENCES,
                BAMBOO_FENCE, CHUNK_FENCE, THATCH_FENCE);

         */

        /*
        createAndAppend(TropicraftTags.Blocks.WALLS, BlockTags.WALLS,
                CHUNK_WALL);

        createAndAppend(TropicraftTags.Blocks.CLIMBABLE, BlockTags.CLIMBABLE, BAMBOO_LADDER);

         Flower pots
        createAndAppend(TropicraftTags.Blocks.FLOWER_POTS, BlockTags.FLOWER_POTS,
                Stream.concat(BAMBOO_POTTED_TROPICS_PLANTS.stream(), Stream.concat(BAMBOO_POTTED_VANILLA_PLANTS.stream(), VANILLA_POTTED_TROPICS_PLANTS.stream()))
                        .toArray(Block[]::new));

        createTag(TropicraftTags.Blocks.BONGOS, SMALL_BONGO_DRUM, MEDIUM_BONGO_DRUM, LARGE_BONGO_DRUM);

         */
    }
    
    @SafeVarargs
    private final <T> T[] resolveAll(IntFunction<T[]> creator, Supplier<? extends T>... suppliers) {
        return Arrays.stream(suppliers).map(Supplier::get).toArray(creator);
    }

    @SafeVarargs
    private final void createTag(Identified<Block> tag, Block... blocks) {
        //getOrCreateTagBuilder(tag).add(resolveAll(Block[]::new, blocks));
    }
    
    @SafeVarargs
    private final void appendToTag(Identified<Block> tag, Identified<Block>... toAppend) {
        for(Identified<Block> singleToAppend: toAppend){
            getOrCreateTagBuilder(tag).addTag(singleToAppend);
        }
    }
    
    @SafeVarargs
    private final void extendTag(Identified<Block> tag, Identified<Block> toExtend, Block... blocks) {
        //getOrCreateTagBuilder(tag).addTag(toExtend).add(resolveAll(Block[]::new, blocks));
    }
    
    @SafeVarargs
    private final void createAndAppend(Identified<Block> tag, Identified<Block> to, Block... blocks) {
        createTag(tag, blocks);
        appendToTag(to, tag);
    }

    @SafeVarargs
    private final void extendAndAppend(Identified<Block> tag, Identified<Block> toExtend, Identified<Block> to, Block... blocks) {
        extendTag(tag, toExtend, blocks);
        appendToTag(to, tag);
    }
    
    @Override
    public String getName() {
        return "Tropicraft Block Tags";
    }
}
