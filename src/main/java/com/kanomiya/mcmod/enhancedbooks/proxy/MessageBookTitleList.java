package com.kanomiya.mcmod.enhancedbooks.proxy;

import com.kanomiya.mcmod.enhancedbooks.EnhancedBooks;
import com.kanomiya.mcmod.enhancedbooks.bookloader.BookInfo;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageBookTitleList {

	public static class Server implements IMessage {
		String[] titlelist;

		public Server(){}

		public Server(BookInfo[] info) {
			titlelist = new String[info.length];

			for (int i=0; i<info.length; i++) {
				titlelist[i] = info[i].getTitle();
			}

		}

		@Override //IMessageのメソッド。ByteBufからデータを読み取る。
		public void fromBytes(ByteBuf buf) {
			titlelist = new String[buf.readInt()];

			for (int i=0; i<titlelist.length; i++) {
				int bl = buf.readInt();
				titlelist[i] = buf.readBytes(bl).toString();
				EnhancedBooks.logger.info("TITLE [" + i + "] : " + titlelist[i]);
			}

		}

		@Override //IMessageのメソッド。ByteBufにデータを書き込む。
		public void toBytes(ByteBuf buf) {
			buf.writeInt(titlelist.length);

			for (int i=0; i<titlelist.length; i++) {
				byte[] bytes = titlelist[i].getBytes();

				buf.writeInt(bytes.length);
				buf.writeBytes(bytes);
			}


		}

	}


	public static class Client implements IMessage {
		public Client(){}

		@Override //IMessageのメソッド。ByteBufからデータを読み取る。
		public void fromBytes(ByteBuf buf) { }

		@Override //IMessageのメソッド。ByteBufにデータを書き込む。
		public void toBytes(ByteBuf buf) { }

	}
}
