package org.example.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.protocol.request.MessageRequestPacket;
import org.example.protocol.response.MessageResponsePacket;
import org.example.session.Session;
import org.example.util.SessionUtil;

@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();
    private MessageRequestHandler(){}
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {
        Channel channel = channelHandlerContext.channel();
        System.out.println(channel.id()+"收到消息");
        Session session = SessionUtil.getSession(channel);
        MessageResponsePacket messageResponsePacket = receiveMessage(session, messageRequestPacket);
        Channel toChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());
        if(toChannel!=null && SessionUtil.hasLogin(toChannel)){
            toChannel.writeAndFlush(messageResponsePacket);
        }else{
            System.err.println("["+messageRequestPacket.getToUserId()+"]不在线，发送失败");
        }
    }

    private MessageResponsePacket receiveMessage(Session fromSession,MessageRequestPacket messageRequestPacket){
        System.out.println("收到客户端消息："+messageRequestPacket.getMsg());
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(fromSession.getUserId());
        messageResponsePacket.setFromUserName(fromSession.getUsername());
        messageResponsePacket.setMsg(messageRequestPacket.getMsg());
        return messageResponsePacket;
    }
}
