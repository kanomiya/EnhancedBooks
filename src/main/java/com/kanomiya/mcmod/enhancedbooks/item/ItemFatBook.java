package com.kanomiya.mcmod.enhancedbooks.item;

import java.util.List;

import com.kanomiya.mcmod.enhancedbooks.EnhancedBooks;
import com.kanomiya.mcmod.enhancedbooks.bookloader.BookInfo;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ItemFatBook extends ItemTool {

    public ItemFatBook() {
    	super(1.5f, Item.ToolMaterial.WOOD, null);

        setCreativeTab(EnhancedBooks.tabEB);

        setUnlocalizedName("itemFatBook");
    }

    @Override
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BLOCK;
    }

    @Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        ItemStack mat = new ItemStack(Items.leather);
        if (mat != null && OreDictionary.itemMatches(mat, repair, false)) return true;
        return super.getIsRepairable(toRepair, repair);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
    	subItems.add(new ItemStack(itemIn, 1, 0));

    	for (BookInfo book: EnhancedBooks.loadedBooks) { if (book != null) { subItems.add(book.copyStack()); } }
    }
}
