package net.tropicraftFabric.mixins;

import com.google.common.collect.Sets;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(GoalSelector.class)
public interface SeaTurtleAccessor {
    @Accessor
    Set<PrioritizedGoal> getGoals();
}