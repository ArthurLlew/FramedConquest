package net.arthurllew.framedcr.block.util;

import net.arthurllew.framedcr.block.FramedStairs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.StairsShape;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class BlockUtils {
    /**
     * @return whether a block with layers can be replaced.
     */
    public static boolean canLayeredBlockBeReplaced(IntegerProperty layerProperty, int maxLayers,
                                                    Block block,
                                                    BlockState state, BlockPlaceContext context,
                                                    Supplier<Boolean> supplier) {
        if (state.getValue(layerProperty) < maxLayers && context.getItemInHand().is(block.asItem())) {
            if (context instanceof DirectionalPlaceContext || !context.replacingClickedOnBlock()) {
                return true;
            } else {
                return supplier.get();
            }
        } else {
            return false;
        }
    }

    /**
     * @return layered block state for placement.
     */
    public static @Nullable BlockState getLayeredBlockStateForPlacement(IntegerProperty layerProperty, int maxLayers,
                                                                        Block block, BlockPlaceContext context,
                                                                        Supplier<BlockState> supplier) {
        BlockState prevState = context.getLevel().getBlockState(context.getClickedPos());
        if (prevState.is(block)) {
            return CustomPlacementStateBuilder.of(block, prevState, context)
                    .withLayerUpdate(layerProperty, maxLayers)
                    .build();
        } else {
            return supplier.get();
        }
    }

    /**
     * @return layered quarter block state for placement.
     */
    public static @Nullable BlockState getLayeredQuarterBlockStateForPlacement(IntegerProperty layerProperty,
                                                                               int maxLayers,
                                                                               Block block, BlockPlaceContext context) {
        return getLayeredBlockStateForPlacement(layerProperty, maxLayers, block, context,
                () -> CustomPlacementStateBuilder.of(block, context)
                        .withQuarterFacing()
                        .withWater()
                        .build());
    }

    /**
     * @return rotated block with AXIS property.
     */
    public static BlockState rotateAxis(BlockState state, Rotation rotation, EnumProperty<Direction.Axis> axis) {
        return switch (rotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> switch (state.getValue(axis)) {
                case X -> state.setValue(axis, Direction.Axis.Z);
                case Z -> state.setValue(axis, Direction.Axis.X);
                default -> state;
            };
            default -> state;
        };
    }

    /**
     * @return stairs shape.
     */
    public static StairsShape getStairsShape(BlockState state, BlockGetter level, BlockPos pos) {
        Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        BlockState blockstate = level.getBlockState(pos.relative(direction));
        if (FramedStairs.isFramedStairs(blockstate)
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
        if (FramedStairs.isFramedStairs(blockstate)
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
        return !FramedStairs.isFramedStairs(blockstate)
                || blockstate.getValue(BlockStateProperties.HORIZONTAL_FACING)
                        != state.getValue(BlockStateProperties.HORIZONTAL_FACING)
                || blockstate.getValue(BlockStateProperties.HALF)
                        != state.getValue(BlockStateProperties.HALF);
    }
}
