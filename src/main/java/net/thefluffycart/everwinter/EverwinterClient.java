package net.thefluffycart.everwinter;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.thefluffycart.everwinter.block.ModBlocks;
import net.thefluffycart.everwinter.entity.ModEntities;
import net.thefluffycart.everwinter.entity.client.*;


public class EverwinterClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.ICEOLOGER, IllagerEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.ICEOLOGER, IceologerRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SNOW_GLOBE, RenderLayer.getTranslucent());
    }
}
