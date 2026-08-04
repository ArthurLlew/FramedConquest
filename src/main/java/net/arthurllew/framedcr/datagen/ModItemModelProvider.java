package net.arthurllew.framedcr.datagen;

import net.arthurllew.framedcr.FramedConquest;
import net.arthurllew.framedcr.block.CustomFramedBlock;
import net.arthurllew.framedcr.block.ICustomFramedDoubleBlock;
import net.arthurllew.framedcr.registry.FramedConquestBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FramedConquest.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Generate block item models for every block using custom block type
        for (DeferredHolder<Block, ? extends Block> holder : FramedConquestBlocks.BLOCKS.getEntries()) {
            if (holder.get() instanceof CustomFramedBlock framedBlock) {
                if (framedBlock.getCustomBlockType().hasBlockItem()) {
                    blockItem((DeferredBlock<? extends Block>) holder,
                            framedBlock.getCustomBlockType().modelVariantForItem());
                }
            }
        }
    }

    private void blockItem(DeferredBlock<? extends Block> holder, String variantEnding) {
        String itemId = holder.getId().getPath();
        String modelPath = holder.get() instanceof ICustomFramedDoubleBlock ?
                itemId.replaceFirst("_(?!.*_)", variantEnding + "/") : itemId + variantEnding;
        withExistingParent(itemId,
                ResourceLocation.fromNamespaceAndPath(FramedConquest.MODID, "block/" + modelPath));
    }
}
