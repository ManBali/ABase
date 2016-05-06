package com.core.api;

import com.core.util.LogUtil;
import com.core.util.constants.CoreConstant;
import com.core.util.encrypt.MD5Util;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Arrays;
public class BellaBuyHttpClient {

	private static AsyncHttpClient client = new AsyncHttpClient();
	
	private static final String APP_NAME="Mobuy";
	private static final String VIA="android";

	static {
		client.setTimeout(50000);
		//设置ssl的通信
		//client.setSSLSocketFactory(getSocketFactory());
	}

	/**
	 * @author caibing.zhang
	 * @createdate 2015年2月1日 上午12:17:27
	 * @Description: 取消所有请求，适用于Activity在Destory
	 */
	public static void cancelAllRequests() {
		client.cancelAllRequests(true);
	}

	/**
	 * @author caibing.zhang
	 * @createdate 2015年1月29日 下午8:58:23
	 * @Description: 配置RequestParams
	 * @param params
	 */
	private static RequestParams configRequestParams(RequestParams params) {
		if (params == null) {
			params = new RequestParams();
		}
		//添加一些公共参数
		params.put("AppName", APP_NAME);
		params.put("via", VIA);
		return sortParamString(params.toString(),params);
		//return params;
	}

	/**
	 * @author caibing.zhang
	 * @createdate 2015年1月29日 下午9:00:23
	 * @Description: GET请求
	 * @param url
	 * @param params
	 * @param responseHandler
	 */
	public static void get(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {

/*		RequestParams pa=configRequestParams(params);


		if (Constants.IS_TEST_FLAG) {
			String[] array = pa.toString().split("&");
			LogUtil.i("--URL-->:" + url);
			for (String string : array) {
				LogUtil.i("--参数-->:" + string);
			}
		}*/
		if(params != null) {
			LogUtil.D("WebRequestApi---------->" + getAbsoluteUrl(url) + "?" + params.toString());
		} else {
			LogUtil.D("WebRequestApi---------->" + getAbsoluteUrl(url));
		}
		client.get(getAbsoluteUrl(url), configRequestParams(params),
				responseHandler);
	}

	/**
	 * @author caibing.zhang
	 * @createdate 2015年1月29日 下午9:00:39
	 * @Description: POST请求
	 * @param url
	 * @param params
	 * @param responseHandler
	 */
	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		url = getAbsoluteUrl(url);
		params = configRequestParams(params);
		if (CoreConstant.IS_TEST_FLAG) {
			String[] array = params.toString().split("&");
			LogUtil.i("--URL-->:" + url);
			for (String string : array) {
				LogUtil.i("--参数-->:" + string);
			}
		}
		LogUtil.D("WebRequestApi---------->"+url+"?"+params.toString());
		client.post(url, params, responseHandler);
	}

	/**
	 * @author caibing.zhang
	 * @createdate 2015年1月29日 下午9:02:13
	 * @Description: 拼接完整URL
	 * @param relativeUrl
	 * @return
	 */
	private static String getAbsoluteUrl(String relativeUrl) {
		return com.core.api.RequestAPI.BASE_URL + relativeUrl;
	}

	/**
	 * 将参数进行首字母排序.
	 * @param paramstr 参数字符串
     */
	private static RequestParams sortParamString(String paramstr,RequestParams mparams){
		RequestParams params = new RequestParams();
		String[] kv = paramstr.split("&");
		Arrays.sort(kv);
		String rawData="";
		for (String str : kv) {
			String[] key_value = str.split("=");
			if (key_value.length<2)
			{
				params.put(key_value[0],"");
				rawData=rawData+"";
			}
			else if (key_value.length==2)
			{
				if ("FILE".equals(key_value[1]))
				{
					return mparams;
				}
				params.put(key_value[0], key_value[1]);
				rawData=rawData+key_value[1];
			}
		}
		params.put("nonce", doEncodeData(rawData));
		return params;
	}

	/**
	 * do the raw data encode
	 * @param rawData
	 * @return
	 */
	public static String doEncodeData(String rawData)
	{
		String temp= MD5Util.md5(rawData);
		String constValue="the key you want to encode";
		String constValue_md5=MD5Util.md5(constValue);
		return MD5Util.md5(temp+constValue_md5);
	}
	/**
	 * 执行SSL的证书加密等
	 */
/*	private static SSLSocketFactory getSocketFactory() {
		// TODO Auto-generated method stub
		SSLSocketFactory sslFactory = null;
		try {
			KeyStore keyStore = KeyStore.getInstance("BKS");

			int bks_version;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
				bks_version = R.raw.mobuy_bks; //The BKS file
			} else {
				bks_version = R.raw.mobuy_old; //The BKS (v-1) file
			}
			InputStream instream = MyApplication.getMyApplication().getResources().openRawResource(bks_version);
			try {
				//加载配置的密码
				keyStore.load(instream,"qugo20140801sufun".toCharArray());
			} catch (CertificateException e) {
				LogUtil.e(" CertificateException" + e.toString());
			}
			sslFactory = new MySSLSocketFactory(keyStore);
		} catch (KeyStoreException e1) {
			// TODO Auto-generated catch block
			LogUtil.e(" KeyStoreException" + e1.toString());
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			LogUtil.e(" NoSuchAlgorithmException" + e1.toString());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			LogUtil.e(" IOException" + e1.toString());
		} catch (UnrecoverableKeyException e1) {
			// TODO Auto-generated catch block
			LogUtil.e(" UnrecoverableKeyException" + e1.toString());
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			LogUtil.e(" KeyManagementException" + e.toString());
		}
		catch (Exception ex)
		{
			LogUtil.e(" Exception" + ex.toString());
		}
		return sslFactory;
	}*/

}
