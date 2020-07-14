package de.maxhenkel.shulkerbox;

import de.maxhenkel.shulkerbox.gui.ShulkerboxContainer;
import de.maxhenkel.shulkerbox.gui.ShulkerboxScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Main.MODID)
public class Main {

    public static final String MODID = "shulkerbox";

    public static ContainerType SHULKERBOX_CONTAINER;

    public Main() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ContainerType.class, this::registerContainers);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(Main.this::clientSetup));
    }

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new Events());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void clientSetup(FMLClientSetupEvent event) {
        ScreenManager.IScreenFactory factory = (ScreenManager.IScreenFactory<ShulkerboxContainer, ShulkerboxScreen>) (container, playerInventory, name) -> new ShulkerboxScreen(playerInventory, container, name);
        ScreenManager.registerFactory(Main.SHULKERBOX_CONTAINER, factory);
    }

    @SubscribeEvent
    public void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
        SHULKERBOX_CONTAINER = new ContainerType<>((id, playerInventory) -> new ShulkerboxContainer(id, playerInventory));
        SHULKERBOX_CONTAINER.setRegistryName(new ResourceLocation(Main.MODID, "shulkerbox"));
        event.getRegistry().register(SHULKERBOX_CONTAINER);
    }
}
