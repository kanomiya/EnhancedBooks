package com.kanomiya.mcmod.enhancedbooks.tileentity;

import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;


public class TileEntityBookPrinter extends TileEntityRSMachineWithInventory {

	public TileEntityBookPrinter() { super(); }

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) { return isBlackDye(stack); }
		if (index == 1) { return isWhiteBook(stack); }

		return true;
	}

	@Override
	public String getName() {
		return hasCustomName() ? customName : "container.bookprinter";
	}


	// --*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--
	// --*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--
	// --*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--
	// --*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--
	// --*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--*--

	public static boolean isBlackDye(ItemStack stack) {
		return (stack.getItem() == Items.dye && EnumDyeColor.byDyeDamage(stack.getMetadata()) == EnumDyeColor.BLACK);
	}

	public static boolean isWhiteBook(ItemStack stack) {
		return (stack.getItem() == Items.book);
	}

}
