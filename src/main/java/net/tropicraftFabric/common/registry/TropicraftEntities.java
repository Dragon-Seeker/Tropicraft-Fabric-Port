package net.tropicraftFabric.common.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.tropicraftFabric.Constants;
import net.tropicraftFabric.common.entity.BambooItemFrameEntity;
import net.tropicraftFabric.common.entity.SeaTurtleEntity;
import net.tropicraftFabric.common.entity.TropiBeeEntity;
import net.tropicraftFabric.common.entity.egg.*;
import net.tropicraftFabric.common.entity.hostile.AshenEntity;
import net.tropicraftFabric.common.entity.hostile.TropiSkellyEntity;
import net.tropicraftFabric.common.entity.hostile.TropiSpiderEntity;
import net.tropicraftFabric.common.entity.neutral.EIHEntity;
import net.tropicraftFabric.common.entity.neutral.IguanaEntity;
import net.tropicraftFabric.common.entity.neutral.TreeFrogEntity;
import net.tropicraftFabric.common.entity.neutral.VMonkeyEntity;
import net.tropicraftFabric.common.entity.passive.*;
import net.tropicraftFabric.common.entity.placeable.*;
import net.tropicraftFabric.common.entity.projectile.ExplodingCoconutEntity;
import net.tropicraftFabric.common.entity.projectile.LavaBallEntity;
import net.tropicraftFabric.common.entity.projectile.PoisonBlotEntity;
import net.tropicraftFabric.common.entity.underdasea.*;

import java.util.Random;


public class TropicraftEntities {

    private static final float EGG_WIDTH = 0.4F;
    private static final float EGG_HEIGHT = 0.5F;

    //public static final DeferredRegister<EntityType<?> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Constants.MODID);

    public static final EntityType<EntityKoaHunter> KOA_HUNTER = registerEntity("koa", koaHunter());
    public static final EntityType<TropiCreeperEntity> TROPI_CREEPER = registerEntity("tropicreeper", tropicreeper());
    public static final EntityType<IguanaEntity> IGUANA = registerEntity("iguana", iguana());
    public static final EntityType<UmbrellaEntity> UMBRELLA = registerEntity("umbrella", umbrella());
    public static final EntityType<ChairEntity> CHAIR = registerEntity("chair", chair());
    public static final EntityType<BeachFloatEntity> BEACH_FLOAT = registerEntity("beach_float", beachFloat());
    public static final EntityType<TropiSkellyEntity> TROPI_SKELLY = registerEntity("tropiskelly", tropiskelly());
    public static final EntityType<EIHEntity> EIH = registerEntity("eih", eih());
    public static final EntityType<WallItemEntity> WALL_ITEM = registerEntity("wall_item", wallItem());
    public static final EntityType<BambooItemFrameEntity> BAMBOO_ITEM_FRAME = registerEntity("bamboo_item_frame", bambooItemFrame());
    // TODO: Register again when volcano eruption is finished
    public static final EntityType<LavaBallEntity> LAVA_BALL = registerEntity("lava_ball", lavaBall());
    public static final EntityType<SeaTurtleEntity> SEA_TURTLE = registerEntity("turtle", turtle());
    public static final EntityType<MarlinEntity> MARLIN = registerEntity("marlin", marlin());
    public static final EntityType<FailgullEntity> FAILGULL = registerEntity("failgull", failgull());
    public static final EntityType<TropicraftDolphinEntity> DOLPHIN = registerEntity("dolphin", dolphin());
    public static final EntityType<SeahorseEntity> SEAHORSE = registerEntity("seahorse", seahorse());
    public static final EntityType<PoisonBlotEntity> POISON_BLOT = registerEntity("poison_blot", poisonBlot());
    public static final EntityType<TreeFrogEntity> TREE_FROG = registerEntity("tree_frog", treeFrog());
    public static final EntityType<SeaUrchinEntity> SEA_URCHIN = registerEntity("sea_urchin", seaUrchin());
    public static final EntityType<SeaUrchinEggEntity> SEA_URCHIN_EGG_ENTITY = registerEntity("sea_urchin_egg", seaUrchinEgg());
    public static final EntityType<StarfishEntity> STARFISH = registerEntity("starfish", starfish());
    public static final EntityType<StarfishEggEntity> STARFISH_EGG = registerEntity("starfish_egg", starfishEgg());
    public static final EntityType<VMonkeyEntity> V_MONKEY = registerEntity("v_monkey", vervetMonkey());
    public static final EntityType<SardineEntity> RIVER_SARDINE = registerEntity("sardine", riverSardine());
    public static final EntityType<PiranhaEntity> PIRANHA = registerEntity("piranha", piranha());
    public static final EntityType<TropicraftTropicalFishEntity> TROPICAL_FISH = registerEntity("tropical_fish", tropicalFish());
    public static final EntityType<EagleRayEntity> EAGLE_RAY = registerEntity("eagle_ray", eagleRay());
    public static final EntityType<TropiSpiderEntity> TROPI_SPIDER = registerEntity("tropi_spider", tropiSpider());
    public static final EntityType<TropiSpiderEggEntity> TROPI_SPIDER_EGG = registerEntity("tropi_spider_egg", tropiSpiderEgg());
    public static final EntityType<AshenMaskEntity> ASHEN_MASK = registerEntity("ashen_mask", ashenMask());
    public static final EntityType<AshenEntity> ASHEN = registerEntity("ashen", ashen());
    public static final EntityType<ExplodingCoconutEntity> EXPLODING_COCONUT = registerEntity("exploding_coconut", explodingCoconut());
    public static final EntityType<SharkEntity> HAMMERHEAD = registerEntity("hammerhead", hammerhead());
    public static final EntityType<SeaTurtleEggEntity> SEA_TURTLE_EGG = registerEntity("turtle_egg", turtleEgg());
    public static final EntityType<TropiBeeEntity> TROPI_BEE = registerEntity("tropibee", tropiBee());
    public static final EntityType<CowktailEntity> COWKTAIL = registerEntity("cowktail", cowktail());
    public static final EntityType<ManOWarEntity> MAN_O_WAR = registerEntity("man_o_war", manOWar());

