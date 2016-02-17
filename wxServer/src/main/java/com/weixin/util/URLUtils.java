package com.weixin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
@Component
public class URLUtils {
	
	public static String  requestPost(String url,Map<String,String> headers,Map<String,String> params)throws Exception{
		                    URL  uri =new URL(url);
		                HttpURLConnection conn =       (HttpURLConnection) uri.openConnection();
		                conn.setRequestProperty("Content-Type", "text/html;charset=utf-8");
		                conn.setRequestMethod("POST");
		             // 设置通用的请求属性
		                conn.setRequestProperty("accept", "*/*");
		                conn.setRequestProperty("connection", "Keep-Alive");
		                conn.setRequestProperty("user-agent",
		                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		                // 发送POST请求必须设置如下两行
		                conn.setDoOutput(true);
		                conn.setDoInput(true);
		                conn.connect();
		                OutputStream out =   conn.getOutputStream();
		                StringBuffer buffer  = new StringBuffer();
		               if(params!=null){
		            	   Set<String> keySet = params.keySet();  
			                for(String key : keySet) {  
			                    buffer.append(key).append("=").append(params.get(key)).append("&");
			                }  
			                int index = buffer.lastIndexOf("&");
			                if(index!=-1){
			                	 buffer.deleteCharAt(index);
			                }
		               }
		                out.write(buffer.toString().getBytes("utf-8"));
		                out.flush();
		                out.close();
		                
		                   return StringUtils.streamToString(conn.getInputStream());            
	}
	public static String  requestPostTest(String url,Map<String,String> headers)throws Exception{
        URL  uri =new URL(url);
    HttpURLConnection conn =       (HttpURLConnection) uri.openConnection();
    conn.setRequestProperty("Content-Type", "text/html;charset=utf-8");
    conn.setRequestMethod("POST");
 // 设置通用的请求属性
    conn.setRequestProperty("accept", "*/*");
    conn.setRequestProperty("connection", "Keep-Alive");
    conn.setRequestProperty("user-agent",
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
    // 发送POST请求必须设置如下两行
    conn.setDoOutput(true);
    conn.setDoInput(true);
    conn.connect();
    OutputStream out =   conn.getOutputStream();
    StringBuffer buffer  = new StringBuffer();
    File file  = new File("d:\\weixin.txt");
    InputStream is = new FileInputStream(file);
    byte[] b = new byte[1024];
    int index = 0;
    while((index=is.read(b))!=-1){
      buffer.append(new String(b, 0, index));
    }
    out.write(buffer.toString().getBytes("utf-8"));
    out.flush();
    out.close();
    
       return StringUtils.streamToString(conn.getInputStream());            
}
	
	public static void main(String[] args) throws Exception{
	  String result = 	requestPostTest("https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+TokenService.acessToken(),null);
	  System.out.println(result);
	}

}
