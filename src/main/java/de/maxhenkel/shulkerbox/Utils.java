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
import net.minecraftforge.fml.network.NetworkHooks;

public class Utils {

    public static ItemStack getShulkerBox(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (isShulkerBox(stack)) {
            return stack;
        }
        return null;
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

    public static boolean isShulkerBox(ItemStack stack) {
        if (stack == null) {
            return false;
        }

        Item item = stack.getItem();

        if (Block.getBlockFromItem(item) instanceof ShulkerBoxBlock) {
            return true;
        }

        return false;
    }

    public static void openShulkerBox(PlayerEntity player, ItemStack stack) {
        if (!player.world.isRemote && player instanceof ServerPlayerEntity) {
            NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
                @Override
                public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                    return new ShulkerboxContainer(id, playerInventory, new AdvancedShulkerBoxInventory(player, stack));
                }

                @Override
                public ITextComponent getDisplayName() {
                    return stack.getDisplayName();
                }
            });
        }
    }

}