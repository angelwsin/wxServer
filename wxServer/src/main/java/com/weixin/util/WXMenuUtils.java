package com.weixin.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.weixin.menu.bean.WXMenu;
import com.weixin.menu.bean.WXTopMenu;

public class WXMenuUtils {
	  private static  final Properties  WXMenu= new Properties();;
	  static{
		 InputStream is = WXMenuUtils.class.getClassLoader().getResourceAsStream("wxMenu.properties");
		
		 try {
			WXMenu.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	
	  public static void main(String[] args) throws Exception{
		   WXTopMenu top = new WXTopMenu();
		   WXMenu m1 = new WXMenu();
		  m1.setName("扫码");
		  List<WXMenu> sub1 = new ArrayList<WXMenu>();
		   WXMenu m1s1 = new WXMenu();
		   m1s1.setName("扫码带提示");
		   m1s1.setType("scancode_waitmsg");
		   m1s1.setKey("rselfmenu_0_0");
		   WXMenu m1s2 = new WXMenu();
		   m1s2.setName("扫码推事件");
		   m1s2.setKey("rselfmenu_0_1");
		   m1s2.setType("scancode_push");
		   sub1.add(m1s1);
		   sub1.add(m1s2);
		   m1.setSub_button(sub1);
		   WXMenu m2 = new WXMenu();
			  m2.setName("扫码");
			  List<WXMenu> sub2 = new ArrayList<WXMenu>();
			   WXMenu m2s1 = new WXMenu();
			   m2s1.setName("扫码带提示");
			   m2s1.setType("scancode_waitmsg");
			   m2s1.setKey("rselfmenu_0_0");
			   WXMenu m2s2 = new WXMenu();
			   m2s2.setName("扫码推事件");
			   m2s2.setKey("rselfmenu_0_1");
			   m2s2.setType("scancode_push");
			   sub2.add(m2s1);
			   sub2.add(m2s2);
			   m2.setSub_button(sub2);
		List<WXMenu> l = new ArrayList<WXMenu>();
		l.add(m1);
		//l.add(m2);
		   top.setButton(l);
		   Map<String,String> header = new HashMap<String, String>();
		   Map<String,String> params = new HashMap<String,String>();
		   System.out.println( TokenService.acessToken());
		   // params.put("access_token", TokenService.acessToken());
		   params.put("body", JSONUtil.encode(top));
		    System.out.println(JSONUtil.encodePrettyFormat(top));
		    /**
		     * create
		     * delete
		     * get
		     */
	String result =    URLUtils.requestPost("https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+TokenService.acessToken(), null, params);
		 System.out.println(result);
		    
		   /* File file = new File("d:\\menu.txt");
		    FileInputStream is = new FileInputStream(file);
		    System.out.println(StringUtils.streamToString(is));*/
		   
		
	}
	  
	  

}
