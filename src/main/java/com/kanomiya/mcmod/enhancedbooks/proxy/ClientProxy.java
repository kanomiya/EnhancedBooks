package com.kanomiya.mcmod.enhancedbooks.proxy;

import com.kanomiya.mcmod.enhancedbooks.render.TileEntityStorageShelfRenderer;
import com.kanomiya.mcmod.enhancedbooks.tileentity.TileEntityBookPrinter;
import com.kanomiya.mcmod.enhancedbooks.tileentity.TileEntityStorageShelf;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class ClientProxy extends CommonProxy {

	@Override
	public void registerTileEntity() {
		ClientRegistry.registerTileEntity(TileEntityStorageShelf.class, "tileentityStorageShelf", new TileEntityStorageShelfRenderer());
		GameRegistry.registerTileEntity(TileEntityBookPrinter.class, "tileentityBookPrinter");
	}


    @Override
    public EntityPlayer getEntityPlayerInstance() {
        return Minecraft.getMinecraft().thePlayer;
    }
}
