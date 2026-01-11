package net.arthurllew.framedcr.block;

import com.google.common.collect.ImmutableList;
import net.arthurllew.framedcr.block.properties.ArchFacadeShape;
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
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.shapes.ShapeProvider;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FramedArchFacade extends CustomFramedBlock {
    private static final VoxelShape EAST_SHAPE = Block.box(0.0F, 0.0F, 0.0F, 8.0F, 16.0F, 16.0F);
    private static final VoxelShape WEST_SHAPE = Block.box(8.0F, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F);
    private static final VoxelShape SOUTH_SHAPE = Block.box(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 8.0F);
    private static final VoxelShape NORTH_SHAPE = Block.box(0.0F, 0.0F, 8.0F, 16.0F, 16.0F, 16.0F);
    private static final VoxelShape ARCH_NORTH_R_SHAPE = Shapes.or(Block.box(0.0F, 8.0F, 8.0F, 16.0F, 16.0F, 16.0F), Block.box(0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 16.0F));
    private static final VoxelShape ARCH_NORTH_L_SHAPE = Shapes.or(Block.box(8.0F, 0.0F, 8.0F, 16.0F, 16.0F, 16.0F), Block.box(0.0F, 8.0F, 8.0F, 8.0F, 16.0F, 16.0F));
    private static final VoxelShape ARCH_WEST_L_SHAPE = Shapes.or(Block.box(8.0F, 8.0F, 0.0F, 16.0F, 16.0F, 16.0F), Block.box(8.0F, 0.0F, 8.0F, 16.0F, 8.0F, 16.0F));
    private static final VoxelShape ARCH_WEST_R_SHAPE = Shapes.or(Block.box(8.0F, 0.0F, 0.0F, 16.0F, 16.0F, 8.0F), Block.box(8.0F, 8.0F, 8.0F, 16.0F, 16.0F, 16.0F));
    private static final VoxelShape ARCH_EAST_R_SHAPE = Shapes.or(Block.box(0.0F, 8.0F, 0.0F, 8.0F, 16.0F, 16.0F), Block.box(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F));
    private static final VoxelShape ARCH_EAST_L_SHAPE = Shapes.or(Block.box(0.0F, 0.0F, 8.0F, 8.0F, 16.0F, 16.0F), Block.box(0.0F, 8.0F, 0.0F, 8.0F, 16.0F, 8.0F));
    private static final VoxelShape ARCH_SOUTH_L_SHAPE = Shapes.or(Block.box(0.0F, 8.0F, 0.0F, 16.0F, 16.0F, 8.0F), Block.box(8.0F, 0.0F, 0.0F, 16.0F, 8.0F, 8.0F));
    private static final VoxelShape ARCH_SOUTH_R_SHAPE = Shapes.or(Block.box(0.0F, 0.0F, 0.0F, 8.0F, 16.0F, 8.0F), Block.box(8.0F, 8.0F, 0.0F, 16.0F, 16.0F, 8.0F));
    private static final VoxelShape ARCH_MIDDLE_SOUTH_SHAPE = Block.box(0.0F, 8.0F, 0.0F, 16.0F, 16.0F, 8.0F);
    private static final VoxelShape ARCH_MIDDLE_NORTH_SHAPE = Block.box(0.0F, 8.0F, 8.0F, 16.0F, 16.0F, 16.0F);
    private static final VoxelShape ARCH_MIDDLE_WEST_SHAPE = Block.box(8.0F, 8.0F, 0.0F, 16.0F, 16.0F, 16.0F);
    private static final VoxelShape ARCH_MIDDLE_EAST_SHAPE = Block.box(0.0F, 8.0F, 0.0F, 8.0F, 16.0F, 16.0F);
    private static final VoxelShape ARCH_NORTH_R_BOTTOM_SHAPE = Shapes.or(Block.box(0.0F, 0.0F, 8.0F, 16.0F, 8.0F, 16.0F), Block.box(0.0F, 8.0F, 8.0F, 8.0F, 16.0F, 16.0F));
    private static final VoxelShape ARCH_NORTH_L_BOTTOM_SHAPE = Shapes.or(Block.box(8.0F, 0.0F, 8.0F, 16.0F, 16.0F, 16.0F), Block.box(0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 16.0F));
    private static final VoxelShape ARCH_WEST_L_BOTTOM_SHAPE = Shapes.or(Block.box(8.0F, 0.0F, 0.0F, 16.0F, 8.0F, 16.0F), Block.box(8.0F, 8.0F, 8.0F, 16.0F, 16.0F, 16.0F));
    private static final VoxelShape ARCH_WEST_R_BOTTOM_SHAPE = Shapes.or(Block.box(8.0F, 0.0F, 0.0F, 16.0F, 16.0F, 8.0F), Block.box(8.0F, 0.0F, 8.0F, 16.0F, 8.0F, 16.0F));
    private static final VoxelShape ARCH_EAST_R_BOTTOM_SHAPE = Shapes.or(Block.box(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 16.0F), Block.box(0.0F, 8.0F, 0.0F, 8.0F, 16.0F, 8.0F));
    private static final VoxelShape ARCH_EAST_L_BOTTOM_SHAPE = Shapes.or(Block.box(0.0F, 0.0F, 8.0F, 8.0F, 16.0F, 16.0F), Block.box(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F));
    private static final VoxelShape ARCH_SOUTH_L_BOTTOM_SHAPE = Shapes.or(Block.box(0.0F, 0.0F, 0.0F, 16.0F, 8.0F, 8.0F), Block.box(8.0F, 8.0F, 0.0F, 16.0F, 16.0F, 8.0F));
    private static final VoxelShape ARCH_SOUTH_R_BOTTOM_SHAPE = Shapes.or(Block.box(0.0F, 0.0F, 0.0F, 8.0F, 16.0F, 8.0F), Block.box(8.0F, 0.0F, 0.0F, 16.0F, 8.0F, 8.0F));
    private static final VoxelShape ARCH_MIDDLE_SOUTH_BOTTOM_SHAPE = Block.box(0.0F, 0.0F, 0.0F, 16.0F, 8.0F, 8.0F);
    private static final VoxelShape ARCH_MIDDLE_NORTH_BOTTOM_SHAPE = Block.box(0.0F, 0.0F, 8.0F, 16.0F, 8.0F, 16.0F);
    private static final VoxelShape ARCH_MIDDLE_WEST_BOTTOM_SHAPE = Block.box(8.0F, 0.0F, 0.0F, 16.0F, 8.0F, 16.0F);
    private static final VoxelShape ARCH_MIDDLE_EAST_BOTTOM_SHAPE = Block.box(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 16.0F);

    /**
     * Arch shape property.
     */
    public static final EnumProperty<ArchFacadeShape> TYPE = EnumProperty.create("shape", ArchFacadeShape.class);
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
    public FramedArchFacade() {
        super(CustomBlockType.FRAMED_ARCH_FACADE);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TYPE, ArchFacadeShape.ONE)
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, Half.TOP));
    }

    /**
     * Appends block state attributes.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE, FACING, HALF, FramedProperties.SOLID, BlockStateProperties.WATERLOGGED);
    }

    /**
     * @return empty shape.
     */
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    /**
     * @return empty shape.
     */
    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    /**
     * @return empty shape.
     */
    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
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
     * @return block state that should be placed in the world depending on provided context.
     */
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return CustomPlacementStateBuilder.of(this, context)
                .withShapeIsCycledCheck()
                .withArchFacing()
                .withTopBottom()
                .withWater()
                .build();
    }

    /**
     * Called when an item is used on this block.
     */
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player.getAbilities().mayBuild && stack.getItem() == this.asItem()) {
            level.setBlock(pos, state.cycle(TYPE), 3);
            return ItemInteractionResult.SUCCESS;
        } else {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
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
     * Produces pairs (block state, shape).
     */
    public static ShapeProvider generateShapes(ImmutableList<BlockState> states) {
        return generateShapes(states, (state) -> {
            if (state.getValue(HALF) == Half.TOP) {
                if (state.getValue(TYPE) == ArchFacadeShape.ONE) {
                    return switch (state.getValue(FACING)) {
                        case NORTH -> NORTH_SHAPE;
                        case SOUTH -> SOUTH_SHAPE;
                        case EAST -> EAST_SHAPE;
                        case WEST -> WEST_SHAPE;
                        default -> throw new IllegalStateException();
                    };
                } else if (state.getValue(TYPE) != ArchFacadeShape.TWO_L && state.getValue(TYPE) != ArchFacadeShape.THREE_L) {
                    if (state.getValue(TYPE) != ArchFacadeShape.TWO_R && state.getValue(TYPE) != ArchFacadeShape.THREE_R) {
                        return switch (state.getValue(FACING)) {
                            case NORTH -> ARCH_MIDDLE_NORTH_SHAPE;
                            case SOUTH -> ARCH_MIDDLE_SOUTH_SHAPE;
                            case EAST -> ARCH_MIDDLE_EAST_SHAPE;
                            case WEST -> ARCH_MIDDLE_WEST_SHAPE;
                            default -> throw new IllegalStateException();
                        };
                    } else {
                        return switch (state.getValue(FACING)) {
                            case NORTH -> ARCH_NORTH_R_SHAPE;
                            case SOUTH -> ARCH_SOUTH_R_SHAPE;
                            case EAST -> ARCH_EAST_R_SHAPE;
                            case WEST -> ARCH_WEST_R_SHAPE;
                            default -> throw new IllegalStateException();
                        };
                    }
                } else {
                    return switch (state.getValue(FACING)) {
                        case NORTH -> ARCH_NORTH_L_SHAPE;
                        case SOUTH -> ARCH_SOUTH_L_SHAPE;
                        case EAST -> ARCH_EAST_L_SHAPE;
                        case WEST -> ARCH_WEST_L_SHAPE;
                        default -> throw new IllegalStateException();
                    };
                }
            } else if (state.getValue(TYPE) == ArchFacadeShape.ONE) {
                return switch (state.getValue(FACING)) {
                    case NORTH -> NORTH_SHAPE;
                    case SOUTH -> SOUTH_SHAPE;
                    case EAST -> EAST_SHAPE;
                    case WEST -> WEST_SHAPE;
                    default -> throw new IllegalStateException();
                };
            } else if (state.getValue(TYPE) != ArchFacadeShape.TWO_L && state.getValue(TYPE) != ArchFacadeShape.THREE_L) {
                if (state.getValue(TYPE) != ArchFacadeShape.TWO_R && state.getValue(TYPE) != ArchFacadeShape.THREE_R) {
                    return switch (state.getValue(FACING)) {
                        case NORTH -> ARCH_MIDDLE_NORTH_BOTTOM_SHAPE;
                        case SOUTH -> ARCH_MIDDLE_SOUTH_BOTTOM_SHAPE;
                        case EAST -> ARCH_MIDDLE_EAST_BOTTOM_SHAPE;
                        case WEST -> ARCH_MIDDLE_WEST_BOTTOM_SHAPE;
                        default -> throw new IllegalStateException();
                    };
                } else {
                    return switch (state.getValue(FACING)) {
                        case NORTH -> ARCH_NORTH_R_BOTTOM_SHAPE;
                        case SOUTH -> ARCH_SOUTH_R_BOTTOM_SHAPE;
                        case EAST -> ARCH_EAST_R_BOTTOM_SHAPE;
                        case WEST -> ARCH_WEST_R_BOTTOM_SHAPE;
                        default -> throw new IllegalStateException();
                    };
                }
            } else {
                return switch (state.getValue(FACING)) {
                    case NORTH -> ARCH_NORTH_L_BOTTOM_SHAPE;
                    case SOUTH -> ARCH_SOUTH_L_BOTTOM_SHAPE;
                    case EAST -> ARCH_EAST_L_BOTTOM_SHAPE;
                    case WEST -> ARCH_WEST_L_BOTTOM_SHAPE;
                    default -> throw new IllegalStateException();
                };
            }
        });
    }
}
