package com.shwang.netty.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class IdleEventHandler extends ChannelDuplexHandler {
	
	@Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
			if (e.state() == IdleState.READER_IDLE) {
				System.out.println("Read timeout, close socket channel!");
                ctx.close();
            }
//			else if (e.state() == IdleState.WRITER_IDLE) {
//                ctx.writeAndFlush(new PingMessage());
//            }
        }
    }
}
