package net.tropicraft.core.mixins;

import net.minecraft.structure.processor.StructureProcessor;
import net.tropicraft.core.mixinExtensions.StructureProcessorExtension;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(StructureProcessor.class)
public class StructureProcessorMixin implements StructureProcessorExtension {

}
