package net.arthurllew.framedcr.client.model;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.neoforged.neoforge.client.model.data.ModelData;
import xfacthd.framedblocks.api.model.data.QuadMap;
import xfacthd.framedblocks.api.model.geometry.Geometry;
import xfacthd.framedblocks.api.model.quad.QuadData;
import xfacthd.framedblocks.api.model.quad.QuadModifier;
import xfacthd.framedblocks.api.model.wrapping.GeometryFactory;

import java.util.List;

public class RemappedFramedGeometry extends Geometry {
    /**
     * Original JSON model.
     */
    BakedModel originalModel;
    /**
     * Associated model block state.
     */
    BlockState state;

    /**
     * Constructor.
     */
    public RemappedFramedGeometry(GeometryFactory.Context ctx) {
        this.originalModel = ctx.baseModel();
        this.state = ctx.state();
    }

    /**
     * Called by API to get camo model quads.
     */
    public void transformQuad(QuadMap quadMap, BakedQuad quad) {
        Direction quadDir = quad.getDirection();

        // Get all cullable original quads, corresponding to current direction
        List<BakedQuad> originalQuads = originalModel.getQuads(state, quadDir, new LegacyRandomSource(0),
                ModelData.EMPTY, null);

        // Add all uncullable original quads, corresponding to current direction
        for(BakedQuad originalQuad : originalModel.getQuads(state, null, new LegacyRandomSource(0), ModelData.EMPTY, null)) {
            if (originalQuad.getDirection() == quadDir) {
                originalQuads.add(originalQuad);
            }
        }

        // For each quad apply remapping, copy tint index (for ability to change color)
        // and then export (save as a camo model quad)
        for(BakedQuad originalQuad : originalQuads) {
            QuadModifier.of(originalQuad).tintIndex(quad.getTintIndex()).apply(remap(new QuadData(quad)))
                    .export(quadMap.get(quadDir));
        }
    }

    /**
     * Remaps original quad to new one.
     */
    public static QuadModifier.Modifier remap(QuadData newData) {
        return (data) -> {
            // Get texture sprites
            TextureAtlasSprite originalSprite = data.quad().getSprite();
            TextureAtlasSprite newSprite = newData.quad().getSprite();

            // Get UV sizes of sprites
            float originalSpriteSizeU = originalSprite.getU1() - originalSprite.getU0();
            float originalSpriteSizeV = originalSprite.getV1() - originalSprite.getV0();
            float newSpriteSizeU = newSprite.getU1() - newSprite.getU0();
            float newSpriteSizeV = newSprite.getV1() - newSprite.getV0();

            // Calculate how to scale UVs of vertices
            float uScaling = newSpriteSizeU / originalSpriteSizeU;
            float vScaling = newSpriteSizeV / originalSpriteSizeV;

            // Remap UV and update color of all 4 vertices
            for (int i = 0; i < 4; i++) {
                // Remap UV
                float u = data.uv(i, 0);
                float v = data.uv(i, 1);
                data.uv(i,
                        (u - originalSprite.getU0()) * uScaling + newSprite.getU0(),
                        (v - originalSprite.getV0()) * vScaling + newSprite.getV0());

                // Update color
                for (int c = 0; c < 4; c++) {
                    data.color(i, c, newData.color(i, c));
                }
            }

            // Return success
            return true;
        };
    }

    /**
     * Forces API to use JSON model when placing empty framed block.
     */
    public boolean forceUngeneratedBaseModel() {
        return true;
    }
}
