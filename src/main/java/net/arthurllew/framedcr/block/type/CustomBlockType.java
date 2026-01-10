package net.arthurllew.framedcr.block.type;

import com.google.common.collect.ImmutableList;
import net.arthurllew.framedcr.block.*;
import net.arthurllew.framedcr.datagen.ModBlockLootTables;
import net.arthurllew.framedcr.datagen.ModItemModelProvider;
import net.arthurllew.framedcr.datagen.ModRecipeProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.loading.FMLEnvironment;
import xfacthd.framedblocks.api.predicate.contex.ConTexMode;
import xfacthd.framedblocks.api.predicate.contex.ConnectionPredicate;
import xfacthd.framedblocks.api.predicate.cull.SideSkipPredicate;
import xfacthd.framedblocks.api.predicate.fullface.FullFacePredicate;
import xfacthd.framedblocks.api.shapes.ReloadableShapeProvider;
import xfacthd.framedblocks.api.shapes.ShapeGenerator;
import xfacthd.framedblocks.api.shapes.ShapeProvider;
import xfacthd.framedblocks.api.type.IBlockType;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.shapes.SplitShapeGenerator;
import xfacthd.framedblocks.common.data.facepreds.FullFacePredicates;
import xfacthd.framedblocks.common.data.shapes.stairs.standard.HalfStairsShapes;
import xfacthd.framedblocks.common.data.skippreds.SideSkipPredicates;
import xfacthd.framedblocks.common.data.conpreds.ConnectionPredicates;
import xfacthd.framedblocks.common.data.skippreds.stairs.StairsSkipPredicate;

import java.util.Locale;
import java.util.Objects;

/**
 * Copies behavior and logic of {@link BlockType}, {@link FullFacePredicates}, {@link SideSkipPredicates} and
 * {@link ConnectionPredicates}.
 */
public enum CustomBlockType implements IBlockType {
    FRAMED_PILLAR(true, false, false, true, true,
            true, false, false,
            ConTexMode.FULL_FACE,
            FramedPillar::generateShapes,
            FramedPillar::fullFacePredicate,
            SideSkipPredicate.FALSE,
            ConnectionPredicate.FULL_EDGE,
            "_1", 3, true),
    FRAMED_BALUSTRADE(true, false, false, true, true,
            true, false, false,
            ConTexMode.FULL_FACE,
            FramedBalustrade::generateShapes,
            FullFacePredicate.FALSE,
            SideSkipPredicate.FALSE,
            ConnectionPredicate.FULL_EDGE,
            "_y"),
    FRAMED_ARROWSLIT(true, false, false, true, true,
            true, false, false,
            ConTexMode.FULL_FACE,
            FramedArrowslit::generateShapes,
            FullFacePredicate.FALSE,
            SideSkipPredicate.FALSE,
            ConnectionPredicate.FULL_EDGE),
    FRAMED_SPHERE(true, false, false, true, true,
            true, false, false,
            ConTexMode.FULL_FACE,
            FramedSphere::generateShapes,
            FullFacePredicate.FALSE,
            SideSkipPredicate.FALSE,
            ConnectionPredicate.FULL_EDGE,
            "_dragonegg"),
    FRAMED_SMALL_ARCH(true, false, false, true, true,
            true, false, false,
            ConTexMode.FULL_FACE,
            FramedSmallArch::generateShapes,
            FullFacePredicate.FALSE,
            SideSkipPredicate.FALSE,
            ConnectionPredicate.FULL_EDGE),
    FRAMED_SMALL_ARCH_HALF(true, false, false, true, true,
            true, false, false,
            ConTexMode.FULL_FACE,
            FramedSmallArchHalf::generateShapes,
            FullFacePredicate.FALSE,
            SideSkipPredicate.FALSE,
            ConnectionPredicate.FULL_EDGE),
    FRAMED_SMALL_WINDOW(true, false, false, true, true,
            true, false, false,
            ConTexMode.FULL_FACE,
            FramedSmallWindow::generateShapes,
            FullFacePredicate.FALSE,
            SideSkipPredicate.FALSE,
            ConnectionPredicate.FULL_EDGE),
    FRAMED_SMALL_WINDOW_HALF(true, false, false, true, true,
            true, false, false,
            ConTexMode.FULL_FACE,
            FramedSmallWindowHalf::generateShapes,
            FullFacePredicate.FALSE,
            SideSkipPredicate.FALSE,
            ConnectionPredicate.FULL_EDGE),
    FRAMED_TWO_METER_ARCH(true, false, false, true, true,
            true, false, false,
            ConTexMode.FULL_FACE,
            FramedTwoMeterArch::generateShapes,
            FramedTwoMeterArch::fullFacePredicate,
            new StairsSkipPredicate(),
            ConnectionPredicate.FULL_EDGE),
    FRAMED_TWO_METER_ARCH_HALF(true, false, false, true, true,
            true, false, false,
            ConTexMode.FULL_FACE,
            HalfStairsShapes::generate,
            FullFacePredicate.FALSE,
            SideSkipPredicate.FALSE,
            ConnectionPredicate.FULL_EDGE,
            "_right"),
    FRAMED_CORNER_VERTICAL(true, false, false, true, true,
            true, false, false,
            ConTexMode.FULL_FACE,
            FramedCornerVertical::generateShapes,
            FullFacePredicate.FALSE,
            SideSkipPredicate.FALSE,
            ConnectionPredicate.FULL_EDGE,
            "_1"),
    FRAMED_QUARTER_HORIZONTAL(true, false, false, true, true,
            true, false, false,
            ConTexMode.FULL_FACE,
            FramedQuarterHorizontal::generateShapes,
            FullFacePredicate.FALSE,
            SideSkipPredicate.FALSE,
            ConnectionPredicate.FULL_EDGE,
            "_2"),
    FRAMED_QUARTER_VERTICAL(true, false, false, true, true,
            true, false, false,
            ConTexMode.FULL_FACE,
            FramedQuarterVertical::generateShapes,
            FullFacePredicate.FALSE,
            SideSkipPredicate.FALSE,
            ConnectionPredicate.FULL_EDGE,
            "_1"),
    FRAMED_ARCH(true, false, false, true, true,
            true, false, false,
            ConTexMode.FULL_FACE,
            FramedArch::generateShapes,
            FullFacePredicate.FALSE,
            SideSkipPredicate.FALSE,
            ConnectionPredicate.FULL_EDGE,
            "_1"),
    FRAMED_ARCH_FACADE(true, false, false, true, true,
            true, false, false,
            ConTexMode.FULL_FACE,
            FramedArchFacade::generateShapes,
            FullFacePredicate.FALSE,
            SideSkipPredicate.FALSE,
            ConnectionPredicate.FULL_EDGE,
            "_1");

