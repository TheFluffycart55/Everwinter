package net.thefluffycart.everwinter.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.thefluffycart.everwinter.Everwinter;

public class ModSounds {
    public static final SoundEvent FROZEN_TUSK = registerSoundEvent("frozen_tusk");

    private static SoundEvent registerSoundEvent(String name) {
        return Registry.register(Registries.SOUND_EVENT, Identifier.of(Everwinter.MOD_ID, name),
                SoundEvent.of(Identifier.of(Everwinter.MOD_ID, name)));
    }

    public static void registerSounds() {
        Everwinter.LOGGER.info("Registering Mod Sounds for " + Everwinter.MOD_ID);
    }
}