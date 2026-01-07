package net.arthurllew.framedcr.registry;

import net.arthurllew.framedcr.FramedConquest;
import net.arthurllew.framedcr.block.CustomFramedBlock;
import net.arthurllew.framedcr.block.FramedPillar;
import net.arthurllew.framedcr.block.entity.FramedConquestBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FramedConquestBlocks {
    /**
     * Deferred Register for blocks.
     */
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FramedConquest.MODID);

    /**
     * Framed CR pillar.
     */
    public static final DeferredBlock<FramedPillar> FRAMED_PILLAR = registerFramedBlock(
            "framed_pillar", FramedPillar::new);

    /**
     * Registers framed block and its item.
     * @param name block id.
     * @param block block supplier.
     * @return registered block.
     * @param <T> block child.
     */
    private static <T extends CustomFramedBlock> DeferredBlock<T> registerFramedBlock(String name, Supplier<T> block) {
        DeferredBlock<T> regBlock = BLOCKS.register(name, block);
        // Block item must be created in block class
        FramedConquestItems.ITEMS.register(name, () -> regBlock.get().createBlockItem());
        return regBlock;
    }
}
