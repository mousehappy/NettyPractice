package com.shwang.netty.backend;

import io.netty.channel.Channel;

public class User {
	public String uid;
	public String ip;
	public int client_port;
	public int peer_port;
	public Channel channel;
}
