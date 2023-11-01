package de.maxhenkel.shulkerbox;

import de.maxhenkel.corelib.config.ConfigBase;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig extends ConfigBase {

    public final ModConfigSpec.BooleanValue onlySneakPlace;

    public ServerConfig(ModConfigSpec.Builder builder) {
        super(builder);
        onlySneakPlace = builder
                .comment("If the Shulker Box should only be placed when sneaking")
                .define("only_sneak_place", true);
    }

}