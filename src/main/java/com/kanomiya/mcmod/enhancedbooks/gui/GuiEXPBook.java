package com.kanomiya.mcmod.enhancedbooks.gui;

import com.kanomiya.mcmod.enhancedbooks.item.ItemEXPBook;
import com.kanomiya.mcmod.enhancedbooks.proxy.MessageGuiXPBookBtnPressed;
import com.kanomiya.mcmod.enhancedbooks.proxy.PacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEXPBook extends GuiScreen {
	private static ResourceLocation background = new ResourceLocation("enhancedbooks:textures/gui/guiXPBook.png");

	public final int xSize = 176;
	public final int ySize = 88;
	protected int guiLeft;
	protected int guiTop;

	private GuiButton plus;
	private GuiButton minus;
	private ItemStack xpBook;

	public GuiEXPBook(ItemStack xpBook) {
		this.xpBook = xpBook;
	}

	@Override
	public void updateScreen() {
		if (mc.thePlayer.isDead || xpBook == null) {
			mc.thePlayer.closeScreen();
			return;
		}
		if (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemEXPBook) {
			xpBook = mc.thePlayer.inventory.getCurrentItem();
		}
		plus.enabled = mc.thePlayer.inventory.getCurrentItem().getItemDamage() > 0 && mc.thePlayer.experienceLevel > 0 && !mc.thePlayer.capabilities.isCreativeMode;
		minus.enabled = mc.thePlayer.inventory.getCurrentItem().getItemDamage() < mc.thePlayer.inventory.getCurrentItem().getMaxDamage() && !mc.thePlayer.capabilities.isCreativeMode;
	}

	/**
	 * Draw the Gui
	 */
	 @Override
	 public void drawScreen(int par1, int par2, float par3) {
		 mc.renderEngine.bindTexture(background); //Bind Texture
		 int k = (width - xSize) / 2;
		 int l = (height - ySize) / 2;
		 this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
		 drawString(fontRendererObj, "Level: " + (xpBook.getMaxDamage() - xpBook.getItemDamage()), guiLeft + 64, guiTop + 20, 0xEEEEEE);
		 for (int j = 0; j < buttonList.size(); ++j) {
			 GuiButton guibutton = (GuiButton) buttonList.get(j);
			 guibutton.drawButton(mc, par1, par2);

		 }
	 }

	 @Override
	 public void initGui() {
		 buttonList.clear();
		 guiLeft = (width - xSize) / 2;
		 guiTop = (height - ySize) / 2;
		 buttonList.add(plus = new GuiButton(0, guiLeft + xSize - 40, guiTop + ySize / 2, 20, 20, "+"));
		 buttonList.add(minus = new GuiButton(1, guiLeft + 20, guiTop + ySize / 2, 20, 20, "-"));
		 plus.enabled = mc.thePlayer.inventory.getCurrentItem().getItemDamage() > 0;
		 minus.enabled = mc.thePlayer.inventory.getCurrentItem().getItemDamage() < mc.thePlayer.inventory.getCurrentItem().getMaxDamage();
	 }

	 @Override
	 public void actionPerformed(GuiButton button) {
		 //If shift-clicking, add ALL the player's xp, or take ALL of the stuff in book out.
		 switch (button.id) {
		 case 0: //Plus
			 if (isShiftKeyDown()) {
				 //All the XP!
				 sendGuiExpBookPacket(2);
			 } else {
				 //Just one
				 sendGuiExpBookPacket(1);
			 }
			 break;
		 case 1: //Minus
			 if (isShiftKeyDown()) {
				 //All the XP!
				 sendGuiExpBookPacket(-2);
			 } else {
				 //Just One
				 sendGuiExpBookPacket(-1);
			 }
			 break;
		 default:
			 break;
		 }
	 }

	 @Override
	 protected void keyTyped(char par1, int par2) {
		 if (par2 == 1 || par2 == mc.gameSettings.keyBindInventory.getKeyCode()) {
			 mc.thePlayer.closeScreen();
		 }
	 }

	 @Override
	 public boolean doesGuiPauseGame() {
		 return false;
	 }

	 public static void sendGuiExpBookPacket(int data) {
		 if (FMLCommonHandler.instance().getSide().equals(Side.CLIENT)) {
			 if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
				 PacketHandler.INSTANCE.sendToServer(new MessageGuiXPBookBtnPressed(data));
			 }
		 }
	 }
}
