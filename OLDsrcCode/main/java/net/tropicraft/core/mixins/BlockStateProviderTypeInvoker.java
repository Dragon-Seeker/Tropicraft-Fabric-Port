package net.tropicraft.core.mixins;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockStateProviderType.class)
public interface BlockStateProviderTypeInvoker {
    @Invoker("register")
    public static <P extends BlockStateProvider> BlockStateProviderType<P> invokeRegister(String id, Codec<P> codec){
        throw new AssertionError();
    }

}
