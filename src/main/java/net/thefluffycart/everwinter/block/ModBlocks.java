package net.thefluffycart.everwinter.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.thefluffycart.everwinter.Everwinter;
import net.thefluffycart.everwinter.block.custom.SnowGlobeBlock;
import net.thefluffycart.everwinter.block.custom.SnowMachineBlock;

public class ModBlocks {
    public static final Block DRY_ICE = registerBlock("dry_ice",
            new Block(AbstractBlock.Settings.copy(Blocks.BLUE_ICE)));
    public static final Block SNOW_GLOBE = registerBlock("snow_globe",
            new SnowGlobeBlock(AbstractBlock.Settings.copy(Blocks.GLASS).nonOpaque()
                    .solidBlock(Blocks::never)
                    .suffocates(Blocks::never)
                    .blockVision(Blocks::never)));
    public static final Block SNOW_MACHINE = registerBlock("snow_machine",
            new SnowMachineBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Everwinter.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Identifier.of(Everwinter.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        Everwinter.LOGGER.info("Registering ModBlocks for " + Everwinter.MOD_ID);
    }
}