    /**
     * Unique ID.
     */
    private final String name;
    /**
     * Whether block can create ambient occlusion.
     */
    private final boolean canOcclude;
    private final boolean specialHitbox;
    private final boolean specialTile;
    /**
     * Whether block is waterloggable.
     */
    private final boolean waterloggable;
    /**
     * Whether block has block item.
     */
    private final boolean blockItem;
    private final boolean allowIntangible;
    /**
     * Whether block occupies two blocks (like doors).
     */
    private final boolean doubleBlock;
    /**
     * Whether block can be locked with a key.
     */
    private final boolean lockable;
    /**
     * Whether block supports connected textures.
     */
    private final boolean supportsCT;
    private final ConTexMode minCTMode;
    /**
     * Block shape generator.
     */
    private final ShapeGenerator shapeGen;
    private final boolean separateOcclusionShapes;
    /**
     * Predicate responsible for calculating whether a block should hide neighboring block faces.
     */
    private final FullFacePredicate fullFacePredicate;
    private final SideSkipPredicate sideSkipPredicate;
    private final ConnectionPredicate connectionPredicates;
    /**
     * Block model variation ending to be used by item model (see {@link ModItemModelProvider}).
     */
    private final String modelVariantForItem;
    /**
     * How many blocks will be crafted with stonecutting recipe (see {@link ModRecipeProvider}).
     */
    private final int craftingCount;
    /**
     * Determines block loot table (see {@link ModBlockLootTables}).
     */
    private final boolean hasSpecialLootTable;

    CustomBlockType(boolean canOcclude, boolean specialHitbox, boolean specialTile, boolean waterloggable,
                    boolean blockItem, boolean allowIntangible, boolean doubleBlock, boolean lockable,
                    ConTexMode minCTMode, ShapeGenerator shapeGen,
                    FullFacePredicate fullFacePredicate,
                    SideSkipPredicate sideSkipPredicate,
                    ConnectionPredicate connectionPredicates) {
        this(canOcclude, specialHitbox, specialTile, waterloggable, blockItem, allowIntangible, doubleBlock, lockable,
                minCTMode, shapeGen, fullFacePredicate, sideSkipPredicate, connectionPredicates,
                "", 1, false);
    }

