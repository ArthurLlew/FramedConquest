package net.arthurllew.framedcr.block;

import net.arthurllew.framedcr.block.predicates.VerticalTextureConnectionPredicate;
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
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedPillarSocket extends CustomFramedBlock {
private static final VoxelShape UP = Shapes.or(
            Block.box(0.0F, 8.0F, 0.0F, 16.0F, 16.0F, 16.0F),
            Block.box(4.0F, 0.0F, 4.0F, 12.0F, 16.0F, 12.0F));
    private static final VoxelShape DOWN = Shapes.or(
            Block.box(0.0F, 0.0F, 0.0F, 16.0F, 8.0F, 16.0F),
            Block.box(4.0F, 0.0F, 4.0F, 12.0F, 16.0F, 12.0F));

    /**
     * Direction property.
     */
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    /**
     * Top/bottom half property.
     */
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    /**
     * Constructor.
     */
    public FramedPillarSocket() {
        super(new CustomBlockType.Builder(FramedPillarSocket::getShapeForState)
                .textureConnectionPredicate(VerticalTextureConnectionPredicate.INSTANCE)
                .modelVariantForItem("_down_flat")
                .build());
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, Half.TOP));
    }

    /**
     * Appends block state attributes.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, HALF);
    }

    /**
     * @return block state that should be placed in the world depending on provided context.
     */
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return CustomPlacementStateBuilder.of(this, context)
                .withTargetFacing()
                .withTopBottom()
                .withWater()
                .build();
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
     * Generates shape for provided state.
     */
    public static VoxelShape getShapeForState(BlockState state) {
        return switch (state.getValue(HALF)) {
            case TOP -> UP;
            case BOTTOM -> DOWN;
        };
    }
}
