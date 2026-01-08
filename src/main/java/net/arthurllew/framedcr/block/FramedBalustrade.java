package net.arthurllew.framedcr.block;

import com.google.common.collect.ImmutableList;
import net.arthurllew.framedcr.block.type.CustomBlockType;
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
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.block.PlacementStateBuilder;
import xfacthd.framedblocks.api.shapes.ShapeProvider;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.common.block.pillar.FramedPillarBlock;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedBalustrade extends CustomFramedBlock {
    public static final VoxelShape XZ_BASE = Block.box(2.0F, 0.0F, 2.0F, 14.0F, 4.0F, 14.0F);
    public static final VoxelShape X_LOWER = Block.box(3.0F, 4.0F, 4.0F, 13.0F, 5.0F, 12.0F);
    public static final VoxelShape X_MIDDLE = Block.box(4.0F, 5.0F, 6.0F, 12.0F, 10.0F, 10.0F);
    public static final VoxelShape X_TOP = Block.box(0.0F, 10.0F, 3.0F, 16.0F, 16.0F, 13.0F);
    public static final VoxelShape Z_LOWER = Block.box(4.0F, 4.0F, 3.0F, 12.0F, 5.0F, 13.0F);
    public static final VoxelShape Z_MIDDLE = Block.box(6.0F, 5.0F, 4.0F, 10.0F, 10.0F, 12.0F);
    public static final VoxelShape Z_TOP = Block.box(3.0F, 10.0F, 0.0F, 13.0F, 16.0F, 16.0F);
    public static final VoxelShape X_AXIS_AABB = Shapes.or(XZ_BASE, Shapes.or(X_LOWER, Shapes.or(X_MIDDLE, X_TOP)));
    public static final VoxelShape Z_AXIS_AABB = Shapes.or(XZ_BASE, Shapes.or(Z_LOWER, Shapes.or(Z_MIDDLE, Z_TOP)));
    public static final VoxelShape Y_BASE = Block.box(2.0F, 0.0F, 2.0F, 14.0F, 4.0F, 14.0F);
    public static final VoxelShape Y_LOWER = Block.box(3.0F, 4.0F, 3.0F, 13.0F, 5.0F, 13.0F);
    public static final VoxelShape Y_MIDDLE = Block.box(4.0F, 5.0F, 4.0F, 12.0F, 11.0F, 12.0F);
    public static final VoxelShape Y_TOP = Block.box(2.0F, 11.0F, 2.0F, 14.0F, 16.0F, 14.0F);
    public static final VoxelShape Y_AXIS_AABB = Shapes.or(Y_BASE, Shapes.or(Y_LOWER, Shapes.or(Y_MIDDLE, Y_TOP)));

    /**
     * Constructor.
     */
    public FramedBalustrade() {
        super(CustomBlockType.FRAMED_BALUSTRADE);
    }

    /// See [FramedPillarBlock].
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.AXIS, FramedProperties.SOLID, BlockStateProperties.WATERLOGGED);
    }

    /// See [FramedPillarBlock].
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return PlacementStateBuilder.of(this, ctx)
                .withCustom((state, modCtx) ->
                        state.setValue(BlockStateProperties.AXIS, modCtx.getClickedFace().getAxis()))
                .withWater()
                .build();
    }

    /// See [FramedPillarBlock].
    public BlockState rotate(BlockState state, Direction side, Rotation rot) {
        return rot != Rotation.NONE ? state.cycle(BlockStateProperties.AXIS) : state;
    }

    /// See [FramedPillarBlock].
    protected BlockState rotate(BlockState state, Rotation rot) {
        Direction.Axis axis = state.getValue(BlockStateProperties.AXIS);
        if (axis != Direction.Axis.Y && rot != Rotation.NONE && rot != Rotation.CLOCKWISE_180) {
            axis = Utils.nextAxisNotEqualTo(axis, Direction.Axis.Y);
            return state.setValue(BlockStateProperties.AXIS, axis);
        } else {
            return state;
        }
    }

    /// See [FramedPillarBlock].
    public BlockState getItemModelSource() {
        return this.defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Y);
    }

    /// See [FramedPillarBlock].
    public BlockState getJadeRenderState(BlockState state) {
        return this.defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Y);
    }

    /**
     * Produces pairs (block state, shape).
     */
    public static ShapeProvider generateShapes(ImmutableList<BlockState> states) {
        return generateShapes(states, (state) ->
                switch (state.getValue(BlockStateProperties.AXIS)) {
                    case X -> X_AXIS_AABB;
                    case Y -> Y_AXIS_AABB;
                    case Z -> Z_AXIS_AABB;
                });
    }
}
