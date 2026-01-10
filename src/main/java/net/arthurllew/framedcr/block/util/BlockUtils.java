package net.arthurllew.framedcr.block.util;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

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
}
