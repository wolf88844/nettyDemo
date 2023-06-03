package org.example.client.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.protocol.response.LoginResponsePacket;
import org.example.session.Session;
import org.example.util.SessionUtil;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    public static final LoginResponseHandler INSTANCE = new LoginResponseHandler();
    private LoginResponseHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket) throws Exception {
        Channel channel = channelHandlerContext.channel();
        if(loginResponsePacket.isSuccess()){
            System.out.println(loginResponsePacket.getUserId()+":"+loginResponsePacket.getUserName()+"客户端登录成功");
            SessionUtil.bindSession(new Session(loginResponsePacket.getUserId(), loginResponsePacket.getUserName()),channel);
        }else{
            System.out.println("客户端登录失败，原因："+loginResponsePacket.getReason());
        }
    }
}
