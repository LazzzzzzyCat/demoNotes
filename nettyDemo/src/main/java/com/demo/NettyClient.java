package com.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author huwj
 * @date 2020-09-23 08:44
 */
public class NettyClient {

    public static void main(String[] args) {
        NioEventLoopGroup clientGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(clientGroup)
                .channel(NioSocketChannel.class)
                .handler(new HttpClientChannelHandler());
        try {
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6655).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            clientGroup.shutdownGracefully();
        }
    }
}
