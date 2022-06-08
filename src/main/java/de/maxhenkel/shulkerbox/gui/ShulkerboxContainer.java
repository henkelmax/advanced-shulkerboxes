package de.maxhenkel.shulkerbox.gui;

import de.maxhenkel.corelib.inventory.ContainerBase;
import de.maxhenkel.corelib.inventory.LockedSlot;
import de.maxhenkel.shulkerbox.AdvancedShulkerBoxInventory;
import de.maxhenkel.shulkerbox.Main;
import de.maxhenkel.shulkerbox.Utils;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ShulkerBoxSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ShulkerboxContainer extends ContainerBase {

    public ShulkerboxContainer(int id, Inventory playerInventory, Container shulkerboxInventory) {
        super(Main.SHULKERBOX_CONTAINER.get(), id, playerInventory, shulkerboxInventory);

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlot(new ShulkerBoxSlot(shulkerboxInventory, x + y * 9, 8 + x * 18, 18 + y * 18));
            }
        }

        for (int l = 0; l < 3; l++) {
            for (int j1 = 0; j1 < 9; j1++) {
                addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 102 + l * 18 - 18));
            }
        }

        int locked = getLockedSlot(playerInventory.player);
        for (int i = 0; i < 9; i++) {
            int x = 8 + i * 18;
            int y = 142;
            if (i == locked) {
                addSlot(new LockedSlot(playerInventory, i, x, y));
            } else {
                addSlot(new Slot(playerInventory, i, x, y));
            }
        }
    }

    public ShulkerboxContainer(int id, Inventory playerInventory) {
        this(id, playerInventory, new AdvancedShulkerBoxInventory(playerInventory.player, Utils.getShulkerBox(playerInventory.player)));
    }

    public static int getLockedSlot(Player player) {
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (Utils.isShulkerBox(stack)) {
            return player.getInventory().selected;
        }
        return -1;
    }

}