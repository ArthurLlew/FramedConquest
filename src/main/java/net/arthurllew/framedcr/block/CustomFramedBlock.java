package net.arthurllew.framedcr.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import net.arthurllew.framedcr.block.entity.FramedConquestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.api.block.AbstractFramedBlock;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.shapes.ShapeProvider;
import xfacthd.framedblocks.api.type.IBlockType;
import xfacthd.framedblocks.common.block.FramedBlock;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Custom implementation of {@link FramedBlock}.
 */
public abstract class CustomFramedBlock extends AbstractFramedBlock {
    /**
     * Simplified constructor.
     */
    protected CustomFramedBlock(IBlockType blockType) {
        super(blockType, IFramedBlock.createProperties(blockType));
    }

    /**
     * Constructor.
     */
    protected CustomFramedBlock(IBlockType blockType, UnaryOperator<Properties> propertyModifier) {
        super(blockType, propertyModifier);
    }

    public BlockState getItemModelSource() {
        return this.defaultBlockState();
    }

    public BlockState getJadeRenderState(BlockState state) {
        return this.defaultBlockState();
    }

    /**
     * @return block entity to spawn.
     */
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FramedConquestBlockEntity(pos, state);
    }

    /**
     * Produces pairs (block state, shape).
     */
    protected static ShapeProvider generateShapes(ImmutableList<BlockState> states,
                                                  Function<BlockState, VoxelShape> getShapeFromState) {
        // Get builder
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        // For each state get appropriate shape
        BlockState state;
        for(UnmodifiableIterator<BlockState> iterator = states.iterator();
            iterator.hasNext();
            builder.put(state, getShapeFromState.apply(state))) {
                state = iterator.next();
        }

        // Build
        return ShapeProvider.of(builder.build());
    }
}
