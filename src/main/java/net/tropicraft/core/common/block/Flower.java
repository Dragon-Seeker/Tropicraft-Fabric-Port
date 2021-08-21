package net.tropicraft.core.common.block;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.shape.VoxelShape;

public class Flower {
    protected static VoxelShape SHAPE;
    public static StatusEffect baseEffect;
    public int effectDuration;
    public String name;

    private static AbstractBlock.Settings FLOWERS = FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GRASS).breakInstantly().noCollision();
    //FabricBlockSettings.copyOf(FLOWERS)

    public static final Int2ObjectMap<Flower> FLOWER = new Int2ObjectOpenHashMap<>();
    public static final Flower ACAI_VINE = new Flower("acai_vine", StatusEffects.REGENERATION, 0, 7, 16);
    public static final Flower ANEMONE = new Flower("anemone", StatusEffects.REGENERATION, 0, 9);
    public static final Flower BROMELIAD = new Flower("bromeliad", StatusEffects.REGENERATION, 0, 9);
    public static final Flower CANNA = new Flower("canna", StatusEffects.REGENERATION, 0);
    public static final Flower COMMELINA_DIFFUSA = new Flower("commelina_diffusa", StatusEffects.REGENERATION, 0);
    public static final Flower CROCOSMIA = new Flower("crocosmia", StatusEffects.REGENERATION, 0);
    public static final Flower CROTON = new Flower("croton", StatusEffects.REGENERATION, 0, 13);
    public static final Flower DRACAENA = new Flower("dracaena", StatusEffects.REGENERATION, 0, 13);
    public static final Flower TROPICAL_FERN = new Flower("tropical_fern", StatusEffects.REGENERATION, 0, 13);
    public static final Flower FOLIAGE = new Flower("foliage", StatusEffects.REGENERATION, 0, 13);
    public static final Flower MAGIC_MUSHROOM = new Flower("magic_mushroom", StatusEffects.REGENERATION, 0, 11);
    public static final Flower ORANGE_ANTHURIUM = new Flower("orange_anthurium", StatusEffects.REGENERATION, 0, 11);
    public static final Flower ORCHID = new Flower("orchid", StatusEffects.REGENERATION, 0);
    public static final Flower PATHOS = new Flower("pathos", StatusEffects.REGENERATION, 0, 15, 12);
    public static final Flower RED_ANTHURIUM = new Flower("red_anthurium", StatusEffects.REGENERATION, 0, 11);


    Flower(String name, StatusEffect baseEffect, int effectDuration) {
        //super(suspiciousStewEffect, effectDuration, settings);
        this(name,baseEffect, effectDuration, 7);
    }

    Flower(String name, StatusEffect baseEffect, int effectDuration, int width) {
        this(name, baseEffect, effectDuration, width, 15);
    }

    Flower(String name, StatusEffect baseEffect, int effectDuration, int width, int height) {
        this.name = name;
        //super(baseEffect, effectDuration, settings);
        this.baseEffect = baseEffect;
        this.effectDuration = effectDuration;
        float halfW = width / 2f;
        this.SHAPE = Block.createCuboidShape(8 - halfW, 0, 8 - halfW, 8 + halfW, height, 8 + halfW);
    }

    public StatusEffect getEffect() {
        return baseEffect;
    }

    public int getEffectDuration() {
        return effectDuration;
    }

    public VoxelShape getShape() {
        return SHAPE;
    }


    /*
    public String getId() {
        //return name;
        return TropicBlocks.FLOWERS.get(this).getTranslationKey();
    }

    public Block get() {
        return TropicBlocks.FLOWERS.get(this);
    }
     */


    /*
    public String getEnglishName() {
        return name;
    }
     */

}
