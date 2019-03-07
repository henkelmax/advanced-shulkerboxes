package de.maxhenkel.shulkerbox;

import de.maxhenkel.shulkerbox.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Main.MODID, version = Main.VERSION, updateJSON = Main.UPDATE_JSON)
public class Main {
    public static final String UPDATE_JSON = "http://maxhenkel.de/update/shulkerbox.json";
    public static final String MODID = "shulkerbox";
    public static final String VERSION = "1.3.1";

    private Config config;
    private Events events;

    @Instance
    private static Main instance;

    @SidedProxy(clientSide = "de.maxhenkel.shulkerbox.proxy.ClientProxy", serverSide = "de.maxhenkel.shulkerbox.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        Configuration c = null;
        try {
            c = new Configuration(event.getSuggestedConfigurationFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
        config = new Config(c);

        instance = this;
        this.events = new Events();
        MinecraftForge.EVENT_BUS.register(events);

        proxy.preinit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        proxy.postinit(event);
    }

    public Config getConfig() {
        return config;
    }

    public Events getEvents() {
        return events;
    }

    public static Main getInstance() {
        return instance;
    }

}
