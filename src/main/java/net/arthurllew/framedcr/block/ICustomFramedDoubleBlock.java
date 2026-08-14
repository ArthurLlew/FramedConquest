package net.arthurllew.framedcr.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblocks.common.block.IFramedDoubleBlock;
import xfacthd.framedblocks.common.data.doubleblock.CamoGetter;
import xfacthd.framedblocks.common.data.doubleblock.DoubleBlockTopInteractionMode;
import xfacthd.framedblocks.common.data.doubleblock.SolidityCheck;

import javax.annotation.Nullable;

public interface ICustomFramedDoubleBlock extends IFramedDoubleBlock {
    /**
     * @return ?.
     */
    @Override
    default DoubleBlockTopInteractionMode calculateTopInteractionMode(BlockState blockState) {
        return DoubleBlockTopInteractionMode.EITHER;
    }

    /**
     * @return ?.
     */
    @Override
    default SolidityCheck calculateSolidityCheck(BlockState blockState, Direction direction) {
        return SolidityCheck.NONE;
    }

    /**
     * @return camo getter from either first or second block for texture connections.
     */
    @Override
    default CamoGetter calculateCamoGetter(BlockState blockState, Direction dir1, @Nullable Direction dir2) {
        return CamoGetter.NONE;
    }
}
