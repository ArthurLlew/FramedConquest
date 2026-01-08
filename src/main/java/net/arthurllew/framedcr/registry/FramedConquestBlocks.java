package net.arthurllew.framedcr.registry;

import net.arthurllew.framedcr.FramedConquest;
import net.arthurllew.framedcr.block.CustomFramedBlock;
import net.arthurllew.framedcr.block.FramedBalustrade;
import net.arthurllew.framedcr.block.FramedPillar;
import net.arthurllew.framedcr.block.FramedTwoMeterArch;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
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
     * Framed CR balustrade.
     */
    public static final DeferredBlock<FramedBalustrade> FRAMED_BALUSTRADE = registerBlock(
            "framed_balustrade", FramedBalustrade::new);
    /**
     * Framed CR two meter arch.
     */
    public static final DeferredBlock<FramedTwoMeterArch> FRAMED_TWO_METER_ARCH = registerBlock(
            "framed_two_meter_arch", FramedTwoMeterArch::new);

    /**
     * Registers block and its item.
     * @param name block id.
     * @param block block supplier.
     * @return registered block.
     * @param <T> block type.
     */
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> reg = BLOCKS.register(name, block);
        FramedConquestItems.ITEMS.register(name, () -> new BlockItem(reg.get(), new Item.Properties()));
        return reg;
    }

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
