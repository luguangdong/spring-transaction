package com.luxiu.spring.conf.aop.atomikos;

import com.luxiu.spring.conf.response.ResponseCode;
import com.luxiu.spring.conf.response.ResponseResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * <p>
 * Description: atomikos事务增强管理器
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName AtomikosTransactionAdvice
 * @date 2020/8/9 20:45
 * @company https://www.beyond.com/
 */

@Aspect
@Component
public class AtomikosTransactionAdvice {

	@Autowired
	JtaTransactionManager jtaTransactionManager;

	// 设置好增强点
	@Pointcut("execution(* com.luxiu.spring.controller.datasourceone.*.*save*(..))")
	private void atomikosPointCut() {
	}

	// around方法
	@Around("atomikosPointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		UserTransaction userTransaction = null;
		try {
			// 获取事务
			userTransaction = jtaTransactionManager.getUserTransaction();
			// 开启事务
			userTransaction.begin();
			// 这里是核心
			joinPoint.proceed();
			// 事务提交
			userTransaction.commit();

		}
		catch (Exception e) {
			// 事务回滚
			try {
				userTransaction.rollback();
			}
			catch (SystemException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return ResponseResult.failure(ResponseCode.INTERFACE_INNER_INVOKE_ERROR, e);
		}
		return ResponseResult.success("执行成功");
	}

}
