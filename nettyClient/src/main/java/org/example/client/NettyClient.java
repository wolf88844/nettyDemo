package org.example.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.example.client.console.ConsoleCommandManager;
import org.example.client.console.LoginConsoleCommand;
import org.example.client.handler.CreateGroupResponseHandler;
import org.example.client.handler.LoginResponseHandler;
import org.example.client.handler.LogoutResponseHandler;
import org.example.client.handler.MessageResponseHandler;
import org.example.codec.PacketDecoder;
import org.example.codec.PacketEncoder;
import org.example.util.SessionUtil;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class NettyClient {
    private static final int MAX_RETRY = 10;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //socketChannel.pipeline().addLast(new LifeCycleTestHandler());
                        socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
                        socketChannel.pipeline().addLast(new PacketDecoder());
                        socketChannel.pipeline().addLast(LoginResponseHandler.INSTANCE);
                        socketChannel.pipeline().addLast(MessageResponseHandler.INSTANCE);
                        socketChannel.pipeline().addLast(CreateGroupResponseHandler.INSTANCE);
                        socketChannel.pipeline().addLast(LogoutResponseHandler.INSTANCE);
                        socketChannel.pipeline().addLast(new PacketEncoder());
                    }
                });
        connect(bootstrap, "127.0.0.1", 8000, MAX_RETRY);
    }

    private static void connect(final Bootstrap bootstrap, final String host, final int port, int retry) {
        bootstrap.connect(host, port)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println("连接成功");
                        Channel channel = ((ChannelFuture) future).channel();
                        startConsoleThread(channel);
                    } else if (retry == 0) {
                        System.out.println("重试次数已完");
                    } else {
                        int order = (MAX_RETRY - retry) + 1;
                        int delay = 1 << order;
                        System.out.println("连接失败，第" + order + "次重连");
                        bootstrap.config()
                                .group()
                                .schedule(() -> connect(bootstrap, host, port, retry - 1),
                                        delay, TimeUnit.SECONDS);

                    }
                });
    }

    private static void startConsoleThread(Channel channel) {
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner scanner = new Scanner(System.in);
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(scanner, channel);
                } else {
                    consoleCommandManager.exec(scanner, channel);
                }
            }
        }).start();
    }


}
