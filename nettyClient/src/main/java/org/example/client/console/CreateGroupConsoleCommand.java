package org.example.client.console;

import io.netty.channel.Channel;
import org.example.protocol.request.CreateGroupRequestPacket;

import java.util.Arrays;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand{
    private static final String USER_ID_SPLIT = ",";
    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();
        System.out.println("【拉人群聊】输入userName列表，之间用英文逗号隔开：");
        String userIds = scanner.next();
        createGroupRequestPacket.setUserNameList(Arrays.asList(userIds.split(USER_ID_SPLIT)));
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
