package net.arthurllew.framedcr.datagen;

import net.arthurllew.framedcr.FramedConquest;
import net.arthurllew.framedcr.registry.FramedConquestBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FramedConquest.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        blockItem(FramedConquestBlocks.FRAMED_PILLAR, "_1");
        blockItem(FramedConquestBlocks.FRAMED_BALUSTRADE, "_y");
        blockItem(FramedConquestBlocks.FRAMED_TWO_METER_ARCH, "");
    }

    private ItemModelBuilder simpleItem(DeferredItem<? extends Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation
                        .withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(FramedConquest.MODID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItem(DeferredBlock<? extends Block> block) {
        return withExistingParent(block.getId().getPath(),
                ResourceLocation
                        .withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(FramedConquest.MODID,"block/" + block.getId().getPath()));
    }

    private ItemModelBuilder blockItem(DeferredBlock<? extends Block> block, String variantEnding) {
        return withExistingParent(block.getId().getPath(),
                ResourceLocation.fromNamespaceAndPath(FramedConquest.MODID,
                        "block/" + block.getId().getPath() + variantEnding));
    }
}
