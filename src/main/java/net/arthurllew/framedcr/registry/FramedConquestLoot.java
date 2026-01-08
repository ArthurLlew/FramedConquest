package net.arthurllew.framedcr.registry;

import com.mojang.serialization.MapCodec;
import net.arthurllew.framedcr.FramedConquest;
import net.arthurllew.framedcr.loot.PillarLootNumberProvider;
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
     * Pillar loot count provider.
     */
    public static final Holder<LootNumberProviderType> FRAMED_PILLAR_ITEM_COUNT_PROVIDER =
            LOOT_NUMBER_PROVIDERS.register("layered_pillar",
                    () -> new LootNumberProviderType(
                            MapCodec.unit(PillarLootNumberProvider.INSTANCE)));
}
