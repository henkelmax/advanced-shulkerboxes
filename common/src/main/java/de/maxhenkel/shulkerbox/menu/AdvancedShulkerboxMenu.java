package de.maxhenkel.shulkerbox.menu;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class AdvancedShulkerboxMenu extends ShulkerBoxMenu {

    protected ServerPlayer player;
    protected ItemStack shulkerBox;

    public AdvancedShulkerboxMenu(int id, Inventory inventory, ServerPlayer player, ItemStack shulkerBox) {
        super(id, inventory, new AdvancedShulkerboxContainer(player, shulkerBox, 3 * 9));
        this.player = player;
        this.shulkerBox = shulkerBox;
    }

    @Override
    public void clicked(int slot, int mouseButton, ClickType clickType, Player player) {
        if (slot < 0 || slot >= slots.size()) {
            super.clicked(slot, mouseButton, clickType, player);
            return;
        }
        ItemStack stack = slots.get(slot).getItem();

        if (stack == shulkerBox) {
            return;
        }
        super.clicked(slot, mouseButton, clickType, player);
    }

    public static void open(ServerPlayer player, ItemStack shulkerBox) {
        player.openMenu(new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return shulkerBox.getHoverName();
            }

            @Override
            public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                if (!(player instanceof ServerPlayer serverPlayer)) {
                    return null;
                }
                return new AdvancedShulkerboxMenu(i, inventory, serverPlayer, shulkerBox);
            }
        });
    }

}
