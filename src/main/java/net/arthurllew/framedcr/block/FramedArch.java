package net.arthurllew.framedcr.block;

import com.google.common.collect.ImmutableList;
import net.arthurllew.framedcr.block.shape.ArchShape;
import net.arthurllew.framedcr.block.type.CustomBlockType;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.block.PlacementStateBuilder;
import xfacthd.framedblocks.api.shapes.ShapeProvider;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedArch extends CustomFramedBlock {
    private static final VoxelShape[] SHAPES = {Shapes.or(Block.box(0.0F, 0.0F, 8.0F, 16.0F, 16.0F, 16.0F), Block.box(0.0F, 8.0F, 0.0F, 16.0F, 16.0F, 8.0F)), Shapes.or(Block.box(0.0F, 0.0F, 0.0F, 8.0F, 16.0F, 16.0F), Block.box(8.0F, 8.0F, 0.0F, 16.0F, 16.0F, 16.0F)), Shapes.or(Block.box(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 8.0F), Block.box(0.0F, 8.0F, 8.0F, 16.0F, 16.0F, 16.0F)), Shapes.or(Block.box(8.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F), Block.box(0.0F, 8.0F, 0.0F, 8.0F, 16.0F, 16.0F))};
    private static final VoxelShape MIDDLE_SHAPE = Block.box(0.0F, 8.0F, 0.0F, 16.0F, 16.0F, 16.0F);

    /**
     * Arch shape property.
     */
    public static final EnumProperty<ArchShape> TYPE = EnumProperty.create("shape", ArchShape.class);
    /**
     * Horizontal direction property.
     */
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    /**
     * Constructor.
     */
    public FramedArch() {
        super(CustomBlockType.FRAMED_ARCH);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TYPE, ArchShape.ONE)
                .setValue(FACING, Direction.NORTH));
    }

    /**
     * Appends block state attributes.
     */
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE, FACING, FramedProperties.SOLID, BlockStateProperties.WATERLOGGED);
    }

    /**
     * @return empty shape.
     */
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    /**
     * @return empty shape.
     */
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    /**
     * @return empty shape.
     */
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    /**
     * @return whether a block can be replaced by the other one.
     */
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return context.getItemInHand().is(this.asItem())
                && context.getPlayer() != null
                && !context.getPlayer().isCrouching();
    }

    /**
     * @return block state that should be placed in the world depending on provided context.
     */
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return PlacementStateBuilder.of(this, context)
                .withCustom((state, modCtx) -> {
                    // Check whether the block shape is being cycled
                    BlockState prevState = context.getLevel().getBlockState(context.getClickedPos());
                    if (prevState.is(this)) {
                        return null;
                    }

                    BlockPos pos = context.getClickedPos();
                    Direction facing = context.getClickedFace();
                    if (facing == Direction.UP || facing == Direction.DOWN) {
                        facing = this.getFacingFromUpDown(context, pos);
                    }

                    return this.defaultBlockState().setValue(FACING, facing).setValue(TYPE, ArchShape.ONE);
                })
                .withWater()
                .build();
    }

    /**
     * @return horizontal direction derived from clicked location.
     */
    private Direction getFacingFromUpDown(BlockPlaceContext context, BlockPos pos) {
        Direction horizontalFacing = context.getHorizontalDirection();
        return switch (horizontalFacing) {
            case EAST -> !(context.getClickLocation().z - (double) pos.getZ() > 0.5D)
                    ? horizontalFacing.getClockWise() : horizontalFacing.getCounterClockWise();
            case SOUTH -> !(context.getClickLocation().x - (double) pos.getX() < 0.5D)
                    ? horizontalFacing.getClockWise() : horizontalFacing.getCounterClockWise();
            case WEST -> !(context.getClickLocation().z - (double) pos.getZ() < 0.5D)
                    ? horizontalFacing.getClockWise() : horizontalFacing.getCounterClockWise();
            case NORTH -> !(context.getClickLocation().x - (double) pos.getX() > 0.5D)
                    ? horizontalFacing.getClockWise() : horizontalFacing.getCounterClockWise();
            default -> throw new IllegalStateException();
        };
    }

    /**
     * Called when item is used on this block.
     */
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!player.getAbilities().mayBuild) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        } else if (stack.getItem() == this.asItem()) {
            level.setBlock(pos, state.cycle(TYPE), 3);
            return ItemInteractionResult.SUCCESS;
        } else {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
    }

    /**
     * Produces pairs (block state, shape).
     */
    public static ShapeProvider generateShapes(ImmutableList<BlockState> states) {
        return generateShapes(states, (state) -> {
            if (state.getValue(TYPE) == ArchShape.ONE) {
                return Shapes.block();
            } else if (state.getValue(TYPE) == ArchShape.THREE_MIDDLE) {
                return MIDDLE_SHAPE;
            } else {
                switch (state.getValue(FACING)) {
                    case EAST -> {
                        return SHAPES[1];
                    }
                    case SOUTH -> {
                        return SHAPES[2];
                    }
                    case WEST -> {
                        return SHAPES[3];
                    }
                    default -> {
                        return SHAPES[0];
                    }
                }
            }
        });
    }
}
