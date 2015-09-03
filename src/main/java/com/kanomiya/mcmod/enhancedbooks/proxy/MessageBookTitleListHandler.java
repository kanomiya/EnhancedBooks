package com.kanomiya.mcmod.enhancedbooks.proxy;

import com.kanomiya.mcmod.enhancedbooks.EnhancedBooks;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageBookTitleListHandler {

	public static class Client implements IMessageHandler<MessageBookTitleList.Server, IMessage> {

		@Override //IMessageHandlerのメソッド
		public IMessage onMessage(MessageBookTitleList.Server message, MessageContext ctx) {

			EnhancedBooks.titleList = message.titlelist;


			return null;
		}

	}

	public static class Server implements IMessageHandler<MessageBookTitleList.Client, IMessage> {

		@Override //IMessageHandlerのメソッド
		public IMessage onMessage(MessageBookTitleList.Client message, MessageContext ctx) {

			return new MessageBookTitleList.Server(EnhancedBooks.loadedBooks);

		}
	}

}
