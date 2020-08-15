package de.maxhenkel.shulkerbox;

import de.maxhenkel.shulkerbox.gui.ShulkerboxContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class Events {

    public static void onPlaceBlock(ItemUseContext context, CallbackInfoReturnable<ActionResultType> cir) {
        PlayerEntity player = context.getPlayer();

        if (player instanceof FakePlayer) {
            return;
        }

        ItemStack stack = Utils.getShulkerBox(player, context.getHand());
        if (stack == null) {
            return;
        }

        if (Main.SERVER_CONFIG.onlySneakPlace.get()) {
            if (!player.isSneaking()) {
                Utils.openShulkerBox(player, stack);
                cir.setReturnValue(ActionResultType.SUCCESS);
            }
        }
    }

    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        PlayerEntity player = event.getPlayer();
        Hand hand = event.getHand();

        if (player instanceof FakePlayer) {
            return;
        }

        if (hand.equals(Hand.OFF_HAND) && Utils.getShulkerBox(player, Hand.MAIN_HAND) != null) {
            event.setCanceled(true);
            return;
        }

        if (player.openContainer instanceof ShulkerboxContainer) {
            return;
        }

        ItemStack stack = Utils.getShulkerBox(player, hand);
        if (stack != null) {
            Utils.openShulkerBox(player, stack);
        }
    }

}