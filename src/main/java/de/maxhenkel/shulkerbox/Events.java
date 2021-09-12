package de.maxhenkel.shulkerbox;

import de.maxhenkel.shulkerbox.gui.ShulkerboxContainer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class Events {

    public static void onPlaceBlock(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Player player = context.getPlayer();

        if (player instanceof FakePlayer) {
            return;
        }

        ItemStack stack = Utils.getShulkerBox(player, context.getHand());
        if (stack == null) {
            return;
        }

        if (Main.SERVER_CONFIG.onlySneakPlace.get()) {
            if (!player.isShiftKeyDown()) {
                Utils.openShulkerBox(player, stack);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
    }

    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getPlayer();
        InteractionHand hand = event.getHand();

        if (player instanceof FakePlayer) {
            return;
        }

        if (hand.equals(InteractionHand.OFF_HAND) && Utils.getShulkerBox(player, InteractionHand.MAIN_HAND) != null) {
            event.setCanceled(true);
            return;
        }

        if (player.containerMenu instanceof ShulkerboxContainer) {
            return;
        }

        ItemStack stack = Utils.getShulkerBox(player, hand);
        if (stack != null) {
            Utils.openShulkerBox(player, stack);
        }
    }

}