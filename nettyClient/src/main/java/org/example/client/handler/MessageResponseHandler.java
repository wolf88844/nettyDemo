package org.example.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.protocol.response.MessageResponsePacket;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    public static final MessageResponseHandler INSTANCE = new MessageResponseHandler();
    private MessageResponseHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) throws Exception {
        String fromUserId = messageResponsePacket.getFromUserId();
        String fromUserName = messageResponsePacket.getFromUserName();
        System.out.println(fromUserId+":"+fromUserName+"->"+messageResponsePacket.getMsg());
    }

}
