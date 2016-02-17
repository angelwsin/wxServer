package com.weixin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import com.weixin.util.JSONUtil;

public class TokenService {
	             private static final String APPID ="wx75475762bb0ef533";
	             private static final String SECRET ="dbd1416aacfd23deba93e9b285e3d19c";
	             private static final String TOKEN_ACCESS_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	             private static final String     JSAPI_TICKET   ="https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	             private static final ConcurrentMap<String,Object>  tokenMap = new ConcurrentHashMap<String,Object>();
	
	       public static   String getAcessToken(){
		          HttpClient  client =  HttpClients.createDefault();
		         String url = TOKEN_ACCESS_URL+"&appid="+APPID+"&secret="+SECRET;
		         HttpGet get = new HttpGet(url);
		        get.setHeader(" Content-Type", "text/html;charset=utf-8");
		        try {
		        	HttpResponse response = 	client.execute(get);
		        	StatusLine status = response.getStatusLine();
		        	if(status.getStatusCode()==200){
		        		 // 获取响应消息实体  
		                HttpEntity entity = response.getEntity();  
		                InputStream is  = entity.getContent();
		               BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		               StringBuffer buffer = new StringBuffer();
		               String s =null;
		               while((s=reader.readLine())!=null){
		            	   buffer.append(s);
		               }
		               is.close();
		               reader.close();
		               return buffer.toString();
		        	}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        return null;
	   }  
	       public static   String getJsapiTicket(){
		          HttpClient  client =  HttpClients.createDefault();
		         String url = JSAPI_TICKET+"?access_token="+TokenService.acessToken()+"&type=jsapi";
		         HttpGet get = new HttpGet(url);
		        get.setHeader(" Content-Type", "text/html;charset=utf-8");
		        try {
		        	HttpResponse response = 	client.execute(get);
		        	StatusLine status = response.getStatusLine();
		        	if(status.getStatusCode()==200){
		        		 // 获取响应消息实体  
		                HttpEntity entity = response.getEntity();  
		                InputStream is  = entity.getContent();
		               BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		               StringBuffer buffer = new StringBuffer();
		               String s =null;
		               while((s=reader.readLine())!=null){
		            	   buffer.append(s);
		               }
		               is.close();
		               reader.close();
		               return buffer.toString();
		        	}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        return null;
	   }   
	       public static String acessJsapiTicket(){
	    	    if(tokenMap.get("ticket")==null){
	    	    	ticketCache();
	    	    }else{
	    	    	long end  = (Long) tokenMap.get("expires_in");
	    	    	 if(end-System.currentTimeMillis()<10*1000){
	    	    		 ticketCache();
	    	    	 }
	    	    }
	    	    return   (String) tokenMap.get("ticket");
	       }
	     public static String acessToken(){
	    	    if(tokenMap.get("access_token")==null){
	    	    	tokenCache();
	    	    }else{
	    	    	long end  = (Long) tokenMap.get("expires_in");
	    	    	 if(end-System.currentTimeMillis()<10*1000){
	    	    		 tokenCache();
	    	    	 }
	    	    }
	    	    return   (String) tokenMap.get("access_token");
	       }
	     @SuppressWarnings("unchecked")
			private static  void ticketCache(){
		    	 Map<String,Object> map =   JSONUtil.getJsonT(getJsapiTicket(), Map.class);
		    	    String token =(String) map.get("ticket");
		    	    if(token!=null){
			    	    int  expires_in=   	 (Integer) map.get("expires_in");
			    	    tokenMap.put("ticket", token);
			    	    tokenMap.put("expires_in", (System.currentTimeMillis()+expires_in*1000));
		    	    }
		     }
	     @SuppressWarnings("unchecked")
		private static  void tokenCache(){
	    	 Map<String,Object> map =   JSONUtil.getJsonT(getAcessToken(), Map.class);
	    	    String token =(String) map.get("access_token");
	    	    if(token!=null){
		    	    int  expires_in=   	 (Integer) map.get("expires_in");
		    	    tokenMap.put("access_token", token);
		    	    tokenMap.put("expires_in", (System.currentTimeMillis()+expires_in*1000));
	    	    }
	     }
	  public static void main(String[] args) {
		  // System.out.println(acessToken());
		  // System.out.println(acessToken());
		  System.out.println(acessJsapiTicket());
		  System.out.println(acessJsapiTicket());
	}   

}
