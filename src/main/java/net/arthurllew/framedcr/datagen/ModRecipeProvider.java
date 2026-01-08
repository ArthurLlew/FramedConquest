package net.arthurllew.framedcr.datagen;

import net.arthurllew.framedcr.registry.FramedConquestBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
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
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(FBContent.BLOCK_FRAMED_CUBE.value().asItem()),
                RecipeCategory.DECORATIONS, FramedConquestBlocks.FRAMED_PILLAR.get(), 3);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(FBContent.BLOCK_FRAMED_CUBE.value().asItem()),
                RecipeCategory.DECORATIONS, FramedConquestBlocks.FRAMED_BALUSTRADE.get(), 1);
    }
}
