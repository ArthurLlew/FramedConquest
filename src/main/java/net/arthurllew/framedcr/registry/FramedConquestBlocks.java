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

    public static final DeferredBlock<FramedPillar> FRAMED_PILLAR = registerFramedBlock(
            "framed_pillar", FramedPillar::new);
    public static final DeferredBlock<FramedBalustrade> FRAMED_BALUSTRADE = registerBlock(
            "framed_balustrade", FramedBalustrade::new);
    public static final DeferredBlock<FramedArrowslit> FRAMED_ARROWSLIT = registerBlock(
            "framed_arrowslit", FramedArrowslit::new);
    public static final DeferredBlock<FramedSphere> FRAMED_SPHERE = registerBlock(
            "framed_sphere", FramedSphere::new);
    public static final DeferredBlock<FramedSmallArch> FRAMED_SMALL_ARCH = registerBlock(
            "framed_small_arch", FramedSmallArch::new);
    public static final DeferredBlock<FramedSmallArchHalf> FRAMED_SMALL_ARCH_HALF = registerBlock(
            "framed_small_arch_half", FramedSmallArchHalf::new);
    public static final DeferredBlock<FramedSmallWindow> FRAMED_SMALL_WINDOW = registerBlock(
            "framed_small_window", FramedSmallWindow::new);
    public static final DeferredBlock<FramedSmallWindowHalf> FRAMED_SMALL_WINDOW_HALF = registerBlock(
            "framed_small_window_half", FramedSmallWindowHalf::new);
    public static final DeferredBlock<FramedTwoMeterArch> FRAMED_TWO_METER_ARCH = registerBlock(
            "framed_two_meter_arch", FramedTwoMeterArch::new);
    public static final DeferredBlock<FramedTwoMeterArchHalf> FRAMED_TWO_METER_ARCH_HALF = registerBlock(
            "framed_two_meter_arch_half", FramedTwoMeterArchHalf::new);
    public static final DeferredBlock<FramedCornerVertical> FRAMED_CORNER_VERTICAL = registerFramedBlock(
            "framed_corner_vertical", FramedCornerVertical::new);
    public static final DeferredBlock<FramedQuarterHorizontal> FRAMED_QUARTER_HORIZONTAL = registerFramedBlock(
            "framed_quarter_horizontal", FramedQuarterHorizontal::new);
    public static final DeferredBlock<FramedQuarterVertical> FRAMED_QUARTER_VERTICAL = registerFramedBlock(
            "framed_quarter_vertical", FramedQuarterVertical::new);
    public static final DeferredBlock<FramedArch> FRAMED_GOTHIC_ARCH = registerBlock(
            "framed_gothic_arch", FramedArch::new);
    public static final DeferredBlock<FramedArch> FRAMED_GOTHIC_ARCH_SMOOTH = registerBlock(
            "framed_gothic_arch_smooth", FramedArch::new);
    public static final DeferredBlock<FramedArch> FRAMED_GOTHIC_BRICK_ARCH = registerBlock(
            "framed_gothic_brick_arch", FramedArch::new);
    public static final DeferredBlock<FramedArch> FRAMED_ROUND_ARCH = registerBlock(
            "framed_round_arch", FramedArch::new);
    public static final DeferredBlock<FramedArch> FRAMED_ROUND_ARCH_SMOOTH = registerBlock(
            "framed_round_arch_smooth", FramedArch::new);
    public static final DeferredBlock<FramedArch> FRAMED_ROUND_BRICK_ARCH = registerBlock(
            "framed_round_brick_arch", FramedArch::new);
    public static final DeferredBlock<FramedArch> FRAMED_SEGMENTAL_ARCH = registerBlock(
            "framed_segmental_arch", FramedArch::new);
    public static final DeferredBlock<FramedArch> FRAMED_SEGMENTAL_ARCH_SMOOTH = registerBlock(
            "framed_segmental_arch_smooth", FramedArch::new);
    public static final DeferredBlock<FramedArch> FRAMED_SEGMENTAL_BRICK_ARCH = registerBlock(
            "framed_segmental_brick_arch", FramedArch::new);
    public static final DeferredBlock<FramedArchFacade> FRAMED_GOTHIC_ARCH_FACADE = registerBlock(
            "framed_gothic_arch_facade", FramedArchFacade::new);
    public static final DeferredBlock<FramedArchFacade> FRAMED_ROUND_ARCH_FACADE = registerBlock(
            "framed_round_arch_facade", FramedArchFacade::new);
    public static final DeferredBlock<FramedArchFacade> FRAMED_SEGMENTAL_ARCH_FACADE = registerBlock(
            "framed_segmental_arch_facade", FramedArchFacade::new);
    public static final DeferredBlock<FramedArchFacade> FRAMED_GOTHIC_BRICK_ARCH_FACADE = registerBlock(
            "framed_gothic_brick_arch_facade", FramedArchFacade::new);
    public static final DeferredBlock<FramedArchFacade> FRAMED_ROUND_BRICK_ARCH_FACADE = registerBlock(
            "framed_round_brick_arch_facade", FramedArchFacade::new);
    public static final DeferredBlock<FramedArchFacade> FRAMED_SEGMENTAL_BRICK_ARCH_FACADE = registerBlock(
            "framed_segmental_brick_arch_facade", FramedArchFacade::new);
    public static final DeferredBlock<FramedRailing> FRAMED_RAILING_1 = registerBlock(
            "framed_railing_1", FramedRailing::new);
    public static final DeferredBlock<FramedRailing> FRAMED_RAILING_1_BEAM = registerBlock(
            "framed_railing_1_beam", FramedRailing::new);
    public static final DeferredBlock<FramedRailing> FRAMED_RAILING_1_SLOPE = registerBlock(
            "framed_railing_1_slope", FramedRailing::new);
    public static final DeferredBlock<FramedRailingCorner> FRAMED_RAILING_1_CORNER = registerBlock(
            "framed_railing_1_corner", FramedRailingCorner::new);

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
