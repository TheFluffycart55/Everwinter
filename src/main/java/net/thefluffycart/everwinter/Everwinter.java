package net.thefluffycart.everwinter;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.thefluffycart.everwinter.block.ModBlocks;
import net.thefluffycart.everwinter.entity.ModEntities;
import net.thefluffycart.everwinter.entity.custom.IceologerEntity;
import net.thefluffycart.everwinter.item.ModItemGroups;
import net.thefluffycart.everwinter.item.ModItems;
import net.thefluffycart.everwinter.sound.ModSounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Everwinter implements ModInitializer {
	public static final String MOD_ID = "everwinter";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModEntities.registerModEntities();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModItemGroups.registerItemGroups();
		ModSounds.registerSounds();

		FabricDefaultAttributeRegistry.register(ModEntities.ICEOLOGER, IceologerEntity.createIceologerAttributes());
	}
}