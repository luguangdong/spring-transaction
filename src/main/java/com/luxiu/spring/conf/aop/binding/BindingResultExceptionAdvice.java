package com.luxiu.spring.conf.aop.binding;

import com.luxiu.spring.conf.exceptions.BusinessException;
import com.luxiu.spring.conf.response.ResponseCode;
import com.luxiu.spring.conf.response.ResponseResult;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * <p>
 * Description: 统一处理表单绑定验证切面
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName BindingResultExceptionAdvice
 * @date 2020/8/15 13:19
 * @company https://www.beyond.com/
 */
@Aspect
@Component
public class BindingResultExceptionAdvice {

	@Around("execution(* com.luxiu.spring.controller.datasourcetwo.*.*create*(..)) && args(..,bindingResult)")
	public Object around(ProceedingJoinPoint joinPoint, BindingResult bindingResult) throws Throwable {
		if (bindingResult.hasErrors()) {
			StringBuffer sb = new StringBuffer();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				sb.append(fieldError.getField() + ":" + fieldError.getDefaultMessage() + ";");
			}
			throw new BusinessException(ResponseCode.FAILURE.getCode(), sb.toString());
		}
		else {
			joinPoint.proceed();
		}
		return ResponseResult.success("执行成功");
	}

}
