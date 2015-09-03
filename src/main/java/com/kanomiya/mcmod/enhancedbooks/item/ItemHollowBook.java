package com.kanomiya.mcmod.enhancedbooks.item;

import com.kanomiya.mcmod.enhancedbooks.EnhancedBooks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHollowBook extends Item {

	public ItemHollowBook() {
        setMaxStackSize(1);
        setCreativeTab(EnhancedBooks.tabEB);
        setUnlocalizedName("itemHollowBook");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

    	player.openGui(EnhancedBooks.instance, EnhancedBooks.GUIID_HOLLOWBOOK, world, (int) player.posX, (int) player.posY, (int) player.posZ);

        return stack;
    }


    @Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
    	return getTagCompound(stack).hasKey("items");
    }

    public static NBTTagCompound getTagCompound(ItemStack item) {
		NBTTagCompound tag = item.getTagCompound();

		if(tag == null) {
			tag = new NBTTagCompound();
		}

		return tag;
	}
}