package org.example.client.console;

import io.netty.channel.Channel;
import org.example.protocol.request.MessageRequestPacket;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("输入某个用户名：");
        String toUserName = scanner.next();
        System.out.println("输入发送的消息：");
        String msg = scanner.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserName,msg));
    }
}
