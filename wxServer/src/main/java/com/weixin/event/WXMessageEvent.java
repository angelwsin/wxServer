package com.weixin.event;

import org.springframework.context.ApplicationEvent;

import com.weixin.message.bean.WXMessage;

@SuppressWarnings("serial")
public class WXMessageEvent extends ApplicationEvent{

	public WXMessageEvent(WXMessage source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

}
