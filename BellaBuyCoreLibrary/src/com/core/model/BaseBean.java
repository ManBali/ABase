package com.core.model;

import java.io.Serializable;

public class BaseBean implements Serializable {
	private static final long serialVersionUID = 5064565652428690188L;
	private int isSuccess;  //请求后状态标识码   
    
	public String msg="";

	private int curPage;

	private int nextPage;

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(int isSuccess) {
		this.isSuccess = isSuccess;
	}

	@Override
	public String toString() {
		return "BaseBean [isSuccess=" + isSuccess + "]";
	}
	
}
