package com.kanomiya.mcmod.enhancedbooks.proxy;

import com.kanomiya.mcmod.enhancedbooks.inventory.ContainerBookPrinter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageBookPrinterOnPrintHandler implements IMessageHandler<MessageBookPrinterOnPrint, IMessage> {

	@Override //IMessageHandlerのメソッド
	public IMessage onMessage(MessageBookPrinterOnPrint message, MessageContext ctx) {

		if (ctx.side.isServer()) {
			EntityPlayer player = ctx.getServerHandler().playerEntity;
			if (player.openContainer instanceof ContainerBookPrinter) {
				ContainerBookPrinter container = (ContainerBookPrinter) player.openContainer;

				container.getSlot(2).putStack(message.bookInfo.copyStack(1));
			}

		}

		return null;
	}

}
