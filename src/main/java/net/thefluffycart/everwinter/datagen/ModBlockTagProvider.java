package net.thefluffycart.everwinter.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.thefluffycart.everwinter.block.ModBlocks;
import net.thefluffycart.everwinter.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.Blocks.SNOW_MACHINE_FUEL_LOW)
                .add(Blocks.ICE)
                .add(ModBlocks.DRY_ICE);
        getOrCreateTagBuilder(ModTags.Blocks.SNOW_MACHINE_FUEL_MID)
                .add(Blocks.PACKED_ICE)
                .add(Blocks.SNOW_BLOCK);
        getOrCreateTagBuilder(ModTags.Blocks.SNOW_MACHINE_FUEL_HIGH)
                .add(Blocks.POWDER_SNOW)
                .add(Blocks.POWDER_SNOW_CAULDRON)
                .add(Blocks.BLUE_ICE);
    }
}