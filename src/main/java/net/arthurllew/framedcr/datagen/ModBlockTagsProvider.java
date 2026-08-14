package net.arthurllew.framedcr.datagen;

import net.arthurllew.framedcr.FramedConquest;
import net.arthurllew.framedcr.block.FramedWall;
import net.arthurllew.framedcr.registry.FramedConquestBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, FramedConquest.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Make all blocks mineable with axe
        IntrinsicTagAppender<Block> mineableWithAxe = this.tag(BlockTags.MINEABLE_WITH_AXE);
        for (DeferredHolder<Block, ? extends Block> holder : FramedConquestBlocks.BLOCKS.getEntries()) {
            mineableWithAxe.add(holder.get());
        }
        // Add walls to corresponding block tag
        IntrinsicTagAppender<Block> walls = this.tag(BlockTags.WALLS);
        for (DeferredHolder<Block, ? extends Block> block : FramedConquestBlocks.BLOCKS.getEntries()) {
            if (block.get() instanceof FramedWall wall) {
                walls.add(wall);
            }
        }
    }
}
