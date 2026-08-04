package net.arthurllew.framedcr.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.arthurllew.framedcr.block.CustomFramedBlock;
import net.arthurllew.framedcr.registry.FramedConquestLoot;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
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
public class LayeredBlockLootNumberProvider implements NumberProvider {
    // The Codec definition
    public static final MapCodec<LayeredBlockLootNumberProvider> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BuiltInRegistries.BLOCK.byNameCodec()
                            .fieldOf("block").forGetter(LayeredBlockLootNumberProvider::getBlock)
            ).apply(instance, LayeredBlockLootNumberProvider::new));

    /**
     * Class has only one instance.
     */
    private final CustomFramedBlock block;

    /**
     * Constructor.
     */
    public LayeredBlockLootNumberProvider(Block block) {
        if (block instanceof CustomFramedBlock framedBlock) {
            this.block = framedBlock;
        }
        else {
            throw new IllegalArgumentException("Incorrect block type in "
                    + LayeredBlockLootNumberProvider.class.getName());
        }
    }

    public CustomFramedBlock getBlock() {
        return block;
    }

    /**
     * @return loot count.
     */
    @Override
    public float getFloat(LootContext ctx) {
        return block.getLootCount(ctx.getParam(LootContextParams.BLOCK_STATE));
    }

    /**
     * @return provider type.
     */
    @Override
    public LootNumberProviderType getType() {
        return FramedConquestLoot.FRAMED_LAYERED_BLOCK_LOOT_COUNT_PROVIDER.value();
    }
}
