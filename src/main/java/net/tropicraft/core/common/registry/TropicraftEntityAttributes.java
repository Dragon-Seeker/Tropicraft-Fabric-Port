package net.tropicraft.core.common.registry;

import net.tropicraft.Constants;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TropicraftEntityAttributes {
    public static final EntityAttribute ENTITY_GRAVITY = new ClampedEntityAttribute("forge.entity_gravity", 0.08D, -8.0D, 8.0D).setTracked(true);

    public void init(){
        register("entity_gravity", ENTITY_GRAVITY);
    }

    private static EntityAttribute register(String id, EntityAttribute attribute) {
        return Registry.register(Registry.ATTRIBUTE, new Identifier(Constants.MODID, id), attribute);
    }

}
