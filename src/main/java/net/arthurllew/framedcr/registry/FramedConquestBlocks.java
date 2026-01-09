package net.arthurllew.framedcr.registry;

import net.arthurllew.framedcr.FramedConquest;
import net.arthurllew.framedcr.block.*;
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
     * Framed CR arrowslit.
     */
    public static final DeferredBlock<FramedArrowslit> FRAMED_ARROWSLIT = registerBlock(
            "framed_arrowslit", FramedArrowslit::new);
    /**
     * Framed CR sphere.
     */
    public static final DeferredBlock<FramedSphere> FRAMED_SPHERE = registerBlock(
            "framed_sphere", FramedSphere::new);
    /**
     * Framed CR small arch.
     */
    public static final DeferredBlock<FramedSmallArch> FRAMED_SMALL_ARCH = registerBlock(
            "framed_small_arch", FramedSmallArch::new);
    /**
     * Framed CR small arch half.
     */
    public static final DeferredBlock<FramedSmallArchHalf> FRAMED_SMALL_ARCH_HALF = registerBlock(
            "framed_small_arch_half", FramedSmallArchHalf::new);
    /**
     * Framed CR small window.
     */
    public static final DeferredBlock<FramedSmallWindow> FRAMED_SMALL_WINDOW = registerBlock(
            "framed_small_window", FramedSmallWindow::new);
    /**
     * Framed CR small window half.
     */
    public static final DeferredBlock<FramedSmallWindowHalf> FRAMED_SMALL_WINDOW_HALF = registerBlock(
            "framed_small_window_half", FramedSmallWindowHalf::new);
    /**
     * Framed CR two meter arch.
     */
    public static final DeferredBlock<FramedTwoMeterArch> FRAMED_TWO_METER_ARCH = registerBlock(
            "framed_two_meter_arch", FramedTwoMeterArch::new);
    /**
     * Framed CR two meter arch half.
     */
    public static final DeferredBlock<FramedTwoMeterArchHalf> FRAMED_TWO_METER_ARCH_HALF = registerBlock(
            "framed_two_meter_arch_half", FramedTwoMeterArchHalf::new);

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
