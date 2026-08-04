package net.arthurllew.framedcr.datagen;

import net.arthurllew.framedcr.block.CustomFramedBlock;
import net.arthurllew.framedcr.loot.LayeredBlockLootNumberProvider;
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
import net.neoforged.neoforge.registries.DeferredHolder;
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
        // Generate "drop self and camo" loot table for every block using custom block type
        for (DeferredHolder<Block, ? extends Block> holder : FramedConquestBlocks.BLOCKS.getEntries()) {
            if (holder.get() instanceof CustomFramedBlock framedBlock) {
                // Layered block
                if (framedBlock.getCustomBlockType().isLayered()) {
                    // Drop with camo + custom count
                    this.add(framedBlock,
                            LootTable.lootTable()
                                    .withPool(this.createDropWithCamoPool(framedBlock))
                                    .withPool(this.applyExplosionCondition(framedBlock,
                                            LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                                    .add(((LootPoolSingletonContainer.Builder<?>)this.applyExplosionDecay(
                                                            framedBlock, LootItem.lootTableItem(framedBlock)))
                                                            .apply(SetItemCountFunction.setCount(
                                                                    new LayeredBlockLootNumberProvider(framedBlock)))))));
                }
                // Simple block
                else {
                    // Drop with camo
                    this.dropSelfWithCamo(holder.get());
                }
            }
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return FramedConquestBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}