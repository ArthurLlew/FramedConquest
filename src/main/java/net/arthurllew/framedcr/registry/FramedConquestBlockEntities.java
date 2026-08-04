package net.arthurllew.framedcr.registry;

import net.arthurllew.framedcr.FramedConquest;
import net.arthurllew.framedcr.block.ICustomFramedDoubleBlock;
import net.arthurllew.framedcr.block.entity.FramedConquestBlockEntity;
import net.arthurllew.framedcr.block.entity.FramedConquestDoubleBlockEntity;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@SuppressWarnings("DataFlowIssue")
public class FramedConquestBlockEntities {
    /**
     * Deferred Register for block entities.
     */
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, FramedConquest.MODID);

    /**
     * Framed CR block entity.
     */
    public static final Supplier<BlockEntityType<FramedConquestBlockEntity>> FRAMED_CONQUEST_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("framed_block", () ->
                    BlockEntityType.Builder.of(FramedConquestBlockEntity::new,
                            FramedConquestBlocks.BLOCKS.getEntries()
                                    .stream().map(Holder::value)
                                    .filter((block -> !(block instanceof ICustomFramedDoubleBlock)))
                                    .toArray(Block[]::new)
                    ).build(null));

    /**
     * Framed CR double (camo) block entity.
     */
    @SuppressWarnings("SuspiciousToArrayCall")
    public static final Supplier<BlockEntityType<FramedConquestDoubleBlockEntity>> FRAMED_CONQUEST_DOUBLE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("framed_double_block", () ->
                    BlockEntityType.Builder.of(FramedConquestDoubleBlockEntity::new,
                            FramedConquestBlocks.BLOCKS.getEntries()
                                    .stream().map(Holder::value)
                                    .filter((block -> block instanceof ICustomFramedDoubleBlock))
                                    .toArray(Block[]::new)
                    ).build(null));
}
