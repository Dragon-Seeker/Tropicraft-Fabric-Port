package net.tropicraft.core.common.block;

import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public enum TropicFlowers implements Supplier<Block>{

    ACAI_VINE(MobEffects.REGENERATION, 0, 7, 16),
    ANEMONE(MobEffects.REGENERATION, 0, 9),
    BROMELIAD(MobEffects.REGENERATION, 0, 9),
    CANNA(MobEffects.REGENERATION, 0),
    COMMELINA_DIFFUSA(MobEffects.REGENERATION, 0),
    CROCOSMIA(MobEffects.REGENERATION, 0),
    CROTON(MobEffects.REGENERATION, 0, 13),
    DRACAENA(MobEffects.REGENERATION, 0, 13),
    TROPICAL_FERN(MobEffects.REGENERATION, 0, 13),
    FOLIAGE(MobEffects.REGENERATION, 0, 13),
    MAGIC_MUSHROOM(MobEffects.REGENERATION, 0, 11),
    ORANGE_ANTHURIUM(MobEffects.REGENERATION, 0, 11),
    ORCHID(MobEffects.REGENERATION, 0),
    PATHOS(MobEffects.REGENERATION, 0, 15, 12),
    RED_ANTHURIUM(MobEffects.REGENERATION, 0, 11);

    public final String name;
    public final MobEffect effect;
    public final int effectDuration;
    public final VoxelShape shape;

    TropicFlowers(MobEffect effect, int effectDuration) {
        this(effect, effectDuration, 7);
    }

    TropicFlowers(MobEffect effect, int effectDuration, int w) {
        this(effect, effectDuration, w, 15);
    }

    TropicFlowers(MobEffect effect, int effectDuration, int w, int h) {
        this(null, effect, effectDuration, w, h);
    }

    TropicFlowers(@Nullable String name, MobEffect effect, int effectDuration, int w, int h) {
        this.name = name == null ? getId() : name; //this.name = name == null ? Util.toEnglishName(name()) : name;
        this.effect = effect;
        this.effectDuration = effectDuration;
        float halfW = w / 2f;
        this.shape = Block.box(8 - halfW, 0, 8 - halfW, 8 + halfW, h, 8 + halfW);
    }

    public MobEffect getEffect() {
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
        return Registry.BLOCK.get(ResourceLocation.tryParse(this.name));
    }

    public String getEnglishName() {
        return name;
    }


}
