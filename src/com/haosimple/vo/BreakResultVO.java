/**
 *  Copyright(C) 2012-2013 Suntec(Shanghai) Software Co., Ltd.
 *  All Right Reserved.
 */
package com.haosimple.vo;

import com.google.gson.annotations.Expose;
import com.haosimple.common.entity.vo.CommonResultVO;


/**
 * Descriptions
 *
 * @version 2014-6-27
 * @author long
 * @since JDK1.6
 *
 */
public class BreakResultVO extends CommonResultVO {
	@Expose
	protected String url;
	
	public void setUrl(String urlString){
		this.url=urlString;
	}
	
}
