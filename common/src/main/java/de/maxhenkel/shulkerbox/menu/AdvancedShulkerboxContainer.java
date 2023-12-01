package de.maxhenkel.shulkerbox.menu;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import javax.annotation.Nullable;

public class AdvancedShulkerboxContainer implements Container {

    public static final String ITEMS_TAG = "Items";
    public static final String LOOT_TABLE_TAG = "LootTable";
    public static final String LOOT_TABLE_SEED_TAG = "LootTableSeed";

    protected NonNullList<ItemStack> items;
    protected ItemStack shulkerBox;
    protected int invSize;
    protected CompoundTag blockEntityTag;

    protected ResourceLocation lootTable;
    protected long lootTableSeed;

    public AdvancedShulkerboxContainer(Player player, ItemStack shulkerBox, int invSize) {
        this.shulkerBox = shulkerBox;
        this.invSize = invSize;
        this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);

        CompoundTag c = shulkerBox.getTag();

        if (c == null) {
            return;
        }

        if (!c.contains(BlockItem.BLOCK_ENTITY_TAG)) {
            return;
        }

        blockEntityTag = c.getCompound(BlockItem.BLOCK_ENTITY_TAG);

        if (blockEntityTag.contains(ITEMS_TAG)) {
            ContainerHelper.loadAllItems(blockEntityTag, items);
        } else if (blockEntityTag.contains(LOOT_TABLE_TAG)) {
            lootTable = new ResourceLocation(blockEntityTag.getString(LOOT_TABLE_TAG));
            lootTableSeed = blockEntityTag.getLong(LOOT_TABLE_SEED_TAG);
            fillWithLoot(player);
            blockEntityTag.remove(LOOT_TABLE_TAG);
            blockEntityTag.remove(LOOT_TABLE_SEED_TAG);
        }
    }

    public void fillWithLoot(@Nullable Player player) {
        if (lootTable != null && player != null) {
            LootTable loottable = player.level().getServer().getLootData().getLootTable(lootTable);
            lootTable = null;

            LootParams.Builder builder = new LootParams.Builder((ServerLevel) player.level());
            builder.withLuck(player.getLuck()).withParameter(LootContextParams.THIS_ENTITY, player);

            loottable.fill(this, builder.create(LootContextParamSets.CHEST), lootTableSeed);
            setChanged();
        }
    }

    @Override
    public int getContainerSize() {
        return invSize;
    }

    @Override
    public ItemStack getItem(int index) {
        return items.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        ItemStack itemstack = ContainerHelper.removeItem(items, index, count);
        setChanged();
        return itemstack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack stack = ContainerHelper.takeItem(items, index);
        setChanged();
        return stack;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        items.set(index, stack);
        setChanged();
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public void setChanged() {
        CompoundTag tag = shulkerBox.getOrCreateTag();
        if (blockEntityTag == null) {
            tag.put(BlockItem.BLOCK_ENTITY_TAG, blockEntityTag = new CompoundTag());
        } else {
            tag.put(BlockItem.BLOCK_ENTITY_TAG, blockEntityTag);
        }

        ContainerHelper.saveAllItems(blockEntityTag, items, true);
    }

    @Override
    public void startOpen(Player player) {
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), getOpenSound(), SoundSource.BLOCKS, 0.5F, getVariatedPitch(player.level()));
    }

    @Override
    public void stopOpen(Player player) {
        setChanged();
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), getCloseSound(), SoundSource.BLOCKS, 0.5F, getVariatedPitch(player.level()));
    }

    protected static float getVariatedPitch(Level world) {
        return world.random.nextFloat() * 0.1F + 0.9F;
    }

    protected SoundEvent getOpenSound() {
        return SoundEvents.SHULKER_BOX_OPEN;
    }

    protected SoundEvent getCloseSound() {
        return SoundEvents.SHULKER_BOX_CLOSE;
    }

    @Override
    public void clearContent() {
        items.clear();
        setChanged();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public boolean stillValid(Player player) {
        return player.getInventory().contains(shulkerBox);
    }

}
