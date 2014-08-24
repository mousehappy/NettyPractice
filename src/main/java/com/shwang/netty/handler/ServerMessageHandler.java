package com.shwang.netty.handler;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.shwang.netty.backend.User;
import com.shwang.netty.backend.UserDB;
import com.shwang.netty.message.LoginMessage;
import com.shwang.netty.message.Message;
import com.shwang.netty.message.server.ServerFailMessage;
import com.shwang.netty.message.server.ServerOKMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerMessageHandler extends SimpleChannelInboundHandler<Message> {
	private UserDB userDB;
	
	public ServerMessageHandler(UserDB idb)
	{
		userDB = idb;
	}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg)
			throws Exception {
		if(msg instanceof LoginMessage)
		{
			LoginMessage lm = (LoginMessage) msg;
			User user = new User();
			String uid = lm.UserID;
			user.uid = uid;
			user.channel = ctx.channel();
			InetSocketAddress clientAddress = (InetSocketAddress) ctx.channel().remoteAddress();
			user.ip = clientAddress.getHostString();
			user.client_port = clientAddress.getPort();
			user.peer_port = lm.peerPort;
			
			if(!userDB.addUser(uid, user))
			{
				System.out.print("User "+uid+" login failed!!");
				ctx.writeAndFlush(new ServerFailMessage("User "+uid+" already logged in, connection will be closed!"));
				ctx.close();
			}
			else
			{
				System.out.print("User "+uid+" success login!");
				ctx.writeAndFlush(new ServerOKMessage("Hello"+uid+"!!! Welcome to chat server!!!"));
			}
		}
	}

}
