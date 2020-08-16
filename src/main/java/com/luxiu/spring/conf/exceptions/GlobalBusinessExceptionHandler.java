package com.luxiu.spring.conf.exceptions;

import com.luxiu.spring.conf.response.ResponseCode;
import com.luxiu.spring.controller.datasourceone.ContentController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * Description: 全局业务异常处理器
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName GlobalBusinessExceptionHandler
 * @date 2020/8/15 12:39
 * @company https://www.beyond.com/
 */
@RestControllerAdvice
public class GlobalBusinessExceptionHandler {

	public static final Logger log = LoggerFactory.getLogger(GlobalBusinessExceptionHandler.class);

	@ExceptionHandler({ BusinessException.class, Exception.class })
	public ResponseEntity<?> handlerException(HttpServletRequest request, Exception ex) {
		Map<String, Object> error = new HashMap<>(2);

		// 业务异常
		if (ex instanceof BusinessException) {
			error.put("code", ((BusinessException) ex).getCode());
			error.put("message", ex.getMessage());
			log.warn("[全局业务异常]\r\n业务编码：{}\r\n异常记录：{}", error.get("code"), error.get("message"));
		}

		// 统一处理 JSON 参数验证
		else if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
			BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
			String msg = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).distinct()
					.collect(Collectors.joining(","));
			error.put("code", HttpStatus.BAD_REQUEST.value());
			error.put("message", msg);
		}
		// 未知错误
		else {
			error.put("code", ResponseCode.UNKNOWN.code());
			//error.put("message", ResponseCode.UNKNOWN.message());
			error.put("message", ex.getMessage());
			log.error(ex.getMessage());
		}

		return new ResponseEntity<>(error, HttpStatus.OK);
	}

}
