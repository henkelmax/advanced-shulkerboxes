package de.maxhenkel.shulkerbox;

import de.maxhenkel.corelib.inventory.ShulkerBoxInventory;
import de.maxhenkel.shulkerbox.gui.ShulkerboxContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class AdvancedShulkerBoxInventory extends ShulkerBoxInventory {

    public AdvancedShulkerBoxInventory(Player player, ItemStack shulkerBox) {
        super(player, shulkerBox);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
        return new ShulkerboxContainer(i, playerInventory);
    }

}