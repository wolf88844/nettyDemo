package org.example.client.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.protocol.response.CreateGroupResponsePacket;
@ChannelHandler.Sharable
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    public static final CreateGroupResponseHandler INSTANCE = new CreateGroupResponseHandler();
    private CreateGroupResponseHandler(){}
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket createGroupResponsePacket) throws Exception {
        System.out.println("群创建成功，id为【"+createGroupResponsePacket.getGroupId()+"】");
        System.out.println("群里有："+createGroupResponsePacket.getUserNameList());
    }
}
