package de.maxhenkel.shulkerbox;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerShulkerBox;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;

import javax.annotation.Nullable;
import java.util.Random;

public class InventoryShulkerBox implements IInventory, IInteractionObject {

    private World world;
    private NonNullList<ItemStack> items;
    private boolean hasCustomName;
    private String name;
    private ItemStack shulkerBox;
    private int invSize;
    private NBTTagCompound blockEntityTag;

    private ResourceLocation lootTable;
    private long lootTableSeed;

    public InventoryShulkerBox(EntityPlayer player, ItemStack shulkerBox) {
        this.world = player.world;
        this.shulkerBox = shulkerBox;
        this.invSize = 27;
        this.items = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
        this.name = shulkerBox.getUnlocalizedName() + ".name";
        this.hasCustomName = false;

        NBTTagCompound c = shulkerBox.getTagCompound();

        if (c == null) {
            return;
        }

        if (c.hasKey("display")) {
            NBTTagCompound display = c.getCompoundTag("display");

            if (display.hasKey("Name")) {
                this.hasCustomName = true;
                this.name = display.getString("Name");
            }
        }

        if (!c.hasKey("BlockEntityTag")) {
            return;
        }

        this.blockEntityTag = c.getCompoundTag("BlockEntityTag");

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
            LootTable loottable = this.world.getLootTableManager().getLootTableFromLocation(lootTable);
            lootTable = null;
            Random random;

            if (this.lootTableSeed == 0L) {
                random = new Random();
            } else {
                random = new Random(this.lootTableSeed);
            }

            LootContext.Builder builder = new LootContext.Builder((WorldServer) this.world);

            if (player != null) {
                builder.withLuck(player.getLuck()).withPlayer(player);
            }

            loottable.fillInventory(this, random, builder.build());
            markDirty();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasCustomName() {
        return hasCustomName;
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]);
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
            if (shulkerBox.hasTagCompound()) {
                NBTTagCompound compound = new NBTTagCompound();
                shulkerBox.getTagCompound().setTag("BlockEntityTag", compound);
                this.blockEntityTag = compound;
            } else {
                NBTTagCompound compound = new NBTTagCompound();
                NBTTagCompound bet = new NBTTagCompound();
                compound.setTag("BlockEntityTag", bet);
                shulkerBox.setTagCompound(compound);
                this.blockEntityTag = bet;
            }
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
        if (stack.getItem() instanceof ItemShulkerBox) {
            return false;
        }

        return true;
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
        for (ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        if (shulkerBox.isEmpty()) {
            return false;
        }
        for (EnumHand hand : EnumHand.values()) {
            if (player.getHeldItem(hand).equals(shulkerBox)) {
                return true;
            }
        }
        return false;
    }

}
