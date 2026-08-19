package net.arthurllew.framedcr.registry;

import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import net.arthurllew.framedcr.FramedConquest;
import net.arthurllew.framedcr.block.*;
import net.arthurllew.framedcr.block.family.ConnectingCapital;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import xfacthd.framedblocks.common.data.doubleblock.CamoGetter;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class FramedConquestBlocks {
    /**
     * Deferred Register for blocks.
     */
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FramedConquest.MODID);

    public static final DeferredBlock<FramedPillar> FRAMED_PILLAR = registerBlock(
            "framed_pillar", () -> new FramedPillar("_1"));
    public static final DeferredBlock<FramedPillarSocket> FRAMED_PILLAR_SOCKET = registerBlock(
            "framed_pillar_socket", FramedPillarSocket::new);
    public static final DeferredBlock<FramedBalustrade> FRAMED_BALUSTRADE = registerBlock(
            "framed_balustrade", FramedBalustrade::new);
    public static final DeferredBlock<FramedArrowslit> FRAMED_ARROWSLIT = registerBlock(
            "framed_arrowslit", FramedArrowslit::new);
    public static final DeferredBlock<FramedSphere> FRAMED_SPHERE = registerBlock(
            "framed_sphere", FramedSphere::new);

    public static final DeferredBlock<FramedSmallArch> FRAMED_SMALL_ARCH = registerBlockWithDouble(
            "framed_small_arch", FramedSmallArch::new,
            FramedSmallArch.Bottom::new, FramedSmallArch.Top::new,
            FramedSmallArch.Double::new);
    public static final DeferredBlock<FramedSmallArchHalf> FRAMED_SMALL_ARCH_HALF = registerBlockWithDouble(
            "framed_small_arch_half", FramedSmallArchHalf::new,
            FramedSmallArchHalf.Bottom::new, FramedSmallArchHalf.Top::new,
            FramedSmallArchHalf.Double::new);
    public static final DeferredBlock<FramedTwoMeterArch> FRAMED_TWO_METER_ARCH = registerBlockWithDouble(
            "framed_two_meter_arch", FramedTwoMeterArch::new,
            FramedTwoMeterArch.Bottom::new, FramedTwoMeterArch.Top::new,
            FramedTwoMeterArch.Double::new);
    public static final DeferredBlock<FramedTwoMeterArchHalf> FRAMED_TWO_METER_ARCH_HALF = registerBlockWithDouble(
            "framed_two_meter_arch_half", FramedTwoMeterArchHalf::new,
            FramedTwoMeterArchHalf.Bottom::new, FramedTwoMeterArchHalf.Top::new,
            FramedTwoMeterArchHalf.Double::new);

    public static final DeferredBlock<FramedSmallWindow> FRAMED_SMALL_WINDOW = registerBlock(
            "framed_small_window", FramedSmallWindow::new);
    public static final DeferredBlock<FramedSmallWindowHalf> FRAMED_SMALL_WINDOW_HALF = registerBlock(
            "framed_small_window_half", FramedSmallWindowHalf::new);

    public static final DeferredBlock<FramedCornerVertical> FRAMED_CORNER_VERTICAL = registerBlock(
            "framed_corner_vertical", FramedCornerVertical::new);
    public static final DeferredBlock<FramedQuarterHorizontal> FRAMED_QUARTER_HORIZONTAL = registerBlock(
            "framed_quarter_horizontal", FramedQuarterHorizontal::new);
    public static final DeferredBlock<FramedQuarterVertical> FRAMED_QUARTER_VERTICAL = registerBlock(
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
            CamoGetter.SECOND, FramedCapital.Doric::new);
    public static final DeferredBlock<FramedWall> FRAMED_CAPITAL_DORIC_DOWN_WALL = registerDoubleBlock(
            "framed_capital_doric_down_wall", FramedWall.Bottom::new, FramedWall.Top::new,
            CamoGetter.SECOND, FramedWall.Double::new);
    public static final DeferredBlock<FramedPillar> FRAMED_CAPITAL_DORIC_DOWN_PILLAR = registerDoubleBlock(
            "framed_capital_doric_down_pillar", FramedPillar.Bottom::new, FramedPillar.Top::new,
            CamoGetter.SECOND, FramedPillar.Double::new);
    public static final DeferredBlock<FramedCapitalSlabVerticalConnecting> FRAMED_CAPITAL_DORIC_DOWN_VERTICAL_SLAB = registerConnectingDoubleBlock(
            "framed_capital_doric_down_vertical_slab", FramedCapitalSlabVerticalConnecting.Bottom::new, FramedCapitalSlabVerticalConnecting.Top::new,
            CamoGetter.SECOND, ConnectingCapital.CAPITAL_DORIC_DOWN, FramedCapitalSlabVerticalConnecting.Double::new);
    public static final DeferredBlock<FramedCapitalCornerVertical> FRAMED_CAPITAL_DORIC_DOWN_VERTICAL_CORNER = registerDoubleBlock(
            "framed_capital_doric_down_vertical_corner", FramedCapitalCornerVertical.Bottom::new, FramedCapitalCornerVertical.Top::new,
            CamoGetter.SECOND, FramedCapitalCornerVertical.Double::new);
    public static final DeferredBlock<FramedCapitalQuarterVerticalConnecting> FRAMED_CAPITAL_DORIC_DOWN_VERTICAL_QUARTER = registerConnectingDoubleBlock(
            "framed_capital_doric_down_vertical_quarter", FramedCapitalQuarterVerticalConnecting.Bottom::new, FramedCapitalQuarterVerticalConnecting.Top::new,
            CamoGetter.SECOND, ConnectingCapital.CAPITAL_DORIC_DOWN, FramedCapitalQuarterVerticalConnecting.Double::new);
    static {
        ConnectingCapital.bind(ConnectingCapital.CAPITAL_DORIC_DOWN,
                FRAMED_CAPITAL_DORIC_DOWN,
                FRAMED_CAPITAL_DORIC_DOWN_VERTICAL_CORNER,
                FRAMED_CAPITAL_DORIC_DOWN_VERTICAL_SLAB,
                FRAMED_CAPITAL_DORIC_DOWN_VERTICAL_QUARTER);
    }

    public static final DeferredBlock<FramedCapital> FRAMED_CAPITAL_DORIC_UP = registerDoubleBlock(
            "framed_capital_doric_up", FramedCapital.DoricBottom::new, FramedCapital.DoricTop::new,
            CamoGetter.FIRST, FramedCapital.Doric::new);
    public static final DeferredBlock<FramedWall> FRAMED_CAPITAL_DORIC_UP_WALL = registerDoubleBlock(
            "framed_capital_doric_up_wall", FramedWall.Bottom::new, FramedWall.Top::new,
            CamoGetter.FIRST, FramedWall.Double::new);
    public static final DeferredBlock<FramedPillar> FRAMED_CAPITAL_DORIC_UP_PILLAR = registerDoubleBlock(
            "framed_capital_doric_up_pillar", FramedPillar.Bottom::new, FramedPillar.Top::new,
            CamoGetter.FIRST, FramedPillar.Double::new);
    public static final DeferredBlock<FramedCapitalSlabVerticalConnecting> FRAMED_CAPITAL_DORIC_UP_VERTICAL_SLAB = registerConnectingDoubleBlock(
            "framed_capital_doric_up_vertical_slab", FramedCapitalSlabVerticalConnecting.Bottom::new, FramedCapitalSlabVerticalConnecting.Top::new,
            CamoGetter.FIRST, ConnectingCapital.CAPITAL_DORIC_UP, FramedCapitalSlabVerticalConnecting.Double::new);
    public static final DeferredBlock<FramedCapitalCornerVertical> FRAMED_CAPITAL_DORIC_UP_VERTICAL_CORNER = registerDoubleBlock(
            "framed_capital_doric_up_vertical_corner", FramedCapitalCornerVertical.Bottom::new, FramedCapitalCornerVertical.Top::new,
            CamoGetter.FIRST, FramedCapitalCornerVertical.Double::new);
    public static final DeferredBlock<FramedCapitalQuarterVerticalConnecting> FRAMED_CAPITAL_DORIC_UP_VERTICAL_QUARTER = registerConnectingDoubleBlock(
            "framed_capital_doric_up_vertical_quarter", FramedCapitalQuarterVerticalConnecting.Bottom::new, FramedCapitalQuarterVerticalConnecting.Top::new,
            CamoGetter.FIRST, ConnectingCapital.CAPITAL_DORIC_UP, FramedCapitalQuarterVerticalConnecting.Double::new);
    static {
        ConnectingCapital.bind(ConnectingCapital.CAPITAL_DORIC_UP,
                FRAMED_CAPITAL_DORIC_UP,
                FRAMED_CAPITAL_DORIC_UP_VERTICAL_CORNER,
                FRAMED_CAPITAL_DORIC_UP_VERTICAL_SLAB,
                FRAMED_CAPITAL_DORIC_UP_VERTICAL_QUARTER);
    }

    public static final DeferredBlock<FramedBalustrade> FRAMED_CAPITAL_IONIAN = registerBlock(
            "framed_capital_ionian", FramedBalustrade::new);
    public static final DeferredBlock<FramedWall> FRAMED_CAPITAL_IONIAN_WALL = registerBlock(
            "framed_capital_ionian_wall", FramedWall::new);
    public static final DeferredBlock<FramedPillar> FRAMED_CAPITAL_IONIAN_PILLAR = registerBlock(
            "framed_capital_ionian_pillar", FramedPillar.WithAxis::new);
    public static final DeferredBlock<FramedCapitalSlabVertical> FRAMED_CAPITAL_IONIAN_VERTICAL_SLAB = registerBlock(
            "framed_capital_ionian_vertical_slab", FramedCapitalSlabVertical::new);
    public static final DeferredBlock<FramedCapitalCornerVertical> FRAMED_CAPITAL_IONIAN_VERTICAL_CORNER = registerBlock(
            "framed_capital_ionian_vertical_corner", FramedCapitalCornerVertical::new);
    public static final DeferredBlock<FramedCapitalQuarterVertical> FRAMED_CAPITAL_IONIAN_VERTICAL_QUARTER = registerBlock(
            "framed_capital_ionian_vertical_quarter", FramedCapitalQuarterVertical::new);

    public static final DeferredBlock<FramedCapital> FRAMED_CAPITAL_CORINTHIAN = registerConnectingBlock(
            "framed_capital_corinthian", FramedCapital.Connected::new,
            ConnectingCapital.CAPITAL_CORINTHIAN);
    public static final DeferredBlock<FramedWall> FRAMED_CAPITAL_CORINTHIAN_WALL = registerBlock(
            "framed_capital_corinthian_wall", FramedWall::new);
    public static final DeferredBlock<FramedPillar> FRAMED_CAPITAL_CORINTHIAN_PILLAR = registerBlock(
            "framed_capital_corinthian_pillar", () -> new FramedPillar("_2"));
    public static final DeferredBlock<FramedCapitalSlabVerticalConnecting> FRAMED_CAPITAL_CORINTHIAN_VERTICAL_SLAB = registerConnectingBlock(
            "framed_capital_corinthian_vertical_slab", FramedCapitalSlabVerticalConnecting::new,
            ConnectingCapital.CAPITAL_CORINTHIAN);
    public static final DeferredBlock<FramedCapitalCornerVertical> FRAMED_CAPITAL_CORINTHIAN_VERTICAL_CORNER = registerBlock(
            "framed_capital_corinthian_vertical_corner", FramedCapitalCornerVertical::new);
    public static final DeferredBlock<FramedCapitalQuarterVerticalConnecting> FRAMED_CAPITAL_CORINTHIAN_VERTICAL_QUARTER = registerConnectingBlock(
            "framed_capital_corinthian_vertical_quarter", FramedCapitalQuarterVerticalConnecting::new,
            ConnectingCapital.CAPITAL_CORINTHIAN);
    static {
        ConnectingCapital.bind(ConnectingCapital.CAPITAL_CORINTHIAN,
                FRAMED_CAPITAL_CORINTHIAN,
                FRAMED_CAPITAL_CORINTHIAN_VERTICAL_CORNER,
                FRAMED_CAPITAL_CORINTHIAN_VERTICAL_SLAB,
                FRAMED_CAPITAL_CORINTHIAN_VERTICAL_QUARTER);
    }

    public static final DeferredBlock<FramedCapital> FRAMED_CORNICE = registerConnectingBlock(
            "framed_cornice", FramedCapital.Connected::new,
            ConnectingCapital.CORNICE);
    public static final DeferredBlock<FramedWall> FRAMED_CORNICE_WALL = registerBlock(
            "framed_cornice_wall", FramedWall::new);
    public static final DeferredBlock<FramedPillar> FRAMED_CORNICE_PILLAR = registerBlock(
            "framed_cornice_pillar", () -> new FramedPillar("_2"));
    public static final DeferredBlock<FramedCapitalSlabVerticalConnecting> FRAMED_CORNICE_VERTICAL_SLAB = registerConnectingBlock(
            "framed_cornice_vertical_slab", FramedCapitalSlabVerticalConnecting::new,
            ConnectingCapital.CORNICE);
    public static final DeferredBlock<FramedCapitalCornerVertical> FRAMED_CORNICE_VERTICAL_CORNER = registerBlock(
            "framed_cornice_vertical_corner", FramedCapitalCornerVertical::new);
    public static final DeferredBlock<FramedCapitalQuarterVerticalConnecting> FRAMED_CORNICE_VERTICAL_QUARTER = registerConnectingBlock(
            "framed_cornice_vertical_quarter", FramedCapitalQuarterVerticalConnecting::new,
            ConnectingCapital.CORNICE);
    static {
        ConnectingCapital.bind(ConnectingCapital.CORNICE,
                FRAMED_CORNICE,
                FRAMED_CORNICE_VERTICAL_CORNER,
                FRAMED_CORNICE_VERTICAL_SLAB,
                FRAMED_CORNICE_VERTICAL_QUARTER);
    }

    public static final DeferredBlock<FramedCapital> FRAMED_PLINTH = registerConnectingBlock(
            "framed_plinth", FramedCapital.Connected::new,
            ConnectingCapital.PLINTH);
    public static final DeferredBlock<FramedWall> FRAMED_PLINTH_WALL = registerBlock(
            "framed_plinth_wall", FramedWall::new);
    public static final DeferredBlock<FramedCapital> FRAMED_PLINTH_SLAB_BOTTOM = registerConnectingBlock(
            "framed_plinth_slab_bottom", FramedCapital.ConnectedSlab::new,
            ConnectingCapital.PLINTH);
    public static final DeferredBlock<FramedCapital> FRAMED_PLINTH_SLAB_TOP = registerConnectingBlock(
            "framed_plinth_slab_top", FramedCapital.ConnectedSlab::new,
            ConnectingCapital.PLINTH);
    public static final DeferredBlock<FramedStairs> FRAMED_PLINTH_STAIRS = registerBlock(
            "framed_plinth_stairs", FramedStairs.Plinth::new);
    public static final DeferredBlock<FramedPillar> FRAMED_PLINTH_PILLAR = registerBlock(
            "framed_plinth_pillar", () -> new FramedPillar("_2"));
    public static final DeferredBlock<FramedCapitalSlabVerticalConnecting> FRAMED_PLINTH_VERTICAL_SLAB = registerConnectingBlock(
            "framed_plinth_vertical_slab", FramedCapitalSlabVerticalConnecting::new,
            ConnectingCapital.PLINTH);
    public static final DeferredBlock<FramedCapitalCornerVertical> FRAMED_PLINTH_VERTICAL_CORNER = registerBlock(
            "framed_plinth_vertical_corner", FramedCapitalCornerVertical::new);
    public static final DeferredBlock<FramedCapitalQuarterVerticalConnecting> FRAMED_PLINTH_VERTICAL_QUARTER = registerConnectingBlock(
            "framed_plinth_vertical_quarter", FramedCapitalQuarterVerticalConnecting::new,
            ConnectingCapital.PLINTH);
    static {
        ConnectingCapital.bind(ConnectingCapital.PLINTH,
                FRAMED_PLINTH,
                FRAMED_PLINTH_VERTICAL_CORNER,
                FRAMED_PLINTH_VERTICAL_SLAB,
                FRAMED_PLINTH_VERTICAL_QUARTER);
    }

    /**
     * Registers framed connecting double block and its item.
     * @param name        block id
     * @param blockBottom block bottom supplier
     * @param blockTop    block top supplier
     * @param camoGetter  double block camo resolver
     * @param blockDouble double block supplier
     * @return registered block
     * @param <T> block type
     */
    private static <T extends CustomFramedBlock, C extends CamoGetter, F extends ConnectingCapital>
    DeferredBlock<T> registerConnectingDoubleBlock(String name,
                                                   Function<F, T> blockBottom,
                                                   Function<F, T> blockTop,
                                                   C camoGetter,
                                                   F blockFamily,
                                                   Function4<T, T, C, F, T> blockDouble) {
        // Register double block parts
        DeferredBlock<T> regBottom = BLOCKS.register(name + "_bottom", () -> blockBottom.apply(blockFamily));
        DeferredBlock<T> regTop = BLOCKS.register(name + "_top", () -> blockTop.apply(blockFamily));
        // Register and return double block
        return registerBlock(name + "_double",
                () -> blockDouble.apply(regBottom.get(), regTop.get(), camoGetter, blockFamily));
    }

    /**
     * Registers framed double block and its item.
     * @param name        block id
     * @param blockBottom block bottom supplier
     * @param blockTop    block top supplier
     * @param camoGetter  double block camo resolver
     * @param blockDouble double block supplier
     * @return registered block
     * @param <T> block type
     */
    private static <T extends CustomFramedBlock, C extends CamoGetter>
    DeferredBlock<T> registerDoubleBlock(String name,
                                         Supplier<T> blockBottom,
                                         Supplier<T> blockTop,
                                         C camoGetter,
                                         Function3<T, T, C, T> blockDouble) {
        // Register double block parts
        DeferredBlock<T> regBottom = BLOCKS.register(name + "_bottom", blockBottom);
        DeferredBlock<T> regTop = BLOCKS.register(name + "_top", blockTop);
        // Register and return double block
        return registerBlock(name + "_double", () -> blockDouble.apply(regBottom.get(), regTop.get(), camoGetter));
    }

    /**
     * Registers framed block, its double variant and their items.
     * @param name        block id
     * @param block       block supplier
     * @param blockBottom block bottom supplier
     * @param blockTop    block top supplier
     * @param blockDouble double block supplier
     * @return registered block
     * @param <T> block type
     */
    private static <T extends CustomFramedBlock>
    DeferredBlock<T> registerBlockWithDouble(String name,
                                             Supplier<T> block,
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
    private static <T extends CustomFramedBlock>
    DeferredBlock<T> registerDoubleBlock(String name,
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
     * Registers connecting block and its item.
     * @param name  block id
     * @param block block supplier
     * @return registered block
     * @param <T> block type
     */
    private static <T extends CustomFramedBlock, F extends ConnectingCapital>
    DeferredBlock<T> registerConnectingBlock(String name, Function<F, T> block, F blockFamily) {
        DeferredBlock<T> reg = BLOCKS.register(name, () -> block.apply(blockFamily));
        // Get block item from the supplier inside class
        FramedConquestItems.ITEMS.register(name, () -> reg.get().createBlockItem());
        return reg;
    }

    /**
     * Registers block and its item.
     * @param name  block id
     * @param block block supplier
     * @return registered block
     * @param <T> block type
     */
    private static <T extends CustomFramedBlock>
    DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> reg = BLOCKS.register(name, block);
        // Get block item from the supplier inside class
        FramedConquestItems.ITEMS.register(name, () -> reg.get().createBlockItem());
        return reg;
    }
}
