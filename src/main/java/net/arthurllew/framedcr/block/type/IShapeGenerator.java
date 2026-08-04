package net.arthurllew.framedcr.block.type;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.api.shapes.ShapeGenerator;
import xfacthd.framedblocks.api.shapes.ShapeProvider;

@FunctionalInterface
public interface IShapeGenerator extends ShapeGenerator {
    /**
     * Constructs provider for producing pairs (block state, shape).
     */
    @Override
    default ShapeProvider generate(ImmutableList<BlockState> states) {
        // Get builder
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        // For each state get appropriate shape
        BlockState state;
        for(UnmodifiableIterator<BlockState> iterator = states.iterator();
            iterator.hasNext();
            builder.put(state, getShapeForState(state))) {
            state = iterator.next();
        }

        // Build
        return ShapeProvider.of(builder.build());
    }

    /**
     * Generates shape for provided state.
     */
    VoxelShape getShapeForState(BlockState state);
}
