package org.example;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {
    public static final AuthHandler INSTANCE = new AuthHandler();
    private AuthHandler(){}
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if(!SessionUtil.hasLogin(channelHandlerContext.channel())){
            channelHandlerContext.channel().close();
        }else{
            channelHandlerContext.pipeline().remove(this);
            super.channelRead(channelHandlerContext, o);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if(SessionUtil.hasLogin(ctx.channel())){
            System.out.println("当前登录验证完毕，无需再次验证，AuthHandler被移除");
        }else{
            System.out.println("无登录验证，强制关闭连接！");
        }
    }
}
