package net.arthurllew.framedcr.datagen;

import net.arthurllew.framedcr.FramedConquest;
import net.arthurllew.framedcr.block.*;
import net.arthurllew.framedcr.registry.FramedConquestBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.common.data.PropertyHolder;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Function;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@SuppressWarnings("SameParameterValue")
public class ModBlockStateProvider extends BlockStateProvider {
    /**
     * Common ignored block state properties.
     */
    private static final Property<?>[] IGNORED_PROPERTIES =
            {FramedProperties.SOLID, BlockStateProperties.WATERLOGGED,
                    FramedProperties.GLOWING, FramedProperties.PROPAGATES_SKYLIGHT};

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, FramedConquest.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Generate block states for some of the blocks
        for (DeferredHolder<Block, ? extends Block> holder : FramedConquestBlocks.BLOCKS.getEntries()) {
            // Capital block
            if (holder.get() instanceof FramedCapital block) {
                this.simpleBlockWithExistingModel(block, ModBlockStateProvider::getDoubleBlockModelPath);
            }
            // Small arch block
            else if (holder.get() instanceof FramedSmallArch block) {
                this.smallArch(block);
            }
            // Small arch half block
            else if (holder.get() instanceof FramedSmallArchHalf block) {
                this.smallArchHalf(block);
            }
            // Two meters arch block
            else if (holder.get() instanceof FramedTwoMeterArch block) {
                this.twoMeterArch(block);
            }
            // Two meters arch half block
            else if (holder.get() instanceof FramedTwoMeterArchHalf block) {
                this.twoMeterArchHalf(block);
            }
            // Stairs block
            else if ((holder.get() instanceof FramedStairs block)
                    && !holder.getId().getPath().equals("framed_steps_7")
                    && !holder.getId().getPath().equals("framed_steps_8")
                    && !(block instanceof FramedStairs.Plinth)) {
                this.stairs(block);
            }
        }
    }

    /**
     * Generates block states for a simple block with existing model.
     */
    public void simpleBlockWithExistingModel(Block block, Function<Block, String> nameGetter) {
        String modelPath = nameGetter.apply(block);
        this.simpleBlock(block, this.models().getExistingFile(
                ResourceLocation.fromNamespaceAndPath(FramedConquest.MODID, "block/" + modelPath)));
    }

    /**
     * Generates block states for a small arch block.
     */
    public void smallArch(FramedSmallArch block) {
        String modelPath = getSmallArchModelPath(block);
        ModelFile model = this.models().getExistingFile(
                ResourceLocation.fromNamespaceAndPath(FramedConquest.MODID, "block/" + modelPath));

        // Iterate main block properties
        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            // Get block state properties
            Direction.Axis axis = state.getValue(BlockStateProperties.HORIZONTAL_AXIS);
            Half half = state.getValue(BlockStateProperties.HALF);

            // Y rotation from direction
            int rotY = axis == Direction.Axis.X ? 90 : 0;

            // Init builder
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder()
                    .modelFile(model)
                    .uvLock(true);

            // Ignore 0 rotation
            if (rotY != 0) {
                builder.rotationY(rotY);
            }

            // Flip X if bottom
            if (half == Half.BOTTOM) {
                builder.rotationX(180);
            }

            // Build
            return builder.build();
        }, IGNORED_PROPERTIES);
    }

    /**
     * Generates block states for a small arch half block.
     */
    public void smallArchHalf(FramedSmallArchHalf block) {
        String modelPath = getSmallArchHalfModelPath(block);
        ModelFile model = this.models().getExistingFile(
                ResourceLocation.fromNamespaceAndPath(FramedConquest.MODID, "block/" + modelPath));

        // Iterate main block properties
        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            // Get block state properties
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            Half half = state.getValue(BlockStateProperties.HALF);

            // Whether is bottom
            boolean bottom = half == Half.BOTTOM;
            // Choose Y rotation
            int baseY = switch (dir) {
                case NORTH -> 0;
                case SOUTH -> 180;
                case WEST -> 270;
                default -> 90;
            };
            // +180 if bottom
            int rotY = bottom ? baseY : (baseY + 180) % 360;

            // Init builder
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(rotY)
                    .uvLock(true);

            // Flip X if bottom
            if (bottom) {
                builder.rotationX(180);
            }

            // Build
            return builder.build();
        }, IGNORED_PROPERTIES);
    }

    /**
     * Generates block states for a two meters arch block.
     */
    public void twoMeterArch(FramedTwoMeterArch block) {
        String modelPath = getTwoMeterArchModelPath(block);
        ModelFile model = this.models().getExistingFile(
                ResourceLocation.fromNamespaceAndPath(FramedConquest.MODID, "block/" + modelPath));

        // Iterate main block properties
        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            // Get block state properties
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            Half half = state.getValue(BlockStateProperties.HALF);

            // Choose Y rotation
            int rotY = switch (dir) {
                case NORTH -> 0;
                case SOUTH -> 180;
                case WEST -> 270;
                default -> 90;
            };

            // Init builder
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(rotY)
                    .uvLock(true);

            // Flip X if bottom
            if (half == Half.BOTTOM) {
                builder.rotationX(180);
            }

            // Build
            return builder.build();
        }, IGNORED_PROPERTIES);
    }

    /**
     * Generates block states for a two meters arch half block.
     */
    public void twoMeterArchHalf(FramedTwoMeterArchHalf block) {
        String modelPathL = getTwoMeterArchHalfModelPath(block, "_left");
        ModelFile modelL = this.models().getExistingFile(
                ResourceLocation.fromNamespaceAndPath(FramedConquest.MODID, "block/" + modelPathL));
        String modelPathR = getTwoMeterArchHalfModelPath(block, "_right");
        ModelFile modelR = this.models().getExistingFile(
                ResourceLocation.fromNamespaceAndPath(FramedConquest.MODID, "block/" + modelPathR));

        // Iterate main block properties
        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            // Get block state properties
            Direction dir = state.getValue(FramedProperties.FACING_HOR);
            boolean top = state.getValue(FramedProperties.TOP);
            boolean right = state.getValue(PropertyHolder.RIGHT);

            // Choose Y rotation
            int baseY = switch (dir) {
                case NORTH -> 0;
                case SOUTH -> 180;
                case WEST -> 270;
                default -> 90;
            };
            // +180 if right
            int rotY = right ? (baseY + 180) % 360 : baseY;

            // Choose model
            ModelFile model = top
                    ? (right ? modelL : modelR)
                    : (right ? modelR : modelL);

            // Init builder
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(rotY)
                    .uvLock(true);

            // Flip X if bottom
            if (!top) {
                builder.rotationX(180);
            }

            // Build
            return builder.build();
        }, IGNORED_PROPERTIES);
    }

    /**
     * Generates block state for a stairs block.
     */
    @SuppressWarnings("ExtractMethodRecommender")
    public void stairs(FramedStairs block) {
        String name = getBlockModelPath(block);
        ModelFile modelStraight = this.models().getExistingFile(
                ResourceLocation.fromNamespaceAndPath(FramedConquest.MODID, "block/" + name));
        ModelFile modelOuter = this.models().getExistingFile(
                ResourceLocation.fromNamespaceAndPath(FramedConquest.MODID, "block/" + name + "_outer"));
        ModelFile modelInner = this.models().getExistingFile(
                ResourceLocation.fromNamespaceAndPath(FramedConquest.MODID, "block/" + name + "_inner"));

        // Iterate main block properties
        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            // Get block state properties
            Direction dir = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            Half half = state.getValue(BlockStateProperties.HALF);
            StairsShape shape = state.getValue(BlockStateProperties.STAIRS_SHAPE);

            // Whether is top
            boolean top = half == Half.TOP;

            // Choose Y rotation
            int baseY = switch (dir) {
                case EAST -> 0;
                case SOUTH -> 90;
                case WEST -> 180;
                default -> 270;
            };
            // Left-handed shapes rotate one step counter-clockwise from the base facing rotation
            int catRotY = ((shape == StairsShape.OUTER_LEFT) || (shape == StairsShape.INNER_LEFT))
                    ? (baseY + 270) % 360 : baseY;

            // Straight keeps the same Y when flipped upside-down
            // outer/inner shapes rotate an extra 90 degrees when flipped, since the corner mirrors
            int rotY = (top && shape != StairsShape.STRAIGHT) ? (catRotY + 90) % 360 : catRotY;

            // Choose model
            ModelFile model = switch (shape) {
                case STRAIGHT -> modelStraight;
                case OUTER_LEFT, OUTER_RIGHT -> modelOuter;
                default -> modelInner; // INNER_LEFT, INNER_RIGHT
            };

            // Init builder
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder()
                    .modelFile(model)
                    .uvLock(true);

            // Ignore 0 rotation
            if (rotY != 0) {
                builder.rotationY(rotY);
            }

            // Flip X if top
            if (top) {
                builder.rotationX(180);
            }

            // Build
            return builder.build();
        }, IGNORED_PROPERTIES);
    }

    /**
     * @return block model path
     */
    protected static String getBlockModelPath(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    /**
     * @return double block model path
     */
    protected static String getDoubleBlockModelPath(Block block) {
        // Replace last underscore with slash
        return getBlockModelPath(block).replaceFirst("_(?!.*_)", "/");
    }

    /**
     * @return double block model path
     */
    protected static String getDoubleBlockModelPath(Block block, String variant) {
        // Replace last underscore with slash
        return getBlockModelPath(block).replaceFirst("_(?!.*_)", variant + "/");
    }

    /**
     * @return double block model path
     */
    protected static String getSmallArchModelPath(FramedSmallArch block) {
        if (block instanceof FramedSmallArch.Bottom
                || block instanceof FramedSmallArch.Top
                || block instanceof FramedSmallArch.Double) {
            return getDoubleBlockModelPath(block);
        }
        else {
            return getBlockModelPath(block);
        }
    }

    /**
     * @return double block model path
     */
    protected static String getSmallArchHalfModelPath(FramedSmallArchHalf block) {
        if (block instanceof FramedSmallArchHalf.Bottom
                || block instanceof FramedSmallArchHalf.Top
                || block instanceof FramedSmallArchHalf.Double) {
            return getDoubleBlockModelPath(block);
        }
        else {
            return getBlockModelPath(block);
        }
    }

    /**
     * @return double block model path
     */
    protected static String getTwoMeterArchModelPath(FramedTwoMeterArch block) {
        if (block instanceof FramedTwoMeterArch.Bottom
                || block instanceof FramedTwoMeterArch.Top
                || block instanceof FramedTwoMeterArch.Double) {
            return getDoubleBlockModelPath(block);
        }
        else {
            return getBlockModelPath(block);
        }
    }

    /**
     * @return double block model path
     */
    protected static String getTwoMeterArchHalfModelPath(FramedTwoMeterArchHalf block, String variant) {
        if (block instanceof FramedTwoMeterArchHalf.Bottom
                || block instanceof FramedTwoMeterArchHalf.Top
                || block instanceof FramedTwoMeterArchHalf.Double) {
            return getDoubleBlockModelPath(block, variant);
        }
        else {
            return getBlockModelPath(block) + variant;
        }
    }
}
