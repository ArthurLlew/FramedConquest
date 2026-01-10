package net.arthurllew.framedcr.block.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
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
            BlockState prevState = this.ctx.getLevel().getBlockState(this.ctx.getClickedPos());
            this.state = prevState.is(this.block) ? null : this.state;
        }

        return this.self();
    }

    /**
     * Calculates arch-like block facing.
     */
    public final T withArchFacing() {
        if (this.state != null) {
            Direction facing = this.ctx.getClickedFace();
            if (facing == Direction.UP || facing == Direction.DOWN) {
                facing = this.getArchFacing(this.ctx);
            }

            this.state = this.state.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
        }

        return this.self();
    }

    /**
     * @return arch direction derived from clicked location.
     */
    private Direction getArchFacing(BlockPlaceContext context) {
        Direction horizontalFacing = context.getHorizontalDirection();
        BlockPos pos = this.ctx.getClickedPos();

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
     * Calculates quarter-like block facing.
     */
    public final T withQuarterFacing() {
        if (this.state != null) {
            BlockPos blockpos = this.ctx.getClickedPos();

            this.state = this.state
                    .setValue(BlockStateProperties.HORIZONTAL_FACING,
                            getQuarterFacing(this.ctx.getHorizontalDirection().getOpposite(), blockpos, this.ctx));
        }

        return this.self();
    }

    /**
     * @return quarter direction derived from clicked location.
     */
    private static Direction getQuarterFacing(Direction facing, BlockPos pos, BlockPlaceContext context) {
        return switch (facing) {
            case NORTH -> !(context.getClickLocation().x - (double)pos.getX() > 0.5D)
                    ? facing.getClockWise() : facing;
            case SOUTH -> !(context.getClickLocation().x - (double)pos.getX() < 0.5D)
                    ? facing.getClockWise() : facing;
            case EAST -> !(context.getClickLocation().z - (double)pos.getZ() > 0.5D)
                    ? facing.getClockWise() : facing;
            default -> !(context.getClickLocation().z - (double)pos.getZ() < 0.5D)
                    ? facing.getClockWise() : facing;
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
            Direction facing = this.ctx.getClickedFace();
            Half upDown = facing == Direction.DOWN
                    || facing != Direction.UP && this.ctx.getClickLocation().y
                    - (double)this.ctx.getClickedPos().getY() > 0.5D ? Half.TOP : Half.BOTTOM;

            this.state = this.state.setValue(BlockStateProperties.HALF, upDown);
        }

        return this.self();
    }

    /**
     * Calculates up and down connections.
     */
    public final T withUpDown(Function<BlockState, Boolean> canConnectTo) {
        if (this.state != null) {
            BlockGetter level = this.ctx.getLevel();
            BlockPos blockpos = this.ctx.getClickedPos();
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

    /**
     * @return placement block state for block with layers.
     */
    public final T withLayerUpdate(IntegerProperty layerProperty, int maxLayers) {
        if (this.state != null) {
            int layers = this.state.getValue(layerProperty);
            this.state = this.state.setValue(layerProperty, Math.min(maxLayers, layers + 1));
        }

        return this.self();
    }
}
