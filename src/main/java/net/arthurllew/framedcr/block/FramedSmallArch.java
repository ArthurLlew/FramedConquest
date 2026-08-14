package net.arthurllew.framedcr.block;

import net.arthurllew.framedcr.block.entity.FramedConquestDoubleBlockEntity;
import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.arthurllew.framedcr.block.util.BlockUtils;
import net.arthurllew.framedcr.block.util.CustomPlacementStateBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedSmallArch extends CustomFramedBlock {
    private static final VoxelShape TOP_AABB = Block.box(0.0F, 8.0F, 0.0F, 16.0F, 16.0F, 16.0F);
    private static final VoxelShape BOTTOM_AABB = Block.box(0.0F, 0.0F, 0.0F, 16.0F, 8.0F, 16.0F);

    /**
     * Horizontal axis property.
     */
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    /**
     * Top/bottom location property.
     */
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    /**
     * Base constructor.
     */
    public FramedSmallArch(CustomBlockType blockType) {
        super(blockType);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(AXIS, Direction.Axis.Z)
                .setValue(HALF, Half.TOP));
    }

    /**
     * Constructor.
     */
    public FramedSmallArch() {
        this(new CustomBlockType.Builder(FramedSmallArch::getShapeForState).build());
    }

    /**
     * Appends block state attributes.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AXIS, HALF);
    }

    /**
     * @return block state that should be placed in the world depending on provided context.
     */
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return CustomPlacementStateBuilder.of(this, context)
                .withHorizontalAxis()
                .withTopBottom()
                .withWater()
                .build();
    }

    /**
     * @return rotated block state.
     */
    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return BlockUtils.rotateAxis(state, rotation, AXIS);
    }

    /**
     * Generates shape for provided state.
     */
    public static VoxelShape getShapeForState(BlockState state) {
        return switch(state.getValue(HALF)) {
            case TOP -> TOP_AABB;
            case BOTTOM -> BOTTOM_AABB;
        };
    }

    /**
     * Double part variant.
     */
    public static class Double extends FramedSmallArch implements ICustomFramedDoubleBlock {
        // Block pair
        private final Block blockLeft, blockRight;

        /**
         * Constructor.
         */
        public Double(Block blockLeft, Block blockRight) {
            super(new CustomBlockType.Builder(FramedSmallArch::getShapeForState)
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
     * Double part variant bottom.
     */
    public static class Bottom extends FramedSmallArch {
        private static final VoxelShape BOTTOM = Block.box(0, 4, 0, 16, 12, 16);

        /**
         * Constructor.
         */
        public Bottom() {
            super(new CustomBlockType.Builder(Bottom::getShapeForState)
                    .blockItem(false)
                    .build());
        }

        /**
         * Generates shape for provided state.
         */
        public static VoxelShape getShapeForState(BlockState state) {
            return Shapes.join(FramedSmallArch.getShapeForState(state), BOTTOM, BooleanOp.AND);
        }
    }

    /**
     * Double part variant top.
     */
    public static class Top extends FramedSmallArch {
        private static final VoxelShape TOP = Shapes.or(Block.box(0, 0, 0, 16, 4, 16),
                Block.box(0, 12, 0, 16, 16, 16));

        /**
         * Constructor.
         */
        public Top() {
            super(new CustomBlockType.Builder(Top::getShapeForState)
                    .blockItem(false)
                    .build());
        }

        /**
         * Generates shape for provided state.
         */
        public static VoxelShape getShapeForState(BlockState state) {
            return Shapes.join(FramedSmallArch.getShapeForState(state), TOP, BooleanOp.AND);
        }
    }
}
