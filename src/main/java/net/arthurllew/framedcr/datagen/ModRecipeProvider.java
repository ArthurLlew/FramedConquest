package net.arthurllew.framedcr.datagen;

import net.arthurllew.framedcr.block.CustomFramedBlock;
import net.arthurllew.framedcr.registry.FramedConquestBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
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
        // Generate recipe for every block
        for (DeferredHolder<Block, ? extends Block> holder : FramedConquestBlocks.BLOCKS.getEntries()) {
            // Is framed block and has block item
            if ((holder.get() instanceof CustomFramedBlock framedBlock) && framedBlock.getCustomBlockType().hasBlockItem()) {
                // Stonecutting recipe
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(FBContent.BLOCK_FRAMED_CUBE.value().asItem()),
                        RecipeCategory.DECORATIONS, framedBlock, framedBlock.getCustomBlockType().craftingCount())
                        .unlockedBy(getHasName(FBContent.BLOCK_FRAMED_CUBE.value()), has(FBContent.BLOCK_FRAMED_CUBE.value()))
                        .save(recipeOutput);
            }
        }
    }
}
