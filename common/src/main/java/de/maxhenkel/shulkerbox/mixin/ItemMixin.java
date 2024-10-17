package de.maxhenkel.shulkerbox.mixin;

import de.maxhenkel.shulkerbox.menu.AdvancedShulkerboxMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void use(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        if (!((Object) this instanceof BlockItem blockItem)) {
            return;
        }
        if (!(blockItem.getBlock() instanceof ShulkerBoxBlock)) {
            return;
        }
        //TODO Check for fake players
        ItemStack itemInHand = player.getItemInHand(interactionHand);

        if (itemInHand.getCount() != 1) {
            return;
        }

        if (player instanceof ServerPlayer serverPlayer) {
            AdvancedShulkerboxMenu.open(serverPlayer, itemInHand);
        }
        cir.setReturnValue(InteractionResult.SUCCESS);
    }

}
