package de.maxhenkel.shulkerbox;

import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AdvancedShulkerboxesMod.MODID)
public class NeoforgeAdvancedShulkerboxesMod extends AdvancedShulkerboxesMod {

    public NeoforgeAdvancedShulkerboxesMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        init();
    }

}