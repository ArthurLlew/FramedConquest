package net.arthurllew.framedcr.block;

import com.google.common.collect.ImmutableList;
import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.arthurllew.framedcr.block.util.CustomPlacementStateBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.shapes.ShapeProvider;
import net.minecraft.world.level.block.StairBlock;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedTwoMeterArch extends CustomFramedBlock {
    public static final VoxelShape TOP_AABB = Block.box(0.0F, 8.0F, 0.0F, 16.0F, 16.0F, 16.0F);
    public static final VoxelShape BOTTOM_OCTET_NW = Block.box(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F);
    public static final VoxelShape BOTTOM_OCTET_NE = Block.box(8.0F, 0.0F, 0.0F, 16.0F, 8.0F, 8.0F);
    public static final VoxelShape BOTTOM_OCTET_SW = Block.box(0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 16.0F);
    public static final VoxelShape BOTTOM_OCTET_SE = Block.box(8.0F, 0.0F, 8.0F, 16.0F, 8.0F, 16.0F);
    public static final VoxelShape BOTTOM_AABB = Block.box(0.0F, 0.0F, 0.0F, 16.0F, 8.0F, 16.0F);
    public static final VoxelShape TOP_OCTET_NW = Block.box(0.0F, 8.0F, 0.0F, 8.0F, 16.0F, 8.0F);
    public static final VoxelShape TOP_OCTET_NE = Block.box(8.0F, 8.0F, 0.0F, 16.0F, 16.0F, 8.0F);
    public static final VoxelShape TOP_OCTET_SW = Block.box(0.0F, 8.0F, 8.0F, 8.0F, 16.0F, 16.0F);
    public static final VoxelShape TOP_OCTET_SE = Block.box(8.0F, 8.0F, 8.0F, 16.0F, 16.0F, 16.0F);

    /// See [StairBlock].
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    /// See [StairBlock].
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    /**
     * Constructor.
     */
    public FramedTwoMeterArch() {
        super(CustomBlockType.FRAMED_TWO_METER_ARCH);
    }

    /// See [StairBlock].
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, HALF, FramedProperties.SOLID, BlockStateProperties.WATERLOGGED);
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

    /// See [StairBlock].
    @Override
    protected BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    /// See [StairBlock].
    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
    }

    /**
     * Produces pairs (block state, shape).
     */
    public static ShapeProvider generateShapes(ImmutableList<BlockState> states) {
        return generateShapes(states, (state) ->
                switch (state.getValue(HALF)) {
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
                });
    }

    /**
     * @return whether this block should cull face in given direction.
     */
    public static boolean fullFacePredicate(BlockState state, Direction dir) {
        if (dir == Direction.UP) {
            return state.getValue(BlockStateProperties.HALF) == Half.TOP;
        } else if (dir == Direction.DOWN) {
            return state.getValue(BlockStateProperties.HALF) == Half.BOTTOM;
        } else {
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            return facing == dir;
        }
    }
}
