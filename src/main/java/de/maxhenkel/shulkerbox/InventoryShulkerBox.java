package de.maxhenkel.shulkerbox;

import net.minecraft.block.Block;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerShulkerBox;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;

import javax.annotation.Nullable;
import java.util.Random;

public class InventoryShulkerBox implements IInventory, IInteractionObject {

    private NonNullList<ItemStack> items;
    private ItemStack shulkerBox;
    private int invSize;
    private NBTTagCompound blockEntityTag;

    private ResourceLocation lootTable;
    private long lootTableSeed;


    public InventoryShulkerBox(EntityPlayer player, ItemStack shulkerBox) {
        this.shulkerBox = shulkerBox;
        this.invSize = 27;
        this.items = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);

        NBTTagCompound c = shulkerBox.getTag();

        if (c == null) {
            return;
        }

        if (!c.hasKey("BlockEntityTag")) {
            return;
        }

        blockEntityTag = c.getCompound("BlockEntityTag");

        if (blockEntityTag.hasKey("Items")) {
            ItemStackHelper.loadAllItems(blockEntityTag, items);
        } else if (blockEntityTag.hasKey("LootTable")) {
            lootTable = new ResourceLocation(blockEntityTag.getString("LootTable"));
            lootTableSeed = blockEntityTag.getLong("LootTableSeed");
            fillWithLoot(player);
            blockEntityTag.removeTag("LootTable");
            blockEntityTag.removeTag("LootTableSeed");
        }
    }

    public void fillWithLoot(@Nullable EntityPlayer player) {
        if (lootTable != null) {
            LootTable loottable = player.world.getServer().getLootTableManager().getLootTableFromLocation(lootTable);
            lootTable = null;
            Random random;

            if (lootTableSeed == 0L) {
                random = new Random();
            } else {
                random = new Random(lootTableSeed);
            }

            LootContext.Builder builder = new LootContext.Builder((WorldServer) player.world);

            if (player != null) {
                builder.withLuck(player.getLuck()).withPlayer(player);
            }

            loottable.fillInventory(this, random, builder.build());
            markDirty();
        }
    }


    @Override
    public ITextComponent getName() {
        return shulkerBox.getDisplayName();
    }

    @Override
    public boolean hasCustomName() {
        return shulkerBox.hasDisplayName();
    }

    @Override
    public ITextComponent getDisplayName() {
        return getName();
    }

    @Nullable
    @Override
    public ITextComponent getCustomName() {
        return hasCustomName() ? getCustomName() : null;
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
            NBTTagCompound tag = shulkerBox.getOrCreateTag();
            tag.setTag("BlockEntityTag", blockEntityTag = new NBTTagCompound());
        }

        ItemStackHelper.saveAllItems(blockEntityTag, items, true);
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return !(Block.getBlockFromItem(stack.getItem()) instanceof BlockShulkerBox);
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        items.clear();
        markDirty();
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerShulkerBox(playerInventory, this, playerIn);
    }

    @Override
    public String getGuiID() {
        return "minecraft:shulker_box";
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        for (EnumHand hand : EnumHand.values()) {
            if (player.getHeldItem(hand).equals(shulkerBox)) {
                return true;
            }
        }
        return false;
    }

}
