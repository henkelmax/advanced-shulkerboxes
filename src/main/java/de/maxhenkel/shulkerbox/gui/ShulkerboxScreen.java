package de.maxhenkel.shulkerbox.gui;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ShulkerboxScreen extends ScreenBase<ShulkerboxContainer> {

    public static final ResourceLocation DEFAULT_IMAGE = new ResourceLocation("textures/gui/container/shulker_box.png");

    private PlayerInventory playerInventory;

    public ShulkerboxScreen(PlayerInventory playerInventory, ShulkerboxContainer shulkerboxContainer, ITextComponent name) {
        super(DEFAULT_IMAGE, shulkerboxContainer, playerInventory, name);

        this.playerInventory = playerInventory;
        xSize = 176;
        ySize = 166;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        super.drawGuiContainerForegroundLayer(x, y);

        font.drawString(getTitle().getFormattedText(), 8.0F, 6.0F, FONT_COLOR);
        font.drawString(playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (ySize - 96 + 3), FONT_COLOR);
    }
}