package net.arthurllew.framedcr.block;

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
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.block.cube.FramedLayeredCubeBlock;
import xfacthd.framedblocks.common.item.FramedSpecialBlockItem;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedQuarterVertical extends CustomFramedBlock {
    protected static final VoxelShape[] NORTH_SHAPE = new VoxelShape[]{Block.box(14.0F, 0.0F, 14.0F, 16.0F, 16.0F, 16.0F), Block.box(12.0F, 0.0F, 12.0F, 16.0F, 16.0F, 16.0F), Block.box(8.0F, 0.0F, 8.0F, 16.0F, 16.0F, 16.0F), Block.box(4.0F, 0.0F, 4.0F, 16.0F, 16.0F, 16.0F)};
    protected static final VoxelShape[] SOUTH_SHAPE = new VoxelShape[]{Block.box(0.0F, 0.0F, 0.0F, 2.0F, 16.0F, 2.0F), Block.box(0.0F, 0.0F, 0.0F, 4.0F, 16.0F, 4.0F), Block.box(0.0F, 0.0F, 0.0F, 8.0F, 16.0F, 8.0F), Block.box(0.0F, 0.0F, 0.0F, 12.0F, 16.0F, 12.0F)};
    protected static final VoxelShape[] EAST_SHAPE = new VoxelShape[]{Block.box(0.0F, 0.0F, 14.0F, 2.0F, 16.0F, 16.0F), Block.box(0.0F, 0.0F, 12.0F, 4.0F, 16.0F, 16.0F), Block.box(0.0F, 0.0F, 8.0F, 8.0F, 16.0F, 16.0F), Block.box(0.0F, 0.0F, 4.0F, 12.0F, 16.0F, 16.0F)};
    protected static final VoxelShape[] WEST_SHAPE = new VoxelShape[]{Block.box(14.0F, 0.0F, 0.0F, 16.0F, 16.0F, 2.0F), Block.box(12.0F, 0.0F, 0.0F, 16.0F, 16.0F, 4.0F), Block.box(8.0F, 0.0F, 0.0F, 16.0F, 16.0F, 8.0F), Block.box(4.0F, 0.0F, 0.0F, 16.0F, 16.0F, 12.0F)};

    /**
     * Layers count.
     */
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
    public FramedQuarterVertical() {
        super(new CustomBlockType.Builder(FramedQuarterVertical::getShapeForState)
                .modelVariantForItem("_1")
                .craftingCount(MAX_LAYERS)
                .isLayered(true)
                .build());
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(LAYERS, 1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getLootCount(BlockState state) {
        return state.getValue(LAYERS) - 1;
    }

    /**
     * Appends block state attributes.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, LAYERS);
    }

    /**
     * @return whether a block can be replaced by the other one.
     */
    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return BlockUtils.canLayeredBlockBeReplaced(LAYERS, MAX_LAYERS, this, state, context,
                () -> {
                    Direction clickedFacing = context.getClickedFace();
                    Direction dir = state.getValue(FACING);
                    return clickedFacing == dir || clickedFacing == dir.getCounterClockWise();
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
                return FramedQuarterVertical.this.getStateForPlacement(ctx);
            }
        };
    }

    /**
     * Generates shape for provided state.
     */
    public static VoxelShape getShapeForState(BlockState state) {
        return switch (state.getValue(FACING)) {
            case NORTH -> NORTH_SHAPE[state.getValue(LAYERS) - 1];
            case WEST -> WEST_SHAPE[state.getValue(LAYERS) - 1];
            case SOUTH -> SOUTH_SHAPE[state.getValue(LAYERS) - 1];
            case EAST -> EAST_SHAPE[state.getValue(LAYERS) - 1];
            default -> throw new IllegalStateException();
        };
    }
}
