package com.shwang.netty.backend;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;

public class UserDB {
	private ConcurrentHashMap<String, User> userMap;
	private ChannelGroup chanGroup;
	
	public UserDB()
	{
		userMap = new ConcurrentHashMap<String, User>();
		chanGroup = new DefaultChannelGroup("broadcaster", GlobalEventExecutor.INSTANCE);
	}
	
	public boolean addUser(String uid, User iuser)
	{
		if(userMap.contains(uid))
			return false;
		
		userMap.put(uid, iuser);
		synchronized(chanGroup)
		{
			chanGroup.add(iuser.channel);
		}	
		
		return true;
	}
	
	public boolean removeUser(String uid)
	{
		if(userMap.contains(uid))
		{
			synchronized(chanGroup)
			{
				chanGroup.remove(userMap.get(uid).channel);
			}
			
			userMap.remove(uid);
			return true;
		}
		
		return false;
	}
}
