package net.tropicraftFabric.mixins;

import net.minecraft.structure.processor.StructureProcessor;
import net.tropicraftFabric.mixinExtensions.StructureProcessorExtension;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(StructureProcessor.class)
public class StructureProcessorMixin implements StructureProcessorExtension {

}
