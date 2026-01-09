package net.arthurllew.framedcr.block;

import com.google.common.collect.ImmutableList;
import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.block.PlacementStateBuilder;
import xfacthd.framedblocks.api.shapes.ShapeProvider;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.block.cube.FramedLayeredCubeBlock;
import xfacthd.framedblocks.common.item.FramedSpecialBlockItem;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedQuarterVertical extends CustomFramedBlock {
    public static final VoxelShape[] NORTH_SHAPE = new VoxelShape[]{Block.box(14.0F, 0.0F, 14.0F, 16.0F, 16.0F, 16.0F), Block.box(12.0F, 0.0F, 12.0F, 16.0F, 16.0F, 16.0F), Block.box(8.0F, 0.0F, 8.0F, 16.0F, 16.0F, 16.0F), Block.box(4.0F, 0.0F, 4.0F, 16.0F, 16.0F, 16.0F)};
    public static final VoxelShape[] SOUTH_SHAPE = new VoxelShape[]{Block.box(0.0F, 0.0F, 0.0F, 2.0F, 16.0F, 2.0F), Block.box(0.0F, 0.0F, 0.0F, 4.0F, 16.0F, 4.0F), Block.box(0.0F, 0.0F, 0.0F, 8.0F, 16.0F, 8.0F), Block.box(0.0F, 0.0F, 0.0F, 12.0F, 16.0F, 12.0F)};
    public static final VoxelShape[] EAST_SHAPE = new VoxelShape[]{Block.box(0.0F, 0.0F, 14.0F, 2.0F, 16.0F, 16.0F), Block.box(0.0F, 0.0F, 12.0F, 4.0F, 16.0F, 16.0F), Block.box(0.0F, 0.0F, 8.0F, 8.0F, 16.0F, 16.0F), Block.box(0.0F, 0.0F, 4.0F, 12.0F, 16.0F, 16.0F)};
    public static final VoxelShape[] WEST_SHAPE = new VoxelShape[]{Block.box(14.0F, 0.0F, 0.0F, 16.0F, 16.0F, 2.0F), Block.box(12.0F, 0.0F, 0.0F, 16.0F, 16.0F, 4.0F), Block.box(8.0F, 0.0F, 0.0F, 16.0F, 16.0F, 8.0F), Block.box(4.0F, 0.0F, 0.0F, 16.0F, 16.0F, 12.0F)};

    private static final int MAX_LAYERS = 4;

    /**
     * Horizontal direction property.
     */
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    /**
     * Layers property.
     */
    public static final IntegerProperty LAYERS = IntegerProperty.create("layer", 1, MAX_LAYERS);

    /**
     * Constructor.
     */
    public FramedQuarterVertical() {
        super(CustomBlockType.FRAMED_QUARTER_VERTICAL);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(LAYERS, 1));
    }

    /**
     * Appends block state attributes.
     */
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, LAYERS, FramedProperties.SOLID, BlockStateProperties.WATERLOGGED);
    }

    /**
     * @return whether a block can be replaced by the other one.
     */
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        if (state.getValue(LAYERS) < MAX_LAYERS && context.getItemInHand().is(this.asItem())) {
            if (context instanceof DirectionalPlaceContext || !context.replacingClickedOnBlock()) {
                return true;
            } else {
                Direction clickedFacing = context.getClickedFace();
                Direction facing = state.getValue(FACING);
                return clickedFacing == facing || clickedFacing == facing.getCounterClockWise();
            }
        } else {
            return false;
        }
    }

    /**
     * @return block state that should be placed in the world depending on provided context.
     */
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return PlacementStateBuilder.of(this, context)
                .withCustom((state, modCtx) -> {
                    BlockState prevState = modCtx.getLevel().getBlockState(modCtx.getClickedPos());
                    if (prevState.is(this)) {
                        int layers = prevState.getValue(LAYERS);
                        return prevState.setValue(LAYERS, Math.min(MAX_LAYERS, layers + 1));
                    } else {
                        BlockPos blockpos = context.getClickedPos();
                        return this.defaultBlockState()
                                .setValue(FACING, getHitVecHorizontalAxisDirection(
                                        context.getHorizontalDirection().getOpposite(), blockpos, context));
                    }
                })
                .withWater()
                .build();
    }

    /**
     * Block item is created inside class to add extra functionality
     * (see {@link FramedLayeredCubeBlock} and {@link FBContent}).
     * If not done in this manner, the block will lose camo and
     * ultimately break in behavior, when changing state on
     * block item usage.
     */
    public BlockItem createBlockItem() {
        return new FramedSpecialBlockItem.Single(this, new Item.Properties()) {
            protected @org.jetbrains.annotations.Nullable BlockState getReplacementState(BlockPlaceContext ctx,
                                                                                         BlockState originalState) {
                return FramedQuarterVertical.this.getStateForPlacement(ctx);
            }
        };
    }

    /**
     * Produces pairs (block state, shape).
     */
    public static ShapeProvider generateShapes(ImmutableList<BlockState> states) {
        return generateShapes(states, (state) ->
                switch (state.getValue(FACING)) {
                    case NORTH -> NORTH_SHAPE[state.getValue(LAYERS) - 1];
                    case WEST -> WEST_SHAPE[state.getValue(LAYERS) - 1];
                    case SOUTH -> SOUTH_SHAPE[state.getValue(LAYERS) - 1];
                    case EAST -> EAST_SHAPE[state.getValue(LAYERS) - 1];
                    default -> throw new IllegalStateException();
                });
    }
}
