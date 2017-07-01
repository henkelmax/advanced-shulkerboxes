package de.maxhenkel.shulkerbox.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{
 
	public void preinit(FMLPreInitializationEvent event) {
		super.preinit(event);
	}
	
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}
	
	public void postinit(FMLPostInitializationEvent event) {
		super.postinit(event);
	}
	
	/*private void addRenderItem(Item i){
		String name=i.getUnlocalizedName().replace("item.", "");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(i, 0, new ModelResourceLocation(Main.MODID +":" +name, "inventory"));
	}
	
	private void addRenderBlock(Block b){
		String name=b.getUnlocalizedName().replace("tile.", "");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(b), 0, new ModelResourceLocation(Main.MODID +":" +name, "inventory"));
	}*/

}
