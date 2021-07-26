package net.tropicraftFabric.common.block;

import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.function.Supplier;

public enum TropicFlowers implements Supplier<Block>{

    ACAI_VINE(StatusEffects.REGENERATION, 0, 7, 16),
    ANEMONE(StatusEffects.REGENERATION, 0, 9),
    BROMELIAD(StatusEffects.REGENERATION, 0, 9),
    CANNA(StatusEffects.REGENERATION, 0),
    COMMELINA_DIFFUSA(StatusEffects.REGENERATION, 0),
    CROCOSMIA(StatusEffects.REGENERATION, 0),
    CROTON(StatusEffects.REGENERATION, 0, 13),
    DRACAENA(StatusEffects.REGENERATION, 0, 13),
    TROPICAL_FERN(StatusEffects.REGENERATION, 0, 13),
    FOLIAGE(StatusEffects.REGENERATION, 0, 13),
    MAGIC_MUSHROOM(StatusEffects.REGENERATION, 0, 11),
    ORANGE_ANTHURIUM(StatusEffects.REGENERATION, 0, 11),
    ORCHID(StatusEffects.REGENERATION, 0),
    PATHOS(StatusEffects.REGENERATION, 0, 15, 12),
    RED_ANTHURIUM(StatusEffects.REGENERATION, 0, 11);

    public final String name;
    public final StatusEffect effect;
    public final int effectDuration;
    public final VoxelShape shape;

    TropicFlowers(StatusEffect effect, int effectDuration) {
        this(effect, effectDuration, 7);
    }

    TropicFlowers(StatusEffect effect, int effectDuration, int w) {
        this(effect, effectDuration, w, 15);
    }

    TropicFlowers(StatusEffect effect, int effectDuration, int w, int h) {
        this(null, effect, effectDuration, w, h);
    }

    TropicFlowers(@Nullable String name, StatusEffect effect, int effectDuration, int w, int h) {
        this.name = name == null ? getId() : name; //this.name = name == null ? Util.toEnglishName(name()) : name;
        this.effect = effect;
        this.effectDuration = effectDuration;
        float halfW = w / 2f;
        this.shape = Block.createCuboidShape(8 - halfW, 0, 8 - halfW, 8 + halfW, h, 8 + halfW);
    }

    public StatusEffect getEffect() {
        return effect;
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

    public Block get() {
        return Registry.BLOCK.get(Identifier.tryParse(this.name));
    }

    public String getEnglishName() {
        return name;
    }


}
