package de.maxhenkel.shulkerbox;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Config {

    public static final ServerConfig SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    public static final ClientConfig CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;

    static {
        Pair<ServerConfig, ForgeConfigSpec> specPairServer = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SERVER_SPEC = specPairServer.getRight();
        SERVER = specPairServer.getLeft();

        Pair<ClientConfig, ForgeConfigSpec> specPairClient = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT_SPEC = specPairClient.getRight();
        CLIENT = specPairClient.getLeft();
    }

    public static class ServerConfig {
        public ForgeConfigSpec.BooleanValue onlySneakPlace;
        public ForgeConfigSpec.ConfigValue<List<? extends String>> shulkerboxWhitelist;

        public ServerConfig(ForgeConfigSpec.Builder builder) {
            onlySneakPlace = builder
                    .comment("If the shulkerbox should only be placed when sneaking")
                    .define("only_sneak_place", true);
            shulkerboxWhitelist = builder
                    .comment("The items that should be able to be opened")
                    .comment("Note that this only works with containers that have 27 slots")
                    .defineList("shulkerbox_whitelist", Arrays.asList(
                            "minecraft:shulker_box",
                            "minecraft:white_shulker_box",
                            "minecraft:orange_shulker_box",
                            "minecraft:magenta_shulker_box",
                            "minecraft:light_blue_shulker_box",
                            "minecraft:yellow_shulker_box",
                            "minecraft:lime_shulker_box",
                            "minecraft:pink_shulker_box",
                            "minecraft:gray_shulker_box",
                            "minecraft:light_gray_shulker_box",
                            "minecraft:cyan_shulker_box",
                            "minecraft:purple_shulker_box",
                            "minecraft:blue_shulker_box",
                            "minecraft:brown_shulker_box",
                            "minecraft:green_shulker_box",
                            "minecraft:red_shulker_box",
                            "minecraft:black_shulker_box"
                    ), Objects::nonNull);
        }

        public boolean isWhitelistedShulkerbox(ForgeRegistryEntry entry) {
            return shulkerboxWhitelist.get().contains(entry.getRegistryName().toString());
        }
    }


    public static class ClientConfig {
        public ClientConfig(ForgeConfigSpec.Builder builder) {

        }
    }

}
