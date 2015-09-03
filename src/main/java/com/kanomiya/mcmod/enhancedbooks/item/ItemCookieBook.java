package com.kanomiya.mcmod.enhancedbooks.item;

import com.kanomiya.mcmod.enhancedbooks.EnhancedBooks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCookieBook extends ItemFood {

    public ItemCookieBook() {
    	super(8, 1.0F, false);
        setCreativeTab(EnhancedBooks.tabEB);
        setUnlocalizedName("itemCookieBook");
        setMaxStackSize(1);
    }

    @Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
    	super.onItemUseFinish(stack, world, player);

    	return stack.stackSize <= 0 ? new ItemStack(Items.book) : stack;
    }

}
