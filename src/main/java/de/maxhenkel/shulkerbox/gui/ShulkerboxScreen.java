package de.maxhenkel.shulkerbox.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ShulkerboxScreen extends ContainerScreen<ShulkerboxContainer> {

    protected static final int FONT_COLOR = 4210752;
    public static final ResourceLocation DEFAULT_IMAGE = new ResourceLocation("textures/gui/container/shulker_box.png");

    private PlayerInventory playerInventory;

    public ShulkerboxScreen(PlayerInventory playerInventory, ShulkerboxContainer shulkerboxContainer, ITextComponent name) {
        super(shulkerboxContainer, playerInventory, name);

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

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(DEFAULT_IMAGE);
        blit(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }
}