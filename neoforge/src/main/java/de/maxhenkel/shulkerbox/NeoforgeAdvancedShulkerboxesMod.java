package de.maxhenkel.shulkerbox;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(AdvancedShulkerboxesMod.MODID)
public class NeoforgeAdvancedShulkerboxesMod extends AdvancedShulkerboxesMod {

    public NeoforgeAdvancedShulkerboxesMod(IEventBus eventBus) {
        eventBus.addListener(this::commonSetup);
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        init();
    }

}