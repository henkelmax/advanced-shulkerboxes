package de.maxhenkel.shulkerbox;

import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.LootTable;

import javax.annotation.Nullable;

public class ShulkerBoxInventory implements IInventory{

    private NonNullList<ItemStack> items;
    private ItemStack shulkerBox;
    private int invSize;
    private CompoundNBT blockEntityTag;

    private ResourceLocation lootTable;
    private long lootTableSeed;


    public ShulkerBoxInventory(PlayerEntity player, ItemStack shulkerBox) {
        this.shulkerBox = shulkerBox;
        this.invSize = 27;
        this.items = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);

        CompoundNBT c = shulkerBox.getTag();

        if (c == null) {
            return;
        }

        if (!c.contains("BlockEntityTag")) {
            return;
        }

        blockEntityTag = c.getCompound("BlockEntityTag");

        if (blockEntityTag.contains("Items")) {
            ItemStackHelper.loadAllItems(blockEntityTag, items);
        } else if (blockEntityTag.contains("LootTable")) {
            lootTable = new ResourceLocation(blockEntityTag.getString("LootTable"));
            lootTableSeed = blockEntityTag.getLong("LootTableSeed");
            fillWithLoot(player);
            blockEntityTag.remove("LootTable");
            blockEntityTag.remove("LootTableSeed");
        }
    }

    public void fillWithLoot(@Nullable PlayerEntity player) {
        if (lootTable != null) {
            LootTable loottable = player.world.getServer().getLootTableManager().getLootTableFromLocation(lootTable);
            lootTable = null;

            LootContext.Builder builder = new LootContext.Builder((ServerWorld) player.world);

            if (lootTableSeed != 0L) {
                builder.withSeed(lootTableSeed);
            }

            if (player != null) {
                builder.withLuck(player.getLuck()).withParameter(LootParameters.field_216281_a, player);
            }

            loottable.func_216118_a(this, builder.build(LootParameterSets.field_216261_b));
            markDirty();
        }
    }

    @Override
    public int getSizeInventory() {
        return invSize;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return items.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack itemstack = ItemStackHelper.getAndSplit(items, index, count);
        markDirty();
        return itemstack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(items, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        items.set(index, stack);
        markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        if (blockEntityTag == null) {
            CompoundNBT tag = shulkerBox.getOrCreateTag();
            tag.put("BlockEntityTag", blockEntityTag = new CompoundNBT());
        }

        ItemStackHelper.saveAllItems(blockEntityTag, items, true);
    }

    @Override
    public void openInventory(PlayerEntity player) {

    }

    @Override
    public void closeInventory(PlayerEntity player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return !(Block.getBlockFromItem(stack.getItem()) instanceof ShulkerBoxBlock);
    }

    @Override
    public void clear() {
        items.clear();
        markDirty();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        for (Hand hand : Hand.values()) {
            if (player.getHeldItem(hand).equals(shulkerBox)) {
                return true;
            }
        }
        return false;
    }
}
