package net.arthurllew.framedcr.datagen;

import net.arthurllew.framedcr.block.CustomFramedBlock;
import net.arthurllew.framedcr.registry.FramedConquestBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.registries.DeferredHolder;
import xfacthd.framedblocks.common.FBContent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        // Generate recipe for every block using custom block type
        for (DeferredHolder<Block, ? extends Block> block : FramedConquestBlocks.BLOCKS.getEntries()) {
            if (block.get() instanceof CustomFramedBlock framedBlock) {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(FBContent.BLOCK_FRAMED_CUBE.value().asItem()),
                        RecipeCategory.DECORATIONS,
                        block.get(),
                        framedBlock.getCustomBlockType().craftingCount());
            }
        }
    }
}