    CustomBlockType(boolean canOcclude, boolean specialHitbox, boolean specialTile, boolean waterloggable,
                    boolean blockItem, boolean allowIntangible, boolean doubleBlock, boolean lockable,
                    ConTexMode minCTMode, ShapeGenerator shapeGen,
                    FullFacePredicate fullFacePredicate,
                    SideSkipPredicate sideSkipPredicate,
                    ConnectionPredicate connectionPredicates,
                    String modelVariantForItem) {
        this(canOcclude, specialHitbox, specialTile, waterloggable, blockItem, allowIntangible, doubleBlock, lockable,
                minCTMode, shapeGen, fullFacePredicate, sideSkipPredicate, connectionPredicates,
                modelVariantForItem, 1, false);
    }

    CustomBlockType(boolean canOcclude, boolean specialHitbox, boolean specialTile, boolean waterloggable,
                    boolean blockItem, boolean allowIntangible, boolean doubleBlock, boolean lockable,
                    ConTexMode minCTMode, ShapeGenerator shapeGen,
                    FullFacePredicate fullFacePredicate,
                    SideSkipPredicate sideSkipPredicate,
                    ConnectionPredicate connectionPredicates,
                    String modelVariantForItem, int craftingCount, boolean hasSpecialLootTable) {
        this.name = this.toString().toLowerCase(Locale.ROOT);
        this.canOcclude = canOcclude;
        this.specialHitbox = specialHitbox;
        this.specialTile = specialTile;
        this.waterloggable = waterloggable;
        this.blockItem = blockItem;
        this.allowIntangible = allowIntangible;
        this.doubleBlock = doubleBlock;
        this.lockable = lockable;
        this.supportsCT = minCTMode != null;
        this.minCTMode = Objects.requireNonNullElse(minCTMode, ConTexMode.NONE);
        this.shapeGen = shapeGen;
        this.separateOcclusionShapes = shapeGen instanceof SplitShapeGenerator;

        this.fullFacePredicate = fullFacePredicate;
        this.sideSkipPredicate = sideSkipPredicate;
        this.connectionPredicates = connectionPredicates;

        this.modelVariantForItem = modelVariantForItem;
        this.craftingCount = craftingCount;
        this.hasSpecialLootTable = hasSpecialLootTable;
    }

    public String modelVariantForItem() {
        return this.modelVariantForItem;
    }

    public int craftingCount() {
        return this.craftingCount;
    }

    public boolean hasSpecialLootTable() {
        return this.hasSpecialLootTable;
    }

    @Override
    public boolean canOccludeWithSolidCamo() {
        return this.canOcclude;
    }

    @Override
    public boolean hasSpecialHitbox() {
        return this.specialHitbox;
    }

    @Override
    public FullFacePredicate getFullFacePredicate() {
        return fullFacePredicate;
    }

    @Override
    public SideSkipPredicate getSideSkipPredicate() {
        return sideSkipPredicate;
    }

    @Override
    public ConnectionPredicate getConnectionPredicate() {
        return connectionPredicates;
    }

    @Override
    public ShapeProvider generateShapes(ImmutableList<BlockState> states) {
        return !FMLEnvironment.production ? new ReloadableShapeProvider(this.shapeGen, states) : this.shapeGen.generate(states);
    }

    @Override
    public ShapeProvider generateOcclusionShapes(ImmutableList<BlockState> states, ShapeProvider shapes) {
        if (this.separateOcclusionShapes) {
            SplitShapeGenerator splitShapeGen = (SplitShapeGenerator)this.shapeGen;
            if (!FMLEnvironment.production) {
                Objects.requireNonNull(splitShapeGen);
                return new ReloadableShapeProvider(splitShapeGen::generateOcclusionShapes, states);
            } else {
                return splitShapeGen.generateOcclusionShapes(states);
            }
        } else {
            return shapes;
        }
    }

    @Override
    public boolean hasSpecialTile() {
        return this.specialTile;
    }

    @Override
    public boolean hasBlockItem() {
        return this.blockItem;
    }

    @Override
    public boolean supportsWaterLogging() {
        return this.waterloggable;
    }

    @Override
    public boolean supportsConnectedTextures() {
        return this.supportsCT;
    }

    @Override
    public ConTexMode getMinimumConTexMode() {
        return this.minCTMode;
    }

    @Override
    public boolean allowMakingIntangible() {
        return this.allowIntangible;
    }

    @Override
    public boolean isDoubleBlock() {
        return this.doubleBlock;
    }

    @Override
    public boolean consumesTwoCamosInCamoApplicationRecipe() {
        return this.doubleBlock;
    }

    @Override
    public boolean canLockState() {
        return this.lockable;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int compareTo(IBlockType other) {
        return other instanceof CustomBlockType type ? this.compareTo(type) : 1;
    }
}
