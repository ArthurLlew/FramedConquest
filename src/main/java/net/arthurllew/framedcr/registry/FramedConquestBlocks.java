package net.arthurllew.framedcr.registry;

import net.arthurllew.framedcr.FramedConquest;
import net.arthurllew.framedcr.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.BiFunction;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class FramedConquestBlocks {
    /**
     * Deferred Register for blocks.
     */
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FramedConquest.MODID);

    public static final DeferredBlock<FramedPillar> FRAMED_PILLAR = registerLayeredBlock(
            "framed_pillar", FramedPillar::new);
    public static final DeferredBlock<FramedBalustrade> FRAMED_BALUSTRADE = registerBlock(
            "framed_balustrade", FramedBalustrade::new);
    public static final DeferredBlock<FramedArrowslit> FRAMED_ARROWSLIT = registerBlock(
            "framed_arrowslit", FramedArrowslit::new);
    public static final DeferredBlock<FramedSphere> FRAMED_SPHERE = registerBlock(
            "framed_sphere", FramedSphere::new);

    public static final DeferredBlock<FramedSmallArch> FRAMED_SMALL_ARCH = registerDoubleBlock(
            "framed_small_arch", FramedSmallArch::new,
            FramedSmallArch.Bottom::new, FramedSmallArch.Top::new,
            FramedSmallArch.Double::new);
    public static final DeferredBlock<FramedSmallArchHalf> FRAMED_SMALL_ARCH_HALF = registerDoubleBlock(
            "framed_small_arch_half", FramedSmallArchHalf::new,
            FramedSmallArchHalf.Bottom::new, FramedSmallArchHalf.Top::new,
            FramedSmallArchHalf.Double::new);
    public static final DeferredBlock<FramedTwoMeterArch> FRAMED_TWO_METER_ARCH = registerDoubleBlock(
            "framed_two_meter_arch", FramedTwoMeterArch::new,
            FramedTwoMeterArch.Bottom::new, FramedTwoMeterArch.Top::new,
            FramedTwoMeterArch.Double::new);
    public static final DeferredBlock<FramedTwoMeterArchHalf> FRAMED_TWO_METER_ARCH_HALF = registerDoubleBlock(
            "framed_two_meter_arch_half", FramedTwoMeterArchHalf::new,
            FramedTwoMeterArchHalf.Bottom::new, FramedTwoMeterArchHalf.Top::new,
            FramedTwoMeterArchHalf.Double::new);

    public static final DeferredBlock<FramedSmallWindow> FRAMED_SMALL_WINDOW = registerBlock(
            "framed_small_window", FramedSmallWindow::new);
    public static final DeferredBlock<FramedSmallWindowHalf> FRAMED_SMALL_WINDOW_HALF = registerBlock(
            "framed_small_window_half", FramedSmallWindowHalf::new);

    public static final DeferredBlock<FramedCornerVertical> FRAMED_CORNER_VERTICAL = registerLayeredBlock(
            "framed_corner_vertical", FramedCornerVertical::new);
    public static final DeferredBlock<FramedQuarterHorizontal> FRAMED_QUARTER_HORIZONTAL = registerLayeredBlock(
            "framed_quarter_horizontal", FramedQuarterHorizontal::new);
    public static final DeferredBlock<FramedQuarterVertical> FRAMED_QUARTER_VERTICAL = registerLayeredBlock(
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
    public static final DeferredBlock<FramedRailingSlope> FRAMED_RAILING_1_BEAM = registerBlock(
            "framed_railing_1_beam", FramedRailingSlope::new);
    public static final DeferredBlock<FramedRailingSlope> FRAMED_RAILING_1_SLOPE = registerBlock(
            "framed_railing_1_slope", FramedRailingSlope::new);
    public static final DeferredBlock<FramedRailingCorner> FRAMED_RAILING_1_CORNER = registerBlock(
            "framed_railing_1_corner", FramedRailingCorner::new);
    public static final DeferredBlock<FramedRailing> FRAMED_RAILING_2 = registerBlock(
            "framed_railing_2", FramedRailing::new);
    public static final DeferredBlock<FramedRailingSlope> FRAMED_RAILING_2_SLOPE = registerBlock(
            "framed_railing_2_slope", FramedRailingSlope::new);
    public static final DeferredBlock<FramedRailingCorner> FRAMED_RAILING_2_CORNER = registerBlock(
            "framed_railing_2_corner", FramedRailingCorner::new);
    public static final DeferredBlock<FramedRailing> FRAMED_RAILING_3 = registerBlock(
            "framed_railing_3", FramedRailing::new);
    public static final DeferredBlock<FramedRailingSlope> FRAMED_RAILING_3_SLOPE = registerBlock(
            "framed_railing_3_slope", FramedRailingSlope::new);
    public static final DeferredBlock<FramedRailingCorner> FRAMED_RAILING_3_CORNER = registerBlock(
            "framed_railing_3_corner", FramedRailingCorner::new);
    public static final DeferredBlock<FramedRailing> FRAMED_RAILING_4 = registerBlock(
            "framed_railing_4", FramedRailing::new);
    public static final DeferredBlock<FramedRailingSlope> FRAMED_RAILING_4_SLOPE = registerBlock(
            "framed_railing_4_slope", FramedRailingSlope::new);
    public static final DeferredBlock<FramedRailingCorner> FRAMED_RAILING_4_CORNER = registerBlock(
            "framed_railing_4_corner", FramedRailingCorner::new);

    public static final DeferredBlock<FramedStairs> FRAMED_STEPS_1 = registerBlock(
            "framed_steps_1", FramedStairs::new);
    public static final DeferredBlock<FramedStairs> FRAMED_STEPS_2 = registerBlock(
            "framed_steps_2", FramedStairs::new);
    public static final DeferredBlock<FramedStairs> FRAMED_STEPS_3 = registerBlock(
            "framed_steps_3", FramedStairs::new);
    public static final DeferredBlock<FramedStairs> FRAMED_STEPS_4 = registerBlock(
            "framed_steps_4", FramedStairs::new);
    public static final DeferredBlock<FramedStairs> FRAMED_STEPS_5 = registerBlock(
            "framed_steps_5", FramedStairs::new);
    public static final DeferredBlock<FramedStairs> FRAMED_STEPS_6 = registerBlock(
            "framed_steps_6", FramedStairs::new);
    public static final DeferredBlock<FramedStairs> FRAMED_STEPS_7 = registerBlock(
            "framed_steps_7", FramedStairs::new);
    public static final DeferredBlock<FramedStairs> FRAMED_STEPS_8 = registerBlock(
            "framed_steps_8", FramedStairs::new);

    public static final DeferredBlock<FramedCapital> FRAMED_CAPITAL_DORIC_DOWN = registerDoubleBlock(
            "framed_capital_doric_down", FramedCapital.DoricBottom::new, FramedCapital.DoricTop::new,
            FramedCapital.Doric::new);
    public static final DeferredBlock<FramedCapital> FRAMED_CAPITAL_DORIC_UP = registerDoubleBlock(
            "framed_capital_doric_up", FramedCapital.DoricBottom::new, FramedCapital.DoricTop::new,
            FramedCapital.Doric::new);

    public static final DeferredBlock<FramedStairs> FRAMED_CAPITAL_PLINTH_STAIRS = registerBlock(
            "framed_plinth_stairs", FramedStairs.Plinth::new);

    /**
     * Registers framed double block and its item.
     * @param name        block id
     * @param block       block supplier
     * @param blockBottom block bottom supplier
     * @param blockTop    block top supplier
     * @param blockDouble double block supplier
     * @return registered block
     * @param <T> block type
     */
    private static <T extends Block> DeferredBlock<T> registerDoubleBlock(String name, Supplier<T> block,
                                                                          Supplier<T> blockBottom,
                                                                          Supplier<T> blockTop,
                                                                          BiFunction<T, T, T> blockDouble) {
        // Register base block
        DeferredBlock<T> reg = registerBlock(name, block);
        // Register double block
        DeferredBlock<T> regDouble = registerDoubleBlock(name, blockBottom, blockTop, blockDouble);
        // Return registered block
        return reg;
    }

    /**
     * Registers framed double block and its item.
     * @param name        block id
     * @param blockBottom block bottom supplier
     * @param blockTop    block top supplier
     * @param blockDouble double block supplier
     * @return registered block
     * @param <T> block type
     */
    private static <T extends Block> DeferredBlock<T> registerDoubleBlock(String name,
                                                                          Supplier<T> blockBottom,
                                                                          Supplier<T> blockTop,
                                                                          BiFunction<T, T, T> blockDouble) {
        // Register double block parts
        DeferredBlock<T> regBottom = BLOCKS.register(name + "_bottom", blockBottom);
        DeferredBlock<T> regTop = BLOCKS.register(name + "_top", blockTop);
        // Register and return double block
        return registerBlock(name + "_double", () -> blockDouble.apply(regBottom.get(), regTop.get()));
    }

    /**
     * Registers block and its item.
     * @param name  block id
     * @param block block supplier
     * @return registered block
     * @param <T> block type
     */
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> reg = BLOCKS.register(name, block);
        // Default block item
        FramedConquestItems.ITEMS.register(name, () -> new BlockItem(reg.get(), new Item.Properties()));
        return reg;
    }

    /**
     * Registers block and its item.
     * @param name  block id
     * @param block block supplier
     * @return registered block
     * @param <T> block child
     */
    private static <T extends CustomFramedBlock> DeferredBlock<T> registerLayeredBlock(String name, Supplier<T> block) {
        DeferredBlock<T> regBlock = BLOCKS.register(name, block);
        // Block item is created in block class
        FramedConquestItems.ITEMS.register(name, () -> regBlock.get().createBlockItem());
        return regBlock;
    }
}
