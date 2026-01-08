package net.arthurllew.framedcr.block;

import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.common.block.ExtPlacementStateBuilder;
import xfacthd.framedblocks.common.block.stairs.standard.FramedHalfStairsBlock;
import xfacthd.framedblocks.common.data.PropertyHolder;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedTwoMeterArchHalf extends CustomFramedBlock {
    /**
     * Constructor.
     */
    public FramedTwoMeterArchHalf() {
        super(CustomBlockType.FRAMED_TWO_METER_ARCH_HALF);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FramedProperties.TOP, false)
                .setValue(PropertyHolder.RIGHT, false));
    }

    /// See [FramedHalfStairsBlock].
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FramedProperties.FACING_HOR, FramedProperties.TOP, PropertyHolder.RIGHT,
                FramedProperties.SOLID, BlockStateProperties.WATERLOGGED);
    }

    /// See [FramedHalfStairsBlock].
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return ExtPlacementStateBuilder.of(this, ctx)
                .withTargetOrHorizontalFacing()
                .withTop()
                .withRight()
                .withWater()
                .build();
    }

    /// See [FramedHalfStairsBlock].
    public BlockState rotate(BlockState state, Direction face, Rotation rot) {
        Direction dir = state.getValue(FramedProperties.FACING_HOR);
        if (Utils.isY(face)) {
            return state.setValue(FramedProperties.FACING_HOR, rot.rotate(dir));
        } else if (rot == Rotation.NONE) {
            return state;
        } else {
            return face.getAxis() == dir.getAxis()
                    ? state.cycle(PropertyHolder.RIGHT) : state.cycle(FramedProperties.TOP);
        }
    }

    /// See [FramedHalfStairsBlock].
    protected BlockState rotate(BlockState state, Rotation rot) {
        return this.rotate(state, Direction.UP, rot);
    }

    /// See [FramedHalfStairsBlock].
    protected BlockState mirror(BlockState state, Mirror mirror) {
        if (mirror == Mirror.NONE) {
            return state;
        } else {
            Direction dir = state.getValue(FramedProperties.FACING_HOR);
            if (mirror == Mirror.FRONT_BACK && Utils.isX(dir) || mirror == Mirror.LEFT_RIGHT && Utils.isZ(dir)) {
                state = state.setValue(FramedProperties.FACING_HOR, dir.getOpposite());
            }

            return state.cycle(PropertyHolder.RIGHT);
        }
    }

    /// See [FramedHalfStairsBlock].
    public BlockState getItemModelSource() {
        return this.defaultBlockState()
                .setValue(FramedProperties.FACING_HOR, Direction.SOUTH)
                .setValue(PropertyHolder.RIGHT, true);
    }

    /// See [FramedHalfStairsBlock].
    public BlockState getJadeRenderState(BlockState state) {
        return this.defaultBlockState()
                .setValue(FramedProperties.FACING_HOR, Direction.SOUTH);
    }
}
