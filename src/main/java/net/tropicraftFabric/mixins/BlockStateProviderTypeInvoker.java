package net.tropicraftFabric.mixins;

import com.mojang.serialization.Codec;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;

@Mixin(BlockStateProviderType.class)
public interface BlockStateProviderTypeInvoker {
    @Invoker("register")
    public static <P extends BlockStateProvider> BlockStateProviderType<P> invokeRegister(String id, Codec<P> codec){
        throw new AssertionError();
    }

}
