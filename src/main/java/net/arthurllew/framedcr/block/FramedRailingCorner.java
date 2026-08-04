package net.arthurllew.framedcr.block;

import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.arthurllew.framedcr.block.util.CustomPlacementStateBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedRailingCorner extends FramedRailing {
    /**
     * Constructor.
     */
    public FramedRailingCorner() {
        super(new CustomBlockType.Builder(FramedRailingCorner::getShapeForState).build());
    }

    /**
     * @return whether a block can be replaced by the other one.
     */
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return CustomPlacementStateBuilder.of(this, context)
                .withShapeIsCycledCheck()
                .withQuarterDirection()
                .withWater()
                .build();
    }

    /**
     * Generates shape for provided state.
     */
    public static VoxelShape getShapeForState(BlockState state) {
        return switch (state.getValue(FACING)) {
            case NORTH -> Shapes.or(NORTH_SHAPE, WEST_SHAPE);
            case WEST -> Shapes.or(WEST_SHAPE, SOUTH_SHAPE);
            case SOUTH -> Shapes.or(SOUTH_SHAPE, EAST_SHAPE);
            case EAST -> Shapes.or(EAST_SHAPE, NORTH_SHAPE);
            default -> throw new IllegalStateException();
        };
    }
}
