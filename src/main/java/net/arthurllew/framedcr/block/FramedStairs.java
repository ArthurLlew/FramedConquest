package net.arthurllew.framedcr.block;

import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.arthurllew.framedcr.block.util.CustomPlacementStateBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.stream.IntStream;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedStairs extends CustomFramedBlock {
    private static final VoxelShape TOP_AABB = Block.box(0.0F, 8.0F, 0.0F, 16.0F, 16.0F, 16.0F);
    private static final VoxelShape BOTTOM_AABB = Block.box(0.0F, 0.0F, 0.0F, 16.0F, 8.0F, 16.0F);
    private static final VoxelShape OCTET_NNN = Block.box(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F);
    private static final VoxelShape OCTET_NNP = Block.box(0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 16.0F);
    private static final VoxelShape OCTET_NPN = Block.box(0.0F, 8.0F, 0.0F, 8.0F, 16.0F, 8.0F);
    private static final VoxelShape OCTET_NPP = Block.box(0.0F, 8.0F, 8.0F, 8.0F, 16.0F, 16.0F);
    private static final VoxelShape OCTET_PNN = Block.box(8.0F, 0.0F, 0.0F, 16.0F, 8.0F, 8.0F);
    private static final VoxelShape OCTET_PNP = Block.box(8.0F, 0.0F, 8.0F, 16.0F, 8.0F, 16.0F);
    private static final VoxelShape OCTET_PPN = Block.box(8.0F, 8.0F, 0.0F, 16.0F, 16.0F, 8.0F);
    private static final VoxelShape OCTET_PPP = Block.box(8.0F, 8.0F, 8.0F, 16.0F, 16.0F, 16.0F);
    private static final VoxelShape[] TOP_SHAPES = makeShapes(TOP_AABB, OCTET_NNN, OCTET_PNN, OCTET_NNP, OCTET_PNP);
    private static final VoxelShape[] BOTTOM_SHAPES = makeShapes(BOTTOM_AABB, OCTET_NPN, OCTET_PPN, OCTET_NPP, OCTET_PPP);
    private static final int[] SHAPE_BY_STATE = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};

    /// See [StairBlock].
    private static VoxelShape[] makeShapes(VoxelShape slabShape,
                                           VoxelShape nwCorner, VoxelShape neCorner,
                                           VoxelShape swCorner, VoxelShape seCorner) {
        return IntStream.range(0, 16)
                .mapToObj((p_56945_) ->
                        makeStairShape(p_56945_, slabShape, nwCorner, neCorner, swCorner, seCorner))
                .toArray(VoxelShape[]::new);
    }

    /// See [StairBlock].
    private static VoxelShape makeStairShape(int bitfield, VoxelShape slabShape,
                                             VoxelShape nwCorner, VoxelShape neCorner,
                                             VoxelShape swCorner, VoxelShape seCorner) {
        VoxelShape voxelshape = slabShape;
        if ((bitfield & 1) != 0) {
            voxelshape = Shapes.or(slabShape, nwCorner);
        }

        if ((bitfield & 2) != 0) {
            voxelshape = Shapes.or(voxelshape, neCorner);
        }

        if ((bitfield & 4) != 0) {
            voxelshape = Shapes.or(voxelshape, swCorner);
        }

        if ((bitfield & 8) != 0) {
            voxelshape = Shapes.or(voxelshape, seCorner);
        }

        return voxelshape;
    }

    /// See [StairBlock].
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    /// See [StairBlock].
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    /// See [StairBlock].
    public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;

    /**
     * Constructor.
     */
    public FramedStairs() {
        super(new CustomBlockType.Builder(FramedStairs::getShapeForState).build());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, Half.TOP)
                .setValue(SHAPE, StairsShape.STRAIGHT));
    }

    /**
     * Appends block state attributes.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, HALF, SHAPE);
    }

    /**
     * @return empty shape.
     */
    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    /**
     * @return block state that should be placed in the world depending on provided context.
     */
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return CustomPlacementStateBuilder.of(this, context)
                .withHorizontalFacing()
                .withTopBottom()
                .withStairsShape()
                .withWater()
                .build();
    }

    /**
     * Generates shape for provided state.
     */
    public static VoxelShape getShapeForState(BlockState state) {
        return (state.getValue(HALF) == Half.TOP ? TOP_SHAPES : BOTTOM_SHAPES)
                [SHAPE_BY_STATE[state.getValue(SHAPE).ordinal() * 4 + state.getValue(FACING).get2DDataValue()]];
    }
}
