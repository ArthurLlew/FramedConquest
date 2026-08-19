package net.arthurllew.framedcr.block.util;

import com.mojang.datafixers.util.Function3;
import net.arthurllew.framedcr.block.FramedStairs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.StairsShape;
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
     * Calculates new shape for blocks with shape cycle.
     */
    public final T withShapeIsCycledCheck() {
        // Avoid null state
        if (this.state != null) {
            // Get previous state
            BlockState prevState = this.ctx.getLevel().getBlockState(this.ctx.getClickedPos());
            // Return valid state if it differs from previous
            this.state = prevState.is(this.block) ? null : this.state;
        }

        return this.self();
    }

    /**
     * Calculates arch-like block direction.
     */
    public final T withArchDirection() {
        // Avoid null state
        if (this.state != null) {
            Direction dir = this.ctx.getClickedFace();
            // If player clicked up or down
            if (dir == Direction.UP || dir == Direction.DOWN) {
                // Get clicked position context
                Direction horizontalFacing = this.ctx.getHorizontalDirection();
                BlockPos pos = this.ctx.getClickedPos();
                // Decide dir
                dir = switch (horizontalFacing) {
                    case EAST -> !(this.ctx.getClickLocation().z - (double) pos.getZ() > 0.5D)
                            ? horizontalFacing.getClockWise() : horizontalFacing.getCounterClockWise();
                    case SOUTH -> !(this.ctx.getClickLocation().x - (double) pos.getX() < 0.5D)
                            ? horizontalFacing.getClockWise() : horizontalFacing.getCounterClockWise();
                    case WEST -> !(this.ctx.getClickLocation().z - (double) pos.getZ() < 0.5D)
                            ? horizontalFacing.getClockWise() : horizontalFacing.getCounterClockWise();
                    case NORTH -> !(this.ctx.getClickLocation().x - (double) pos.getX() > 0.5D)
                            ? horizontalFacing.getClockWise() : horizontalFacing.getCounterClockWise();
                    default -> throw new IllegalStateException();
                };
            }

            // Update state
            this.state = this.state.setValue(BlockStateProperties.HORIZONTAL_FACING, dir);
        }

        return this.self();
    }

    /**
     * Calculates quarter-like block direction.
     */
    public final T withQuarterDirection() {
        // Avoid null state
        if (this.state != null) {
            BlockPos blockpos = this.ctx.getClickedPos();
            Direction dir = this.ctx.getHorizontalDirection().getOpposite();
            dir = switch (dir) {
                case NORTH -> !(this.ctx.getClickLocation().x - (double)blockpos.getX() > 0.5D)
                        ? dir.getClockWise() : dir;
                case SOUTH -> !(this.ctx.getClickLocation().x - (double)blockpos.getX() < 0.5D)
                        ? dir.getClockWise() : dir;
                case EAST -> !(this.ctx.getClickLocation().z - (double)blockpos.getZ() > 0.5D)
                        ? dir.getClockWise() : dir;
                default -> !(this.ctx.getClickLocation().z - (double)blockpos.getZ() < 0.5D)
                        ? dir.getClockWise() : dir;
            };

            // Update state
            this.state = this.state.setValue(BlockStateProperties.HORIZONTAL_FACING, dir);
        }

        return this.self();
    }

    /**
     * Calculates block horizontal axis.
     */
    public final T withHorizontalAxis() {
        // Avoid null state
        if (this.state != null) {
            // Update state
            this.state = this.state
                    .setValue(BlockStateProperties.HORIZONTAL_AXIS, this.ctx.getHorizontalDirection().getAxis());
        }

        return this.self();
    }

    /**
     * Calculates block top-bottom half.
     */
    public final T withTopBottom() {
        // Avoid null state
        if (this.state != null) {
            Direction dir = this.ctx.getClickedFace();
            Half upDown = dir == Direction.DOWN
                    || dir != Direction.UP && this.ctx.getClickLocation().y
                    - (double)this.ctx.getClickedPos().getY() > 0.5D ? Half.TOP : Half.BOTTOM;

            this.state = this.state.setValue(BlockStateProperties.HALF, upDown);
        }

        return this.self();
    }

    /**
     * Calculates block up and down connections.
     */
    public final T withUpDown(Function<BlockState, Boolean> canConnectTo) {
        // Avoid null state
        if (this.state != null) {
            BlockGetter level = this.ctx.getLevel();
            BlockPos blockpos = this.ctx.getClickedPos();
            BlockState BlockStateUp = level.getBlockState(blockpos.above());
            BlockState BlockStateDown = level.getBlockState(blockpos.below());
            this.state = state
                    .setValue(BlockStateProperties.UP, canConnectTo.apply(BlockStateUp))
                    .setValue(BlockStateProperties.DOWN, canConnectTo.apply(BlockStateDown));
        }

        return this.self();
    }

    /**
     * Calculates capital horizontal connections.
     */
    public final T withCapitalConnection(Function3<BlockState, Direction, BlockState, Boolean> canConnectTo) {
        // Avoid null state
        if (this.state != null) {
            // Check horizontal connections
            BlockGetter level = this.ctx.getLevel();
            BlockPos blockpos = this.ctx.getClickedPos();
            BlockState BlockStateNorth = level.getBlockState(blockpos.north());
            BlockState BlockStateWest = level.getBlockState(blockpos.west());
            BlockState BlockStateSouth = level.getBlockState(blockpos.south());
            BlockState BlockStateEast = level.getBlockState(blockpos.east());
            this.state = state
                    .setValue(BlockStateProperties.NORTH, canConnectTo.apply(this.state, Direction.NORTH, BlockStateNorth))
                    .setValue(BlockStateProperties.WEST, canConnectTo.apply(this.state, Direction.WEST, BlockStateWest))
                    .setValue(BlockStateProperties.SOUTH, canConnectTo.apply(this.state, Direction.SOUTH, BlockStateSouth))
                    .setValue(BlockStateProperties.EAST, canConnectTo.apply(this.state, Direction.EAST, BlockStateEast));
        }

        return this.self();
    }

    /**
     * Calculates capital vertical slab horizontal connections.
     */
    public final T withCapitalVerticalSlabConnection(Function3<BlockState, Direction, BlockState, Boolean> canConnectTo) {
        // Avoid null state
        if (this.state != null) {
            // Current direction
            Direction dir = this.state.getValue(BlockStateProperties.HORIZONTAL_FACING);

            // Check connections behind and to the right
            BlockGetter level = this.ctx.getLevel();
            BlockPos blockpos = this.ctx.getClickedPos();
            BlockState BlockStateLeft = level.getBlockState(blockpos.relative(dir.getCounterClockWise()));
            BlockState BlockStateBehind = level.getBlockState(blockpos.relative(dir.getOpposite()));
            BlockState BlockStateRight = level.getBlockState(blockpos.relative(dir.getClockWise()));
            this.state = state
                    .setValue(BlockStateProperties.WEST, canConnectTo.apply(this.state, Direction.WEST, BlockStateLeft))
                    .setValue(BlockStateProperties.SOUTH, canConnectTo.apply(this.state, Direction.SOUTH, BlockStateBehind))
                    .setValue(BlockStateProperties.EAST, canConnectTo.apply(this.state, Direction.EAST, BlockStateRight));
        }

        return this.self();
    }

    /**
     * Calculates capital vertical quarter horizontal connections.
     */
    public final T withCapitalVerticalQuarterConnection(Function3<BlockState, Direction, BlockState, Boolean> canConnectTo) {
        // Avoid null state
        if (this.state != null) {
            // Current direction
            Direction dir = this.state.getValue(BlockStateProperties.HORIZONTAL_FACING);

            // Check connections behind and to the right
            BlockGetter level = this.ctx.getLevel();
            BlockPos blockpos = this.ctx.getClickedPos();
            BlockState BlockStateBehind = level.getBlockState(blockpos.relative(dir.getOpposite()));
            BlockState BlockStateRight = level.getBlockState(blockpos.relative(dir.getClockWise()));
            this.state = state
                    .setValue(BlockStateProperties.SOUTH, canConnectTo.apply(this.state, Direction.SOUTH, BlockStateBehind))
                    .setValue(BlockStateProperties.EAST, canConnectTo.apply(this.state, Direction.EAST, BlockStateRight));
        }

        return this.self();
    }

    /**
     * Calculates layered block shape.
     */
    public final T withLayerUpdate(IntegerProperty layerProperty, int maxLayers) {
        // Avoid null state
        if (this.state != null) {
            // Increment layer property
            int layers = this.state.getValue(layerProperty);
            this.state = this.state.setValue(layerProperty, Math.min(maxLayers, layers + 1));
        }

        return this.self();
    }

    /**
     * Calculates stairs block shape.
     */
    public final T withStairsShape() {
        // Avoid null state
        if (this.state != null) {
            this.state = this.state.setValue(BlockStateProperties.STAIRS_SHAPE,
                    getStairsShape(this.state, this.ctx.getLevel(), this.ctx.getClickedPos()));
        }

        return this.self();
    }

    /**
     * @return stairs shape.
     */
    public static StairsShape getStairsShape(BlockState state, BlockGetter level, BlockPos pos) {
        Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        BlockState blockstate = level.getBlockState(pos.relative(direction));
        if (isFramedStairs(blockstate)
                && state.getValue(BlockStateProperties.HALF) == blockstate.getValue(BlockStateProperties.HALF)) {
            Direction direction1 = blockstate.getValue(BlockStateProperties.HORIZONTAL_FACING);
            if (direction1.getAxis() != state.getValue(BlockStateProperties.HORIZONTAL_FACING)
                    .getAxis() && canStairsTakeShape(state, level, pos, direction1.getOpposite())) {
                if (direction1 == direction.getCounterClockWise()) {
                    return StairsShape.OUTER_LEFT;
                }

                return StairsShape.OUTER_RIGHT;
            }
        }

        blockstate = level.getBlockState(pos.relative(direction.getOpposite()));
        if (isFramedStairs(blockstate)
                && state.getValue(BlockStateProperties.HALF) == blockstate.getValue(BlockStateProperties.HALF)) {
            Direction direction2 = blockstate.getValue(BlockStateProperties.HORIZONTAL_FACING);
            if (direction2.getAxis() != state.getValue(BlockStateProperties.HORIZONTAL_FACING)
                    .getAxis() && canStairsTakeShape(state, level, pos, direction2)) {
                if (direction2 == direction.getCounterClockWise()) {
                    return StairsShape.INNER_LEFT;
                }

                return StairsShape.INNER_RIGHT;
            }
        }

        return StairsShape.STRAIGHT;
    }

    /**
     * @return whether stairs can take a particular shape.
     */
    private static boolean canStairsTakeShape(BlockState state, BlockGetter level, BlockPos pos, Direction face) {
        BlockState blockstate = level.getBlockState(pos.relative(face));
        return !isFramedStairs(blockstate)
                || blockstate.getValue(BlockStateProperties.HORIZONTAL_FACING)
                != state.getValue(BlockStateProperties.HORIZONTAL_FACING)
                || blockstate.getValue(BlockStateProperties.HALF)
                != state.getValue(BlockStateProperties.HALF);
    }

    /**
     * @return whether provided block is stairs.
     */
    private static boolean isFramedStairs(BlockState state) {
        return state.getBlock() instanceof FramedStairs;
    }
}
