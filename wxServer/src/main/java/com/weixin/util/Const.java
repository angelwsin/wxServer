package com.weixin.util;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletContext;

import com.weixin.message.bean.WXBaseMessage;

public class Const {
     
	 public  static  ServletContext CONTEXT ;
	 public static   String contextPath;
	 public static final  ConcurrentMap<String,String> UPLOAD_DIR =new ConcurrentHashMap<String, String>();
	 static{
		 UPLOAD_DIR.put(WXBaseMessage.MSG_IMAGE, File.separator+"upload"+File.separator+"image");
		 UPLOAD_DIR.put(WXBaseMessage.MSG_VOICE, File.separator+"upload"+File.separator+"voice");
		 UPLOAD_DIR.put(WXBaseMessage.MSG_VIDEO, File.separator+"upload"+File.separator+"video");
		 UPLOAD_DIR.put(WXBaseMessage.MSG_SHORT_VIDEO, File.separator+"upload"+File.separator+"shortvideo");
		 UPLOAD_DIR.put(WXBaseMessage.MSG_SHORT_VIDEO, File.separator+"upload");
	 }
	public static final String DEFAULT_UPLOAD_DIR="default";
	public static final String TEST_PATH="http://angelwsin.tunnel.mobi/weixin/";
	
}
