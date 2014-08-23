package com.shwang.netty.server;

import com.shwang.netty.handler.DefaultAcceptHandler;
import com.shwang.netty.handler.DiscardServerHandler;

import io.netty.bootstrap.ChannelFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {
	private int port;
	
	public DiscardServer(int port)
	{
		this.port = port;
	}
	
	public void run() throws Exception
	{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup(10);
		
		try{
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class);
			bootstrap.handler(new ChannelInitializer<NioServerSocketChannel>() { // (4)
                @Override
                public void initChannel(NioServerSocketChannel ch) throws Exception {
                	ch.pipeline().addLast(new DefaultAcceptHandler());
                   // ch.pipeline().addLast(new DiscardServerHandler());
                }
            });
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                	ch.pipeline().addLast(new DefaultAcceptHandler());
                    ch.pipeline().addLast(new DiscardServerHandler());
                }
            });
			
			//ootstrap.childHandler(new DefaultAcceptHandler());
			//bootstrap.childHandler(new DiscardServerHandler());
			bootstrap.option(ChannelOption.SO_BACKLOG, 128)
			.childOption(ChannelOption.SO_KEEPALIVE, true);
			
			ChannelFuture f = bootstrap.bind(this.port).sync();
			
			f.channel().closeFuture().sync();
			//.channelFactory(new ChannelFactory<NioServerSockeChannel>{
			//});
		}finally
		{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8082;
        }
        new DiscardServer(port).run();
    }
}
