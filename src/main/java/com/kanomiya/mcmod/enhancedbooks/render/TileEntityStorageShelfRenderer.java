package com.kanomiya.mcmod.enhancedbooks.render;

import com.kanomiya.mcmod.enhancedbooks.tileentity.TileEntityStorageShelf;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// TileEntityEnchantmentTableRenderer
@SideOnly(Side.CLIENT)
public class TileEntityStorageShelfRenderer extends TileEntitySpecialRenderer {
	private static final ResourceLocation mainResource = new ResourceLocation("enhancedbooks:textures/tileentity/tileentityStorageShelf.png");
	private static final ResourceLocation bookResource = new ResourceLocation("enhancedbooks:textures/tileentity/bookBookShelf.png");
	private ModelStorageShelf mainmodel = new ModelStorageShelf();
	private ModelBookBookShelf bookmodel = new ModelBookBookShelf();

	public void renderStorageShelf(TileEntityStorageShelf te, double posX, double posY, double posZ, float per8, int per9) {

		/*
		float f1 = per8;

		float f4 = per8 + 0.25F;
		float f5 = per8 + 0.75F;

		f4 = (f4 - MathHelper.truncateDoubleToInt(f4)) * 1.6F - 0.3F;
		f5 = (f5 - MathHelper.truncateDoubleToInt(f5)) * 1.6F - 0.3F;

		if (f4 < 0.0F) { f4 = 0.0F; }
		if (f5 < 0.0F) { 	f5 = 0.0F; }
		if (f4 > 1.0F) { f4 = 1.0F; }
		if (f5 > 1.0F) { f5 = 1.0F; }

		float f6 = per8;

		*/

		GlStateManager.pushMatrix(); // 座標保存

		GlStateManager.translate((float)posX +0.5f, (float)posY -0.5f, (float)posZ +0.5f);
		GlStateManager.scale(0.0625f, 0.0625f, 0.0625f);
		GlStateManager.rotate(metaToRotate(te.getBlockMetadata()) *90f, 0f, 1f, 0f);
		GlStateManager.enableCull(); // 背面描画の省略?


		bindTexture(mainResource);
		mainmodel.renderStorageShelf(1f);

		GlStateManager.translate(5f, 17f, -7f);


		bindTexture(bookResource);

		for (int i=0; i<te.getSizeInventory(); i++) {
			if (te.getStackInSlot(i) != null) {
				bookmodel.renderBook(1f);
			}

			GlStateManager.translate(-2f, 0f, 0f);
			if (i == te.getSizeInventory()/2 -1) { GlStateManager.translate(2f* te.getSizeInventory()/2, -8f, 0f); }
		}

		GlStateManager.popMatrix(); // 座標展開

	}

	@Override
	public void renderTileEntityAt(TileEntity te, double posX, double posY, double posZ, float rot_rot, int p_180535_9_) {
		renderStorageShelf((TileEntityStorageShelf) te, posX, posY, posZ, rot_rot, p_180535_9_);
	}

	public float metaToRotate(int meta) {
		switch (meta) {
		case 0: return 2;
		case 1: return 1;
		case 2: return 0;
		case 3: return 3;
		default: return 0;

		}
	}
}
