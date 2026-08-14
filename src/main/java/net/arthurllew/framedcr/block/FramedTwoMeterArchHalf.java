package net.arthurllew.framedcr.block;

import net.arthurllew.framedcr.block.entity.FramedConquestDoubleBlockEntity;
import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.shapes.ShapeUtils;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.common.block.ExtPlacementStateBuilder;
import xfacthd.framedblocks.common.block.stairs.standard.FramedHalfStairsBlock;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.data.shapes.stairs.standard.HalfStairsShapes;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedTwoMeterArchHalf extends CustomFramedBlock {
    private static final VoxelShape BOTTOM_LEFT = ShapeUtils.orUnoptimized(Block.box(8.0F, 0.0F, 0.0F, 16.0F, 8.0F, 16.0F), Block.box(8.0F, 8.0F, 8.0F, 16.0F, 16.0F, 16.0F));
    private static final VoxelShape BOTTOM_RIGHT = ShapeUtils.orUnoptimized(Block.box(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 16.0F), Block.box(0.0F, 8.0F, 8.0F, 8.0F, 16.0F, 16.0F));
    private static final VoxelShape TOP_LEFT = ShapeUtils.orUnoptimized(Block.box(8.0F, 8.0F, 0.0F, 16.0F, 16.0F, 16.0F), Block.box(8.0F, 0.0F, 8.0F, 16.0F, 8.0F, 16.0F));
    private static final VoxelShape TOP_RIGHT = ShapeUtils.orUnoptimized(Block.box(0.0F, 8.0F, 0.0F, 8.0F, 16.0F, 16.0F), Block.box(0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 16.0F));
    private static final VoxelShape[] ALL_SHAPES;

    /**
     * Base constructor.
     */
    public FramedTwoMeterArchHalf(CustomBlockType blockType) {
        super(blockType);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FramedProperties.FACING_HOR, Direction.NORTH)
                .setValue(FramedProperties.TOP, true)
                .setValue(PropertyHolder.RIGHT, false));
    }

    /**
     * Constructor.
     */
    public FramedTwoMeterArchHalf() {
        this(new CustomBlockType.Builder(HalfStairsShapes::generate)
                .modelVariantForItem("_right")
                .build());
    }

    /// See [FramedHalfStairsBlock].
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FramedProperties.FACING_HOR, FramedProperties.TOP, PropertyHolder.RIGHT);
    }

    /// See [FramedHalfStairsBlock].
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return ExtPlacementStateBuilder.of(this, ctx)
                .withTargetOrHorizontalFacing()
                .withTop()
                .withRight()
                .withWater()
                .build();
    }

    /// See [FramedHalfStairsBlock].
    @Override
    public BlockState rotate(BlockState state, Direction face, Rotation rot) {
        Direction dir = state.getValue(FramedProperties.FACING_HOR);
        if (Utils.isY(face)) {
            return state.setValue(FramedProperties.FACING_HOR, rot.rotate(dir));
        } else if (rot == Rotation.NONE) {
            return state;
        } else {
            return face.getAxis() == dir.getAxis()
                    ? state.cycle(PropertyHolder.RIGHT) : state.cycle(FramedProperties.TOP);
        }
    }

    /// See [FramedHalfStairsBlock].
    @Override
    protected BlockState rotate(BlockState state, Rotation rot) {
        return this.rotate(state, Direction.UP, rot);
    }

    /// See [FramedHalfStairsBlock].
    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        if (mirror == Mirror.NONE) {
            return state;
        } else {
            Direction dir = state.getValue(FramedProperties.FACING_HOR);
            if (mirror == Mirror.FRONT_BACK && Utils.isX(dir) || mirror == Mirror.LEFT_RIGHT && Utils.isZ(dir)) {
                state = state.setValue(FramedProperties.FACING_HOR, dir.getOpposite());
            }

            return state.cycle(PropertyHolder.RIGHT);
        }
    }

    /// See [FramedHalfStairsBlock].
    @Override
    public BlockState getJadeRenderState(BlockState state) {
        return this.defaultBlockState()
                .setValue(FramedProperties.FACING_HOR, Direction.SOUTH);
    }

    /// See [HalfStairsShapes].
    public static VoxelShape getShapeForState(BlockState state) {
        // Get block state properties
        Direction dir = state.getValue(FramedProperties.FACING_HOR);
        int top = state.getValue(FramedProperties.TOP) ? 4 : 0;
        int right = state.getValue(PropertyHolder.RIGHT) ? 8 : 0;
        // Select shape
        return ALL_SHAPES[dir.get2DDataValue() | top | right];
    }

    /// See [HalfStairsShapes].
    static {
        // Static init of shapes
        VoxelShape[] shapes = new VoxelShape[16];
        ShapeUtils.makeHorizontalRotations(BOTTOM_LEFT, Direction.SOUTH, shapes, 0);
        ShapeUtils.makeHorizontalRotations(BOTTOM_RIGHT, Direction.SOUTH, shapes, 8);
        ShapeUtils.makeHorizontalRotations(TOP_LEFT, Direction.SOUTH, shapes, 4);
        ShapeUtils.makeHorizontalRotations(TOP_RIGHT, Direction.SOUTH, shapes, 4 | 8);
        ALL_SHAPES = shapes;
    }

    /**
     * Double part variant.
     */
    public static class Double extends FramedTwoMeterArchHalf implements ICustomFramedDoubleBlock {
        // Block pair
        private final Block blockLeft, blockRight;

        /**
         * Constructor.
         */
        public Double(Block blockLeft, Block blockRight) {
            super(new CustomBlockType.Builder(FramedTwoMeterArchHalf::getShapeForState)
                    .doubleBlock(true)
                    .modelVariantForItem("_right")
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
     * Double part variant bottom.
     */
    public static class Bottom extends FramedTwoMeterArchHalf {
        private static final VoxelShape BOTTOM = Block.box(0, 0, 0, 16, 8, 16);

        /**
         * Constructor.
         */
        public Bottom() {
            super(new CustomBlockType.Builder(Bottom::getShapeForState)
                    .blockItem(false)
                    .modelVariantForItem("_right")
                    .build());
        }

        /**
         * Generates shape for provided state.
         */
        public static VoxelShape getShapeForState(BlockState state) {
            return Shapes.join(FramedTwoMeterArchHalf.getShapeForState(state), BOTTOM, BooleanOp.AND);
        }
    }

    /**
     * Double part variant top.
     */
    public static class Top extends FramedTwoMeterArchHalf {
        private static final VoxelShape TOP = Block.box(0, 8, 0, 16, 16, 16);

        /**
         * Constructor.
         */
        public Top() {
            super(new CustomBlockType.Builder(Top::getShapeForState)
                    .blockItem(false)
                    .modelVariantForItem("_right")
                    .build());
        }

        /**
         * Generates shape for provided state.
         */
        public static VoxelShape getShapeForState(BlockState state) {
            return Shapes.join(FramedTwoMeterArchHalf.getShapeForState(state), TOP, BooleanOp.AND);
        }
    }
}
