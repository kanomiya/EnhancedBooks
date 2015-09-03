package com.kanomiya.mcmod.enhancedbooks.gui;

import com.kanomiya.mcmod.enhancedbooks.EnhancedBooks;

import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.client.GuiScrollingList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSlotBookList extends GuiScrollingList {
	private GuiBookPrinter parent;


	public GuiSlotBookList(GuiBookPrinter parent, int lw, int lh, int gt, int gl) {
		super(parent.getMinecraftInstance(), lw, 0, gt +13, gt +84, gl +7, 20);
		// Minecraft client, int width, int height, int top, int bottom, int left, int entryHeight

		this.parent = parent;
	}

	@Override
	protected int getSize() { return (EnhancedBooks.titleList != null) ? EnhancedBooks.titleList.length : 0; }

	@Override
	protected void elementClicked(int var1, boolean var2) {
		parent.container.setSelected(var1);
	}

	@Override
	protected boolean isSelected(int var1) {
		return (parent.container.getSelected() == var1);
	}

	@Override
	protected void drawBackground() { }

	@Override
	protected void drawSlot(int listIndex, int boxRight, int top, int var, Tessellator var5) {

		if (EnhancedBooks.titleList != null) {
			parent.getFontRenderer().drawString(EnhancedBooks.titleList[listIndex], var +boxRight -listWidth, top, 0xFFFFFF);
		}
	}
}
