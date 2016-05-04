package com.core.util.http;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.core.CoreApplication;
import com.core.util.exception.NoDataException;
import com.core.util.log.CoreLog;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-16 下午2:34:24
 * @Description: Http请求
 */
public class HttpUtil{
	
	private static final String CONNECTION_JSON="json";
	private static final String APP_JSON="APPLICATION/JSON";
	private static final String ACCEPT="Accept-Charset";
	private static final String UTF8 = "UTF-8";
	private static final String UTF8_ES = "UTF-8,*";
	private static final String REQUEST_URL="请求URL:";
	private static final String RESPONSE_STATUS="响应状态码:";
	private static final String RESPONSE_PARAMS="返回的参数为:";
	private static final String TERMINAL_ID="terminalId";
	
	/**
	 * @Description: 上传文件
	 * @param url 请求的URL
	 * @param params 请求的参数封装
	 * @return
	 * @throws Exception
	 */
	public static String uploadFile(String url, LinkedHashMap<String, String> params,File file)throws Exception {
		CoreLog.d(REQUEST_URL + url);
		HttpPost httpost = new HttpPost(url);
		MultipartEntity mpEntity = new MultipartEntity(); // 文件传输
		if (file != null) {
			ContentBody cbFile = new FileBody(file);
			mpEntity.addPart("file", cbFile);
		}
		for (String key : params.keySet()) {
			if (params.get(key) != null) {
				StringBody stringBody = new StringBody(params.get(key), Charset.forName("UTF-8"));
				CoreLog.d("key:"+key+", value:"+stringBody);
				mpEntity.addPart(key, stringBody);
			}
		}
		httpost.setEntity(mpEntity);
		return execute(httpost);
	}

	/**
	 * @Description: post请求
	 * @param url 请求的URL
	 * @param params 请求的参数封装
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, LinkedHashMap<String, Object> params)throws Exception {
		CoreLog.d(REQUEST_URL + url);
		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if(params!=null){
			for (String key : params.keySet()) {
				CoreLog.d("key:"+key+", value:"+params.get(key));
				nvps.add(new BasicNameValuePair(key, params.get(key)==null?null:params.get(key).toString()));
			}
			params.clear();
			params=null;
		}
		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		return execute(httpost);
	}
	
	/**
	 * @Description: post请求
	 * @param methodName 方法名
	 * @param params 请求的参数以json方式发送
	 * @return
	 * @throws Exception
	 */
	public static String postToJson(String url, Object javabean)throws Exception {
		HttpPost httpost = new HttpPost(url);
		String json=JSON.toJSONString(javabean);
		CoreLog.e(json);
		StringEntity se = new StringEntity(json, HTTP.UTF_8);
		httpost.setEntity(se);
		httpost.setHeader(CONNECTION_JSON, APP_JSON);
		httpost.setHeader(HTTP.CONTENT_TYPE, APP_JSON);
		return execute(httpost);
	}
	
	private static String execute(HttpUriRequest req) throws Exception {
		String result = null;
		InputStream instream = null;
		try {
			HttpResponse response =executeLoad(req);
			if (response != null) {
				int statusCode = response.getStatusLine().getStatusCode();
				CoreLog.d(RESPONSE_STATUS+statusCode);
				switch (statusCode) {
				case HttpStatus.SC_OK:
					result = EntityUtils.toString(response.getEntity(), UTF8);
					break;
				case HttpStatus.SC_FORBIDDEN:  // 验证未通过
					break;
				case HttpStatus.SC_NOT_FOUND:  // 请求错误
					break;
				}
			}
		} finally {
			if(instream!=null){
				instream.close();
				instream = null;
			}
		}
		if(TextUtils.isEmpty(result)){
			throw new NoDataException("request not data :"+req.getURI());
		}
		CoreLog.d(RESPONSE_PARAMS+result);
		return result;
	}
	
	public static HttpResponse executeLoad(HttpUriRequest req)throws Exception {
		HttpClient httpclient = CoreHttpClient.getInstance();
		req.addHeader(ACCEPT, UTF8_ES);
		HttpResponse response = httpclient.execute(req);
		return response;
	}
	
}
