package net.arthurllew.framedcr.block;

import com.google.common.collect.ImmutableMap;
import net.arthurllew.framedcr.block.entity.FramedConquestDoubleBlockEntity;
import net.arthurllew.framedcr.block.predicates.VerticalTextureConnectionPredicate;
import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.common.data.doubleblock.CamoGetter;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedWall extends CustomFramedBlock {
    private static final VoxelShape POST = Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);
    private static final VoxelShape NORTH = Block.box(7.0, 0.0, 0.0, 9.0, 16.0, 9.0);
    private static final VoxelShape SOUTH = Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 16.0);
    private static final VoxelShape WEST = Block.box(0.0, 0.0, 7.0, 9.0, 16.0, 9.0);
    private static final VoxelShape EAST = Block.box(7.0, 0.0, 7.0, 16.0, 16.0, 9.0);

    // Shapes lookup
    private static final Map<ShapeKey, VoxelShape> SHAPE_BY_INDEX = makeShapes(4.0F, 3.0F, 16.0F, 0.0F, 14.0F, 16.0F);
    private static final Map<ShapeKey, VoxelShape> COLLISION_SHAPE_BY_INDEX = makeShapes(4.0F, 3.0F, 24.0F, 0.0F, 24.0F, 24.0F);

    // Connection properties
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final EnumProperty<WallSide> EAST_WALL = BlockStateProperties.EAST_WALL;
    public static final EnumProperty<WallSide> NORTH_WALL = BlockStateProperties.NORTH_WALL;
    public static final EnumProperty<WallSide> SOUTH_WALL = BlockStateProperties.SOUTH_WALL;
    public static final EnumProperty<WallSide> WEST_WALL = BlockStateProperties.WEST_WALL;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    /**
     * Base constructor.
     */
    public FramedWall(CustomBlockType blockType) {
        super(blockType);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(UP, true)
                .setValue(NORTH_WALL, WallSide.NONE)
                .setValue(EAST_WALL, WallSide.NONE)
                .setValue(SOUTH_WALL, WallSide.NONE)
                .setValue(WEST_WALL, WallSide.NONE));
    }

    /**
     * Constructor.
     */
    public FramedWall() {
        this(new CustomBlockType.Builder(FramedWall::getShapeForState)
                .textureConnectionPredicate(VerticalTextureConnectionPredicate.INSTANCE)
                .modelVariantForItem("_ns")
                .craftingCount(6)
                .build());
    }

    /**
     * Appends block state attributes.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(UP, NORTH_WALL, EAST_WALL, SOUTH_WALL, WEST_WALL);
    }

    /// See [WallBlock].
    @SuppressWarnings({"SameParameterValue"})
    private static Map<ShapeKey, VoxelShape> makeShapes(float width, float depth, float wallPostHeight,
                                                        float wallMinY, float wallLowHeight, float wallTallHeight) {
        float f = 8.0F - width;
        float f1 = 8.0F + width;
        float f2 = 8.0F - depth;
        float f3 = 8.0F + depth;
        VoxelShape voxelShape0 = Block.box(f, 0.0, f, f1, wallPostHeight, f1);
        VoxelShape voxelShape1 = Block.box(f2, wallMinY, 0.0, f3, wallLowHeight, f3);
        VoxelShape voxelShape2 = Block.box(f2, wallMinY, f2, f3, wallLowHeight, 16.0);
        VoxelShape voxelShape3 = Block.box(0.0, wallMinY, f2, f3, wallLowHeight, f3);
        VoxelShape voxelShape4 = Block.box(f2, wallMinY, f2, 16.0, wallLowHeight, f3);
        VoxelShape voxelShape5 = Block.box(f2, wallMinY, 0.0, f3, wallTallHeight, f3);
        VoxelShape voxelShape6 = Block.box(f2, wallMinY, f2, f3, wallTallHeight, 16.0);
        VoxelShape voxelShape7 = Block.box(0.0, wallMinY, f2, f3, wallTallHeight, f3);
        VoxelShape voxelShape8 = Block.box(f2, wallMinY, f2, 16.0, wallTallHeight, f3);
        ImmutableMap.Builder<ShapeKey, VoxelShape> builder = ImmutableMap.builder();

        for (Boolean bool : BlockStateProperties.UP.getPossibleValues()) {
            for (WallSide wallside0 : BlockStateProperties.EAST_WALL.getPossibleValues()) {
                for (WallSide wallside1 : BlockStateProperties.NORTH_WALL.getPossibleValues()) {
                    for (WallSide wallside2 : BlockStateProperties.WEST_WALL.getPossibleValues()) {
                        for (WallSide wallside3 : BlockStateProperties.SOUTH_WALL.getPossibleValues()) {
                            VoxelShape voxelshape9 = Shapes.empty();
                            voxelshape9 = applyWallShape(voxelshape9, wallside0, voxelShape4, voxelShape8);
                            voxelshape9 = applyWallShape(voxelshape9, wallside2, voxelShape3, voxelShape7);
                            voxelshape9 = applyWallShape(voxelshape9, wallside1, voxelShape1, voxelShape5);
                            voxelshape9 = applyWallShape(voxelshape9, wallside3, voxelShape2, voxelShape6);
                            if (bool) {
                                voxelshape9 = Shapes.or(voxelshape9, voxelShape0);
                            }

                            builder.put(new ShapeKey(bool, wallside1, wallside0, wallside3, wallside2), voxelshape9);
                        }
                    }
                }
            }
        }

        return builder.build();
    }

    /**
     * @param state block state
     * @return corresponding shape key
     */
    private static ShapeKey keyOf(BlockState state) {
        return new ShapeKey(
                state.getValue(UP),
                state.getValue(NORTH_WALL),
                state.getValue(EAST_WALL),
                state.getValue(SOUTH_WALL),
                state.getValue(WEST_WALL)
        );
    }

    /// See [WallBlock].
    private static VoxelShape applyWallShape(VoxelShape baseShape, WallSide height, VoxelShape lowShape, VoxelShape tallShape) {
        if (height == WallSide.TALL) {
            return Shapes.or(baseShape, tallShape);
        } else {
            return height == WallSide.LOW ? Shapes.or(baseShape, lowShape) : baseShape;
        }
    }

    /**
     * Stores wall shape info.
     */
    private record ShapeKey(boolean up, WallSide north, WallSide east, WallSide south, WallSide west) {}

    /// See [WallBlock].
    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE_BY_INDEX.get(keyOf(state));
    }

    /// See [WallBlock].
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelReader levelreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockPos posNorth = blockpos.north();
        BlockPos posEast = blockpos.east();
        BlockPos posSouth = blockpos.south();
        BlockPos posWest = blockpos.west();
        BlockPos posAbove = blockpos.above();
        BlockState stateNorth = levelreader.getBlockState(posNorth);
        BlockState stateEast = levelreader.getBlockState(posEast);
        BlockState stateSouth = levelreader.getBlockState(posSouth);
        BlockState stateWest = levelreader.getBlockState(posWest);
        BlockState stateAbove = levelreader.getBlockState(posAbove);
        return this.updateShape(levelreader, this.defaultBlockState(), posAbove, stateAbove,
                this.canConnect(stateNorth, stateNorth.isFaceSturdy(levelreader, posNorth, Direction.SOUTH), Direction.SOUTH),
                this.canConnect(stateEast, stateEast.isFaceSturdy(levelreader, posEast, Direction.WEST), Direction.WEST),
                this.canConnect(stateSouth, stateSouth.isFaceSturdy(levelreader, posSouth, Direction.NORTH), Direction.NORTH),
                this.canConnect(stateWest, stateWest.isFaceSturdy(levelreader, posWest, Direction.EAST), Direction.EAST));
    }

    /// See [WallBlock].
    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (facing == Direction.DOWN) {
            return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        } else {
            return facing == Direction.UP
                    ? this.topUpdate(level, state, facingPos, facingState)
                    : this.sideUpdate(level, currentPos, state, facingPos, facingState, facing);
        }
    }

    /// See [WallBlock].
    private BlockState topUpdate(LevelReader level, BlockState state, BlockPos pos, BlockState secondState) {
        boolean flag = isConnected(state, NORTH_WALL);
        boolean flag1 = isConnected(state, EAST_WALL);
        boolean flag2 = isConnected(state, SOUTH_WALL);
        boolean flag3 = isConnected(state, WEST_WALL);
        return this.updateShape(level, state, pos, secondState, flag, flag1, flag2, flag3);
    }

    /// See [WallBlock].
    private BlockState sideUpdate(LevelReader level, BlockPos firstPos, BlockState firstState, BlockPos secondPos, BlockState secondState, Direction dir) {
        Direction direction = dir.getOpposite();
        boolean flag = dir == Direction.NORTH
                ? this.canConnect(secondState, secondState.isFaceSturdy(level, secondPos, direction), direction)
                : isConnected(firstState, NORTH_WALL);
        boolean flag1 = dir == Direction.EAST
                ? this.canConnect(secondState, secondState.isFaceSturdy(level, secondPos, direction), direction)
                : isConnected(firstState, EAST_WALL);
        boolean flag2 = dir == Direction.SOUTH
                ? this.canConnect(secondState, secondState.isFaceSturdy(level, secondPos, direction), direction)
                : isConnected(firstState, SOUTH_WALL);
        boolean flag3 = dir == Direction.WEST
                ? this.canConnect(secondState, secondState.isFaceSturdy(level, secondPos, direction), direction)
                : isConnected(firstState, WEST_WALL);
        BlockPos blockpos = firstPos.above();
        BlockState blockstate = level.getBlockState(blockpos);
        return this.updateShape(level, firstState, blockpos, blockstate, flag, flag1, flag2, flag3);
    }

    /// See [WallBlock].
    private static boolean isConnected(BlockState state, Property<WallSide> heightProperty) {
        return state.getValue(heightProperty) != WallSide.NONE;
    }

    /// See [WallBlock].
    private boolean canConnect(BlockState state, boolean sideSolid, Direction direction) {
        Block block = state.getBlock();
        boolean flag = block instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(state, direction);
        return state.is(BlockTags.WALLS) || !isExceptionForConnection(state) && sideSolid || block instanceof IronBarsBlock || flag;
    }

    /// See [WallBlock].
    private BlockState updateShape(
            LevelReader level,
            BlockState state,
            BlockPos pos,
            BlockState neighbour,
            boolean northConnection,
            boolean eastConnection,
            boolean southConnection,
            boolean westConnection
    ) {
        VoxelShape voxelshape = neighbour.getCollisionShape(level, pos).getFaceShape(Direction.DOWN);
        BlockState blockstate = this.updateSides(state, northConnection, eastConnection, southConnection, westConnection, voxelshape);
        return blockstate.setValue(UP, this.shouldRaisePost(blockstate, neighbour, voxelshape));
    }

    /// See [WallBlock].
    private boolean shouldRaisePost(BlockState state, BlockState neighbour, VoxelShape shape) {
        boolean flag = neighbour.getBlock() instanceof WallBlock && neighbour.getValue(UP);
        if (flag) {
            return true;
        } else {
            WallSide wallside = state.getValue(NORTH_WALL);
            WallSide wallside1 = state.getValue(SOUTH_WALL);
            WallSide wallside2 = state.getValue(EAST_WALL);
            WallSide wallside3 = state.getValue(WEST_WALL);
            boolean flag1 = wallside1 == WallSide.NONE;
            boolean flag2 = wallside3 == WallSide.NONE;
            boolean flag3 = wallside2 == WallSide.NONE;
            boolean flag4 = wallside == WallSide.NONE;
            boolean flag5 = flag4 && flag1 && flag2 && flag3 || flag4 != flag1 || flag2 != flag3;
            if (flag5) {
                return true;
            } else {
                boolean flag6 = wallside == WallSide.TALL && wallside1 == WallSide.TALL || wallside2 == WallSide.TALL && wallside3 == WallSide.TALL;
                return !flag6 && (neighbour.is(BlockTags.WALL_POST_OVERRIDE) || isCovered(shape, POST));
            }
        }
    }

    /// See [WallBlock].
    private BlockState updateSides(BlockState state, boolean northConnection, boolean eastConnection, boolean southConnection, boolean westConnection, VoxelShape wallShape) {
        return state.setValue(NORTH_WALL, this.makeWallState(northConnection, wallShape, NORTH))
                .setValue(EAST_WALL, this.makeWallState(eastConnection, wallShape, EAST))
                .setValue(SOUTH_WALL, this.makeWallState(southConnection, wallShape, SOUTH))
                .setValue(WEST_WALL, this.makeWallState(westConnection, wallShape, WEST));
    }

    /// See [WallBlock].
    private WallSide makeWallState(boolean allowConnection, VoxelShape shape, VoxelShape neighbourShape) {
        if (allowConnection) {
            return isCovered(shape, neighbourShape) ? WallSide.TALL : WallSide.LOW;
        } else {
            return WallSide.NONE;
        }
    }

    /// See [WallBlock].
    private static boolean isCovered(VoxelShape firstShape, VoxelShape secondShape) {
        return !Shapes.joinIsNotEmpty(secondShape, firstShape, BooleanOp.ONLY_FIRST);
    }

    /// See [WallBlock].
    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return !state.getValue(WATERLOGGED);
    }

    /// See [WallBlock].
    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    /**
     * @return rotated block state.
     */
    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_180 -> state.setValue(NORTH_WALL, state.getValue(SOUTH_WALL))
                    .setValue(EAST_WALL, state.getValue(WEST_WALL))
                    .setValue(SOUTH_WALL, state.getValue(NORTH_WALL))
                    .setValue(WEST_WALL, state.getValue(EAST_WALL));
            case COUNTERCLOCKWISE_90 -> state.setValue(NORTH_WALL, state.getValue(EAST_WALL))
                    .setValue(EAST_WALL, state.getValue(SOUTH_WALL))
                    .setValue(SOUTH_WALL, state.getValue(WEST_WALL))
                    .setValue(WEST_WALL, state.getValue(NORTH_WALL));
            case CLOCKWISE_90 -> state.setValue(NORTH_WALL, state.getValue(WEST_WALL))
                    .setValue(EAST_WALL, state.getValue(NORTH_WALL))
                    .setValue(SOUTH_WALL, state.getValue(EAST_WALL))
                    .setValue(WEST_WALL, state.getValue(SOUTH_WALL));
            default -> state;
        };
    }

    /**
     * @return mirrored block state.
     */
    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT ->
                    state.setValue(NORTH_WALL, state.getValue(SOUTH_WALL)).setValue(SOUTH_WALL, state.getValue(NORTH_WALL));
            case FRONT_BACK ->
                    state.setValue(EAST_WALL, state.getValue(WEST_WALL)).setValue(WEST_WALL, state.getValue(EAST_WALL));
            default -> super.mirror(state, mirror);
        };
    }

    /**
     * Generates shape for provided state.
     */
    public static VoxelShape getShapeForState(BlockState state) {
        return SHAPE_BY_INDEX.get(keyOf(state));
    }
    
    /**
     * Double part variant.
     */
    public static class Double extends FramedWall implements ICustomFramedDoubleBlock {
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
            super(new CustomBlockType.Builder(FramedWall::getShapeForState)
                    .doubleBlock(true)
                    .textureConnectionPredicate(VerticalTextureConnectionPredicate.INSTANCE)
                    .modelVariantForItem("_ns")
                    .craftingCount(6)
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
    public static class Bottom extends FramedWall {
        private static final VoxelShape BOTTOM = Block.box(0, 0, 0, 16, 8, 16);

        /**
         * Constructor.
         */
        public Bottom() {
            super(new CustomBlockType.Builder(Bottom::getShapeForState)
                    .blockItem(false)
                    .textureConnectionPredicate(VerticalTextureConnectionPredicate.INSTANCE)
                    .modelVariantForItem("_ns")
                    .craftingCount(6)
                    .build());
        }

        /**
         * Generates shape for provided state.
         */
        public static VoxelShape getShapeForState(BlockState state) {
            return Shapes.join(FramedWall.getShapeForState(state), BOTTOM, BooleanOp.AND);
        }
    }

    /**
     * Double part variant top.
     */
    public static class Top extends FramedWall {
        private static final VoxelShape TOP = Block.box(0, 8, 0, 16, 16, 16);

        /**
         * Constructor.
         */
        public Top() {
            super(new CustomBlockType.Builder(Top::getShapeForState)
                    .blockItem(false)
                    .textureConnectionPredicate(VerticalTextureConnectionPredicate.INSTANCE)
                    .modelVariantForItem("_ns")
                    .craftingCount(6)
                    .build());
        }

        /**
         * Generates shape for provided state.
         */
        public static VoxelShape getShapeForState(BlockState state) {
            return Shapes.join(FramedWall.getShapeForState(state), TOP, BooleanOp.AND);
        }
    }
}
