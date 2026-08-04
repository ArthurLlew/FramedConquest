package net.arthurllew.framedcr.block;

import net.arthurllew.framedcr.block.entity.FramedConquestDoubleBlockEntity;
import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.arthurllew.framedcr.block.util.CustomPlacementStateBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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
