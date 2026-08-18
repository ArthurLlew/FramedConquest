package net.arthurllew.framedcr.block;

import net.arthurllew.framedcr.block.entity.FramedConquestDoubleBlockEntity;
import net.arthurllew.framedcr.block.predicates.VerticalTextureConnectionPredicate;
import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.arthurllew.framedcr.block.util.BlockUtils;
import net.arthurllew.framedcr.block.util.CustomPlacementStateBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
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
public class FramedPillar extends CustomFramedBlock {
    /**
     * Block bounding shape.
     */
    private static final VoxelShape[] SHAPE = new VoxelShape[]{
            Block.box(6.0F, 0.0F, 6.0F, 10.0F, 16.0F, 10.0F),
            Block.box(4.0F, 0.0F, 4.0F, 12.0F, 16.0F, 12.0F),
            Block.box(2.0F, 0.0F, 2.0F, 14.0F, 16.0F, 14.0F)};

    /**
     * Layers count.
     */
    private static final int MAX_LAYERS = 3;
    
    /**
     * Layers property.
     */
    public static final IntegerProperty LAYERS = IntegerProperty.create("layer", 1, MAX_LAYERS);

    /**
     * Base constructor.
     */
    public FramedPillar(CustomBlockType blockType) {
        super(blockType);
        this.registerDefaultState(this.defaultBlockState().setValue(LAYERS, 1));
    }

    /**
     * Constructor.
     */
    public FramedPillar(String modelVariantForItem) {
        this(new CustomBlockType.Builder(FramedPillar::getShapeForState)
                .textureConnectionPredicate(VerticalTextureConnectionPredicate.INSTANCE)
                .modelVariantForItem(modelVariantForItem)
                .craftingCount(MAX_LAYERS)
                .isLayered(true)
                .build());
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
        builder.add(LAYERS);
    }

    /**
     * @return whether a block can be replaced by the other one.
     */
    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return BlockUtils.canLayeredBlockBeReplaced(LAYERS, MAX_LAYERS, this, state, context,
                () -> {
                    Direction dir = context.getClickedFace();
                    return dir != Direction.UP && dir != Direction.DOWN;
                });
    }

    /**
     * @return block state that should be placed in the world depending on provided context.
     */
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return BlockUtils.getLayeredBlockStateForPlacement(LAYERS, MAX_LAYERS, this, context,
                () -> CustomPlacementStateBuilder.of(this, context)
                        .withWater()
                        .build());
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
                return FramedPillar.this.getStateForPlacement(ctx);
            }
        };
    }

    /**
     * Generates shape for provided state.
     */
    public static VoxelShape getShapeForState(BlockState state) {
        return SHAPE[state.getValue(LAYERS) - 1];
    }

    /**
     * Axis directional variant.
     */
    public static class WithAxis extends FramedPillar {
        /**
         * Constructor.
         */
        public WithAxis() {
            super(new CustomBlockType.Builder(FramedPillar::getShapeForState)
                    .textureConnectionPredicate(VerticalTextureConnectionPredicate.INSTANCE)
                    .modelVariantForItem("_2_y")
                    .craftingCount(MAX_LAYERS)
                    .isLayered(true)
                    .build());
            this.registerDefaultState(this.defaultBlockState()
                    .setValue(BlockStateProperties.AXIS, Direction.Axis.Y));
        }

        /**
         * @return whether a block can be replaced by the other one.
         */
        @Override
        protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
            return BlockUtils.canLayeredBlockBeReplaced(LAYERS, MAX_LAYERS, this, state, context,
                    () -> {
                        Direction dir = context.getClickedFace();
                        return dir != Direction.UP && dir != Direction.DOWN;
                    });
        }

        /**
         * Appends block state attributes.
         */
        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(BlockStateProperties.AXIS);
        }

        /**
         * @return block state that should be placed in the world depending on provided context.
         */
        @Override
        public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
            return BlockUtils.getLayeredBlockStateForPlacement(LAYERS, MAX_LAYERS, this, context,
                    () -> CustomPlacementStateBuilder.of(this, context)
                            .withClickedAxis()
                            .withWater()
                            .build());
        }

        /**
         * @return rotated block state.
         */
        @Override
        protected BlockState rotate(BlockState state, Rotation rotation) {
            return BlockUtils.rotateAxis(state, rotation, BlockStateProperties.AXIS);
        }
    }

    /**
     * Double part variant.
     */
    public static class Double extends FramedPillar implements ICustomFramedDoubleBlock {
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
            super(new CustomBlockType.Builder(FramedPillar::getShapeForState)
                    .doubleBlock(true)
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
         * {@inheritDoc}
         */
        @Override
        public Tuple<Block, Block> calculateBlockPair() {
            return new Tuple<>(this.blockFirst, this.blockSecond);
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
    public static class Bottom extends FramedPillar {
        private static final VoxelShape BOTTOM = Block.box(0, 0, 0, 16, 8, 16);

        /**
         * Constructor.
         */
        public Bottom() {
            super(new CustomBlockType.Builder(Bottom::getShapeForState)
                    .blockItem(false)
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
            return Shapes.join(FramedPillar.getShapeForState(state), BOTTOM, BooleanOp.AND);
        }
    }

    /**
     * Double part variant top.
     */
    public static class Top extends FramedPillar {
        private static final VoxelShape TOP = Block.box(0, 8, 0, 16, 16, 16);

        /**
         * Constructor.
         */
        public Top() {
            super(new CustomBlockType.Builder(Top::getShapeForState)
                    .blockItem(false)
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
            return Shapes.join(FramedPillar.getShapeForState(state), TOP, BooleanOp.AND);
        }
    }
}
