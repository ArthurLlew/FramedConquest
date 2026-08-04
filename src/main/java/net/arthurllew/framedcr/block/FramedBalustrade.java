package net.arthurllew.framedcr.block;

import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.arthurllew.framedcr.block.util.BlockUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import xfacthd.framedblocks.api.block.PlacementStateBuilder;
import xfacthd.framedblocks.common.block.pillar.FramedPillarBlock;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedBalustrade extends CustomFramedBlock {
    private static final VoxelShape XZ_BASE = Block.box(2.0F, 0.0F, 2.0F, 14.0F, 4.0F, 14.0F);
    private static final VoxelShape X_LOWER = Block.box(3.0F, 4.0F, 4.0F, 13.0F, 5.0F, 12.0F);
    private static final VoxelShape X_MIDDLE = Block.box(4.0F, 5.0F, 6.0F, 12.0F, 10.0F, 10.0F);
    private static final VoxelShape X_TOP = Block.box(0.0F, 10.0F, 3.0F, 16.0F, 16.0F, 13.0F);
    private static final VoxelShape Z_LOWER = Block.box(4.0F, 4.0F, 3.0F, 12.0F, 5.0F, 13.0F);
    private static final VoxelShape Z_MIDDLE = Block.box(6.0F, 5.0F, 4.0F, 10.0F, 10.0F, 12.0F);
    private static final VoxelShape Z_TOP = Block.box(3.0F, 10.0F, 0.0F, 13.0F, 16.0F, 16.0F);
    private static final VoxelShape X_AXIS_AABB = Shapes.or(XZ_BASE, Shapes.or(X_LOWER, Shapes.or(X_MIDDLE, X_TOP)));
    private static final VoxelShape Z_AXIS_AABB = Shapes.or(XZ_BASE, Shapes.or(Z_LOWER, Shapes.or(Z_MIDDLE, Z_TOP)));
    private static final VoxelShape Y_BASE = Block.box(2.0F, 0.0F, 2.0F, 14.0F, 4.0F, 14.0F);
    private static final VoxelShape Y_LOWER = Block.box(3.0F, 4.0F, 3.0F, 13.0F, 5.0F, 13.0F);
    private static final VoxelShape Y_MIDDLE = Block.box(4.0F, 5.0F, 4.0F, 12.0F, 11.0F, 12.0F);
    private static final VoxelShape Y_TOP = Block.box(2.0F, 11.0F, 2.0F, 14.0F, 16.0F, 14.0F);
    private static final VoxelShape Y_AXIS_AABB = Shapes.or(Y_BASE, Shapes.or(Y_LOWER, Shapes.or(Y_MIDDLE, Y_TOP)));

    /**
     * Constructor.
     */
    public FramedBalustrade() {
        super(new CustomBlockType.Builder(FramedBalustrade::getShapeForState)
                .modelVariantForItem("_y")
                .build());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BlockStateProperties.AXIS, Direction.Axis.Y));
    }

    /// See [FramedPillarBlock].
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.AXIS);
    }

    /// See [FramedPillarBlock].
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return PlacementStateBuilder.of(this, ctx)
                .withClickedAxis()
                .withWater()
                .build();
    }

    /**
     * @return rotated block state.
     */
    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return BlockUtils.rotateAxis(state, rotation, BlockStateProperties.AXIS);
    }

    /// See [FramedPillarBlock].
    @Override
    public BlockState getItemModelSource() {
        return this.defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Y);
    }

    /// See [FramedPillarBlock].
    @Override
    public BlockState getJadeRenderState(BlockState state) {
        return this.defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Y);
    }

    /**
     * Generates shape for provided state.
     */
    public static VoxelShape getShapeForState(BlockState state) {
        return switch (state.getValue(BlockStateProperties.AXIS)) {
            case X -> X_AXIS_AABB;
            case Y -> Y_AXIS_AABB;
            case Z -> Z_AXIS_AABB;
        };
    }
}
