package net.arthurllew.framedcr.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import xfacthd.framedblocks.common.block.IFramedDoubleBlock;
import xfacthd.framedblocks.common.data.doubleblock.CamoGetter;
import xfacthd.framedblocks.common.data.doubleblock.DoubleBlockTopInteractionMode;
import xfacthd.framedblocks.common.data.doubleblock.SolidityCheck;

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
     * @return ?.
     */
    @Override
    default CamoGetter calculateCamoGetter(BlockState blockState, Direction direction, @Nullable Direction direction1) {
        return CamoGetter.NONE;
    }
}
