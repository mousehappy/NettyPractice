package com.shwang.netty.handler;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
	{
		ByteBuf in = (ByteBuf)msg;
		if(in.isReadable())
		{
			System.out.print("Read in Discard! " + in.toString(Charset.forName("UTF-8")));
			ctx.writeAndFlush(msg);
		}
		else
		{
			System.out.print("Empty message!!");
		}
		//in.release();
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable exception)
	{
		exception.printStackTrace();
		ctx.close();
	}
}
