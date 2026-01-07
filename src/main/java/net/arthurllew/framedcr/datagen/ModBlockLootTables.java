package net.arthurllew.framedcr.datagen;

import net.arthurllew.framedcr.loot.PillarLootNumberProvider;
import net.arthurllew.framedcr.registry.FramedConquestBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import xfacthd.framedblocks.api.datagen.loot.FramedBlockLootSubProvider;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ModBlockLootTables extends FramedBlockLootSubProvider {
    protected ModBlockLootTables(HolderLookup.Provider registries) {
        super(registries);
    }

    @Override
    public void generate() {
        // Drop with custom count + drops camo
        this.add(FramedConquestBlocks.FRAMED_PILLAR.value(),
                LootTable.lootTable()
                        .withPool(this.createDropWithCamoPool(FramedConquestBlocks.FRAMED_PILLAR.value()))
                        .withPool(this.applyExplosionCondition(FramedConquestBlocks.FRAMED_PILLAR.value(),
                                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                .add(((LootPoolSingletonContainer.Builder<?>)this.applyExplosionDecay(
                                        FramedConquestBlocks.FRAMED_PILLAR.value(),
                                        LootItem.lootTableItem(FramedConquestBlocks.FRAMED_PILLAR.value())))
                                                .apply(SetItemCountFunction.setCount(
                                                        PillarLootNumberProvider.INSTANCE))))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return FramedConquestBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}