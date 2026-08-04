package net.arthurllew.framedcr.client;

import net.arthurllew.framedcr.FramedConquest;
import net.arthurllew.framedcr.block.ICustomFramedDoubleBlock;
import net.arthurllew.framedcr.client.model.GeometryFromJSON;
import net.arthurllew.framedcr.registry.FramedConquestBlockEntities;
import net.arthurllew.framedcr.registry.FramedConquestBlocks;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import xfacthd.framedblocks.api.block.render.FramedBlockColor;
import xfacthd.framedblocks.api.model.wrapping.RegisterModelWrappersEvent;
import xfacthd.framedblocks.api.model.wrapping.WrapHelper;
import xfacthd.framedblocks.api.model.wrapping.statemerger.StateMerger;
import xfacthd.framedblocks.api.render.debug.AttachDebugRenderersEvent;
import xfacthd.framedblocks.client.FBClient;
import xfacthd.framedblocks.client.model.DoubleBlockItemModelInfo;
import xfacthd.framedblocks.client.model.FramedDoubleBlockModel;
import xfacthd.framedblocks.client.render.debug.impl.ConnectionPredicateDebugRenderer;
import xfacthd.framedblocks.client.render.debug.impl.QuadWindingDebugRenderer;
import xfacthd.framedblocks.common.data.doubleblock.NullCullPredicate;

@Mod(value = FramedConquest.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = FramedConquest.MODID, value = Dist.CLIENT)
public class FramedConquestClient {
    /**
     * Each framed block entity should have debug renderers associated with it (see {@link FBClient}).
     */
    @SubscribeEvent
    static void registerDebugRenderers(AttachDebugRenderersEvent event) {
        // Single blocks entity
        event.attach(FramedConquestBlockEntities.FRAMED_CONQUEST_BLOCK_ENTITY.get(),
                ConnectionPredicateDebugRenderer.INSTANCE);
        event.attach(FramedConquestBlockEntities.FRAMED_CONQUEST_BLOCK_ENTITY.get(),
                QuadWindingDebugRenderer.INSTANCE);
        // Double blocks entity
        event.attach(FramedConquestBlockEntities.FRAMED_CONQUEST_DOUBLE_BLOCK_ENTITY.get(),
                ConnectionPredicateDebugRenderer.INSTANCE);
        event.attach(FramedConquestBlockEntities.FRAMED_CONQUEST_DOUBLE_BLOCK_ENTITY.get(),
                QuadWindingDebugRenderer.INSTANCE);
    }

    /**
     * Each framed block should have model wrapper associated with it (see {@link FBClient}).
     */
    @SubscribeEvent
    static void registerModelWrappers(RegisterModelWrappersEvent event) {
        for (DeferredHolder<Block, ? extends Block> block : FramedConquestBlocks.BLOCKS.getEntries()) {
            // Double blocks
            if (block.get() instanceof ICustomFramedDoubleBlock) {
                WrapHelper.wrapSpecial(block,
                        (ctx) -> new FramedDoubleBlockModel(ctx,
                                NullCullPredicate.NEVER,
                                DoubleBlockItemModelInfo.INSTANCE),
                        StateMerger.ignoring(WrapHelper.IGNORE_DEFAULT));
            }
            // Single blocks
            else {
                WrapHelper.wrap(block, GeometryFromJSON::new, WrapHelper.IGNORE_DEFAULT);
            }
        }
    }

    /**
     * Each framed block should have block color wrapper associated with it (see {@link FBClient}).
     */
    @SubscribeEvent
    private static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        Block[] blocks = FramedConquestBlocks.BLOCKS.getEntries().stream().map(Holder::value)
                .toArray(Block[]::new);
        event.register(FramedBlockColor.INSTANCE, blocks);
    }
}
