package com.dragonseeker.tropicfabricport.block;

import com.dragonseeker.tropicfabricport.registry.TropicBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Util;
import net.minecraft.util.shape.VoxelShape;

import java.util.Locale;
import java.util.function.Supplier;

public enum TropicFlowerTest implements Supplier<Block> {
    ;

    @Override
    public Block get() {
        return null;
    }
    /*
    ACAI_VINE(StatusEffects.REGENERATION.delegate, 0, 7, 16),
    ANEMONE(StatusEffects.REGENERATION.delegate, 0, 9),
    BROMELIAD(StatusEffects.REGENERATION.delegate, 0, 9),
    CANNA(StatusEffects.REGENERATION.delegate, 0),
    COMMELINA_DIFFUSA(StatusEffects.REGENERATION.delegate, 0),
    CROCOSMIA(StatusEffects.REGENERATION.delegate, 0),
    CROTON(StatusEffects.REGENERATION.delegate, 0, 13),
    DRACAENA(StatusEffects.REGENERATION.delegate, 0, 13),
    TROPICAL_FERN(StatusEffects.REGENERATION.delegate, 0, 13),
    FOLIAGE(StatusEffects.REGENERATION.delegate, 0, 13),
    MAGIC_MUSHROOM(StatusEffects.REGENERATION.delegate, 0, 11),
    ORANGE_ANTHURIUM(StatusEffects.REGENERATION.delegate, 0, 11),
    ORCHID(StatusEffects.REGENERATION.delegate, 0),
    PATHOS(StatusEffects.REGENERATION.delegate, 0, 15, 12),
    RED_ANTHURIUM(StatusEffects.REGENERATION.delegate, 0, 11);

    public final String name;
    public final Supplier<StatusEffects> effect;
    public final int effectDuration;
    public final VoxelShape shape;

    TropicFlowerTest(Supplier<StatusEffects> effect, int effectDuration) {
        this(effect, effectDuration, 7);
    }

    TropicFlowerTest(Supplier<StatusEffects> effect, int effectDuration, int w) {
        this(effect, effectDuration, w, 15);
    }

    public TropicFlowerTest(Supplier<StatusEffects> effect, int effectDuration, int w, int h) {
        this(null, effect, effectDuration, w, h);
    }

    TropicFlowerTest(@Nullable String name, Supplier<StatusEffects> effect, int effectDuration, int w, int h) {
        this.name = name == null ? getId() : name; //this.name = name == null ? Util.toEnglishName(name()) : name;
        this.effect = effect;
        this.effectDuration = effectDuration;
        float halfW = w / 2f;
        this.shape = Block.createCuboidShape(8 - halfW, 0, 8 - halfW, 8 + halfW, h, 8 + halfW);
    }

    public StatusEffects getEffect() {
        return effect.get();
    }

    public int getEffectDuration() {
        return effectDuration;
    }

    public VoxelShape getShape() {
        return shape;
    }

    public String getId() {
        return name().toLowerCase(Locale.ROOT);
    }

    @Override
    public Block get() {
        return TropicBlocks.FLOWERS.get(this).get();
    }

    public String getEnglishName() {
        return name;
    }

     */
}
