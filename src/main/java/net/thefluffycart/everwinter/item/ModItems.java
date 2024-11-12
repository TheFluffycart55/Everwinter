package net.thefluffycart.everwinter.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.InstrumentTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.thefluffycart.everwinter.Everwinter;
import net.thefluffycart.everwinter.entity.ModEntities;
import net.thefluffycart.everwinter.item.custom.FrozenTuskItem;

public class ModItems {

    public static final Item FROZEN_TUSK = registerItem("frozen_tusk", new FrozenTuskItem(new Item.Settings().maxCount(1), InstrumentTags.GOAT_HORNS));
    public static final Item SNOW_BLOWER = registerItem("snow_blower", new Item(new Item.Settings().maxDamage(432).rarity(Rarity.RARE)));
    public static final Item ICEOLOGER_SPAWN_EGG = registerItem("iceologer_spawn_egg",
            new SpawnEggItem(ModEntities.ICEOLOGER, 0x43518A, 0x959B9B, new Item.Settings()));
    public static Item registerItem(String name, Item item)
    {
        return Registry.register(Registries.ITEM, Identifier.of(Everwinter.MOD_ID, name), item);
    }

    public static void registerModItems()
    {
        Everwinter.LOGGER.info("Registering Items for " + Everwinter.MOD_ID);
    }
}
