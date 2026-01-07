package net.arthurllew.framedcr;

import net.arthurllew.framedcr.registry.FramedConquestBlockEntities;
import net.arthurllew.framedcr.registry.FramedConquestBlocks;
import net.arthurllew.framedcr.registry.FramedConquestItems;

import net.arthurllew.framedcr.registry.FramedConquestLoot;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(FramedConquest.MODID)
public class FramedConquest {
    /**
     * Mod ID.
     */
    public static final String MODID = "framedcr";

    /**
     * Mod constructor. Performs basic mod init.
     */
    public FramedConquest(IEventBus modEventBus) {
        // Register the commonSetup method for mod loading
        modEventBus.addListener(this::commonSetup);

        // Register mod content
        FramedConquestBlocks.BLOCKS.register(modEventBus);
        FramedConquestBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        FramedConquestItems.ITEMS.register(modEventBus);
        FramedConquestItems.CREATIVE_MODE_TABS.register(modEventBus);
        FramedConquestLoot.LOOT_NUMBER_PROVIDERS.register(modEventBus);
    }

    /**
     * Common mod setup event handler.
     * @param event common setup event
     */
    private void commonSetup(FMLCommonSetupEvent event) {
    }
}
