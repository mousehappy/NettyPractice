package com.shwang.netty.handler;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler;
import io.netty.handler.ipfilter.AbstractRemoteAddressFilter;

@ChannelHandler.Sharable
public class DefaultAcceptHandler extends AbstractRemoteAddressFilter<InetSocketAddress>{

	@Override
	protected boolean accept(ChannelHandlerContext ctx,
			InetSocketAddress remoteAddress) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	protected void channelAccepted(ChannelHandlerContext ctx, InetSocketAddress remoteAddress)
	{
		System.out.println("Accept client from: "+remoteAddress);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e)
	{
		e.printStackTrace();
		ctx.close();
	}

}
