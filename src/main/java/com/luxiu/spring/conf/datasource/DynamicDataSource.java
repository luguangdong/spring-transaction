package com.luxiu.spring.conf.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * <p>
 * Description: 根据 DatabaseContextHolder 设置的数据源来获取数据源
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName DynamicDataSource
 * @date 2020/7/31 17:46
 * @company https://www.beyond.com/
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DatabaseContextHolder.getCustomerType();
	}

}
