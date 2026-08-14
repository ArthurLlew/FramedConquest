package net.arthurllew.framedcr.block.predicates;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.predicate.contex.ConTexMode;
import xfacthd.framedblocks.api.predicate.contex.ConnectionPredicate;

import javax.annotation.Nullable;

public class VerticalTextureConnectionPredicate implements ConnectionPredicate {
    /**
     * Class instance.
     */
    public static final VerticalTextureConnectionPredicate INSTANCE = new VerticalTextureConnectionPredicate();

    /**
     * Class has single instance.
     */
    private VerticalTextureConnectionPredicate(){}

    /**
     * @return whether the given state of the block this predicate belongs to can connect on
     * the given side at the given full-width edge.
     */
    public boolean canConnectFullEdge(BlockState state, Direction side, @Nullable Direction edge) {
        return side.getAxis() != Direction.Axis.Y || state.getValue(FramedProperties.SOLID);
    }

    /**
     * @return whether the given state of the block this predicate belongs to can connect on
     * the given side at the given edge in {@link ConTexMode#DETAILED}.
     */
    public boolean canConnectDetailed(BlockState state, Direction side, Direction edge) {
        return canConnectFullEdge(state, side, edge);
    }
}
