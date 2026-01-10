package net.arthurllew.framedcr.block.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import org.jetbrains.annotations.Nullable;
import xfacthd.framedblocks.api.block.PlacementStateBuilder;

import java.util.function.Function;

public class CustomPlacementStateBuilder<T extends CustomPlacementStateBuilder<T>> extends PlacementStateBuilder<T> {
    /// See [PlacementStateBuilder].
    protected CustomPlacementStateBuilder(Block block, @Nullable BlockState state, BlockPlaceContext ctx) {
        super(block, state, ctx);
    }

    /// See [PlacementStateBuilder].
    public static CustomPlacementStateBuilder<?> of(Block block, BlockPlaceContext ctx) {
        return of(block, block.defaultBlockState(), ctx);
    }

    /// See [PlacementStateBuilder].
    public static CustomPlacementStateBuilder<?> of(Block block, @Nullable BlockState state, BlockPlaceContext ctx) {
        return new CustomPlacementStateBuilder<>(block, state, ctx);
    }

    /**
     * Checks if the clicked block is the same. If not, will replace active state with {@code null}.
     * {@code null} block state means "there is nothing to place".
     */
    public final T withShapeIsCycledCheck() {
        if (this.state != null) {
            BlockState prevState = ctx.getLevel().getBlockState(ctx.getClickedPos());
            this.state = prevState.is(this.block) ? null : this.state;
        }

        return this.self();
    }

    /**
     * Calculates arch-like block facing.
     */
    public final T withArchFacing() {
        if (this.state != null) {
            Direction facing = ctx.getClickedFace();
            if (facing == Direction.UP || facing == Direction.DOWN) {
                facing = this.getFacingFromUpDown(ctx);
            }

            this.state = this.state.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
        }

        return this.self();
    }

    /**
     * @return horizontal direction derived from clicked location.
     */
    private Direction getFacingFromUpDown(BlockPlaceContext context) {
        Direction horizontalFacing = context.getHorizontalDirection();
        BlockPos pos = ctx.getClickedPos();

        return switch (horizontalFacing) {
            case EAST -> !(context.getClickLocation().z - (double) pos.getZ() > 0.5D)
                    ? horizontalFacing.getClockWise() : horizontalFacing.getCounterClockWise();
            case SOUTH -> !(context.getClickLocation().x - (double) pos.getX() < 0.5D)
                    ? horizontalFacing.getClockWise() : horizontalFacing.getCounterClockWise();
            case WEST -> !(context.getClickLocation().z - (double) pos.getZ() < 0.5D)
                    ? horizontalFacing.getClockWise() : horizontalFacing.getCounterClockWise();
            case NORTH -> !(context.getClickLocation().x - (double) pos.getX() > 0.5D)
                    ? horizontalFacing.getClockWise() : horizontalFacing.getCounterClockWise();
            default -> throw new IllegalStateException();
        };
    }

    /**
     * Calculates horizontal axis.
     */
    public final T withHorizontalAxis() {
        if (this.state != null) {
            this.state = this.state
                    .setValue(BlockStateProperties.HORIZONTAL_AXIS, this.ctx.getHorizontalDirection().getAxis());
        }

        return this.self();
    }

    /**
     * Calculates top-bottom half.
     */
    public final T withTopBottom() {
        if (this.state != null) {
            Direction facing = ctx.getClickedFace();
            Half upDown = facing == Direction.DOWN
                    || facing != Direction.UP && ctx.getClickLocation().y
                    - (double)ctx.getClickedPos().getY() > 0.5D ? Half.TOP : Half.BOTTOM;

            this.state = this.state.setValue(BlockStateProperties.HALF, upDown);
        }

        return this.self();
    }

    /**
     * Calculates up and down connections.
     */
    public final T withUpDown(Function<BlockState, Boolean> canConnectTo) {
        if (this.state != null) {
            BlockGetter level = ctx.getLevel();
            BlockPos blockpos = ctx.getClickedPos();
            BlockPos up = blockpos.above();
            BlockPos down = blockpos.below();
            BlockState BlockStateUp = level.getBlockState(up);
            BlockState BlockStateDown = level.getBlockState(down);
            this.state = state
                    .setValue(BlockStateProperties.UP, canConnectTo.apply(BlockStateUp))
                    .setValue(BlockStateProperties.DOWN, canConnectTo.apply(BlockStateDown));
        }

        return this.self();
    }
}
