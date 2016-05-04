package com.core.bean;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-16 下午3:55:05
 * @Description: 基类
 */
public class RootVo {
	
	//状态码
	protected int statusCode;
	
	//失败描述信息
	protected String message;
	
	//id
	protected String id;
	
	public int getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
