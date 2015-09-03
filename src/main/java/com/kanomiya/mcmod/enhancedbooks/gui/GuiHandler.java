package com.kanomiya.mcmod.enhancedbooks.gui;

import com.kanomiya.mcmod.enhancedbooks.EBConfig;
import com.kanomiya.mcmod.enhancedbooks.EnhancedBooks;
import com.kanomiya.mcmod.enhancedbooks.inventory.ContainerBookPrinter;
import com.kanomiya.mcmod.enhancedbooks.inventory.ContainerHollowBook;
import com.kanomiya.mcmod.enhancedbooks.inventory.ContainerStorageShelf;
import com.kanomiya.mcmod.enhancedbooks.item.ItemEXPBook;
import com.kanomiya.mcmod.enhancedbooks.tileentity.TileEntityBookPrinter;
import com.kanomiya.mcmod.enhancedbooks.tileentity.TileEntityStorageShelf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == EnhancedBooks.GUIID_STORAGESHELF) {
			TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
			if (tileEntity instanceof TileEntityStorageShelf) {
				return new ContainerStorageShelf(player.inventory, (TileEntityStorageShelf) tileEntity);
			}
		}

		if (id == EnhancedBooks.GUIID_EXPBOOK) {
			// Do nothing :D
		}

		if (id == EnhancedBooks.GUIID_HOLLOWBOOK) {
			return new ContainerHollowBook(player.inventory);
		}

		if (id == EnhancedBooks.GUIID_BOOKPRINTER && EBConfig.PRINTER_ENABLED) {
			TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
			if (tileEntity instanceof TileEntityBookPrinter) {
				return new ContainerBookPrinter(player.inventory, (TileEntityBookPrinter) tileEntity);
			}

		}


		return null;
	}

	// returns an instance of the gui thingamabob
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == EnhancedBooks.GUIID_STORAGESHELF) {
			TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
			if (tileEntity instanceof TileEntityStorageShelf) {
				return new GuiStorageShelf(player.inventory, (TileEntityStorageShelf) tileEntity);
			}
		}

		if (id == EnhancedBooks.GUIID_EXPBOOK) {
			ItemStack stack = player.inventory.getCurrentItem();
			return new GuiEXPBook(stack.getItem() instanceof ItemEXPBook ? stack : null);
		}

		if (id == EnhancedBooks.GUIID_HOLLOWBOOK) {
			return new GuiHollowBook(player.inventory);
		}

		if (id == EnhancedBooks.GUIID_BOOKPRINTER && EBConfig.PRINTER_ENABLED) {
			TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
			if (tileEntity instanceof TileEntityBookPrinter) {
				return new GuiBookPrinter(player.inventory, (TileEntityBookPrinter) tileEntity);
			}

		}

		return null;
	}

}
