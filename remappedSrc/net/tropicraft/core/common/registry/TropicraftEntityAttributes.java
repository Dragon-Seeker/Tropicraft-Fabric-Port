package net.tropicraft.core.common.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.tropicraft.Constants;

public class TropicraftEntityAttributes {
    public static final Attribute ENTITY_GRAVITY = register("entity_gravity", new RangedAttribute("tropicraft.entity_gravity", 0.08D, -8.0D, 8.0D).setSyncable(true));
    public static final Attribute SWIM_SPEED = register("swim_speed", new RangedAttribute("tropicraft.swimSpeed", 1.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final Attribute NAMETAG_DISTANCE = register("nametag_distance",new RangedAttribute("tropicraft.nameTagDistance", 64.0D, 0.0D, 64.0).setSyncable(true));


    public static void init(){
    }

    private static Attribute register(String id, Attribute attribute) {
        return (Attribute)Registry.register(Registry.ATTRIBUTE, new ResourceLocation(Constants.MODID, id), attribute);
    }

}
