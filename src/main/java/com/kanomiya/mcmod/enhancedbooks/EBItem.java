package com.kanomiya.mcmod.enhancedbooks;

import com.kanomiya.mcmod.enhancedbooks.item.ItemCookieBook;
import com.kanomiya.mcmod.enhancedbooks.item.ItemEXPBook;
import com.kanomiya.mcmod.enhancedbooks.item.ItemFatBook;
import com.kanomiya.mcmod.enhancedbooks.item.ItemHollowBook;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public enum EBItem {
	// sample(new SampleItem(), "itemSample", "itemSample0"),
	expBook(new ItemEXPBook(), "itemEXPBook"),
	hollowBook(new ItemHollowBook(), "itemHollowBook"),
	fatBook(new ItemFatBook(), "itemFatBook"),
	cookieBook(new ItemCookieBook(), "itemCookieBook"),
	;

	private String itemname;
	private String[] modelnames;
	private Item item;

	private EBItem(Item item, String itemname, String... modelnames) {
		this.item = item;
		this.itemname = itemname;

		if (modelnames != null && modelnames.length != 0) { this.modelnames = modelnames; }
		else { this.modelnames = new String[] { itemname }; }

		for (int i=0; i<this.modelnames.length; i++) {
			if (! this.modelnames[i].contains(":")) {
				this.modelnames[i] = EnhancedBooks.MODID + ":" + this.modelnames[i];
			}
		}

	}

	public String getItemName() { return itemname; }
	public String[] getModelNames() { return modelnames; }
	public Item getItem() { return item; }



	public static void registerItems() {
		for (EBItem sb: EBItem.values()) {
			GameRegistry.registerItem(sb.getItem(), sb.getItemName());
		}
	}

	public static void registerModels() {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		for (EBItem sb: EBItem.values()) {
			ModelBakery.addVariantName(sb.getItem(), sb.getModelNames());

			for (int i=0; i<sb.getModelNames().length; i++) {
				mesher.register(sb.getItem(), i, new ModelResourceLocation(sb.getModelNames()[i], "inventory"));
			}
		}
	}

}
