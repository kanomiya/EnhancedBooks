package com.kanomiya.mcmod.enhancedbooks.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class InventoryHollowBook implements IInventory {
	private ItemStack[] items;
    private String customName;

    public InventoryPlayer invPlayer;
    public EntityPlayer player;
    public ItemStack currentItem;

	public InventoryHollowBook(InventoryPlayer invPlayer) {
		this.invPlayer = invPlayer;
		player = invPlayer.player;
		currentItem = invPlayer.getCurrentItem();

		items = new ItemStack[getSizeInventory()];
	}










	// --*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--
	// --*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--
	// IInventory's Methods
	// --*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--
	// --*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--]

	@Override
	public String getName() { return hasCustomName() ? customName : "container.hollowbook"; }

	@Override
	public boolean hasCustomName() { return customName != null && customName.length() > 0; }

	public void setCustomName(String name) { customName = name; }

	@Override
	 public IChatComponent getDisplayName() {
		return hasCustomName() ? new ChatComponentText(getName()) : new ChatComponentTranslation(getName(), new Object[0]);
	}

	@Override
	public int getSizeInventory() { return 1; }

	@Override
	public ItemStack getStackInSlot(int index) { return items[index]; }


	@Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
    {
        if (items[p_70298_1_] != null)
        {
            ItemStack itemstack;

            if (items[p_70298_1_].stackSize <= p_70298_2_)
            {
                itemstack = items[p_70298_1_];
                items[p_70298_1_] = null;
                markDirty();
                return itemstack;
            }
            else
            {
                itemstack = items[p_70298_1_].splitStack(p_70298_2_);

                if (items[p_70298_1_].stackSize == 0)
                {
                    items[p_70298_1_] = null;
                }

                markDirty();
                return itemstack;
            }
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_)
    {
        if (items[p_70304_1_] != null)
        {
            ItemStack itemstack = items[p_70304_1_];
            items[p_70304_1_] = null;
            return itemstack;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
    {
        items[p_70299_1_] = p_70299_2_;

        if (p_70299_2_ != null && p_70299_2_.stackSize > getInventoryStackLimit())
        {
            p_70299_2_.stackSize = getInventoryStackLimit();
        }

        markDirty();
    }


	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)  {
		return true;
	}

 /*
        Containerが開かれたタイミングでItemStackの持っているNBTからアイテムを読み込んでいる
     */
    @Override
    public void openInventory(EntityPlayer player)
    {
        if(! currentItem.hasTagCompound()) {
            currentItem.setTagCompound(new NBTTagCompound());
            currentItem.getTagCompound().setTag("items", new NBTTagList());
        }

        NBTTagList tags = (NBTTagList) currentItem.getTagCompound().getTag("items");

        for(int i = 0; i < tags.tagCount(); i++)
        {
            NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
            int slot = tagCompound.getByte("Slot");
            if(slot >= 0 && slot < items.length)
            {
                items[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }
    }

    /*
        Containerを閉じるときに保存
     */
    @Override
    public void closeInventory(EntityPlayer player)
    {
        NBTTagList tagList = new NBTTagList();
        for(int i = 0; i < items.length; i++)
        {
            if(items[i] != null)
            {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setByte("slot", (byte) i);
                items[i].writeToNBT(compound);
                tagList.appendTag(compound);
            }
        }
        ItemStack result = new ItemStack(currentItem.getItem(), 1);
        result.setTagCompound(new NBTTagCompound());
        result.getTagCompound().setTag("items", tagList);

        //ItemStackをセットする。NBTは右クリック等のタイミングでしか保存されないため再セットで保存している。
        invPlayer.mainInventory[invPlayer.currentItem] = result;
    }

	@Override
	public boolean isItemValidForSlot(int index, ItemStack currentItem) {
		return true;
	}

	@Override
	public int getField(int id) { return 0; }

	@Override
	public void setField(int id, int value) { }

	@Override
	public int getFieldCount() { return 0; }

	@Override
	public void clear() {
		for (int i = 0; i < items.length; ++i) {
            items[i] = null;
        }
	}



	@Override
	public void markDirty() { }

	// --*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--
	// --*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--
	// --*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--
	// --*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--
	// --*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--


}
