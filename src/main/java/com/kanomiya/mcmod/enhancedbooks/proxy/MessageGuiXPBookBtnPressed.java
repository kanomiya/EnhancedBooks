package com.kanomiya.mcmod.enhancedbooks.proxy;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageGuiXPBookBtnPressed implements IMessage {

    public int data;

    public MessageGuiXPBookBtnPressed(){}

    public MessageGuiXPBookBtnPressed(int par1) {
        data= par1;
    }

    @Override//IMessageのメソッド。ByteBufからデータを読み取る。
    public void fromBytes(ByteBuf buf) {
        data= buf.readInt();
    }

    @Override//IMessageのメソッド。ByteBufにデータを書き込む。
    public void toBytes(ByteBuf buf) {
        buf.writeInt(data);
    }
}
