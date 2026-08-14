package net.arthurllew.framedcr.block;

import net.arthurllew.framedcr.block.entity.FramedConquestDoubleBlockEntity;
import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.arthurllew.framedcr.block.util.CustomPlacementStateBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedTwoMeterArch extends CustomFramedBlock {
    private static final VoxelShape TOP_AABB = Block.box(0.0F, 8.0F, 0.0F, 16.0F, 16.0F, 16.0F);
    private static final VoxelShape BOTTOM_OCTET_NW = Block.box(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F);
    private static final VoxelShape BOTTOM_OCTET_NE = Block.box(8.0F, 0.0F, 0.0F, 16.0F, 8.0F, 8.0F);
    private static final VoxelShape BOTTOM_OCTET_SW = Block.box(0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 16.0F);
    private static final VoxelShape BOTTOM_OCTET_SE = Block.box(8.0F, 0.0F, 8.0F, 16.0F, 8.0F, 16.0F);
    private static final VoxelShape BOTTOM_AABB = Block.box(0.0F, 0.0F, 0.0F, 16.0F, 8.0F, 16.0F);
    private static final VoxelShape TOP_OCTET_NW = Block.box(0.0F, 8.0F, 0.0F, 8.0F, 16.0F, 8.0F);
    private static final VoxelShape TOP_OCTET_NE = Block.box(8.0F, 8.0F, 0.0F, 16.0F, 16.0F, 8.0F);
    private static final VoxelShape TOP_OCTET_SW = Block.box(0.0F, 8.0F, 8.0F, 8.0F, 16.0F, 16.0F);
    private static final VoxelShape TOP_OCTET_SE = Block.box(8.0F, 8.0F, 8.0F, 16.0F, 16.0F, 16.0F);

    /// See [StairBlock].
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    /// See [StairBlock].
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    /**
     * Base constructor.
     */
    public FramedTwoMeterArch(CustomBlockType blockType) {
        super(blockType);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, Half.TOP));
    }

    /**
     * Constructor.
     */
    public FramedTwoMeterArch() {
        this(new CustomBlockType.Builder(FramedTwoMeterArch::getShapeForState)
                .fullFacePredicate(FramedTwoMeterArch::fullFacePredicate)
                .build());
    }

    /// See [StairBlock].
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, HALF);
    }

    /// See [StairBlock].
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return CustomPlacementStateBuilder.of(this, context)
                .withHorizontalFacing()
                .withTopBottom()
                .withWater()
                .build();
    }

    /**
     * Generates shape for provided state.
     */
    public static VoxelShape getShapeForState(BlockState state) {
        return switch (state.getValue(HALF)) {
            case TOP -> switch (state.getValue(FACING)) {
                case NORTH -> Shapes.or(TOP_AABB, Shapes.or(BOTTOM_OCTET_NW, BOTTOM_OCTET_NE));
                case WEST -> Shapes.or(TOP_AABB, Shapes.or(BOTTOM_OCTET_NW, BOTTOM_OCTET_SW));
                case SOUTH -> Shapes.or(TOP_AABB, Shapes.or(BOTTOM_OCTET_SW, BOTTOM_OCTET_SE));
                case EAST -> Shapes.or(TOP_AABB, Shapes.or(BOTTOM_OCTET_NE, BOTTOM_OCTET_SE));
                default -> throw new IllegalStateException();
            };
            case BOTTOM -> switch (state.getValue(FACING)) {
                case NORTH -> Shapes.or(BOTTOM_AABB, Shapes.or(TOP_OCTET_NW, TOP_OCTET_NE));
                case WEST -> Shapes.or(BOTTOM_AABB, Shapes.or(TOP_OCTET_NW, TOP_OCTET_SW));
                case SOUTH -> Shapes.or(BOTTOM_AABB, Shapes.or(TOP_OCTET_SW, TOP_OCTET_SE));
                case EAST -> Shapes.or(BOTTOM_AABB, Shapes.or(TOP_OCTET_NE, TOP_OCTET_SE));
                default -> throw new IllegalStateException();
            };
        };
    }

    /**
     * @return whether this block face in given direction is considered full.
     */
    public static boolean fullFacePredicate(BlockState state, Direction dir) {
        if (dir == Direction.UP) {
            return state.getValue(HALF) == Half.TOP;
        } else if (dir == Direction.DOWN) {
            return state.getValue(HALF) == Half.BOTTOM;
        } else {
            return state.getValue(FACING) == dir;
        }
    }

    /**
     * Double part variant.
     */
    public static class Double extends FramedTwoMeterArch implements ICustomFramedDoubleBlock {
        // Block pair
        private final Block blockFirst, blockSecond;

        /**
         * Constructor.
         */
        public Double(Block blockFirst, Block blockSecond) {
            super(new CustomBlockType.Builder(FramedTwoMeterArch::getShapeForState)
                    .doubleBlock(true)
                    .fullFacePredicate(FramedTwoMeterArch::fullFacePredicate)
                    .build());
            this.blockFirst = blockFirst;
            this.blockSecond = blockSecond;
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
            BlockState blockStateLeft = this.blockFirst.defaultBlockState();
            BlockState blockStateRight = this.blockSecond.defaultBlockState();
            for (Property<?> property : blockState.getProperties()) {
                blockStateLeft = applyProperty(blockStateLeft, blockState, property);
                blockStateRight = applyProperty(blockStateRight, blockState, property);
            }
            // Return states pair
            return new Tuple<>(blockStateLeft, blockStateRight);
        }
    }

    /**
     * Double part variant bottom.
     */
    public static class Bottom extends FramedTwoMeterArch {
        private static final VoxelShape BOTTOM = Block.box(0, 0, 0, 16, 8, 16);

        /**
         * Constructor.
         */
        public Bottom() {
            super(new CustomBlockType.Builder(Bottom::getShapeForState)
                    .blockItem(false)
                    .fullFacePredicate(FramedTwoMeterArch::fullFacePredicate)
                    .build());
        }

        /**
         * Generates shape for provided state.
         */
        public static VoxelShape getShapeForState(BlockState state) {
            return Shapes.join(FramedTwoMeterArch.getShapeForState(state), BOTTOM, BooleanOp.AND);
        }
    }

    /**
     * Double part variant top.
     */
    public static class Top extends FramedTwoMeterArch {
        private static final VoxelShape TOP = Block.box(0, 8, 0, 16, 16, 16);

        /**
         * Constructor.
         */
        public Top() {
            super(new CustomBlockType.Builder(Top::getShapeForState)
                    .blockItem(false)
                    .fullFacePredicate(FramedTwoMeterArch::fullFacePredicate)
                    .build());
        }

        /**
         * Generates shape for provided state.
         */
        public static VoxelShape getShapeForState(BlockState state) {
            return Shapes.join(FramedTwoMeterArch.getShapeForState(state), TOP, BooleanOp.AND);
        }
    }
}
