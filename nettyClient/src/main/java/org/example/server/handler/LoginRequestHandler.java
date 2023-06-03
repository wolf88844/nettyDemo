package org.example.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.protocol.request.LoginRequestPacket;
import org.example.protocol.response.LoginResponsePacket;
import org.example.session.Session;
import org.example.util.SessionUtil;

@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();
    private LoginRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        channelHandlerContext.channel().writeAndFlush(login(channelHandlerContext.channel(),loginRequestPacket));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }

    private LoginResponsePacket login(Channel channel, LoginRequestPacket loginRequestPacket) {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUsername());
        String userId = channel.id().toString();
        loginResponsePacket.setUserId(userId);
        if(valid(loginRequestPacket)){
            System.out.println("["+userId+":"+loginRequestPacket.getUsername()+"]登录成功");
            loginResponsePacket.setSuccess(true);
            SessionUtil.bindSession(new Session(userId,loginRequestPacket.getUsername()),channel);
        }else{
            System.out.println("["+loginRequestPacket.getUsername()+"]登录失败");
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号密码错误");
        }
        return loginResponsePacket;
    }

    private boolean valid(LoginRequestPacket loginRequestPacket){
        return true;
    }

}
