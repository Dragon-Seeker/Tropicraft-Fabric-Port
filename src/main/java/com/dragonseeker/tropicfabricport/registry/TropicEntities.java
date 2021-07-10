package com.dragonseeker.tropicfabricport.registry;

import com.dragonseeker.tropicfabricport.Tropicfabricport;
import com.dragonseeker.tropicfabricport.entity.BambooItemFrameEntity;
import com.dragonseeker.tropicfabricport.entity.ExplodingCoconutEntity;
import com.dragonseeker.tropicfabricport.entity.IEntityType;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TropicEntities<T extends Entity> implements IEntityType {

    public static EntityType<BambooItemFrameEntity> BAMBOO_ITEM_FRAME;
    public static EntityType<ExplodingCoconutEntity> EXPLODING_COCONUT;

    @Override
    public boolean alwaysUpdateVelocity() {
        if(this.equals(BAMBOO_ITEM_FRAME)) {
            return false;
        }
        else{
            return true;
        }
    }

    static {
        //BAMBOO_ITEM_FRAME = registerEntityType("bamboo_item_frame", SpawnGroup.MISC, 0.5F, 0.5F, 8, 3, false, BambooItemFrameEntity::new);
                /*Builder.create(BambooItemFrameEntity::new, SpawnGroup.MISC)
                .setDimensions(0.5F, 0.5F)
                .maxTrackingRange(8)
                .trackingTickInterval(3));

                 */

        EXPLODING_COCONUT = registerEntity("exploding_coconut", explodingCoconut());

        BAMBOO_ITEM_FRAME = registerEntity("bamboo_item_frame", bambooItemFrame());

        //BAMBOO_ITEM_FRAME = registerEntityType("bamboo_item_frame", SpawnGroup.MISC, 0.5F, 0.5F, 8, 3, false, BambooItemFrameEntity::new);



        /*
        BAMBOO_ITEM_FRAME = registerEntityType("bamboo_item_frame", EntityType.Builder.create(BambooItemFrameEntity::new, SpawnGroup.MISC)
                .setDimensions(0.5F, 0.5F)
                .maxTrackingRange(8)
                .trackingTickInterval(3));
                //.setShouldReceiveVelocityUpdates(false);

         */

    }


    public static void init(){

    }

    public static <T extends Entity> EntityType<T> entityBuilder(SpawnGroup group, float width, float height, int trackingRange, int trackingInterval, boolean velocityUpdates, EntityType.EntityFactory<T> entity) { //FabricEntityTypeBuilder<T> builder,
        //return (EntityType) Registry.register(Registry.ENTITY_TYPE, new Identifier(Tropicfabricport.MOD_ID, id), builder.build());

        EntityType<T> type = FabricEntityTypeBuilder
                .create(group, entity)
                .dimensions(EntityDimensions
                        .fixed(width, height))
                .trackRangeBlocks(trackingRange)
                .trackedUpdateRate(trackingInterval)
                .forceTrackedVelocityUpdates(velocityUpdates)
                .build();

        return type;
    }

    public static <T extends Entity> EntityType<T> registerEntity(String id, FabricEntityTypeBuilder<T> builder) { //FabricEntityTypeBuilder<T> builder,
        return (EntityType) Registry.register(Registry.ENTITY_TYPE, new Identifier(Tropicfabricport.MOD_ID, id), builder.build());

        //return Registry.register(Registry.ENTITY_TYPE, new Identifier(Tropicfabricport.MOD_ID, id), type);

    }


    /*
    public static <T extends Entity> EntityType<T> registerEntityType(String id, EntityType.Builder<T> builder) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(Tropicfabricport.MOD_ID, id), builder.build(null));
    }

     */

    private static FabricEntityTypeBuilder<ExplodingCoconutEntity> explodingCoconut() {
        return FabricEntityTypeBuilder
                .create(SpawnGroup.MISC, (EntityType.EntityFactory<ExplodingCoconutEntity>) ExplodingCoconutEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.25F, 0.25F))
                .trackRangeBlocks(4)
                .trackedUpdateRate(10)
                .forceTrackedVelocityUpdates(true);
        /*
        return FabricEntityTypeBuilder
                .create(SpawnGroup.MISC, (type, world) -> new ExplodingCoconutEntity((EntityType<? extends ExplodingCoconutEntity>) type, world))
                .dimensions(EntityDimensions
                        .fixed(0.25F, 0.25F))
                .trackRangeBlocks(4)
                .trackedUpdateRate(10)
                .forceTrackedVelocityUpdates(true)
                .build();

         */
    }

    private static FabricEntityTypeBuilder<BambooItemFrameEntity> bambooItemFrame() {
        return FabricEntityTypeBuilder
                .create(SpawnGroup.MISC, (EntityType.EntityFactory<BambooItemFrameEntity>) BambooItemFrameEntity::new)
                .dimensions(EntityDimensions
                        .fixed(0.5F, 0.5F))
                .trackRangeBlocks(8)
                .trackedUpdateRate(3)
                .forceTrackedVelocityUpdates(true);
    }

}
