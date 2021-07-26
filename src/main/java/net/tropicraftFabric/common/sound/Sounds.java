package net.tropicraftFabric.common.sound;

import net.tropicraftFabric.Constants;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * All Tropicraft mod sounds are registered here. To create a new one:
 *
 * - Create a new public static SoundEvent field named whatever you want
 * - Set that field = null
 * - Add an @SoundName annotation with a string inside equal to whatever you put as the key in sounds.json
 * - Reference it anywhere. This class is initialized super early so you don't have to worry about
 *   something using it before its values are set.
 */
public class Sounds {
    @SoundName("buried_treasure")
    public static SoundEvent BURIED_TREASURE = null;

    @SoundName("eastern_isles")
    public static SoundEvent EASTERN_ISLES = null;

    @SoundName("low_tide")
    public static SoundEvent LOW_TIDE = null;

    @SoundName("summering")
    public static SoundEvent SUMMERING = null;

    @SoundName("the_tribe")
    public static SoundEvent THE_TRIBE = null;

    @SoundName("trade_winds")
    public static SoundEvent TRADE_WINDS = null;

    @SoundName("page_flip")
    public static SoundEvent PAGE_FLIP = null;

    @SoundName("bongo.low")
    public static SoundEvent BONGO_LOW = null;

    @SoundName("bongo.medium")
    public static SoundEvent BONGO_MED = null;

    @SoundName("bongo.high")
    public static SoundEvent BONGO_HIGH = null;

    @SoundName("headlaughing")
    public static SoundEvent HEAD_LAUGHING = null;

    @SoundName("headattack")
    public static SoundEvent HEAD_ATTACK = null;

    @SoundName("headshort")
    public static SoundEvent HEAD_SHORT = null;

    @SoundName("headmed")
    public static SoundEvent HEAD_MED = null;

    @SoundName("headpain")
    public static SoundEvent HEAD_PAIN = null;

    @SoundName("headdeath")
    public static SoundEvent HEAD_DEATH = null;

    @SoundName("iggyattack")
    public static SoundEvent IGGY_ATTACK = null;

    @SoundName("iggydeath")
    public static SoundEvent IGGY_DEATH = null;

    @SoundName("iggyliving")
    public static SoundEvent IGGY_LIVING = null;

    @SoundName("ashen_laugh")
    public static SoundEvent ASHEN_LAUGH = null;

    @SoundName("dolphin")
    public static SoundEvent DOLPHIN = null;

    @SoundName("frogspit")
    public static SoundEvent FROG_SPIT = null;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public static @interface SoundName {
        String value();
    }

    /**
     * Maintains a list of registered sounds so we can check for duplicate registrations
     */
    private static List<String> registeredSounds = new ArrayList<>();
    /*
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            for (Field f : Sounds.class.getDeclaredFields()) {
                if (f.isAnnotationPresent(SoundName.class)) {
                    try {
                        f.set(null, register(f.getAnnotation(SoundName.class).value()));
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
     */
    public static void init(){
        for (Field f : Sounds.class.getDeclaredFields()) {
            if (f.isAnnotationPresent(SoundName.class)) {
                try {
                    f.set(null, register(f.getAnnotation(SoundName.class).value()));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static SoundEvent register(String soundPath) {
        Identifier resLoc = new Identifier(Constants.MODID, soundPath);
        SoundEvent soundEvent = new SoundEvent(resLoc);
        Registry.register(Registry.SOUND_EVENT, resLoc, soundEvent);
        //ForgeRegistries.SOUND_EVENTS.register(soundEvent.setRegistryName(resLoc));
        if (registeredSounds.contains(soundPath)) {
            System.out.println("TCWARNING: duplicate sound registration for " + soundPath);
        }
        registeredSounds.add(soundPath);

        return soundEvent;
    }
}
