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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedSmallWindow extends CustomFramedBlock {
    private static final VoxelShape TOPLEFT = Block.box(0.0F, 0.0F, 0.0F, 4.0F, 16.0F, 4.0F);
    private static final VoxelShape TOPRIGHT = Block.box(12.0F, 0.0F, 0.0F, 16.0F, 16.0F, 4.0F);
    private static final VoxelShape BOTTOMLEFT = Block.box(0.0F, 0.0F, 12.0F, 4.0F, 16.0F, 16.0F);
    private static final VoxelShape BOTTOMRIGHT = Block.box(12.0F, 0.0F, 12.0F, 16.0F, 16.0F, 16.0F);
    private static final VoxelShape TOP_SOUTH = Block.box(4.0F, 13.5F, 12.0F, 12.0F, 16.0F, 16.0F);
    private static final VoxelShape TOP_WEST = Block.box(0.0F, 13.5F, 4.0F, 4.0F, 16.0F, 12.0F);
    private static final VoxelShape TOP_NORTH = Block.box(4.0F, 13.5F, 0.0F, 12.0F, 16.0F, 4.0F);
    private static final VoxelShape TOP_EAST = Block.box(12.0F, 13.5F, 4.0F, 16.0F, 16.0F, 12.0F);
    private static final VoxelShape BOTTOM_SOUTH = Block.box(4.0F, 0.0F, 12.0F, 12.0F, 2.5F, 16.0F);
    private static final VoxelShape BOTTOM_WEST = Block.box(0.0F, 0.0F, 4.0F, 4.0F, 2.5F, 12.0F);
    private static final VoxelShape BOTTOM_NORTH = Block.box(4.0F, 0.0F, 0.0F, 12.0F, 2.5F, 4.0F);
    private static final VoxelShape BOTTOM_EAST = Block.box(12.0F, 0.0F, 4.0F, 16.0F, 2.5F, 12.0F);
    private static final VoxelShape TOP_SHAPE = Shapes.or(TOPLEFT, TOPRIGHT);
    private static final VoxelShape BOTTOM_SHAPE = Shapes.or(BOTTOMLEFT, BOTTOMRIGHT);
    private static final VoxelShape SHAPE = Shapes.or(TOP_SHAPE, BOTTOM_SHAPE);
    private static final VoxelShape UP_NESW = Shapes.or(TOP_NORTH, Shapes.or(TOP_EAST, Shapes.or(TOP_SOUTH, TOP_WEST)));
    private static final VoxelShape DOWN_NESW = Shapes.or(BOTTOM_NORTH, Shapes.or(BOTTOM_EAST, Shapes.or(BOTTOM_SOUTH, BOTTOM_WEST)));
    private static final VoxelShape UP_SHAPE = Shapes.or(SHAPE, UP_NESW);
    private static final VoxelShape DOWN_SHAPE = Shapes.or(SHAPE, DOWN_NESW);
    private static final VoxelShape UPDOWN_SHAPE = Shapes.or(SHAPE, Shapes.or(UP_NESW, DOWN_NESW));

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
    public FramedSmallWindow() {
        super(new CustomBlockType.Builder(FramedSmallWindow::getShapeForState).build());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(UP, false)
                .setValue(DOWN, false));
    }

    /**
     * Appends block state attributes.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(UP, DOWN);
    }

    /**
     * @return block state that should be placed in the world depending on provided context.
     */
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return CustomPlacementStateBuilder.of(this, context)
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
        return block != Blocks.BARRIER && (block == this);// || block instanceof Arch);
    }

    /**
     * Generates shape for provided state.
     */
    public static VoxelShape getShapeForState(BlockState state) {
        if (state.getValue(DOWN) && state.getValue(UP)) {
            return SHAPE;
        } else if (!(Boolean)state.getValue(DOWN) && state.getValue(UP)) {
            return DOWN_SHAPE;
        } else {
            return state.getValue(DOWN) && !(Boolean)state.getValue(UP) ? UP_SHAPE : UPDOWN_SHAPE;
        }
    }
}
