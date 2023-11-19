package de.maxhenkel.shulkerbox;

import de.maxhenkel.corelib.ClientRegistry;
import de.maxhenkel.corelib.CommonRegistry;
import de.maxhenkel.shulkerbox.gui.ShulkerboxContainer;
import de.maxhenkel.shulkerbox.gui.ShulkerboxScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(Main.MODID)
public class Main {

    public static final String MODID = "shulkerbox";

    private static final DeferredRegister<MenuType<?>> MENU_TYPE_REGISTER = DeferredRegister.create(BuiltInRegistries.MENU, Main.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<ShulkerboxContainer>> SHULKERBOX_CONTAINER = MENU_TYPE_REGISTER.register("shulkerbox", () ->
            IMenuTypeExtension.create((windowId, inv, data) -> new ShulkerboxContainer(windowId, inv))
    );
    public static ServerConfig SERVER_CONFIG;

    public Main() {
        SERVER_CONFIG = CommonRegistry.registerConfig(ModConfig.Type.SERVER, ServerConfig.class);

        if (FMLEnvironment.dist.isClient()) {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(Main.this::clientSetup);
        }

        MENU_TYPE_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @OnlyIn(Dist.CLIENT)
    public void clientSetup(FMLClientSetupEvent event) {
        ClientRegistry.<ShulkerboxContainer, ShulkerboxScreen>registerScreen(Main.SHULKERBOX_CONTAINER.get(), ShulkerboxScreen::new);
    }

}
