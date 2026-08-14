package net.arthurllew.framedcr.block;

import net.arthurllew.framedcr.block.entity.FramedConquestDoubleBlockEntity;
import net.arthurllew.framedcr.block.predicates.VerticalTextureConnectionPredicate;
import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.arthurllew.framedcr.block.util.BlockUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.block.cube.FramedLayeredCubeBlock;
import xfacthd.framedblocks.common.data.doubleblock.CamoGetter;
import xfacthd.framedblocks.common.item.FramedSpecialBlockItem;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedCapitalCornerVertical extends CustomFramedBlock {
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
    public static final IntegerProperty LAYERS = IntegerProperty.create("layer", 2, MAX_LAYERS);

    /**
     * Base constructor.
     */
    public FramedCapitalCornerVertical(CustomBlockType blockType) {
        super(blockType);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(LAYERS, 2));
    }

    /**
     * Constructor.
     */
    public FramedCapitalCornerVertical() {
        this(new CustomBlockType.Builder(FramedCapitalCornerVertical::getShapeForState)
                .fullFacePredicate(FramedCornerVertical::fullFacePredicate)
                .modelVariantForItem("_2")
                .craftingCount(MAX_LAYERS)
                .isLayered(true)
                .build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getLootCount(BlockState state) {
        return state.getValue(LAYERS) - 2;
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
                return FramedCapitalCornerVertical.this.getStateForPlacement(ctx);
            }
        };
    }

    /**
     * Generates shape for provided state.
     */
    public static VoxelShape getShapeForState(BlockState state) {
        return switch (state.getValue(FACING)) {
            case NORTH -> FramedCornerVertical.NORTH_SHAPE[state.getValue(LAYERS) - 1];
            case WEST -> FramedCornerVertical.WEST_SHAPE[state.getValue(LAYERS) - 1];
            case SOUTH -> FramedCornerVertical.SOUTH_SHAPE[state.getValue(LAYERS) - 1];
            case EAST -> FramedCornerVertical.EAST_SHAPE[state.getValue(LAYERS) - 1];
            default -> throw new IllegalStateException();
        };
    }

    /**
     * Double part variant.
     */
    public static class Double extends FramedCapitalCornerVertical implements ICustomFramedDoubleBlock {
        // Block pair
        private final Block blockFirst, blockSecond;
        /**
         * Which block camo should be used to connect textures.
         */
        private final CamoGetter camoGetter;

        /**
         * Constructor.
         */
        public Double(Block blockFirst, Block blockSecond, CamoGetter camoGetter) {
            super(new CustomBlockType.Builder(FramedCapitalCornerVertical::getShapeForState)
                    .doubleBlock(true)
                    .fullFacePredicate(FramedCornerVertical::fullFacePredicate)
                    .textureConnectionPredicate(VerticalTextureConnectionPredicate.INSTANCE)
                    .modelVariantForItem("_2")
                    .craftingCount(MAX_LAYERS)
                    .isLayered(true)
                    .build());
            this.blockFirst = blockFirst;
            this.blockSecond = blockSecond;
            this.camoGetter = camoGetter;
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

        /**
         * @return camo getter from either first or second block for texture connections.
         */
        @Override
        public CamoGetter calculateCamoGetter(BlockState blockState, Direction dir1, @Nullable Direction dir2) {
            return this.camoGetter;
        }
    }

    /**
     * Double part variant bottom.
     */
    public static class Bottom extends FramedCapitalCornerVertical {
        private static final VoxelShape BOTTOM = Block.box(0, 0, 0, 16, 8, 16);

        /**
         * Constructor.
         */
        public Bottom() {
            super(new CustomBlockType.Builder(Bottom::getShapeForState)
                    .blockItem(false)
                    .fullFacePredicate(FramedCornerVertical::fullFacePredicate)
                    .textureConnectionPredicate(VerticalTextureConnectionPredicate.INSTANCE)
                    .modelVariantForItem("_2")
                    .craftingCount(MAX_LAYERS)
                    .isLayered(true)
                    .build());
        }

        /**
         * Generates shape for provided state.
         */
        public static VoxelShape getShapeForState(BlockState state) {
            return Shapes.join(FramedCapitalCornerVertical.getShapeForState(state), BOTTOM, BooleanOp.AND);
        }
    }

    /**
     * Double part variant top.
     */
    public static class Top extends FramedCapitalCornerVertical {
        private static final VoxelShape TOP = Block.box(0, 8, 0, 16, 16, 16);

        /**
         * Constructor.
         */
        public Top() {
            super(new CustomBlockType.Builder(Top::getShapeForState)
                    .blockItem(false)
                    .fullFacePredicate(FramedCornerVertical::fullFacePredicate)
                    .textureConnectionPredicate(VerticalTextureConnectionPredicate.INSTANCE)
                    .modelVariantForItem("_2")
                    .craftingCount(MAX_LAYERS)
                    .isLayered(true)
                    .build());
        }

        /**
         * Generates shape for provided state.
         */
        public static VoxelShape getShapeForState(BlockState state) {
            return Shapes.join(FramedCapitalCornerVertical.getShapeForState(state), TOP, BooleanOp.AND);
        }
    }
}
