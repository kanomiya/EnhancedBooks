package com.kanomiya.mcmod.enhancedbooks;

import com.kanomiya.mcmod.enhancedbooks.block.BlockBookPrinter;
import com.kanomiya.mcmod.enhancedbooks.block.BlockStorageShelf;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public enum EBBlock {
	// sample(new SampleBlock(), "blockSample", "blockSample0"),
	storageshelf(new BlockStorageShelf(), "blockStorageShelf"),
	bookPrinter(new BlockBookPrinter(), "blockBookPrinter"),
	;

	private String blockname;
	private String[] modelnames;
	private Block block;

	private EBBlock(Block block, String blockname, String... modelnames) {
		this.block = block;
		this.blockname = blockname;

		if (modelnames != null && modelnames.length != 0) { this.modelnames = modelnames; }
		else { this.modelnames = new String[] { blockname }; }

		for (int i=0; i<this.modelnames.length; i++) {
			if (! this.modelnames[i].contains(":")) {
				this.modelnames[i] = EnhancedBooks.MODID + ":" + this.modelnames[i];
			}
		}

	}

	public String getBlockName() { return blockname; }
	public String[] getModelNames() { return modelnames; }
	public Block getBlock() { return block; }



	public static void registerBlocks() {
		for (EBBlock sb: EBBlock.values()) {
			sb.getBlock().setUnlocalizedName(sb.getBlockName());
			GameRegistry.registerBlock(sb.getBlock(), sb.getBlockName());
		}
	}

	public static void registerModels() {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		for (EBBlock sb: EBBlock.values()) {
			ModelBakery.addVariantName(Item.getItemFromBlock(sb.getBlock()), sb.getModelNames());

			for (int i=0; i<sb.getModelNames().length; i++) {
				mesher.register(Item.getItemFromBlock(sb.getBlock()), i, new ModelResourceLocation(sb.getModelNames()[i], "inventory"));
			}
		}
	}

}
