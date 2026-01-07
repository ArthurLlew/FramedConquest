package net.arthurllew.framedcr.loot;

import net.arthurllew.framedcr.block.FramedPillar;
import net.arthurllew.framedcr.registry.FramedConquestLoot;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import xfacthd.framedblocks.common.data.loot.LayeredCubeAdditionalItemCountNumberProvider;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Adopted from {@link LayeredCubeAdditionalItemCountNumberProvider}
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class PillarLootNumberProvider implements NumberProvider {
    /**
     * Class has only one instance.
     */
    public static final PillarLootNumberProvider INSTANCE = new PillarLootNumberProvider();

    /**
     * Constructor.
     */
    private PillarLootNumberProvider() {}

    /**
     * @return loot count.
     */
    public float getFloat(LootContext ctx) {
        BlockState state = ctx.getParam(LootContextParams.BLOCK_STATE);
        return state.hasProperty(FramedPillar.LAYERS) ? (float)(state.getValue(FramedPillar.LAYERS) - 1) : 0.0F;
    }

    /**
     * @return provider type.
     */
    public LootNumberProviderType getType() {
        return FramedConquestLoot.FRAMED_PILLAR_ITEM_COUNT_PROVIDER.value();
    }
}
