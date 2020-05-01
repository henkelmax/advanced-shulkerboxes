package de.maxhenkel.shulkerbox.gui;

import de.maxhenkel.shulkerbox.Config;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ShulkerBoxSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class SlotAdvancedShulkerbox extends ShulkerBoxSlot {
    public SlotAdvancedShulkerbox(IInventory inventoryIn, int slotIndexIn, int xPosition, int yPosition) {
        super(inventoryIn, slotIndexIn, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (!super.isItemValid(stack)) {
            return false;
        }

        if (Config.SERVER.isWhitelistedShulkerbox(stack.getItem())) {
            CompoundNBT tag = stack.getTag();
            return tag == null || !tag.contains("BlockEntityTag");
        }

        return true;
    }
}
