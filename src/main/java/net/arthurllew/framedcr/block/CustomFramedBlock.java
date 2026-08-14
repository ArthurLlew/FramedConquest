package net.arthurllew.framedcr.block;

import net.arthurllew.framedcr.block.entity.FramedConquestBlockEntity;
import net.arthurllew.framedcr.block.type.CustomBlockType;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import xfacthd.framedblocks.api.block.AbstractFramedBlock;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.common.block.FramedBlock;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Custom implementation of {@link FramedBlock}.
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class CustomFramedBlock extends AbstractFramedBlock {
    /**
     * Constructor.
     */
    protected CustomFramedBlock(CustomBlockType blockType) {
        super(blockType, IFramedBlock.createProperties(blockType));
        this.registerDefaultState(this.defaultBlockState()
                .setValue(BlockStateProperties.WATERLOGGED, false));
    }

    /**
     * Appends basic block state attributes.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FramedProperties.SOLID, BlockStateProperties.WATERLOGGED);
    }

    /**
     * May override block item model using provided block state.
     * @return {@code null} meaning that an already existing item model will be used.
     */
    @Override
    public @Nullable BlockState getItemModelSource() {
        return null;
    }

    /// See [AbstractFramedBlock].
    @Override
    public BlockState getJadeRenderState(BlockState state) {
        return this.defaultBlockState();
    }

    /**
     * @return connected block entity.
     */
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FramedConquestBlockEntity(pos, state);
    }

    /**
     * @return this block type.
     */
    public CustomBlockType getCustomBlockType() {
        return (CustomBlockType) this.getBlockType();
    }

    /**
     * @param state this block state
     * @return how many blocks this block state drops
     */
    public float getLootCount(BlockState state) {
        return 1;
    }

    /**
     * Helper method for transferring block state property.
     */
    protected static <T extends Comparable<T>> BlockState applyProperty(BlockState target,
                                                                BlockState source,
                                                                Property<T> property) {
        return target.setValue(property, source.getValue(property));
    }
}
