package net.tropicraft.core.common.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.tropicraft.Constants;
import net.tropicraft.core.common.block.*;
import net.tropicraft.core.common.block.plants.TropicPineapple;
import net.tropicraft.core.common.block.plants.TropicTallPlant;
import net.tropicraft.core.common.item.TropicBlockItem;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TropicraftBlocks {
    public static final PortalWaterBlock PORTAL_WATER = registerNoItem(
            "portal_water", new PortalWaterBlock(FabricBlockSettings.of(Material.WATER).noDrops()));

    public static final Block CHUNK = registerBlock("chunk", Builder.tropicBlock(Material.STONE, MaterialColor.COLOR_BLACK,6.0F,30F));
    public static final Block CHUNK_WALL = registerBlock("chunk_wall", Builder.Wall(CHUNK));

    public static final VolcanoBlock VOLCANO = registerNoItem("volcano", new VolcanoBlock(FabricBlockSettings.copyOf(Blocks.BEDROCK).noDrops()));

    public static final SifterBlock SIFTER = registerBlock("sifter", new SifterBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).noOcclusion()));

    public static final DrinkMixerBlock DRINK_MIXER = registerBlock("drink_mixer", new DrinkMixerBlock(FabricBlockSettings.of(Material.STONE).strength(2, 30).noOcclusion()));

    public static final AirCompressorBlock AIR_COMPRESSOR = registerBlock("air_compressor", new AirCompressorBlock(FabricBlockSettings.of(Material.STONE).strength(2, 30).noOcclusion()));


    public static final TropicOreBlock MANGANESE_ORE = registerBlock("manganese_ore", Builder.OreBlock(MaterialColor.COLOR_GRAY, UniformInt.of(0, 0)));
    public static final TropicOreBlock SHAKA_ORE = registerBlock("shaka_ore", Builder.OreBlock(MaterialColor.COLOR_GRAY, UniformInt.of(0, 0)));
    public static final TropicOreBlock EUDIALYTE_ORE = registerBlock("eudialyte_ore", Builder.OreBlock(MaterialColor.COLOR_GRAY, 2, UniformInt.of(1, 4)));
    public static final TropicOreBlock AZURITE_ORE = registerBlock("azurite_ore", Builder.OreBlock(MaterialColor.COLOR_GRAY, 2, UniformInt.of(2, 5)));
    public static final TropicOreBlock ZIRCON_ORE = registerBlock("zircon_ore", Builder.OreBlock(MaterialColor.COLOR_GRAY, 1, UniformInt.of(0, 2)));

    public static final Block AZURITE_BLOCK = registerBlock("azurite_block", Builder.MetalBlock(MaterialColor.COLOR_LIGHT_BLUE));
    public static final Block EUDIALYTE_BLOCK = registerBlock("eudialyte_block", Builder.MetalBlock(MaterialColor.COLOR_PINK));
    public static final Block MANGANESE_BLOCK = registerBlock("manganese_block", Builder.MetalBlock(MaterialColor.COLOR_PURPLE));
    public static final Block SHAKA_BLOCK = registerBlock("shaka_block", Builder.MetalBlock(MaterialColor.COLOR_BLUE));
    public static final Block ZIRCON_BLOCK = registerBlock("zircon_block", Builder.MetalBlock(MaterialColor.COLOR_RED));
    public static final Block ZIRCONIUM_BLOCK = registerBlock("zirconium_block", Builder.MetalBlock(MaterialColor.COLOR_PINK));

    public static final Block PURIFIED_SAND = registerBlock("purified_sand", Builder.Sand());
    public static final Block PACKED_PURIFIED_SAND = registerBlock("packed_purified_sand", Builder.Sand(2.0F, 30.0F));
    public static final Block CORAL_SAND = registerBlock("coral_sand", Builder.Sand(MaterialColor.COLOR_PINK));
    public static final Block FOAMY_SAND = registerBlock("foamy_sand", Builder.Sand(MaterialColor.COLOR_GREEN));
    public static final Block VOLCANIC_SAND = registerBlock("volcanic_sand", Builder.Sand(MaterialColor.COLOR_LIGHT_GRAY));
    public static final Block MINERAL_SAND = registerBlock("mineral_sand", Builder.Sand());

    public static final Block MAHOGANY_LOG = registerBlock("mahogany_log", Builder.Log(Material.WOOD, MaterialColor.WOOD, MaterialColor.COLOR_BROWN, SoundType.WOOD)); //Was MaterialColor.WOOD
    public static final Block MAHOGANY_WOOD = registerBlock("mahogany_wood", Builder.Bark(Material.WOOD, SoundType.WOOD));
    public static final Block MAHOGANY_PLANKS = registerBlock("mahogany_planks", Builder.tropicBlock(Material.WOOD, MaterialColor.COLOR_BROWN,2.0F,3.0F, SoundType.WOOD));

    public static final Block PALM_LOG = registerBlock("palm_log", Builder.Log(Material.WOOD, MaterialColor.WOOD, MaterialColor.COLOR_BROWN, SoundType.WOOD)); //Was MaterialColor.WOOD
    public static final Block PALM_WOOD = registerBlock("palm_wood", Builder.Bark(Material.WOOD, SoundType.WOOD));
    public static final Block PALM_PLANKS = registerBlock("palm_planks", Builder.tropicBlock(Material.WOOD, MaterialColor.COLOR_BROWN,2.0F,3.0F, SoundType.WOOD));

    public static final Block BAMBOO_BUNDLE = registerBlock("bamboo_bundle", Builder.Bark(Material.WOOD, SoundType.WOOD));
    public static final Block THATCH_BUNDLE = registerBlock("thatch_bundle", Builder.Planks(Material.CLAY, SoundType.CROP, 0.2F, 5.0F));

    public static final Block PALM_STAIRS = registerBlock("palm_stairs", Builder.Stairs(PALM_PLANKS));
    public static final Block MAHOGANY_STAIRS = registerBlock("mahogany_stairs", Builder.Stairs(MAHOGANY_PLANKS));
    public static final Block THATCH_STAIRS = registerBlock("thatch_stairs", Builder.Stairs(THATCH_BUNDLE));
    public static final Block THATCH_STAIRS_FUZZY = registerBlock("thatch_stairs_fuzzy", Builder.Stairs(THATCH_BUNDLE));
    public static final Block BAMBOO_STAIRS = registerBlock("bamboo_stairs", Builder.Stairs(BAMBOO_BUNDLE));
    public static final Block CHUNK_STAIRS = registerBlock("chunk_stairs", Builder.Stairs(CHUNK));

    public static final Block COCONUT = registerBlock("coconut", new TropicraftCoconutBlock());
    public static final CoffeeBushBlock COFFEE_BUSH = registerNoItem("coffee_bush", new CoffeeBushBlock(FabricBlockSettings.of(Material.PLANT, MaterialColor.COLOR_GREEN).strength(0.15f).sound(SoundType.GRASS).noCollission()));//Was MaterialColor.GRASS

    public static final Block BAMBOO_SLAB = registerBlock("bamboo_slab", Builder.Slab(BAMBOO_BUNDLE));
    public static final Block THATCH_SLAB = registerBlock("thatch_slab", Builder.Slab(THATCH_BUNDLE));
    public static final Block CHUNK_SLAB = registerBlock("chunk_slab", Builder.Slab(CHUNK));
    public static final Block PALM_SLAB = registerBlock("palm_slab", Builder.Slab(PALM_PLANKS));
    public static final Block MAHOGANY_SLAB = registerBlock("mahogany_slab", Builder.Slab(MAHOGANY_PLANKS));

    public static final Block BAMBOO_FENCE = registerBlock("bamboo_fence", Builder.Fence(BAMBOO_BUNDLE));
    public static final Block THATCH_FENCE = registerBlock("thatch_fence", Builder.Fence(THATCH_BUNDLE));
    public static final Block CHUNK_FENCE = registerBlock("chunk_fence", Builder.Fence(CHUNK));
    public static final Block PALM_FENCE = registerBlock("palm_fence", Builder.Fence(PALM_PLANKS));
    public static final Block MAHOGANY_FENCE = registerBlock("mahogany_fence", Builder.Fence(MAHOGANY_PLANKS));

    public static final Block BAMBOO_FENCE_GATE = registerBlock("bamboo_fence_gate", Builder.FenceGate(BAMBOO_BUNDLE));
    public static final Block THATCH_FENCE_GATE = registerBlock("thatch_fence_gate", Builder.FenceGate(THATCH_BUNDLE));
    public static final Block CHUNK_FENCE_GATE = registerBlock("chunk_fence_gate", Builder.FenceGate(CHUNK));
    public static final Block PALM_FENCE_GATE = registerBlock("palm_fence_gate", Builder.FenceGate(PALM_PLANKS));
    public static final Block MAHOGANY_FENCE_GATE = registerBlock("mahogany_fence_gate", Builder.FenceGate(MAHOGANY_PLANKS));

    public static final Block BAMBOO_DOOR = registerBlock("bamboo_door", new TropicDoors(BAMBOO_BUNDLE, 1.0F));
    public static final Block PALM_DOOR = registerBlock("palm_door", new TropicDoors(Blocks.OAK_DOOR));
    public static final Block MAHOGANY_DOOR = registerBlock("mahogany_door", new TropicDoors(Blocks.OAK_DOOR));
    public static final Block THATCH_DOOR = registerBlock("thatch_door", new TropicDoors(THATCH_BUNDLE));

    public static final Block BAMBOO_TRAPDOOR = registerBlock("bamboo_trapdoor", new TropicTrapdoorBlock(BAMBOO_DOOR));
    public static final Block PALM_TRAPDOOR = registerBlock("palm_trapdoor", new TropicTrapdoorBlock(PALM_DOOR));
    public static final Block MAHOGANY_TRAPDOOR = registerBlock("mahogany_trapdoor", new TropicTrapdoorBlock(MAHOGANY_DOOR));
    public static final Block THATCH_TRAPDOOR = registerBlock("thatch_trapdoor", new TropicTrapdoorBlock(THATCH_BUNDLE));

    public static final Block BAMBOO_LADDER = registerBlock("bamboo_ladder", new TropicLadderBlock());
    public static final Block BAMBOO_CHEST = registerBlock("bamboo_chest", new TropicBambooChestBlock(BAMBOO_BUNDLE));

    //Will need more work before it will work

    //public static FabricBlockSettings FlowerPot = FabricBlockSettings.of(Material.REPLACEABLE_PLANT).breakInstantly().nonOpaque();

    public static BlockBehaviour.Properties FlowerPot = FabricBlockSettings.copyOf(BAMBOO_BUNDLE).instabreak().noOcclusion();

    public static final Block BAMBOO_FLOWER_POT = registerBlock("bamboo_flower_pot", Builder.BambooPot(Blocks.AIR));

    private static BlockBehaviour.Properties FLOWERS_SETTINGS = FabricBlockSettings.of(Material.PLANT).sound(SoundType.GRASS).instabreak().noCollission();

    public static final Block ACAI_VINE = registerBlock("acai_vine", new TropicFlowerBlock(MobEffects.REGENERATION, 0, 7, 16, FabricBlockSettings.copyOf(FLOWERS_SETTINGS)));
    public static final Block ANEMONE = registerBlock("anemone", new TropicFlowerBlock(MobEffects.REGENERATION, 0, 9, FabricBlockSettings.copyOf(FLOWERS_SETTINGS)));
    public static final Block BROMELIAD = registerBlock("bromeliad", new TropicFlowerBlock(MobEffects.REGENERATION, 0, 9, FabricBlockSettings.copyOf(FLOWERS_SETTINGS)));

    public static final Block CANNA = registerBlock("canna", new TropicFlowerBlock(MobEffects.REGENERATION, 0, FabricBlockSettings.copyOf(FLOWERS_SETTINGS)));
    public static final Block COMMELINA_DIFFUSA = registerBlock("commelina_diffusa", new TropicFlowerBlock(MobEffects.REGENERATION, 0, FabricBlockSettings.copyOf(FLOWERS_SETTINGS)));
    public static final Block CROCOSMIA = registerBlock("crocosmia", new TropicFlowerBlock(MobEffects.REGENERATION, 0,  FabricBlockSettings.copyOf(FLOWERS_SETTINGS)));

    public static final Block CROTON = registerBlock("croton", new TropicFlowerBlock(MobEffects.REGENERATION, 0, 13, FabricBlockSettings.copyOf(FLOWERS_SETTINGS)));
    public static final Block DRACAENA = registerBlock("dracaena", new TropicFlowerBlock(MobEffects.REGENERATION, 0, 13, FabricBlockSettings.copyOf(FLOWERS_SETTINGS)));
    public static final Block TROPICAL_FERN = registerBlock("tropical_fern", new TropicFlowerBlock(MobEffects.REGENERATION, 0, 13, FabricBlockSettings.copyOf(FLOWERS_SETTINGS)));
    public static final Block FOLIAGE = registerBlock("foliage", new TropicFlowerBlock(MobEffects.REGENERATION, 0, 13, FabricBlockSettings.copyOf(FLOWERS_SETTINGS)));

    public static final Block MAGIC_MUSHROOM = registerBlock("magic_mushroom", new TropicFlowerBlock(MobEffects.REGENERATION, 0, 11, FabricBlockSettings.copyOf(FLOWERS_SETTINGS)));
    public static final Block ORANGE_ANTHURIUM = registerBlock("orange_anthurium", new TropicFlowerBlock(MobEffects.REGENERATION, 0, 11,  FabricBlockSettings.copyOf(FLOWERS_SETTINGS)));

    public static final Block ORCHID = registerBlock("orchid", new TropicFlowerBlock(MobEffects.REGENERATION, 0,  FabricBlockSettings.copyOf(FLOWERS_SETTINGS)));
    public static final Block PATHOS = registerBlock("pathos", new TropicFlowerBlock(MobEffects.REGENERATION, 0, 15, 12, FabricBlockSettings.copyOf(FLOWERS_SETTINGS)));
    public static final Block RED_ANTHURIUM = registerBlock("red_anthurium", new TropicFlowerBlock(MobEffects.REGENERATION, 0, 11,  FabricBlockSettings.copyOf(FLOWERS_SETTINGS)));

    /*
    public static final ImmutableMap<Flower, Block> FLOWERS = ImmutableMap.copyOf(
            Flower.FLOWER.values().stream()
            .collect(Collectors.toMap(Function.identity(), d -> registerBlockWithPotAndBlock(d.getId(), Builder.Flower(d, FLOWERS_SETTINGS)))));
     */

    /*
    public static final Map<TropicraftFlower, Registry<FlowerBlock>> FLOWERS = Arrays.<TropicraftFlower>stream(TropicraftFlower.values())
            .collect(Collectors.toMap(Function.identity(), f -> registerBlock(f.getId(), Builder.flower(f)),
                    (f1, f2) -> { throw new IllegalStateException(); }, () -> new EnumMap<>(TropicraftFlower.class)));
    */

    //public static final RegistryObject<TropicBambooPot> BAMBOO_FLOWER_POT = register(
    //            "bamboo_flower_pot", Builder.tropicraftPot());


    public static final Block TIKI_TORCH = registerBlock("tiki_torch", new TropicTikiTorch());

    //public static final RegistryObject<TikiTorchBlock> TIKI_TORCH = register(
    //            "tiki_torch", () -> new TikiTorchBlock(Block.Properties.from(Blocks.TORCH).sound(SoundType.WOOD).setLightLevel(state -> state.get(TikiTorchBlock.SECTION) == TorchSection.UPPER ? 15 : 0)));


    private static BlockBehaviour.Properties SAPLINGS = FabricBlockSettings.of(Material.PLANT).sound(SoundType.GRASS).instabreak().randomTicks().noCollission().noOcclusion();

    public final static SaplingBlock GRAPEFRUIT_SAPLING = registerBlock("grapefruit_sapling", Builder.sapling(TropicraftTrees.GRAPEFRUIT, FabricBlockSettings.copyOf(SAPLINGS)));
    public final static SaplingBlock LEMON_SAPLING = registerBlock("lemon_sapling", Builder.sapling(TropicraftTrees.LEMON, FabricBlockSettings.copyOf(SAPLINGS)));
    public final static SaplingBlock LIME_SAPLING = registerBlock("lime_sapling", Builder.sapling(TropicraftTrees.LIME, FabricBlockSettings.copyOf(SAPLINGS)));
    public final static SaplingBlock ORANGE_SAPLING = registerBlock("orange_sapling", Builder.sapling(TropicraftTrees.ORANGE, FabricBlockSettings.copyOf(SAPLINGS)));
    public final static SaplingBlock MAHOGANY_SAPLING = registerBlock("mahogany_sapling", Builder.sapling(TropicraftTrees.RAINFOREST, FabricBlockSettings.copyOf(SAPLINGS)));
    public final static SaplingBlock PALM_SAPLING = registerBlock("palm_sapling", Builder.sapling(TropicraftTrees.PALM, FabricBlockSettings.copyOf(SAPLINGS), Blocks.SAND, CORAL_SAND, FOAMY_SAND, VOLCANIC_SAND, PURIFIED_SAND, MINERAL_SAND));

    /*
    public static final RegistryObject<SaplingBlock> GRAPEFRUIT_SAPLING = register("grapefruit_sapling", Builder.sapling(TropicraftTrees.GRAPEFRUIT));
    public static final RegistryObject<SaplingBlock> LEMON_SAPLING = register("lemon_sapling", Builder.sapling(TropicraftTrees.LEMON));
    public static final RegistryObject<SaplingBlock> LIME_SAPLING = register("lime_sapling", Builder.sapling(TropicraftTrees.LIME));
    public static final RegistryObject<SaplingBlock> ORANGE_SAPLING = register("orange_sapling", Builder.sapling(TropicraftTrees.ORANGE));
    public static final RegistryObject<SaplingBlock> MAHOGANY_SAPLING = register("mahogany_sapling", Builder.sapling(TropicraftTrees.RAINFOREST));
    public static final RegistryObject<SaplingBlock> PALM_SAPLING = register(
            "palm_sapling", Builder.sapling(TropicraftTrees.PALM, () -> Blocks.SAND, CORAL_SAND, FOAMY_SAND, VOLCANIC_SAND, PURIFIED_SAND, MINERAL_SAND));
    */

    public static final Block MAHOGANY_LEAVES = registerBlock("mahogany_leaves", createLeavesBlock(false));
    public static final Block PALM_LEAVES = registerBlock("palm_leaves", createLeavesBlock(false));
    public static final Block KAPOK_LEAVES = registerBlock("kapok_leaves", createLeavesBlock(false));
    public static final Block FRUIT_LEAVES = registerBlock("fruit_leaves", createLeavesBlock(true));
    public static final Block GRAPEFRUIT_LEAVES = registerBlock("grapefruit_leaves", createLeavesBlock(true));
    public static final Block LEMON_LEAVES = registerBlock("lemon_leaves", createLeavesBlock(true));
    public static final Block LIME_LEAVES = registerBlock("lime_leaves", createLeavesBlock(true));
    public static final Block ORANGE_LEAVES = registerBlock("orange_leaves", createLeavesBlock(true));

    //public static final RegistryObject<PineappleBlock> PINEAPPLE = register(
    //        "pineapple", () -> new PineappleBlock(Block.Properties.create(Material.TALL_PLANTS).tickRandomly().doesNotBlockMovement().hardnessAndResistance(0).sound(SoundType.PLANT)));


    public static final TropicPineapple PINEAPPLE = registerBlock("pineapple", new TropicPineapple());

    public static final Block IRIS = registerBlock("iris", new TropicTallPlant());


    //public static final Block BOX_BLOCK = registerBlock("box_block", new BoxBlock(FabricBlockSettings.copyOf(Blocks.CHEST)));
    //public static final Block BOX_BLOCK_CHEST = registerBlock("box_chest_block", new BoxChestBlock(FabricBlockSettings.copyOf(Blocks.CHEST)));

    /*

    public static final RegistryObject<BongoDrumBlock> SMALL_BONGO_DRUM = register("small_bongo_drum", Builder.bongo(BongoDrumBlock.Size.SMALL));
    public static final RegistryObject<BongoDrumBlock> MEDIUM_BONGO_DRUM = register("medium_bongo_drum", Builder.bongo(BongoDrumBlock.Size.MEDIUM));
    public static final RegistryObject<BongoDrumBlock> LARGE_BONGO_DRUM = register("large_bongo_drum", Builder.bongo(BongoDrumBlock.Size.LARGE));

    public static final RegistryObject<SifterBlock> SIFTER = register(
            "sifter", () -> new SifterBlock(Block.Properties.from(Blocks.OAK_PLANKS).notSolid()));
    public static final RegistryObject<DrinkMixerBlock> DRINK_MIXER = register(
            "drink_mixer", () -> new DrinkMixerBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2, 30).notSolid()),
            () -> drinkMixerRenderer());

    public static final RegistryObject<AirCompressorBlock> AIR_COMPRESSOR = register(
            "air_compressor", () -> new AirCompressorBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2, 30).notSolid()),
            () -> airCompressorRenderer());

    public static final RegistryObject<VolcanoBlock> VOLCANO = registerNoItem(
            "volcano", () -> new VolcanoBlock(Block.Properties.from(Blocks.BEDROCK).noDrops()));

    public static final RegistryObject<CoffeeBushBlock> COFFEE_BUSH = registerNoItem(
            "coffee_bush", () -> new CoffeeBushBlock(Block.Properties.create(Material.PLANTS, MaterialColor.GRASS).hardnessAndResistance(0.15f).sound(SoundType.PLANT).notSolid()));

    */


    private static final Set<Block> POTTABLE_PLANTS = ImmutableSet.<Block>builder()
            .add(PALM_SAPLING, MAHOGANY_SAPLING, GRAPEFRUIT_SAPLING, LEMON_SAPLING, LIME_SAPLING, ORANGE_SAPLING)
            .add(IRIS)
            .add(ACAI_VINE, ANEMONE, BROMELIAD, CANNA, COMMELINA_DIFFUSA, CROCOSMIA, CROTON, DRACAENA, TROPICAL_FERN, FOLIAGE, MAGIC_MUSHROOM, ORANGE_ANTHURIUM, ORCHID, PATHOS, RED_ANTHURIUM)
            .build();

    public static final List<Block> BAMBOO_POTTED_TROPICS_PLANTS = ImmutableList.copyOf(POTTABLE_PLANTS.stream()
            .map(b -> registerNoItem("bamboo_potted_" + Registry.BLOCK.getKey(b).getPath(), Builder.tropicraftPot(b)))
            .collect(Collectors.toList()));

    public static final List<Block> VANILLA_POTTED_TROPICS_PLANTS = ImmutableList.copyOf(POTTABLE_PLANTS.stream()
            .map(b -> registerNoItem("potted_" + Registry.BLOCK.getKey(b).getPath(), Builder.vanillaPot(b)))
            .collect(Collectors.toList()));

    public static final List<Block> BAMBOO_POTTED_VANILLA_PLANTS = ImmutableList.copyOf(
            Stream.of(Blocks.OAK_SAPLING, Blocks.SPRUCE_SAPLING, Blocks.BIRCH_SAPLING, Blocks.JUNGLE_SAPLING,
                    Blocks.ACACIA_SAPLING, Blocks.DARK_OAK_SAPLING, Blocks.FERN, Blocks.DANDELION, Blocks.POPPY,
                    Blocks.BLUE_ORCHID, Blocks.ALLIUM, Blocks.AZURE_BLUET, Blocks.RED_TULIP, Blocks.ORANGE_TULIP,
                    Blocks.WHITE_TULIP, Blocks.PINK_TULIP, Blocks.OXEYE_DAISY, Blocks.CORNFLOWER, Blocks.LILY_OF_THE_VALLEY,
                    Blocks.WITHER_ROSE, Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM, Blocks.DEAD_BUSH, Blocks.CACTUS)
                    .map(b -> registerNoItem("bamboo_potted_" + Registry.BLOCK.getKey(b).getPath(), Builder.tropicraftPot(b)))
                    .collect(Collectors.toList()));

    public static final List<Block> ALL_POTTED_PLANTS = ImmutableList.<Block>builder()
            .addAll(BAMBOO_POTTED_TROPICS_PLANTS)
            .addAll(VANILLA_POTTED_TROPICS_PLANTS)
            .addAll(BAMBOO_POTTED_VANILLA_PLANTS)
            .build();


    private static <T extends Block> T registerNoItem(String id, T block) {
        Registry.register(Registry.BLOCK, new ResourceLocation(Constants.MODID, id), block);
        return block;
    }

    private static CoffeeBushBlock registerNoItemCoffee(String id, CoffeeBushBlock block) {
        Registry.register(Registry.BLOCK, new ResourceLocation(Constants.MODID, id), block);
        return block;
    }

    /*
    public static Block registerBlock(String id, Block block) {
        if(block == BAMBOO_CHEST || block == COCONUT){
            Registry.register(Registry.BLOCK, new Identifier(Constants.MODID, id), block);
        }

        else {
            Registry.register(Registry.BLOCK, new Identifier(Constants.MODID, id), block);
            registerBlockItem(id, new TropicBlockItem(block));
        }

        return block;
    }
     */

    public static <T extends Block> T registerBlock(String id, T block) {
        if(block == BAMBOO_CHEST || block == COCONUT){
            Registry.register(Registry.BLOCK, new ResourceLocation(Constants.MODID, id), block);
        }

        else {
            Registry.register(Registry.BLOCK, new ResourceLocation(Constants.MODID, id), block);
            TropicraftItems.registerItem(id, new TropicBlockItem(block));
        }

        return block;
    }

    private static Block createLeavesBlock(boolean isDecay){
        if(isDecay == true){
            return new LeavesBlock(FabricBlockSettings.of(Material.LEAVES)
                    .breakByTool(FabricToolTags.HOES)
                    .breakByTool(FabricToolTags.SHEARS)
                    .breakByHand(true)
                    .strength(0.2f)
                    .noOcclusion()
                    .sound(SoundType.GRASS));
        }
        else{
            return new TropicLeavesBlock();
        }
    }

    public static void init() {

    }
}
