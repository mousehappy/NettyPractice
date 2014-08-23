package com.shwang.netty.handler;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		ByteBuf in = (ByteBuf)msg;
		if(in.isReadable())
		{
			System.out.print("Read in HearBeat! " + in.toString(Charset.forName("UTF-8")));
		}
		
		in.release();
		
        ctx.fireChannelRead(msg);
    }
}
