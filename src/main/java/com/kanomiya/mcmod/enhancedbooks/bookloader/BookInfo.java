package com.kanomiya.mcmod.enhancedbooks.bookloader;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

public class BookInfo {
	public static final int DESC_MAXLENGTH = 15;
	private String title, author, description;
	private String[] pages;
	private ItemStack stack;

	public BookInfo(ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();

		if (nbt == null) { stack.setTagCompound(new NBTTagCompound()); }

		title = nbt.getString("title");
		author = nbt.getString("author");

		NBTTagList pageList = nbt.getTagList("pages", NBT.TAG_STRING);
		pages = new String[pageList.tagCount()];

		for (int i=0; i<pageList.tagCount(); i++) {
			pages[i] = pageList.getStringTagAt(i);
		}

		updateDescription();
		this.stack = stack;
	}

	public BookInfo(String title, String author, String[] pages) {
		this(BookLoader.readBook(title, author, pages));
	}

	private void updateDescription() {
		if (pages.length == 0) { description = "[ERROR]"; }
		else {
			description = (pages[0].length() <= DESC_MAXLENGTH) ? pages[0] : pages[0].substring(0, DESC_MAXLENGTH -1);
		}

	}

	public String getTitle() { return title; }
	public String getAuthor() { return author; }
	public String getDescription() { return description; }

	public String[] getPages() { return pages; }
	public String getPage(int index) { return pages[index]; }
	public int getPageSize() { return pages.length; }

	public ItemStack copyStack() { return stack.copy(); }
	public ItemStack copyStack(int amount) {
		ItemStack clone = copyStack();
		clone.stackSize = amount;

		return clone;
	}

}
