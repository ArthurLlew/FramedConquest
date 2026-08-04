package net.arthurllew.framedcr.registry;

import net.arthurllew.framedcr.FramedConquest;
import net.arthurllew.framedcr.loot.LayeredBlockLootNumberProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FramedConquestLoot {
    /**
     * Deferred Register for loot modifiers.
     */
    public static final DeferredRegister<LootNumberProviderType> LOOT_NUMBER_PROVIDERS =
            DeferredRegister.create(Registries.LOOT_NUMBER_PROVIDER_TYPE, FramedConquest.MODID);

    /**
     * Layered block loot count provider.
     */
    public static final Holder<LootNumberProviderType> FRAMED_LAYERED_BLOCK_LOOT_COUNT_PROVIDER =
            LOOT_NUMBER_PROVIDERS.register("layered",
                    () -> new LootNumberProviderType(LayeredBlockLootNumberProvider.CODEC));
}
