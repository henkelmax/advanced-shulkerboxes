package de.maxhenkel.shulkerbox;

import net.minecraft.block.Block;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class Events {

    @SubscribeEvent
    public void onRightClick(BlockEvent.PlaceEvent event) {
        if (event.isCanceled()) {
            return;
        }

        EntityPlayer player = event.getPlayer();

        if (player instanceof FakePlayer) {
            return;
        }

        if (!(event.getPlacedBlock().getBlock() instanceof BlockShulkerBox)) {
            return;
        }

        ItemStack stack = player.getHeldItem(event.getHand());

        if (!isShulkerBox(stack)) {
            return;
        }

        if (Config.onlySneakPlace) {
            if (!player.isSneaking()) {
                displayGUI(player, stack);
                event.setCanceled(true);
            }
        }

    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.isCanceled()) {
            return;
        }

        EntityPlayer player = event.getEntityPlayer();

        if (player instanceof FakePlayer) {
            return;
        }

        if (!isShulkerBox(event.getItemStack())) {
            return;
        }

        ItemStack stackMain = player.getHeldItem(EnumHand.MAIN_HAND);

        if (isShulkerBox(stackMain)) {
            displayGUI(player, stackMain);
        }
    }

    private boolean isShulkerBox(ItemStack stack) {
        if (stack == null) {
            return false;
        }

        Item item = stack.getItem();

        if (Block.getBlockFromItem(item) instanceof BlockShulkerBox) {
            return true;
        }

        return false;
    }

    private void displayGUI(EntityPlayer player, ItemStack stack) {
        if (!player.world.isRemote) {
            player.displayGUIChest(new InventoryShulkerBox(stack));
        }
    }

}
