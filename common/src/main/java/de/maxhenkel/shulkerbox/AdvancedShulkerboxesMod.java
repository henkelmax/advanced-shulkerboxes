package de.maxhenkel.shulkerbox;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.shulkerbox.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public abstract class AdvancedShulkerboxesMod {

    public static final String MODID = "shulkerbox";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static Config CONFIG;

    public void init() {
        CONFIG = ConfigBuilder.builder(Config::new).path(Path.of(".", "config", MODID).resolve("%s.properties".formatted(MODID))).build();
    }

}
