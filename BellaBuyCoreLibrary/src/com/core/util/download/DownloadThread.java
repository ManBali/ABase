package com.core.util.download;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import com.core.util.constants.CoreConstant;
import com.core.util.constants.CoreIntentConstant;
import com.core.util.http.HttpUtil;
import android.os.Handler;
import android.os.Message;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-16 下午2:55:52
 * @Description: 异步下载 
 */
public class DownloadThread implements Runnable{
	
	private String url;
	private Handler handler;
	
	public DownloadThread(String url,Handler handler){
		this.url=url;
		this.handler=handler;
	}
	
	@Override
	public void run() {
		HttpGet get = new HttpGet(url);
		HttpResponse response;
		try {
			response=HttpUtil.executeLoad(get);
			HttpEntity entity = response.getEntity();
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode==HttpStatus.SC_OK){
				float length = entity.getContentLength();
				InputStream is = entity.getContent();
				FileOutputStream fileOutputStream = null;
				if (is != null) {
					File file = new File(CoreConstant.DOWNLOAD_CLIENT_PATH);
					fileOutputStream = new FileOutputStream(file);
					byte[] buf = new byte[1024];
					int ch = -1;
					float count = 0;
					while ((ch = is.read(buf)) != -1) {
						fileOutputStream.write(buf, 0, ch);
						count += ch;
						sendMsg(CoreIntentConstant.DOWNLOAD_PROCESS_LOADING,(int) (count*100/length));
					}
				}
				sendMsg(CoreIntentConstant.DOWNLOAD_PROCESS_COMPLETE,0);
				fileOutputStream.flush();
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			}else{
				sendMsg(CoreIntentConstant.DOWNLOAD_PROCESS_NO,0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			sendMsg(CoreIntentConstant.DOWNLOAD_PROCESS_ERROR,0);
		}
	}
	
	private void sendMsg(int flag,int c) {
		Message msg = new Message();
		msg.what = flag;
		msg.arg1=c;
		handler.sendMessage(msg);
	}
	
}
