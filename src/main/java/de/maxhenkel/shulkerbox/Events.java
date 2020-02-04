package de.maxhenkel.shulkerbox;

import de.maxhenkel.shulkerbox.gui.ShulkerboxContainer;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class Events {

    @SubscribeEvent
    public void onRightClick(BlockEvent.EntityPlaceEvent event) {
        if (event.isCanceled()) {
            return;
        }

        if (!(event.getEntity() instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity player = (PlayerEntity) event.getEntity();

        if (player instanceof FakePlayer) {
            return;
        }

        if (!(event.getPlacedBlock().getBlock() instanceof ShulkerBoxBlock)) {
            return;
        }

        ItemStack stack = Utils.getShulkerBox(player);
        if (stack == null) {
            return;
        }

        if (Config.SERVER.onlySneakPlace.get()) {
            if (!player.isShiftKeyDown()) {
                Utils.openShulkerBox(player, stack);
                event.setCanceled(true);
            }
        }

    }

  /*  @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.isCanceled()) {
            return;
        }

        PlayerEntity player = event.getPlayer();
        Hand hand = event.getHand();

        if (player instanceof FakePlayer) {
            return;
        }

        if (hand.equals(Hand.OFF_HAND) && Utils.getShulkerBox(player, Hand.MAIN_HAND) != null) {
            event.setCanceled(true);
            return;
        }

        ItemStack stack = Utils.getShulkerBox(player, hand);
        if (stack == null) {
            return;
        }

        if (Config.SERVER.onlySneakPlace.get()) {
            if (!player.func_225608_bj_()) {
                Utils.openShulkerBox(player, stack);
                event.setCanceled(true);
            }
        }
    }*/

    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.isCanceled()) {
            return;
        }

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

    @SubscribeEvent
    public void openShulkerBox(PlayerContainerEvent.Open event) {
        if (event.getContainer() instanceof ShulkerboxContainer) {
            PlayerEntity player = event.getPlayer();
            player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.BLOCK_SHULKER_BOX_OPEN, SoundCategory.BLOCKS, 0.5F, player.world.rand.nextFloat() * 0.1F + 0.9F);
        }
    }

    @SubscribeEvent
    public void closeShulkerBox(PlayerContainerEvent.Close event) {
        if (event.getContainer() instanceof ShulkerboxContainer) {
            PlayerEntity player = event.getPlayer();
            player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.BLOCK_SHULKER_BOX_CLOSE, SoundCategory.BLOCKS, 0.5F, player.world.rand.nextFloat() * 0.1F + 0.9F);
        }
    }

}
