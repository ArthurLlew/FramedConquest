package net.arthurllew.framedcr.registry;

import net.arthurllew.framedcr.FramedConquest;
import net.arthurllew.framedcr.block.entity.FramedConquestBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FramedConquestBlockEntities {
    /**
     * Deferred Register for block entities.
     */
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, FramedConquest.MODID);;

    /**
     * Framed CR block entity.
     */
    public static final Supplier<BlockEntityType<FramedConquestBlockEntity>> FRAMED_CONQUEST_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("framed_tile", () ->
                    BlockEntityType.Builder.of(FramedConquestBlockEntity::new,
                            FramedConquestBlocks.FRAMED_PILLAR.get()
                    ).build(null));
}
