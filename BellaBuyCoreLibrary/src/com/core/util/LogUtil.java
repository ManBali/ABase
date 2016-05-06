package com.core.util;

import android.util.Log;

import com.core.util.constants.CoreConstant;
import com.core.util.log.CoreLog;

/**
 * @author sufun.wu
 * @createdate 2012-9-17 下午4:01:04
 * @Description: 日志
 */
public class LogUtil extends CoreLog {
	public static boolean isDebug= CoreConstant.IS_TEST_FLAG;
	public static void D(String data)
	{
		if(CoreConstant.IS_TEST_FLAG)
		{
			Log.i("LogUtil  ", "--->" + data);
		}
	}
}
