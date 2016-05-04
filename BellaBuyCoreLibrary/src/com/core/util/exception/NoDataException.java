package com.core.util.exception;

/**
 * @author miaoxin.ye
 * @createdate 2013-12-16 下午2:31:42
 * @Description: 自定义异常类————没有数据返回
 */
public class NoDataException extends Exception{
	
	private static final long serialVersionUID = 738131917943443382L;

	public NoDataException(){
        super();
    }
    
    public NoDataException(String msg){
        super(msg);
    }
    
    public NoDataException(String msg, Throwable cause){
        super(msg, cause);
    }
    
    public NoDataException(Throwable cause){
        super(cause);
    }
    
}