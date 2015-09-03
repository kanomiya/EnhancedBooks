package com.kanomiya.mcmod.enhancedbooks.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.kanomiya.mcmod.enhancedbooks.inventory.ContainerBookPrinter;
import com.kanomiya.mcmod.enhancedbooks.tileentity.TileEntityBookPrinter;


//GuiEnchantment
@SideOnly(Side.CLIENT)
public class GuiBookPrinter extends GuiContainer {
	private static ResourceLocation background = new ResourceLocation("enhancedbooks:textures/gui/guiBookPrinter.png");

	// private TileEntityBookPrinter printer;

	public ContainerBookPrinter container;
	private GuiSlotBookList slotBooks;

	public GuiBookPrinter(InventoryPlayer inventoryPlayer, TileEntityBookPrinter bookPrinter) {
		super(new ContainerBookPrinter(inventoryPlayer, bookPrinter));
		ySize = 176;

		container = (ContainerBookPrinter) inventorySlots;
	}

	@Override
	public void initGui() {
		super.initGui();

		slotBooks = new GuiSlotBookList(this, 70, 62, guiTop, guiLeft);
	}


	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

		slotBooks.drawScreen(mouseX, mouseY, partialTicks);

	}


	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		fontRendererObj.drawString("Book Printer", 8, 5, 4210752);

		//draws "Inventory" or your regional equivalent
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 5, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(background);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}

	Minecraft getMinecraftInstance() { return mc; }
	FontRenderer getFontRenderer() { return fontRendererObj; }
}
