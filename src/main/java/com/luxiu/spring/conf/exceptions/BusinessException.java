package com.luxiu.spring.conf.exceptions;

import com.luxiu.spring.conf.response.ResponseCode;

/**
 * <p>
 * Description: 业务异常
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName BusinessException
 * @date 2020/8/4 20:35
 * @company https://www.beyond.com/
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 6654377093974920789L;

	private Integer code;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public BusinessException() {
	}

	public BusinessException(String message) {
		super(message);
		this.code = -1;
	}

	public BusinessException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public BusinessException(ResponseCode status) {
		super(status.message());
		this.code = status.code();
	}

}
