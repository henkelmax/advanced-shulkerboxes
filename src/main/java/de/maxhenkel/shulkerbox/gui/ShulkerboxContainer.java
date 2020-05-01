package de.maxhenkel.shulkerbox.gui;

import de.maxhenkel.shulkerbox.Main;
import de.maxhenkel.shulkerbox.ShulkerBoxInventory;
import de.maxhenkel.shulkerbox.Utils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ShulkerBoxSlot;

public class ShulkerboxContainer extends ContainerBase {

    private IInventory albumInventory;

    public ShulkerboxContainer(int id, IInventory playerInventory, IInventory shulkerboxInventory) {
        super(Main.SHULKERBOX_CONTAINER, id, playerInventory, shulkerboxInventory);
        this.albumInventory = shulkerboxInventory;

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlot(new ShulkerBoxSlot(shulkerboxInventory, x + y * 9, 8 + x * 18, 18 + y * 18));
            }
        }

        addInvSlots();
    }

    public ShulkerboxContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, new ShulkerBoxInventory(playerInventory.player, Utils.getShulkerBox(playerInventory.player)));
    }

    @Override
    public int getInventorySize() {
        return 27;
    }

    @Override
    public int getInvOffset() {
        return 0;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return albumInventory.isUsableByPlayer(playerIn);
    }
}
