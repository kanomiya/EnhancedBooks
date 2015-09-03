package com.kanomiya.mcmod.enhancedbooks.inventory;

import com.kanomiya.mcmod.enhancedbooks.EnhancedBooks;
import com.kanomiya.mcmod.enhancedbooks.proxy.ClientProxy;
import com.kanomiya.mcmod.enhancedbooks.proxy.MessageBookPrinterOnPrint;
import com.kanomiya.mcmod.enhancedbooks.proxy.PacketHandler;
import com.kanomiya.mcmod.enhancedbooks.tileentity.TileEntityBookPrinter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

// ContainerFurnace
// ContainerEnchantment
public class ContainerBookPrinter extends Container {

	int selected;
	Slot inkSlot, bookSlot, outputSlot;
	public TileEntityBookPrinter bookPrinter;
    public IInventory craftResult = new InventoryCraftResult();
    World worldObj;

	public ContainerBookPrinter(InventoryPlayer inventoryPlayer, TileEntityBookPrinter bookPrinter) {
		worldObj = inventoryPlayer.player.worldObj;
		this.bookPrinter = bookPrinter;

		//the Slot constructor takes the IInventory and the slot number in that it binds to
		//and the x-y coordinates it resides on-screen
		addSlotToContainer(inkSlot = new FixedSlot(bookPrinter, 0, 102, 27));
		addSlotToContainer(bookSlot = new FixedSlot(bookPrinter, 1, 138, 27));

		addSlotToContainer(outputSlot = new Slot(craftResult, 0, 120, 63) {

			@Override
			public boolean isItemValid(ItemStack stack) { return false; }

			@Override
			public boolean canTakeStack(EntityPlayer playerIn) { return canPrint(playerIn); }

			@Override
			public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack)
			{
				boolean flag = canPrint(playerIn);

				if (! playerIn.capabilities.isCreativeMode) {
					inkSlot.decrStackSize(1);
					bookSlot.decrStackSize(1);
				}

				if (flag && EnhancedBooks.proxy instanceof ClientProxy) {
					PacketHandler.INSTANCE.sendToServer(new MessageBookPrinterOnPrint(EnhancedBooks.loadedBooks[selected]));
				}

				// setSelected(getSelected());
				onSlotChanged();
			}

			public boolean canPrint(EntityPlayer playerIn) {
				return (playerIn.capabilities.isCreativeMode) || (inkSlot.getHasStack() && bookSlot.getHasStack());
			}


		});


		//commonly used vanilla code that adds the player's inventory
		bindPlayerInventory(inventoryPlayer);

		setSelected(0);
	}

	public int getSelected() { return selected; }

	public void setSelected(int index) {
		selected = index;

		if (EnhancedBooks.proxy instanceof ClientProxy) {

			if (EnhancedBooks.loadedBooks.length > index) {
				getSlot(2).putStack(EnhancedBooks.loadedBooks[index].copyStack(1));
			}
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return bookPrinter.isUseableByPlayer(player);
	}


	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 95 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 153));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{

		ItemStack itemstack = null;
		Slot slot = (Slot)inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 2)
			{
				if (! mergeItemStack(itemstack1, 3, 39, true))
				{
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (bookPrinter.getSizeInventory() <= index)
			{
				if (TileEntityBookPrinter.isBlackDye(itemstack1))
				{
					if (! mergeItemStack(itemstack1, 0, 1, false))
					{
						return null;
					}
				}
				else if (TileEntityBookPrinter.isWhiteBook(itemstack1))
				{
					if (! mergeItemStack(itemstack1, 1, 2, false))
					{
						return null;
					}
				}
				else if (3 <= index && index < 30)
				{
					if (! mergeItemStack(itemstack1, 30, 39, false))
					{
						return null;
					}
				}
				else if (30 <= index && index < 39 && ! mergeItemStack(itemstack1, 3, 30, false))
				{
					return null;
				}
			}
			else if (! mergeItemStack(itemstack1, 3, 39, false))
			{
				return null;
			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack)null);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(playerIn, itemstack1);
		}

		return itemstack;
	}

	/*
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int slotNum) {
		ItemStack stack = null;
		Slot slot = (Slot) inventorySlots.get(slotNum);

		if (slot == null || ! slot.getHasStack()) { return stack; }

		ItemStack slotStack = slot.getStack();
		stack = slotStack.copy();

		// System.out.println("***" + slotNum + " * " + slotStack.toString());

		if (slotNum < invPrinter.getSizeInventory())
		{
			if (! mergeItemStack(stack, invPrinter.getSizeInventory(), inventorySlots.size(), true))
			{
				return null;
			}
		}
		else
		{
			if (InventoryBookPrinter.isBlackDye(stack)) {
				if (! mergeItemStack(stack, 0, 1, false)) {
					return null;
				}
			}
			else if (stack.getItem() == Items.book) {
				if (! mergeItemStack(stack, 1, 2, false)) {
					return null;
				}
			}
			else if (! mergeItemStack(stack, 0, invPrinter.getSizeInventory(), false))
			{
				return null;
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

		slot.onPickupFromSlot(playerIn, slotStack);

		return stack;
	}
	*/

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		bookPrinter.closeInventory(player);
	}

}

