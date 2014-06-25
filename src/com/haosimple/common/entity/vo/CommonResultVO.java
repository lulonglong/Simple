/**
 *  Copyright(C) 2012 Suntec Software(Shanghai) Co., Ltd.
 *  All Right Reserved.
 */
package com.haosimple.common.entity.vo;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.haosimple.common.entity.BaseVO;
import com.haosimple.common.util.StringUtil;

public class CommonResultVO extends BaseVO {
	@Expose
	protected String status;
	@Expose
	@SerializedName("errorcode")
	protected String errorCode;

	/**
	 * 
	 */
	public CommonResultVO() {
		super();

		status = "0";
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.status = StringUtil.isNotNull(errorCode) ? "1" : "0";
		this.errorCode = errorCode;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	//
	// =================================================================>>
	// [getter/setter]
	//

	public String toJsonString() {
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gs = builder.create();
		return gs.toJson(this);
	}

	public String toXmlString() {
		String midString = "";
		if ("0".equals(getStatus())) {

		} else {
			midString += "<error code=\"" + errorCode + "\"/>";
		}
		return toXmlStringPrefix() + midString + toXmlStringSuffix();
	}

	public String toXmlStringPrefix() {
		return "<result code=\"" + status + "\">";
	}

	public String toXmlStringSuffix() {
		return "</result>";
	}

}
