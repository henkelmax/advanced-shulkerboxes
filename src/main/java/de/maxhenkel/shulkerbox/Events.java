package de.maxhenkel.shulkerbox;

import de.maxhenkel.shulkerbox.UpdateChecker.IUpdateCheckResult;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Events {

	public static final String KEY_CHECK_UPDATES = "check_updates";
	public static final String KEY_ONLY_SNEAK_PLACE = "only_sneak_place";

	private boolean checkUpdates;
	private boolean updateShown;
	
	private boolean onlySneakPlace;

	public Events() {
		this.checkUpdates = Main.getInstance().getConfig().getBoolean(KEY_CHECK_UPDATES, true);
		this.onlySneakPlace = Main.getInstance().getConfig().getBoolean(KEY_ONLY_SNEAK_PLACE, true);
		this.updateShown = false;
	}

	@SubscribeEvent
	public void onRightClick(PlayerInteractEvent event) {
		if(event.isCanceled()){
			return;
		}
		
		EntityPlayer player = event.getEntityPlayer();

		ItemStack stack = event.getItemStack();

		if (stack == null) {
			return;
		}

		Item item = stack.getItem();
		
		if(!(item instanceof ItemShulkerBox)){
			return;
		}
		
		if(event instanceof PlayerInteractEvent.RightClickItem){
			displayGUI(player, stack);
			//event.setCanceled(true);
		}else if(event instanceof PlayerInteractEvent.RightClickBlock){
			if(onlySneakPlace){
				if(!player.isSneaking()){
					displayGUI(player, stack);
					event.setCanceled(true);
				}
			}
		}
		
	}
	
	private void displayGUI(EntityPlayer player, ItemStack stack){
		if(!player.world.isRemote){
			player.displayGUIChest(new InventoryShulkerBox(stack));
		}
	}

	@SubscribeEvent
	public void playerJoin(EntityJoinWorldEvent event) {
		if (event.isCanceled()) {
			return;
		}

		if (!(event.getEntity() instanceof EntityPlayer)) {
			return;
		}

		if (!event.getWorld().isRemote) {
			return;
		}

		final EntityPlayer player = (EntityPlayer) event.getEntity();

		if (player.isDead) {
			return;
		}

		if (!checkUpdates) {
			return;
		}

		if (updateShown) {
			return;
		}

		UpdateChecker checker = new UpdateChecker(new IUpdateCheckResult() {
			@Override
			public void onResult(boolean isAvailable, String updateURL) {
				if(!isAvailable){
					return;
				}
				
				String msg = "[" + new TextComponentTranslation("message.name").getFormattedText()
						+ "] " + new TextComponentTranslation("message.update").getFormattedText()
						+ " ";

				ClickEvent openUrl = new ClickEvent(Action.OPEN_URL,
						updateURL);
				Style style = new Style();

				style.setClickEvent(openUrl);
				style.setUnderlined(true);
				style.setColor(TextFormatting.GREEN);
				style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(
						new TextComponentTranslation("message.update.hover").getFormattedText())));
				TextComponentTranslation comp = new TextComponentTranslation("message.download");
				comp.setStyle(style);
				player.sendMessage(new TextComponentString(msg).appendSibling(comp));
			}
		}, Main.VERSION_NUMBER, Main.UPDATE_ADDRESS);

		checker.start();
		updateShown = true;
	}

}
