package net.arthurllew.framedcr.block.type;

import com.google.common.collect.ImmutableList;
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
import xfacthd.framedblocks.common.data.conpreds.ConnectionPredicates;
import xfacthd.framedblocks.common.data.facepreds.FullFacePredicates;
import xfacthd.framedblocks.common.data.shapes.SplitShapeGenerator;
import xfacthd.framedblocks.common.data.skippreds.SideSkipPredicates;

import java.util.Locale;
import java.util.Objects;

/**
 * Custom implementation of {@link BlockType}, {@link FullFacePredicates}, {@link SideSkipPredicates} and
 * {@link ConnectionPredicates}.
 */
public class CustomBlockType implements IBlockType {
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
     * Whether this block contains two camos.
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
     * Block model suffix (see {@link ModItemModelProvider}).
     */
    private final String modelVariantForItem;
    /**
     * How many blocks will be crafted with stonecutting recipe (see {@link ModRecipeProvider}).
     */
    private final int craftingCount;
    /**
     * Whether this block has special loot table (see {@link ModBlockLootTables}).
     */
    private final boolean isLayered;

    /**
     * Full constructor.
     */
    CustomBlockType(boolean canOcclude, boolean specialHitbox, boolean specialTile, boolean waterloggable,
                    boolean blockItem, boolean allowIntangible, boolean doubleBlock, boolean lockable,
                    ConTexMode minCTMode,
                    FullFacePredicate fullFacePredicate,
                    SideSkipPredicate sideSkipPredicate,
                    ConnectionPredicate connectionPredicates,
                    ShapeGenerator shapeGen,
                    String modelVariantForItem,
                    int craftingCount,
                    boolean isLayered) {
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
        this.isLayered = isLayered;
    }

    public String modelVariantForItem() {
        return this.modelVariantForItem;
    }

    public int craftingCount() {
        return this.craftingCount;
    }

    public boolean isLayered() {
        return this.isLayered;
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
        return other instanceof CustomBlockType ? 0 : 1;
    }

    /**
     * Instance builder.
     */
    @SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
    public static class Builder {
        private boolean canOcclude = true;
        private boolean specialHitbox = false;
        private boolean specialTile = false;
        private boolean waterloggable = true;
        private boolean blockItem = true;
        private boolean allowIntangible = true;
        private boolean doubleBlock = false;
        private boolean lockable = false;
        private ConTexMode minCTMode = ConTexMode.FULL_FACE;
        private FullFacePredicate fullFacePredicate = FullFacePredicate.FALSE;
        private SideSkipPredicate sideSkipPredicate = SideSkipPredicate.FALSE;
        private ConnectionPredicate connectionPredicates = ConnectionPredicate.FULL_FACE;
        private final ShapeGenerator shapeGen;
        private String modelVariantForItem = "";
        private int craftingCount = 1;
        private boolean isLayered = false;

        public Builder(ShapeGenerator shapeGen) {
            this.shapeGen = shapeGen;
        }

        public Builder(IShapeGenerator shapeGen) {
            this.shapeGen = shapeGen;
        }

        public Builder waterloggable(boolean waterloggable) {
            this.waterloggable = waterloggable;

            return this;
        }

        public Builder blockItem(boolean blockItem) {
            this.blockItem = blockItem;

            return this;
        }

        public Builder doubleBlock(boolean doubleBlock) {
            this.doubleBlock = doubleBlock;

            return this;
        }

        public Builder fullFacePredicate(FullFacePredicate fullFacePredicate) {
            this.fullFacePredicate = fullFacePredicate;

            return this;
        }

        public Builder modelVariantForItem(String modelVariantForItem) {
            this.modelVariantForItem = modelVariantForItem;

            return this;
        }

        public Builder craftingCount(int craftingCount) {
            this.craftingCount = craftingCount;

            return this;
        }

        public Builder isLayered(boolean isLayered) {
            this.isLayered = isLayered;

            return this;
        }

        public CustomBlockType build() {
            return new CustomBlockType(this.canOcclude, this.specialHitbox, this.specialTile, this.waterloggable,
                    this.blockItem, this.allowIntangible, this.doubleBlock, this.lockable,
                    this.minCTMode, this.fullFacePredicate, this.sideSkipPredicate, this.connectionPredicates,
                    this.shapeGen, this.modelVariantForItem, this.craftingCount, this.isLayered);
        }
    }
}
