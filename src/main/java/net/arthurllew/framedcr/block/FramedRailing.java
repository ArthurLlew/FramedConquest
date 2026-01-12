package net.arthurllew.framedcr.block;

import com.google.common.collect.ImmutableList;
import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.arthurllew.framedcr.block.util.CustomPlacementStateBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.shapes.ShapeProvider;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedRailing extends CustomFramedBlock {
    protected static final VoxelShape NORTH_SHAPE = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape WEST_SHAPE = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SOUTH_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    protected static final VoxelShape EAST_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);

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
    private FramedRailing(CustomBlockType blockType) {
        super(blockType);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, Half.TOP));
    }

    /**
     * Throws {@link IllegalArgumentException} if {@link CustomBlockType} is incorrect.
     * @return instance of {@link FramedRailing}
     */
    public static FramedRailing of (CustomBlockType blockType) {
        if (blockType != CustomBlockType.FRAMED_RAILING && blockType != CustomBlockType.FRAMED_RAILING_CORNER)
            throw new IllegalArgumentException();

        return new FramedRailing(blockType);
    }

    /**
     * Appends block state attributes.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, HALF, FramedProperties.SOLID, BlockStateProperties.WATERLOGGED);
    }

    /**
     * @return empty shape.
     */
    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    /**
     * @return whether a block can be replaced by the other one.
     */
    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return context.getItemInHand().is(this.asItem())
                && context.getPlayer() != null
                && !context.getPlayer().isCrouching();
    }

    /**
     * @return whether a block can be replaced by the other one.
     */
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        if (this.getCustomBlockType() == CustomBlockType.FRAMED_RAILING)
        {
            return CustomPlacementStateBuilder.of(this, context)
                    .withShapeIsCycledCheck()
                    .withHorizontalFacing(true)
                    .withWater()
                    .build();
        }
        else {
            return CustomPlacementStateBuilder.of(this, context)
                    .withShapeIsCycledCheck()
                    .withQuarterFacing()
                    .withWater()
                    .build();
        }
    }

    /**
     * Called when an item is used on this block.
     */
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player.getAbilities().mayBuild && stack.getItem() == this.asItem()) {
            switch (state.getValue(HALF)) {
                case TOP -> level.setBlock(pos, state.setValue(HALF, Half.BOTTOM), 3);
                case BOTTOM -> level.setBlock(pos, state.setValue(HALF, Half.TOP), 3);
            }
            return ItemInteractionResult.SUCCESS;
        } else {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
    }

    /**
     * Produces pairs (block state, shape).
     */
    public static ShapeProvider generateShapes(ImmutableList<BlockState> states) {
        return generateShapes(states, (state) -> switch (state.getValue(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> throw new IllegalStateException();
        });
    }

    /**
     * Produces pairs (block state, shape).
     */
    public static ShapeProvider generateCornerShapes(ImmutableList<BlockState> states) {
        return generateShapes(states, (state) -> switch (state.getValue(FACING)) {
            case NORTH -> Shapes.or(NORTH_SHAPE, WEST_SHAPE);
            case WEST -> Shapes.or(WEST_SHAPE, SOUTH_SHAPE);
            case SOUTH -> Shapes.or(SOUTH_SHAPE, EAST_SHAPE);
            case EAST -> Shapes.or(EAST_SHAPE, NORTH_SHAPE);
            default -> throw new IllegalStateException();
        });
    }
}
