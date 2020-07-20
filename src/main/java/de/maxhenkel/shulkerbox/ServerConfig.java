package de.maxhenkel.shulkerbox;

import de.maxhenkel.corelib.config.ConfigBase;
import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig extends ConfigBase {

    public final ForgeConfigSpec.BooleanValue onlySneakPlace;

    public ServerConfig(ForgeConfigSpec.Builder builder) {
        super(builder);
        onlySneakPlace = builder
                .comment("If the Shulker Box should only be placed when sneaking")
                .define("only_sneak_place", true);
    }

}