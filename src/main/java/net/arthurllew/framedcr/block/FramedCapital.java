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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedCapital extends CustomFramedBlock {
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

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
        private static final BooleanProperty NORTH = BlockStateProperties.NORTH;
        private static final BooleanProperty WEST = BlockStateProperties.WEST;
        private static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
        private static final BooleanProperty EAST = BlockStateProperties.EAST;

        /**
         * Constructor.
         */
        public Connected() {
            super(new CustomBlockType.Builder(FramedCapital::getShapeForState)
                    .waterloggable(false)
                    .build());
            this.registerDefaultState(this.stateDefinition.any()
                    .setValue(NORTH, false)
                    .setValue(WEST, false)
                    .setValue(SOUTH, false)
                    .setValue(EAST, false));
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
                    .withHorizontalConnection(this::canConnectTo)
                    .build();
        }

        /**
         * @return new block state after the neighbor was updated.
         */
        @Override
        public BlockState updateShape(BlockState state, Direction dir, BlockState neighborState,
                                      LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
            return super.updateShape(state, dir, neighborState, level, pos, neighborPos)
                    .setValue(NORTH, this.canConnectTo(level, pos.north()))
                    .setValue(WEST, this.canConnectTo(level, pos.west()))
                    .setValue(SOUTH, this.canConnectTo(level, pos.south()))
                    .setValue(EAST, this.canConnectTo(level, pos.east()));
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
            return blockstate.is(this);
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
        private static final VoxelShape BOTTOM = Block.box(0, 0, 0, 16, 8, 16);

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
        private static final VoxelShape TOP = Block.box(0, 8, 0, 16, 16, 16);

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
