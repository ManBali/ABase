 package com.core.util.http;

import java.security.KeyStore;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-16 下午2:45:03
 * @Description: 自定义HttpClient
 */
public class CoreHttpClient {

	private final static int MAX_TOTAL_CONNECTIONS = 10000;   
	private final static int WAIT_TIMEOUT = 150000;   
	private final static int MAX_ROUTE_CONNECTIONS = 800;   
	private final static int CONNECT_TIMEOUT = 150000;   
	private final static int READ_TIMEOUT = 150000;
	private static HttpClient customerHttpClient;

	private CoreHttpClient() {}
	
	public static HttpClient getInstance(){
		if(null==customerHttpClient){
			synchronized (CoreHttpClient.class) {
				if(null==customerHttpClient){
					customerHttpClient=new CoreHttpClient().getHttpClient();
				}
			}
		}
		return customerHttpClient;
	}

	private HttpClient getHttpClient() {
		if (null == customerHttpClient) {
			HttpParams params = new BasicHttpParams();
			// 设置一些基本参数
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			HttpProtocolParams.setUseExpectContinue(params, true);
			HttpProtocolParams.setUserAgent(params,"Linux; Android 2.2.1;zh-CN; itkt_Android");
			// 设置最大连接数   
			ConnManagerParams.setMaxTotalConnections(params, MAX_TOTAL_CONNECTIONS);   
			// 设置获取连接的最大等待时间   
			ConnManagerParams.setTimeout(params, WAIT_TIMEOUT);   
			// 设置每个路由最大连接数   
			ConnPerRouteBean connPerRoute = new ConnPerRouteBean(MAX_ROUTE_CONNECTIONS);   
			ConnManagerParams.setMaxConnectionsPerRoute(params,connPerRoute);   
			// 设置连接超时时间   
			HttpConnectionParams.setConnectionTimeout(params, CONNECT_TIMEOUT);   
			// 设置读取超时时间   
			HttpConnectionParams.setSoTimeout(params, READ_TIMEOUT);
			try {  
				KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
				SSLSocketFactory socketFactory = new DefaultSSLSocketFactory(trustStore);
				socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				SchemeRegistry schReg = new SchemeRegistry();
				schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
				schReg.register(new Scheme("https", socketFactory, 8444));  
				// 使用线程安全的连接管理来创建HttpClient
				ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
				customerHttpClient = new DefaultHttpClient(conMgr, params);
				customerHttpClient.getParams().setParameter("http.protocol.content-charset", HTTP.UTF_8);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return customerHttpClient;
	}
	
}
