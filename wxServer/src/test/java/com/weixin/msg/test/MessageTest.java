package com.weixin.msg.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;

import com.weixin.event.WXMessageEvent;
import com.weixin.message.bean.WXMessage;
import com.weixin.message.bean.WXNewItmesMessage;
import com.weixin.message.bean.WXNewsRespMessage;
import com.weixin.util.WXMessageFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext-context.xml")
@Configuration("classpath*:spring-servlet.xml")
public class MessageTest {

	 @Autowired
	      private ApplicationContext applicationContext;
	 private WebApplicationContext webApplicationContext;
	// @Test
	   public void  testMessage(){
		 FileInputStream is;
		try {
			is = new FileInputStream("d:\\message.txt");
			  WXMessage msg =  WXMessageFactory.getMessageInstance(is);
			    //     applicationContext.publishEvent(new WXMessageEvent(msg));
			  applicationContext.publishEvent(new WXMessageEvent(msg));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	   }
	   @Test
	   public void test(){
		  
		    WXNewsRespMessage msg = new WXNewsRespMessage();
		    WXNewItmesMessage  t = new WXNewItmesMessage();
		    t.setDescription("000");
		    t.setPicUrl("0000");
		    t.setTitle("999");
		    t.setUrl("088");
		    msg.setCreateTime(999999);
		    msg.setFromUserName("0000");
		    msg.setMsgType("image");
		    msg.setToUserName("988888");
		    msg.setArticleCount(1);
		    List<WXNewItmesMessage> ts = new ArrayList<WXNewItmesMessage>();
		    ts.add(t);
		    msg.setArticles(ts);
			String s =WXMessageFactory.getMessageToXml(msg,new String[]{"xml","item"} ,new Class<?>[]{WXNewsRespMessage.class,WXNewItmesMessage.class});
			System.out.println(s);
	   }
}
