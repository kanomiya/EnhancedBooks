package com.kanomiya.mcmod.enhancedbooks.item;

import java.util.List;

import com.kanomiya.mcmod.enhancedbooks.EnhancedBooks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemEXPBook extends Item {

    public ItemEXPBook() {
        setMaxStackSize(1);
        setCreativeTab(EnhancedBooks.tabEB);
        setUnlocalizedName("itemEXPBook");
        setMaxDamage(50);
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer par2EntityPlayer, List descriptionList, boolean par4) {
        descriptionList.add("Level: " + (itemStack.getMaxDamage() - itemStack.getItemDamage()) + "/" + itemStack.getMaxDamage());
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
        entityPlayer.openGui(EnhancedBooks.instance, EnhancedBooks.GUIID_EXPBOOK, world, (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);
        return itemStack;
    }

    @Override
	@SideOnly(Side.CLIENT)
    // That goddamn enchanted shining effect :D
    public boolean hasEffect(ItemStack itemStack) {
        return itemStack.getItemDamage() != itemStack.getMaxDamage();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
    	subItems.add(new ItemStack(itemIn, 1, getMaxDamage()));
    	subItems.add(new ItemStack(itemIn, 1, 0));

    }
}

