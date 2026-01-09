package net.arthurllew.framedcr.block;

import com.google.common.collect.ImmutableList;
import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.block.PlacementStateBuilder;
import xfacthd.framedblocks.api.shapes.ShapeProvider;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedSmallArchHalf extends CustomFramedBlock {
    public static final VoxelShape BOTTOM_OCTET_NW = Block.box(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F);
    public static final VoxelShape BOTTOM_OCTET_NE = Block.box(8.0F, 0.0F, 0.0F, 16.0F, 8.0F, 8.0F);
    public static final VoxelShape BOTTOM_OCTET_SW = Block.box(0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 16.0F);
    public static final VoxelShape BOTTOM_OCTET_SE = Block.box(8.0F, 0.0F, 8.0F, 16.0F, 8.0F, 16.0F);
    public static final VoxelShape TOP_OCTET_NW = Block.box(0.0F, 8.0F, 0.0F, 8.0F, 16.0F, 8.0F);
    public static final VoxelShape TOP_OCTET_NE = Block.box(8.0F, 8.0F, 0.0F, 16.0F, 16.0F, 8.0F);
    public static final VoxelShape TOP_OCTET_SW = Block.box(0.0F, 8.0F, 8.0F, 8.0F, 16.0F, 16.0F);
    public static final VoxelShape TOP_OCTET_SE = Block.box(8.0F, 8.0F, 8.0F, 16.0F, 16.0F, 16.0F);

    /**
     * Horizontal direction property.
     */
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    /**
     * Top/bottom location property.
     */
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    /**
     * Constructor.
     */
    public FramedSmallArchHalf() {
        super(CustomBlockType.FRAMED_SMALL_ARCH_HALF);
    }

    /**
     * Appends block state attributes.
     */
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, HALF, FramedProperties.SOLID, BlockStateProperties.WATERLOGGED);
    }

    /**
     * @return block state that should be placed in the world depending on provided context.
     */
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return PlacementStateBuilder.of(this, context)
                .withCustom((state, modCtx) -> {
                    Direction direction = context.getClickedFace();
                    BlockPos blockpos = context.getClickedPos();
                    return this.defaultBlockState()
                            .setValue(FACING, context.getHorizontalDirection())
                            .setValue(HALF, direction == Direction.DOWN
                                    || direction != Direction.UP
                                    && context.getClickLocation().y - (double)blockpos.getY() > 0.5D
                                    ? Half.TOP : Half.BOTTOM);
                })
                .withWater()
                .build();
    }

    /**
     * Produces pairs (block state, shape).
     */
    public static ShapeProvider generateShapes(ImmutableList<BlockState> states) {
        return generateShapes(states, (state) ->
                switch (state.getValue(HALF)) {
                    case TOP -> switch (state.getValue(FACING)) {
                        case NORTH -> Shapes.or(TOP_OCTET_NW, TOP_OCTET_NE);
                        case WEST -> Shapes.or(TOP_OCTET_NW, TOP_OCTET_SW);
                        case SOUTH -> Shapes.or(TOP_OCTET_SW, TOP_OCTET_SE);
                        case EAST -> Shapes.or(TOP_OCTET_NE, TOP_OCTET_SE);
                        default -> throw new IllegalStateException();
                    };
                    case BOTTOM -> switch (state.getValue(FACING)) {
                        case NORTH -> Shapes.or(BOTTOM_OCTET_NW, BOTTOM_OCTET_NE);
                        case WEST -> Shapes.or(BOTTOM_OCTET_NW, BOTTOM_OCTET_SW);
                        case SOUTH -> Shapes.or(BOTTOM_OCTET_SW, BOTTOM_OCTET_SE);
                        case EAST -> Shapes.or(BOTTOM_OCTET_NE, BOTTOM_OCTET_SE);
                        default -> throw new IllegalStateException();
                    };
                });
    }
}
