package com.core.api;


/**
 * @author sufun.wu
 * @createdate 2015年1月17日 下午10:35:26
 * @Description: 错误码说明
 */
public class ConstantsCode {


	/**
	 * @author sufun.wu
	 * @createdate 2015年3月18日 下午2:06:09
	 * @Description: 成功状态码
	 * @param code
	 * @return
	 */
	public static boolean successCode(int code) {
		boolean flag = false;
		switch (code) {
		case 0: // 错误--》0：暂无沟通信息
			flag = true;
			break;
		case 1: // 成功
			flag = true;
			break;
		default:
			flag = false;
			break;
			
		}
		return flag;
	}

	/**
	 * @author sufun.wu
	 * @createdate 2015年3月18日 下午2:05:43
	 * @Description: 失败错误码
	 * @return
	 */
	public static int errorCode(int code) {
		int resStringId=0;//R.string.error_code_parameter_anomaly;
		switch (code) {
		case 2: // 参数异常     用户有可能是以匿名状态的登陆，导致有一些接口返回提示参数异常。
			resStringId = 0;//R.string.error_code_parameter_anomaly;   错误码对应的提示
			break;
		default:
			resStringId = 0;//R.string.error_code_unknown_error;
			break;
		}
		return resStringId;
	}
	public static final int CODE_COD_ERROR=52;  //货到付款创建订单失败

	public static final int CODE_COD_ERROR_UNUPLOAD=0;  //审核需上传照片

	public static final int CODE_COD_ERROR_PROCESSING=1;  //待审核

	public static final int CODE_COD_ERROR_PASSED=2;   //审核通过

	public static final int CODE_COD_ERROR_FAIL=3;    //审核失败

	public static final String KEY_LONGITUDE="KEY_LONGITUDE";   //用户所在的经度

	public static final String KEY_LATITUDE="KEY_LATITUDE";   //用户所在的纬度

	public static final String KEY_COUNTRY_CODE="KEY_COUNTRY_CODE";  //用户所在的国家代码
}
