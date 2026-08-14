package net.arthurllew.framedcr.block;

import net.arthurllew.framedcr.block.entity.FramedConquestDoubleBlockEntity;
import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.arthurllew.framedcr.block.util.CustomPlacementStateBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedCapital extends CustomFramedBlock {
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);
    private static final VoxelShape BOTTOM = Block.box(0, 0, 0, 16, 8, 16);
    private static final VoxelShape TOP = Block.box(0, 8, 0, 16, 16, 16);

    /**
     * Base constructor.
     */
    public FramedCapital(CustomBlockType blockType) {
        super(blockType);
    }

    /**
     * Constructor.
     */
    @SuppressWarnings("unused")
    public FramedCapital() {
        this(new CustomBlockType.Builder(FramedCapital::getShapeForState)
                .waterloggable(false)
                .build());
    }

    /**
     * @return block state that should be placed in the world depending on provided context.
     */
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return CustomPlacementStateBuilder.of(this, context).build();
    }

    /**
     * Generates shape for provided state.
     */
    public static VoxelShape getShapeForState(BlockState state) {
        return SHAPE;
    }

    /**
     * Capital that can horizontally connect to its neighbors.
     */
    public static class Connected extends FramedCapital {
        // Connection properties
        private static final BooleanProperty NORTH = BlockStateProperties.NORTH;
        private static final BooleanProperty WEST = BlockStateProperties.WEST;
        private static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
        private static final BooleanProperty EAST = BlockStateProperties.EAST;

        /**
         * Base constructor.
         */
        public Connected(CustomBlockType blockType) {
            super(blockType);
            this.registerDefaultState(this.defaultBlockState()
                    .setValue(NORTH, false)
                    .setValue(WEST, false)
                    .setValue(SOUTH, false)
                    .setValue(EAST, false));
        }

        /**
         * Constructor.
         */
        public Connected() {
            this(new CustomBlockType.Builder(FramedCapital::getShapeForState)
                    .waterloggable(false)
                    .build());
        }

        /**
         * Appends block state attributes.
         */
        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(NORTH, WEST, SOUTH, EAST);
        }

        /**
         * @return block state that should be placed in the world depending on provided context.
         */
        @Override
        public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
            return CustomPlacementStateBuilder.of(this, context)
                    .withCapitalHorizontalConnection(this::canConnectTo)
                    .build();
        }

        /**
         * @return new block state after the neighbor was updated.
         */
        @Override
        public BlockState updateShape(BlockState state, Direction dir, BlockState neighborState,
                                      LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
            // Base block state
            BlockState base = super.updateShape(state, dir, neighborState, level, pos, neighborPos);
            // On horizontal axis
            if ((dir.getAxis() == Direction.Axis.X) || (dir.getAxis() == Direction.Axis.Z)) {
                return switch (dir) {
                    case NORTH -> base.setValue(NORTH, this.canConnectTo(state, neighborState));
                    case WEST -> base.setValue(WEST, this.canConnectTo(state, neighborState));
                    case SOUTH -> base.setValue(SOUTH, this.canConnectTo(state, neighborState));
                    default -> base.setValue(EAST, this.canConnectTo(state, neighborState));
                };
            }
            // Return base state otherwise
            else {
                return base;
            }
        }

        /**
         * @return whether provided block state can connect to provided neighbor block state.
         */
        protected boolean canConnectTo(BlockState state, BlockState neighborState) {
            return neighborState.is(this);
        }
    }

    /**
     * Capital that can horizontally connect to its neighbors.
     */
    public static class ConnectedSlab extends Connected {
        /**
         * Top/bottom location property.
         */
        public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

        /**
         * Constructor.
         */
        public ConnectedSlab() {
            super(new CustomBlockType.Builder(ConnectedSlab::getShapeForState)
                    .modelVariantForItem("_lower")
                    .build());
            this.registerDefaultState(this.defaultBlockState()
                    .setValue(HALF, Half.TOP));
        }

        /**
         * Appends block state attributes.
         */
        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(HALF);
        }

        /**
         * @return block state that should be placed in the world depending on provided context.
         */
        @Override
        public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
            return CustomPlacementStateBuilder.of(this, context)
                    .withCapitalHorizontalConnection(this::canConnectTo)
                    .withTopBottom()
                    .build();
        }

        /**
         * @return whether this block can connect to provided block state.
         */
        @Override
        protected boolean canConnectTo(BlockState state, BlockState neighborState) {
            if (neighborState.is(this)) {
                return (state.getValue(HALF) == neighborState.getValue(HALF));
            }
            else {
                return false;
            }
        }

        /**
         * Generates shape for provided state.
         */
        public static VoxelShape getShapeForState(BlockState state) {
            return state.getValue(HALF) == Half.TOP ? TOP : BOTTOM;
        }
    }

    /**
     * Doric capital.
     */
    public static class Doric extends FramedCapital implements ICustomFramedDoubleBlock {
        // Block pair
        private final Block blockLeft, blockRight;

        /**
         * Constructor.
         */
        public Doric(Block blockLeft, Block blockRight) {
            super(new CustomBlockType.Builder(FramedCapital::getShapeForState)
                    .waterloggable(false)
                    .doubleBlock(true)
                    .build());
            this.blockLeft = blockLeft;
            this.blockRight = blockRight;
        }

        /**
         * @return connected block entity.
         */
        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new FramedConquestDoubleBlockEntity(pos, state);
        }

        /**
         * @return two blocks used to shape double block.
         */
        @Override
        public Tuple<BlockState, BlockState> calculateBlockPair(BlockState blockState) {
            // Copy block state properties
            BlockState blockStateLeft = this.blockLeft.defaultBlockState();
            BlockState blockStateRight = this.blockRight.defaultBlockState();
            for (Property<?> property : blockState.getProperties()) {
                blockStateLeft = applyProperty(blockStateLeft, blockState, property);
                blockStateRight = applyProperty(blockStateRight, blockState, property);
            }
            // Return states pair
            return new Tuple<>(blockStateLeft, blockStateRight);
        }
    }

    /**
     * Bottom doric capital part.
     */
    public static class DoricBottom extends FramedCapital {
        /**
         * Constructor.
         */
        public DoricBottom() {
            super(new CustomBlockType.Builder(DoricBottom::getShapeForState)
                    .waterloggable(false)
                    .blockItem(false)
                    .build());
        }

        /**
         * Generates shape for provided state.
         */
        public static VoxelShape getShapeForState(BlockState state) {
            return BOTTOM;
        }
    }

    /**
     * Top doric capital part.
     */
    public static class DoricTop extends FramedCapital {
        /**
         * Constructor.
         */
        public DoricTop() {
            super(new CustomBlockType.Builder(DoricTop::getShapeForState)
                    .waterloggable(false)
                    .blockItem(false)
                    .build());
        }

        /**
         * Generates shape for provided state.
         */
        public static VoxelShape getShapeForState(BlockState state) {
            return TOP;
        }
    }
}
