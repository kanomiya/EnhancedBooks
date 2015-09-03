package com.kanomiya.mcmod.enhancedbooks.bookloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;

import com.kanomiya.mcmod.enhancedbooks.EnhancedBooks;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class BookLoader {

	public static ItemStack[] readBooks(File parent) {
		File[] bookdirs = parent.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory();
			}

		});

		ItemStack[] books = new ItemStack[bookdirs.length];

		int i = 0;
		for (; i<bookdirs.length; i++) {
			books[i] = readBook(bookdirs[i]);
		}

		EnhancedBooks.logger.info("[BookLoader] Loaded " + i + " Books");

		return books;
	}

	public static ItemStack readBook(File dir) {
		String title = dir.getName();
		String author = "";
		String[] pages = new String[8];

		for (int i=0; i<pages.length; i++) {
			File file = new File(dir, "page" + i + ".txt");
			if (! file.exists()) { break; }

			pages[i] = new String("");

			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				while (br.ready()) { pages[i] += br.readLine() + "\n"; }
				br.close();
			} catch (IOException e) {
				pages[i] = new String(e.getMessage());
			}

			if (pages[i].length() > 32767) {
				pages[i] = pages[i].substring(0, 32766);
			}
		}

		File infofile = new File(dir, "author.txt");
		if (infofile.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(infofile));
				author = br.readLine();
				br.close();

			} catch (IOException e) {
				author = "[Error]";
			}
		}

		return readBook(title, author, pages);
	}

	public static ItemStack readBook(String title, String author, String[] pages) {
		ItemStack stack = new ItemStack(Items.written_book); // ItemEditableBook ItemWritableBook
		NBTTagCompound nbt  = new NBTTagCompound();
		NBTTagList tagpages = new NBTTagList();

		int i = 0;
		for (; i<pages.length; i++) {
			if (pages[i] == null) { break; }

			tagpages.appendTag(new NBTTagString(pages[i]));
			if (i == 0 && pages[i].isEmpty()) { break; }
		}

		nbt.setString("title", title);
		nbt.setString("author", author);

		nbt.setBoolean("resolved", true);
		nbt.setTag("pages", tagpages);

		stack.setTagCompound(nbt);

		EnhancedBooks.logger.info("[BookLoader] Loaded " + title + " (author: " + author + " page: " + i + ")");

		return stack;
	}


	public static BookInfo[] getBooksInfo(ItemStack[] books) {
		BookInfo[] info = new BookInfo[books.length];

		for (int i=0; i<info.length; i++) {
			info[i] = new BookInfo(books[i]);
		}

		return info;
	}


	public static String[] getTitles(BookInfo[] books) {
		String[] titles = new String[books.length];

		for (int i=0; i<books.length; i++) {
			titles[i] = books[i].getTitle();
		}

		return titles;
	}


}
