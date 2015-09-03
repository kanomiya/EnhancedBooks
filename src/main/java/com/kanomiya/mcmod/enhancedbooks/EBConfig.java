package com.kanomiya.mcmod.enhancedbooks;

import java.io.File;
import java.util.ArrayList;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RegistryNamespaced;
import net.minecraftforge.common.config.Configuration;

public class EBConfig {
	private static ArrayList<Item> BOOKITEM_LIST = new ArrayList<Item>();
	private static boolean BOOKONLY;
	public static boolean PRINTER_ENABLED;

	public static void init(File configfile) {
		Configuration config = new Configuration(configfile);

		try {
			config.load();

			BOOKONLY = config.getBoolean("BookOnly", "BookShelf", true, "If false, All Items will be able to be contained.");

			RegistryNamespaced reg = Item.itemRegistry;

			String[] bookIds = config.getStringList("BookItemList", "BookShelf", new String[] {
					reg.getNameForObject(Items.book).toString(),
					reg.getNameForObject(Items.writable_book).toString(),
					reg.getNameForObject(Items.written_book).toString(),
					reg.getNameForObject(Items.enchanted_book).toString(),

					reg.getNameForObject(EBItem.expBook.getItem()).toString(),
					reg.getNameForObject(EBItem.hollowBook.getItem()).toString(),
					reg.getNameForObject(EBItem.fatBook.getItem()).toString(),
					reg.getNameForObject(EBItem.cookieBook.getItem()).toString(),

			}, "Item Name");

			if (BOOKONLY) {
				// Items
				addBook(bookIds);

			}


			PRINTER_ENABLED = config.getBoolean("PrinterEnabled", "BookPrinter", true, "");


		} catch (Exception e) {
			EnhancedBooks.logger.error("Loading BookItem Config", e);
		} finally {
			config.save();
		}

	}

	public static boolean isBook(ItemStack stack) {
		return (! BOOKONLY) || (BOOKITEM_LIST.contains(stack.getItem()));
	}



	private static boolean addBook(Item item) {
		if (item != null) BOOKITEM_LIST.add(item);
		return (item != null);
	}

	private static void addBook(String id) {
		if (! addBook(Item.getByNameOrId(id))) EnhancedBooks.logger.error("[BookItem] Unknown Item  \"" + id + "\"");
	}

	private static void addBook(String... ids) {
		for (String id: ids) addBook(id);
	}

}
