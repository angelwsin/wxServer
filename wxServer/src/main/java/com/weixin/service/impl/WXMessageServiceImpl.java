package com.weixin.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.weixin.event.WXMessageEvent;
import com.weixin.message.bean.WXMessage;
import com.weixin.service.WXMessageService;
import com.weixin.util.WXEventMessageApplicationContext;

@Service("wxMessageService")
public class WXMessageServiceImpl  implements WXMessageService{
	              private ApplicationContext applicationContext;
	              @Autowired
	              private WXEventMessageApplicationContext wxEventMessageApplicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		      this.applicationContext = applicationContext;
	}
	/*
	 * 利用spring的事件驱动处理
	 * 接受微信信息处理的总入口
	 */
	public void onMessage(WXMessage message){
	  wxEventMessageApplicationContext.publishEvent(new WXMessageEvent(message));
	}

	      
}
