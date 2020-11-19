package com.demo;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * server handler
 *
 * @author huwj
 * @date 2020-09-23 08:54
 */
public class ServerHttpChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("h1", new HttpServerCodec())
                .addLast("h2", new HttpServerChannelHandler());
    }
}
