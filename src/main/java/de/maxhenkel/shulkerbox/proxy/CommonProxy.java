package de.maxhenkel.shulkerbox.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preinit(FMLPreInitializationEvent event) {
		
	}

	public void init(FMLInitializationEvent event) {

	}

	public void postinit(FMLPostInitializationEvent event) {

	}

	/*private void registerItem(Item i) {
		GameRegistry.register(i);
	}

	private void registerBlock(Block b) {
		GameRegistry.register(b);
		GameRegistry.register(new ItemBlock(b).setRegistryName(b.getRegistryName()));
	}*/

}
