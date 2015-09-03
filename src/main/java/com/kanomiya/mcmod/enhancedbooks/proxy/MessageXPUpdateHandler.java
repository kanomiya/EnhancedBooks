package com.kanomiya.mcmod.enhancedbooks.proxy;

import com.kanomiya.mcmod.enhancedbooks.EnhancedBooks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageXPUpdateHandler implements IMessageHandler<MessageXPUpdate, IMessage> {

	@Override //IMessageHandlerのメソッド
	public IMessage onMessage(MessageXPUpdate message, MessageContext ctx) {
		//クライアントへ送った際に、EntityPlayerインスタンスはこのように取れる。
		//EntityPlayer player = SamplePacketMod.proxy.getEntityPlayerInstance();
		//サーバーへ送った際に、EntityPlayerインスタンス（EntityPlayerMPインスタンス）はこのように取れる。
		//EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
		//Do something.

		EntityPlayer player = EnhancedBooks.proxy.getEntityPlayerInstance();

		if (player.isDead || player.capabilities.isCreativeMode) return null;

		player.experience = message.exp;
		player.experienceTotal = message.expTotal;
		player.experienceLevel = message.expLevel;


		return null;//本来は返答用IMessageインスタンスを返すのだが、旧来のパケットの使い方をするなら必要ない。
	}

}

