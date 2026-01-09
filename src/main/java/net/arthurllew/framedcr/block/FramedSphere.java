package net.arthurllew.framedcr.block;

import com.google.common.collect.ImmutableList;
import net.arthurllew.framedcr.block.shape.SphereShape;
import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.block.PlacementStateBuilder;
import xfacthd.framedblocks.api.shapes.ShapeProvider;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedSphere extends CustomFramedBlock {
    private static final VoxelShape SMALL = Shapes.or(Block.box(4.0F, 1.0F, 4.0F, 12.0F, 9.0F, 12.0F), Block.box(5.5F, 0.0F, 5.5F, 10.5F, 1.0F, 10.5F), Block.box(5.5F, 9.0F, 5.5F, 10.5F, 10.0F, 10.5F), Block.box(3.0F, 2.5F, 5.5F, 4.0F, 7.5F, 10.5F), Block.box(12.0F, 2.5F, 5.5F, 13.0F, 7.5F, 10.5F), Block.box(5.5F, 2.5F, 12.0F, 10.5F, 7.5F, 13.0F), Block.box(5.5F, 2.5F, 2.9999, 10.5F, 7.5F, 3.9999));
    private static final VoxelShape LARGE = Shapes.or(Block.box(1.64, 1.59, 1.64, 14.36, 14.31, 14.36), Block.box(4.025, 3.975, 0.04979, 11.975, 11.925, 1.63979), Block.box(4.025, 0.0F, 4.025, 11.975, 1.59, 11.975), Block.box(4.025, 14.31, 4.025, 11.975, 15.9, 11.975), Block.box(0.05, 3.975, 4.025, 1.64, 11.925, 11.975), Block.box(14.36, 3.975, 4.025, 15.95, 11.925, 11.975), Block.box(4.025, 3.975, 14.36, 11.975, 11.925, 15.95));
    private static final VoxelShape EGG = Shapes.or(Block.box(6.0F, 15.0F, 6.0F, 10.0F, 16.0F, 10.0F), Block.box(5.0F, 14.0F, 5.0F, 11.0F, 15.0F, 11.0F), Block.box(5.0F, 13.0F, 5.0F, 11.0F, 14.0F, 11.0F), Block.box(3.0F, 11.0F, 3.0F, 13.0F, 13.0F, 13.0F), Block.box(2.0F, 8.0F, 2.0F, 14.0F, 11.0F, 14.0F), Block.box(1.0F, 3.0F, 1.0F, 15.0F, 8.0F, 15.0F), Block.box(2.0F, 1.0F, 2.0F, 14.0F, 3.0F, 14.0F), Block.box(3.0F, 0.0F, 3.0F, 13.0F, 1.0F, 13.0F));

    /**
     * Sphere type.
     */
    public static final EnumProperty<SphereShape> TYPE = EnumProperty.create("type", SphereShape.class);

    /**
     * Constructor.
     */
    public FramedSphere() {
        super(CustomBlockType.FRAMED_SPHERE);
        this.registerDefaultState(this.stateDefinition.any().setValue(TYPE, SphereShape.LARGE));
    }

    /**
     * Appends block state attributes.
     */
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE, FramedProperties.SOLID, BlockStateProperties.WATERLOGGED);
    }

    /**
     * @return whether a block can be replaced by the other one.
     */
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return context.getItemInHand().is(this.asItem());
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
                        // Cycle through shapes
                        return prevState.setValue(TYPE, switch (prevState.getValue(TYPE)) {
                            case SphereShape.LARGE -> SphereShape.SMALL;
                            case SphereShape.SMALL -> SphereShape.EGG;
                            case SphereShape.EGG -> SphereShape.LARGE;
                        });
                    } else {
                        return state;
                    }
                })
                .withWater()
                .build();
    }

    /**
     * Produces pairs (block state, shape).
     */
    public static ShapeProvider generateShapes(ImmutableList<BlockState> states) {
        return generateShapes(states, (state) -> switch (state.getValue(TYPE).toString()) {
            case "egg" -> EGG;
            case "small" -> SMALL;
            case "large" -> LARGE;
            default -> throw new IllegalStateException();
        });
    }
}
