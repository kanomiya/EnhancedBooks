package com.kanomiya.mcmod.enhancedbooks.inventory;

import com.kanomiya.mcmod.enhancedbooks.EBConfig;
import com.kanomiya.mcmod.enhancedbooks.tileentity.TileEntityStorageShelf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

// ContainerFurnace
// ContainerEnchantment
public class ContainerStorageShelf extends Container {

	protected TileEntityStorageShelf tileEntity;
	int slotNum = 0;

	public ContainerStorageShelf(InventoryPlayer inventoryPlayer, TileEntityStorageShelf te) {
		tileEntity = te;

		//the Slot constructor takes the IInventory and the slot number in that it binds to
		//and the x-y coordinates it resides on-screen
		for (int i = 0; i < 2; ++i) {
			for (int j = 0; j < 7; ++j) {
				addSlotToContainer(new FixedSlot(tileEntity, slotNum, 24 + j * 18, 30 + i * 18));
				++slotNum;
			}
		}

		//commonly used vanilla code that adds the player's inventory
		bindPlayerInventory(inventoryPlayer);

		tileEntity.openInventory(inventoryPlayer.player);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntity.isUseableByPlayer(player);
	}


	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 94 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 152));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slotNum) {
		ItemStack stack = null;
		Slot slot = (Slot) inventorySlots.get(slotNum);

		if (slot == null || ! slot.getHasStack()) { return stack; }

		ItemStack slotStack = slot.getStack();
		stack = slotStack.copy();

		// System.out.println("***" + slotNum + " * " + slotStack.toString());

		if (slotNum < tileEntity.getSizeInventory())
		{
			if (! mergeItemStack(stack, tileEntity.getSizeInventory(), inventorySlots.size(), true))
			{
				return null;
			}
		}
		//シフトクリック時に、このアイテムだったら動かさない。
		else if(slot.getStack() != null && ! EBConfig.isBook(slotStack))
		{
			return null;
		}
		else if (! mergeItemStack(stack, 0, tileEntity.getSizeInventory(), false))
		{
			return null;
		}

		if (stack.stackSize == 0) { slot.putStack((ItemStack) null); }
		else { slot.onSlotChanged(); }

		return stack;
	}



	/*
	// Modified Version - Only merges for 1 stack
	@Override
	protected boolean mergeItemStack(ItemStack par1ItemStack, int startSlot, int endSlot, boolean reverse) {
		boolean success = false;
		int thisStartSlot;

		Slot currentSlot;
		ItemStack currentStack;

		if (par1ItemStack.stackSize > 0) {
			if (reverse) {
				thisStartSlot = endSlot - 1;
			} else {
				thisStartSlot = startSlot;
			}

			while (!reverse && thisStartSlot < endSlot || reverse && thisStartSlot >= startSlot) {
				currentSlot = (Slot) inventorySlots.get(thisStartSlot);
				currentStack = currentSlot.getStack();

				if (currentStack == null) {
					currentSlot.putStack(par1ItemStack.copy());
					currentSlot.getStack().stackSize = 1;
					currentSlot.onSlotChanged();
					par1ItemStack.stackSize -= 1;
					success = true;
					break;
				}

				if (reverse) {
					--thisStartSlot;
				} else {
					++thisStartSlot;
				}
			}
		}

		return success;
	}
	*/

	@Override
	public void onContainerClosed(EntityPlayer p_75134_1_) {
		super.onContainerClosed(p_75134_1_);
		tileEntity.closeInventory(p_75134_1_);
	}

}