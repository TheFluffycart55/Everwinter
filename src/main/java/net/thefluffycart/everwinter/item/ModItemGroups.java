package net.thefluffycart.everwinter.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.thefluffycart.everwinter.Everwinter;
import net.thefluffycart.everwinter.block.ModBlocks;

public class ModItemGroups {
    public static final ItemGroup EVERWINTER = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Everwinter.MOD_ID, "everwinter"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.everwinter"))
                    .icon(()-> new ItemStack(ModItems.FROZEN_TUSK)).entries((displayContext, entries) -> {
                        entries.add(ModItems.FROZEN_TUSK);
                        entries.add(ModBlocks.DRY_ICE);
                        entries.add(ModBlocks.SNOW_MACHINE);
                        entries.add(ModItems.ICEOLOGER_SPAWN_EGG);
                        entries.add(ModBlocks.SNOW_GLOBE);
                        entries.add(ModItems.SNOW_BLOWER);
                    }).build());

    public static void registerItemGroups() {
        Everwinter.LOGGER.info("Registering Item Groups for " + Everwinter.MOD_ID);
    }
}
