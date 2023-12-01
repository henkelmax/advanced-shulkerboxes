package de.maxhenkel.shulkerbox;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AdvancedShulkerboxesMod.MODID)
public class ForgeAdvancedShulkerboxesMod extends AdvancedShulkerboxesMod {

    public ForgeAdvancedShulkerboxesMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        init();
    }

}