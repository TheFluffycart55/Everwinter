package net.thefluffycart.everwinter.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.thefluffycart.everwinter.Everwinter;
import net.thefluffycart.everwinter.entity.custom.IceChunkEntity;
import net.thefluffycart.everwinter.entity.custom.IceologerEntity;

public class ModEntities {
    public static final EntityType<IceologerEntity> ICEOLOGER = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(Everwinter.MOD_ID, "iceologer"),
            EntityType.Builder.create(IceologerEntity::new, SpawnGroup.MONSTER).dimensions(1f, 2f).build());

    public static void registerModEntities() {
        Everwinter.LOGGER.info("Registering Mod Entities for " + Everwinter.MOD_ID);
    }
}
