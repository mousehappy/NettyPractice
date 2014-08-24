package com.shwang.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import com.shwang.netty.backend.UserDB;
import com.shwang.netty.handler.DefaultAcceptHandler;
import com.shwang.netty.handler.DiscardServerHandler;
import com.shwang.netty.handler.HeartBeatHandler;
import com.shwang.netty.handler.IdleEventHandler;
import com.shwang.netty.handler.ServerMessageHandler;

public class ChatServer {
	private int port;
	
	public ChatServer(int port)
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
			
			final UserDB globalDB = new UserDB();
			
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                	ch.pipeline().addLast(new DefaultAcceptHandler());
                	ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(60, 10, 0));
                	ch.pipeline().addLast("idleEventHandler", new IdleEventHandler());
                	ch.pipeline().addLast(new HeartBeatHandler());
                	ch.pipeline().addLast("serverMessageHandler", new ServerMessageHandler(globalDB));
                	ch.pipeline().addLast(new DiscardServerHandler());
                }
            });
			
			//ootstrap.childHandler(new DefaultAcceptHandler());
			//bootstrap.childHandler(new DiscardServerHandler());
			bootstrap.option(ChannelOption.SO_BACKLOG, 128)
			.childOption(ChannelOption.SO_KEEPALIVE, true);
			
			System.out.println("ChatServer is starting at port: "+this.port);
			
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
        new ChatServer(port).run();
    }
}

