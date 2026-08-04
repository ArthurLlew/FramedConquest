package net.arthurllew.framedcr.block;

import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.arthurllew.framedcr.block.util.CustomPlacementStateBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.common.block.ExtPlacementStateBuilder;
import xfacthd.framedblocks.common.block.stairs.standard.FramedHalfStairsBlock;
import xfacthd.framedblocks.common.data.PropertyHolder;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedRailingSlope extends FramedRailing {
    /**
     * Constructor.
     */
    public FramedRailingSlope() {
        super(new CustomBlockType.Builder(FramedRailing::getShapeForState)
                .modelVariantForItem("_r")
                .build());
        this.registerDefaultState(this.defaultBlockState()
                .setValue(PropertyHolder.RIGHT, false));
    }

    /**
     * Appends block state attributes.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PropertyHolder.RIGHT);
    }

    /**
     * @return block state that should be placed in the world depending on provided context.
     */
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return CustomPlacementStateBuilder.of(this,
                        ExtPlacementStateBuilder.of(this, context).withRight().build(),
                        context)
                .withShapeIsCycledCheck()
                .withHorizontalFacing(true)
                .withWater()
                .build();
    }

    /// See [FramedHalfStairsBlock].
    @Override
    public BlockState rotate(BlockState state, Direction face, Rotation rot) {
        Direction dir = state.getValue(FACING);
        if (Utils.isY(face)) {
            return state.setValue(FACING, rot.rotate(dir));
        } else if (rot == Rotation.NONE) {
            return state;
        } else {
            return face.getAxis() == dir.getAxis()
                    ? state.cycle(PropertyHolder.RIGHT) : state.cycle(FramedProperties.TOP);
        }
    }

    /// See [FramedHalfStairsBlock].
    @Override
    protected BlockState rotate(BlockState state, Rotation rot) {
        return this.rotate(state, Direction.UP, rot);
    }

    /// See [FramedHalfStairsBlock].
    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        if (mirror == Mirror.NONE) {
            return state;
        } else {
            Direction dir = state.getValue(FACING);
            if (mirror == Mirror.FRONT_BACK && Utils.isX(dir) || mirror == Mirror.LEFT_RIGHT && Utils.isZ(dir)) {
                state = state.setValue(FACING, dir.getOpposite());
            }

            return state.cycle(PropertyHolder.RIGHT);
        }
    }
}
