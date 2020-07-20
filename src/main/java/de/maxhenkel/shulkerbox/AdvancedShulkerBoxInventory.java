package de.maxhenkel.shulkerbox;

import de.maxhenkel.corelib.inventory.ShulkerBoxInventory;
import de.maxhenkel.shulkerbox.gui.ShulkerboxContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class AdvancedShulkerBoxInventory extends ShulkerBoxInventory {

    public AdvancedShulkerBoxInventory(PlayerEntity player, ItemStack shulkerBox) {
        super(player, shulkerBox);
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ShulkerboxContainer(i, playerInventory);
    }

}