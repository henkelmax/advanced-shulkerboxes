package de.maxhenkel.shulkerbox;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class MixinConnector implements IMixinConnector {

    @Override
    public void connect() {
        Mixins.addConfiguration("%s.mixins.json".formatted(AdvancedShulkerboxesMod.MODID));
    }

}

