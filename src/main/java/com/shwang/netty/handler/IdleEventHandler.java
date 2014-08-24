package com.shwang.netty.handler;

import java.net.SocketAddress;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class IdleEventHandler extends ChannelDuplexHandler {
	//private ByteBuf pingMsg;
	
	public IdleEventHandler(){
	}
	
	@Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
			if (e.state() == IdleState.READER_IDLE) {
				SocketAddress client = ctx.channel().remoteAddress();
				System.out.println("No ping message received from client: "+client.toString()+". Close socket!!");
				ctx.close();
            }
			else if (e.state() == IdleState.WRITER_IDLE) {
				ByteBuf pingMsg = Unpooled.buffer(4);
				pingMsg.writeBytes("ping".getBytes(Charset.forName("UTF-8")));
				ctx.writeAndFlush(pingMsg);
            }
        }
    }
}
