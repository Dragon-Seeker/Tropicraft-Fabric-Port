package net.tropicraftFabric.common.dimension.feature.jigsaw;

import com.google.common.base.Preconditions;
import net.minecraft.block.Blocks;
import net.minecraft.block.JigsawBlock;
import net.minecraft.structure.Structure;
import net.minecraft.structure.Structure.StructureBlockInfo;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.math.*;
import net.minecraft.util.math.Direction.Axis;
import net.tropicraftFabric.Constants;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

public abstract class PathStructureProcessor extends StructurePassProcessor {

    protected PathStructureProcessor() {}

    // Represents a section of the structure which is a path going in a certain direction
    private static class PathVector {
        final Direction dir;
        final BlockBox bb;

        PathVector(BlockPos start, Direction dir) {
            Preconditions.checkArgument(dir.getAxis().isHorizontal(), "Invalid direction for path vector at " + start);
            this.dir = dir;
            Vec3d ortho = Vec3d.of(dir.rotateYClockwise().getVector());
            bb = toMutable(new Box(start)
                    // Expand 16 blocks in front of the vector
                    .stretch(Vec3d.of(dir.getVector()).multiply(16))
                    // Add 1 block to each side
                    .stretch(ortho).stretch(ortho.negate())
                    // Cover a good amount of vertical space
                    .expand(0, 3, 0));
        }

        boolean contains(BlockPos pos, StructurePlacementData settings) {
            return bb.contains(Structure.transform(settings, pos));
        }

        private BlockBox toMutable(Box bb) {
            return new BlockBox((int) bb.minX, (int) bb.minY, (int) bb.minZ, (int) bb.maxX, (int) bb.maxY, (int) bb.maxZ);
        }
    }

    // Cache vectors for this structure to avoid redoing work
    private static final WeakHashMap<StructurePlacementData, List<PathVector>> VECTOR_CACHE = new WeakHashMap<>(); 

    protected @Nullable Axis getPathDirection(BlockPos seedPos, StructureBlockInfo current, StructurePlacementData settings, Structure template) {
        /*
         *  Use special marker jigsaw blocks to represent "vectors" of paths.
         *
         *  Each jigsaw with attachment type "tropicraft:path_center" is a different vector,
         *  with the facing representing the direction of the vector. A vector extends from
         *  the jigsaw block to the end of the structure in that direction, and 1 block to
         *  either side.
         */
        return VECTOR_CACHE.computeIfAbsent(settings, s ->
                template.getInfosForBlock(seedPos, settings, Blocks.JIGSAW).stream() // Find all jigsaw blocks
                        .filter(b -> b.tag.getString("attachement_type").equals(Constants.MODID + ":path_center")) // Filter for vector markers
                        .map(bi -> new PathVector(bi.pos.subtract(seedPos), JigsawBlock.getFacing(bi.state))) // Convert pos to structure local, extract facing
                        .collect(Collectors.toList()))
                .stream()
                .filter(pv -> pv.contains(current.pos, settings)) // Find vectors that contain this block
                .findFirst() // If there's more than one, we just choose the first, better some attempt than nothing
                .map(pv -> pv.dir.getAxis())
                .orElse(null);
    }
}
