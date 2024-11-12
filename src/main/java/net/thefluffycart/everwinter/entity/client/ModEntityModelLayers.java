package net.thefluffycart.everwinter.entity.client;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.thefluffycart.everwinter.Everwinter;

public class ModEntityModelLayers {
    public static final EntityModelLayer ICEOLOGER =
            new EntityModelLayer(Identifier.of(Everwinter.MOD_ID, "iceologer"), "main");
    public static final EntityModelLayer ICE_CHUNK =
            new EntityModelLayer(Identifier.of(Everwinter.MOD_ID, "ice_chunk"), "main");
}
