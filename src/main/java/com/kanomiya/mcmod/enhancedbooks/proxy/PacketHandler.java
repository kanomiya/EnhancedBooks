package com.kanomiya.mcmod.enhancedbooks.proxy;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("EnhancedBooks");


	public static void init() {
		/*IMesssageHandlerクラスとMessageクラスの登録。
		 *第三引数：MessageクラスのMOD内での登録ID。256個登録できる
		 *第四引数：送り先指定。クライアントかサーバーか、Side.CLIENT Side.SERVER*/
		INSTANCE.registerMessage(MessageGuiXPBookBtnPressedHandler.class, MessageGuiXPBookBtnPressed.class, 0, Side.SERVER);
		INSTANCE.registerMessage(MessageXPUpdateHandler.class, MessageXPUpdate.class, 1, Side.CLIENT);
		INSTANCE.registerMessage(MessageBookPrinterOnPrintHandler.class, MessageBookPrinterOnPrint.class, 2, Side.SERVER);

		// INSTANCE.registerMessage(MessageBookTitleListHandler.Client.class, MessageBookTitleList.Server.class, 2, Side.SERVER);
		// INSTANCE.registerMessage(MessageBookTitleListHandler.Server.class, MessageBookTitleList.Client.class, 3, Side.CLIENT);
	}





}
