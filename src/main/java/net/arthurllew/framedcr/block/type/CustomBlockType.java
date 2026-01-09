package net.arthurllew.framedcr.block.type;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import net.arthurllew.framedcr.block.FramedArrowslit;
import net.arthurllew.framedcr.block.FramedBalustrade;
import net.arthurllew.framedcr.block.FramedPillar;
import net.arthurllew.framedcr.block.FramedTwoMeterArch;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
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
            true, false, false, ConTexMode.FULL_FACE,
            FramedPillar::generateShapes,
            (state, dir) -> state.getValue(FramedPillar.LAYERS) == 4,
            SideSkipPredicate.FALSE,
            ConnectionPredicate.FULL_EDGE),
    FRAMED_BALUSTRADE(true, false, false, true, true,
            true, false, false, ConTexMode.FULL_FACE,
            FramedBalustrade::generateShapes,
            FullFacePredicate.FALSE,
            SideSkipPredicate.FALSE,
            ConnectionPredicate.FULL_EDGE),
    FRAMED_ARROWSLIT(true, false, false, true, true,
            true, false, false, ConTexMode.FULL_FACE,
            FramedArrowslit::generateShapes,
            FullFacePredicate.FALSE,
            SideSkipPredicate.FALSE,
            ConnectionPredicate.FULL_EDGE),
    FRAMED_TWO_METER_ARCH(true, false, false, true, true,
            true, false, false, ConTexMode.FULL_FACE,
            FramedTwoMeterArch::generateShapes,
            (state, dir) -> {
                if (dir == Direction.UP) {
                    return state.getValue(BlockStateProperties.HALF) == Half.TOP;
                } else if (dir == Direction.DOWN) {
                    return state.getValue(BlockStateProperties.HALF) == Half.BOTTOM;
                } else {
                    Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                    return facing == dir;
                }
            },
            new StairsSkipPredicate(),
            ConnectionPredicate.FULL_EDGE),
    FRAMED_TWO_METER_ARCH_HALF(true, false, false, true, true,
            true, false, false, ConTexMode.FULL_FACE,
            HalfStairsShapes::generate,
            FullFacePredicate.FALSE,
            SideSkipPredicate.FALSE,
            ConnectionPredicate.FULL_EDGE);

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

    CustomBlockType(boolean canOcclude, boolean specialHitbox, boolean specialTile, boolean waterloggable,
                    boolean blockItem, boolean allowIntangible, boolean doubleBlock, boolean lockable,
                    ConTexMode minCTMode,
                    FullFacePredicate fullFacePredicate,
                    SideSkipPredicate sideSkipPredicate,
                    ConnectionPredicate connectionPredicates) {
        this(canOcclude, specialHitbox, specialTile, waterloggable, blockItem, allowIntangible, doubleBlock,
                lockable, minCTMode, ShapeGenerator.EMPTY,
                fullFacePredicate, sideSkipPredicate, connectionPredicates);
    }

    CustomBlockType(boolean canOcclude, boolean specialHitbox, boolean specialTile, boolean waterloggable,
                    boolean blockItem, boolean allowIntangible, boolean doubleBlock, boolean lockable,
                    ConTexMode minCTMode, VoxelShape shape,
                    FullFacePredicate fullFacePredicate,
                    SideSkipPredicate sideSkipPredicate,
                    ConnectionPredicate connectionPredicates) {
        this(canOcclude, specialHitbox, specialTile, waterloggable, blockItem, allowIntangible, doubleBlock,
                lockable, minCTMode, ShapeGenerator.singleShape(shape),
                fullFacePredicate, sideSkipPredicate, connectionPredicates);
        Preconditions.checkArgument(!waterloggable || !Shapes.joinUnoptimized(shape, Shapes.block(),
                BooleanOp.NOT_SAME).isEmpty(), "Blocks with full cube shape can't be waterloggable");
    }

    CustomBlockType(boolean canOcclude, boolean specialHitbox, boolean specialTile, boolean waterloggable,
                    boolean blockItem, boolean allowIntangible, boolean doubleBlock, boolean lockable,
                    ConTexMode minCTMode, ShapeGenerator shapeGen,
                    FullFacePredicate fullFacePredicate,
                    SideSkipPredicate sideSkipPredicate,
                    ConnectionPredicate connectionPredicates) {
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
    }

    public boolean canOccludeWithSolidCamo() {
        return this.canOcclude;
    }

    public boolean hasSpecialHitbox() {
        return this.specialHitbox;
    }

    public FullFacePredicate getFullFacePredicate() {
        return fullFacePredicate;
    }

    public SideSkipPredicate getSideSkipPredicate() {
        return sideSkipPredicate;
    }

    public ConnectionPredicate getConnectionPredicate() {
        return connectionPredicates;
    }

    public ShapeProvider generateShapes(ImmutableList<BlockState> states) {
        return !FMLEnvironment.production ? new ReloadableShapeProvider(this.shapeGen, states) : this.shapeGen.generate(states);
    }

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

    public boolean hasSpecialTile() {
        return this.specialTile;
    }

    public boolean hasBlockItem() {
        return this.blockItem;
    }

    public boolean supportsWaterLogging() {
        return this.waterloggable;
    }

    public boolean supportsConnectedTextures() {
        return this.supportsCT;
    }

    public ConTexMode getMinimumConTexMode() {
        return this.minCTMode;
    }

    public boolean allowMakingIntangible() {
        return this.allowIntangible;
    }

    public boolean isDoubleBlock() {
        return this.doubleBlock;
    }

    public boolean consumesTwoCamosInCamoApplicationRecipe() {
        return this.doubleBlock;
    }

    public boolean canLockState() {
        return this.lockable;
    }

    public String getName() {
        return this.name;
    }

    public int compareTo(IBlockType other) {
        if (other instanceof BlockType type) {
            return this.compareTo(type);
        } else {
            return 1;
        }
    }
}
