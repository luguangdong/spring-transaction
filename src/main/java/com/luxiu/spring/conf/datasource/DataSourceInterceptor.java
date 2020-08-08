package com.luxiu.spring.conf.datasource;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Description: 使用AOP来动态切换数据源
 *
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName DataSourceInterceptor
 * @date 2020/7/31 17:51
 * @company https://www.beyond.com/
 */

@Component
public class DataSourceInterceptor {

	public void setDataSource(JoinPoint jp) {
		DatabaseContextHolder.setCustomerType("dataSource");
	}

	public void setDataSourceTwo(JoinPoint jp) {
		DatabaseContextHolder.setCustomerType("dataSourceTwo");
	}

}
