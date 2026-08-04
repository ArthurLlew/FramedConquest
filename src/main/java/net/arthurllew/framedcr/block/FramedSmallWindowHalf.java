package net.arthurllew.framedcr.block;

import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.arthurllew.framedcr.block.util.CustomPlacementStateBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedSmallWindowHalf extends CustomFramedBlock {
    private static final VoxelShape TOPLEFT = Block.box(0.0F, 0.0F, 0.0F, 4.0F, 16.0F, 4.0F);
    private static final VoxelShape TOPRIGHT = Block.box(12.0F, 0.0F, 0.0F, 16.0F, 16.0F, 4.0F);
    private static final VoxelShape BOTTOMLEFT = Block.box(0.0F, 0.0F, 12.0F, 4.0F, 16.0F, 16.0F);
    private static final VoxelShape BOTTOMRIGHT = Block.box(12.0F, 0.0F, 12.0F, 16.0F, 16.0F, 16.0F);
    private static final VoxelShape SOUTH_SHAPE = Shapes.or(TOPLEFT, TOPRIGHT);
    private static final VoxelShape WEST_SHAPE = Shapes.or(BOTTOMRIGHT, TOPRIGHT);
    private static final VoxelShape NORTH_SHAPE = Shapes.or(BOTTOMLEFT, BOTTOMRIGHT);
    private static final VoxelShape EAST_SHAPE = Shapes.or(BOTTOMLEFT, TOPLEFT);
    private static final VoxelShape TOP_NORTH = Block.box(4.0F, 13.5F, 12.0F, 12.0F, 16.0F, 16.0F);
    private static final VoxelShape TOP_EAST = Block.box(0.0F, 13.5F, 4.0F, 4.0F, 16.0F, 12.0F);
    private static final VoxelShape TOP_SOUTH = Block.box(4.0F, 13.5F, 0.0F, 12.0F, 16.0F, 4.0F);
    private static final VoxelShape TOP_WEST = Block.box(12.0F, 13.5F, 4.0F, 16.0F, 16.0F, 12.0F);
    private static final VoxelShape BOTTOM_NORTH = Block.box(4.0F, 0.0F, 12.0F, 12.0F, 2.5F, 16.0F);
    private static final VoxelShape BOTTOM_EAST = Block.box(0.0F, 0.0F, 4.0F, 4.0F, 2.5F, 12.0F);
    private static final VoxelShape BOTTOM_SOUTH = Block.box(4.0F, 0.0F, 0.0F, 12.0F, 2.5F, 4.0F);
    private static final VoxelShape BOTTOM_WEST = Block.box(12.0F, 0.0F, 4.0F, 16.0F, 2.5F, 12.0F);
    private static final VoxelShape UP_SOUTH_SHAPE = Shapes.or(SOUTH_SHAPE, TOP_SOUTH);
    private static final VoxelShape UP_WEST_SHAPE = Shapes.or(WEST_SHAPE, TOP_WEST);
    private static final VoxelShape UP_NORTH_SHAPE = Shapes.or(NORTH_SHAPE, TOP_NORTH);
    private static final VoxelShape UP_EAST_SHAPE = Shapes.or(EAST_SHAPE, TOP_EAST);
    private static final VoxelShape DOWN_SOUTH_SHAPE = Shapes.or(SOUTH_SHAPE, BOTTOM_SOUTH);
    private static final VoxelShape DOWN_WEST_SHAPE = Shapes.or(WEST_SHAPE, BOTTOM_WEST);
    private static final VoxelShape DOWN_NORTH_SHAPE = Shapes.or(NORTH_SHAPE, BOTTOM_NORTH);
    private static final VoxelShape DOWN_EAST_SHAPE = Shapes.or(EAST_SHAPE, BOTTOM_EAST);
    private static final VoxelShape UPDOWN_SOUTH_SHAPE = Shapes.or(SOUTH_SHAPE, Shapes.or(TOP_SOUTH, BOTTOM_SOUTH));
    private static final VoxelShape UPDOWN_WEST_SHAPE = Shapes.or(WEST_SHAPE, Shapes.or(TOP_WEST, BOTTOM_WEST));
    private static final VoxelShape UPDOWN_NORTH_SHAPE = Shapes.or(NORTH_SHAPE, Shapes.or(TOP_NORTH, BOTTOM_NORTH));
    private static final VoxelShape UPDOWN_EAST_SHAPE = Shapes.or(EAST_SHAPE, Shapes.or(TOP_EAST, BOTTOM_EAST));

    /**
     * Horizontal direction property.
     */
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    /**
     * Up connection property.
     */
    public static final BooleanProperty UP = BlockStateProperties.UP;
    /**
     * Down connection property.
     */
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;

    /**
     * Constructor.
     */
    public FramedSmallWindowHalf() {
        super(new CustomBlockType.Builder(FramedSmallWindowHalf::getShapeForState)
                .modelVariantForItem("_updown")
                .build());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(UP, false)
                .setValue(DOWN, false));
    }

    /**
     * Appends block state attributes.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(UP, DOWN, FACING);
    }

    /**
     * @return block state that should be placed in the world depending on provided context.
     */
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return CustomPlacementStateBuilder.of(this, context)
                .withHorizontalFacing(true)
                .withUpDown(this::canConnectTo)
                .withWater()
                .build();
    }

    /**
     * @return new block state after the neighbor was updated.
     */
    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return super.updateShape(state, dir, neighborState, level, pos, neighborPos)
                .setValue(UP, this.canConnectTo(level, pos.above()))
                .setValue(DOWN, this.canConnectTo(level, pos.below()));
    }

    /**
     * @return whether this block can connect to block state at provided position.
     */
    private boolean canConnectTo(LevelAccessor world, BlockPos pos) {
        return canConnectTo(world.getBlockState(pos));
    }

    /**
     * @return whether this block can connect to provided block state.
     */
    private boolean canConnectTo(BlockState blockstate) {
        Block block = blockstate.getBlock();
        return block != Blocks.BARRIER && (block == this);// || block instanceof Arch || block instanceof ArchHalf);
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
     * Generates shape for provided state.
     */
    public static VoxelShape getShapeForState(BlockState state) {
        if (state.getValue(DOWN) && state.getValue(UP)) {
            return switch (state.getValue(FACING)) {
                case NORTH -> NORTH_SHAPE;
                case WEST -> WEST_SHAPE;
                case SOUTH -> SOUTH_SHAPE;
                case EAST -> EAST_SHAPE;
                default -> throw new IllegalStateException();
            };
        } else if (!(Boolean)state.getValue(DOWN) && state.getValue(UP)) {
            return switch (state.getValue(FACING)) {
                case NORTH -> DOWN_NORTH_SHAPE;
                case WEST -> DOWN_WEST_SHAPE;
                case SOUTH -> DOWN_SOUTH_SHAPE;
                case EAST -> DOWN_EAST_SHAPE;
                default -> throw new IllegalStateException();
            };
        } else if (state.getValue(DOWN) && !(Boolean)state.getValue(UP)) {
            return switch (state.getValue(FACING)) {
                case NORTH -> UP_NORTH_SHAPE;
                case WEST -> UP_WEST_SHAPE;
                case SOUTH -> UP_SOUTH_SHAPE;
                case EAST -> UP_EAST_SHAPE;
                default -> throw new IllegalStateException();
            };
        } else {
            return switch (state.getValue(FACING)) {
                case NORTH -> UPDOWN_NORTH_SHAPE;
                case WEST -> UPDOWN_WEST_SHAPE;
                case SOUTH -> UPDOWN_SOUTH_SHAPE;
                case EAST -> UPDOWN_EAST_SHAPE;
                default -> throw new IllegalStateException();
            };
        }
    }
}
