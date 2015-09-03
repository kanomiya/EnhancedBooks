package com.kanomiya.mcmod.enhancedbooks.proxy;

import com.kanomiya.mcmod.enhancedbooks.tileentity.TileEntityBookPrinter;
import com.kanomiya.mcmod.enhancedbooks.tileentity.TileEntityStorageShelf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public void registerTileEntity() {
		GameRegistry.registerTileEntity(TileEntityStorageShelf.class, "tileentityStorageShelf");
		GameRegistry.registerTileEntity(TileEntityBookPrinter.class, "tileentityBookPrinter");
    }


    public EntityPlayer getEntityPlayerInstance() { return null; }

}