    public static <T extends Entity> EntityType<T> registerEntity(String id, FabricEntityTypeBuilder<T> builder) { //FabricEntityTypeBuilder<T> builder,
        return (EntityType) Registry.register(Registry.ENTITY_TYPE, new Identifier(Constants.MODID, id), builder.build());

        //return Registry.register(Registry.ENTITY_TYPE, new Identifier(Tropicfabricport.MOD_ID, id), type);

    }

    // TODO review -- tracking range is in chunks...these values seem way too high

    private static FabricEntityTypeBuilder<CowktailEntity> cowktail() {
        return FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CowktailEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.9F, 1.4F))
                .trackRangeBlocks(10)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<ManOWarEntity> manOWar() {
        return FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, ManOWarEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.6F, 0.8F)
                )
                .trackRangeBlocks(10)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<TropiBeeEntity> tropiBee() {
        return FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TropiBeeEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.4F, 0.6F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<SeaTurtleEggEntity> turtleEgg() {
        return FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SeaTurtleEggEntity::new)
                .dimensions(EntityDimensions
                        .fixed(EGG_WIDTH, EGG_HEIGHT)
                )
                .trackRangeBlocks(6)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(false);
    }
    
    private static FabricEntityTypeBuilder<SharkEntity> hammerhead() {
        return FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, SharkEntity::new)
                .dimensions(EntityDimensions
                        .fixed(2.4F, 1.4F)
                )
                .trackRangeBlocks(5)
                .trackedUpdateRate(2)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<ExplodingCoconutEntity> explodingCoconut() {
        return FabricEntityTypeBuilder
                .create(SpawnGroup.MISC, (EntityType.EntityFactory<ExplodingCoconutEntity>) ExplodingCoconutEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.25F, 0.25F))
                .trackRangeBlocks(4)
                .trackedUpdateRate(10)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<AshenMaskEntity> ashenMask() {
        return FabricEntityTypeBuilder.<AshenMaskEntity>create( SpawnGroup.MISC, AshenMaskEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.8F, 0.2F)
                )
                .trackRangeBlocks(6)
                .trackedUpdateRate(100)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<AshenEntity> ashen() {
        return FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, AshenEntity::new )
                .dimensions(EntityDimensions
                        .fixed(0.5F, 1.3F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<TropiSpiderEntity> tropiSpider() {
        return FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TropiSpiderEntity::new)
                .dimensions(EntityDimensions
                        .fixed(1.4F, 0.9F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<TropiSpiderEggEntity> tropiSpiderEgg() {
        return FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TropiSpiderEggEntity::new)
                .dimensions(EntityDimensions
                        .fixed(EGG_WIDTH, EGG_HEIGHT)
                )
                .trackRangeBlocks(6)
                .trackedUpdateRate(10)
                .forceTrackedVelocityUpdates(false);
    }

    private static FabricEntityTypeBuilder<EagleRayEntity> eagleRay() {
        return FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, EagleRayEntity::new )
                .dimensions(EntityDimensions
                        .fixed(2F, 0.4F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<TropicraftTropicalFishEntity> tropicalFish() {
        return FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, TropicraftTropicalFishEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.3F, 0.4F)
                )
                .trackRangeBlocks(4)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<SardineEntity> riverSardine() {
        return FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, SardineEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.3F, 0.4F)
                )
                .trackRangeBlocks(4)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<PiranhaEntity> piranha() {
        return FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, PiranhaEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.3F, 0.4F)
                )
                .trackRangeBlocks(4)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<VMonkeyEntity> vervetMonkey() {
        return FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, VMonkeyEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.8F, 0.8F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<StarfishEggEntity> starfishEgg() {
        return FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, StarfishEggEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.4F, 0.5F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(false);
    }

    private static FabricEntityTypeBuilder<StarfishEntity> starfish() {
        return FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, StarfishEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.5F, 0.5F)
                )
                .trackRangeBlocks(4)
                .trackedUpdateRate(15)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<SeaUrchinEggEntity> seaUrchinEgg() {
        return FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, SeaUrchinEggEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.4F, 0.5F)
                )
                .trackRangeBlocks(6)
                .trackedUpdateRate(15)
                .forceTrackedVelocityUpdates(false);
    }

    private static FabricEntityTypeBuilder<SeaUrchinEntity> seaUrchin() {
        return FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, SeaUrchinEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.5F, 0.5F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<TreeFrogEntity> treeFrog() {
        return FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TreeFrogEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.6F, 0.4F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<PoisonBlotEntity> poisonBlot() {
        return FabricEntityTypeBuilder.<PoisonBlotEntity>create(SpawnGroup.MISC, PoisonBlotEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.25F, 0.25F)
                )
                .trackRangeBlocks(4)
                .trackedUpdateRate(20)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<SeahorseEntity> seahorse() {
        return FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, SeahorseEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.5F, 0.6F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<TropicraftDolphinEntity> dolphin() {
        return FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, TropicraftDolphinEntity::new)
                .dimensions(EntityDimensions
                        .fixed(1.4F, 0.5F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<FailgullEntity> failgull() {
        return FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, FailgullEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.4F, 0.6F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<MarlinEntity> marlin() {
        return FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, MarlinEntity::new)
                .dimensions(EntityDimensions
                        .fixed(1.4F, 0.95F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<SeaTurtleEntity> turtle() {
        return FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SeaTurtleEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.8F, 0.35F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<BambooItemFrameEntity> bambooItemFrame() {
        return FabricEntityTypeBuilder
                .create(SpawnGroup.MISC, (EntityType.EntityFactory<BambooItemFrameEntity>) BambooItemFrameEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.5F, 0.5F))
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(false);
    }

    private static FabricEntityTypeBuilder<LavaBallEntity> lavaBall() {
        return FabricEntityTypeBuilder.<LavaBallEntity>create(SpawnGroup.MISC, LavaBallEntity::new)
                .dimensions(EntityDimensions
                        .fixed(1.0F, 1.0F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<WallItemEntity> wallItem() {
        return FabricEntityTypeBuilder.<WallItemEntity>create(SpawnGroup.MISC, WallItemEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.5F, 0.5F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(Integer.MAX_VALUE)
                .forceTrackedVelocityUpdates(false);
    }

    private static FabricEntityTypeBuilder<EIHEntity> eih() {
        return FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EIHEntity::new)
                .dimensions(EntityDimensions
                        .fixed(1.2F, 3.25F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<TropiSkellyEntity> tropiskelly() {
        return FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TropiSkellyEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.7F, 1.95F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<UmbrellaEntity> umbrella() {
        return FabricEntityTypeBuilder.<UmbrellaEntity>create(SpawnGroup.MISC, UmbrellaEntity::new)
                .dimensions(EntityDimensions
                        .fixed(1.0F, 4.0F)
                )
                .trackRangeBlocks(10)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(false);
    }

    private static FabricEntityTypeBuilder<ChairEntity> chair() {
        return FabricEntityTypeBuilder.<ChairEntity>create(SpawnGroup.MISC, ChairEntity::new)
                .dimensions(EntityDimensions
                        .fixed(1.5F, 0.5F)
                )
                .trackRangeBlocks(10)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(false);
    }

    private static FabricEntityTypeBuilder<BeachFloatEntity> beachFloat() {
        return FabricEntityTypeBuilder.<BeachFloatEntity>create(SpawnGroup.MISC, BeachFloatEntity::new)
                .dimensions(EntityDimensions
                        .fixed(2F, 0.175F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(false);
    }
    
    private static FabricEntityTypeBuilder<IguanaEntity> iguana() {
        return FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, IguanaEntity::new)
                .dimensions(EntityDimensions
                        .fixed(1.0F, 0.4F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .fireImmune()
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<EntityKoaHunter> koaHunter() {
        return FabricEntityTypeBuilder.create(SpawnGroup.MISC, EntityKoaHunter::new)
                .dimensions(EntityDimensions
                        .fixed(0.6F, 1.95F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .fireImmune()
                .forceTrackedVelocityUpdates(true);
    }

    private static FabricEntityTypeBuilder<TropiCreeperEntity> tropicreeper() {
        return FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TropiCreeperEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.6F, 1.7F)
                )
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }



    public static boolean canAnimalSpawn(EntityType<? extends MobEntity> animal, WorldAccess worldIn, SpawnReason reason, BlockPos pos, Random random) {
        return worldIn.getBlockState(pos.down()).getBlock() == Blocks.GRASS_BLOCK || worldIn.getBlockState(pos.down()).getMaterial() == Material.AGGREGATE;
    }

    private static <T extends MobEntity> void registerLandSpawn(final EntityType<T> type, SpawnRestriction.SpawnPredicate<T> predicate) {
        SpawnRestrictionAccessor.callRegister(type, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, predicate);
    }

    private static <T extends MobEntity> void registerWaterSpawn(final EntityType<T> type, SpawnRestriction.SpawnPredicate<T> predicate) {
        SpawnRestrictionAccessor.callRegister(type, SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, predicate);
    }

    public static <T extends MobEntity> boolean canSpawnOceanWaterMob(EntityType<T> waterMob, WorldAccess world, SpawnReason reason, BlockPos pos, Random rand) {
        return pos.getY() > 90 && pos.getY() < world.getSeaLevel() && world.getFluidState(pos).isIn(FluidTags.WATER);
    }

    public static <T extends MobEntity> boolean canSpawnSurfaceOceanWaterMob(EntityType<T> waterMob, WorldAccess world, SpawnReason reason, BlockPos pos, Random rand) {
        return pos.getY() > world.getSeaLevel() - 3 && pos.getY() < world.getSeaLevel() && world.getFluidState(pos).isIn(FluidTags.WATER);
    }

    public static void init(){

    }

    public static void registerSpawns() {
        registerWaterSpawn(TROPICAL_FISH, FishEntity::canSpawn);
        registerWaterSpawn(RIVER_SARDINE, FishEntity::canSpawn);
        registerWaterSpawn(PIRANHA, FishEntity::canSpawn);
        registerWaterSpawn(DOLPHIN, TropicraftEntities::canSpawnOceanWaterMob);
        registerWaterSpawn(EAGLE_RAY, TropicraftEntities::canSpawnOceanWaterMob);
        registerWaterSpawn(MARLIN, TropicraftEntities::canSpawnOceanWaterMob);
        registerWaterSpawn(SEAHORSE, TropicraftEntities::canSpawnOceanWaterMob);
        registerWaterSpawn(SEA_URCHIN, TropicraftEntities::canSpawnOceanWaterMob);
        registerWaterSpawn(STARFISH, TropicraftEntities::canSpawnOceanWaterMob);
        registerWaterSpawn(HAMMERHEAD, TropicraftEntities::canSpawnOceanWaterMob);
        registerWaterSpawn(MAN_O_WAR, TropicraftEntities::canSpawnSurfaceOceanWaterMob);

        registerLandSpawn(KOA_HUNTER, TropicraftEntities::canAnimalSpawn);
        registerLandSpawn(TROPI_CREEPER, TropicraftEntities::canAnimalSpawn);
        registerLandSpawn(IGUANA, TropicraftEntities::canAnimalSpawn);
        registerLandSpawn(TROPI_SKELLY, HostileEntity::canSpawnInDark);
        registerLandSpawn(TROPI_SPIDER, HostileEntity::canSpawnInDark);
        registerLandSpawn(EIH, TropicraftEntities::canAnimalSpawn);
        registerLandSpawn(SEA_TURTLE, SeaTurtleEntity::canSpawnOnLand);
        registerLandSpawn(TREE_FROG, TropicraftEntities::canAnimalSpawn);
        registerLandSpawn(V_MONKEY, TropicraftEntities::canAnimalSpawn);
        registerLandSpawn(COWKTAIL, TropicraftEntities::canAnimalSpawn);

        registerLandSpawn(ASHEN, MobEntity::canMobSpawn);
        registerLandSpawn(FAILGULL, MobEntity::canMobSpawn);
        registerLandSpawn(TROPI_BEE, MobEntity::canMobSpawn);
        // TODO tropibee, or from nests?
    }

    public static void registerEntityAttributes(){
        FabricDefaultAttributeRegistry.register(TropicraftEntities.KOA_HUNTER, EntityKoaBase.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.TROPI_CREEPER, TropiCreeperEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.IGUANA, IguanaEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.TROPI_SKELLY, TropiSkellyEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.EIH, EIHEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.SEA_TURTLE, SeaTurtleEntity.createTurtleAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.MARLIN, MarlinEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.FAILGULL, FailgullEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.DOLPHIN, TropicraftDolphinEntity.createDolphinAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.SEAHORSE, SeahorseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.TREE_FROG, TreeFrogEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.SEA_URCHIN, SeaUrchinEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.SEA_URCHIN_EGG_ENTITY, EggEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.STARFISH, StarfishEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.STARFISH_EGG, EggEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.V_MONKEY, VMonkeyEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.RIVER_SARDINE, SardineEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.PIRANHA, PiranhaEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.TROPICAL_FISH, TropicraftTropicalFishEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.EAGLE_RAY, EagleRayEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.TROPI_SPIDER, TropiSpiderEntity.createSpiderAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.TROPI_SPIDER_EGG, EggEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.ASHEN, AshenEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.HAMMERHEAD, SharkEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.SEA_TURTLE_EGG, EggEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.TROPI_BEE, TropiBeeEntity.createBeeAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.COWKTAIL, CowktailEntity.createCowAttributes());
        FabricDefaultAttributeRegistry.register(TropicraftEntities.MAN_O_WAR, ManOWarEntity.createAttributes());
    }


}
