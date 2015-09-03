package com.kanomiya.mcmod.enhancedbooks.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.kanomiya.mcmod.enhancedbooks.inventory.ContainerStorageShelf;
import com.kanomiya.mcmod.enhancedbooks.tileentity.TileEntityStorageShelf;


@SideOnly(Side.CLIENT)
public class GuiStorageShelf extends GuiContainer {
	private static ResourceLocation background = new ResourceLocation("enhancedbooks:textures/gui/guiStorageShelf.png");

	public GuiStorageShelf(InventoryPlayer inventoryPlayer, TileEntityStorageShelf tileEntity) {
		super(new ContainerStorageShelf(inventoryPlayer, tileEntity));
		ySize = 176;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {

		fontRendererObj.drawString("Bookshelf", 8, 6, 4210752);
		//draws "Inventory" or your regional equivalent
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(background);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}

}
