package com.kanomiya.mcmod.enhancedbooks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EBCreativeTab extends CreativeTabs {
	public EBCreativeTab() {
		super("Enhanced Books");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return Item.getItemFromBlock(EBBlock.storageshelf.getBlock());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return "Enhanced Books";
	}

}
