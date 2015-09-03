package com.kanomiya.mcmod.enhancedbooks.inventory;

import com.kanomiya.mcmod.enhancedbooks.EBItem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;


public class ContainerHollowBook extends Container {
	ItemStack hollowBook;
	InventoryHollowBook invHollow;

	public ContainerHollowBook(InventoryPlayer invPlayer) {
		invHollow = new InventoryHollowBook(invPlayer);

		addSlotToContainer(new Slot(invHollow, 0, 79, 32));
		bindPlayerInventory(invPlayer);
		hollowBook = invHollow.currentItem;

		invHollow.openInventory(invPlayer.player);
	}

	/**
	 * True is the current equipped item is the opened item otherwise false.
	 */
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		if (player.inventory.getCurrentItem() != null) {
			if (player.inventory.getCurrentItem().isItemEqual(hollowBook)) {
				return true;
			}
		}

		return false;
	}


	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}


	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slotNum) {
		ItemStack stack = null;
		Slot slot = (Slot) inventorySlots.get(slotNum);

		if (slot != null && slot.getHasStack()) {
			ItemStack slotStack = slot.getStack();
			stack = slotStack.copy();

			if (slotNum == 0) {
				// Put into player inv
				if (! mergeItemStack(slotStack, 1, inventorySlots.size(), true)) {
					return null;
				}

				slot.onSlotChange(slotStack, stack);
			} else {

				if(slot.getStack() != null && slot.getStack().getItem() == EBItem.hollowBook.getItem()) {
					return null;
				}

				if (! mergeItemStack(slotStack, 0, 0, false)) {
					return null;
				}

				if (((Slot)inventorySlots.get(0)).getHasStack() || !((Slot)inventorySlots.get(0)).isItemValid(slotStack))
				{
					if (slotStack.stackSize == 0) {
						slot.putStack((ItemStack) null);
					} else {
						slot.onSlotChanged();
					}

					return null;
				}

				if (slotStack.hasTagCompound() && slotStack.stackSize == 1)
				{
					((Slot)inventorySlots.get(0)).putStack(slotStack.copy());
					slotStack.stackSize = 0;
				}
				else if (slotStack.stackSize >= 1)
				{
					((Slot)inventorySlots.get(0)).putStack(new ItemStack(slotStack.getItem(), 1, slotStack.getMetadata()));
					--slotStack.stackSize;
				}
			}


			if (slotStack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (slotStack.stackSize == stack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, slotStack);
		}

		return stack;
	}

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

	@Override
	public void onContainerClosed(EntityPlayer p_75134_1_) {
		super.onContainerClosed(p_75134_1_);
		invHollow.closeInventory(p_75134_1_);
	}

}