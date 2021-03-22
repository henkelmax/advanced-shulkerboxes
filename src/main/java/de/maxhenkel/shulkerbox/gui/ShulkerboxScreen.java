package de.maxhenkel.shulkerbox.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ShulkerboxScreen extends ScreenBase<ShulkerboxContainer> {

    public static final ResourceLocation DEFAULT_IMAGE = new ResourceLocation("textures/gui/container/shulker_box.png");

    private PlayerInventory playerInventory;

    public ShulkerboxScreen(ShulkerboxContainer shulkerboxContainer, PlayerInventory playerInventory, ITextComponent name) {
        super(DEFAULT_IMAGE, shulkerboxContainer, playerInventory, name);

        this.playerInventory = playerInventory;
        imageWidth = 176;
        imageHeight = 166;
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int x, int y) {
        font.draw(matrixStack, getTitle(), 8F, 6F, FONT_COLOR);
        font.draw(matrixStack, playerInventory.getDisplayName(), 8F, (float) (imageHeight - 96 + 4), FONT_COLOR);
    }

}