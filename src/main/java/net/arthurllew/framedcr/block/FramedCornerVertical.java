package net.arthurllew.framedcr.block;

import com.google.common.collect.ImmutableList;
import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.arthurllew.framedcr.block.util.BlockUtils;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.Shapes;
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
public class FramedCornerVertical extends CustomFramedBlock {
    private static final VoxelShape[] VERTICAL_SLAB_EAST_SHAPE = new VoxelShape[]{Block.box(0.0F, 0.0F, 0.0F, 2.0F, 16.0F, 16.0F), Block.box(0.0F, 0.0F, 0.0F, 4.0F, 16.0F, 16.0F), Block.box(0.0F, 0.0F, 0.0F, 8.0F, 16.0F, 16.0F), Block.box(0.0F, 0.0F, 0.0F, 12.0F, 16.0F, 16.0F)};
    private static final VoxelShape[] VERTICAL_SLAB_WEST_SHAPE = new VoxelShape[]{Block.box(14.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F), Block.box(12.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F), Block.box(8.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F), Block.box(4.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F)};
    private static final VoxelShape[] VERTICAL_SLAB_SOUTH_SHAPE = new VoxelShape[]{Block.box(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 2.0F), Block.box(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 4.0F), Block.box(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 8.0F), Block.box(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 12.0F)};
    private static final VoxelShape[] VERTICAL_SLAB_NORTH_SHAPE = new VoxelShape[]{Block.box(0.0F, 0.0F, 14.0F, 16.0F, 16.0F, 16.0F), Block.box(0.0F, 0.0F, 12.0F, 16.0F, 16.0F, 16.0F), Block.box(0.0F, 0.0F, 8.0F, 16.0F, 16.0F, 16.0F), Block.box(0.0F, 0.0F, 4.0F, 16.0F, 16.0F, 16.0F)};
    private static final VoxelShape[] EAST_SHAPE = new VoxelShape[]{Shapes.or(VERTICAL_SLAB_EAST_SHAPE[0], VERTICAL_SLAB_NORTH_SHAPE[0]), Shapes.or(VERTICAL_SLAB_EAST_SHAPE[1], VERTICAL_SLAB_NORTH_SHAPE[1]), Shapes.or(VERTICAL_SLAB_EAST_SHAPE[2], VERTICAL_SLAB_NORTH_SHAPE[2]), Shapes.or(VERTICAL_SLAB_EAST_SHAPE[3], VERTICAL_SLAB_NORTH_SHAPE[3])};
    private static final VoxelShape[] WEST_SHAPE = new VoxelShape[]{Shapes.or(VERTICAL_SLAB_WEST_SHAPE[0], VERTICAL_SLAB_SOUTH_SHAPE[0]), Shapes.or(VERTICAL_SLAB_WEST_SHAPE[1], VERTICAL_SLAB_SOUTH_SHAPE[1]), Shapes.or(VERTICAL_SLAB_WEST_SHAPE[2], VERTICAL_SLAB_SOUTH_SHAPE[2]), Shapes.or(VERTICAL_SLAB_WEST_SHAPE[3], VERTICAL_SLAB_SOUTH_SHAPE[3])};
    private static final VoxelShape[] NORTH_SHAPE = new VoxelShape[]{Shapes.or(VERTICAL_SLAB_NORTH_SHAPE[0], VERTICAL_SLAB_WEST_SHAPE[0]), Shapes.or(VERTICAL_SLAB_NORTH_SHAPE[1], VERTICAL_SLAB_WEST_SHAPE[1]), Shapes.or(VERTICAL_SLAB_NORTH_SHAPE[2], VERTICAL_SLAB_WEST_SHAPE[2]), Shapes.or(VERTICAL_SLAB_NORTH_SHAPE[3], VERTICAL_SLAB_WEST_SHAPE[3])};
    private static final VoxelShape[] SOUTH_SHAPE = new VoxelShape[]{Shapes.or(VERTICAL_SLAB_SOUTH_SHAPE[0], VERTICAL_SLAB_EAST_SHAPE[0]), Shapes.or(VERTICAL_SLAB_SOUTH_SHAPE[1], VERTICAL_SLAB_EAST_SHAPE[1]), Shapes.or(VERTICAL_SLAB_SOUTH_SHAPE[2], VERTICAL_SLAB_EAST_SHAPE[2]), Shapes.or(VERTICAL_SLAB_SOUTH_SHAPE[3], VERTICAL_SLAB_EAST_SHAPE[3])};

    private static final int MAX_LAYERS = 4;

    /**
     * Horizontal direction property.
     */
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    /**
     * Layers property.
     */
    public static final IntegerProperty LAYERS = IntegerProperty.create("layer", 1, MAX_LAYERS);

    /**
     * Constructor.
     */
    public FramedCornerVertical() {
        super(CustomBlockType.FRAMED_CORNER_VERTICAL);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LAYERS, 1));
    }

    /**
     * Appends block state attributes.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, LAYERS, FramedProperties.SOLID, BlockStateProperties.WATERLOGGED);
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
        return BlockUtils.getLayeredQuarterBlockStateForPlacement(LAYERS, MAX_LAYERS, this, context);
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
                return FramedCornerVertical.this.getStateForPlacement(ctx);
            }
        };
    }

    /**
     * Produces pairs (block state, shape).
     */
    public static ShapeProvider generateShapes(ImmutableList<BlockState> states) {
        return generateShapes(states, (state) ->
                switch (state.getValue(FACING)) {
                    case NORTH -> NORTH_SHAPE[state.getValue(LAYERS) - 1];
                    case WEST -> WEST_SHAPE[state.getValue(LAYERS) - 1];
                    case SOUTH -> SOUTH_SHAPE[state.getValue(LAYERS) - 1];
                    case EAST -> EAST_SHAPE[state.getValue(LAYERS) - 1];
                    default -> throw new IllegalStateException();
                });
    }
}
