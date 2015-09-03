package com.kanomiya.mcmod.enhancedbooks.proxy;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

import com.kanomiya.mcmod.enhancedbooks.bookloader.BookInfo;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageBookPrinterOnPrint implements IMessage {
	BookInfo bookInfo;

	public MessageBookPrinterOnPrint() { }

	public MessageBookPrinterOnPrint(BookInfo bookInfo) {
		this.bookInfo = bookInfo;
	}

	@Override //IMessageのメソッド。ByteBufからデータを読み取る。
	public void fromBytes(ByteBuf buf) {
		int titleLength = buf.readInt();
		String title = buf.readBytes(titleLength).toString(Charset.forName("UTF-8"));

		int authorLength = buf.readInt();
		String author = buf.readBytes(authorLength).toString(Charset.forName("UTF-8"));

		int pageSize = buf.readInt();
		String[] pages = new String[pageSize];

		for (int i=0; i<pageSize; i++) {
			int pageLength = buf.readInt();
			pages[i] = buf.readBytes(pageLength).toString(Charset.forName("UTF-8"));
		}

		bookInfo = new BookInfo(title, author, pages);
	}

	@Override //IMessageのメソッド。ByteBufにデータを書き込む。
	public void toBytes(ByteBuf buf) {
		String title = bookInfo.getTitle();
		String author = bookInfo.getAuthor();
		buf.writeInt(title.getBytes().length);
		buf.writeBytes(title.getBytes());

		buf.writeInt(author.getBytes().length);
		buf.writeBytes(author.getBytes());


		int pageSize = bookInfo.getPageSize();
		buf.writeInt(pageSize);

		for (int i=0; i<pageSize; i++) {
			String page = bookInfo.getPage(i);
			buf.writeInt(page.getBytes().length);
			buf.writeBytes(page.getBytes());

		}

	}

}
