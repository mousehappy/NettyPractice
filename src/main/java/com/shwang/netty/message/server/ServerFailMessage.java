package com.shwang.netty.message.server;

import com.shwang.netty.message.Message;

public class ServerFailMessage implements Message{
	public String message;
	
	public ServerFailMessage(String msg)
	{
		this.message = msg;
	}
}
