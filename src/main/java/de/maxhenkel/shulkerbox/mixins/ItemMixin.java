package de.maxhenkel.shulkerbox.mixins;

import de.maxhenkel.shulkerbox.Events;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class ItemMixin {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void useOn(ItemUseContext context, CallbackInfoReturnable<ActionResultType> cir) {
        Events.onPlaceBlock(context, cir);
    }

}
