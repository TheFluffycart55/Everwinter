package net.thefluffycart.everwinter.util;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.thefluffycart.everwinter.Everwinter;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> SNOW_MACHINE_FUEL_LOW = createTag("snow_machine_fuel_low");
        public static final TagKey<Block> SNOW_MACHINE_FUEL_MID = createTag("snow_machine_fuel_mid");
        public static final TagKey<Block> SNOW_MACHINE_FUEL_HIGH = createTag("snow_machine_fuel_high");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(Everwinter.MOD_ID, name));
        }
    }
}
