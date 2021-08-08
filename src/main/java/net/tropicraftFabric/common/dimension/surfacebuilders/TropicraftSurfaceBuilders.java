package net.tropicraftFabric.common.dimension.surfacebuilders;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.tropicraftFabric.Constants;

public class TropicraftSurfaceBuilders {
    //public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, Constants.MODID);

    public static final TropicsSurfaceBuilder TROPICS = register("tropics", new TropicsSurfaceBuilder(TropicsSurfaceBuilder.Config.CODEC));
    public static final UnderwaterSurfaceBuilder UNDERWATER = register("underwater", new UnderwaterSurfaceBuilder(UnderwaterSurfaceBuilder.Config.CODEC));

    private static <T extends SurfaceBuilder<?>> T register(final String name, final T sup) {
        return Registry.register(Registry.SURFACE_BUILDER, new Identifier(Constants.MODID, name), sup);
        //return Registry.register(Registry.SURFACE_BUILDER, )
        //return SURFACE_BUILDERS.register(name, sup);
    }

    public static void init(){

    }
}
