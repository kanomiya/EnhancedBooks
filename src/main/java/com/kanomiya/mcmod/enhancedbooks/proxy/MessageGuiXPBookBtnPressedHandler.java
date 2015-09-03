package com.kanomiya.mcmod.enhancedbooks.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageGuiXPBookBtnPressedHandler  implements IMessageHandler<MessageGuiXPBookBtnPressed, IMessage> {

	@Override //IMessageHandlerのメソッド
	public IMessage onMessage(MessageGuiXPBookBtnPressed message, MessageContext ctx) {
		//クライアントへ送った際に、EntityPlayerインスタンスはこのように取れる。
		//EntityPlayer player = SamplePacketMod.proxy.getEntityPlayerInstance();
		//サーバーへ送った際に、EntityPlayerインスタンス（EntityPlayerMPインスタンス）はこのように取れる。
		//EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
		//Do something.

		EntityPlayer player = ctx.getServerHandler().playerEntity;
		int buttonPressed = message.data;

		if (player.isDead || player.capabilities.isCreativeMode) return null;
		ItemStack item = player.inventory.getCurrentItem();
		if (buttonPressed == 1) {
			// Add one level to book
			if (player.experienceLevel >= 1 && item.getItemDamage() >= 1) {
				item.damageItem(-1, player);
				player.experienceLevel -= 1;
			}
		}

		if (buttonPressed == 2) {
			while (player.experienceLevel >= 1 && item.getItemDamage() >= 1) {
				// Add all the player's xp! :D
				item.damageItem(-1, player);
				player.experienceLevel -= 1;
			}
		}

		// KK, subtracting now
		if (buttonPressed == -1) {
			// Subtract 1 level from book
			if (item.getItemDamage() < item.getMaxDamage()) {
				item.damageItem(1, player);
				player.experienceLevel += 1;
			}
		}
		if (buttonPressed == -2) {
			// Subtract all the XP from the book! :D
			while (item.getItemDamage() < item.getMaxDamage()) {
				item.damageItem(1, player);
				player.experienceLevel += 1;
			}
		}

		// Send a packet back to the client to tell it to update XP
		// PacketDispatcher.sendPacketToPlayer(new Packet43Experience(player.experience, player.experienceTotal, player.experienceLevel), (Player) player);


		return new MessageXPUpdate(player.experience, player.experienceTotal, player.experienceLevel);
	}
}
