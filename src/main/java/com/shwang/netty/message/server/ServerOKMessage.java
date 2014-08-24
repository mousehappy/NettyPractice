package com.shwang.netty.message.server;

import com.shwang.netty.message.Message;

public class ServerOKMessage implements Message {
	public String message;
	
	public ServerOKMessage( String msg)
	{
		this.message = msg;
	}
}
