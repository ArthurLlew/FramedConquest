package net.arthurllew.framedcr.block;

import com.google.common.collect.ImmutableList;
import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.block.PlacementStateBuilder;
import xfacthd.framedblocks.api.shapes.ShapeProvider;
import xfacthd.framedblocks.common.block.cube.FramedLayeredCubeBlock;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedArrowslit extends CustomFramedBlock {
    public static final VoxelShape EAST_FR = Block.box(0.0F, 0.0F, 9.0F, 1.0F, 16.0F, 13.0F);
    public static final VoxelShape EAST_FL = Block.box(0.0F, 0.0F, 3.0F, 1.0F, 16.0F, 7.0F);
    public static final VoxelShape EAST_SR = Block.box(0.0F, 0.0F, 13.0F, 8.0F, 16.0F, 16.0F);
    public static final VoxelShape EAST_SL = Block.box(0.0F, 0.0F, 0.0F, 8.0F, 16.0F, 3.0F);
    public static final VoxelShape EAST_SHAPE = Shapes.or(Shapes.or(EAST_FR, EAST_FL), Shapes.or(EAST_SR, EAST_SL));
    public static final VoxelShape WEST_FR = Block.box(15.0F, 0.0F, 9.0F, 16.0F, 16.0F, 13.0F);
    public static final VoxelShape WEST_FL = Block.box(15.0F, 0.0F, 3.0F, 16.0F, 16.0F, 7.0F);
    public static final VoxelShape WEST_SR = Block.box(8.0F, 0.0F, 13.0F, 16.0F, 16.0F, 16.0F);
    public static final VoxelShape WEST_SL = Block.box(8.0F, 0.0F, 0.0F, 16.0F, 16.0F, 3.0F);
    public static final VoxelShape WEST_SHAPE = Shapes.or(Shapes.or(WEST_FR, WEST_FL), Shapes.or(WEST_SR, WEST_SL));
    public static final VoxelShape NORTH_FR = Block.box(9.0F, 0.0F, 15.0F, 13.0F, 16.0F, 16.0F);
    public static final VoxelShape NORTH_FL = Block.box(3.0F, 0.0F, 15.0F, 7.0F, 16.0F, 16.0F);
    public static final VoxelShape NORTH_SR = Block.box(13.0F, 0.0F, 8.0F, 16.0F, 16.0F, 16.0F);
    public static final VoxelShape NORTH_SL = Block.box(0.0F, 0.0F, 8.0F, 3.0F, 16.0F, 16.0F);
    public static final VoxelShape NORTH_SHAPE = Shapes.or(Shapes.or(NORTH_FR, NORTH_FL), Shapes.or(NORTH_SR, NORTH_SL));
    public static final VoxelShape SOUTH_FR = Block.box(9.0F, 0.0F, 0.0F, 13.0F, 16.0F, 1.0F);
    public static final VoxelShape SOUTH_FL = Block.box(3.0F, 0.0F, 0.0F, 7.0F, 16.0F, 1.0F);
    public static final VoxelShape SOUTH_SR = Block.box(13.0F, 0.0F, 0.0F, 16.0F, 16.0F, 8.0F);
    public static final VoxelShape SOUTH_SL = Block.box(0.0F, 0.0F, 0.0F, 3.0F, 16.0F, 8.0F);
    public static final VoxelShape SOUTH_SHAPE = Shapes.or(Shapes.or(SOUTH_FR, SOUTH_FL), Shapes.or(SOUTH_SR, SOUTH_SL));

    /// See [StairBlock].
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    /**
     * Constructor.
     */
    public FramedArrowslit() {
        super(CustomBlockType.FRAMED_ARROWSLIT);
    }

    /// See [StairBlock].
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, FramedProperties.SOLID, BlockStateProperties.WATERLOGGED);
    }

    /// See [StairBlock].
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return PlacementStateBuilder.of(this, context)
                .withCustom((state, modCtx) -> this.defaultBlockState()
                        .setValue(FACING, context.getHorizontalDirection().getOpposite()))
                .withWater()
                .build();
    }

    /**
     * Produces pairs (block state, shape).
     */
    public static ShapeProvider generateShapes(ImmutableList<BlockState> states) {
        return generateShapes(states, (state) -> switch (state.getValue(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> throw new IllegalStateException();
        });
    }
}
