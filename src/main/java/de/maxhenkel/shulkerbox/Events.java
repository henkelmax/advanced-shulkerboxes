package de.maxhenkel.shulkerbox;

import de.maxhenkel.shulkerbox.gui.ShulkerboxContainer;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class Events {

    @SubscribeEvent
    public void onRightClick(BlockEvent.EntityPlaceEvent event) {
        if (event.isCanceled()) {
            return;
        }

        if (!(event.getEntity() instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity player = (PlayerEntity) event.getEntity();

        if (player instanceof FakePlayer) {
            return;
        }

        if (!(event.getPlacedBlock().getBlock() instanceof ShulkerBoxBlock)) {
            return;
        }

        ItemStack stack = getShulkerBox(player);
        if (stack == null) {
            return;
        }

        if (Config.onlySneakPlace) {
            if (!player.isCrouching()) {
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

        PlayerEntity player = event.getPlayer();

        if (player instanceof FakePlayer) {
            return;
        }

        if (!isShulkerBox(event.getItemStack())) {
            return;
        }

        ItemStack stack = getShulkerBox(player);
        if (stack != null) {
            displayGUI(player, stack);
        }
    }

    public static ItemStack getShulkerBox(PlayerEntity player) {
        ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
        if (isShulkerBox(stack)) {
            return stack;
        }
        stack = player.getHeldItem(Hand.OFF_HAND);
        if (isShulkerBox(stack)) {
            return stack;
        }
        return null;
    }

    private static boolean isShulkerBox(ItemStack stack) {
        if (stack == null) {
            return false;
        }

        Item item = stack.getItem();

        if (Block.getBlockFromItem(item) instanceof ShulkerBoxBlock) {
            return true;
        }

        return false;
    }

    private void displayGUI(PlayerEntity player, ItemStack stack) {
        if (!player.world.isRemote && player instanceof ServerPlayerEntity) {
            NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
                @Nullable
                @Override
                public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                    return new ShulkerboxContainer(id, playerInventory, new ShulkerBoxInventory(player, stack));
                }

                @Override
                public ITextComponent getDisplayName() {
                    return new TranslationTextComponent(stack.getTranslationKey());
                }
            });
        }
    }

}
