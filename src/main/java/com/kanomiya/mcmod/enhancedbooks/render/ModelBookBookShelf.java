package com.kanomiya.mcmod.enhancedbooks.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// Date: 2014/12/24 14:03:30
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

@SideOnly(Side.CLIENT)
public class ModelBookBookShelf extends ModelBase {
	// fields
	ModelRenderer book;

	public ModelBookBookShelf() {
		textureWidth = 32;
		textureHeight = 32;

		book = new ModelRenderer(this, 0, 0);
		book.addBox(0F, 0F, 0F, 2, 6, 4);
		book.setRotationPoint(0F, 0F, 0F);
		book.setTextureSize(32, 32);
		book.mirror = true;
	}

	public void renderBook(float f) {
		book.render(f);
	}



}
