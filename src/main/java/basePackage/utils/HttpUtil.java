package basePackage.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class HttpUtil {
	private static final String CHAR_SET="utf-8";

    private final static int CONNECT_TIMEOUT = 5000; // in milliseconds  

	public static String post(String url,Map<String,String>map) {
		HttpClient client= HttpClientBuilder.create().build();
		InputStream inputStream=null;
		StringBuffer stringBuffer = null;
		try {
			URI uri = new URI(url);
			HttpPost post = new HttpPost(uri);
			//添加参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for(String str:map.keySet()) {
				params.add(new BasicNameValuePair(str, map.get("str")));
			}
			post.setEntity(new UrlEncodedFormEntity(params,CHAR_SET));
			//执行请求
			HttpResponse response = client.execute(post);
			if(response.getStatusLine().getStatusCode() == 200) {
				//处理请求结果
				stringBuffer = new StringBuffer();
				String line = null;
				inputStream = response.getEntity().getContent();
				BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,CHAR_SET));
				while((line=reader.readLine())!=null) {
					stringBuffer.append(line);
				}
			}
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}finally {
			//关闭流
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return stringBuffer==null?null:stringBuffer.toString();
	}
	  public static String postData(String urlStr, String data){  
          BufferedReader reader = null;  
          try {  
              URL url = new URL(urlStr);  
              URLConnection conn = url.openConnection();  
              conn.setDoOutput(true);  
              conn.setConnectTimeout(CONNECT_TIMEOUT);  
              conn.setReadTimeout(CONNECT_TIMEOUT);  
//              if(contentType != null)  
//                  conn.setRequestProperty("content-type", contentType);  
              OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), CHAR_SET);  
              if(data == null)  
                  data = "";  
              writer.write(data);   
              writer.flush();  
              writer.close();    

              reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), CHAR_SET));  
              StringBuilder sb = new StringBuilder();  
              String line = null;  
              while ((line = reader.readLine()) != null) {  
                  sb.append(line);  
                  sb.append("\r\n");  
              }  
              return sb.toString();  
          } catch (IOException e) {  
              //logger.error("Error connecting to " + urlStr + ": " + e.getMessage());  
          } finally {  
              try {  
                  if (reader != null)  
                      reader.close();  
              } catch (IOException e) {  
              }  
          }  
          return null;  
      }  

	public static String get(String url) {
		HttpClient client= HttpClientBuilder.create().build();
		InputStream inputStream=null;
		StringBuffer stringBuffer = null;
		try {
			URI uri = new URI(url);
//			HttpPost post = new HttpPost(uri);
			HttpGet get = new HttpGet(uri);
			//添加参数
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			for(String str:map.keySet()) {
//				params.add(new BasicNameValuePair(str, map.get("str")));
//			}
//			post.setEntity(new UrlEncodedFormEntity(params,CHAR_SET));
			//执行请求
			HttpResponse response = client.execute(get);
			if(response.getStatusLine().getStatusCode() == 200) {
				//处理请求结果
				stringBuffer = new StringBuffer();
				String line = null;
				inputStream = response.getEntity().getContent();
				BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,CHAR_SET));
				while((line=reader.readLine())!=null) {
					stringBuffer.append(line);
				}
			}
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}finally {
			//关闭流
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return stringBuffer==null?null:stringBuffer.toString();
	}

}
