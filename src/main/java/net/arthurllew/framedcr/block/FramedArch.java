package net.arthurllew.framedcr.block;

import net.arthurllew.framedcr.block.properties.ArchShape;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

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
        super(new CustomBlockType.Builder(FramedArch::getShapeForState)
                .modelVariantForItem("_1")
                .build());
        this.registerDefaultState(this.defaultBlockState()
                .setValue(TYPE, ArchShape.ONE)
                .setValue(FACING, Direction.NORTH));
    }

    /**
     * Appends block state attributes.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE, FACING);
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
                .withArchDirection()
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
     * Generates shape for provided state.
     */
    public static VoxelShape getShapeForState(BlockState state) {
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
    }
}
