package net.tropicraft.core.common.registry;

import net.tropicraft.Constants;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TropicraftEntityAttributes {
    public static final EntityAttribute ENTITY_GRAVITY = new ClampedEntityAttribute("tropicraft.entity_gravity", 0.08D, -8.0D, 8.0D).setTracked(true);
    public static final EntityAttribute SWIM_SPEED = new ClampedEntityAttribute("tropicraft.swimSpeed", 1.0D, 0.0D, 1024.0D).setTracked(true);
    public static final EntityAttribute NAMETAG_DISTANCE = new ClampedEntityAttribute("tropicraft.nameTagDistance", 64.0D, 0.0D, 64.0).setTracked(true);


    public void init(){
        register("entity_gravity", ENTITY_GRAVITY);
        register("swim_speed", SWIM_SPEED);
        register("nametag_distance", NAMETAG_DISTANCE);
    }

    private static EntityAttribute register(String id, EntityAttribute attribute) {
        return Registry.register(Registry.ATTRIBUTE, new Identifier(Constants.MODID, id), attribute);
    }

}
