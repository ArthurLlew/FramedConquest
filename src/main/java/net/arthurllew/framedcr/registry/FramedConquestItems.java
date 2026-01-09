package net.arthurllew.framedcr.registry;

import net.arthurllew.framedcr.FramedConquest;
import net.arthurllew.framedcr.block.CustomFramedBlock;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FramedConquestItems {
    /**
     * Deferred Register for items.
     */
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FramedConquest.MODID);
    /**
     * Deferred Register for creative tabs.
     */
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FramedConquest.MODID);

    /**
     * Memento Beta item group.
     */
    public static final Supplier<CreativeModeTab> BETA_DECO_ITEM_GROUP =
            CREATIVE_MODE_TABS.register("memento_beta", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemgroup." + FramedConquest.MODID + ".items"))
                    .icon(() -> new ItemStack(FramedConquestBlocks.FRAMED_BALUSTRADE.get()))
                    .displayItems((parameters, output) -> {
                        for (Block block : FramedConquestBlocks.BLOCKS.getEntries()
                                .stream().map(Holder::value).toArray(Block[]::new)) {
                            if (block instanceof CustomFramedBlock framedBlock) {
                                if (framedBlock.getCustomBlockType().hasBlockItem()) {
                                    output.accept(block);
                                }
                            }
                        }
                    }).build());
}
