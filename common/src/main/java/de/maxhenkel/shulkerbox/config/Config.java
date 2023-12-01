package de.maxhenkel.shulkerbox.config;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.entry.ConfigEntry;

public class Config {

    public final ConfigEntry<Boolean> sneakPlace;

    public Config(ConfigBuilder builder) {
        sneakPlace = builder.booleanEntry("sneak_place", true, "When enabled, shulker boxes will only be placed when sneaking");
    }

}
