package net.arthurllew.framedcr.block;

import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.common.block.IFramedDoubleBlock;
import xfacthd.framedblocks.common.data.doubleblock.CamoGetter;
import xfacthd.framedblocks.common.data.doubleblock.DoubleBlockTopInteractionMode;
import xfacthd.framedblocks.common.data.doubleblock.SolidityCheck;

import javax.annotation.Nullable;

public interface ICustomFramedDoubleBlock extends IFramedDoubleBlock {
    /**
     * @return ?
     */
    @Override
    default DoubleBlockTopInteractionMode calculateTopInteractionMode(BlockState blockState) {
        return DoubleBlockTopInteractionMode.EITHER;
    }

    /**
     * @return ?
     */
    @Override
    default SolidityCheck calculateSolidityCheck(BlockState blockState, Direction direction) {
        return SolidityCheck.NONE;
    }

    /**
     * @return camo getter from either first or second block for texture connections
     */
    @Override
    default CamoGetter calculateCamoGetter(BlockState blockState, Direction dir1, @Nullable Direction dir2) {
        return CamoGetter.NONE;
    }

    /**
     * @return parts of the double block state
     */
    @Override
    default Tuple<BlockState, BlockState> calculateBlockPair(BlockState blockState) {
        Tuple<Block, Block> blocks = calculateBlockPair();
        // Copy block state properties
        BlockState blockStateFirst = blocks.getA().defaultBlockState();
        BlockState blockStateSecond = blocks.getB().defaultBlockState();
        for (Property<?> property : blockState.getProperties()) {
            // Avoid special properties
            if (property != FramedProperties.SOLID
                    && property != FramedProperties.GLOWING
                    && property != BlockStateProperties.WATERLOGGED) {
                blockStateFirst = applyProperty(blockStateFirst, blockState, property);
                blockStateSecond = applyProperty(blockStateSecond, blockState, property);
            }
        }
        // Return states pair
        return new Tuple<>(blockStateFirst, blockStateSecond);
    }

    /**
     * @return parts of the double block
     */
    Tuple<Block, Block> calculateBlockPair();

    /**
     * Helper method for transferring block state property.
     */
    default <T extends Comparable<T>> BlockState applyProperty(BlockState target,
                                                               BlockState source,
                                                               Property<T> property) {
        return target.setValue(property, source.getValue(property));
    }
}
