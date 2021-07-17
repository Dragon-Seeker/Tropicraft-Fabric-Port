package com.dragonseeker.tropicfabricport.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;

public class TropicEntityAttributes {

    public static void init(){
        FabricDefaultAttributeRegistry.register(TropicEntities.ASHEN, ashenAttriubtesBuilder());
    }


    private static DefaultAttributeContainer.Builder ashenAttriubtesBuilder(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35D);
    }


}
