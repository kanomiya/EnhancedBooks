package com.kanomiya.mcmod.enhancedbooks.proxy;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageXPUpdate implements IMessage {

    public float exp;
    public int expLevel, expTotal;


    public MessageXPUpdate(){}

    public MessageXPUpdate(float experience, int experienceTotal, int experienceLevel) {
    	exp = experience;
    	expTotal = experienceTotal;
    	expLevel = experienceLevel;
    }

    @Override//IMessageのメソッド。ByteBufからデータを読み取る。
    public void fromBytes(ByteBuf buf) {
        exp = buf.readFloat();
        expTotal = buf.readInt();
        expLevel = buf.readInt();
    }

    @Override//IMessageのメソッド。ByteBufにデータを書き込む。
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(exp);
        buf.writeInt(expTotal);
        buf.writeInt(expLevel);
    }
}
