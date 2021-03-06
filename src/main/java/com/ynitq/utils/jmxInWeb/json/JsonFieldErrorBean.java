package com.ynitq.utils.jmxInWeb.json;

/**
 * <pre>
 * 在json返回时，用于字段校验的错误信息
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年8月8日
 */
public class JsonFieldErrorBean {
	private final String fieldName;
	private final String errorMsg;

	public JsonFieldErrorBean(String fieldName, String errorMsg) {
		super();
		this.fieldName = fieldName;
		this.errorMsg = errorMsg;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}
