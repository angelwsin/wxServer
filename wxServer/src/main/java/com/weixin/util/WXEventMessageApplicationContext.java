package com.weixin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
@Component
public class WXEventMessageApplicationContext extends AbstractXmlApplicationContext{
	 /*直接拿容器中的
	  * ApplicationContext自动到本地容器里找一个名字为”“的ApplicationEventMulticaster实现，
	  * 如果没有自己new一个SimpleApplicationEventMulticaster
	  */
	          @Autowired
	          private ApplicationEventMulticaster applicationEventMulticaster;
	          /*
	           * (non-Javadoc)
	           * 复写publishEvent 去除了父类调用的步骤
	           */
	        @Override
	        public void publishEvent(ApplicationEvent event) {
	        	// TODO Auto-generated method stub
	        	Assert.notNull(event, "Event must not be null");
	    		if (logger.isTraceEnabled()) {
	    			logger.trace("Publishing event in " + getDisplayName() + ": " + event);
	    		}
	    		applicationEventMulticaster.multicastEvent(event);
	        }
		
			public void setApplicationEventMulticaster(ApplicationEventMulticaster applicationEventMulticaster) {
				this.applicationEventMulticaster = applicationEventMulticaster;
			}
	        
	        

}
