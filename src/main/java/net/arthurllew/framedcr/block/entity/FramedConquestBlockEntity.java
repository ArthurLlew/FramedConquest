package net.arthurllew.framedcr.block.entity;

import net.arthurllew.framedcr.registry.FramedConquestBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblocks.api.block.blockentity.FramedBlockEntity;

/**
 * Custom block entity. Is mandatory, because Minecraft checks which blocks are connected with each block entity
 * instance, so the world data is not messed up.
 */
public class FramedConquestBlockEntity extends FramedBlockEntity {
    public FramedConquestBlockEntity(BlockPos pos, BlockState state) {
        super(FramedConquestBlockEntities.FRAMED_CONQUEST_BLOCK_ENTITY.get(), pos, state);
    }
}
