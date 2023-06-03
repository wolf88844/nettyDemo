package org.example.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;
import org.example.protocol.request.CreateGroupRequestPacket;
import org.example.protocol.response.CreateGroupResponsePacket;
import org.example.util.IDUtil;
import org.example.util.SessionUtil;

import java.util.ArrayList;
import java.util.List;

@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();
    private CreateGroupRequestHandler(){}
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
        List<String> userNameList = createGroupRequestPacket.getUserNameList();
        DefaultChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        for (String userName : userNameList) {
            String userId = SessionUtil.getUserId(userName);
            if(null==userId){
                System.out.println(userName+"不在线");
                continue;
            }
            Channel channel = SessionUtil.getChannel(userId);
            if(null!=channel){
                channelGroup.add(channel);
            }
        }

        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setGroupId(IDUtil.randomId());
        createGroupResponsePacket.setUserNameList(userNameList);

        channelGroup.writeAndFlush(createGroupResponsePacket);

        System.out.println("群创建成功，id为【"+createGroupResponsePacket.getGroupId()+"】");
        System.out.println("群里有："+createGroupResponsePacket.getUserNameList());
    }
}
