package de.maxhenkel.shulkerbox;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

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

        public ServerConfig(ForgeConfigSpec.Builder builder) {
            onlySneakPlace = builder
                    .comment("If the shulkerbox should only be placed when sneaking")
                    .define("only_sneak_place", true);
        }
    }

    public static class ClientConfig {

        public ClientConfig(ForgeConfigSpec.Builder builder) {

        }
    }

}
