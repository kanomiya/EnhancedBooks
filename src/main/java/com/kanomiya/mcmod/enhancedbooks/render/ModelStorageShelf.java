package com.kanomiya.mcmod.enhancedbooks.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// Date: 2014/12/24 16:18:08
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

@SideOnly(Side.CLIENT)
public class ModelStorageShelf extends ModelBase {
	// fields
	ModelRenderer bottomBoard;
	ModelRenderer topBoard;
	ModelRenderer rightBoard;
	ModelRenderer leftBoard;
	ModelRenderer backBoard;
	ModelRenderer partition;

	public ModelStorageShelf() {
		textureWidth = 128;
		textureHeight = 64;

		bottomBoard = new ModelRenderer(this, 0, 47);
		bottomBoard.addBox(0F, 0F, 0F, 16, 1, 16);
		bottomBoard.setRotationPoint(-8F, 23F, -8F);
		bottomBoard.setTextureSize(128, 64);
		bottomBoard.mirror = true;

		topBoard = new ModelRenderer(this, 0, 47);
		topBoard.addBox(0F, 0F, 0F, 16, 1, 16);
		topBoard.setRotationPoint(-8F, 8F, -8F);
		topBoard.setTextureSize(128, 64);
		topBoard.mirror = false;

		rightBoard = new ModelRenderer(this, 0, 16);
		rightBoard.addBox(0F, 0F, 0F, 1, 14, 16);
		rightBoard.setRotationPoint(7F, 9F, -8F);
		rightBoard.setTextureSize(128, 64);
		rightBoard.mirror = false;

		leftBoard = new ModelRenderer(this, 0, 16);
		leftBoard.addBox(0F, 0F, 0F, 1, 14, 16);
		leftBoard.setRotationPoint(-8F, 9F, -8F);
		leftBoard.setTextureSize(128, 64);
		leftBoard.mirror = true;

		backBoard = new ModelRenderer(this, 0, 0);
		backBoard.addBox(0F, 0F, 0F, 14, 14, 1);
		backBoard.setRotationPoint(-7F, 9F, 7F);
		backBoard.setTextureSize(128, 64);
		backBoard.mirror = true;

		partition = new ModelRenderer(this, 34, 31);
		partition.addBox(0F, 0F, 0F, 14, 2, 14);
		partition.setRotationPoint(-7F, 15F, -7F);
		partition.setTextureSize(128, 64);
		partition.mirror = true;
	}

	public void renderStorageShelf(float f) {
		bottomBoard.render(f);
		topBoard.render(f);
		rightBoard.render(f);
		leftBoard.render(f);
		backBoard.render(f);
		partition.render(f);
	}

}