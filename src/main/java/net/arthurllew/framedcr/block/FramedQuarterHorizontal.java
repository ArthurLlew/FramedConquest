package net.arthurllew.framedcr.block;

import com.google.common.collect.ImmutableList;
import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.arthurllew.framedcr.block.util.BlockUtils;
import net.arthurllew.framedcr.block.util.CustomPlacementStateBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.shapes.ShapeProvider;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.block.cube.FramedLayeredCubeBlock;
import xfacthd.framedblocks.common.item.FramedSpecialBlockItem;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedQuarterHorizontal extends CustomFramedBlock {
    private static final VoxelShape[] BOTTOM_SOUTH_SHAPE = new VoxelShape[]{Block.box(0.0F, 0.0F, 0.0F, 16.0F, 4.0F, 4.0F), Block.box(0.0F, 0.0F, 0.0F, 16.0F, 8.0F, 8.0F), Block.box(0.0F, 0.0F, 0.0F, 16.0F, 12.0F, 12.0F)};
    private static final VoxelShape[] BOTTOM_NORTH_SHAPE = new VoxelShape[]{Block.box(0.0F, 0.0F, 12.0F, 16.0F, 4.0F, 16.0F), Block.box(0.0F, 0.0F, 8.0F, 16.0F, 8.0F, 16.0F), Block.box(0.0F, 0.0F, 4.0F, 16.0F, 12.0F, 16.0F)};
    private static final VoxelShape[] BOTTOM_WEST_SHAPE = new VoxelShape[]{Block.box(12.0F, 0.0F, 0.0F, 16.0F, 4.0F, 16.0F), Block.box(8.0F, 0.0F, 0.0F, 16.0F, 8.0F, 16.0F), Block.box(4.0F, 0.0F, 0.0F, 16.0F, 12.0F, 16.0F)};
    private static final VoxelShape[] BOTTOM_EAST_SHAPE = new VoxelShape[]{Block.box(0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 16.0F), Block.box(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 16.0F), Block.box(0.0F, 0.0F, 0.0F, 12.0F, 12.0F, 16.0F)};
    private static final VoxelShape[] TOP_SOUTH_SHAPE = new VoxelShape[]{Block.box(0.0F, 12.0F, 0.0F, 16.0F, 16.0F, 4.0F), Block.box(0.0F, 8.0F, 0.0F, 16.0F, 16.0F, 8.0F), Block.box(0.0F, 4.0F, 0.0F, 16.0F, 16.0F, 12.0F)};
    private static final VoxelShape[] TOP_NORTH_SHAPE = new VoxelShape[]{Block.box(0.0F, 12.0F, 12.0F, 16.0F, 16.0F, 16.0F), Block.box(0.0F, 8.0F, 8.0F, 16.0F, 16.0F, 16.0F), Block.box(0.0F, 4.0F, 4.0F, 16.0F, 16.0F, 16.0F)};
    private static final VoxelShape[] TOP_WEST_SHAPE = new VoxelShape[]{Block.box(12.0F, 12.0F, 0.0F, 16.0F, 16.0F, 16.0F), Block.box(8.0F, 8.0F, 0.0F, 16.0F, 16.0F, 16.0F), Block.box(4.0F, 4.0F, 0.0F, 16.0F, 16.0F, 16.0F)};
    private static final VoxelShape[] TOP_EAST_SHAPE = new VoxelShape[]{Block.box(0.0F, 12.0F, 0.0F, 4.0F, 16.0F, 16.0F), Block.box(0.0F, 8.0F, 0.0F, 8.0F, 16.0F, 16.0F), Block.box(0.0F, 4.0F, 0.0F, 12.0F, 16.0F, 16.0F)};

    private static final int MAX_LAYERS = 3;

    /**
     * Horizontal direction property.
     */
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    /**
     * Top/bottom location property.
     */
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    /**
     * Layers property.
     */
    public static final IntegerProperty LAYERS = IntegerProperty.create("layer", 1, MAX_LAYERS);

    /**
     * Constructor.
     */
    public FramedQuarterHorizontal() {
        super(CustomBlockType.FRAMED_QUARTER_HORIZONTAL);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, Half.TOP)
                .setValue(LAYERS, 1));
    }

    /**
     * Appends block state attributes.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, HALF, LAYERS, FramedProperties.SOLID, BlockStateProperties.WATERLOGGED);
    }

    /**
     * @return whether a block can be replaced by the other one.
     */
    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return BlockUtils.canLayeredBlockBeReplaced(LAYERS, MAX_LAYERS, this, state, context,
                () -> {
                    Direction clickedFacing = context.getClickedFace();
                    Direction facing = state.getValue(FACING);
                    return clickedFacing == facing || clickedFacing == facing.getCounterClockWise();
                });
    }

    /**
     * @return block state that should be placed in the world depending on provided context.
     */
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return BlockUtils.getLayeredBlockStateForPlacement(LAYERS, MAX_LAYERS, this, context,
                () -> CustomPlacementStateBuilder.of(this, context)
                        .withHorizontalFacing(true)
                        .withTopBottom()
                        .withWater()
                        .build());
    }

    /**
     * @return rotated block state.
     */
    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    /**
     * @return mirrored block state.
     */
    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
    }

    /**
     * Block item is created inside class to add extra functionality
     * (see {@link FramedLayeredCubeBlock} and {@link FBContent}).
     * If not done in this manner, the block will lose camo and
     * ultimately break in behavior, when changing state on
     * block item usage.
     */
    @Override
    public BlockItem createBlockItem() {
        return new FramedSpecialBlockItem.Single(this, new Item.Properties()) {
            protected @Nullable BlockState getReplacementState(BlockPlaceContext ctx, BlockState originalState) {
                return FramedQuarterHorizontal.this.getStateForPlacement(ctx);
            }
        };
    }

    /**
     * Produces pairs (block state, shape).
     */
    public static ShapeProvider generateShapes(ImmutableList<BlockState> states) {
        return generateShapes(states, (state) ->
                switch (state.getValue(HALF)) {
                    case TOP -> switch (state.getValue(FACING)) {
                        case NORTH -> TOP_NORTH_SHAPE[state.getValue(LAYERS) - 1];
                        case WEST -> TOP_WEST_SHAPE[state.getValue(LAYERS) - 1];
                        case SOUTH -> TOP_SOUTH_SHAPE[state.getValue(LAYERS) - 1];
                        case EAST -> TOP_EAST_SHAPE[state.getValue(LAYERS) - 1];
                        default -> throw new IllegalStateException();
                    };
                    case BOTTOM -> switch (state.getValue(FACING)) {
                        case NORTH -> BOTTOM_NORTH_SHAPE[state.getValue(LAYERS) - 1];
                        case WEST -> BOTTOM_WEST_SHAPE[state.getValue(LAYERS) - 1];
                        case SOUTH -> BOTTOM_SOUTH_SHAPE[state.getValue(LAYERS) - 1];
                        case EAST -> BOTTOM_EAST_SHAPE[state.getValue(LAYERS) - 1];
                        default -> throw new IllegalStateException();
                    };
                });
    }
}
