package net.arthurllew.framedcr.block;

import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.arthurllew.framedcr.block.util.BlockUtils;
import net.arthurllew.framedcr.block.util.CustomPlacementStateBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.block.cube.FramedLayeredCubeBlock;
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
     * Constructor.
     */
    public FramedPillar() {
        super(new CustomBlockType.Builder(FramedPillar::getShapeForState)
                .modelVariantForItem("_1")
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

    /// See [FramedLayeredCubeBlock].
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LAYERS);
    }

    /// See [FramedLayeredCubeBlock].
    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return BlockUtils.canLayeredBlockBeReplaced(LAYERS, MAX_LAYERS, this, state, context,
                () -> {
                    Direction dir = context.getClickedFace();
                    return dir != Direction.UP && dir != Direction.DOWN;
                });
    }

    /// See [FramedLayeredCubeBlock].
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
}
