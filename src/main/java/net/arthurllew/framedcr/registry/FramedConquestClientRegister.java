package net.arthurllew.framedcr.registry;

import net.arthurllew.framedcr.FramedConquest;
import net.arthurllew.framedcr.client.model.RemappedFramedGeometry;
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
import xfacthd.framedblocks.api.render.debug.AttachDebugRenderersEvent;
import xfacthd.framedblocks.client.FBClient;
import xfacthd.framedblocks.client.render.debug.impl.ConnectionPredicateDebugRenderer;
import xfacthd.framedblocks.client.render.debug.impl.QuadWindingDebugRenderer;

@Mod(value = FramedConquest.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = FramedConquest.MODID, value = Dist.CLIENT)
public class FramedConquestClientRegister {
    /**
     * Each framed block entity should have debug renderers associated with it (see {@link FBClient}).
     */
    @SubscribeEvent
    static void registerDebugRenderers(AttachDebugRenderersEvent event) {
        event.attach(FramedConquestBlockEntities.FRAMED_CONQUEST_BLOCK_ENTITY.get(),
                ConnectionPredicateDebugRenderer.INSTANCE);
        event.attach(FramedConquestBlockEntities.FRAMED_CONQUEST_BLOCK_ENTITY.get(),
                QuadWindingDebugRenderer.INSTANCE);
    }

    /**
     * Each framed block should have model wrapper associated with it (see {@link FBClient}).
     */
    @SubscribeEvent
    static void registerModelWrappers(RegisterModelWrappersEvent event) {
        for (DeferredHolder<Block, ? extends Block> block : FramedConquestBlocks.BLOCKS.getEntries()) {
            WrapHelper.wrap(block, RemappedFramedGeometry::new, WrapHelper.IGNORE_DEFAULT);
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
